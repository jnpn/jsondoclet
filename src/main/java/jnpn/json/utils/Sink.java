package jnpn.json.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import static java.lang.System.*;

public class Sink implements ISink {

    String dirname;

    public Sink() {
        this.dirname = "json";
    }

    public Sink(String dirname) {
        this.dirname = dirname;
    }

    private File ensureDir() {
        var d = Paths.get(dirname).toFile();
        if (!d.exists()) {
            err.println("Created: " + d);
            var b = d.mkdir();
        }
        return d;
    }

    public boolean save(String name, String json) {
        var d = ensureDir();
        var fn = new File(d, name + ".json");
        try (Writer writer = new BufferedWriter
                (new OutputStreamWriter
                        (new FileOutputStream(fn), StandardCharsets.UTF_8))) {
            err.println("[info] saving to: " + fn);
            writer.write(json.toString());
            return true;
        } catch (IOException e) {
            err.println("Error " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public String toString() {
        return "Default Sink(dir:" + dirname + ").";
    }

}
