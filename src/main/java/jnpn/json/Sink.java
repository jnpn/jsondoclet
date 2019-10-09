package jnpn.json;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;

import java.nio.file.Paths;

public class Sink implements ISink {

    String dirname;

    public Sink () { this.dirname = "json"; }
    public Sink (String dirname) { this.dirname = dirname; }

    private File ensureDir() {
	var d = Paths.get(dirname).toFile();
	if (!d.exists()) {
	    System.err.println("Created: " + d);
	    d.mkdir();
	}
	return d;
    }

    public boolean save(String name, String json) {
	var d = ensureDir();
	var fn = new File(d, name + ".json");
	try (Writer writer = new BufferedWriter
	     (new OutputStreamWriter
	      (new FileOutputStream(fn), "utf-8"))) {
	    writer.write(json.toString());
	    return true;
	}
	catch (IOException e) {
	    System.err.println("Error " + e.getMessage());
	    e.printStackTrace();
	} finally {
	    return false;
	}
    }

}
