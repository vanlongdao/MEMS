package arrow.framework.persistence;

import java.io.Serializable;

import javax.enterprise.inject.Vetoed;

import org.apache.deltaspike.data.api.AbstractEntityRepository;

import arrow.mems.constant.CommonConstants;
import arrow.mems.persistence.entity.AbstractDeletable;

@Vetoed
public abstract class AbstractDeletableRepositoryWrapper<E extends AbstractDeletable, PK extends Serializable> extends
AbstractEntityRepository<E, PK> {
  public E deleteItem(E entity) {
    entity.setIsDeleted(CommonConstants.DELETE);
    return this.saveAndFlush(entity);
  }

}
