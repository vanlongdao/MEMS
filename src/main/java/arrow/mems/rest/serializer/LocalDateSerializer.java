package arrow.mems.rest.serializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {

  @Override
  public void serialize(final LocalDate value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException,
      JsonGenerationException {

    // Angular only accept Date value in ISO format
    final DateTimeFormatter pattern = DateTimeFormatter.ISO_DATE;

    final String format = pattern.format(value);
    jgen.writeString(format);
  }

}
