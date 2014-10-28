//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(OfficeMapped.class)
public class OfficeMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<OfficeMapped, java.lang.Integer> officeId;
  public static volatile SingularAttribute<OfficeMapped, java.lang.String> accountantPsn;
  public static volatile SingularAttribute<OfficeMapped, java.lang.String> addrCode;
  public static volatile SingularAttribute<OfficeMapped, java.lang.String> corpCode;
  public static volatile SingularAttribute<OfficeMapped, java.lang.String> equipmentMgrPsn;
  public static volatile SingularAttribute<OfficeMapped, java.lang.String> fax;
  public static volatile SingularAttribute<OfficeMapped, java.lang.String> managerPsn;
  public static volatile SingularAttribute<OfficeMapped, java.lang.String> name;
  public static volatile SingularAttribute<OfficeMapped, java.lang.String> officeCode;
  public static volatile SingularAttribute<OfficeMapped, java.lang.String> taxCode;
  public static volatile SingularAttribute<OfficeMapped, java.lang.String> technicianPsn;
  public static volatile SingularAttribute<OfficeMapped, java.lang.String> tel;
  public static volatile SingularAttribute<OfficeMapped, arrow.mems.persistence.entity.Address> address;
  public static volatile SingularAttribute<OfficeMapped, arrow.mems.persistence.entity.Corporate> corporate;
}