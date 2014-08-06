package com.ldaniels528.robowars.objects.vehicles

import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.fxcore3d.{FxAngle3D, FxPoint3D, FxWorld}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.objects.vehicles.FesseTankRemains._
import com.ldaniels528.robowars.objects.structures.AbstractMovingScenery

/**
 * Remains of a Fesse Tank
 * @author lawrence.daniels@gmail.com
 */
class FesseTankRemains(world: FxWorld, deadTank: AbstractVehicle)
  extends AbstractMovingScenery(world, deadTank.position, deadTank.angle, deadTank.getdPosition(), deadTank.getdAngle()) {

  // set the default polyhedron instance
  lazy val polyhedronInstance = new FxPolyhedronInstance(MODEL, SCALE)

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
    if (p.y < SCALE.y) {
      val dp = getdPosition()
      p.y = SCALE.y
      dp.set(0, 0, 0)
      setPosition(p)
      val a = angle
      a.set(0, a.y, 0)
      setAngle(a)
      setdAngle(new FxAngle3D())
      setdPosition(dp)
    } else if (p.y > SCALE.y) {
      val dp = getdPosition()
      dp.y += world.gravity * dt
      setdPosition(dp)
    }
  }
}

/**
 * FesseTankRemains Companion Object
 * @author lawrence.daniels@gmail.com
 */
object FesseTankRemains {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/actors/fesseTankRemains.f3d")
  val SCALE = new FxPoint3D(1.5d, 0.23d, 2d)
  val ourSpeedUp: Double = 20d
  val ourRandRot: Double = 3d

}