package arrow.framework.faces.util;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.sun.faces.application.MethodBindingMethodExpressionAdapter;

@Named
@ApplicationScoped
public class FacesELUtils {
  // MUST Always state reason for @SuppressWarnings("all")
  // Reason: Suppress warning about invalid expression
  private static final String EL_START = "#{";
  private static final String EL_END = "}";
  private static final Class<?>[] VALIDATOR_PARAM_TYPES = new Class[] {FacesContext.class, UIComponent.class, Object.class};

  // value expression
  public static ValueExpression createValueExpression(final String valueExpression) {
    return FacesELUtils.createValueExpression(valueExpression, Object.class);
  }

  public static ValueExpression createValueExpression(final String expression, final Class<?> valueType) {
    final FacesContext facesContext = FacesContext.getCurrentInstance();
    return facesContext.getApplication().getExpressionFactory()
        .createValueExpression(facesContext.getELContext(), FacesELUtils.buildEL(expression), valueType);
  }

  public static ValueExpression createValueExpressionFromValue(final Object value, final Class<?> valueType) {
    final FacesContext facesContext = FacesContext.getCurrentInstance();
    return facesContext.getApplication().getExpressionFactory().createValueExpression(value, valueType);
  }


  // method expressions
  public static MethodExpression createMethodExpression(final String actionExpression) {
    return FacesELUtils.createMethodExpression(actionExpression, null, new Class[0]);
  }

  public static MethodExpression createMethodExpression(final String actionExpression, final Class<?> returnType) {
    return FacesELUtils.createMethodExpression(actionExpression, returnType, new Class[0]);
  }

  public static MethodExpression createMethodExpression(final String actionExpression, final Class<?>[] expectedParamTypes) {
    return FacesELUtils.createMethodExpression(actionExpression, null, expectedParamTypes);
  }

  public static MethodExpression createMethodExpression(final String expression, final Class<?> returnType, final Class<?>[] expectedParamTypes) {
    final FacesContext facesContext = FacesContext.getCurrentInstance();
    return facesContext.getApplication().getExpressionFactory()
        .createMethodExpression(facesContext.getELContext(), FacesELUtils.buildEL(expression), returnType, expectedParamTypes);
  }

  public static MethodExpression createValidatorMethodExpression(final String strValidatorExpression) {
    return FacesELUtils.createMethodExpression(strValidatorExpression, Void.class, FacesELUtils.VALIDATOR_PARAM_TYPES);
  }

  public static MethodBindingMethodExpressionAdapter createValidatorMethodBinding(final String strValidatorExpression) {
    return new MethodBindingMethodExpressionAdapter(FacesELUtils.createValidatorMethodExpression(strValidatorExpression));
  }

  // invocation and evaluation
  public static Object invokeMethodExpression(final String expression) {
    final FacesContext facesContext = FacesContext.getCurrentInstance();
    final ELContext elContext = facesContext.getELContext();
    final MethodExpression methodExpression = FacesELUtils.createMethodExpression(expression, Object.class, new Class[0]);
    return methodExpression.invoke(elContext, new Object[0]);
  }

  public static Object invokeMethodExpression(final MethodExpression methodExpression, final Object... params) {
    final FacesContext facesContext = FacesContext.getCurrentInstance();
    final ELContext elContext = facesContext.getELContext();
    return methodExpression.invoke(elContext, params);
  }

  public static Object invokeMethodExpression(final MethodExpression methodExpression) {
    final FacesContext facesContext = FacesContext.getCurrentInstance();
    final ELContext elContext = facesContext.getELContext();
    return methodExpression.invoke(elContext, new Object[0]);
  }

  public static Object evaluateValueExpression(final String expression) {
    final FacesContext facesContext = FacesContext.getCurrentInstance();
    final Application application = facesContext.getApplication();
    final ELContext elContext = facesContext.getELContext();
    final ExpressionFactory expressionFactory = application.getExpressionFactory();
    final ValueExpression valueExpression = expressionFactory.createValueExpression(elContext, expression, Object.class);
    return valueExpression.getValue(elContext);
  }

  public static Object evaluateValueExpression(final ValueExpression valueExpression) {
    final FacesContext facesContext = FacesContext.getCurrentInstance();
    final ELContext elContext = facesContext.getELContext();
    return valueExpression.getValue(elContext);
  }

  /**
   * If the expressions is #{backingBean.currentEntity.attribute}, then the bean is the result of
   * #{backingBean.currentEntity}.
   *
   * @param expression
   * @return
   */
  public static Object evaluateBean(final String expression) {
    if ((expression == null) || !expression.startsWith(FacesELUtils.EL_START))
      return null;

    final String[] split = expression.substring(2, expression.length() - 1).split("\\.");
    if (split.length == 1)
      return null;

    final StringBuffer beanExpression = new StringBuffer();

    for (int i = 0; i < (split.length - 1); ++i) {
      if (beanExpression.length() > 0) {
        beanExpression.append(".");
      }

      beanExpression.append(split[i]);
    }
    return FacesELUtils.evaluateValueExpression(FacesELUtils.EL_START + beanExpression.toString() + FacesELUtils.EL_END);
  }

  /**
   * If the expressions is #{backingBean.currentEntity.myAttribute}, then the attribute is
   * myAttribute.
   *
   * @param expression
   * @return
   */
  public static String getAttribute(final String expression) {
    if ((expression == null) || !expression.startsWith(FacesELUtils.EL_START))
      return null;

    final String[] split = expression.substring(2, expression.length() - 1).split("\\.");
    return split[split.length - 1];
  }


  /**
   * Use this method to avoid those EL syntax warnings
   */
  public static String buildEL(final String expressionWithoutBraces) {
    if (!expressionWithoutBraces.startsWith(FacesELUtils.EL_START))
      return FacesELUtils.EL_START + expressionWithoutBraces + FacesELUtils.EL_END;
    else
      return expressionWithoutBraces;
  }
}
