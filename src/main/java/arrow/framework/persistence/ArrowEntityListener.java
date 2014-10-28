package arrow.framework.persistence;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;

import arrow.framework.persistence.qualifier.EntityPersisted;
import arrow.framework.persistence.qualifier.EntityPrePersisted;
import arrow.framework.persistence.qualifier.EntityRemoved;
import arrow.framework.persistence.qualifier.EntityUpdated;
import arrow.framework.util.CDIUtils;

public class ArrowEntityListener {
  @PostPersist
  public void postPersist(ArrowEntity entity) {
    CDIUtils.getBeanManager().fireEvent(this, new EntityPersisted.Literal(entity.getEntityClass()));
  }

  @PostUpdate
  public void postUpdate(ArrowEntity entity) {
    CDIUtils.getBeanManager().fireEvent(this, new EntityUpdated.Literal(entity.getEntityClass()));
  }

  @PostRemove
  public void postRemove(ArrowEntity entity) {
    CDIUtils.getBeanManager().fireEvent(this, new EntityRemoved.Literal(entity.getEntityClass()));
  }

  @PrePersist
  public void prePersist(ArrowEntity entity) {
    CDIUtils.getBeanManager().fireEvent(this, new EntityPrePersisted.Literal(entity.getEntityClass()));
  }
}
