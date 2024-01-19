package network.something.somepotter.lib.serialization;

import com.google.gson.*;
import net.minecraft.network.FriendlyByteBuf;

import java.lang.reflect.Type;
import java.util.function.Supplier;

public class GenericHolder<T> implements Supplier<T> {

    protected final Class<T> clazz;
    protected final T value;

    public GenericHolder(T value) {
        this.value = value;
        this.clazz = (Class<T>) value.getClass();
    }

    @Override
    public T get() {
        return value;
    }

    public FriendlyByteBuf encodeBytes(FriendlyByteBuf buffer) {
        String json = Serializer.toJson(this);
        buffer.writeUtf(json);
        return buffer;
    }

    public static <T> GenericHolder<T> decodeBytes(FriendlyByteBuf buffer) {
        String data = buffer.readUtf();
        return Serializer.fromJson(data, GenericHolder.class);
    }


    public static class TypeAdapter implements JsonDeserializer<GenericHolder<?>>, JsonSerializer<GenericHolder<?>> {
        public GenericHolder<?> deserialize(JsonElement data, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            String className = data.getAsJsonObject().get("clazz").getAsString();
            JsonElement valueJson = data.getAsJsonObject().get("value");
            try {
                Object value = ctx.deserialize(valueJson, Class.forName(className));
                return new GenericHolder<>(value);
            } catch (ClassNotFoundException e) {
                throw new JsonParseException("error parsing GenericHolder. could not find class " + className);
            }
        }

        public JsonElement serialize(GenericHolder<?> obj, Type type, JsonSerializationContext ctx) {
            JsonObject out = new JsonObject();
            out.addProperty("clazz", obj.clazz.getName());
            out.add("value", ctx.serialize(obj.value));
            return out;
        }
    }
}
