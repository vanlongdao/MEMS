package arrow.framework.persistence;

import javax.persistence.EntityManager;

import arrow.framework.persistence.wrapper.EntityManagerAbstractWrapper;

public class ArrowEntityManagerImpl extends EntityManagerAbstractWrapper implements ArrowEntityManager {

  public ArrowEntityManagerImpl(final EntityManager em) {
    this.wrapped = em;
  }

  private EntityManager wrapped;

  @Override
  protected EntityManager getWrapped() {
    return this.wrapped;
  }

}
