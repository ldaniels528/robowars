package com.ldaniels528.robowars.objects.weapons

import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.objects.vehicles.AbstractVehicle
import com.ldaniels528.robowars.objects.weapons.MachineGunRound._

/**
 * Represents a Machine-gun Round
 * @author lawrence.daniels@gmail.com
 */
case class MachineGunRound(w: FxWorld, theShooter: AbstractVehicle, p: FxPoint3D, a: FxAngle3D)
  extends AbstractProjectile(w, theShooter, FxPoint3D(p.x, p.y/2, p.z), FxVelocityVector(a.y, a.x, velocity = 70d), impactDamage = 0.25d, lifeTime = 3d) {

  // define the 3D model
  lazy val modelInstance = new FxPolyhedronInstance(MODEL, SCALE)

}

/**
 * Machine-gun Round (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object MachineGunRound {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/weapons/bullet.f3d")
  val SCALE = FxScale3D(0.15d, 0.15d, 0.75d)

}
