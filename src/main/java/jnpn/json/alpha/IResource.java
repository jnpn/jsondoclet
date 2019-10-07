package jnpn.json.alpha;

public interface IResource<R> {
    public void open();
    public R close();
}
