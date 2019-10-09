# jsondoclet(beta)

	JSON backend prototype for javadoc (Doclet 9 API).

	doclet generating json formatted javadoc

# dataflow

	doctree -> Tree[javax.lang.model.Element] -> json tree

# TODO

  - actual JSON ouput (gson | jackson | custom)
  - format specs
	define a clear mapping from POJO metaclasses to Json Objects
  - tests
	obviously
  - configuration file :DONE
	use java Properties to load configuration from config.properties jar resource
  - overridable configuration file
  - configurable pojo : json mapping
	metamodel description to json object

	```
		Element.name -> [name]
		...
	```
  - Redis endpoint
	LSP oriented javadoc server, so your IDE can query documentation

# CLI                                                                   :wip:

  - jsondoclet generate <source-root>
  - jsondoclet serve <source-code> <port:8081>
