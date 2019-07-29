package jnpn.json;

public interface <R> Resource<R> {
    public void open<R>(R r);
    public R close();
}
