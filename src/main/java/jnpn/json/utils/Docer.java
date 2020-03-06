package jnpn.json.utils;

import com.sun.source.util.DocTrees;

import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import java.util.List;

public class Docer {

    /**
     * formatAnnotation : formats the Annotation of a Construct
     *
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

    public List<String> getDoc(DocTrees trees, Element e) {
        var doc = trees.getDocCommentTree(e);
        if (doc == null) {
            return List.of();
        } else {
            return List.of(doc.toString());
        }
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
        }
    }

}
