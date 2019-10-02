package jnpn.json.testing;

import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Dummy: jar main class
 *  - prints a prologue and a json array [1,2,3]
 *  - used to test classpath issues mostly
 *
 * @class Dummy: jar main class to test classpath issues
 *
 **/
public class Dummy {
    public static void main(String[] args) {
	// var v = new JVisitor();
	System.out.println("jsondoclet-bis (c) jnpn 2019-");
	var g = new GsonBuilder().setPrettyPrinting().create();
	var j = g.toJson(List.of(1,2,3));
	System.out.println(j);
    }
}