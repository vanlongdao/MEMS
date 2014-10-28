package arrow.framework.persistence;

import java.time.LocalDate;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.jboss.weld.exceptions.IllegalArgumentException;

import arrow.framework.persistence.util.Condition;
import arrow.framework.util.DateUtils;
import arrow.framework.util.collections.SelectableList;
import arrow.mems.util.string.ArrowStrUtils;


public class ArrowQuery<E> {
  public static enum Type {
    SELECT, UPDATE, DELETE
  }

  public static final int DEFAULT_MAX_RESULTS = 2000;

  private Type type;

  public Type getType() {
    return this.type;
  }

  private final boolean managed;

  public boolean isManaged() {
    return this.managed;
  }

  private void assertManaged() {
    if (!this.managed)
      throw new UnsupportedOperationException("JPA queries are NOT managed internally");
  }

  private void assertUnmanaged() {
    if (this.managed)
      throw new UnsupportedOperationException("JPA queries are managed internally");
  }

  private String resetResultsEventName;

  public void setResetResultsEventName(final String resetResultsEventName) {
    this.resetResultsEventName = resetResultsEventName;
  }

  private final ResultList<E> resultList = new ResultList<E>(this);

  public ResultList<E> getResultList() {
    return this.resultList;
  }

  @SuppressWarnings("unchecked")
  public E getSingleResult() {
    return (E) this.getJPAQuery().getSingleResult();
  }

  // unmanaged SynQuery
  public ArrowQuery(final Query query, final Query countQuery) {
    this.query = query;
    this.countQuery = countQuery;
    this.managed = false;
  }

  private String queryString;
  private Query query;

  public Query getJPAQuery() {
    if (this.query == null) {
      this.buildJPAQueries();
    }
    return this.query;
  }

  public void setJPAQuery(final Query jpaQuery) {
    this.assertUnmanaged();
    this.query = jpaQuery;
    this.resultList.resetResults();
  }

  public void setLockMode(final LockModeType lockModeType) {
    this.getJPAQuery().setLockMode(lockModeType);
  }

  private String countQueryString;
  private Query countQuery;

  public Query getJPACountQuery() {
    if (this.countQuery == null) {
      this.buildJPAQueries();
    }
    return this.countQuery;
  }

  public void setJPACountQuery(final Query jpaCountQuery) {
    this.assertUnmanaged();
    this.countQuery = jpaCountQuery;
    this.resultList.resetResults();
  }

  private String originalCountQueryString;
  private Query originalCountQuery;

  public Query getJPAOriginalCountQuery() {
    if (this.originalCountQuery == null) {
      this.buildJPAQueries();
    }
    return this.originalCountQuery;
  }

  public void setJPAOriginalCountQuery(final Query jpaOriginalCountQuery) {
    this.assertUnmanaged();
    this.originalCountQuery = jpaOriginalCountQuery;
    this.resultList.resetResults();
  }

  // managed SynQuery
  private EntityManager em;

  public ArrowQuery(final EntityManager em) {
    this.managed = true;
    this.em = em;
  }

  public void setEntityManager(final EntityManager entityManager) {
    this.assertManaged();
    this.em = entityManager;
    this.resetQueries();
  }

  private void forceReCreateQueries() {
    this.query = null;
    this.countQuery = null;
  }

  private void resetQueries() {
    this.assertManaged();
    this.forceReCreateQueries();
    this.resultList.resetResults();
  }

  private StatementList selects;
  private StatementList deletes;
  private StatementList updates;
  private StatementList froms;
  private StatementList leftJoins;
  private StatementList innerJoins;
  private final Condition.Conjunction whereCondition = new Condition.Conjunction();
  private Condition effectiveWhereCondition;
  private Condition havingCondition;
  private StatementList groupBys;
  private StatementList orderBys;
  private Integer maxResults = ArrowQuery.DEFAULT_MAX_RESULTS;
  private Integer firstResult;

  // select
  public ArrowQuery<E> select(final String select) {
    if (this.type == null) {
      this.type = Type.SELECT;
    } else if (this.type != Type.SELECT)
      throw new IllegalStateException("not SELECT");

    this.getSelects().add(select);
    return this;
  }

  public ArrowQuery<E> distinct() {
    this.distinct(true);
    return this;
  }

  public ArrowQuery<E> distinct(final boolean isDistinct) {
    this.getSelects().setPrefix(isDistinct ? "SELECT DISTINCT " : "SELECT ");
    return this;
  }

  // delete/update: not fully implemented
  public ArrowQuery<E> delete(final String delete) {
    if (this.type == null) {
      this.type = Type.DELETE;
    } else if (this.type != Type.DELETE)
      throw new IllegalStateException("not DELETE");

    this.getDeletes().add(delete);
    return this;
  }

  public ArrowQuery<E> update(final String update) {
    if (this.type == null) {
      this.type = Type.UPDATE;
    } else if (this.type != Type.UPDATE)
      throw new IllegalStateException("not UPDATE");

    this.getUpdates().add(update);
    return this;
  }

  // from
  public ArrowQuery<E> from(final String from) {
    this.getFroms().add(from);
    return this;
  }

  // joins
  public ArrowQuery<E> leftJoin(final String join) {
    this.getLeftJoins().add(join);
    return this;
  }

  public ArrowQuery<E> leftJoinFetch(final String join) {
    this.getLeftJoins().add("FETCH " + join);
    return this;
  }

  public void innerJoin(final String join) {
    this.getInnerJoins().add(join);
  }

  // where
  public ArrowQuery<E> where(final Condition where) {
    this.whereCondition.add(where);
    return this;
  }

  public ArrowQuery<E> where(final String condition, final Object... params) {
    return this.where(new Condition(condition, params));
  }

  // group by
  public ArrowQuery<E> groupBy(final String groupBy) {
    this.getGroupBy().add(groupBy);
    return this;
  }

  // having
  public ArrowQuery<E> having(final Condition having) {
    this.havingCondition = having;
    return this;
  }

  // order by
  public ArrowQuery<E> orderBy(final String orderBy) {
    this.getOrderBy().add(orderBy);
    return this;
  }

  // result limit and offset
  public ArrowQuery<E> setMaxResults(final Integer max) {
    this.maxResults = max;
    return this;
  }

  public ArrowQuery<E> setFirstResult(final Integer result) {
    this.firstResult = result;
    return this;
  }

  private final Map<String, String> filters = new HashMap<String, String>();

  public ArrowQuery<E> addFilter(final String name, final ResultList.FilterType filterType, final String queryFragment) {
    // convert legacy DATE filter to DATE_RANGE filter
    if (filterType == ResultList.FilterType.DATE) {
      // extract dateFieldPath from queryFragment by removing " = ?"
      String dateFieldPath = queryFragment.trim();
      dateFieldPath = dateFieldPath.substring(0, dateFieldPath.length() - 1);
      dateFieldPath = dateFieldPath.trim();
      dateFieldPath = dateFieldPath.substring(0, dateFieldPath.length() - 1);
      dateFieldPath = dateFieldPath.trim();

      if (!dateFieldPath.contains(" ") && !dateFieldPath.contains("?") && !dateFieldPath.contains("=")) {
        this.addDateRangeFilter(name, dateFieldPath);
        return this;
      }
    }

    // convert legacy NUMBER filter to support range
    else if ((filterType == ResultList.FilterType.NUMBER) && !queryFragment.contains(">=")) {
      // TODO: need implement to support range of number: >=2 <=4, >2, <4, =3,...
      String numberFieldPath = queryFragment.trim();
      numberFieldPath = numberFieldPath.substring(0, numberFieldPath.length() - 1);
      numberFieldPath = numberFieldPath.trim();
      numberFieldPath = numberFieldPath.substring(0, numberFieldPath.length() - 1);
      numberFieldPath = numberFieldPath.trim();

      if (!numberFieldPath.contains(" ") && !numberFieldPath.contains("?") && !numberFieldPath.contains("=")) {
        this.addNumberFilter(name, numberFieldPath);
        return this;
      }
    }

    this.filters.put(name, queryFragment);
    this.resultList.addFilter(name, filterType);
    return this;

  }

  public ArrowQuery<E> addFilter(final String name, final String queryFragment) {
    return this.addFilter(name, ResultList.FilterType.STRING, queryFragment);
  }

  /**
   * date range filter would give a single input field where user manually type a date range without
   * calendar lookup
   */
  public ArrowQuery<E> addDateRangeFilter(final String name, final String dateFieldPath) {
    final String queryFragment = dateFieldPath + " >= ? AND " + dateFieldPath + " <= ?";
    return this.addFilter(name, ResultList.FilterType.DATE_RANGE, queryFragment);
  }

  /**
   * date from/to filter would give a 2 input fields with calendar lookups
   */
  public ArrowQuery<E> addDateFromToFilter(final String name, final String dateFieldPath) {
    final String queryFragment = dateFieldPath + " >= ? AND " + dateFieldPath + " <= ?";
    return this.addFilter(name, ResultList.FilterType.DATE_FROM_TO, queryFragment);
  }

  /**
   * number range filter
   */
  public ArrowQuery<E> addNumberFilter(final String name, final String numberFieldPath) {
    final String queryFragment = numberFieldPath + " > ? AND " + numberFieldPath + " < ?";
    return this.addFilter(name, ResultList.FilterType.NUMBER, queryFragment);
  }

  // convenience methods to add both filter and sorter
  private static String getNameFromFieldPath(final String fieldPath) {
    return ArrowStrUtils.getLastToken(fieldPath, "\\.");
  }

  public ArrowQuery<E> addFilterAndSorterForString(final String name, final String fieldPath) {
    this.addFilter(name, "UPPER(" + fieldPath + ") LIKE ?");
    this.addSorter(name, fieldPath);

    return this;
  }

  public ArrowQuery<E> addFilterAndSorterForString(final String fieldPath) {
    return this.addFilterAndSorterForString(ArrowQuery.getNameFromFieldPath(fieldPath), fieldPath);
  }

  public ArrowQuery<E> addFilterAndSorterForNumber(final String name, final String fieldPath) {
    this.addNumberFilter(name, fieldPath);
    this.addSorter(name, fieldPath);

    return this;
  }

  public ArrowQuery<E> addFilterAndSorterForNumber(final String fieldPath) {
    return this.addFilterAndSorterForNumber(ArrowQuery.getNameFromFieldPath(fieldPath), fieldPath);
  }

  public ArrowQuery<E> addFilterAndSorterForDateRange(final String name, final String fieldPath) {
    this.addDateRangeFilter(name, fieldPath);
    this.addSorter(name, fieldPath);

    return this;
  }

  public ArrowQuery<E> addFilterAndSorterForDateRange(final String fieldPath) {
    return this.addFilterAndSorterForDateRange(ArrowQuery.getNameFromFieldPath(fieldPath), fieldPath);
  }

  private final Map<String, String> sorters = new HashMap<String, String>();

  public ArrowQuery<E> addSorter(final String name, final String queryFragment) {
    this.sorters.put(name, queryFragment);
    return this;
  }

  public void sort(final String name) {
    this.resultList.sort(name);
  }

  public void sort(final String name, final boolean isAscending) {
    this.resultList.sort(name, isAscending);
  }

  private void buildJPAQueries() {
    final String main =
        this.type == Type.SELECT ? this.getSelects().build() : this.type == Type.DELETE ? this.getDeletes().build() : this.getUpdates().build();

    String selectCount = null;
    if (this.type == Type.SELECT) {
      selectCount = "SELECT COUNT(" + main.substring(7) + ")";
    }

    final String fromsAfterBuild = this.getFroms().build();
    final String leftJoinAfterBuild = this.getLeftJoins().build();
    final String outerJoins = this.getInnerJoins().build();

    if (this.resultList.appliedFilters.isEmpty()) {
      this.effectiveWhereCondition = this.whereCondition;
    } else {
      final Condition.Conjunction conjunction = new Condition.Conjunction();
      conjunction.add(this.whereCondition);
      for (final Condition condition : this.resultList.appliedFilters.values()) {
        conjunction.add(condition);
      }

      this.effectiveWhereCondition = conjunction;
    }
    final String where =
        (this.effectiveWhereCondition == null) || this.effectiveWhereCondition.isEmpty() ? "" : " WHERE " + this.effectiveWhereCondition.build();
    final String originalWhere = (this.whereCondition == null) || this.whereCondition.isEmpty() ? "" : " WHERE " + this.whereCondition.build();

    final String groupBy = this.getGroupBy().build();
    final String having = (this.havingCondition == null) || this.havingCondition.isEmpty() ? "" : " HAVING " + this.havingCondition.build();

    String orderBy = null;
    if (this.resultList.currentSorter != null) {
      String newOrder = this.sorters.get(this.resultList.currentSorter);
      final String[] orders = newOrder.split(",");
      if (orders.length > 1) {
        final StringBuffer tempOrder = new StringBuffer();
        int count = 1;
        for (final String order : orders) {
          tempOrder.append(((count > 1 ? ", " : "") + order + (this.resultList.isAscending ? " ASC" : " DESC")));
          count++;
        }
        newOrder = tempOrder.toString();
      } else {
        newOrder += (this.resultList.isAscending ? " ASC" : " DESC");
      }
      orderBy = " ORDER BY " + newOrder;
    }

    final String defaultOrderBy = this.getOrderBy().build();

    if (orderBy == null) {
      orderBy = defaultOrderBy;
    }

    else {
      if (defaultOrderBy.length() > 0) {
        orderBy = orderBy + "," + StringUtils.trim(defaultOrderBy.substring(defaultOrderBy.indexOf("ORDER BY") + 8));
      }
    }

    final String afterMain = ArrowQuery.replaceQuestionMarks(fromsAfterBuild + leftJoinAfterBuild + outerJoins + where + groupBy + having);
    final String originalAfterMain =
        ArrowQuery.replaceQuestionMarks(fromsAfterBuild + leftJoinAfterBuild + outerJoins + originalWhere + groupBy + having);

    this.queryString = main + afterMain + orderBy;
    this.countQueryString = selectCount == null ? null : selectCount + afterMain;
    this.originalCountQueryString = selectCount == null ? null : selectCount + originalAfterMain;
    // build the query and set the parameters
    this.query = this.em.createQuery(this.queryString);
    this.countQuery = this.countQueryString == null ? null : this.em.createQuery(this.countQueryString);
    this.originalCountQuery = this.originalCountQueryString == null ? null : this.em.createQuery(this.originalCountQueryString);

    final List<Object> params = this.getParams();

    for (int i = 0; i < params.size(); i++) {
      this.query.setParameter(i + 1, params.get(i));
      if (this.countQuery != null) {
        this.countQuery.setParameter(i + 1, params.get(i));
      }

      if ((this.originalCountQuery != null) && ((i + 1) <= this.originalCountQuery.getParameters().size())) {
        this.originalCountQuery.setParameter(i + 1, params.get(i));
      }
    }

    if (this.firstResult != null) {
      this.query.setFirstResult(this.firstResult);
    }
    if (this.maxResults != null) {
      this.query.setMaxResults(this.maxResults);
    }

  }

  public static String replaceQuestionMarks(final String query) {
    final StringBuffer buffer = new StringBuffer(query);
    int pointer = 1;
    int count = 1;

    while (pointer > 0) {
      pointer = buffer.indexOf("?", pointer) + 1;
      if (pointer > 0) {
        buffer.replace(pointer - 1, pointer, "?" + count++);
      }
    }

    return buffer.toString();
  }

  private List<Object> getParams() {
    final List<Object> params = new ArrayList<Object>();
    if ((this.effectiveWhereCondition != null) && !this.effectiveWhereCondition.isEmpty()) {
      params.addAll(this.effectiveWhereCondition.getParameters());
    }
    if ((this.havingCondition != null) && !this.havingCondition.isEmpty()) {
      params.addAll(this.havingCondition.getParameters());
    }
    return params;
  }

  private synchronized StatementList getSelects() {
    if (this.selects == null) {
      this.selects = new StatementList("SELECT ");
      this.selects.setSeparator(", ");
    }
    return this.selects;
  }

  private synchronized StatementList getDeletes() {
    if (this.deletes == null) {
      this.deletes = new StatementList("DELETE ");
      this.deletes.setSeparator(", ");
    }
    return this.deletes;
  }

  private synchronized StatementList getUpdates() {
    if (this.updates == null) {
      this.updates = new StatementList("UPDATE ");
      this.updates.setSeparator(", ");
    }
    return this.updates;
  }

  private synchronized StatementList getFroms() {
    if (this.froms == null) {
      this.froms = new StatementList(" FROM ");
      this.froms.setSeparator(", ");
    }
    return this.froms;
  }

  private synchronized StatementList getLeftJoins() {
    if (this.leftJoins == null) {
      this.leftJoins = new StatementList(" LEFT JOIN ");
      this.leftJoins.setSeparator(" LEFT JOIN ");
    }
    return this.leftJoins;
  }

  private synchronized StatementList getInnerJoins() {
    if (this.innerJoins == null) {
      this.innerJoins = new StatementList(" INNER JOIN ");
      this.innerJoins.setSeparator(" INNER JOIN ");
    }
    return this.innerJoins;
  }

  private synchronized StatementList getOrderBy() {
    if (this.orderBys == null) {
      this.orderBys = new StatementList(" ORDER BY ");
      this.orderBys.setSeparator(", ");
    }
    return this.orderBys;
  }

  private synchronized StatementList getGroupBy() {
    if (this.groupBys == null) {
      this.groupBys = new StatementList(" GROUP BY ");
      this.groupBys.setSeparator(", ");
    }
    return this.groupBys;
  }

  @Override
  public String toString() {
    return System.identityHashCode(this) + " " + this.queryString;
  }

  // result list class
  public static class ResultList<E> extends AbstractList<E> implements SelectableList<E> {
    private static final int DEFAULT_PAGE_SIZE = 20;
    private final ArrowQuery<E> synQuery;

    public ResultList(final ArrowQuery<E> query) {
      this.synQuery = query;
    }

    /****************** Business methods for this result list ******************/

    public void resetResults() {
      this.loadedResultsMap.clear();
      this.resultCount = -1;
      this.originalCount = -1;
      if ((this.synQuery != null) && StringUtils.isNotEmpty(this.synQuery.resetResultsEventName)) {
        // TODO: Need a feature to raise this event.
        // Events.instance().raiseEvent(this.synQuery.resetResultsEventName);
      }
      this.selectedIndexes.clear();
    }

    private int pageSize = ResultList.DEFAULT_PAGE_SIZE;

    public int getPageSize() {
      return this.pageSize;
    }

    public void setPageSize(final int pageSize) {
      this.pageSize = pageSize;
    }

    // Types of filter
    // Only STRING and DATE(single) were implemented at the present
    // Used by xhtml to display related input type like text box, calendar, etc.
    public enum FilterType {
      STRING, NUMBER, DATE, DATE_RANGE, OR_STRING, DATE_FROM_TO, EQUAL_STRING
    }

    // filtering
    // filter helper inner class
    public class Filter {
      private final String name;
      private Object value;
      private Object dateFrom;
      private Object dateTo;

      public Object getDateFrom() {
        return this.dateFrom;
      }

      public void setDateFrom(final Object dateFrom) {
        if ((dateFrom != null) && (this.dateTo != null)) {
          final Date val = (Date) dateFrom;
          final Date val1 = (Date) this.dateTo;
          if (val.after(val1))
            throw new IllegalArgumentException("Date from must be smaller than Date To");
        }

        this.dateFrom = dateFrom;
        if ((this.dateFrom != null) && (this.dateTo == null)) {
          this.dateTo = this.dateFrom;
        }
        this.applyDateFromTo(this.dateFrom, this.dateTo);
      }

      public Object getDateTo() {
        return this.dateTo;
      }

      public void setDateTo(final Object dateTo) {
        if ((dateTo != null) && (this.dateFrom != null)) {
          final Date val = (Date) dateTo;
          final Date val1 = (Date) this.dateFrom;
          if (val.before(val1))
            throw new IllegalArgumentException("Date from must be smaller than Date To");
        }
        this.dateTo = dateTo;
        this.applyDateFromTo(this.dateFrom, this.dateTo);
      }

      private void applyDateFromTo(final Object fromDate, final Object toDate) {
        ResultList.this.applyFilter(this.name, ((fromDate == null) && (this.dateTo == null) ? null : new Date[] {
            (Date) (fromDate == null ? DateUtils.MIN_DATE : fromDate), (Date) (toDate == null ? DateUtils.MAX_DATE : toDate)}),
            FilterType.DATE_FROM_TO);
      }

      private FilterType type;

      // Create a String filter
      Filter(final String name) {
        this(name, FilterType.STRING);
      }

      Filter(final String name, final FilterType type) {
        this.name = name;
        this.type = type;
      }

      public void setValue(final Object value) {
        this.value = value;
        ResultList.this.applyFilter(this.name, value, this.type);
      }

      public String getName() {
        return this.name;
      }

      public Object getValue() {
        return this.value;
      }

      public FilterType getType() {
        return this.type;
      }
    }

    private final Map<String, Condition> appliedFilters = new HashMap<String, Condition>();

    private void applyFilter(final String name, Object value, final ResultList.FilterType type) {
      if ((value == null) || ((value instanceof String) && (((String) value).trim().length() == 0))) {
        this.appliedFilters.remove(name);
      } else {
        if (type == FilterType.DATE_RANGE) {
          Object[] dates = null;
          if (value instanceof String) {
            dates = DateUtils.parseDateRange((String) value);
          } else {
            dates = (LocalDate[]) value;
          }

          if (dates != null) {
            this.appliedFilters.put(name, new Condition(this.synQuery.filters.get(name), dates));
          } else {
            this.filterMap.get(name).setValue(null);
          }
        } else if (type == FilterType.DATE_FROM_TO) {

          // need add time portion.
          final LocalDate dateFrom = ((LocalDate[]) value)[0];
          final LocalDate dateTo = ((LocalDate[]) value)[1];
          final Object[] dates = new Object[] {dateFrom, dateTo};
          this.isAscending = !this.isAscending; // Micheal: bypass reversing order
          this.appliedFilters.put(name, new Condition(this.synQuery.filters.get(name), dates));
        } else if (type == FilterType.OR_STRING) { // for query fragment contains multiple OR
          if (value instanceof String) {
            value = ArrowStrUtils.likePattern((String) value);
          }
          final String conditionFragment = this.synQuery.filters.get(name);
          final String[] fragments = conditionFragment.split("OR");
          final Object[] values = new Object[fragments.length];

          for (int i = 0; i < fragments.length; i++) {
            values[i] = String.valueOf(value);
          }
          this.appliedFilters.put(name, new Condition(this.synQuery.filters.get(name), values));
        } else if (type == FilterType.NUMBER) {
          Object[] intRange = null;

          if (value instanceof String) {
            intRange = ArrowStrUtils.parseNumberRange((String) value);
          } else {
            intRange = (Integer[]) value;
          }

          if (intRange != null) {
            this.appliedFilters.put(name, new Condition(this.synQuery.filters.get(name), intRange));
          }

          else {
            this.filterMap.get(name).setValue(null);
          }
        } else if (type == FilterType.EQUAL_STRING) {
          if (value instanceof String) {
            value = StringUtils.trim((String) value);
          }
          this.appliedFilters.put(name, new Condition(this.synQuery.filters.get(name), value));
        } else {
          if (value instanceof String) {
            value = ArrowStrUtils.likePattern((String) value);
          }
          this.appliedFilters.put(name, new Condition(this.synQuery.filters.get(name), value));
        }
      }
      this.synQuery.resetQueries();
    }

    private final Map<String, Filter> filterMap = new HashMap<String, Filter>();

    private void addFilter(final String name, final FilterType type) {
      this.filterMap.put(name, new Filter(name, type));
    }

    // this is the where the filters can be applied from xhtml: filter('name').setValue('filter
    // value');
    public Filter filter(final String name) {
      return this.filterMap.get(name);
    }

    // sorting
    private String currentSorter;
    private boolean isAscending = true;

    // these are the public method to be called from facelet
    public void sort(final String name) {
      if (name.equals(this.currentSorter)) {
        this.sort(name, !this.isAscending);
      } else {
        this.sort(name, true);
      }
    }

    public void sort(final String name, final boolean isAscending) {
      if (this.synQuery.sorters.containsKey(name)) {
        this.currentSorter = name;
        this.isAscending = isAscending;
        this.synQuery.resetQueries();
      }
    }

    // private void sort(final Comparator<E> comparator)
    // {
    // List<E> temp = new ArrayList<E>(this);
    // Collections.sort(temp, comparator);
    //
    // for (int i = 0; i < this.size(); i++)
    // {
    // this.loadedResultsMap.put(i, temp.get(i));
    // }
    // }

    /********************** Methods for List interface *******************/
    private final Map<Integer, E> loadedResultsMap = new HashMap<Integer, E>();

    @SuppressWarnings("unchecked")
    @Override
    public E get(final int i) {
      if (!this.loadedResultsMap.containsKey(i)) {
        final int firstResult = this.synQuery.firstResult == null ? i : this.synQuery.firstResult + i;
        final int maxResult = this.synQuery.maxResults == null ? this.pageSize : Math.min(this.synQuery.maxResults - firstResult, this.pageSize);

        // try to recreate query with new Entity Manager, because the old Entity Manager is
        // closed after request end.
        this.synQuery.forceReCreateQueries();
        final List<E> results = this.synQuery.getJPAQuery().setFirstResult(firstResult).setMaxResults(maxResult).getResultList();
        for (int j = 0; j < results.size(); j++) {
          this.loadedResultsMap.put(i + j, results.get(j));
        }
      }
      return this.loadedResultsMap.get(i);
    }

    private int resultCount = -1;
    private int originalCount = -1;

    @Override
    public int size() {
      final int size = this.realSize();
      return this.synQuery.maxResults == null ? size : Math.min(this.synQuery.maxResults, size);
    }

    public int realSize() {
      if (this.resultCount < 0) {
        this.resultCount = ((Long) this.synQuery.getJPACountQuery().getSingleResult()).intValue();
      }
      return this.resultCount;
    }

    public int originalSize() {
      if (this.originalCount < 0) {
        this.originalCount = ((Long) this.synQuery.getJPAOriginalCountQuery().getSingleResult()).intValue();
      }
      return this.originalCount;
    }

    public Map<String, Condition> getAppliedFilters() {
      return this.appliedFilters;
    }

    @Override
    public void selectAll() {
      this.selectedIndexes.clear();
      for (int i = 0; i < this.size(); i++) {
        this.selectedIndexes.add(i);
      }
    }

    @Override
    public void deselectAll() {
      this.selectedIndexes.clear();
    }

    @Override
    public void select(final E element, final boolean selected) {
      this.getElementSelectionMap().put(element, selected);
    }

    private final SortedSet<Integer> selectedIndexes = new TreeSet<Integer>();

    @Override
    public SortedSet<Integer> getSelectedIndexes() {
      return this.selectedIndexes;
    }

    private final Map<E, Boolean> elementSelectionMap = new AbstractMap<E, Boolean>() {
      @Override
      public Set<Map.Entry<E, Boolean>> entrySet() {
        throw new UnsupportedOperationException();
      }

      @Override
      public boolean containsKey(final Object key) {
        return ResultList.this.selectedIndexes.contains(key);
      }

      @Override
      public Boolean get(final Object key) {
        return ResultList.this.selectedIndexes.contains(ResultList.this.indexOf(key));
      }

      @Override
      public Boolean put(final E key, final Boolean value) {
        if (value) {
          ResultList.this.selectedIndexes.add(ResultList.this.indexOf(key));
        }

        else {
          ResultList.this.selectedIndexes.remove(ResultList.this.indexOf(key));
        }

        return value;
      }
    };

    @Override
    public Map<E, Boolean> getElementSelectionMap() {
      return this.elementSelectionMap;
    }

    private final Map<Integer, Boolean> indexSelectionMap = new AbstractMap<Integer, Boolean>() {
      @Override
      public Set<Map.Entry<Integer, Boolean>> entrySet() {
        throw new UnsupportedOperationException();
      }

      @Override
      public boolean containsKey(final Object key) {
        final int index = (Integer) key;
        return (index > -1) && (index < ResultList.this.size());
      }

      @Override
      public Boolean get(final Object key) {
        return ResultList.this.selectedIndexes.contains(key);
      }

      @Override
      public Boolean put(final Integer key, final Boolean value) {
        if (value) {
          ResultList.this.selectedIndexes.add(key);
        }

        else {
          ResultList.this.selectedIndexes.remove(key);
        }

        return value;
      }
    };

    @Override
    public Map<Integer, Boolean> getIndexSelectionMap() {
      return this.indexSelectionMap;
    }

    public Map<String, Filter> getFilterMap() {
      return this.filterMap;
    }
  }

  /***************************** List element selection ************************/

  /**
   * Accept type of <code>SynQuery.ResultList</code> and reload up-to-date result from the database.
   *
   * @param resultList
   */

  public static void resetResults(final List<?> resultList) {
    /* Won't use SynCollection.isNotEmpty() as resultList need to reload even if it was empty */
    if ((resultList != null) && (resultList instanceof ArrowQuery.ResultList)) {
      ((ArrowQuery.ResultList<?>) resultList).resetResults();
    }
  }


}
