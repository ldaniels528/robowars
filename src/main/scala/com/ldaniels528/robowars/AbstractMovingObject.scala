package com.ldaniels528.robowars

import com.ldaniels528.fxcore3d._

/**
 * Represents an abstract moving object
 * @param world
 * @param pos
 * @param agl
 * @param dpos
 * @param dagl
 * @author lawrence.daniels@gmail.com
 */
abstract class AbstractMovingObject(world: FxWorld,
                                    val pos: FxPoint3D,
                                    val agl: FxAngle3D,
                                    val dpos: FxVelocityVector,
                                    val dagl: FxAngle3D,
                                    var health: Double = Double.MaxValue)
  extends FxMovingObject(world, pos, agl, dpos, dagl) {

  /**
   * Causes the host's health to be reduced by the given damage amount
   * @param damage the given damage amount
   * @return the remaining health amount
   */
  def damageHealth(damage: Double): Double = {
    health -= damage
    if(health > 5) health = 5
    health
  }

}