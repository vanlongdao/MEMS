package arrow.framework.persistence.datatype;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.HibernateException;
import org.hibernate.usertype.EnhancedUserType;

public abstract class AbstractUserType implements EnhancedUserType, Serializable {
  @Override
  public int hashCode(Object object) throws HibernateException {
    return object.hashCode();
  }

  @Override
  public Object deepCopy(Object value) throws HibernateException {
    return value;
  }

  @Override
  public boolean isMutable() {
    return false;
  }

  @Override
  public Serializable disassemble(Object value) throws HibernateException {
    return (Serializable) value;
  }

  @Override
  public Object assemble(Serializable cached, Object value) throws HibernateException {
    return cached;
  }

  @Override
  public Object replace(Object original, Object target, Object owner) throws HibernateException {
    return original;
  }

  @Override
  public String objectToSQLString(Object object) {
    throw new UnsupportedOperationException();
  }

  @Override
  public String toXMLString(Object object) {
    return object.toString();
  }

  @Override
  public Object fromXMLString(String string) {
    return LocalDate.parse(string);
  }
}
