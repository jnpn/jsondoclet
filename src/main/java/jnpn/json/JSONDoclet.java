package jnpn.json;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;
import jnpn.json.utils.Parents;
import jnpn.json.utils.Sink;
import jnpn.json.visitors.Json;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic.Kind;
import java.util.HashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

public class JSONDoclet implements Doclet {

    private Reporter reporter;
    private Locale locale;
    private Properties props;
    private String configFile = "/custom/config.properties";

    @Override
    public String getName() {
        return "jnpn.JSON:Doclet";
    }

    @Override
    public Set<? extends Doclet.Option> getSupportedOptions() {
        return new HashSet<Doclet.Option>();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_9;
    }

    @Override
    public boolean run(DocletEnvironment environment) {

        reporter.print(Kind.NOTE, "---- JSON.java Doclet [JDK9 API] ----");

        showEnvironment(environment);

        var trees = environment.getDocTrees();
        if (trees == null) {
            System.err.println("[WARNING] NO TREES");
            return false;
        }

        var elems = environment.getSpecifiedElements();
        var out = props.getProperty("config.outputdir", "json");
        System.out.println("using config " + out);
        var sink = new Sink(out);

        reporter.print(Kind.NOTE, "using sink: " + sink);

        for (Element e : elems) {
            var res = new Json(trees).visit(e, null);
            var fullname = Parents.lineage(e)
                    .stream()
                    .map((el) -> el.getSimpleName().toString())
                    .reduce("doc", (a, b) -> a + ":" + b);
            var saved = sink.save(fullname, res.toString());
            if (saved) {
                System.err.println("[info] done");
                // reporter.print(Kind.OTHER, e, "done.");
            } else {
                System.err.println("[info] failed to save...");
                // reporter.print(Kind.WARNING, e, "done.");
            }
        }

        return true;
    }

    private boolean showEnvironment(DocletEnvironment environment) {
        System.out.println("[info] " + environment);
        System.out.println("[info] " + environment.getDocTrees());    // Returns an instance of the DocTrees utility class.
        System.out.println("[info] " + " - included: " + environment.getIncludedElements());    // Returns the module, package and type elements that should be included in the documentation.
        System.out.println("[info] " + environment.getModuleMode());    // Returns the required level of module documentation.
        System.out.println("[info] " + environment.getSourceVersion());    // Returns the source version of the source files that were read.
        System.out.println("[info] " + " - elements: " + environment.getSpecifiedElements());    // Returns the elements specified when the tool is invoked.
        return true;
    }

    @Override
    public void init(Locale locale, Reporter reporter) {
        this.reporter = reporter;
        this.locale = locale;
        System.out.println("[init] " + "doclet: " + getName());
        System.out.println("[init] " + "locale: " + locale);
        System.out.println("[init] " + "reporter: " + reporter);
        reporter.print(Kind.NOTE, "> " + "...");

        props = conf(configFile);

    }

    private Properties conf(String filename) {

        Properties p = new Properties();
        try {
            var s = this.getClass().getResourceAsStream(filename);
            if (s != null) {
                p.load(s);
                System.err.println("[info] properties: " + p);
            } else {
                System.err.println("[error] path " + filename + " not found.");
            }
        } catch (Throwable e) {
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
        }
        return p;

    }

}
