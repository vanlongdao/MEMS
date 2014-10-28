package arrow.mems.generator.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.StringUtils;
import org.jboss.weld.exceptions.IllegalArgumentException;

import arrow.mems.generator.utils.IdGeneratorUtils;
import arrow.mems.persistence.entity.AbstractEntity;

public abstract class AbstractIdGenerator {
  public static final String MDEV_CHECKLIST = "15";
  public static final String MDEV_CHECKLIST_ITEM = "16";
  public static final String MDEVICE = "14";
  public static final String TYPE_PATTERN = "%2s";
  public static final int TYPE_PATTERN_LEN = 2;
  public static final String COUNTRY_ID_PATTERN = "%03d";
  public static final int COUNTRY_PATTERN_LEN = 3;
  public static final String YEAR_PATTERN = "%02d";
  public static final int YEAR_PATTERN_LEN = 2;
  public static final String OFFICE_PATTERN = "%12s";
  public static final int OFFICE_PATTERN_LEN = 12;
  public static final String MDEVICE_PATTERN = "%8s";
  public static final int MDEVICE_PATTERN_LEN = 8;

  public abstract SingularAttribute<?, java.lang.String> getCodeField();

  public abstract Class<? extends AbstractEntity> getEntityClass();

  /**
   * Expected length of code
   *
   * @return expected length of code
   */
  public abstract int getExpectedLength();

  /**
   * find Max Code with same prefix in database.
   *
   * @param pPrefix
   * @return
   */
  public String getMaxCode(String pPrefix) {
    return IdGeneratorUtils.getMaxCode(this.getEntityClass(), this.getCodeField(), this.getPrefixValue() + "%");
  }

  public String getNext() {
    this.validateRequiredParams();
    return this.getNextId();
  }

  /**
   * Pattern of Prefix
   *
   * @return
   */
  public abstract String getPrefix();

  public abstract int getPrefixLength();

  /**
   * Prefix value after fill all input params into Prefix Pattern
   *
   * @return
   */
  public abstract String getPrefixValue();

  /**
   * Type of code
   *
   * @return
   */
  public abstract String getTypeCode();

  /**
   * Validate input params which required to generate code
   */
  public abstract void validateRequiredParams();

  private String getNextId() {
    final String prefix = this.getPrefixValue();

    // find max id with prefix
    final String maxCode = this.getMaxCode(prefix);
    int nextIndex = 0;
    if (!StringUtils.isEmpty(maxCode)) {
      final Matcher matcher = Pattern.compile(this.getSerialExp()).matcher(maxCode);
      if (matcher.matches()) {
        final String maxIndex = matcher.group(1);
        nextIndex = Integer.parseInt(maxIndex) + 1;
      }
    } else {
      nextIndex = 1;
    }

    final String nextCode = prefix + String.format(this.getSerialPattern(), nextIndex);

    // check length
    if (this.getExpectedLength() != nextCode.length())
      throw new IllegalArgumentException("Wrong Length");
    return nextCode;
  }

  private String getSerialExp() {
    return ".*(\\d{" + this.getSerialLength() + "}$)";
  }

  /**
   * Length of serial part in code
   *
   * @return
   */
  private int getSerialLength() {
    return this.getExpectedLength() - this.getPrefixLength();
  }

  private String getSerialPattern() {
    return "%0" + this.getSerialLength() + "d";
  }
}
