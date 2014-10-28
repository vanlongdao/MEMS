package arrow.framework.persistence.wrapper;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;


/**
 * Pure wrapper that does absolutely nothing except delegating method calls to wrapped object.
 * !!! DO NOT MODIFY THIS CLASS !!!
 * To add/modify functionalities to/of SynEntityManager, update SynEntityManager interface and/or
 * modify SynEntityManagerImpl
 *
 * @author Hugh Nguyen
 */
@SuppressWarnings("rawtypes")
public abstract class EntityManagerAbstractWrapper implements EntityManager {
  protected abstract EntityManager getWrapped();

  @Override
  public void persist(final Object entity) {
    this.getWrapped().persist(entity);
  }

  @Override
  public <T> T merge(final T entity) {
    return this.getWrapped().merge(entity);
  }

  @Override
  public void remove(final Object entity) {
    this.getWrapped().remove(entity);
  }

  @Override
  public <T> T find(final Class<T> entityClass, final Object primaryKey) {
    return this.getWrapped().find(entityClass, primaryKey);
  }

  @Override
  public <T> T find(final Class<T> entityClass, final Object primaryKey, final Map<String, Object> properties) {
    return this.getWrapped().find(entityClass, primaryKey, properties);
  }

  @Override
  public <T> T find(final Class<T> entityClass, final Object primaryKey, final LockModeType lockMode) {
    return this.getWrapped().find(entityClass, primaryKey, lockMode);
  }

  @Override
  public <T> T find(final Class<T> entityClass, final Object primaryKey, final LockModeType lockMode, final Map<String, Object> properties) {
    return this.getWrapped().find(entityClass, primaryKey, lockMode, properties);
  }

  @Override
  public <T> T getReference(final Class<T> entityClass, final Object primaryKey) {
    return this.getWrapped().getReference(entityClass, primaryKey);
  }

  @Override
  public void flush() {
    this.getWrapped().flush();

  }

  @Override
  public void setFlushMode(final FlushModeType flushMode) {
    this.getWrapped().setFlushMode(flushMode);

  }

  @Override
  public FlushModeType getFlushMode() {
    return this.getWrapped().getFlushMode();
  }

  @Override
  public void lock(final Object entity, final LockModeType lockMode) {
    this.getWrapped().lock(entity, lockMode);
  }

  @Override
  public void lock(final Object entity, final LockModeType lockMode, final Map<String, Object> properties) {
    this.getWrapped().lock(entity, lockMode, properties);

  }

  @Override
  public void refresh(final Object entity) {
    this.getWrapped().refresh(entity);

  }

  @Override
  public void refresh(final Object entity, final Map<String, Object> properties) {
    this.getWrapped().refresh(entity, properties);

  }

  @Override
  public void refresh(final Object entity, final LockModeType lockMode) {
    this.getWrapped().refresh(entity, lockMode);
  }

  @Override
  public void refresh(final Object entity, final LockModeType lockMode, final Map<String, Object> properties) {
    this.getWrapped().refresh(entity, lockMode, properties);
  }

  @Override
  public void clear() {
    this.getWrapped().clear();
  }

  @Override
  public void detach(final Object entity) {
    this.getWrapped().detach(entity);
  }

  @Override
  public boolean contains(final Object entity) {
    return this.getWrapped().contains(entity);
  }

  @Override
  public LockModeType getLockMode(final Object entity) {
    return this.getWrapped().getLockMode(entity);
  }

  @Override
  public void setProperty(final String propertyName, final Object value) {
    this.getWrapped().setProperty(propertyName, value);
  }

  @Override
  public Map<String, Object> getProperties() {
    return this.getWrapped().getProperties();
  }

  @Override
  public Query createQuery(final String qlString) {
    return this.getWrapped().createQuery(qlString);
  }

  @Override
  public <T> TypedQuery<T> createQuery(final CriteriaQuery<T> criteriaQuery) {
    return this.getWrapped().createQuery(criteriaQuery);
  }

  @Override
  public <T> TypedQuery<T> createQuery(final String qlString, final Class<T> resultClass) {
    return this.getWrapped().createQuery(qlString, resultClass);
  }

  @Override
  public Query createNamedQuery(final String name) {
    return this.getWrapped().createNamedQuery(name);
  }

  @Override
  public <T> TypedQuery<T> createNamedQuery(final String name, final Class<T> resultClass) {
    return this.getWrapped().createNamedQuery(name, resultClass);
  }

  @Override
  public Query createNativeQuery(final String sqlString) {
    return this.getWrapped().createNativeQuery(sqlString);
  }

  @Override
  public Query createNativeQuery(final String sqlString, final Class resultClass) {
    return this.getWrapped().createNativeQuery(sqlString, resultClass);
  }

  @Override
  public Query createNativeQuery(final String sqlString, final String resultSetMapping) {
    return this.getWrapped().createNativeQuery(sqlString, resultSetMapping);
  }

  @Override
  public void joinTransaction() {
    this.getWrapped().joinTransaction();
  }

  @Override
  public <T> T unwrap(final Class<T> cls) {
    return this.getWrapped().unwrap(cls);
  }

  @Override
  public Object getDelegate() {
    return this.getWrapped().getDelegate();
  }

  @Override
  public void close() {
    this.getWrapped().close();
  }

  @Override
  public boolean isOpen() {
    return this.getWrapped().isOpen();
  }

  @Override
  public EntityTransaction getTransaction() {
    return this.getWrapped().getTransaction();
  }

  @Override
  public EntityManagerFactory getEntityManagerFactory() {
    return this.getWrapped().getEntityManagerFactory();
  }

  @Override
  public CriteriaBuilder getCriteriaBuilder() {
    return this.getWrapped().getCriteriaBuilder();
  }

  @Override
  public Metamodel getMetamodel() {
    return this.getWrapped().getMetamodel();
  }


  @Override
  public Query createQuery(final CriteriaUpdate updateQuery) {
    return this.getWrapped().createQuery(updateQuery);
  }


  @Override
  public Query createQuery(final CriteriaDelete deleteQuery) {
    return this.getWrapped().createQuery(deleteQuery);
  }


  @Override
  public StoredProcedureQuery createNamedStoredProcedureQuery(final String name) {
    return this.getWrapped().createNamedStoredProcedureQuery(name);
  }


  @Override
  public StoredProcedureQuery createStoredProcedureQuery(final String procedureName) {
    return this.getWrapped().createStoredProcedureQuery(procedureName);
  }



  @Override
  public StoredProcedureQuery createStoredProcedureQuery(final String procedureName, final Class... resultClasses) {
    return this.getWrapped().createStoredProcedureQuery(procedureName, resultClasses);
  }


  @Override
  public StoredProcedureQuery createStoredProcedureQuery(final String procedureName, final String... resultSetMappings) {
    return this.getWrapped().createStoredProcedureQuery(procedureName, resultSetMappings);
  }


  @Override
  public boolean isJoinedToTransaction() {
    return this.getWrapped().isJoinedToTransaction();
  }


  @Override
  public <T> EntityGraph<T> createEntityGraph(final Class<T> rootType) {
    return this.getWrapped().createEntityGraph(rootType);
  }


  @Override
  public EntityGraph<?> createEntityGraph(final String graphName) {
    return this.getWrapped().createEntityGraph(graphName);
  }


  @Override
  public EntityGraph<?> getEntityGraph(final String graphName) {
    return this.getWrapped().getEntityGraph(graphName);
  }


  @Override
  public <T> List<EntityGraph<? super T>> getEntityGraphs(final Class<T> entityClass) {
    return this.getWrapped().getEntityGraphs(entityClass);
  }
}
