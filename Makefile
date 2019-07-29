# jsondoclet-bis Makefile
#
# https://www.codejava.net/java-core/tools/using-jar-command-examples

PROJET=jsondocletbis
VERSION=0.1a
JARDIR=jars/
BDIR=build
SDIR=src
PACKAGE=jnpn.json

jar:	compile jardir
	jar -cfve jars/$(PROJET)_$(VERSION).jar $(PACKAGE).JVTest -C $(BDIR) jnpn/json

jardir:
	mkdir -pv $(JARDIR)

run:	jar
	java -jar $(JARDIR)$(PROJET)_$(VERSION).jar

cleanbuild:
	rm -v $(BDIR)/jnpn/json/*.class

compile:	prelude
	javac -cp $(BDIR) -d $(BDIR) $(SDIR)/jnpn/json/*

compilev:	prelude
	javac -verbose -cp $(BDIR) -d $(BDIR) $(SDIR)/jnpn/json/*

test:	compile
	java -cp $(BDIR) $(PACKAGE).JVTest

prelude:
	@echo ""
	@echo "make | jsondoclet (c) jnpn"
	@echo ""

doctest: jar
	javadoc -cp $(JARDIR)$(PROJET)_$(VERSION).jar -doclet $(PACKAGE).JSON -docletpath $(JARDIR)$(PROJET)_$(VERSION).jar $(SDIR)/jnpn/json/*.java
