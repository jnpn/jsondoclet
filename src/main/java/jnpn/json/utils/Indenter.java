package jnpn.json.utils;

public class Indenter {
    private int level = 0;
    private int step = 2;
    private char prefix = ' ';

    public Indenter() {
    }

    public Indenter(int level, int step, char prefix) {
        this.level = level;
        this.prefix = prefix;
        this.step = step;
    }

    public void inc() {
        level += step;
    }

    public void dec() {
        if (level <= step) {
            level = 0;
        } else {
            level -= step;
        }
    }

    public String toString() {
        var sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append(prefix);
        }
        return sb.toString();
    }
}
