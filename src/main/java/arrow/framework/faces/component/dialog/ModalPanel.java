package arrow.framework.faces.component.dialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.el.ELException;
import javax.el.MethodExpression;

import arrow.framework.faces.util.FacesELUtils;


public abstract class ModalPanel implements Serializable {
  public static final int ORIGINAL_MAX_HEIGHT = 600;
  public static final int ORIGINAL_MAX_WIDTH = 800;
  public static final int ORIGINAL_MIN_WIDTH = 400;
  public static final int ORIGINAL_TOP = 5;

  private String title;

  public String getTitle() {
    return this.title;
  }

  public void setTitle(final String title) {
    this.cleanup();
    this.title = title;
  }

  protected void cleanup() {
    this.actionTargets.clear();
    this.additionalActions.clear();
    this.selectedBeans.clear();
    this.includeParamNames.clear();
    this.includeParams.clear();
    this.messages.clear();

    this.allowBatchAction = false;
    this.allowCommandLink = true;
    this.panelButtonLabel = null;
    this.panelButtonAction = null;
    this.includeSrc = null;
    this.dataIncluded = null;
    this.defaultDismissButton = true;
    this.closePopupControl = true;
    this.maxHeight = ModalPanel.ORIGINAL_MAX_HEIGHT;
    this.maxWidth = ModalPanel.ORIGINAL_MAX_WIDTH;
    this.minWidth = ModalPanel.ORIGINAL_MIN_WIDTH;
    this.top = ModalPanel.ORIGINAL_TOP;
  }

  private Object dataIncluded;

  public Object getDataIncluded() {
    return this.dataIncluded;
  }

  public void setDataIncluded(final Object dataIncluded) {
    this.dataIncluded = dataIncluded;
  }

  private boolean limitToList;

  public boolean isLimitToList() {
    return this.limitToList;
  }

  public void setLimitToList(final boolean limitToList) {
    this.limitToList = limitToList;
  }

  private boolean rendered;

  public boolean isRendered() {
    return this.rendered;
  }

  public void show() {
    this.rendered = true;
  }

  public void hide() {
    this.rendered = false;
  }

  private String includeSrc;

  public String getIncludeSrc() {
    return this.includeSrc;
  }

  public void setIncludeSrc(final String src) {
    this.cleanup();
    this.includeSrc = src;
  }

  private final List<String> messages = new ArrayList<String>();

  public List<String> getMessages() {
    return this.messages;
  }

  public void setMessages(final String message) {
    this.messages.add(message);
  }

  private final List<String> includeParamNames = new ArrayList<String>();

  public List<String> getIncludeParamNames() {
    return this.includeParamNames;
  }

  public String getIncludeParamName() {
    return null;
  }

  public void setIncludeParamName(final String name) {
    this.includeParamNames.add(name);
  }

  private final Map<String, Object> includeParams = Collections.synchronizedMap(new HashMap<String, Object>());

  public Map<String, Object> getIncludeParams() {
    return this.includeParams;
  }

  public Object getIncludeParam() {
    return null;
  }

  public void setIncludeParam(final Object param) {
    this.includeParams.put(this.includeParamNames.get(this.includeParamNames.size() - 1), param);
  }

  private final ArrayList<Object> actionTargets = new ArrayList<Object>();

  public ArrayList<Object> getActionTargets() {
    return this.actionTargets;
  }

  public void setActionTarget(final Object target) {
    this.actionTargets.add(target);
  }

  public Object getActionTarget() {
    return null;
  }

  private final ArrayList<Object> additionalActions = new ArrayList<Object>();

  public void setAdditionalAction(final Object action) {
    if (action == null)
      return;
    this.additionalActions.add(action);
  }

  private boolean defaultDismissButton = true;

  public boolean getDefaultDismissButton() {
    return this.defaultDismissButton;
  }

  public void setDefaultDismissButton(final boolean b) {
    this.defaultDismissButton = b;
  }

  private boolean closePopupControl = true;

  public boolean isClosePopupControl() {
    return this.closePopupControl;
  }

  public void setClosePopupControl(final boolean closePopupControl) {
    this.closePopupControl = closePopupControl;
  }

  private String reRender;

  public String getReRender() {
    return this.reRender;
  }

  public void setReRender(final String reRender) {
    this.reRender = reRender;
  }

  private String panelButtonLabel;

  public String getPanelButtonLabel() {
    return this.panelButtonLabel;
  }

  public void setPanelButtonLabel(final String panelButtonLabel) {
    this.panelButtonLabel = panelButtonLabel;
  }

  private String panelButtonAction;

  public String getPanelButtonAction() {
    return this.panelButtonAction;
  }

  public void setPanelButtonAction(final String panelButtonAction) {
    this.panelButtonAction = panelButtonAction;
  }

  public void executeAction() {
    try {
      if (this.panelButtonAction != null) {
        FacesELUtils.invokeMethodExpression(this.panelButtonAction);
      }
      this.invokeAdditionalActions();
    } catch (final ELException e) {
      ModalPanel.handleELException(e);
    } finally {
      this.hide();
    }
  }

  public void onClose() {
    this.hide();
  }

  /**************************** Batch action ****************************/
  private final Set<Serializable> selectedBeans = new HashSet<Serializable>();

  public Set<Serializable> getSelectedBeans() {
    return this.selectedBeans;
  }

  public void clearSelectedBeans() {
    if (this.allowBatchAction) {
      this.selectedBeans.clear();
    }
  }

  private final Map<Serializable, Boolean> selectionMap = Collections.synchronizedMap(new HashMap<Serializable, Boolean>() {
    /**
     *
     */

    @Override
    public Boolean get(final Object obj) {
      return ModalPanel.this.getSelectedBeans().contains(obj);
    }

    @Override
    public Boolean put(final Serializable obj, final Boolean b) {
      if ((b != null) && b) {
        ModalPanel.this.selectedBeans.add(obj);
      } else {
        ModalPanel.this.selectedBeans.remove(obj);
      }

      return null;
    }
  });

  public Map<Serializable, Boolean> getSelectionMap() {
    return this.selectionMap;
  }

  public void executeBatchAction() {
    try {
      if (this.batchAction != null) {
        FacesELUtils.invokeMethodExpression(this.batchAction);
      }
    } catch (final ELException e) {
      ModalPanel.handleELException(e);
    }

    finally {
      this.clearSelectedBeans();
    }

    this.rendered = false;
  }

  private boolean allowBatchAction;

  public boolean getAllowBatchAction() {
    return this.allowBatchAction;
  }

  public void setAllowBatchAction(final boolean allowBatchAction) {
    this.allowBatchAction = allowBatchAction;
  }

  private boolean allowCommandLink = true;

  public boolean isAllowCommandLink() {
    return this.allowCommandLink;
  }

  public void setAllowCommandLink(final boolean allowCommandLink) {
    this.allowCommandLink = allowCommandLink;
  }

  private String batchAction;

  public String getBatchAction() {
    return this.batchAction;
  }

  public void setBatchAction(final String batchAction) {
    this.batchAction = batchAction;
  }

  private int maxHeight = ModalPanel.ORIGINAL_MAX_HEIGHT;

  public int getMaxHeight() {
    return this.maxHeight;
  }

  public void setMaxHeight(final int maxHeight) {
    this.maxHeight = maxHeight;
  }

  private int maxWidth = ModalPanel.ORIGINAL_MAX_WIDTH;

  public int getMaxWidth() {
    return this.maxWidth;
  }

  public void setMaxWidth(final int maxWidth) {
    this.maxWidth = maxWidth;
  }

  private int minWidth = ModalPanel.ORIGINAL_MIN_WIDTH;

  public int getMinWidth() {
    return this.minWidth;
  }

  public void setMinWidth(final int minWidth) {
    this.minWidth = minWidth;
  }

  public int getIntValue(final int value) {
    return value;
  }

  private int top = ModalPanel.ORIGINAL_TOP;

  public int getTop() {
    return this.top;
  }

  public void setTop(final int top) {
    this.top = top;
  }

  // ///////////////////////////////////////////////////////////////////////////////////

  public static void handleELException(final ELException e) {
    // final Throwable cause = e.getCause();
    // if (cause != null) {
    // if (cause instanceof SynException) {
    // ErrorMessages.instance().add((SynException) cause);
    // cause.printStackTrace();
    // }
    // else {
    // throw new RuntimeException(e.getCause());
    // }
    // }
    // else {
    // throw new RuntimeException(e);
    // }
  }

  protected void invokeAdditionalActions() {
    for (final Object action : this.additionalActions) {
      if (action instanceof String) {
        FacesELUtils.invokeMethodExpression(action.toString());
      } else if (action instanceof MethodExpression) {
        FacesELUtils.invokeMethodExpression((MethodExpression) action);
      }
    }
  }
}
