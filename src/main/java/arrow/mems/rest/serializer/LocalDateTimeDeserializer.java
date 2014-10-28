package arrow.mems.rest.serializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateDeserializer;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

  @Override
  public LocalDateTime deserialize(JsonParser pJp, DeserializationContext pCtxt) throws IOException, JsonProcessingException {
    final Date date = DateDeserializer.instance.deserialize(pJp, pCtxt);
    final Instant instant = date.toInstant();
    return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
  }

}
