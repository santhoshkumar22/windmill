# windmill
This is windmill farm management system.
Runing in port:7080

Exposed API,
1. HTTP POST - /api/windmill/register - To register the new windmill
sample input: {"uniqueId":"string","name":"string","address":"string","location":{"latitude":"string","longitude":"string"}}
2. HTTP GET - /api/windmill/getWindmill/{uid} - To get the registered windmill details
3. HTTP GET - /api/windmill/getHistoricalData/{uid} - To get the historical data of the particular windmill to display in chart

Scheduler:
WindMillFarmScheduler.java

A scheduler will run every 5 minutes which will read the power generated by every windmill in "Input.txt" file placed in src/main/resource/scheduler folder and once application started can be edit the values in classpath:\target\classes\scheduler folder path.
Once the values are processed the file will be flushed.

Format for input in file: {uniqueId}:{power}
Sample input: WINDMILL00000001:12456

The delay in running the scheduler can be configured in application.properties file "scheduler.fixedDelay.milliseconds"

H2 Database is used in this application.
Use below information to connect to DB console:-
Url: http://localhost:7080/h2/
JDBC  Url: jdbc:h2:mem:testdb
User Name: sa
password: password
