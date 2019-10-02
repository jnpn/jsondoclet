package jnpn.json;

public interface IResource<R> {
    public void open();
    public R close();
}
