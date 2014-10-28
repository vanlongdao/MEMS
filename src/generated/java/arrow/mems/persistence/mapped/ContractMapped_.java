//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ContractMapped.class)
public class ContractMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<ContractMapped, java.lang.Integer> contractId;
  public static volatile SingularAttribute<ContractMapped, java.lang.String> contractCode;
  public static volatile SingularAttribute<ContractMapped, java.time.LocalDate> contractDate;
  public static volatile SingularAttribute<ContractMapped, java.lang.String> contractType;
  public static volatile SingularAttribute<ContractMapped, java.lang.String> description;
  public static volatile SingularAttribute<ContractMapped, java.time.LocalDate> endDate;
  public static volatile SingularAttribute<ContractMapped, java.lang.Double> feeDiscountPrice;
  public static volatile SingularAttribute<ContractMapped, java.lang.Double> feeDiscountRate;
  public static volatile SingularAttribute<ContractMapped, java.lang.String> hospCode;
  public static volatile SingularAttribute<ContractMapped, java.lang.String> name;
  public static volatile SingularAttribute<ContractMapped, java.lang.Double> partDiscountPrice;
  public static volatile SingularAttribute<ContractMapped, java.lang.Double> partDiscountRate;
  public static volatile SingularAttribute<ContractMapped, java.lang.Double> price;
  public static volatile SingularAttribute<ContractMapped, java.lang.String> serviceOffice;
  public static volatile SingularAttribute<ContractMapped, java.lang.String> servicePsn;
  public static volatile SingularAttribute<ContractMapped, java.time.LocalDate> startDate;
  public static volatile SingularAttribute<ContractMapped, java.lang.Double> tax;
  public static volatile SingularAttribute<ContractMapped, java.lang.Double> totDiscountPrice;
  public static volatile SingularAttribute<ContractMapped, java.lang.Double> totDiscountRate;
}