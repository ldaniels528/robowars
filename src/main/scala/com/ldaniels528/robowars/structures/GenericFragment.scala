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
class GenericFragment(
  world: FxWorld,
  size: Double,
  origin: FxPoint3D,
  spread: Double,
  generation0: Int,
  speed: Double,
  rotation: Double)
  extends AbstractMovingScenery(world,
    new FxPoint3D(
      origin.x + spread * (random() - 0.5),
      origin.y + spread * (random() - 0.5),
      origin.z + spread * (random() - 0.5)),
    new FxAngle3D(random() * 3, random() * 3, random() * 3),
    new FxVelocityVector(),
    new FxAngle3D(random() * 3, random() * 3, random() * 3)) {

  val generation = generation0 - 1

  // --
  usePolyhedronInstance(new FxPolyhedronInstance(ourDefaultPolyhedron,
    new FxPoint3D(FxWorld.rand(0, size), FxWorld.rand(0, size),
      FxWorld.rand(0, size))))

  // --
  setdPosition({
    val v = getdPosition()
    v.x = FxWorld.rand(-speed, speed)
    v.y = FxWorld.rand(0, speed * velYfactor)
    v.z = FxWorld.rand(-speed, speed)
    v
  })

  override def update(dt: Double) {
    super.update(dt)

    setdPosition({
      val v = getdPosition()
      v.y -= 10 * dt
      v
    })

    val p = getPosition()
    if (p.y < 0) {
      p.y = size
      setPosition(p)
      die()
    }
  }

  override def die() {
    super.die()
    if (generation > 0) {
      for (n <- 1 to 5) {
        new GenericFragment(getWorld(), size / 2, getPosition(), size, generation - 1, getdPosition().y * 0.5, 3)
      }
    }
  }

}

/**
 * Generic Fragment (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object GenericFragment {
  val ourDefaultPolyhedron: FxPolyhedron = ContentManager.loadModel("/models/structures/fragment.f3d")
  val velYfactor: Double = 3d

}
