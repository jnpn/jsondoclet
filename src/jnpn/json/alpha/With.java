package jnpn.json.alpha;

import java.util.function.*;

/**
 * mimicking python Contexts
 * With <object> as <name>: <block>
 * ==
 * (<name> -> <block>.apply(<name>)).apply(<object>)
 *
 **/
public class With<R,T> {

/**
 * mimicking python Contexts
 * With <object> as <name>: <block>
 * ==
 * (<name> -> <block>.apply(<name>)).apply(<object>)
 *
 **/
    private IResource<R> r;

/**
 * mimicking python Contexts
 * With <object> as <name>: <block>
 * ==
 * (<name> -> <block>.apply(<name>)).apply(<object>)
 *
 **/
    private Function<IResource<R>, T> f;


/**
 * mimicking python Contexts
 * With <object> as <name>: <block>
 * ==
 * (<name> -> <block>.apply(<name>)).apply(<object>)
 *
 **/
    public With(IResource<R> r, Function<IResource<R>, T> f) {
	this.r = r;
	this.f = f;
    }

    public T apply() {
	r.open();
	var t = f.apply(r);
	var s = r.close();
	return t;
    }

}
