package jnpn.json.modelserliazers;

import java.lang.reflect.Type;
import javax.lang.model.element.TypeParameterElement;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSerializationContext;

public class JSONTypeParameterElement implements JsonSerializer<TypeParameterElement> {

    public JsonElement serialize(TypeParameterElement e, Type src, JsonSerializationContext ctx) {
	System.out.println(e);
	return null;
    }

}
