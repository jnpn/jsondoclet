package jnpn.json;

import jnpn.json.alpha.AClass;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ATest {

    @Test
    public void test_foo() {
	assertEquals("xyz", new AClass().foo());
    }

    @Test
    public void test_bar() {
	assertEquals("uvw", new AClass().bar());
    }
    
}
