package jnpn.json;

import java.util.function.*;

class FileResource implements IResource<File> {
    private File f;
    public FileResource(File f) {
	this.f = f;
    }

    public void open() {
	this.f.open();
    }

    public File close() {
	this.f.close();
	return this.f;
    }
}

/**
 * mimicking python Contexts
 * With <object> as <name>: <block>
 * ==
 * (<name> -> <block>.apply(<name>)).apply(<object>)
 *
 **/
public class <R,T> With {
    private IResource<R> r;
    private Function<Resource<R>, T> f;

    public With(IResource<R> r, Function<Resource<R>, T> f) {
	this.r = r;
	this.f = f;
    }

    public apply() {
	this.r.open();
	var t = f.apply(q);
	var s = q.close();
	return t;
    }

}
