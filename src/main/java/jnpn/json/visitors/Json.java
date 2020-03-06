package jnpn.json.visitors;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.source.util.DocTrees;

import javax.lang.model.element.*;
import java.util.function.Function;

/**
 * @Class Json : JSON tree visitor for Doclets
 * @Return a JSON Document
 */
public class Json implements ElementVisitor<JsonElement, Void> {

    private DocTrees trees;

    public Json(DocTrees trees) {
        this.trees = trees;
    }

    /**
     * Iterable<T> -> Function<T, JsonElement> -> JsonArray
     * <p>
     * JsonElement := JsonObject | JsonArray | JsonNull | JsonPrimitive(Char, String, Integer, Bool)
     */
    private <T, U extends JsonElement> JsonArray listify(Iterable<T> l, Function<T, U> f) {
        var a = new JsonArray();
        for (T t : l) {
            a.add(f.apply(t));
        }
        return a;
    }

    private <T> JsonArray listify(Iterable<T> l) {
        var a = new JsonArray();
        for (T t : l) {
            a.add(t.toString());
        }
        return a;
    }

    /**
     * basic :: javax.lang.model.Element -> JsonElement
     * returns a JsonObject with some metadata about it
     * - name
     * - kind
     * - doc
     * - annotations
     * - modifiers
     * - children?
     *
     * @param e: Element
     * @return JsonElement
     */
    private JsonElement basic(Element e) {
        var j = new JsonObject();
        j.addProperty("name", e.getClass().getName());
        j.addProperty("simplename", e.getSimpleName().toString());
        j.addProperty("enclosingelement", e.getEnclosingElement().toString());
        j.addProperty("kind", e.getKind().toString());

        var doc = trees.getDocCommentTree(e);
        var lines = (doc != null) ? doc.getFullBody().toString() : "";
        j.addProperty("DocTree", lines);

        j.add("modifiers", listify(e.getModifiers()));
        j.add("annotations", listify(e.getAnnotationMirrors()));

        var children = listify(e.getEnclosedElements(), (en) -> en.accept(this, null));
        j.add("elements", children);
        return j;
    }

    public JsonElement visitModule(ModuleElement e, Void p) {
        System.out.println();
        return null;
    }

    public JsonElement visit(Element e, Void p) {
        return e.accept(this, null);
    }

    public JsonElement visitExecutable(ExecutableElement e, Void p) {
        return basic(e);
    }

    public JsonElement visitPackage(PackageElement e, Void p) {
        return basic(e);
    }

    public JsonElement visitType(TypeElement e, Void p) {
        return basic(e);
    }

    public JsonElement visitTypeParameter(TypeParameterElement e, Void p) {
        return basic(e);
    }

    public JsonElement visitUnknown(Element e, Void p) {
        System.out.println("UNK: " + e);
        return basic(e);
    }

    public JsonElement visitVariable(VariableElement e, Void p) {
        return basic(e);
    }

}
