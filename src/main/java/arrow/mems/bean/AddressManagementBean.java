/**
 *
 */
package arrow.mems.bean;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import arrow.framework.helper.ServiceResult;
import arrow.mems.bean.base.AbstractApprovalBean;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.MemsDataType;
import arrow.mems.messages.MMIXMessages;
import arrow.mems.persistence.dto.AddressDto;
import arrow.mems.persistence.entity.Address;
import arrow.mems.persistence.entity.ApprovalSummary;
import arrow.mems.service.AddressService;
import arrow.mems.service.ApprovalService;
import arrow.mems.service.CountryService;

/**
 * @author tainguyen
 *
 */
@Named
@ViewScoped
public class AddressManagementBean extends AbstractApprovalBean {
  @Inject
  AddressService addressService;

  @Inject
  UserCredential userCredential;

  @Inject
  CountryService countryService;

  private AddressDto currentAddress;
  private Address selectedAddress;
  private List<Address> listAddresses;
  private String comment;

  public void toggleSelection(Address address) {
    if (address.isSelected()) {
      if (this.selectedAddress != null) {
        this.selectedAddress.setSelected(false);
      }
      this.selectedAddress = address;
    } else {
      this.selectedAddress = null;
    }
  }

  public void addAddress() {
    this.currentAddress = new AddressDto();
  }

  // Delete selected address
  public void deleteAddress() {
    final ServiceResult<Address> result = this.addressService.deleteAddress(this.currentAddress);
    result.showMessages();
    if (result.isSuccess()) {
      this.cleanStage();
    }
  }

  public void saveAddress() {
    final ServiceResult<Address> result = this.addressService.saveAddress(this.currentAddress);
    result.showMessages();
    if (result.isSuccess()) {
      this.cleanStage();
    }
  }

  public void editAddress() {
    final ServiceResult<AddressDto> result = this.addressService.editAddress(this.selectedAddress);
    if (result.isSuccess()) {
      this.currentAddress = result.getData();
    }
  }


  public void closeAddress(ActionEvent ae) {
    // reset data
    this.currentAddress = null;

    // reset stage
    this.resetStage(ae);
  }

  public void cleanStage() {
    this.currentAddress = null;
    this.listAddresses = null;
    if (this.selectedAddress != null) {
      this.selectedAddress.setSelected(false);
      this.selectedAddress = null;
    }
  }

  @Inject
  private ApprovalService approvalService;

  public void requestApprove() {
    final ServiceResult<ApprovalSummary> result =
        this.approvalService.requestApproval(MemsDataType.ADDRESS, this.currentAddress.getAddrCode(), this.userCredential.getUserId(),
            this.currentAddress.getAddress1());
    if (result.isSuccess()) {
      MMIXMessages.MMIX00004().show();
    } else {
      MMIXMessages.MMIX00005().show();
    }
  }

  public boolean isEnableEdit() {
    return this.selectedAddress != null;
  }

  public Address getSelectedAddress() {
    return this.selectedAddress;
  }

  public void setSelectedAddress(Address pSelectedAddress) {
    this.selectedAddress = pSelectedAddress;
  }

  public AddressDto getCurrentAddress() {
    return this.currentAddress;
  }

  public void setCurrentAddress(AddressDto pCurrentAddress) {
    this.currentAddress = pCurrentAddress;
  }

  public List<Address> getListAddresses() {
    if (this.userCredential.isLoggedIn() && (this.listAddresses == null)) {
      this.listAddresses = this.addressService.getListAddresses(this.userCredential.getUserInfo().getOfficeCode());
    }
    return this.listAddresses;
  }

  public void setListAddresses(List<Address> listAddresses) {
    this.listAddresses = listAddresses;
  }

  public String getComment() {
    return this.comment;
  }

  public void setComment(String pComment) {
    this.comment = pComment;
  }

  @Override
  public Object getEntityId() {
    return this.currentAddress.getAddrCode();
  }

  @Override
  public String getDataType() {
    return MemsDataType.ADDRESS;
  }


  @Override
  public String getItemLabel() {
    return this.currentAddress.getAddress1();
  }

  // @Override
  // protected boolean canProcess(AbstractApprovalEntity pTargetEntity) {
  // // TODO Must Implement
  // return true;
  // }

}
