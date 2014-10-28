package arrow.framework.faces.converter;

import java.util.HashMap;
import java.util.Map;

import javax.faces.convert.Converter;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.SingularAttribute;

import arrow.framework.persistence.util.MetaModelUtils;
import arrow.framework.util.i18n.Messages;

public class EntityConstraint<T> {
  private final EntityManager em;
  private SingularAttribute<? super T, ?> currentField;
  private final Map<SingularAttribute<? super T, ?>, Object> otherFieldsMap = new HashMap<SingularAttribute<? super T, ?>, Object>();
  private EntityConverter<T> entityConverter;
  // private SearchService searchService;
  private Class<T> entityClass;

  public EntityConstraint(final Class<T> entityClass, final EntityManager emMain) {
    if (entityClass == null)
      throw new NullPointerException("Attribute entityClass can not be null");

    this.entityClass = entityClass;
    this.em = emMain;
  }

  public Converter getConverter() {
    if (this.entityConverter == null) {
      this.entityConverter = new EntityConverter<T>(this.entityClass, this.getCurrentField(), this.otherFieldsMap);
    }
    return this.entityConverter;
  }

  public void setEntityClass(final Class<T> entityClass) {
    if (entityClass == null)
      throw new IllegalArgumentException("Attribute entityClass can not be null");
    this.entityClass = entityClass;
  }

  public void setCurrentField(final SingularAttribute<? super T, ?> currentField) {
    this.currentField = currentField;
  }

  public SingularAttribute<? super T, ?> getCurrentField() {
    if (this.currentField == null) {
      this.currentField = MetaModelUtils.getSinglePKAttribute(this.entityClass, this.em);
    }
    if (this.currentField == null)
      throw new IllegalArgumentException("You must define currentField for this constraint");

    return this.currentField;
  }

  public void addOtherField(final SingularAttribute<? super T, ?> fieldName, final Object fieldValue) {
    this.otherFieldsMap.put(fieldName, fieldValue);
  }

  public String getSearchPanelTitle() {
    return Messages.get("search-title." + this.entityClass.getName());
  }
}
