# Viewing the data in the datastores #

One of the benefits of a persistence architecture is that the persisted data can be examined using tools that make querying the data relatively easy.For example, a relational database will support SQL queries. Persistence is not just from an OO language to a datastore - it also provides a means whereby the OO object graph can be populated with existing data in a persistent data store.

## Apache Derby

Apache Derby offers the `ij` command line tool to interact with a database.  A typical session follows.

	cd /path/to/jpa-examples
	ij
	connect 'jdbc:derby:testD';
	select * from je.family;
	select * from je.person;
	exit;

## Neo4j

Neo4j is a graph database. Unlike Apache Derby, it seems that a server process needs to be running . Before starting the server process it should be configured to use the `testN` database. For example, by editing `$NEO4J_HOME/conf/neo4j-server.properties` and changing the `org.neo4j.server.database.location` property to `myGraph.db`, we can use symbolic links to change where the server process looks for the database:

	ln -fsn /path/to/jpa-examples/testN $NEO4J_HOME/myGraph.db

To start the neo4j-service

	sudo service neo4j-service start

If that does not work, open a terminal and try the following

	sudo /var/lib/neo4j/bin/neo4j console

Open a browser window and point to <http://localhost:7474/webadmin/#/console/> and issue the following `cypher` command in <http://localhost:7474/webadmin/#/console/shell>

	START n=node\(\*\) RETURN n;

Equivalently issue the following `gremlin` commands in <http://localhost:7474/webadmin/#/console/gremlin>

	g.V.map
	g.E

