package arrow.framework.persistence.datatype;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;

public class LocalTimeUserType extends AbstractUserType {

  private static final int[] SQL_TYPES = new int[] {Types.TIME};
  private static final int A_YEAR = 2000;
  private static final int A_MONTH = 1;
  private static final int A_DAY = 1;

  @Override
  public int[] sqlTypes() {
    return LocalTimeUserType.SQL_TYPES;
  }

  // There is no way to walk-around
  @SuppressWarnings("rawtypes")
  @Override
  public Class returnedClass() {
    return LocalTime.class;
  }

  @Override
  public boolean equals(Object x, Object y) throws HibernateException {
    if (x == y)
      return true;
    if ((x == null) || (y == null))
      return false;
    final LocalTime dtx = (LocalTime) x;
    final LocalTime dty = (LocalTime) y;
    return dtx.equals(dty);
  }

  @Override
  public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
    final Object timestamp = StandardBasicTypes.TIME.nullSafeGet(resultSet, names, session, owner);
    if (timestamp == null)
      return null;
    final Date time = (Date) timestamp;
    final Instant instant = Instant.ofEpochMilli(time.getTime());
    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
  }

  @Override
  public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SessionImplementor session) throws HibernateException,
      SQLException {
    if (value == null) {
      StandardBasicTypes.TIME.nullSafeSet(preparedStatement, null, index, session);
    } else {
      final LocalTime lt = ((LocalTime) value);
      final Instant instant =
          lt.atDate(LocalDate.of(LocalTimeUserType.A_YEAR, LocalTimeUserType.A_MONTH, LocalTimeUserType.A_DAY)).atZone(ZoneId.systemDefault())
              .toInstant();
      final Date time = Date.from(instant);
      StandardBasicTypes.TIME.nullSafeSet(preparedStatement, time, index, session);
    }
  }

}
