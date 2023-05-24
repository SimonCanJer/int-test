# Inituit Challenge

### Given
   
- create microservices, which reads players  data from an .csv file  and retuns the readen data upon REST request.
- The common request method is GET and common sub uri is /api
- All readen data are returned by means of get request for the url /api/players. 
- A particular player data is returned way get request from the url /apli/players/{playerId}
- Data are contained in an CSV file, which is applied.

### Implementation in accordance to requirements.
-  Data are readen from the CSV file and in current implementation are kept in in memory map, because files are not large at this moment and  the data can be kept in the JVM memory. Database+ cache will be needed for large volume of data, that can be achieved by replacing implementatiion of the IDao interface.
-  Data are returned by a controller class com.intuit.test.rest.spring.RestEndPoint. 
-  The /api/players GET request should have 200 reponse code. Alternativly the INTERNAL_SERVER_ERROR returned.
-  The /api/player/{playerId} returns a player only when it is found and in thes case response code is 200. Otherwise the ERROR_NOT_FOUND returned response code .
-  The port by default is 10101. It can be changed by means of the server.port property and ENV_SERVER_PORT environhment variable from outside
-  The field playerID in the CSV is considered as identifier for retrieve operation


####  Structure
- The application has the main package is com.intuit.test
- the application has the following layers:
- - model (data model of the application)
- - rest   (rest layer)
- - source_read (source reader)
-   model has the sublayers
-  - dao (controls data access)
-  - dao.api (exposes common dao interface)
-  - dao.in_mem (represents in memeory implementation of DAO)
-  - dao.in_mem.config.spring (pure Spring configuration)
-  rest layer currently has the only sublayer:
-  - spring (Spring controller and xecurity configuration.
-  The system configuration is managed by Spring and properties/environment variables, which point to @Configuration classes of Spring. The structure provides to configure application in run time, depending current needs.
-  The business value of a layer is decreasing in accordance to related package depth. Low level packages are of  infrastructure leve.
-  Location of file is defined by properties and environment variable ENV_DATA_SOURCE_FILE, which really shoud refer to a file location in a volume (of Kubernetes, for instance). In real case, we'll need monitor in side the application and file reload.
-  properties of the application are defined in the application.properties file. Key properties  are controller by an environment variables.


##    Undefined important topics.
- Security. Currently api is configured to be a free for access. Regarding business logic of the  case, it is for public use. However, secuity can be set to up by changing security porvider.
- Data size.   Currently there is no needs to use external DB for this data. The small amount of data promisess fastest access. Database -  cache would be source of additional resources spent (as cache and database replication, backups).  However, there can be a  situation, when DB will be needed for  huge data together with cache.
- In the case of big amount of data, the /api/players GET request must contain pagination parameters, because all data could have a huge size.
- SLI/PKI metrics must be defined. I have added a custom metrics for all players and concrete player retrieve counters, but we should define others (as timing)

##        Large amount of data case:

###    I  belive that
- CSV Files can be used but together with external queries as Snowflake or BigQuery, which can deal with files system(for huge data).
- For big amounts of data, the best model is: a scheduler service, which is updated with new versions of file, sends updates to service way of message queue( as Kafka). The data are inserted/removed to a K-V or relational  database. Removed entries must be  removed from cache also.

##     Project reorganization 
- really project must be divided by modules, where all the buisness part (as interfaces, entities) must be in separated modules.
- Spring inherent staff must be in modules, which depends on business layers.

##      Resources required for scalable, load ballanced and independent microservide implementation and test:
4 days.

##               Important remarks.
-   Because data storage type is not defined for a concrete case, and I consider PERSISTED Hazelcast (in memory server ) for large amounty of instances, jPA/Hibernate dependenies are still in the project upon discussed



