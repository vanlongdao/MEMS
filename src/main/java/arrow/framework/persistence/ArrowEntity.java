package arrow.framework.persistence;

import java.io.Serializable;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(ArrowEntityListener.class)
public abstract class ArrowEntity implements Serializable {
  // to return the original Entity class, instead of the proxied class, if the entity is proxied.
  public abstract Class<? extends ArrowEntity> getEntityClass();

  public abstract Object getPk();
  //
  // @Override
  // public int hashCode() {
  // return this.getPk() != null ? this.getPk().hashCode() : 0;
  // }
  //
  // @Override
  // public boolean equals(final Object object) {
  // if ((object == null) || (object.getClass() != this.getClass()))
  // return false;
  //
  // final ArrowEntity other = (ArrowEntity) object;
  // return this.getPk() == null ? other.getPk() == null : this.getPk().equals(other.getPk());
  // }
  //
  //
  // @Override
  // public String toString() {
  // try {
  // return this.getClass().getName() + "[PK: " + (this.getPk() != null ? this.getPk().toString() :
  // "null") + "]";
  // } catch (final Exception e) {
  // e.printStackTrace();
  // return "ExceptionInToStringMethod";
  // }
  // }

  // @PostPersist
  // public void postPersist() {
  // CDIUtils.getBeanManager().fireEvent(this, new EntityPersisted.Literal(this.getEntityClass()));
  // }
  //
  // @PostUpdate
  // public void postUpdate() {
  // CDIUtils.getBeanManager().fireEvent(this, new EntityUpdated.Literal(this.getEntityClass()));
  // }
  //
  // @PostRemove
  // public void postRemove() {
  // CDIUtils.getBeanManager().fireEvent(this, new EntityRemoved.Literal(this.getEntityClass()));
  // }
}
