package com.ldaniels528.robowars.actors

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.actors.GliderRemains._
import com.ldaniels528.robowars.structures.AbstractMovingScenery

/**
 * Glider Remains
 * @author lawrence.daniels@gmail.com
 */
class GliderRemains(world: FxWorld, deadActor: AbstractActor)
  extends AbstractMovingScenery(world, deadActor.position, deadActor.angle, deadActor.getdPosition(), deadActor.getdAngle()) {

  // set the default polyhedron instance
  lazy val polyhedronInstance = new FxPolyhedronInstance(MODEL, SCALE)

  // -- set a random rotation on the remaining glider
  setdAngle(FxWorld.random3DAngle(ourRandRot))

  override def update(dt: Double) {
    super.update(dt)
    val p = position

    // -- check if collision with ground
    if (p.y < SCALE.y) {
      p.y = SCALE.y

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
    } else if (p.y > SCALE.y) {
      // -- GRAVITY
      val dp = getdPosition()
      dp.y += world.gravity * dt
      setdPosition(dp)
    }
  }

}

object GliderRemains {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/actors/gliderRemains.f3d")
  val SCALE: FxPoint3D = new FxPoint3D(8d, 0.2d, 4d)
  val ourRandRot: Double = 1d

}