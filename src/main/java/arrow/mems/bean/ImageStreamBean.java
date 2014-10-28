package arrow.mems.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import arrow.framework.bean.AbstractBean;
import arrow.mems.constant.CommonConstants;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.Device;
import arrow.mems.persistence.entity.MDevice;
import arrow.mems.persistence.entity.PartEstimate;
import arrow.mems.persistence.entity.PartOrder;
import arrow.mems.persistence.entity.ReplPart;
import arrow.mems.service.DeviceManagementService;
import arrow.mems.service.DevicesManagementService;
import arrow.mems.service.InputRepairRequestService;
import arrow.mems.service.PartEstimateService;
import arrow.mems.service.PartsOrderService;
import arrow.mems.service.ReplacedPartService;


@Named
@SessionScoped
public class ImageStreamBean extends AbstractBean {
  @PostConstruct
  public void init() {
    this.mapDeviceAndImage = new HashMap<String, byte[]>();
    this.mapDeviceAndLocationImg = new HashMap<String, byte[]>();
    this.mapRepairRequestAndImage = new HashMap<String, byte[]>();
  }

  @Inject
  private DevicesManagementService masterDeviceService;

  @Inject
  private DeviceManagementService deviceService;

  @Inject
  private PartEstimateService estimateService;
  @Inject
  private PartsOrderService orderService;
  @Inject
  private ReplacedPartService replacedPartService;
  @Inject
  private InputRepairRequestService repairService;


  private byte[] contentFile;
  private boolean isEditting;

  public StreamedContent getImage() throws IOException {
    final FacesContext context = FacesContext.getCurrentInstance();
    if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE)
      return new DefaultStreamedContent();
    else {
      // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
      final String mdevCode = context.getExternalContext().getRequestParameterMap().get("mdeviceCode");
      final MDevice device = this.masterDeviceService.getDeviceFromMdevCode(mdevCode, CommonConstants.ACTIVE);
      if (this.isEditting) {
        this.isEditting = false;
        if (this.contentFile != null)
          return new DefaultStreamedContent(new ByteArrayInputStream(this.contentFile));
      }
      if (device != null)
        if (device.getPicture() != null)
          return new DefaultStreamedContent(new ByteArrayInputStream(device.getPicture()));
      return new DefaultStreamedContent();
    }
  }

  public byte[] getContentFile() {
    return this.contentFile;
  }

  public void setContentFile(byte[] pContentFile) {
    this.contentFile = pContentFile;
  }

  public boolean isEditting() {
    return this.isEditting;
  }

  public void setEditting(boolean pIsEditting) {
    this.isEditting = pIsEditting;
  }

  public void reset() {
    this.isEditting = false;
    this.contentFile = null;
    this.mapDeviceAndImage.clear();
    this.mapDeviceAndLocationImg.clear();
    this.mapRepairRequestAndImage.clear();
  }


  private Map<String, byte[]> mapDeviceAndImage;
  private Map<String, byte[]> mapDeviceAndLocationImg;

  private Map<String, byte[]> mapRepairRequestAndImage;

  public Map<String, byte[]> getMapDeviceAndImage() {
    return this.mapDeviceAndImage;
  }

  public void setMapDeviceAndImage(Map<String, byte[]> pMapDeviceAndImage) {
    this.mapDeviceAndImage = pMapDeviceAndImage;
  }

  public Map<String, byte[]> getMapDeviceAndLocationImg() {
    return this.mapDeviceAndLocationImg;
  }

  public void setMapDeviceAndLocationImg(Map<String, byte[]> pMapDeviceAndLocationImg) {
    this.mapDeviceAndLocationImg = pMapDeviceAndLocationImg;
  }

  public Map<String, byte[]> getMapRepairRequestAndImage() {
    return this.mapRepairRequestAndImage;
  }

  public void setMapRepairRequestAndImage(Map<String, byte[]> pMapRepairRequestAndImage) {
    this.mapRepairRequestAndImage = pMapRepairRequestAndImage;
  }

  public void uploadDeviceImageTemporary(String deviceCode, byte[] fileContent) {
    this.mapDeviceAndImage.put(deviceCode, fileContent);
  }

  public void uploadEstimateImageTemporary(String peCode, byte[] fileContent) {
    this.mapRepairRequestAndImage.put(peCode, fileContent);
  }

  public void removeDeviceImage(String deviceCode) {
    this.mapDeviceAndImage.remove(deviceCode);
  }

  public void removeEstimateImage(String peCode) {
    this.mapRepairRequestAndImage.remove(peCode);
  }

  public byte[] getDeviceImage(String pCurrentDeviceKey) {
    return this.mapDeviceAndImage.get(pCurrentDeviceKey);
  }


  public StreamedContent getDeviceImageAsStream() {
    final FacesContext context = FacesContext.getCurrentInstance();
    if (context.getCurrentPhaseId() != PhaseId.RENDER_RESPONSE) {
      final String deviceCode = context.getExternalContext().getRequestParameterMap().get("key");
      if (!this.mapDeviceAndImage.containsKey(deviceCode)) {
        final Device device = this.deviceService.loadDeviceByDeviceCode(deviceCode);
        if (device != null) {
          this.mapDeviceAndImage.put(deviceCode, device.getImageFile());
        }
      }
      if (this.mapDeviceAndImage.containsKey(deviceCode)) {
        final byte[] image = this.mapDeviceAndImage.get(deviceCode);
        if (image != null)
          return new DefaultStreamedContent(new ByteArrayInputStream(this.mapDeviceAndImage.get(deviceCode)));
      }
    }
    return new DefaultStreamedContent();
  }

  public byte[] getDeviceLocationImage(String pCurrentDeviceKey) {
    return this.mapDeviceAndLocationImg.get(pCurrentDeviceKey);
  }

  public void uploadDeviceLocationImageTemporary(String pCurrentDeviceKey, byte[] pContent) {
    this.mapDeviceAndLocationImg.put(pCurrentDeviceKey, pContent);
  }


  public StreamedContent getEstimateImageAsStream() {
    final FacesContext context = FacesContext.getCurrentInstance();
    if (context.getCurrentPhaseId() != PhaseId.RENDER_RESPONSE) {
      final String peCode = context.getExternalContext().getRequestParameterMap().get("peCode");
      if (!this.mapRepairRequestAndImage.containsKey(peCode)) {
        final PartEstimate estimate = this.estimateService.loadPartEstimateByPeCode(peCode);
        if (estimate != null) {
          this.mapRepairRequestAndImage.put(peCode, estimate.getImageFile());
        }
      }
      if (this.mapRepairRequestAndImage.containsKey(peCode)) {
        final byte[] image = this.mapRepairRequestAndImage.get(peCode);
        if (image != null)
          return new DefaultStreamedContent(new ByteArrayInputStream(this.mapRepairRequestAndImage.get(peCode)));
      }
    }
    return new DefaultStreamedContent();
  }

  public StreamedContent getOrderImageAsStream() {
    final FacesContext context = FacesContext.getCurrentInstance();
    if (context.getCurrentPhaseId() != PhaseId.RENDER_RESPONSE) {
      final String poCode = context.getExternalContext().getRequestParameterMap().get("poCode");
      if (!this.mapRepairRequestAndImage.containsKey(poCode)) {
        final PartOrder order = this.orderService.loadPartOrderByPoCode(poCode);
        if (order != null) {
          this.mapRepairRequestAndImage.put(poCode, order.getImageFile());
        }
      }
      if (this.mapRepairRequestAndImage.containsKey(poCode)) {
        final byte[] image = this.mapRepairRequestAndImage.get(poCode);
        if (image != null)
          return new DefaultStreamedContent(new ByteArrayInputStream(this.mapRepairRequestAndImage.get(poCode)));
      }
    }
    return new DefaultStreamedContent();
  }

  public StreamedContent getReplacePartImageAsStream() {
    final FacesContext context = FacesContext.getCurrentInstance();
    if (context.getCurrentPhaseId() != PhaseId.RENDER_RESPONSE) {
      final String key = context.getExternalContext().getRequestParameterMap().get("key");
      if (!this.mapRepairRequestAndImage.containsKey(key)) {
        final String[] keys = key.split(",");
        final ReplPart replPart = this.replacedPartService.getActiveReplPartByActionCodeDevCodeRemoveDevCode(keys[0], keys[1], keys[2]);
        if (replPart != null) {
          this.mapRepairRequestAndImage.put(key, replPart.getImageFile());
        }
      }
      if (this.mapRepairRequestAndImage.containsKey(key)) {
        final byte[] image = this.mapRepairRequestAndImage.get(key);
        if (image != null)
          return new DefaultStreamedContent(new ByteArrayInputStream(this.mapRepairRequestAndImage.get(key)));
      }
    }
    return new DefaultStreamedContent();
  }

  public StreamedContent getDeviceLocationImageAsStream() {
    final FacesContext context = FacesContext.getCurrentInstance();
    if (context.getCurrentPhaseId() != PhaseId.RENDER_RESPONSE) {
      final String deviceCode = context.getExternalContext().getRequestParameterMap().get("key");
      if (!this.mapDeviceAndLocationImg.containsKey(deviceCode)) {
        final Device device = this.deviceService.loadDeviceByDeviceCode(deviceCode);
        if (device != null) {
          this.mapDeviceAndLocationImg.put(deviceCode, device.getLocationImage());
        }
      }
      if (this.mapDeviceAndLocationImg.containsKey(deviceCode)) {
        final byte[] image = this.mapDeviceAndLocationImg.get(deviceCode);
        if (image != null)
          return new DefaultStreamedContent(new ByteArrayInputStream(this.mapDeviceAndLocationImg.get(deviceCode)));
      }
    }
    return new DefaultStreamedContent();
  }

  public StreamedContent getActionLogImageAsStream() {
    final FacesContext context = FacesContext.getCurrentInstance();
    if (context.getCurrentPhaseId() != PhaseId.RENDER_RESPONSE) {
      final String actionCode = context.getExternalContext().getRequestParameterMap().get("actionCode");
      if (!this.mapRepairRequestAndImage.containsKey(actionCode)) {
        final ActionLog action = this.repairService.loadActionLogByActionCode(actionCode);
        if (action != null) {
          this.mapRepairRequestAndImage.put(actionCode, action.getInstallConfirmImg());
        }
      }
      if (this.mapRepairRequestAndImage.containsKey(actionCode)) {
        final byte[] image = this.mapRepairRequestAndImage.get(actionCode);
        if (image != null)
          return new DefaultStreamedContent(new ByteArrayInputStream(this.mapRepairRequestAndImage.get(actionCode)));
      }
    }
    return new DefaultStreamedContent();
  }

  public byte[] getRequestImage(String key) {
    return this.mapRepairRequestAndImage.get(key);
  }

}
