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

    @Override
    public boolean run(DocletEnvironment environment) {

	System.out.println("---- JSON.java Doclet [JDK9 API] ----");

	showEnvironment(environment);

	var trees = environment.getDocTrees();
	if (trees == null) {
	    System.out.println("[WARNING] NO TREES");
	    return false;
	}

	var elems = environment.getSpecifiedElements();
	var gson = new GsonBuilder().setPrettyPrinting().create();

	for (Element e : elems) {
	    System.out.println("--- " + e);
	    var r = new Json(trees).visit(e, null);
	    System.out.println(gson.toJson(r));
	    save(e.getSimpleName().toString(), gson.toJson(r));
	}
	return true;
    }

    private boolean showEnvironment(DocletEnvironment environment) {
	System.out.println(environment);
	System.out.println(environment.getDocTrees());	// Returns an instance of the DocTrees utility class.
	System.out.println(" - included: " + environment.getIncludedElements());	// Returns the module, package and type elements that should be included in the documentation.
	System.out.println(environment.getModuleMode());	// Returns the required level of module documentation.
	System.out.println(environment.getSourceVersion());	// Returns the source version of the source files that were read.
	System.out.println(" - elements: " + environment.getSpecifiedElements());	// Returns the elements specified when the tool is invoked.
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
	reporter.print(Kind.NOTE, "> " + "...");
    }

}
