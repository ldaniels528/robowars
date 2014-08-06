package com.ldaniels528.robowars.objects.weapons

import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.fxcore3d.{FxAngle3D, FxPoint3D, FxVelocityVector, FxWorld}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.objects.vehicles.AbstractVehicle
import com.ldaniels528.robowars.objects.weapons.MachineGunRound._

/**
 * Represents a Machine-gun Round
 * @author lawrence.daniels@gmail.com
 */
case class MachineGunRound(w: FxWorld, theShooter: AbstractVehicle, p: FxPoint3D, a: FxAngle3D)
  extends AbstractProjectile(w, theShooter, p, FxVelocityVector(a.y, a.x, velocity = 70d), impactDamage = 0.25d, lifeTime = 3d) {

  // define the 3D model
  lazy val polyhedronInstance = new FxPolyhedronInstance(MODEL, SCALE)

}

/**
 * Machine-gun Round (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object MachineGunRound {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/weapons/bullet.f3d")
  val SCALE = new FxPoint3D(0.1d, 0.1d, 0.5d)

}
