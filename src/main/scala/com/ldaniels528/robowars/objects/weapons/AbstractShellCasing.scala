package com.ldaniels528.robowars.objects.weapons

import com.ldaniels528.fxcore3d.FxWorld._
import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.objects.structures.moving.AbstractMovingScenery

/**
 * Abstract Shell Casing
 * @author lawrence.daniels@gmail.com
 */
abstract class AbstractShellCasing(world: FxWorld,
                                   origin: FxPoint3D,
                                   agl: FxAngle3D,
                                   dpos: FxVelocityVector,
                                   dagl: FxAngle3D,
                                   randomSpread: Double,
                                   randomRotation: Double,
                                   lifeTime: Double)
  extends AbstractMovingScenery(world, origin, agl,
    FxVelocityVector(
      dpos.x + rand(-randomSpread, randomSpread),
      dpos.y + rand(-randomSpread, randomSpread),
      dpos.z + rand(-randomSpread, randomSpread)),
    FxAngle3D(
      dagl.x + rand(-randomRotation, randomRotation),
      dagl.y + rand(-randomRotation, randomRotation),
      dagl.z + rand(-randomRotation, randomRotation))) {

  override def update(dt: Double) {
    super.update(dt)

    val v = getdPosition()
    v.y += world.gravity * dt
    setdPosition(v)

    if (position.y < 0 || age > lifeTime) {
      die()
    }
  }

}

/**
 * Abstract Shell-Casing (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object AbstractShellCasing {
  private val SPEED: Double = 3d

  def computeVector(vel: FxPoint3D, a: FxAngle3D): FxVelocityVector = {
    FxVelocityVector(
      vel.x + SPEED * Math.sin(a.y + Math.PI / 2),
      vel.y + SPEED,
      vel.z + SPEED * Math.cos(a.y + Math.PI / 2))
  }

}
