package com.ldaniels528.robowars.objects.vehicles

import com.ldaniels528.fxcore3d.polygon.FxModelInstance
import com.ldaniels528.fxcore3d.{FxPoint3D, FxScale3D, FxVelocityVector, FxWorld}
import com.ldaniels528.robowars.objects.vehicles.Glider._
import com.ldaniels528.robowars.objects.weapons.{BombLauncher, MiniCannon, MissileLauncher}

/**
 * Glider Vehicle
 * @author lawrence.daniels@gmail.com
 */
case class Glider(w: FxWorld, p: FxPoint3D)
  extends AbstractVehicle(w, FxPoint3D(p.x, p.y + SCALE.h, p.z), FxVelocityVector(Math.PI, 0, 0)) {

  val turningRate: Double = 0.6d
  val pitchRate: Double = 0.5d
  val acceleration: Double = 3d
  val brakingRate: Double = 3d
  val maxVelocity: Double = 150d
  val climbRate: Double = 3d
  val decentRate: Double = 4d
  val pitchClimbRateFactor: Double = 1d

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/vehicles/glider.f3d", SCALE)

  // attach some weapons
  this += new MiniCannon(this, new FxPoint3D(0, SCALE.h, 0))
  this += new MissileLauncher(this, new FxPoint3D(0, SCALE.h, 0))
  this += new BombLauncher(this, new FxPoint3D(0, SCALE.h, 0))

  override def update(dt: Double) {
    super.update(dt)

    // -- check collision with ground
    val p = position
    if (p.y < SCALE.h) {
      p.y = SCALE.h
      setPosition(p)

      // update the velocity
      setdPosition({
        val dp = getdPosition()
        dp.setAngleAboutAxisX(0)
        dp.setVelocity(0)
        dp
      })

      // -- some damage depending on the speed
      val vel = getdPosition().velocity
      damageHealth(vel)
    }
    ()
  }

}

/**
 * Glider Companion Object
 * @author lawrence.daniels@gmail.com
 */
object Glider {
  val SCALE = FxScale3D(8d, 1d, 4d)

}
