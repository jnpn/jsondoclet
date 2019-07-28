package jnpn.json;

import java.util.*;
import jdk.javadoc.doclet.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import javax.tools.Diagnostic.Kind;
import com.sun.source.util.*;
import com.sun.source.util.DocSourcePositions;

public class Docer {

    /**
     * formatAnnotation : formats the Annotation of a Construct
     * @param x value of ....
     * @return result of ....
     **/
    public String formatAnnotation(AnnotatedConstruct ac) {
	var sb = new StringBuilder();
	for (AnnotationMirror am : ac.getAnnotationMirrors()) {
	    sb.append("@");
	    sb.append(am.getElementValues());
	    sb.append("\n");
	}
	return sb.toString();
    }

    /**
     * shodDoc method that prints ..well this.
     **/
    public void showDoc(DocTrees trees, Element e, Indenter indenter) {
	var doc = trees.getDocCommentTree(e);
	if (doc == null) {
	    System.out.println(indenter + "no DocTree on " + e.getSimpleName());
	} else {
	    System.out.println(indenter + "---- Javadoc");
	    System.out.println(indenter + ", " + doc);
	    // System.out.println(indenter + "getFullBody -> " + doc.getFullBody());
	    // System.out.println(indenter + "getBlockTags: " + doc.getBlockTags());
	    // System.out.println(indenter + "getBody: " + doc.getBody());
	    // System.out.println(indenter + "getFirstSentence: " + doc.getFirstSentence());
	    // System.out.println(indenter + "getFullBody: [" + doc.getFullBody() + "]");
	    // System.out.println(indenter + "getPostamble: " + doc.getPostamble());
	    // System.out.println(indenter + "getPreamble: " + doc.getPreamble());
	}
    }

}
