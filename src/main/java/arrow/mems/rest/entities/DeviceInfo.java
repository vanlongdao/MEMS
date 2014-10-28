package arrow.mems.rest.entities;

import java.util.List;

import arrow.mems.persistence.entity.HumanResource;

public class DeviceInfo {
  private int devId;

  public int getDevId() {
    return this.devId;
  }

  public void setDevId(int pDevId) {
    this.devId = pDevId;
  }

  private String name;
  private String model;
  private String serial;
  private String manufacturer;
  private String supplier;
  private String location;
  private List<HumanResource> listHumanResource;

  public String getName() {
    return this.name;
  }

  public void setName(String pName) {
    this.name = pName;
  }

  public String getModel() {
    return this.model;
  }

  public void setModel(String pModel) {
    this.model = pModel;
  }

  public String getSerial() {
    return this.serial;
  }

  public void setSerial(String pSerial) {
    this.serial = pSerial;
  }

  public String getManufacturer() {
    return this.manufacturer;
  }

  public void setManufacturer(String pManufacturer) {
    this.manufacturer = pManufacturer;
  }

  public String getSupplier() {
    return this.supplier;
  }

  public void setSupplier(String pSupplier) {
    this.supplier = pSupplier;
  }

  public String getLocation() {
    return this.location;
  }

  public void setLocation(String pLocation) {
    this.location = pLocation;
  }

  public List<HumanResource> getListHumanResource() {
    return this.listHumanResource;
  }

  public void setListHumanResource(List<HumanResource> pListHumanResource) {
    this.listHumanResource = pListHumanResource;
  }

}
