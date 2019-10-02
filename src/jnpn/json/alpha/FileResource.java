package jnpn.json.alpha;

import java.io.File;

/**
 * @class FileResource
 * @param x value of ....
 * @return result of ....
 */
public class FileResource implements IResource<File> {

    /**
     * @class JVisitor : JSON tree visitor for Doclets
     * @param x value of ....
     * @return result of ....
     */
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
