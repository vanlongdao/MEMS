package arrow.mems.rest.serializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import arrow.mems.constant.CommonConstants;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

  @Override
  public void serialize(final LocalDateTime value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException,
      JsonGenerationException {
    final DateTimeFormatter pattern = DateTimeFormatter.ofPattern(CommonConstants.DEFAULT_DATETIME_FORMAT);
    final String format = pattern.format(value);
    jgen.writeString(format);
  }
}
