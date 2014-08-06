package com.ldaniels528.robowars.objects.weapons

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars._
import com.ldaniels528.robowars.objects.vehicles.AbstractVehicle
import com.ldaniels528.robowars.objects.weapons.MiniCannonRound._

/**
 * Represents a MiniCannon Round
 * @author lawrence.daniels@gmail.com
 */
case class MiniCannonRound(w: FxWorld, theShooter: AbstractVehicle, pos0: FxPoint3D, agl0: FxAngle3D)
  extends AbstractProjectile(w, theShooter, pos0, FxVelocityVector(agl0.y, agl0.x, velocity = 70d), impactDamage = 1d, lifeTime = 3d) {

  // define the 3D model
  lazy val polyhedronInstance = new FxPolyhedronInstance(MODEL, SCALE)

}

/**
 * MiniCannon Round (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object MiniCannonRound {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/weapons/bullet.f3d")
  val SCALE = new FxPoint3D(0.1d, 0.1d, 0.5d)

}
