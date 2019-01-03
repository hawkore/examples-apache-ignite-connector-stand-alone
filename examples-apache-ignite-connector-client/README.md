## Samples for Hawkore's Apache Ignite connector stand-alone

This maven project contains sample source code for [Hawkore's Apache Ignite connector stand-alone
](https://docs.hawkore.com/private/apache-ignite-connector-standalone).

**IMPORTANT**: You must start Apache Ignite Servers before running this samples. See [README.md](../ignite-server-node-test/README.md) for more info.

### Steps for testing

- 1. Start as many client nodes as you want (change `nodeName` and `server.port` per client you start). Open a terminal per client and run:

```
mvn spring-boot:run -DnodeName=client1 -Dserver.port=8080
```

```
mvn spring-boot:run -DnodeName=client2 -Dserver.port=8081
```

- 2. Test operations with [Postman](https://www.getpostman.com/apps). Open Postman and make requests (view code for more samples), change port to any started client's one:

	- Some cache operations (you will see on client terminals traces):
		- Data ingestion: GET http://localhost:8080/caches/ingestPois?cc=ES&count=100000&initialId=0
		- Put entry: PUT http://localhost:8080/caches/put?key=1&value=theValue
		- Get entry: GET http://localhost:8080/caches/get?key=1
		- Delete entry: DELETE http://localhost:8080/caches/del?key=1
		- Cache size: GET http://localhost:8080/caches/size?cache=CACHE2

	- Some query operations (you will see on client terminals traces):
		- Geo search: GET http://localhost:8080/queries/pois/sqlGeoSearch?radius=20km&limit=30&lang=es
		- Text search: GET http://localhost:8080/queries/pois/textSearch?query=airport&limit=30&lang=en

	- Some queue operations (you will see on client terminals traces):
		- publish message: POST http://localhost:8080/queues/publish?message=aMessage
		- publish-consume message: POST http://localhost:8080/queues/publishConsume?message=aMessage
		- Data ingestion: GET http://localhost:8080/queues/ingestEntities?count=100000&initialId=0 (check how cache size is increased by GET http://localhost:8080/caches/size?cache=CACHE2)


	- Some topic operations (you will see on client terminals traces):
		- publish message: POST http://localhost:8080/topics/publish?message=aMessage

Visit [Hawkore's home page](https://www.hawkore.com).