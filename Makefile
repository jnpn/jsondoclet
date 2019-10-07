# jsondoclet-bis Makefile
#
# https://www.codejava.net/java-core/tools/using-jar-command-examples

VERSION=0.1b

JSON=json
JAR=jsondocletbis_$(VERSION).jar
LIB=libs
OUT=build
CP=$(LIBS):$(JAR):.

VERBOSE=-verbose

TOP=src/jnpn/json
SOURCE_DIRS=$(TOP)/testing/*.java $(TOP)/modelserializers/*.java $(TOP)/**/*.java

META=meta-inf
DOCLET=jnpn.json.JSONDoclet
TEST=jnpn.json.testing.Dummy

remanifest:
	$(shell ./scripts/manifest.py)
	@echo new manifest
	@cat $(META)/manifest-cp.mf

jar:	remanifest compile
	@echo '-- check manifest'
	@cat $(META)/manifest-cp.mf
	@cat $(META)/manifest-name.mf
	jar cfve $(JAR) $(TEST) -C $(OUT) jnpn/json
	jar ufvm $(JAR) meta-inf/manifest-cp.mf
	jar ufvm $(JAR) meta-inf/manifest-name.mf

run:	jar
	java -jar $(JAR)

cleanbuild:
	-rm -rv build/jnpn/
	-rm $(JAR)

compile:	hi
	javac -cp $(shell ./scripts/packages.py --libs libs) -d $(OUT) $(shell ./scripts/packages.py --sources src) # $(SOURCE_DIRS) ## old version	

compilev:	hi
	@javac $(VERBOSE) -cp $(CP) -d $(OUT) src/jnpn/json/*

test:	jar
	java -cp $(shell ./scripts/packages.py --libs libs) -jar $(JAR) $(TEST)

hi:
	@echo "make:jsondoclet"

doctest: jar
	javadoc -cp $(shell ./scripts/packages.py --libs libs) -doclet $(DOCLET) -docletpath $(shell ./scripts/packages.py --libs libs):build $(shell ./scripts/packages.py --sources src)

doctrees:	doctest
	cat $(JSON)/*.json | jq '.elements | .[].DocTree' | grep -v '""'


