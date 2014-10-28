package arrow.mems.debug;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class UploadSample implements Serializable {
  private byte[] fileContentInDb;

  private UploadedFile uploadedFile;

  private String filename;

  public String getFilename() {
    return this.filename;
  }

  public void setFilename(String pFilename) {
    this.filename = pFilename;
  }

  public byte[] getFileContentInDb() {
    return this.fileContentInDb;
  }

  public void setFileContentInDb(byte[] pFileContentInDb) {
    this.fileContentInDb = pFileContentInDb;
  }

  public UploadedFile getUploadedFile() {
    return this.uploadedFile;
  }

  public void setUploadedFile(UploadedFile pUploadedFile) {
    this.uploadedFile = pUploadedFile;
  }

  public void handleUploadEvent(FileUploadEvent e) throws IOException {
    this.uploadedFile = e.getFile();
    this.filename = e.getFile().getFileName();
    this.fileContentInDb = IOUtils.toByteArray(e.getFile().getInputstream());
  }

}
