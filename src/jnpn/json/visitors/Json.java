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
	b.registerTypeAdapter(ExecutableElement.class, new JSONExecutable());
	b.registerTypeAdapter(PackageElement.class, new JSONPackage());
	b.registerTypeAdapter(TypeElement.class, new JSONType());
	b.registerTypeAdapter(TypeParameterElement.class, new JSONTypeParameterElement());
	b.registerTypeAdapter(Element.class, new JSONUnknown());
	b.registerTypeAdapter(VariableElement.class, new JSONVariableElement());
	root = b.create();
    }

    private JsonElement basic(Element e) {
	var children = new JsonArray();

	for (Element ee : e.getEnclosedElements()) {
	    //System.out.println(indent + ee);
	    var o = ee.accept(this, null);
	    children.add(o);
	}

	var j = new JsonObject();
	j.addProperty("kind", e.getKind().toString());
	j.addProperty("name", e.toString());
	j.add("elements", children);
	return j;
    }

    public JsonElement visitModule(ModuleElement e, Void p) { System.out.println(""); return null; }

    public JsonElement visit(Element e, Void p) {
	//	System.out.println("- visit: " + e + " | " + e.getKind());
	//	System.out.println(indent + "annotations?");
	e.accept(this, null);
	return basic(e);
    }

    public JsonElement visitExecutable(ExecutableElement e, Void p) {
	//	System.out.println("visitExecutable: " + e);
	//var o = root.toJson(e); // <= gson doesn't like javax.lang.model
	return basic(e);
    }

    private JsonElement jseq(Element e) {
	var j = new JsonObject();
	j.addProperty(":json", e.toString());
	return j;
    }

    public JsonElement visitPackage(PackageElement e, Void p) {
	//	System.out.println("visitPackage: " + e + " -> " + jseq(e));
	return basic(e);
    }

    public JsonElement visitType(TypeElement e, Void p) {
	//	System.out.println("visitType: " + e + " -> " + jseq(e));
	return basic(e);
    }

    public JsonElement visitTypeParameter(TypeParameterElement e, Void p) {
	//	System.out.println("visitTypeParameter" + " -> " + jseq(e));
	return basic(e);
    }

    public JsonElement visitUnknown(Element e, Void p) {
	System.out.println("UNK: " + e);
	return basic(e);
    }

    public JsonElement visitVariable(VariableElement e, Void p) {
	var js = e.getEnclosedElements()
	    .stream()
	    .map((el) -> { return root.toJson(el); })
	    .collect(Collectors.toList());
	// System.out.println("visitVariable" + " -> " + js);
	return basic(e);
    }

}
