//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

import arrow.mems.messages.MessageCode;
import arrow.mems.persistence.mapped.PartOrderMapped;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
@Table(name="PART_ORDER")
public class PartOrder extends PartOrderMapped {

  public PartOrder() {
  }


  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "DIST_OFFICE", referencedColumnName = "OFFICE_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected arrow.mems.persistence.entity.Office distributorOffice;

  public arrow.mems.persistence.entity.Office getDistributorOffice() {
    return this.distributorOffice;
  }

  public void setDistributorOffice(final arrow.mems.persistence.entity.Office distributorOffice) {
    this.distributorOffice = distributorOffice;
    this.distOffice = this.distributorOffice != null ? this.distributorOffice.getOfficeCode() : null;
    if (this.distributorOffice != null) {
      this.isDeleted = this.distributorOffice.getIsDeleted();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "DIST_PSN", referencedColumnName = "PSN_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected arrow.mems.persistence.entity.Person distributorPerson;

  public arrow.mems.persistence.entity.Person getDistributorPerson() {
    return this.distributorPerson;
  }

  public void setDistributorPerson(final arrow.mems.persistence.entity.Person distributorPerson) {
    this.distributorPerson = distributorPerson;
    this.distPsn = this.distributorPerson != null ? this.distributorPerson.getPsnCode() : null;
    if (this.distributorPerson != null) {
      this.isDeleted = this.distributorPerson.getIsDeleted();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "SERVICE_OFFICE", referencedColumnName = "OFFICE_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected arrow.mems.persistence.entity.Office entityServiceOffice;

  public arrow.mems.persistence.entity.Office getEntityServiceOffice() {
    return this.entityServiceOffice;
  }

  public void setEntityServiceOffice(final arrow.mems.persistence.entity.Office entityServiceOffice) {
    this.entityServiceOffice = entityServiceOffice;
    this.serviceOffice = this.entityServiceOffice != null ? this.entityServiceOffice.getOfficeCode() : null;
    if (this.entityServiceOffice != null) {
      this.isDeleted = this.entityServiceOffice.getIsDeleted();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "SERVICE_PSN", referencedColumnName = "PSN_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected arrow.mems.persistence.entity.Person servicePerson;

  public arrow.mems.persistence.entity.Person getServicePerson() {
    return this.servicePerson;
  }

  public void setServicePerson(final arrow.mems.persistence.entity.Person servicePerson) {
    this.servicePerson = servicePerson;
    this.servicePsn = this.servicePerson != null ? this.servicePerson.getPsnCode() : null;
    if (this.servicePerson != null) {
      this.isDeleted = this.servicePerson.getIsDeleted();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "REQUESTER_OFFICE", referencedColumnName = "OFFICE_CODE", insertable = false, updatable = false),
      @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected arrow.mems.persistence.entity.Office entityRequestOffice;

  public arrow.mems.persistence.entity.Office getEntityRequestOffice() {
    return this.entityRequestOffice;
  }

  public void setEntityRequestOffice(final arrow.mems.persistence.entity.Office entityRequestOffice) {
    this.entityRequestOffice = entityRequestOffice;
    this.requesterOffice = this.entityRequestOffice != null ? this.entityRequestOffice.getOfficeCode() : null;
    if (this.entityRequestOffice != null) {
      this.isDeleted = this.entityRequestOffice.getIsDeleted();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "REQUESTER_PSN", referencedColumnName = "PSN_CODE", insertable = false, updatable = false),
      @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected arrow.mems.persistence.entity.Person requestPerson;

  public arrow.mems.persistence.entity.Person getRequestPerson() {
    return this.requestPerson;
  }

  public void setRequestPerson(final arrow.mems.persistence.entity.Person requestPerson) {
    this.requestPerson = requestPerson;
    this.requesterPsn = this.requestPerson != null ? this.requestPerson.getPsnCode() : null;
    if (this.requestPerson != null) {
      this.isDeleted = this.requestPerson.getIsDeleted();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "DEST_OFFICE", referencedColumnName = "OFFICE_CODE", insertable = false, updatable = false),
      @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected arrow.mems.persistence.entity.Office destinationOffice;

  public arrow.mems.persistence.entity.Office getDestinationOffice() {
    return this.destinationOffice;
  }

  public void setDestinationOffice(final arrow.mems.persistence.entity.Office destinationOffice) {
    this.destinationOffice = destinationOffice;
    this.destOffice = this.destinationOffice != null ? this.destinationOffice.getOfficeCode() : null;
    if (this.destinationOffice != null) {
      this.isDeleted = this.destinationOffice.getIsDeleted();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "ESTIMATE_CODE", referencedColumnName = "PE_CODE", insertable = false, updatable = false),
      @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected arrow.mems.persistence.entity.PartEstimate partEstimate;

  public arrow.mems.persistence.entity.PartEstimate getPartEstimate() {
    return this.partEstimate;
  }

  public void setPartEstimate(final arrow.mems.persistence.entity.PartEstimate partEstimate) {
    this.partEstimate = partEstimate;
    this.estimateCode = this.partEstimate != null ? this.partEstimate.getPeCode() : null;
    if (this.partEstimate != null) {
      this.isDeleted = this.partEstimate.getIsDeleted();
    }
  }



  @Transient
  private String fakeId;

  public void setFakeId(String pFakeId) {
    this.fakeId = pFakeId;
  }

  public String getFakeId() {
    if (StringUtils.isEmpty(this.fakeId)) {
      this.fakeId = UUID.randomUUID().toString();
    }
    return this.fakeId;
  }

  @Override
  @NotNull(message = "{" + MessageCode.MRR00013 + "}")
  public String getEstimateCode() {
    return super.getEstimateCode();
  }

  @Override
  @NotNull(message = "{" + MessageCode.MRR00026 + "}")
  public String getDistOffice() {
    return super.getDistOffice();
  }

  @Override
  @NotNull(message = "{" + MessageCode.MRR00025 + "}")
  public LocalDate getOrderDate() {
    return super.getOrderDate();
  }

  @OneToMany(mappedBy = "partOrder", cascade = CascadeType.ALL)
  protected List<PartOrderItem> partOrderItems = new ArrayList<PartOrderItem>();

  public List<PartOrderItem> getPartOrderItems() {
    return this.partOrderItems;
  }

  public void setPartOrderItems(List<PartOrderItem> pPartOrderItems) {
    this.partOrderItems = pPartOrderItems;
  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}
