package com.ldaniels528.robowars.events

/**
 * Game Event Constants
 * @author lawrence.daniels@gmail.com
 */
trait Events {
  // motion events
  val GOTO_POSITION = 0

  // steering events
  val TURN_LEFT = 1
  val TURN_RIGHT = 2
  val INCREASE_VELOCITY = 3
  val BRAKE = 4
  val CLIMB = 5
  val DECENT = 6
  val PITCH_UP = 7
  val PITCH_DOWN = 8
  val DECREASE_VELOCITY = 9

  // weapon events
  val FIRE = 10
  val SELECT = 11
  val MINI_CANNON = 12
  val MISSILE = 13
  val BOMB = 14

}
