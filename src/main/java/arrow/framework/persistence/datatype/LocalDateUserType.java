package arrow.framework.persistence.datatype;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;

public class LocalDateUserType extends AbstractUserType {

  private static final int[] SQL_TYPES = new int[] {Types.DATE};

  @Override
  public int[] sqlTypes() {
    return LocalDateUserType.SQL_TYPES;
  }

  // There is no way to walk-around
  @SuppressWarnings("rawtypes")
  @Override
  public Class returnedClass() {
    return LocalDate.class;
  }

  @Override
  public boolean equals(Object x, Object y) throws HibernateException {
    if (x == y)
      return true;
    if ((x == null) || (y == null))
      return false;
    final LocalDate dtx = (LocalDate) x;
    final LocalDate dty = (LocalDate) y;
    return dtx.equals(dty);
  }

  @Override
  public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
    final Object timestamp = StandardBasicTypes.DATE.nullSafeGet(resultSet, names, session, owner);
    if (timestamp == null)
      return null;
    final Date date = (Date) timestamp;
    final Instant instant = Instant.ofEpochMilli(date.getTime());
    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
  }

  @Override
  public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SessionImplementor session) throws HibernateException,
      SQLException {
    if (value == null) {
      StandardBasicTypes.DATE.nullSafeSet(preparedStatement, null, index, session);
    } else {
      final LocalDate ld = ((LocalDate) value);
      final Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
      final Date time = Date.from(instant);
      StandardBasicTypes.DATE.nullSafeSet(preparedStatement, time, index, session);
    }
  }
}
