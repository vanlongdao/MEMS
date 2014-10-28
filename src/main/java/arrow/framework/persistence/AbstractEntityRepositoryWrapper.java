package arrow.framework.persistence;

import java.io.Serializable;

import javax.enterprise.inject.Vetoed;

import org.apache.deltaspike.data.api.AbstractEntityRepository;

import arrow.mems.persistence.entity.AbstractEntity;

@Vetoed
public abstract class AbstractEntityRepositoryWrapper<E extends AbstractEntity, PK extends Serializable> extends AbstractEntityRepository<E, PK> {
}
