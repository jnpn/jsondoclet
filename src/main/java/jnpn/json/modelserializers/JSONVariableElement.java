package jnpn.json.modelserializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import javax.lang.model.element.VariableElement;
import java.lang.reflect.Type;

public class JSONVariableElement implements JsonSerializer<VariableElement> {

    public JsonElement serialize(VariableElement e, Type src, JsonSerializationContext ctx) {
        System.out.println(e);
        return null;
    }

}
