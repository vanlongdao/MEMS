package arrow.mems.generator.utils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.StringUtils;

import arrow.framework.persistence.EntityManagerProducer;
import arrow.framework.util.Instance;
import arrow.mems.persistence.entity.MDevice;
import arrow.mems.persistence.entity.MtCountry;
import arrow.mems.persistence.entity.Office;
import arrow.mems.persistence.repository.MDeviceRepository;
import arrow.mems.persistence.repository.MtCountryRepository;
import arrow.mems.persistence.repository.OfficeRepository;

public class IdGeneratorUtils {

  @SuppressWarnings({"unchecked", "rawtypes"})
  public static <T> String getMaxCode(final Class<T> type, SingularAttribute codeField, String prefix) {
    final EntityManager em = Instance.get(EntityManagerProducer.class).getEm();
    final CriteriaBuilder cb = em.getCriteriaBuilder();
    final CriteriaQuery<String> query = cb.createQuery(String.class);
    final Root root = query.from(type);
    query.select(root.get(codeField));
    final Predicate whereCondition = cb.like(root.get(codeField), prefix);
    query.where(whereCondition);
    query.orderBy(cb.desc(root.get(codeField)));
    try {
      return em.createQuery(query).setMaxResults(1).getSingleResult();
    } catch (final NoResultException e) {
      return StringUtils.EMPTY;
    }
  }

  public static int getTwoDigitsOfYear(int year) {
    return year % 100;
  }

  public static void validateCountryCode(int countryCode) {
    final MtCountry c = Instance.get(MtCountryRepository.class).findBy(countryCode);
    if (null == c)
      throw new IllegalArgumentException("Input Country code first");
  }

  public static void validateOfficeCode(String pOfficeCode) {
    final Office office = Instance.get(OfficeRepository.class).findActiveOfficeByOfficeCode(pOfficeCode);
    if (null == office)
      throw new java.lang.IllegalArgumentException("Input Office Code first");

  }

  public static void validateMdevCode(String mdevCode) {
    final MDevice mdevice = Instance.get(MDeviceRepository.class).findActiveMdeviceBymdevCode(mdevCode);
    if (null == mdevice)
      throw new java.lang.IllegalArgumentException("Input MDevice Code first");

  }

  public static void validateTwoYearDigits(int pTwoYearDigits) {
    if ((pTwoYearDigits < 0) || (pTwoYearDigits >= 100))
      throw new java.lang.IllegalArgumentException("You must input valid 2 digits of year");
  }
}
