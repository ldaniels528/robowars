package com.ldaniels528.robowars.objects.vehicles

import com.ldaniels528.fxcore3d.polygon.FxModelInstance
import com.ldaniels528.fxcore3d.{FxAngle3D, FxMovingObject, FxScale3D, FxWorld}
import com.ldaniels528.robowars.objects.vehicles.VehicleRuins._

/**
 * Remains of a Vehicle
 * @author lawrence.daniels@gmail.com
 */
class VehicleRuins(world: FxWorld, deadTank: AbstractVehicle)
  extends FxMovingObject(world, deadTank.position, deadTank.angle, deadTank.getdPosition(), deadTank.getdAngle()) {

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/vehicles/vehicleRuins.f3d", SCALE)

  // throw the remaining tank up in the air
  val dp = getdPosition()
  dp.y = FxWorld.rand(0, SPEED_UP)
  setdPosition(dp)

  // set a random rotation on the remaining tank
  setdAngle(FxWorld.random3DAngle(ROTATION))

  override def update(dt: Double) {
    super.update(dt)
    val p = position
    // -- check if hit the ground
    if (p.y < SCALE.h) {
      val dp = getdPosition()
      p.y = SCALE.h
      dp.set(0, 0, 0)
      setPosition(p)
      val a = angle
      a.set(0, a.y, 0)
      setAngle(a)
      setdAngle(new FxAngle3D())
      setdPosition(dp)
    } else if (p.y > SCALE.h) {
      val dp = getdPosition()
      dp.y += world.gravity * dt
      setdPosition(dp)
    }
  }
}

/**
 * VehicleRuins Companion Object
 * @author lawrence.daniels@gmail.com
 */
object VehicleRuins {
  val SCALE = new FxScale3D(1.5d, 0.23d, 2d)
  val SPEED_UP: Double = 20d
  val ROTATION: Double = 3d

}