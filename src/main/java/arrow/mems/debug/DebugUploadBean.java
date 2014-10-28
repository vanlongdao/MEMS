package arrow.mems.debug;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import arrow.framework.bean.AbstractBean;

@ViewScoped
@Named
public class DebugUploadBean extends AbstractBean {

  @PostConstruct
  public void init() {
    this.log.debug("Create Bean");
  }

  private UploadedFile file;

  private InputStream fileContent;

  @Inject
  DebugBean debugBean;

  public void uploadFile() throws IOException {
    if (this.file != null) {
      this.debugBean.setMessage("uploaded");
      this.fileContent = this.file.getInputstream();

    } else {
      this.debugBean.setMessage("failed");
    }
  }

  public void resetMessage() {
    this.debugBean.setMessage("Begin");
  }

  public UploadedFile getFile() {
    return this.file;
  }

  public void setFile(UploadedFile pFile) {
    this.file = pFile;
  }

  public StreamedContent getFileToPreview() throws IOException {
    if (this.fileContent != null)
      return new DefaultStreamedContent(this.fileContent, this.file.getContentType());
    return null;
  }

}
