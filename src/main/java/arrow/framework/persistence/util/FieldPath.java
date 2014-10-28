package arrow.framework.persistence.util;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import arrow.framework.util.collections.ArrayUtils;
import arrow.mems.util.string.ArrowStrUtils;

/**
 * An object of this class represents a 'field path' in the system. Let say we have two entity
 * classes: Person and Address. address is an object of
 * type Person. street_name is an attribute of class Address. Then there are two field paths
 * associated with the entity class Person: 1. address (a
 * field is also a field path). 2. address.street_name.
 * <p>
 * To avoid confusing between a field path (represented by a string) and other strings, we represent
 * all field paths by instances of this class.
 *
 * @author linhbn
 */
public class FieldPath {

  public static final String SEPARATOR = ".";

  private final String[] fields;

  public String getFieldPath() {
    final StringBuffer fieldPath = new StringBuffer();

    for (final String field : this.fields) {
      if (fieldPath.length() > 0) {
        fieldPath.append(FieldPath.SEPARATOR);
      }

      fieldPath.append(field);
    }

    return fieldPath.toString();
  }

  public String[] getFields() {
    return this.fields;
  }

  @Override
  public boolean equals(final Object object) {
    if (!(object instanceof FieldPath))
      return false;

    final FieldPath fieldPath = (FieldPath) object;

    if (this.fields.length != fieldPath.getFields().length)
      return false;

    for (int i = 0; i < this.fields.length; ++i) {
      if (!ArrowStrUtils.areEquals(this.fields[i], fieldPath.getFields()[i]))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this);
  }

  @Override
  public String toString() {
    return this.getFieldPath();
  }

  public FieldPath(final String fieldPath) {
    if (StringUtils.isEmpty(fieldPath))
      throw new IllegalArgumentException("fieldPath can't be empty.");

    this.fields = fieldPath.split("\\" + FieldPath.SEPARATOR);
  }

  public FieldPath(final String[] fields) {
    if (ArrayUtils.isEmpty(fields))
      throw new IllegalArgumentException("fields can't be empty.");

    for (int i = 0; i < fields.length; ++i) {
      if (StringUtils.isEmpty(fields[i]))
        throw new IllegalArgumentException("Element #" + (i + 1) + " in the arguments is empty.");
    }

    this.fields = fields;
  }

  public static FieldPath valueOf(final String fieldPath) {
    return new FieldPath(fieldPath);
  }

  public static FieldPath valueOf(final String... fields) {
    return new FieldPath(fields);
  }

  public static FieldPath valueOf(final List<String> fieldList) {
    return new FieldPath(fieldList.toArray(new String[fieldList.size()]));
  }

}
