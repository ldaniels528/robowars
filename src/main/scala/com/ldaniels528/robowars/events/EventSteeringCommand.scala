package com.ldaniels528.robowars.events

import com.ldaniels528.fxcore3d.FxEvent

/**
 * Represents a vehicle steering command/event
 * @author lawrence.daniels@gmail.com
 */
class EventSteeringCommand(
  val time: Double,
  val command: Int,
  val factor: Double,
  val dt: Double) extends FxEvent(time)

/**
 * EventSteeringCommand Companion Object
 * @author lawrence.daniels@gmail.com
 */
object EventSteeringCommand {
  val TURN_LEFT = 1;
  val TURN_RIGHT = 2;
  val INCREASE_VELOCITY = 3;
  val BRAKE = 4;
  val CLIMB = 5;
  val DECENT = 6;
  val PITCH_UP = 7;
  val PITCH_DOWN = 8;
  val DECREASE_VELOCITY = 9;
}