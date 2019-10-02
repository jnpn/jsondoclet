# jsondoclet-bis Makefile
#
# https://www.codejava.net/java-core/tools/using-jar-command-examples

VERSION=0.1b

JAR=jsondocletbis_$(VERSION).jar
LIB=libs
LIBS=$(LIB)/gson-2.8.5.jar:$(LIB)/jedis-3.1.0.jar
OUT=build
CP=$(LIBS):$(JAR):.

VERBOSE=-verbose

TOP=src/jnpn/json
SOURCE_DIRS=$(TOP)/testing/*.java $(TOP)/modelserializers/*.java $(TOP)/**/*.java

DOCLET=jnpn.json.JSONDoclet
TEST=jnpn.json.testing.Dummy

jar:	compile
	jar cfve $(JAR) jnpn.json.testing.Dummy -C $(OUT) jnpn/json
	jar ufvm $(JAR) manifest

run:	jar
	java -jar $(JAR)

cleanbuild:
	rm -rv build/jnpn/
	rm jars/$(JAR)

compile:	hi
	@javac -cp $(CP) -d $(OUT) $(shell ./scripts/packages.py) # $(SOURCE_DIRS) ## old version

compilev:	hi
	@javac $(VERBOSE) -cp $(CP) -d $(OUT) src/jnpn/json/*

test:	jar
	java -cp $(CP) -jar $(JAR) $(TEST)

hi:
	echo "make:jsondoclet"

doctest: jar
	javadoc -cp $(CP) -doclet $(DOCLET) -docletpath $(JAR) $(shell ./scripts/packages.py) # src/jnpn/json/*.java
