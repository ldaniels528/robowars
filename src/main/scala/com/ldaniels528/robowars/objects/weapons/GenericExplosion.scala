package com.ldaniels528.robowars.objects.weapons

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.FxModelInstance
import com.ldaniels528.robowars.objects.Destructible

/**
 * Generic Explosion
 * @author lawrence.daniels@gmail.com
 */
class GenericExplosion(world: FxWorld,
                       pos: FxPoint3D,
                       strength0: Double,
                       time0: Double,
                       strength: Double,
                       time1: Double,
                       strength1: Double)
  extends FxMovingObject(world, FxPoint3D(pos.x, strength0, pos.z), FxAngle3D(), FxVelocityVector(), FxAngle3D(0, 3, 0))
  with Destructible {

  val dScale1: Double = (strength - strength0) / time0
  val dScale2: Double = (strength1 - strength) / time1

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/weapons/explosion.f3d", FxScale3D(strength0 * 2, strength0 * 0.33, strength0 * 2))

  // create some fragments
  explodeIntoFragments(
    fragments = strength.toInt,
    size = strength * 0.2,
    speed = strength,
    spread = strength0,
    rotation = strength0 * 0.3
  )

  override def update(dt: Double) {
    super.update(dt)

    // adjust the scaling of the polyhedron
    val scale = modelInstance.scalingFactor
    if (age > (time0 + time1)) die()
    else if (age > time0) scale += (dScale2 * dt)
    else scale += (dScale1 * dt)

    // scale up the explosion (sphere)
    modelInstance.setScalingFactor(scale)

    // adjust the position so that the bottom always touches the ground
    $position.y = scale.y
  }

}
