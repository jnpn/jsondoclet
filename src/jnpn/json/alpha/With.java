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
     * field r: resource
     **/
    private IResource<R> r;

    /**
     * field f: thunk to apply with resource accessible
     **/
    private Function<IResource<R>, T> f;


    /**
     * main constructor (IResource, IResource -> T)
     **/
    public With(IResource<R> r, Function<IResource<R>, T> f) {
	this.r = r;
	this.f = f;
    }

    /**
     * main method apply:
     *  - open resource
     *  - apply thunk yield r
     *  - close resource
     **/
    public T apply() {
	r.open();
	var t = f.apply(r);
	var s = r.close();
	return t;
    }

}
