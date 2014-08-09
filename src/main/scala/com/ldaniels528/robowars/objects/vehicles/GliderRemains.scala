package com.ldaniels528.robowars.objects.vehicles

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxModelInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.objects.structures.AbstractMovingScenery
import com.ldaniels528.robowars.objects.vehicles.GliderRemains._

/**
 * Glider Remains
 * @author lawrence.daniels@gmail.com
 */
class GliderRemains(world: FxWorld, deadActor: AbstractVehicle)
  extends AbstractMovingScenery(world, deadActor.position, deadActor.angle, deadActor.getdPosition(), deadActor.getdAngle()) {

  // set the default polyhedron instance
  lazy val modelInstance = new FxModelInstance(MODEL, SCALE)

  // -- set a random rotation on the remaining glider
  setdAngle(FxWorld.random3DAngle(ourRandRot))

  override def update(dt: Double) {
    super.update(dt)
    val p = position

    // -- check if collision with ground
    if (p.y < SCALE.h) {
      p.y = SCALE.h

      val dp = getdPosition()
      dp.x = 0
      dp.y = 0
      dp.z = 0

      setPosition(p)

      val a = angle
      a.set(0, a.y, 0)
      // a.x = a.z = 0
      setAngle(a)
      setdAngle(new FxAngle3D())
      setdPosition(dp)
    } else if (p.y > SCALE.h) {
      // -- GRAVITY
      val dp = getdPosition()
      dp.y += world.gravity * dt
      setdPosition(dp)
    }
  }

}

object GliderRemains {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/actors/gliderRemains.f3d")
  val SCALE = new FxScale3D(8d, 0.2d, 4d)
  val ourRandRot: Double = 1d

}