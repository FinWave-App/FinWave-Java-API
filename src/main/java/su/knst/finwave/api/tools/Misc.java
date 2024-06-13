package su.knst.finwave.api.tools;

import com.google.gson.*;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Misc {
    public static final Gson GSON;

    static {
        GsonBuilder builder = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(Instant.class, new InstantAdapter())
                .registerTypeAdapter(OffsetDateTime.class, (JsonDeserializer<OffsetDateTime>)
                        (j, t, c) -> OffsetDateTime.parse(j.getAsString()))
                .registerTypeAdapter(OffsetDateTime.class, (JsonSerializer<OffsetDateTime>)
                        (o, t, c) -> c.serialize(o.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                        (j, t, c) -> LocalDate.parse(j.getAsString()))
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>)
                        (o, t, c) -> c.serialize(o.toString()))
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>)
                        (j, t, c) -> LocalDateTime.parse(j.getAsString()))
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>)
                        (o, t, c) -> c.serialize(o.toString()));

        GSON = builder.create();
    }

    public static String formatQueryURL(String base, Object... args) {
        if (args.length == 0) return base;
        if (args.length % 2 != 0) throw new IllegalArgumentException("The number of arguments is odd!");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(base).append("?");

        for (int i = 0; i < args.length / 2; i++) {
            if (args[i * 2 + 1] == null)
                continue;

            if (i > 0)
                stringBuilder.append("&");

            stringBuilder
                    .append(args[i * 2])
                    .append("=")
                    .append(URLEncoder.encode(args[i * 2 + 1].toString(), StandardCharsets.UTF_8));
        }

        return stringBuilder.toString();
    }

    public static String formatQueryURL(String base, List<Object> args) {
        return formatQueryURL(base, args.toArray(new Object[0]));
    }

    static class LocalDateAdapter implements JsonDeserializer<LocalDate>, JsonSerializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE);
        }

        @Override
        public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }
    }

    static class InstantAdapter implements JsonDeserializer<Instant>, JsonSerializer<Instant> {
        @Override
        public Instant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return Instant.ofEpochMilli(json.getAsBigDecimal().multiply(new BigDecimal("1000")).longValue());
        }

        @Override
        public JsonElement serialize(Instant src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }
    }
}
