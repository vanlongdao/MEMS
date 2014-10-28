package arrow.framework.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import arrow.framework.logging.ArrowLogger;
import arrow.framework.persistence.qualifier.ArrowDB;

@ApplicationScoped
public class EntityManagerProducer {

  @PersistenceContext(unitName = "mems")
  private EntityManager em;

  // @PersistenceUnit
  // private EntityManagerFactory entityManagerFactory;

  @Inject
  ArrowLogger logger;

  @Produces
  @Default
  @ArrowDB
  @RequestScoped
  public EntityManager create() {
    this.logger.debug("Create  Arrow Entity Manager");
    return this.em;
    // return this.entityManagerFactory.createEntityManager();
    // return new ArrowEntityManagerImpl(this.entityManagerFactory.createEntityManager());
  }

  public void dispose(@Disposes @Default @ArrowDB final EntityManager entityManager) {
    if (entityManager.isOpen()) {
      // this.logger.debug("Close  Arrow Entity Manager");
      // entityManager.close();
    }
  }

  public EntityManager getEm() {
    return this.em;
  }
}
