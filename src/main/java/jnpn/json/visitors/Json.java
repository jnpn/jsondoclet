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

import jnpn.json.alpha.Bean;
import jnpn.json.modelserliazers.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	try {
	    System.out.println("---- ---- ----");
	    Bean b0 = new Bean();
	    System.out.println("pojo -> " + b0);
	    var m = new ObjectMapper();
	    String s = m.writerWithDefaultPrettyPrinter().writeValueAsString(b0);;
	    System.out.println("json -> " + s);
	}
	catch (Exception e) {
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	}

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

    private JsonElement basic(Element e) {
	var j = new JsonObject();
	j.addProperty("name", e.getClass().getName().toString());
	j.addProperty("simplename", e.getSimpleName().toString());
	j.addProperty("enclosingelement", e.getEnclosingElement​().toString());
	j.addProperty("kind", e.getKind​().toString());

	var jm = new JsonArray();
	e.getModifiers().stream().forEach((m) -> jm.add(m.toString()));
	j.add("modifiers", jm);

	var ja = new JsonArray();
	e.getAnnotationMirrors().stream().forEach((a) -> ja.add(a.toString()));
	j.add("annotations", ja);

	var doc = trees.getDocCommentTree(e);
	var lines = (doc != null) ? doc.getFullBody().toString() : "";
	j.addProperty("DocTree", lines);

	var children = new JsonArray();
	e.getEnclosedElements().forEach((en) -> children.add(en.accept(this, null)));

	j.add("elements", children);
	return j;
    }

    public JsonElement visitModule(ModuleElement e, Void p) { System.out.println(""); return null; }

    public JsonElement visit(Element e, Void p) {
	e.accept(this, null);
	return basic(e);
    }

    public JsonElement visitExecutable(ExecutableElement e, Void p) {
	// try {
	//     var m = new ObjectMapper();
	//     String s = m.writerWithDefaultPrettyPrinter().writeValueAsString(e);;
	//     System.out.println("json -> " + s);
	// }
	// catch (JsonProcessingException j) {
	//     System.out.println("Error " + j.getMessage());
	//     j.printStackTrace();
	// }
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
