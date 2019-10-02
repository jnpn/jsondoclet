package jnpn.json;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import jdk.javadoc.doclet.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import javax.tools.Diagnostic.Kind;
import com.sun.source.doctree.DocTree;
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

    private JsonObject prologue(Element e, List<DocTree> trees) {
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
	    System.out.println(gson.toJson(p));

	    var v = new Json(trees);
	    var r = v.visit(e, null);
	    Function<String,String> wrapped = (s) -> { return "\"" + s + "\""; };

	    System.out.println(".");

	}
	return true;
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
	System.out.println(new JSON().getName() + " bye.");
    }

}
