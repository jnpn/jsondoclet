package jnpn.json.modelserializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import javax.lang.model.element.TypeParameterElement;
import java.lang.reflect.Type;

public class JSONTypeParameterElement implements JsonSerializer<TypeParameterElement> {

    public JsonElement serialize(TypeParameterElement e, Type src, JsonSerializationContext ctx) {
        System.out.println(e);
        return null;
    }

}
