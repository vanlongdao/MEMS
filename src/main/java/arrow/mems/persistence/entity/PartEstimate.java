//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

import arrow.mems.messages.MessageCode;
import arrow.mems.persistence.mapped.PartEstimateMapped;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
@Table(name="PART_ESTIMATE")
public class PartEstimate extends PartEstimateMapped {

  public PartEstimate() {
  }


  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "ACTION_CODE", referencedColumnName = "ACTION_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected ActionLog actionLog;

  public ActionLog getActionLog() {
    return this.actionLog;
  }

  public void setActionLog(ActionLog pActionLog) {
    this.actionLog = pActionLog;
    this.actionCode = this.actionLog != null ? this.actionLog.getActionCode() : null;
    if (this.actionLog != null) {
      this.isDeleted = this.actionLog.getIsDeleted();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "DIST_OFFICE", referencedColumnName = "OFFICE_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected Office distributorOffice;

  public Office getDistributorOffice() {
    return this.distributorOffice;
  }

  public void setDistributorOffice(Office pDistributorOffice) {
    this.distributorOffice = pDistributorOffice;
    this.distOffice = this.distributorOffice != null ? this.distributorOffice.getOfficeCode() : null;
    if (this.distributorOffice != null) {
      this.isDeleted = this.distributorOffice.getIsDeleted();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "DIST_PSN", referencedColumnName = "PSN_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected Person distributorPerson;

  public Person getDistributorPerson() {
    return this.distributorPerson;
  }

  public void setDistributorPerson(Person pDistributorPerson) {
    this.distributorPerson = pDistributorPerson;
    this.distPsn = this.distributorPerson != null ? this.distributorPerson.getPsnCode() : null;
    if (this.distributorPerson != null) {
      this.isDeleted = this.distributorPerson.getIsDeleted();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "SERVICE_OFFICE", referencedColumnName = "OFFICE_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected Office supplierOffice;

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "SERVICE_PSN", referencedColumnName = "PSN_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected Person supplierPerson;

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "REQUESTER_OFFICE", referencedColumnName = "OFFICE_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected Office clientOffice;

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "REQUESTER_PSN", referencedColumnName = "PSN_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected Person clientPerson;

  public Office getSupplierOffice() {
    return this.supplierOffice;
  }

  public void setSupplierOffice(Office pSupplierOffice) {
    this.supplierOffice = pSupplierOffice;
    this.serviceOffice = this.supplierOffice != null ? this.supplierOffice.getOfficeCode() : null;
    if (this.supplierOffice != null) {
      this.isDeleted = this.supplierOffice.getIsDeleted();
    }
  }

  public Person getSupplierPerson() {
    return this.supplierPerson;
  }

  public void setSupplierPerson(Person pSupplierPerson) {
    this.supplierPerson = pSupplierPerson;
    this.servicePsn = this.supplierPerson != null ? this.supplierPerson.getPsnCode() : null;
    if (this.supplierPerson != null) {
      this.isDeleted = this.supplierPerson.getIsDeleted();
    }
  }

  public Office getClientOffice() {
    return this.clientOffice;
  }

  public void setClientOffice(Office pClientOffice) {
    this.clientOffice = pClientOffice;
    this.requesterOffice = this.clientOffice != null ? this.clientOffice.getOfficeCode() : null;
    if (this.clientOffice != null) {
      this.isDeleted = this.clientOffice.getIsDeleted();
    }
  }

  public Person getClientPerson() {
    return this.clientPerson;
  }

  public void setClientPerson(Person pClientPerson) {
    this.clientPerson = pClientPerson;
    this.requesterPsn = this.clientPerson != null ? this.clientPerson.getPsnCode() : null;
    if (this.clientPerson != null) {
      this.isDeleted = this.clientPerson.getIsDeleted();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "REQ_DEST_OFFICE", referencedColumnName = "OFFICE_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected Office deliveryToOffice;

  public Office getDeliveryToOffice() {
    return this.deliveryToOffice;
  }

  public void setDeliveryToOffice(Office pDeliveryToOffice) {
    this.deliveryToOffice = pDeliveryToOffice;
    this.reqDestOffice = this.deliveryToOffice != null ? this.deliveryToOffice.getOfficeCode() : null;
    if (this.deliveryToOffice != null) {
      this.isDeleted = this.deliveryToOffice.getIsDeleted();
    }
  }

  @Transient
  private String fakeId;

  public String getFakeId() {
    if (StringUtils.isEmpty(this.fakeId)) {
      this.fakeId = UUID.randomUUID().toString();
    }
    return this.fakeId;
  }

  public void setFakeId(String pFakeId) {
    this.fakeId = pFakeId;
  }

  @Override
  @NotNull(message = "{" + MessageCode.MRR00024 + "}")
  public LocalDate getRequestDate() {
    return super.getRequestDate();
  }

  @Override
  @NotNull(message = "{" + MessageCode.MRR00033 + "}")
  public String getRequesterOffice() {
    return super.getRequesterOffice();
  }

  @Override
  @NotNull(message = "{" + MessageCode.MRR00034 + "}")
  public String getRequesterPsn() {
    return super.getRequesterPsn();
  }

  @Override
  @NotNull(message = "{" + MessageCode.MRR00035 + "}")
  public String getPeType() {
    return super.getPeType();
  }



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}