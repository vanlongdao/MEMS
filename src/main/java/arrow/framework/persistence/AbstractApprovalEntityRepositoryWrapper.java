package arrow.framework.persistence;

import java.io.Serializable;

import javax.enterprise.inject.Vetoed;

import org.apache.deltaspike.data.api.EntityManagerDelegate;

import arrow.mems.persistence.entity.AbstractApprovalEntity;
import arrow.mems.persistence.entity.Users;

@Vetoed
public abstract class AbstractApprovalEntityRepositoryWrapper<E extends AbstractApprovalEntity, PK extends Serializable> extends
    AbstractDeletableRepositoryWrapper<E, PK> implements EntityManagerDelegate<E> {
  public Users getCreatedByUser(E entity) {
    return this.entityManager().find(Users.class, entity.getCreatedBy());
  }

  public Users getCheckedByUser(E entity) {
    return this.entityManager().find(Users.class, entity.getCheckedBy());
  }
}
