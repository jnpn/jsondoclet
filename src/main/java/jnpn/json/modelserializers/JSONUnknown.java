package jnpn.json.modelserializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import javax.lang.model.element.Element;
import java.lang.reflect.Type;

public class JSONUnknown implements JsonSerializer<Element> {

    public JsonElement serialize(Element e, Type src, JsonSerializationContext ctx) {
        System.out.println(e);
        return null;
    }

}
