# LogTrackerApp

Requirements:-
Our custom-build server logs different events to a file. Every event has 2 entries in a log - one entry when the event was started and another when the event was finished. The entries in a log file have no specific order (it can occur that a specific event is logged before the event starts) Every line in the file is a JSON object containing event data: id - the unique event identifier state - whether the event was started or finished (can have values "STARTED" or "FINISHED" timestamp - the timestamp of the event in milliseconds Application Server logs also have the additional attributes: type - type of log host - hostname Example:

In the example above, the event scsmbstgrb duration is 1401377495216 - 1491377495213 = 3ms The longest event is scsmbstgrc (1491377495218 - 1491377495210 = 8ms)

Solution:-

This Application Program:- 1.) Takes the input file path as input argument. 2.) Flags any long events that take longer than 4ms with a column in the database called "alert" by writing the event details to file-based HSQLDB (http://hsqldb.org/) in the working folder. 3.)The application creates a new table and enters the following values of Event id and Event Duration(Span) 4.)Marks "alert" as true for event duration of more than 4ms.

This application is created using Java 8 and Spring.

Before running the application, start the HSQLDB server by traversing till bin of hsql-db folder and running the runManagerSwing batch file.

Once started, type the command:- java -classpath lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:hsqldb/demodb --dbname.0 testdb and start the hsql db server.

Then run the application.
