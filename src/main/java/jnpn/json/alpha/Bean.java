package jnpn.json.alpha;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * classe Bean
 *
 * @class Bean
 */
@JsonPropertyOrder({"name", "city"})
public class Bean {
    //@JsonValue
    String name = "john";

    //@JsonValue
    String city = "london";

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String n) {
        city = n;
    }
}
