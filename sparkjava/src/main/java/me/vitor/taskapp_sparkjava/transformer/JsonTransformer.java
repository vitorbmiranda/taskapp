package me.vitor.taskapp_sparkjava.transformer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.Instant;
import spark.ResponseTransformer;

/**
 * Apply a JSON transformation to the API routes that uses this.
 * <p>Uses a custom deserializer for Instants when building {@link Gson} responses.</p>
 */
public class JsonTransformer implements ResponseTransformer {

  private Gson gson;

  public JsonTransformer() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(Instant.class, new InstantDeserializer());
    gson = gsonBuilder.create();
  }

  @Override
  public String render(Object model) {
    return gson.toJson(model);
  }

  class InstantDeserializer implements JsonSerializer<Instant> {
    @Override
    public JsonElement serialize(Instant instant, Type type,
        JsonSerializationContext jsonSerializationContext) {
      return new JsonPrimitive(instant.toString());
    }

  }

}