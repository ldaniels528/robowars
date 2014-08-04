# RoboWars

A 3D combat game written in Scala.

### Objective

The objective is to create a free JVM-based MMO, where players can build and customize their machines
and battle it out in a 3D battle arena.

### Status

The basic 3D game engine is up and running. The next step is add the network code to allow multiple players
to compete in battle arenas. 

### Build Requirements

* Scala 2.11+
* SBT 0.13+

### Building the code

    $ sbt clean package
      
### Running the tests

    $ sbt test    
    
### Run the application

	$ java -jar robowars.jar
