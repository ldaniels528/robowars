package com.ldaniels528.robowars.objects.weapons

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.objects.vehicles.AbstractVehicle
import com.ldaniels528.robowars.objects.weapons.Missile._

/**
 * Represents a Missile
 * @author lawrence.daniels@gmail.com
 */
case class Missile(w: FxWorld, theShooter: AbstractVehicle, p: FxPoint3D, a: FxAngle3D)
  extends AbstractProjectile(w, theShooter, p, FxVelocityVector(a.y, a.x, velocity = 50d), impactDamage = 20d, lifeTime = 4d) {

  // define the 3D model
  lazy val modelInstance = new FxPolyhedronInstance(MODEL, SCALE)

}

/**
 * Missile (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object Missile {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/weapons/missile.f3d")
  val SCALE = FxPoint3D(0.25d, 0.25d, 1.5d)

}