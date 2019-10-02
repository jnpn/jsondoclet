package jnpn.json.visitors;

import java.util.*;
import jdk.javadoc.doclet.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import javax.tools.Diagnostic.Kind;
import com.sun.source.util.*;
import com.sun.source.util.DocSourcePositions;

import jnpn.json.utils.*;

/**
 * @class Mapped : JSON tree visitor for Doclets
 * @param x value of ....
 * @return result of ....
 */
public class Mapped implements ElementVisitor<Map<String,List<String>>,Map<String,List<String>>> {

    private Element root;
    private DocTrees trees;
    private Docer docer;
    private Map<String,List<String>> m;

    public Mapped(DocTrees trees) {
	this.trees = trees;
	this.docer = new Docer();
	this.m = new HashMap<>();
    }

    //element ~ Represents a program element such as a module, package, class, or method.
    /**
     * map -> map (stateful)
     **/
    public Map<String, List<String>> visit(Element e, Map<String, List<String>> m) {
	root = e;
	m.put(e.toString(), docer.getDoc(trees, e));
	e.accept(this, m);
	return m;
    }

    //:leaf
    //+ExecutableElement : method | constructor | initializer (static or instance)
    // of a class or interface, including annotation type elements.
    public Map<String, List<String>> visitExecutable(ExecutableElement e, Map<String, List<String>> m) {
	m.put(e.toString(), docer.getDoc(trees, e));
	return m;
    }

    //:node
    //+Package
    public Map<String, List<String>> visitPackage(PackageElement e, Map<String, List<String>> m) {
	m.put(e.toString(), docer.getDoc(trees, e));
	e.getEnclosedElements()
	    .stream()
	    .forEach((ee) -> { ee.accept(this, m); }); // stream idiom
	return m;
    }

    //:node
    //+Class | Interface
    public Map<String, List<String>> visitType(TypeElement e, Map<String, List<String>> m) {
	m.put(e.toString(), docer.getDoc(trees, e));
	for (Element ee : e.getEnclosedElements()) { ee.accept(this, m); }
	return m;
    }

    //:node
    //+type parameter of a generic class, interface, method, or constructor
    public Map<String, List<String>> visitTypeParameter(TypeParameterElement e, Map<String, List<String>> m) {
	m.put(e.toString(), docer.getDoc(trees, e));
	for (Element ee : e.getEnclosedElements()) { ee.accept(this, m); }
	return m;
    }

    //:node
    public Map<String, List<String>> visitUnknown(Element e, Map<String, List<String>> m) {
	System.out.println("[unknown] " + e);
	return m;
    }

    //:node
    //+ field
    //| enum constant
    //| method or constructor parameter
    //| local variable
    //| resource variable
    //| exception parameter
    public Map<String, List<String>> visitVariable(VariableElement e, Map<String, List<String>> m) {
	m.put(e.toString(), docer.getDoc(trees, e));
	for (Element ee : e.getEnclosedElements()) { ee.accept(this, m); }
	return m;
    }

}