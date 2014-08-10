package com.ldaniels528.robowars.objects.structures

/**
 * Represents the states of a Moving Door
 * @author lawrence.daniels@gmail.com
 */
object MovingDoorStates extends Enumeration {
  type DoorStates = Value
  val InitialState, StartMovingUp, MovingUp, Waiting, MovingDown = Value
}