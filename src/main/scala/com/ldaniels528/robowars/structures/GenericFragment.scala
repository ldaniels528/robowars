package com.ldaniels528.robowars.structures

import java.lang.Math.random

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.structures.GenericFragment._

/**
 * Generic Fragment
 * @author lawrence.daniels@gmail.com
 */
class GenericFragment(world: FxWorld, size: Double, origin: FxPoint3D, spread: Double, generation0: Int, speed: Double, rotation: Double)
  extends AbstractMovingScenery(world,
    FxPoint3D(
      origin.x + spread * (random() - 0.5),
      origin.y + spread * (random() - 0.5),
      origin.z + spread * (random() - 0.5)),
    FxAngle3D(random() * 3, random() * 3, random() * 3),
    FxVelocityVector(),
    FxAngle3D(random() * 3, random() * 3, random() * 3)) {

  val generation = generation0 - 1

  // set the default polyhedron instance
  lazy val polyhedronInstance = new FxPolyhedronInstance(MODEL, FxWorld.random3DPoint(size))

  // set the delta-position
  setdPosition({
    val v = getdPosition()
    v.x = FxWorld.rand(-speed, speed)
    v.y = FxWorld.rand(0, speed * velYfactor)
    v.z = FxWorld.rand(-speed, speed)
    v
  })

  override def die() {
    super.die()

    if (generation > 0) {
      (1 to 5) foreach { n =>
        new GenericFragment(world, size / 2d, position, size, generation - 1, getdPosition().y * 0.5, 3)
      }
    }
  }

  override def update(dt: Double) {
    super.update(dt)

    setdPosition({
      val v = getdPosition()
      v.y -= 10 * dt
      v
    })

    val p = position
    if (p.y < 0) {
      p.y = size
      setPosition(p)
      die()
    }
  }

}

/**
 * Generic Fragment (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object GenericFragment {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/structures/fragment.f3d")
  val velYfactor: Double = 3d

}
