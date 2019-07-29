package jnpn.json;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import jdk.javadoc.doclet.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import javax.tools.Diagnostic.Kind;
import com.sun.source.util.DocSourcePositions;

public class JSON implements Doclet {

    @Override
    public String getName() { return "jnpn.JSON:Doclet"; }

    @Override
    public Set<Doclet.Option> getSupportedOptions() { return new HashSet<Doclet.Option>(); }

    @Override
    public SourceVersion getSupportedSourceVersion() { return SourceVersion.RELEASE_9; }

    @Override
    public boolean run(DocletEnvironment environment) { 
	var trees = environment.getDocTrees();
	if (trees == null) { System.out.println("WAT TREES"); return false; }
	System.out.println("TREES: " + trees);
	var elems = environment.getIncludedElements();
	var indent = "   ";

	System.out.println("Listing Elements");

	for (Element e : elems) {
	    System.out.println("\n");
	    System.out.println(e + " (" + e.getClass().getName() + ")");
	    System.out.println("----");

	    System.out.println(indent + "simple name: " + e.getSimpleName());
	    // System.out.println(indent + e.hashCode​());
	    System.out.println(indent + "as type: " + e.asType​());

	    System.out.println(indent + "enc element: " + e.getEnclosingElement​());
	    System.out.println(indent + "kind: " + e.getKind​());

	    System.out.println(indent + "modifiers:");
	    for (Modifier m : e.getModifiers()) {
		System.out.println(indent + " - " + m);
	    }

	    System.out.println("DocTree:");
	    var doc = trees.getDocCommentTree(e);
	    if (doc == null) {
		System.out.println("null doctree...");
	    } else {
		System.out.println(doc.getFullBody());
	    }

	    // <A extends Annotation> A	getAnnotation​(Class<A> annotationType)
	    // List<? extends AnnotationMirror> getAnnotationMirrors​()
	    System.out.println(indent + "annotations:");
	    for (AnnotationMirror a : e.getAnnotationMirrors()) {
		System.out.println(indent + " @ " + a);
	    }
	    // List<? extends Element> getEnclosedElements​()

	    var v = new MVisitor(trees);
	    var m = new HashMap<String,List<String>>();
	    var r = v.visit(e, m);
	    var pre = "  --  ";
	    System.out.println("Doc Map:");
	    System.out.println("-------");
	    r.entrySet()
		.stream()
		.filter((d) -> { return (d.getValue().size() > 0); })
		.forEach((d) -> {
			var dk = d.getKey();
			var dv = d.getValue()
			    .stream()
			    .map((s) -> { return pre + s.replace("\n", "\n " + pre); })
			    .collect(Collectors.toList());
			System.out.println(" - " + dk + " ::: Comment\n" + dv + "\n");
		    });

	    // JSON output
	    //  {
	    //    comments: {
	    //      "${qualName}" : "${comment},
	    //      ...
	    //    }
	    //  }

	    Function<String,String> wrapped = (s) -> { return "\"" + s + "\""; };
	    // withOpenFile("fn", (file) -> {
	    //	    file.writeln("{");
	    //	    file.writeln("  comments: {");
	    //	    r.entrySet()
	    //		.stream()
	    //		.forEach((d) -> {
	    //			file.writeln("\"" + d.getKey() + "\"" + ":" + "\"" + d.getValue() + "\"");
	    //		    });
	    //	    file.writeln("  }");
	    //	    file.writeln("}");
	    //	});

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
