//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(DeviceMapped.class)
public class DeviceMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<DeviceMapped, java.lang.Integer> devId;
  public static volatile SingularAttribute<DeviceMapped, java.time.LocalDate> acceptanceDate;
  public static volatile SingularAttribute<DeviceMapped, java.lang.String> acqCode;
  public static volatile SingularAttribute<DeviceMapped, java.lang.String> clientMgmtCode;
  public static volatile SingularAttribute<DeviceMapped, java.lang.String> devCode;
  public static volatile SingularAttribute<DeviceMapped, java.time.LocalDate> expireDate;
  public static volatile SingularAttribute<DeviceMapped, java.lang.String> hospDeptCode;
  public static volatile SingularAttribute<DeviceMapped, byte[]> imageFile;
  public static volatile SingularAttribute<DeviceMapped, java.time.LocalDate> installDate;
  public static volatile SingularAttribute<DeviceMapped, java.lang.String> lastServiceOffice;
  public static volatile SingularAttribute<DeviceMapped, java.lang.String> lastServicePsn;
  public static volatile SingularAttribute<DeviceMapped, java.lang.String> location;
  public static volatile SingularAttribute<DeviceMapped, byte[]> locationImage;
  public static volatile SingularAttribute<DeviceMapped, java.lang.String> mdevCode;
  public static volatile SingularAttribute<DeviceMapped, java.lang.Integer> noSerialConfirm;
  public static volatile SingularAttribute<DeviceMapped, java.lang.Double> price;
  public static volatile SingularAttribute<DeviceMapped, java.lang.Integer> pCcy;
  public static volatile SingularAttribute<DeviceMapped, java.lang.String> serialNo;
  public static volatile SingularAttribute<DeviceMapped, java.lang.String> softwareRev;
  public static volatile SingularAttribute<DeviceMapped, java.lang.String> supllierOffice;
  public static volatile SingularAttribute<DeviceMapped, java.lang.String> supplierPsn;
  public static volatile SingularAttribute<DeviceMapped, java.lang.String> targetDevCode;
  public static volatile SingularAttribute<DeviceMapped, arrow.mems.persistence.entity.MDevice> mDevice;
}