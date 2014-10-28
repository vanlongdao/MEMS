package arrow.framework.faces.util;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIForm;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;

import com.sun.faces.component.visit.FullVisitContext;

public class UIComponentUtils {

  public static UIForm getContainingForm(final UIComponent component) {
    final UIComponent parentComponent = component.getParent();

    if (parentComponent == null)
      return null;

    if (parentComponent instanceof UIForm)
      return (UIForm) parentComponent;

    return UIComponentUtils.getContainingForm(parentComponent);
  }

  /**
   *
   * @param parentComponent
   * @param clientId must be the client id, NOT the JSF id. Otherwise we can't ensure the
   *        uniqueness.
   * @return
   */
  @SuppressWarnings("unchecked")
  public static <T extends UIComponent> T findComponentById(final Class<T> clazz, final String id) {
    final FacesContext facesContext = FacesContext.getCurrentInstance();
    if (facesContext == null)
      throw new IllegalStateException("FacesContext not available");

    final UIViewRoot viewRoot = facesContext.getViewRoot();
    if (viewRoot == null)
      throw new IllegalStateException("ViewRoot not available");

    try {
      // return (T) viewRoot.findComponent(id);

      // try to search in full context, to make sure that the component will be found :)
      // http://stackoverflow.com/questions/14378437/find-component-by-id-in-jsf
      return (T) UIComponentUtils.findComponentInFullContext(id);
    }

    catch (final ClassCastException e) {
      return null;
    }
  }

  public static UIComponent findComponentInFullContext(final String id) {

    final FacesContext context = FacesContext.getCurrentInstance();
    final UIViewRoot root = context.getViewRoot();
    final UIComponent[] found = new UIComponent[1];

    root.visitTree(new FullVisitContext(context), (context1, component) -> {
      if (component.getId().equals(id)) {
        found[0] = component;
        return VisitResult.COMPLETE;
      }
      return VisitResult.ACCEPT;
    });

    return found[0];

  }

  /**
   * Notice: if the specified <code>component</code> is an instance of {@link UIData}, that UIData
   * is not counted, because it's not a 'containing' one.
   *
   * @param component
   * @return
   */
  public static UIData getContainingUIData(final UIComponent component) {
    if (component.getParent() == null)
      return null;
    else if (component.getParent() instanceof UIData)
      return (UIData) component.getParent();
    else
      return UIComponentUtils.getContainingUIData(component.getParent());
  }

  /**
   * Reset submitting value for the JSF component that is EditableValueHolder
   *
   * @param component
   */
  public static void resetInputValueIfPossible(final UIComponent component) {
    if (component instanceof EditableValueHolder) {
      ((EditableValueHolder) component).resetValue();
    }
  }

  public static void resetComponentStatus(UIComponent component) {
    if (component instanceof EditableValueHolder) {
      final EditableValueHolder editableValueHolder = (EditableValueHolder) component;
      UIComponentUtils.resetInputValueIfPossible(component);
      editableValueHolder.setValid(true);
    }
    for (final UIComponent child : component.getChildren()) {
      UIComponentUtils.resetComponentStatus(child);
    }
  }
}
