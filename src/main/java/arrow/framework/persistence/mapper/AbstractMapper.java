package arrow.framework.persistence.mapper;

import org.apache.deltaspike.data.api.mapping.QueryInOutMapper;

import arrow.framework.logging.ArrowLogger;
import arrow.framework.logging.ArrowLoggerProducer;
import arrow.mems.persistence.entity.AbstractEntity;

public abstract class AbstractMapper<T extends AbstractEntity> implements QueryInOutMapper<T> {
  protected ArrowLogger logger = ArrowLoggerProducer.getLogger();
}
