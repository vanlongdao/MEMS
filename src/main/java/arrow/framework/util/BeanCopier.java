/*
 * package: arrow.framework.faces.util
 * file: BeanCopier.java
 * time: Jun 27, 2014
 *
 * @author michael
 */
package arrow.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Pattern;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import arrow.framework.exception.ArrowException;
import arrow.framework.logging.ArrowLogger;
import arrow.framework.logging.ArrowLoggerProducer;
import arrow.framework.persistence.ArrowPk;

public class BeanCopier {
  // log injection is not available
  private static final ArrowLogger LOGGER = ArrowLoggerProducer.getLogger();
  private static final Pattern SETTER_PATTERN = Pattern.compile("^set[A-Z]");

  private static String getDefaultGetterName(final Method setter) {
    return "get" + setter.getName().substring(3);
  }

  private static String getDefaultSetterName(final String field) {
    return "set" + WordUtils.capitalize(field);
  }

  private static boolean isMatchField(String setterName, String... ignoredFields) {
    for (final String field : ignoredFields) {
      if (StringUtils.equals(setterName, BeanCopier.getDefaultSetterName(field)))
        return true;
    }
    return false;
  }

  private static boolean isIgnoredMethod(String methodName) {
    final String[] ignoreMethods = new String[] {"setObjectVersion", "setCreatedBy", "setCreatedAt", "setCheckedBy", "setCheckedAt"};
    return (ArrayUtils.contains(ignoreMethods, methodName));
  }

  private static boolean isDefaultCopy(final Method method, final Class<?> srcClass) {
    final String name = method.getName();

    if (BeanCopier.isIgnoredMethod(name))
      return false;

    return BeanCopier.SETTER_PATTERN.matcher(method.getName()).find() && (method.getReturnType() == void.class) && (method.getParameterTypes().length == 1);
  }

  public static boolean flatCopy(final Object srcBean, final Object desBean) {
    final Class<?> srcClass = srcBean.getClass();
    final Class<?> desClass = desBean.getClass();
    final Method[] srcMethods = srcClass.getMethods();
    final Method[] desMethods = desClass.getMethods();

    for (final Method srcMethod : srcMethods) {
      final String srcGetter = srcMethod.getName();

      if ((srcGetter.startsWith("get") == false) || (srcMethod.getParameterTypes().length > 0) || srcGetter.equals("getObject_version")) {
        continue;
      }
      final Class<?> retType = srcMethod.getReturnType();
      if (retType.isPrimitive() || Number.class.isAssignableFrom(retType) || Date.class.isAssignableFrom(retType) || LocalDate.class
          .isAssignableFrom(retType) || LocalDateTime.class.isAssignableFrom(retType) || LocalTime.class.isAssignableFrom(retType) || Boolean.class
          .equals(retType) || String.class.equals(retType)) {
        final String desSetter = "set" + srcGetter.substring(3);

        for (final Method desMethod : desMethods) {
          if (desMethod.getName().equals(desSetter) && desMethod.getReturnType().equals(void.class) && (desMethod.getParameterTypes().length == 1)) {
            final Class<?> paramType = desMethod.getParameterTypes()[0];
            if (paramType.isAssignableFrom(retType)) {
              try {
                desMethod.invoke(desBean, srcMethod.invoke(srcBean));
              } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
                BeanCopier.LOGGER.debug("Error:", e);
              }
            }
          }
        }
      }
    }

    return true;
  }

  /**
   * Copy with pk.
   *
   * @param srcBean the src bean
   * @param desBean the des bean
   * @return true, if successful
   */
  public static boolean copyWithPk(final Object srcBean, final Object desBean) {
    BeanCopier.copy(srcBean, desBean);

    final Class<?> srcClass = srcBean.getClass();
    final Class<?> desClass = desBean.getClass();

    // get only visible methods
    try {
      final Field pkFields = desClass.getField("pk");
      pkFields.setAccessible(true);

      final String getterName = "getPk";
      final Method getter = srcClass.getMethod(getterName);
      final Object pk = getter.invoke(srcBean);

      pkFields.set(desBean, pk);
      return true;

    } catch (final NoSuchFieldException | SecurityException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException e) {
      BeanCopier.LOGGER.debug("Error while copying Beans", e);
    }
    return false;
  }

  // this perform a shallow copy from getters of srcBean to setters of desBean, skipping all
  // collections.
  /**
   * Copy.
   *
   * @param srcBean the src bean
   * @param desBean the des bean
   * @return true, if successful
   */
  public static boolean copy(final Object srcBean, final Object desBean, String... ignoreFields) {
    final Class<?> srcClass = srcBean.getClass();
    final Class<?> desClass = desBean.getClass();

    // get only visible methods
    final Method[] methods = desClass.getMethods();

    for (final Method desMethod : methods) {
      if (BeanCopier.isDefaultCopy(desMethod, srcClass) && !BeanCopier.isMatchField(desMethod.getName(), ignoreFields)) {
        final String getterName = BeanCopier.getDefaultGetterName(desMethod);

        try {
          final Class<?> setterParamType = desMethod.getParameterTypes()[0];
          // only set non-collection fields.
          if (Collection.class.isAssignableFrom(setterParamType)) {
            continue;
          }

          final Method getter = srcClass.getMethod(getterName);

          try {
            if (setterParamType.isAssignableFrom(getter.getReturnType())) {
              desMethod.invoke(desBean, getter.invoke(srcBean));
            } else
              throw new IllegalArgumentException();
          } catch (final IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            BeanCopier.LOGGER.debugf(
                e,
                "Source class: " + srcClass.getName() + ", getter return type: " + getter.getReturnType() + " is not castable to: " + desClass
                .getName() + ", setter's param type: " + setterParamType);
          }

        } catch (final NoSuchMethodException e) {
          // BeanCopier.LOGGER.debugf(e, "Missing getter in source bean: " + srcClass.getName() +
          // "." + getterName);

          // ignore this exception
        }
      }

    }

    return true;
  }

  /**
   * Copy.
   *
   * @param <T> the generic type
   * @param srcBean the src bean
   * @param desClass the des class
   * @return the t
   * @throws ArrException the arr exception
   */
  public static <T> T copy(final Object srcBean, final Class<T> desClass) throws ArrowException {
    if (srcBean == null)
      return null;

    T toBean;
    try {
      toBean = desClass.newInstance();
      return BeanCopier.copy(srcBean, toBean) ? toBean : null;
    } catch (InstantiationException | IllegalAccessException e) {
      BeanCopier.LOGGER.debugf(e, "Copy Failed");
      throw new ArrowException("error.BeanCopier.copyFailed");
    }
  }


  /**
   * Copy pk.
   *
   * @param fromBean the from bean
   * @param toBean the to bean
   * @return the base pk
   */
  public static ArrowPk copyPk(final ArrowPk fromBean, final ArrowPk toBean) {
    if ((fromBean == null) || (toBean == null))
      return null;

    final Class<?> fromClass = fromBean.getClass();
    final Class<?> toClass = toBean.getClass();

    final Field[] fields = toClass.getDeclaredFields();

    for (final Field field : fields) {
      try {
        final String propertyName = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);

        final Method getter = fromClass.getMethod("get" + propertyName);

        if ((field.getAnnotation(OneToMany.class) != null) || (((field.getAnnotation(ManyToOne.class) != null) || (field
            .getAnnotation(OneToOne.class) != null)) && (!field.getType().equals(getter.getReturnType())))) {
          continue;
        }

        final Method setter = toClass.getMethod("set" + propertyName, getter.getReturnType());

        setter.invoke(toBean, getter.invoke(fromBean));
      } catch (final NoSuchMethodException e) {
        BeanCopier.LOGGER.debugf(e, "Destination bean does not contain the setter correspond to source bean's getter method ");
        continue;
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e2) {
        BeanCopier.LOGGER.debugf("UnExpected Exception", e2);
      }
    }
    return toBean;
  }
}
