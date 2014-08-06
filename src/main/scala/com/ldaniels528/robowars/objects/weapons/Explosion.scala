package com.ldaniels528.robowars.objects.weapons

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.objects.weapons.Explosion._

/**
 * Explosion
 * @author lawrence.daniels@gmail.com
 */
class Explosion(world: FxWorld, strength: Double)
  extends AbstractProjectile(world, null, new FxPoint3D(), FxVelocityVector(0, 0, 0), strength) {

  val maxVelocity = 30d

  // set the default polyhedron instance
  lazy val polyhedronInstance = new FxPolyhedronInstance(MODEL, FxPoint3D(strength, strength, strength))

}

/**
 * Generic Bomb (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object Explosion {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/weapons/bomb1.f3d")

}