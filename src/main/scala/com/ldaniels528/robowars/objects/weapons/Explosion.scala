package com.ldaniels528.robowars.objects.weapons

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.FxModelInstance

/**
 * Explosion
 * @author lawrence.daniels@gmail.com
 */
class Explosion(world: FxWorld, strength: Double)
  extends AbstractProjectile(world, null, new FxPoint3D(), FxVelocityVector(0, 0, 0), strength) {

  val maxVelocity = 30d

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/weapons/bomb1.f3d", FxScale3D(strength, strength, strength))

}
