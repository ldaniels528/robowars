package com.ldaniels528.robowars.objects.weapons

import com.ldaniels528.fxcore3d.FxWorld._
import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.FxModelInstance
import com.ldaniels528.robowars.objects.weapons.ShellCasing._

/**
 * Bullet Shell Casing
 * @author lawrence.daniels@gmail.com
 */
class ShellCasing(world: FxWorld, pos: FxPoint3D, agl: FxAngle3D, vel: FxVelocityVector, scale: FxScale3D)
  extends FxMovingObject(world, pos, agl, randomVector(vel), randomAngle(vel.angle)) {

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/weapons/shell.f3d", scale)

  override def update(dt: Double) {
    super.update(dt)

    // if the shell is above ground and within its lifespan
    if ($position.y > 0 && age < 1.5d) {
      // apply the effects of gravity
      applyGravity(dt)
    }
    else die()
  }

}

/**
 * Abstract Shell-Casing (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object ShellCasing {

  private def randomVector(dPos: FxVelocityVector, randomSpread: Double = 0.5d): FxVelocityVector = {
    FxVelocityVector(
      dPos.x + rand(-randomSpread, randomSpread),
      dPos.y + rand(-randomSpread, randomSpread),
      dPos.z + rand(-randomSpread, randomSpread))
  }

  private def randomAngle(dAgl: FxAngle3D, randomRotation: Double = 2d): FxAngle3D = {
    FxAngle3D(
      dAgl.x + rand(-randomRotation, randomRotation),
      dAgl.y + rand(-randomRotation, randomRotation),
      dAgl.z + rand(-randomRotation, randomRotation))
  }

}
