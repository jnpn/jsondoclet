# jsondoclet-bis Makefile
#
# https://www.codejava.net/java-core/tools/using-jar-command-examples

JAR=jsondocletbis.jar
JARS=jars/gson-2.8.5.jar:jars/jedis-3.1.0.jar:$(JAR):.

jar:	compile
	jar cfve $(JAR) jnpn.json.JVTest -C build jnpn/json
	# following line is the good idea
	# but doesn't work
	# updating the .jar manifest by hand to add class-path fix the bug
	jar ufvm $(JAR) manifest

run:	jar
	java -jar $(JAR)

cleanbuild:
	rm $(JAR)
	rm -rv build/jnpn/json/

compile:	hi
	javac -cp $(JARS) -d build src/jnpn/json/modelserializers/*.java src/jnpn/json/*.java

compilev:	hi
	javac -verbose -cp build -d build src/jnpn/json/*

test:	compile
	java -cp $(JARS) jnpn.json.JVTest

hi:
	echo "make:jsondoclet"

doctest: jar
	javadoc -cp $(JARS) -doclet jnpn.json.JSON -docletpath $(JAR) src/jnpn/json/*.java
