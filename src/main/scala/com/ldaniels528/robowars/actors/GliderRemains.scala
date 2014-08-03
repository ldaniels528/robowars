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
  extends AbstractMovingScenery(world, deadActor.getPosition(), deadActor.getAngle(), deadActor.getdPosition(), deadActor.getdAngle()) {

  // -- use the default polyhedron instance
  usePolyhedronInstance(new FxPolyhedronInstance(ourDefaultPolyhedron, ourScale))

  // -- set a random rotation on the remaining glider
  setdAngle(new FxAngle3D(
    FxWorld.rand(-ourRandRot, ourRandRot),
    FxWorld.rand(-ourRandRot, ourRandRot),
    FxWorld.rand(-ourRandRot, ourRandRot)))

  override def update(dt: Double) {
    super.update(dt)
    val p = getPosition()

    // -- check if collision with ground
    if (p.y < ourScale.y) {
      p.y = ourScale.y

      val dp = getdPosition()
      dp.x = 0
      dp.y = 0
      dp.z = 0

      setPosition(p)

      val a = getAngle()
      a.set(0, a.y, 0)
      // a.x = a.z = 0
      setAngle(a)
      setdAngle(new FxAngle3D())
      setdPosition(dp)
    } else if (p.y > ourScale.y) {
      // -- GRAVITY
      val dp = getdPosition()
      dp.y += world.gravity * dt
      setdPosition(dp)
    }
  }

}

object GliderRemains {
  val ourDefaultPolyhedron: FxPolyhedron = ContentManager.loadModel("/models/actors/gliderRemains.f3d")
  val ourScale: FxPoint3D = new FxPoint3D(8d, 0.2d, 4d)
  val ourRandRot: Double = 1d

}