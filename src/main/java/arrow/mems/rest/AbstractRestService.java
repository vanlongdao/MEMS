package arrow.mems.rest;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import arrow.framework.validator.ArrowValidator;
import arrow.mems.rest.serializer.LocalDateDeserializer;
import arrow.mems.rest.serializer.LocalDateSerializer;
import arrow.mems.rest.serializer.LocalDateTimeDeserializer;
import arrow.mems.rest.serializer.LocalDateTimeSerializer;
import arrow.mems.rest.serializer.LocalTimeDeserializer;
import arrow.mems.rest.serializer.LocalTimeSerializer;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class AbstractRestService implements Serializable {
  @Inject
  protected ArrowValidator validator;

  protected <T> Response buildResponse(RestResult<T> data) {
    return Response.status(Status.OK).entity(data).build();
  }

  protected void registerCustomSerializers() {
    final ObjectMapper mapper = new ObjectMapper();
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
}
