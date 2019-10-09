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

import jnpn.json.visitors.Json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;


public class JSONDoclet implements Doclet {

    private Reporter reporter;
    private Locale locale;
    @Override
    public String getName() { return "jnpn.JSON:Doclet"; }

    @Override
    public Set<Doclet.Option> getSupportedOptions() { return new HashSet<Doclet.Option>(); }

    @Override
    public SourceVersion getSupportedSourceVersion() { return SourceVersion.RELEASE_9; }

    @Override
    public boolean run(DocletEnvironment environment) {

	reporter.print(Kind.NOTE, "---- JSON.java Doclet [JDK9 API] ----");

	showEnvironment(environment);

	var trees = environment.getDocTrees();
	if (trees == null) {
	    System.err.println("[WARNING] NO TREES");
	    return false;
	}

	var elems = environment.getSpecifiedElements();
	var gson = new GsonBuilder().setPrettyPrinting().create();
	var sink = new Sink(); // "json" is default

	for (Element e : elems) {
	    System.out.println("--- " + e);
	    var res = new Json(trees).visit(e, null);
	    var js = gson.toJson(res);
	    var fn = e.getSimpleName().toString();
	    System.err.println(js);
	    sink.save(fn, js);
	}
	return true;
    }

    private boolean showEnvironment(DocletEnvironment environment) {
	System.out.println("[info] " + environment);
	System.out.println("[info] " + environment.getDocTrees());	// Returns an instance of the DocTrees utility class.
	System.out.println("[info] " + " - included: " + environment.getIncludedElements());	// Returns the module, package and type elements that should be included in the documentation.
	System.out.println("[info] " + environment.getModuleMode());	// Returns the required level of module documentation.
	System.out.println("[info] " + environment.getSourceVersion());	// Returns the source version of the source files that were read.
	System.out.println("[info] " + " - elements: " + environment.getSpecifiedElements());	// Returns the elements specified when the tool is invoked.
	return true;
    }
    
    @Override
    public void init(Locale locale, Reporter reporter) {
	this.reporter = reporter;
	this.locale = locale;
	System.out.println("[init] " + "doclet: " + getName());
	System.out.println("[init] " + "locale: " + locale);
	System.out.println("[init] " + "reporter: " + reporter);
	reporter.print(Kind.NOTE, "> " + "...");
    }

}
