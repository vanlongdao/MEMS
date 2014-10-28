package arrow.mems.util.dialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.context.RequestContext;

public class DialogUtil {

  public static void OpenDialog(final String outcome, Map<String, Object> options, Map<String, List<String>> params) {
    RequestContext.getCurrentInstance().openDialog(outcome, options, params);
  }

  public static void OpenDialog(final String outcome) {

    // Default options
    final Map<String, Object> options = new HashMap<String, Object>();;
    options.put("modal", true);
    options.put("resizable", false);
    options.put("height", 250);
    options.put("width", 400);
    options.put("contentHeight", 240);
    options.put("contentWidth", 380);

    RequestContext.getCurrentInstance().openDialog(outcome, options, null);
  }

  public static void CloseDialog(Object data) {
    RequestContext.getCurrentInstance().closeDialog(data);
  }

  public static void CloseDialog() {
    RequestContext.getCurrentInstance().closeDialog(null);
  }
}
