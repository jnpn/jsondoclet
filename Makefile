# jsondoclet-bis Makefile

pack:	build
	jar -cfve jsondocletbis.jar jnpn.json.JVTest -C build jnpn/json

run:	pack
	java -jar jsondocletbis.jar

cleanbuild:
	rm -v build/jnpn/json/*.class

build:	hi
	javac -cp build -d build src/jnpn/json/*

buildv:
	javac -verbose -cp build -d build src/jnpn/json/*

test:	build
	java -cp build jnpn.json.JVTest

hi:
	echo "make:jsondoclet"
