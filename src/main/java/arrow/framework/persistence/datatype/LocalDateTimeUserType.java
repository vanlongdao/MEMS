package arrow.framework.persistence.datatype;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;

public class LocalDateTimeUserType extends AbstractUserType {

  private static final int[] SQL_TYPES = new int[] {Types.TIMESTAMP};

  @Override
  public int[] sqlTypes() {
    return LocalDateTimeUserType.SQL_TYPES;
  }

  // There is no way to walk-around
  @SuppressWarnings("rawtypes")
  @Override
  public Class returnedClass() {
    return LocalDateTime.class;
  }

  @Override
  public boolean equals(Object x, Object y) throws HibernateException {
    if (x == y)
      return true;
    if ((x == null) || (y == null))
      return false;
    final LocalDateTime dtx = (LocalDateTime) x;
    final LocalDateTime dty = (LocalDateTime) y;
    return dtx.equals(dty);
  }

  @Override
  public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
    final Object timestamp = StandardBasicTypes.TIMESTAMP.nullSafeGet(resultSet, names, session, owner);
    if (timestamp == null)
      return null;
    final Date ts = (Date) timestamp;
    final Instant instant = Instant.ofEpochMilli(ts.getTime());
    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
  }

  @Override
  public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SessionImplementor session) throws HibernateException,
      SQLException {
    if (value == null) {
      StandardBasicTypes.TIMESTAMP.nullSafeSet(preparedStatement, null, index, session);
    } else {
      final LocalDateTime ldt = ((LocalDateTime) value);
      final Instant instant = ldt.atZone(ZoneId.systemDefault()).toInstant();
      final Date timestamp = Date.from(instant);
      StandardBasicTypes.TIMESTAMP.nullSafeSet(preparedStatement, timestamp, index, session);
    }
  }
}
