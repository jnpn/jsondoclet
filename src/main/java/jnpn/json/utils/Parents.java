package jnpn.json.utils;

import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Parents {

    public static List<Element> ancestors(Element e) {
        var l = new ArrayList<Element>();
        var p = e.getEnclosingElement();
        while (p != null) {
            l.add(p);
            p = p.getEnclosingElement();
        }
        return l;
    }

    public static List<Element> lineage(Element e) {
        var l = new ArrayList<Element>();
        var p = e;
        while (p != null) {
            l.add(p);
            p = p.getEnclosingElement();
        }
        if (p != null) {
            l.add(p);
        } // @FIXME: hack
        Collections.reverse(l);
        return l;
    }

}
