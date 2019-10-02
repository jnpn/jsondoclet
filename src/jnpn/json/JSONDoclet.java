package jnpn.json;

import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import jdk.javadoc.doclet.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import javax.tools.Diagnostic.Kind;
import com.sun.source.doctree.DocTree;
import com.sun.source.util.DocTrees;
import com.sun.source.util.DocSourcePositions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import jnpn.json.visitors.Json;

public class JSONDoclet implements Doclet {

    @Override
    public String getName() { return "jnpn.JSON:Doclet"; }

    @Override
    public Set<Doclet.Option> getSupportedOptions() { return new HashSet<Doclet.Option>(); }

    @Override
    public SourceVersion getSupportedSourceVersion() { return SourceVersion.RELEASE_9; }

    private JsonObject prologue(Element e, DocTrees trees) {
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
	    var lines = (doc != null) ? doc.getFullBody().toString() : "null doctree";
	    j.addProperty("DocTree", lines);

	    return j;
    }

    @Override
    public boolean run(DocletEnvironment environment) {

	Function<String,String> wrapped = (s) -> { return "\"" + s + "\""; };

	System.out.println("---- JSON.java Docet [JDK9 API] ----");

	var trees = environment.getDocTrees();
	if (trees == null) {
	    System.out.println("[WARNING] NO TREES");
	    return false;
	}

	System.out.println("TREES: " + trees);

	var elems = environment.getIncludedElements();
	var gson = new GsonBuilder().setPrettyPrinting().create();

	System.out.println("Listing Elements");

	for (Element e : elems) {

	    System.out.println("--- " + e);

	    var p = prologue(e, trees);
	    var r = new Json(trees).visit(e, null);
	    //	    System.out.println(gson.toJson(r));
	    p.add("elements", r);
	    System.out.println(gson.toJson(p));
	    save(e.getSimpleName().toString(), gson.toJson(p));
	}
	return true;
    }

    boolean convert(DocletEnvironment env) {
	var trees = env.getDocTrees();
	if (trees == null) {
	    System.err.println("[WARNING] NO TREES");
	    return false;
	}
	var elems = env.getIncludedElements();
	var gson = new GsonBuilder().setPrettyPrinting().create();

	System.out.println("Listing Elements");
	
	// var jsons = elems
	//     .stream()
	//     .map(toJson);

	for (Element e : elems) {
	    System.out.println("--- " + e);
	    var p = prologue(e, trees);
	    var r = new Json(trees).visit(e, null);
	    p.add("elements", r);
	    System.out.println(gson.toJson(p));
	    save(e.getSimpleName().toString(), gson.toJson(p));
	}
	return true;
    }

    private boolean save(String name, String json) {
	// @FIXME: hard coded paths
	var fn = "json/" + name + ".json";
	try (Writer writer = new BufferedWriter
	     (new OutputStreamWriter
	      (new FileOutputStream(fn), "utf-8"))) {
	    writer.write(json.toString());
	    return true;
	}
	catch (IOException e) {
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	} finally {
	    return false;
	}
    }

    @Override
    public void init(Locale locale, Reporter reporter) {
	System.out.println("doclet: " + getName());
	System.out.println("locale: " + locale);
	System.out.println("reporter: " + reporter);
	reporter.print(Kind.NOTE, "> " + some());
    }

    public String some() { return "some"; }

    /**
     * Public Static Void Main:
     *   this is the main method.
     *   it does things,
     *   or nothing,
     *   it's up to you.
     *
     * ~also:
     *   more comment ?
     *   yes.
     *
     * @param args : cli arguments
     * @param foo : fake
     * @param bar : fake'
     *
     * The end.
     * @end.
     **/
    public static void main(String[] args) {
	var s = "foo";
	System.out.println(s);
	System.out.println(new JSONDoclet().getName() + " bye.");
    }

}
