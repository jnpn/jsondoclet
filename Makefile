# jsondoclet-bis Makefile
#
# https://www.codejava.net/java-core/tools/using-jar-command-examples

jar:	compile
	jar -cfve jsondocletbis.jar jnpn.json.JVTest -C build jnpn/json

run:	jar
	java -jar jsondocletbis.jar

cleanbuild:
	rm -v build/jnpn/json/*.class

compile:	hi
	javac -cp build -d build src/jnpn/json/*

compilev:	hi
	javac -verbose -cp build -d build src/jnpn/json/*

test:	build
	java -cp build jnpn.json.JVTest

hi:
	echo "make:jsondoclet"

doctest: jar
	javadoc -cp jsondocletbis.jar -doclet jnpn.json.JSON -docletpath jsondocletbis.jar src/jnpn/json/*.java
