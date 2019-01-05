## Samples for Hawkore's Apache Ignite connector stand-alone

This maven project contains sample source code for [Hawkore's Apache Ignite connector stand-alone
](https://docs.hawkore.com/private/apache-ignite-connector-standalone).

**IMPORTANT**: You must start Apache Ignite Servers before running this samples, as this application starts Apache Ignite as client node. See [README.md](../ignite-server-node-test/README.md) for more info.

### Steps for testing

- 1. Start as many client nodes as you want (change `nodeName` and `server.port` per client you start). Open a terminal per client and run:

```
mvn spring-boot:run -DnodeName=client1 -Dserver.port=8080
```

```
mvn spring-boot:run -DnodeName=client2 -Dserver.port=8081
```

- 2. Test operations with [Postman](https://www.getpostman.com/apps). Open Postman and send below requests (view code for more samples), change port to any started client's one:

	- Prepare some data:
		- Data ingestion into CACHE: GET http://localhost:8080/caches/ingestPois?cc=ES&count=100000&initialId=0
		- Data ingestion into QUEUE: GET http://localhost:8080/queues/ingestEntities?count=100000&initialId=0 (received data on queue will be stored into CACHE2 check how cache size is increased by: GET http://localhost:8080/caches/size?cache=CACHE2)
		
	- Some **cache** operations (you will see on client terminals traces):
		- Put entry: PUT http://localhost:8080/caches/put?key=1&value=theValue
		- Get entry: GET http://localhost:8080/caches/get?key=1
		- Delete entry: DELETE http://localhost:8080/caches/del?key=1
		- Cache size: GET http://localhost:8080/caches/size?cache=CACHE2
		- Clear cache: GET http://localhost:8080/caches/clear?cache=CACHE2

	- Some **queue** operations (you will see on client terminals traces):
		- publish message: POST http://localhost:8080/queues/publish?message=aMessage
		- publish-consume message: POST http://localhost:8080/queues/publishConsume?message=aMessage
		
	- Some **query** operations (you will see on client terminals traces):
	    - Get poi by id: GET http://localhost:8080/queries/pois/10?lang=en
	    - Delete poi by id: DELETE http://localhost:8080/queries/pois/10
		- Search pois by geo search (Advanced Lucene Query): GET http://localhost:8080/queries/pois/sqlGeoSearch?radius=20km&limit=30&lang=es
		- Search pois by text (SqlQuery): GET http://localhost:8080/queries/pois/sqlSearch?query=airport&limit=30&lang=en
		- Search pois by text (TextQuery): GET http://localhost:8080/queries/pois/textSearch?query=airport&limit=30&lang=en

	- Some **topic** operations (you will see on client terminals traces):
		- publish message to topic subscribers: POST http://localhost:8080/topics/publish?message=aMessage

Visit [Hawkore's home page](https://www.hawkore.com).