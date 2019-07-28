package jnpn.json;

import java.util.*;
import jdk.javadoc.doclet.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import javax.tools.Diagnostic.Kind;
import com.sun.source.util.*;
import com.sun.source.util.DocSourcePositions;

/**
 * @class JVisitor : JSON tree visitor for Doclets
 * @param x value of ....
 * @return result of ....
 */
public class JVisitor implements ElementVisitor<Void,Void> {

    /**
     * Enclosed Element count.
     **/
    private int eeCount = 0;

    /**
     * Type Element Count.
     **/
    private int teCount = 0;

    private Element root;
    private DocTrees trees;
    private Indenter indenter;
    private Docer docer;

    public JVisitor(DocTrees trees) {
	this.trees = trees;
	this.indenter = new Indenter();
	this.docer = new Docer();
    }

    // Void visit(Element e) {}
    // Void visitModule(ModuleElement e, Void p) {}

    public Void visit(Element e, Void p) {
	root = e;
	System.out.println("visit: " + e);
	System.out.println(" annotations?");
	docer.formatAnnotation(e);
	docer.showDoc(trees, e, indenter);
	e.accept(this, null);
	System.out.println("Summary:");
	System.out.println("------- ");
	System.out.println("for " + root);
	System.out.println("enclosed elements total: " + eeCount);
	System.out.println("type elements total: " + teCount);
	System.out.println("done.");
	return null;
    }

    public Void visitExecutable(ExecutableElement e, Void p) {
	System.out.println("visitExecutable");
	System.out.println(indenter.toString() + e.getSimpleName() + " :: " + e.getReturnType());
	docer.showDoc(trees, e, indenter);
	return null;
    }

    public Void visitPackage(PackageElement e, Void p) {
	System.out.println("visitPackage");
	docer.showDoc(trees, e, indenter);
	indenter.inc();
	for (Element ee : e.getEnclosedElements()) {
	    System.out.println(indenter.toString() + "[" + e.getSimpleName() + "] " + ee);
	    eeCount++;
	    ee.accept(this, null);
	}
	indenter.dec();
	return null;
    }

    public Void visitType(TypeElement e, Void p) {
	System.out.println("visitType");
	docer.showDoc(trees, e, indenter);
	indenter.inc();
	for (Element ee : e.getEnclosedElements()) {
	    System.out.println(indenter.toString() + e.getSimpleName() + "." + ee);
	    teCount++;
	    ee.accept(this, null);
	}
	indenter.dec();
	return null;
    }

    public Void visitTypeParameter(TypeParameterElement e, Void p) {
	System.out.println("visitTypeParameter");
	docer.showDoc(trees, e, indenter);
	indenter.inc();
	for (Element ee : e.getEnclosedElements()) {
	    System.out.println(indenter.toString() + e.getSimpleName() + "." + ee);
	    ee.accept(this, null);
	}
	indenter.dec();
	return null;
    }

    public Void visitUnknown(Element e, Void p) {
	System.out.println("visitUnknown");
	docer.showDoc(trees, e, indenter);
	indenter.inc();
	for (Element ee : e.getEnclosedElements()) {
	    System.out.println(indenter.toString() + e.getSimpleName() + "." + ee);
	    ee.accept(this, null);
	}
	indenter.dec();
	return null;
    }

    public Void visitVariable(VariableElement e, Void p) {
	System.out.println("visitVariable");
	docer.showDoc(trees, e, indenter);
	indenter.inc();
	for (Element ee : e.getEnclosedElements()) {
	    System.out.println(indenter.toString() + e.getSimpleName() + "." + ee);
	    ee.accept(this, null);
	}
	indenter.dec();
	return null;
    }

}
