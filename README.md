# RoboWars

A 3D combat game written in Scala.

### Objective

The objective is to create a free JVM-based MMO, where players can build and customize their machines
and battle it out in a 3D battle arena.

### Status

The game is up and running in single-player mode with sound effects. The next step is add the network code to 
support multiple players to competing in battle arenas.


### Features

* Fast-paced 3D action
* Single-player mode
* Multi-player mode (**Coming soon**)
* Spectator mode (**Coming soon**)
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
