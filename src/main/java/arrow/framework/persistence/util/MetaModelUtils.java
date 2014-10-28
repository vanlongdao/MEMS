package arrow.framework.persistence.util;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;

import org.apache.deltaspike.core.util.ReflectionUtils;

import arrow.framework.util.collections.ArrayUtils;

public class MetaModelUtils {

  public static <T> EntityType<T> getMetaModelEntityType(final EntityManager em, final Class<T> clazz) {
    final Metamodel m = em.getMetamodel();
    return m.entity(clazz);
  }

  /**
   * Checks whether the <code>aimerClass</code> is a super class of the <code>targetClass</code>
   *
   * @param targetClazz
   * @param aimerClazz
   * @return e.g. <code>aimerClass</code> = List.class, <code>targetClass</code> = ArrayList.class,
   *         the method returns true.
   */
  public static boolean isSuperClass(final Class<?> aimerClazz, final Class<?> targetClazz) {
    if (targetClazz.equals(aimerClazz))
      return true;

    if (targetClazz.equals(Object.class))
      return false;

    return MetaModelUtils.isSuperClass(targetClazz.getSuperclass(), aimerClazz);
  }

  public static boolean isEntityClass(final Class<?> clazz) {
    return clazz.getAnnotation(Entity.class) != null;
  }

  public static FieldPath[] getPkFieldPaths(final Class<?> clazz) {
    final Field[] fields = clazz.getDeclaredFields();

    for (final Field field : fields) {
      final EmbeddedId emb = field.getAnnotation(EmbeddedId.class);
      if (emb == null) {
        continue;
      }

      final Field[] pkFields = field.getType().getDeclaredFields();
      final FieldPath[] pks = new FieldPath[pkFields.length];

      for (int i = 0; i < pkFields.length; ++i) {
        pks[i] = FieldPath.valueOf(field.getName(), pkFields[i].getName());
      }

      return pks;
    }

    for (final Field field : fields) {
      if (field.getAnnotation(Id.class) != null)
        return new FieldPath[] {FieldPath.valueOf(field.getName())};
    }

    return new FieldPath[] {};
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public static List<FieldPath> getFieldPathsWithAnnotation(final Class<?> clazz, final Class annotation) {
    final List<FieldPath> paths = new ArrayList<>();
    final Set<Field> fields = ReflectionUtils.getAllDeclaredFields(clazz);

    for (final Field field : fields) {
      if (field.getAnnotation(annotation) != null) {
        paths.add(FieldPath.valueOf(field.getName()));
      }
    }

    return paths;
  }

  /**
   * Gets the next {@link SingularAttribute} from the specified <code>singularAttribute</code> and
   * <code>field</code>.
   *
   * @param singularAttribute
   * @param field
   *        the field that we want to retrieve the {@link SingularAttribute} object. Notice that
   *        this is a field, not a field path. For example,
   *        an <code>address</code> has field <code>manufacturerPerson</code> with type =
   *        <code>Person</code>.
   *        Person has field <code>name</code> with type
   *        = {@link String}. Then for the {@link SingularAttribute} object that represents the
   *        <code>address</code> attribute, <code>manufacturerPerson</code> is a valid field, but
   *        <code>manufacturerPerson.name</code> is invalid (it is a field path, not a field).
   * @return
   */
  private static <X, Y> SingularAttribute<? super Y, ?> getSingularAttributeInternally(final SingularAttribute<X, Y> singularAttribute,
      final String field) {
    final Type<Y> type = singularAttribute.getType();

    if (!(type instanceof ManagedType))
      throw new RuntimeException("singularAttribute: [" + singularAttribute.getName() + "] invalid: its associated type [" + type.getJavaType()
          .getName() + "] is not a managed type.");

    final ManagedType<Y> managedType = (ManagedType<Y>) type;
    return managedType.getSingularAttribute(field);
  }

  /**
   * Gets the next {@link SingularAttribute} from the specified <code>singularAttribute</code> and
   * <code>fieldPath</code>.
   *
   * @param singularAttribute
   * @param fieldPath
   *        the field path. It could also be a field. e.g. manufacturerPerson or
   *        manufacturerPerson.name are all valid.
   * @return
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public static <X, Y> SingularAttribute[] getSingularAttributes(final SingularAttribute<X, Y> singularAttribute, final String fieldPath) {
    final String[] fields = fieldPath.split("\\.");

    final SingularAttribute[] results = new SingularAttribute[fields.length];
    results[0] = MetaModelUtils.getSingularAttributeInternally(singularAttribute, fields[0]);

    for (int i = 1; i < fields.length; ++i) {
      results[i] = MetaModelUtils.getSingularAttributeInternally(results[i - 1], fields[i]);
    }

    return results;
  }

  /**
   * Gets the next {@link SingularAttribute} from the specified <code>singularAttribute</code> and
   * <code>fieldPath</code>.
   *
   * @param singularAttribute
   * @param fieldPath
   *        the field path. It could also be a field. e.g. manufacturerPerson or
   *        manufacturerPerson.name are all valid.
   * @return
   */
  @SuppressWarnings("rawtypes")
  public static <X, Y> SingularAttribute getSingularAttribute(final SingularAttribute<X, Y> singularAttribute, final String fieldPath) {
    final SingularAttribute[] results = MetaModelUtils.getSingularAttributes(singularAttribute, fieldPath);
    return ArrayUtils.isEmpty(results) ? null : results[results.length - 1];
  }

  /**
   * Gets the singular attributes for the specified <code>fieldPath</code> and <code>clazz</code>.
   * For example, an <code>address</code> has field <code>manufacturerPerson</code> with type =
   * <code>Person</code>. Person has field <code>name</code>. Then with
   * <code>clazz=Address.class</code> and <code>fieldPath=manufacturerPerson.name</code>, this
   * method will
   * return an array of two {@link SingularAttribute} objects:
   * <ul>
   * <li>A {@link SingularAttribute} object for attribute <code>manufacturerPerson</code> in
   * <code>Address</code> class.</li>
   * <li>A {@link SingularAttribute} object for attribute <code>name</code> in <code>Person</code>
   * class.</li>
   * </ul>
   *
   * @param entityManager
   * @param clazz
   * @param fieldPath
   *        the field path. It could also be a field. e.g. manufacturerPerson or
   *        manufacturerPerson.name are all valid.
   * @return
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public static SingularAttribute[] getSingularAttributes(final EntityManager entityManager, final Class<?> clazz, final FieldPath fieldPath) {
    final EntityType<?> entityType = MetaModelUtils.getMetaModelEntityType(entityManager, clazz);

    final SingularAttribute[] results = new SingularAttribute[fieldPath.getFields().length];
    results[0] = entityType.getSingularAttribute(fieldPath.getFields()[0]);

    for (int i = 1; i < fieldPath.getFields().length; ++i) {
      results[i] = MetaModelUtils.getSingularAttribute(results[i - 1], fieldPath.getFields()[i]);
    }

    return results;
  }

  /**
   * Gets the leaf singular attribute for the specified <code>fieldPath</code> and
   * <code>clazz</code>. For example, an <code>address</code> has
   * field <code>manufacturerPerson</code> with type = <code>Person</code>. Person has field
   * <code>name</code>.
   * Then with <code>clazz=Address.class</code> and <code>fieldPath=manufacturerPerson.name</code>,
   * this method
   * will return a {@link SingularAttribute} object for attribute <code>name</code> in
   * <code>Person</code> class.
   *
   * @param entityManager
   * @param clazz
   * @param fieldPath
   *        the field path. It could also be a field. e.g. manufacturerPerson or
   *        manufacturerPerson.name are all valid.
   * @return
   */
  @SuppressWarnings("rawtypes")
  public static SingularAttribute getSingularAttribute(final EntityManager entityManager, final Class<?> clazz, final FieldPath fieldPath) {
    final SingularAttribute[] results = MetaModelUtils.getSingularAttributes(entityManager, clazz, fieldPath);
    return ArrayUtils.isEmpty(results) ? null : results[results.length - 1];
  }

  @SuppressWarnings("rawtypes")
  public static <T> SingularAttribute[] getPkSingularAttributes(final EntityManager entityManager, final Class<T> clazz) {
    final FieldPath[] pkFieldPaths = MetaModelUtils.getPkFieldPaths(clazz.getSuperclass());
    final SingularAttribute[] results = new SingularAttribute[pkFieldPaths.length];
    for (int i = 0; i < pkFieldPaths.length; i++) {
      results[i] = MetaModelUtils.getSingularAttribute(entityManager, clazz, pkFieldPaths[i]);
    }
    return results;
  }

  /**
   * Get single primary key attribute for specific entityClass return null in case entityClass has
   * zero or composite primary keys
   *
   * @param entityClass
   */
  public static <T> SingularAttribute<? super T, ?> getSinglePKAttribute(final Class<T> entityClass, final EntityManager emMain) {
    @SuppressWarnings("unchecked")
    final SingularAttribute<? super T, ?>[] pkAttributes = MetaModelUtils.getPkSingularAttributes(emMain, entityClass);
    if (pkAttributes.length == 1)
      return pkAttributes[0];
    return null;
  }

  @SuppressWarnings("rawtypes")
  public static List<SingularAttribute> getSingulareAttributesByAnnocation(final EntityManager entityManager, final Class<?> clazz,
      final Class<?> annotation) {
    final List<SingularAttribute> attributes = new ArrayList<>();
    final List<FieldPath> paths = MetaModelUtils.getFieldPathsWithAnnotation(clazz, annotation);
    for (final FieldPath path : paths) {
      attributes.add(MetaModelUtils.getSingularAttribute(entityManager, clazz, path));
    }
    return attributes;
  }

  /**
   * Gets the field path from an array of <code>singularAttributes</code>.
   *
   * @param singularAttributes
   * @return e.g. <code>singularAttributes</code> = { SingularAttribute<Address, Person>
   *         manufacturerPerson,
   *         SingularAttribute<Person, String> name }, the
   *         method returns manufacturerPerson.name
   */
  @SuppressWarnings("rawtypes")
  public static FieldPath buildFieldPath(final SingularAttribute[] singularAttributes) {
    final List<String> fieldList = new ArrayList<String>();

    for (final SingularAttribute attribute : singularAttributes) {
      fieldList.add(attribute.getName());
    }

    return FieldPath.valueOf(fieldList);
  }

  /**
   * Builds a {@link Path} object from the specified <code>root</code> and
   * <code>singularAttributes</code>.
   *
   * @param root
   * @param singularAttributes
   * @return
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public static <T> Path buildPath(final Root<T> root, final SingularAttribute[] singularAttributes) {
    Path path = root.get(singularAttributes[0]);

    for (int i = 1; i < singularAttributes.length; ++i) {
      path = path.get(singularAttributes[i]);
    }

    return path;
  }

  @SuppressWarnings("rawtypes")
  public static <T> Path getPath(final Root<T> root, final EntityManager entityManager, final Class<T> clazz, final FieldPath fieldPath) {
    final SingularAttribute[] singularAttributes = MetaModelUtils.getSingularAttributes(entityManager, clazz, fieldPath);
    return MetaModelUtils.buildPath(root, singularAttributes);
  }

  /**
   * Breaks the <code>path</code> to an array of associated {@link SingularAttribute} objects.
   *
   * @param path
   * @return
   */
  @SuppressWarnings("rawtypes")
  public static SingularAttribute[] decompile(Path path) {
    final List<SingularAttribute> attributeList = new ArrayList<SingularAttribute>();

    SingularAttribute singularAttribute;
    Object object;

    while (true) {
      object = path.getModel();
      if (object instanceof EntityType<?>) {
        break;
      }

      singularAttribute = (SingularAttribute) object;
      attributeList.add(singularAttribute);
      path = path.getParentPath();
    }

    final SingularAttribute[] attributes = new SingularAttribute[attributeList.size()];

    for (int i = attributeList.size() - 1; i >= 0; --i) {
      attributes[attributeList.size() - i - 1] = attributeList.get(i);
    }

    return attributes;
  }

  private static Object invokeInternally(final EntityManager entityManager, final Object entityObject,
      @SuppressWarnings("rawtypes") final SingularAttribute[] singularAttributes, final int fromIndex) {
    if (fromIndex >= singularAttributes.length)
      throw new IllegalArgumentException("fromIndex [" + fromIndex + "] is invalid. There are only " + singularAttributes.length + " elements.");

    final Member member = singularAttributes[fromIndex].getJavaMember();
    if (!(member instanceof Field))
      throw new IllegalStateException(
          "object: [" + entityObject.toString() + "]: " + singularAttributes[fromIndex].getName() + " (Java Member = " + member + ") is not a field.");

    final Object result;

    try {
      result = ((Field) member).get(entityObject);
    } catch (final IllegalArgumentException | IllegalAccessException ex) {
      throw new IllegalStateException(
          "Exception accessing field " + singularAttributes[fromIndex].getName() + " of object " + entityObject + ": " + ex.getMessage());
    }

    if (fromIndex == (singularAttributes.length - 1))
      return result;

    return MetaModelUtils.invokeInternally(entityManager, result, singularAttributes, fromIndex + 1);
  }

  public static Object invoke(final EntityManager entityManager, final Object entityObject,
      @SuppressWarnings("rawtypes") final SingularAttribute[] sas) {
    return MetaModelUtils.invokeInternally(entityManager, entityObject, sas, 0);
  }

  @SuppressWarnings("rawtypes")
  public static Object invoke(final EntityManager entityManager, final Object entityObject, final FieldPath fieldPath) {
    final SingularAttribute[] singularAttributes = MetaModelUtils.getSingularAttributes(entityManager, entityObject.getClass(), fieldPath);
    return MetaModelUtils.invokeInternally(entityManager, entityObject, singularAttributes, 0);
  }
}
