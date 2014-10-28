package arrow.mems.rest.serializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import arrow.mems.persistence.entity.AbstractEntity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class AbstractEntitySerializer extends JsonSerializer<AbstractEntity> {
  protected void registerCustomSerializers(ObjectMapper mapper) {
    final SimpleModule dateModule = new SimpleModule("mems.DateModule", new Version(1, 0, 0, null, null, null));
    dateModule.addSerializer(LocalDate.class, new LocalDateSerializer());
    dateModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
    mapper.registerModule(dateModule);

    final SimpleModule dateTimeModule = new SimpleModule("mems.DateTimeModule", new Version(1, 0, 0, null, null, null));
    dateTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
    dateTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
    mapper.registerModule(dateTimeModule);

    final SimpleModule timeModule = new SimpleModule("mems.TimeModule", new Version(1, 0, 0, null, null, null));
    timeModule.addSerializer(LocalTime.class, new LocalTimeSerializer());
    timeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
    mapper.registerModule(timeModule);
  }

  @Override
  public void serialize(AbstractEntity pValue, JsonGenerator pJgen, SerializerProvider pProvider) throws IOException, JsonProcessingException {
    final ObjectMapper mapper = new ObjectMapper();
    this.registerCustomSerializers(mapper);
  }

}
