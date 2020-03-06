package jnpn.json.alpha;

public interface IResource<R> {
    void open();

    R close();
}
