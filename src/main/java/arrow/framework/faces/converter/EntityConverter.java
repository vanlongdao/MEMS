package arrow.framework.faces.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.StringUtils;

import arrow.framework.faces.util.ConverterUtils;
import arrow.framework.persistence.EntityManagerProducer;
import arrow.framework.util.Instance;
import arrow.framework.util.NumberUtils;
import arrow.mems.persistence.entity.AbstractEntity;
import arrow.mems.util.string.ArrowStrUtils;

public class EntityConverter<T> implements javax.faces.convert.Converter {
  private final Class<T> entityClass;
  private final SingularAttribute<? super T, ?> currentField;
  private Map<SingularAttribute<? super T, ?>, Object> otherFieldsMap = new HashMap<SingularAttribute<? super T, ?>, Object>();

  public EntityConverter(final Class<T> entityClass, final SingularAttribute<? super T, ?> currentField,
      final Map<SingularAttribute<? super T, ?>, Object> otherFieldsMap) {
    super();
    this.entityClass = entityClass;
    this.currentField = currentField;
    this.otherFieldsMap = otherFieldsMap;
  }

  @Override
  public Object getAsObject(final FacesContext context, final UIComponent component, String plainInputValue) {
    plainInputValue = ArrowStrUtils.idTrim(plainInputValue);
    if (StringUtils.isEmpty(plainInputValue))
      return null;
    // in some cases, we need add a dump item into the list, then allow users to select and convert
    // it to a Special Object.
    final Object specialObject = component.getAttributes().get("specialItem");

    try {
      final Object convertedInput = NumberUtils.convertInputType(plainInputValue, this.currentField.getType().getClass());
      final List<FieldValuePair<T>> otherFieldValues = FieldValuePair.evaluateOtherFields(this.otherFieldsMap);

      final EntityManager em = Instance.get(EntityManagerProducer.class).getEm();
      final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
      final CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(this.entityClass);
      final Root<T> root = criteriaQuery.from(this.entityClass);

      Predicate finalPredicate = criteriaBuilder.equal(root.get(this.currentField), convertedInput);
      for (int i = 0; i < otherFieldValues.size(); i++) {
        final FieldValuePair<T> valuePair = otherFieldValues.get(i);
        finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.equal(root.get(valuePair.getFieldAttribute()), valuePair.getValue()));
      }
      criteriaQuery.where(finalPredicate);
      final TypedQuery<T> query = em.createQuery(criteriaQuery);

      return query.getSingleResult();
    } catch (final PersistenceException | NumberFormatException | SecurityException exp) {
      if (specialObject != null)
        return specialObject;
      else
        throw ConverterUtils.throwConverterException(component, exp, "converter.invalidCode");
    }
  }

  @Override
  public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
    if (value != null) {
      final AbstractEntity abstractEntity = (AbstractEntity) value;
      final Object abstractPk = abstractEntity.getPk();
      return String.valueOf(abstractPk);
    } else
      return null;
  }
}
