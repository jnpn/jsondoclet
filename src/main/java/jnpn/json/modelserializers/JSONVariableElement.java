package jnpn.json.modelserliazers;

import java.lang.reflect.Type;
import javax.lang.model.element.VariableElement;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSerializationContext;

public class JSONVariableElement implements JsonSerializer<VariableElement> {

    public JsonElement serialize(VariableElement e, Type src, JsonSerializationContext ctx) {
	System.out.println(e);
	return null;
    }

}
