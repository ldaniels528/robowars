package com.ldaniels528.robowars.weapons

import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.actors.AbstractVehicle

/**
 * Abstract Round
 * @author lawrence.daniels@gmail.com
 */
abstract class AbstractRound(world: FxWorld,
                             val shooter: AbstractVehicle,
                             pos: FxPoint3D,
                             vector: FxVelocityVector,
                             val impactDamage: Double)
  extends AbstractVehicle(world, pos, vector, health = 0) {

  def turningRate: Double = 0d
  def pitchRate: Double = 0
  def acceleration: Double = 30d
  def brakingRate: Double = 0d
  def climbRate: Double = 0
  def decentRate: Double = 0
  def pitchClimbRateFactor: Double = 0

  override def handleCollisionWith(obj: FxObject, dt: Double): Boolean = {
    if (obj != shooter) {
      die()
      false
    } else true
  }

}

