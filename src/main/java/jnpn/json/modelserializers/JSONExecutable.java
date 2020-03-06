package jnpn.json.modelserializers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import javax.lang.model.element.ExecutableElement;
import java.lang.reflect.Type;

public class JSONExecutable implements JsonSerializer<ExecutableElement> {

    public JsonElement serialize(ExecutableElement e, Type src, JsonSerializationContext ctx) {
        System.out.println("[JSONExecutable] serialize /begin");
        var j = new JsonArray();
        j.add(e.toString());
        System.out.println(e);
        System.out.println(j);
        System.out.println("[JSONExecutable] serialize /end");
        return j;
    }

}
