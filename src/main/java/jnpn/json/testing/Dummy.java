package jnpn.json.testing;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jnpn.json.alpha.Bean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	System.out.println("gson");
	var g = new GsonBuilder().setPrettyPrinting().create();
	var j = g.toJson(List.of(1,2,3));
	System.out.println(j);

	System.out.println("jackson");

	try {
	    var b = new Bean();
	    System.out.println("pojo -> " + b);
	    var s = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(b);
	    System.out.println("json -> " + s);
	}
	catch (JsonProcessingException e) {
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	}

	System.out.println("bye");
    }
}
