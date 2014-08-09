package com.ldaniels528.robowars.objects

import com.ldaniels528.fxcore3d._

/**
 * Represents an abstract moving object
 * @param world the virtual world
 * @param pos the object's position in space
 * @param agl the object's angle in space
 * @param dpos the object's positional acceleration
 * @param dagl the object's angular acceleration
 * @param initialHealth the object's initial health
 * @author lawrence.daniels@gmail.com
 */
abstract class AbstractMovingObject(world: FxWorld,
                                    val pos: FxPoint3D,
                                    val agl: FxAngle3D,
                                    val dpos: FxVelocityVector,
                                    val dagl: FxAngle3D,
                                    val initialHealth: Double = Double.MaxValue)
  extends FxMovingObject(world, pos, agl, dpos, dagl) with Damageable