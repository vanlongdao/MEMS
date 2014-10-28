package arrow.framework.util;

import java.lang.reflect.InvocationTargetException;

import arrow.mems.persistence.entity.AbstractEntity;

public class DtoCreator<T extends AbstractEntity, K> {
  private final T entityObject;
  private final K dtoObject;

  public DtoCreator(final T entityObject, final K dtoObject) {
    this.entityObject = entityObject;
    this.dtoObject = dtoObject;
  }

  public T getEntity(final K dtoObject) throws IllegalAccessException, InvocationTargetException {
    BeanCopier.copyWithPk(dtoObject, this.entityObject);
    // BeanUtils.copyProperties(this.entityObject, dtoObject);
    // currently PK and Foreign Key hasn't been copied.

    return this.entityObject;

  }

  public K getDto(final T entityObject) {
    BeanCopier.copyWithPk(entityObject, this.dtoObject);
    return this.dtoObject;
  }

}
