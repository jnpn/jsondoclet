package jnpn.json.visitors;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;

import jdk.javadoc.doclet.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import javax.tools.Diagnostic.Kind;
import com.sun.source.util.*;
import com.sun.source.util.DocSourcePositions;

import jnpn.json.modelserliazers.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

/**
 * @class Json : JSON tree visitor for Doclets
 * @param None
 * @return a JSON Document
 */
public class Json implements ElementVisitor<JsonElement,Void> {

    private Gson root;
    private DocTrees trees;
    private String indent = "   ";

    public Json(DocTrees trees) {
	this.trees = trees;
	var b = new GsonBuilder();
	// unused so far, potential infinite cycle/loop
	// aww noes, gson cannot handle cyclic structures T_T
	// options:
	//  - fork gson
	//  - make a tree from the javax.model classes
	b.registerTypeAdapter(ExecutableElement.class, new JSONExecutable());
	b.registerTypeAdapter(PackageElement.class, new JSONPackage());
	b.registerTypeAdapter(TypeElement.class, new JSONType());
	b.registerTypeAdapter(TypeParameterElement.class, new JSONTypeParameterElement());
	b.registerTypeAdapter(Element.class, new JSONUnknown());
	b.registerTypeAdapter(VariableElement.class, new JSONVariableElement());
	root = b.create();
    }

    /**
     * Iterable<T> -> Function<T, JsonElement> -> JsonArray
     *
     * JsonElement := JsonObject | JsonArray | JsonNull | JsonPrimitive(Char, String, Integer, Bool)
     *
     */
    private <T,U extends JsonElement> JsonArray listify(Iterable<T> l, Function<T, U> f) {
	var a = new JsonArray();
	for(T t: l) { a.add(f.apply(t)); };
	return a;
    }

    private <T> JsonArray listify(Iterable<T> l) {
	var a = new JsonArray();
	for (T t: l) { a.add(t.toString()); }
	return a;
    }

    /**
     * basic :: javax.lang.model.Element -> JsonElement
     * returns a JsonObject with some metadata about it
     *  - name
     *  - kind
     *  - doc
     *  - annotations
     *  - modifiers
     *  - children?
     *
     * @param e: Element
     * @return JsonElement
     */
    private JsonElement basic(Element e) {
	var j = new JsonObject();
	j.addProperty("name", e.getClass().getName().toString());
	j.addProperty("simplename", e.getSimpleName().toString());
	j.addProperty("enclosingelement", e.getEnclosingElement​().toString());
	j.addProperty("kind", e.getKind​().toString());

	var doc = trees.getDocCommentTree(e);
	var lines = (doc != null) ? doc.getFullBody().toString() : "";
	j.addProperty("DocTree", lines);

	j.add("modifiers", listify(e.getModifiers()));
	j.add("annotations", listify(e.getAnnotationMirrors()));

	var children = listify(e.getEnclosedElements(), (en) -> en.accept(this, null));
	j.add("elements", children);
	return j;
    }

    public JsonElement visitModule(ModuleElement e, Void p) { System.out.println(""); return null; }

    public JsonElement visit(Element e, Void p) {
	e.accept(this, null);
	return basic(e);
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
