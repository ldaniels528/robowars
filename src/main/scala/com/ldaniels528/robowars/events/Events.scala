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

  // weapon constants
  val WEAPON_CODE = 12
  val MACHINE_GUN = 12
  val MINI_CANNON = 13
  val MISSILE = 14
  val BOMB = 15

}
