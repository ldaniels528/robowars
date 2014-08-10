package com.ldaniels528.robowars.objects.weapons

import com.ldaniels528.fxcore3d.FxWorld._
import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.FxModelInstance
import com.ldaniels528.robowars.objects.structures.moving.AbstractMovingScenery
import com.ldaniels528.robowars.objects.weapons.ShellCasing._

/**
 * Bullet Shell Casing
 * @author lawrence.daniels@gmail.com
 */
class ShellCasing(world: FxWorld, pos: FxPoint3D, agl: FxAngle3D, vel: FxVelocityVector, scale: FxScale3D)
  extends AbstractMovingScenery(world, pos, agl, randomVector(vel), randomAngle(vel.angle)) {

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/weapons/shell.f3d", scale)

  override def update(dt: Double) {
    super.update(dt)

    // move-out toward the ground
    setdPosition({
      val v = getdPosition()
      v.y += world.gravity * dt
      v
    })

    // die once it hits the ground
    if (position.y < 0 || age > 1.5d) {
      die()
    }
  }

}

/**
 * Abstract Shell-Casing (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object ShellCasing {

  def randomVector(dPos: FxVelocityVector, randomSpread: Double = 0.5d): FxVelocityVector = {
    FxVelocityVector(
      dPos.x + rand(-randomSpread, randomSpread),
      dPos.y + rand(-randomSpread, randomSpread),
      dPos.z + rand(-randomSpread, randomSpread))
  }

  def randomAngle(dAgl: FxAngle3D, randomRotation: Double = 2d): FxAngle3D = {
    FxAngle3D(
      dAgl.x + rand(-randomRotation, randomRotation),
      dAgl.y + rand(-randomRotation, randomRotation),
      dAgl.z + rand(-randomRotation, randomRotation))
  }

}
