# jsondoclet-bis Makefile
#
# https://www.codejava.net/java-core/tools/using-jar-command-examples

JAR=jsondocletbis.jar
JARS=jars/gson-2.8.5.jar:jars/jedis-3.1.0.jar:$(JAR):.
TOP=src/jnpn/json
SOURCE_DIRS=$(TOP)/testing/*.java $(TOP)/modelserializers/*.java $(TOP)/**/*.java

jar:	compile
	jar cfve $(JAR) jnpn.json.testing.Dummy -C build jnpn/json
	# following line is the good idea
	# but doesn't work
	# updating the .jar manifest by hand to add class-path fix the bug
	jar ufvm $(JAR) manifest

run:	jar
	java -jar $(JAR)

cleanbuild:
	rm -rv build/jnpn/
	rm $(JAR)

compile:	hi
	echo SOURCE_DIRS: $(SOURCE_DIRS)
	#	javac -cp $(JARS) -d build $(shell ./packages.py) # $(SOURCE_DIRS)
	javac -cp $(JARS) -d build $(SOURCE_DIRS)

compilev:	hi
	javac -verbose -cp build -d build src/jnpn/json/*

test:	jar
	java -cp $(JARS):build:. -jar $(JAR) jnpn.json.testing.Dummy

hi:
	echo "make:jsondoclet"

doctest: jar
	javadoc -cp $(JARS) -doclet jnpn.json.JsonDoclet -docletpath $(JAR) src/jnpn/json/*.java
