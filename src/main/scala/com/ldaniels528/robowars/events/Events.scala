package com.ldaniels528.robowars.events

/**
 * Created by ldaniels on 8/2/14.
 */
trait Events {
  // motion events
  val GOTO_POSITION = 1

  // steering events
  val TURN_LEFT = 1;
  val TURN_RIGHT = 2;
  val INCREASE_VELOCITY = 3;
  val BRAKE = 4;
  val CLIMB = 5;
  val DECENT = 6;
  val PITCH_UP = 7;
  val PITCH_DOWN = 8;
  val DECREASE_VELOCITY = 9;

  // weapon events
  val FIRE = 19
  val SELECT = 18
  val MINICANNON = 20
  val MISSILE = 21
  val BOMB = 22

}
