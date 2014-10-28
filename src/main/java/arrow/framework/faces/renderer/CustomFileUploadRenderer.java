package arrow.framework.faces.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.primefaces.component.fileupload.FileUploadRenderer;

public class CustomFileUploadRenderer extends FileUploadRenderer {
  @Override
  public void decode(final FacesContext context, final UIComponent component) {
    if (context.getExternalContext().getRequestContentType().toLowerCase().startsWith("multipart/")) {
      super.decode(context, component);
    }
  }
}
