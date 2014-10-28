package arrow.framework.persistence;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.swing.SortOrder;

import org.apache.commons.lang3.tuple.Pair;

import arrow.framework.persistence.util.JpaUtils;
import arrow.framework.util.CollectionUtils;
import arrow.framework.util.FilterUtils;

public class ArrowResultList<T> extends AbstractList<T> {
  private static final int PAGE_SIZE = 2;
  private final EntityManager em;
  private final CriteriaBuilder cb;

  private final CriteriaQuery<T> query;
  private final Path<T> selection;

  private final Predicate originalRestriction;

  private final List<Order> originalOrders;
  private final List<Order> additionalOrders = new ArrayList<Order>();


  private int count = -1;
  private final Map<Integer, T> loadedResultsMap = new HashMap<Integer, T>();

  private void resetResults() {
    this.count = -1;
    this.loadedResultsMap.clear();
  }

  // externally set by the user of the query to constraint the result list.
  // there would be separate internal firstResult / maxResult for paging
  private int firstResult = 0;
  private Integer maxResults = null;

  public void setFirstResult(final int i) {
    if (i >= 0) {
      this.firstResult = i;
      this.resetResults();
    }
  }

  public void setMaxResult(final Integer i) {
    if ((i == null) || (i < 0)) {
      this.maxResults = null;
    }

    else {
      this.maxResults = i;
    }

    this.resetResults();
  }


  public ArrowResultList(final EntityManager em, final CriteriaQuery<T> query) {
    this.em = em;
    this.cb = em.getCriteriaBuilder();

    this.query = query;

    try {
      this.selection = (Path<T>) query.getSelection();
    }

    catch (final ClassCastException e) {
      throw new IllegalStateException("ArrowResultList does not support Query with CompoundSelection");
    }

    this.originalRestriction = query.getRestriction();

    this.originalOrders = query.getOrderList();

    if (this.originalOrders.isEmpty())
      throw new IllegalStateException("Must specify at least PK ordering to use with ArrowResultList");
  }

  private Map<String, Object> currentFilters = Collections.emptyMap();
  private List<Pair<String, SortOrder>> currentSorters = Collections.emptyList();



  public void applyFilters(Map<String, Object> filters) {
    if (filters == null) {
      filters = Collections.emptyMap();
    }

    if (!CollectionUtils.equal(filters, this.currentFilters)) {
      this.currentFilters = filters;

      final List<Predicate> filterPredicates = new ArrayList<Predicate>();

      for (final String attribute : filters.keySet()) {
        if (attribute.equalsIgnoreCase("globalFilter")) {
          // no support for global filter
          continue;
        }

        final Path<?> path = JpaUtils.buildPath(this.selection, attribute);
        final Class<?> javaType = path.getJavaType();

        if (javaType.isEnum()) {
          @SuppressWarnings({"unchecked", "rawtypes"})
          final Class<? extends Enum> enumType = (Class<? extends Enum>) javaType;
          try {
            @SuppressWarnings("unchecked")
            final Enum<?> enumValue = Enum.valueOf(enumType, filters.get(attribute).toString().toUpperCase());
            final Predicate pred = this.cb.equal(path, enumValue);
            filterPredicates.add(pred);
          } catch (final IllegalArgumentException e) {
            // Not a member of this Enum Type
            final Predicate pred = this.cb.not(path.in((Object[]) enumType.getEnumConstants()));
            filterPredicates.add(pred);
          }
        }

        else if (String.class.isAssignableFrom(javaType)) {
          @SuppressWarnings("unchecked")
          final Expression<String> stringPath = this.cb.lower((Expression<String>) path);
          final String[] filterStrings = FilterUtils.parseStringForLikeOperator(filters.get(attribute).toString());

          Predicate pred = null;

          for (final String s : filterStrings) {
            final Predicate subPred = this.cb.like(stringPath, s);

            if (pred == null) {
              pred = subPred;
            }

            else {
              pred = this.cb.or(pred, subPred);
            }
          }

          if (pred != null) {
            filterPredicates.add(pred);
          }
        }

        else if (Date.class.isAssignableFrom(javaType)) {
          try {
            @SuppressWarnings("unchecked")
            final Expression<LocalDate> datePath = (Expression<LocalDate>) path;
            final List<LocalDate[]> dateRanges = FilterUtils.parseMultipleDateRanges(filters.get(attribute).toString());

            Predicate pred = null;

            for (final LocalDate[] dateRange : dateRanges) {
              Predicate eachRange;
              if (dateRange[0].equals(dateRange[1])) {
                eachRange = this.cb.equal(path, dateRange[0]);
              }

              else {
                eachRange = this.cb.and(this.cb.greaterThanOrEqualTo(datePath, dateRange[0]), this.cb.lessThanOrEqualTo(datePath, dateRange[1]));
              }

              if (pred == null) {
                pred = eachRange;
              } else {
                pred = this.cb.or(pred, eachRange);
              }
            }

            if (pred != null) {
              filterPredicates.add(pred);
            }
          } catch (final ParseException e) {
            // TODO: filter text ParseException: do we want to display error to the user? or just
            // ignore the invalid filter?
            e.printStackTrace();
          }
        }

        else if (Number.class.isAssignableFrom(javaType)) {
          try {
            @SuppressWarnings("unchecked")
            final Expression<Double> numberPath = (Expression<Double>) path;
            final List<Double[]> numberRanges = FilterUtils.parseMultipleNumberRanges(filters.get(attribute).toString());

            Predicate pred = null;

            for (final Double[] numberRange : numberRanges) {
              Predicate eachRange;
              if (numberRange[0] == null) { // From negative infinity to numberRange[1]
                eachRange = this.cb.lessThanOrEqualTo(numberPath, numberRange[1]);
              } else if (numberRange[1] == null) { // From numberRange[0] to infinity
                eachRange = this.cb.greaterThanOrEqualTo(numberPath, numberRange[0]);
              } else if (numberRange[0].equals(numberRange[1])) { // Equal
                eachRange = this.cb.equal(path, numberRange[0]);
              } else { // Between
                eachRange =
                    this.cb.and(this.cb.greaterThanOrEqualTo(numberPath, numberRange[0]), this.cb.lessThanOrEqualTo(numberPath, numberRange[1]));
              }

              if (pred == null) {
                pred = eachRange;
              } else {
                pred = this.cb.or(pred, eachRange);
              }
            }

            if (pred != null) {
              filterPredicates.add(pred);
            }
          } catch (final ParseException e) {
            e.printStackTrace();
          }
        }
      }


      filterPredicates.add(this.originalRestriction);
      this.query.where(filterPredicates.toArray(new Predicate[] {}));
      this.resetResults();
    }
  }



  public void applySorters(List<Pair<String, SortOrder>> sortSpecs) {
    if (sortSpecs == null) {
      sortSpecs = Collections.emptyList();
    }

    if (!CollectionUtils.equal(sortSpecs, this.currentSorters)) {
      this.currentSorters = sortSpecs;

      this.additionalOrders.clear();

      if (sortSpecs != null) {
        for (final Pair<String, SortOrder> sortSpec : sortSpecs) {
          final String sortField = sortSpec.getLeft();
          final SortOrder sortOrder = sortSpec.getRight();
          if (sortOrder != SortOrder.UNSORTED) {
            final Order order = JpaUtils.buildSortOrder(this.cb, this.selection, sortField, sortOrder == SortOrder.ASCENDING);
            this.additionalOrders.add(order);
          }
        }
      }

      final List<Order> effectiveOrders = new ArrayList<Order>();
      effectiveOrders.addAll(this.additionalOrders);
      effectiveOrders.addAll(this.originalOrders);

      this.query.orderBy(effectiveOrders);
      this.resetResults();
    }
  }



  public T get(final int i) {
    if (!this.loadedResultsMap.containsKey(i)) {
      final int pageFirstResult = this.firstResult + i;
      final int pageMaxResults =
          this.maxResults == null ? ArrowResultList.PAGE_SIZE : Math.min(this.maxResults - pageFirstResult, ArrowResultList.PAGE_SIZE);

      final List<T> results = this.em.createQuery(this.query).setFirstResult(pageFirstResult).setMaxResults(pageMaxResults).getResultList();
      for (int j = 0; j < results.size(); j++) {
        this.loadedResultsMap.put(i + j, results.get(j));
      }
    }
    return this.loadedResultsMap.get(i);
  }


  public int size() {
    if (this.count < 0) {
      final CriteriaQuery<Long> countQuery = JpaUtils.createCountQuery(this.em.getCriteriaBuilder(), this.query);
      this.count = this.em.createQuery(countQuery).getSingleResult().intValue() - this.firstResult;

      if (this.count < 0) {
        this.count = 0;
      }

      else if ((this.maxResults != null) && (this.count > this.maxResults)) {
        this.count = this.maxResults;
      }
    }

    return this.count;
  }


  // to allow sorting for caller that's not aware that this is a SynResultList
  // note that such sorting would cause the list to be loaded fully
  public T set(final int i, final T value) {
    final T previous = this.get(i);
    this.loadedResultsMap.put(i, value);
    return previous;
  }


  // debugging
  // private ArrayList<T> debugExport;

  // export a sublist the debugExport to be examined by the debugger
  // public void debugExport(final int start, final int end) {
  // this.debugExport = new ArrayList<T>();
  // this.debugExport.addAll(this.subList(start, end));
  // }
  //
  // public void debugExportAll() {
  // this.debugExport = new ArrayList<T>();
  // this.debugExport.addAll(this);
  // }
}
