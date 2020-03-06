package jnpn.json.modelserializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import javax.lang.model.element.TypeElement;
import java.lang.reflect.Type;

public class JSONType implements JsonSerializer<TypeElement> {

    public JsonElement serialize(TypeElement e, Type src, JsonSerializationContext ctx) {
        System.out.println(e);
        return null;
    }

}
