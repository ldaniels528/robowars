# RoboWars

A 3D combat game written in Scala.

### Objective

The objective is to create a free JVM-based MMO, where players can build and customize their machines
and battle it out in a 3D battle arena.

### Status

The basic 3D game engine is up and running. The next step is add the network code to allow multiple players
to compete in battle arenas. 

* Fast-paced 3D action
* Single-player mode
* Multi-player mode (**Coming soon**)
* Basic AI combatants (**Needs improvement**)
* Sound effects

### Build Requirements

* Scala 2.11+
* SBT 0.13+

### Getting the code

    $ git clone git@github.com:ldaniels528/robowars.git

### Building the code

    $ sbt clean package
      
### Running the tests

    $ sbt test    
    
### Run the application

	$ java -jar robowars.jar

### How to play

* Cursor Left/Right to Navigate
* Cursor Up/Down to Pitch up/down
* CTRL key to accelerate
* ATL key to decelerate/reverse
* Shift key to fire weapon
* 'Z' key to switch between weapons
