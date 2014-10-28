package arrow.mems.service;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.deltaspike.jpa.api.transaction.Transactional;

import arrow.framework.logging.ArrowLogger;
import arrow.framework.persistence.qualifier.ArrowDB;
import arrow.mems.config.AppConfig;

@Transactional
public class AbstractService implements Serializable {
  @Inject
  protected ArrowLogger logger;

  @Inject
  @ArrowDB
  protected EntityManager em;

  @Inject
  protected AppConfig appConfig;
}
