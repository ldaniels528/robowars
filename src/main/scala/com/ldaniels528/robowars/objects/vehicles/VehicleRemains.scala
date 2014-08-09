package com.ldaniels528.robowars.objects.vehicles

import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxModelInstance}
import com.ldaniels528.fxcore3d.{FxScale3D, FxAngle3D, FxPoint3D, FxWorld}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.objects.structures.AbstractMovingScenery
import com.ldaniels528.robowars.objects.vehicles.VehicleRemains._

/**
 * Remains of a Vehicle
 * @author lawrence.daniels@gmail.com
 */
class VehicleRemains(world: FxWorld, deadTank: AbstractVehicle)
  extends AbstractMovingScenery(world, deadTank.position, deadTank.angle, deadTank.getdPosition(), deadTank.getdAngle()) {

  // set the default polyhedron instance
  lazy val modelInstance = new FxModelInstance(MODEL, SCALE)

  // throw the remaining tank up in the air
  val dp = getdPosition()
  dp.y = FxWorld.rand(0, ourSpeedUp)
  setdPosition(dp)

  // set a random rotation on the remaining tank
  setdAngle(FxWorld.random3DAngle(ourRandRot))

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
 * VehicleRemains Companion Object
 * @author lawrence.daniels@gmail.com
 */
object VehicleRemains {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/actors/fesseTankRemains.f3d")
  val SCALE = new FxScale3D(1.5d, 0.23d, 2d)
  val ourSpeedUp: Double = 20d
  val ourRandRot: Double = 3d

}