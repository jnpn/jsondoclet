package jnpn.json.utils;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import javax.lang.model.element.Element;

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
	if (p != null) { l.add(p); } // @FIXME: hack
	Collections.reverse(l);
	return l;
    }

}
