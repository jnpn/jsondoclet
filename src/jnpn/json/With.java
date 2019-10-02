package jnpn.json;

import java.io.File;
import java.util.function.*;

class FileResource implements IResource<File> {
    private File f;
    public FileResource(File f) {
	this.f = f;
    }

    public void open() {
	// this.f.open();
	return;
    }

    public File close() {
	// this.f.close();
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
public class With<R,T> {
    private IResource<R> r;
    private Function<IResource<R>, T> f;

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
