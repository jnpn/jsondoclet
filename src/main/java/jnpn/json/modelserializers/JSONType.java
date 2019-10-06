package jnpn.json.modelserliazers;

import java.lang.reflect.Type;
import javax.lang.model.element.TypeElement;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSerializationContext;

public class JSONType implements JsonSerializer<TypeElement> {

    public JsonElement serialize(TypeElement e, Type src, JsonSerializationContext ctx) {
	System.out.println(e);
	return null;
    }

}
