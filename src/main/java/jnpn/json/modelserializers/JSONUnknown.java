package jnpn.json.modelserliazers;

import java.lang.reflect.Type;
import javax.lang.model.element.Element;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSerializationContext;

public class JSONUnknown implements JsonSerializer<Element> {

    public JsonElement serialize(Element e, Type src, JsonSerializationContext ctx) {
	System.out.println(e);
	return null;
    }

}
