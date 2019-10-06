package jnpn.json.modelserliazers;

import java.lang.reflect.Type;
import javax.lang.model.element.PackageElement;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSerializationContext;

public class JSONPackage implements JsonSerializer<PackageElement> {

    public JsonElement serialize(PackageElement e, Type src, JsonSerializationContext ctx) {
	var o = new JsonObject();
	o.addProperty("element", e.toString());
	System.out.println(e + " => " + o);
	return o;
    }

}
