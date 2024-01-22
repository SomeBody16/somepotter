package network.something.somelib.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Serializer {

    protected static final GsonBuilder GSON_BUILDER = new GsonBuilder().setLenient()
            .registerTypeAdapter(GenericHolder.class, new GenericHolder.TypeAdapter());

    protected static Gson GSON = GSON_BUILDER.create();

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> type) {
        return GSON.fromJson(json, type);
    }

    protected static void init() {
        GSON = GSON_BUILDER.create();
    }

}
