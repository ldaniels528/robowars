package com.ldaniels528.robowars.objects

import java.lang.Math.random

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.FxModelInstance
import com.ldaniels528.robowars.objects.Fragment._

/**
 * Represents a Fragment
 * @author lawrence.daniels@gmail.com
 */
class Fragment(world: FxWorld, size: Double, origin: FxPoint3D, spread: Double, generation0: Int, speed: Double, rotation: Double)
  extends FxMovingObject(world, randomPoint(origin, spread), randomAngle, FxVelocityVector(), randomAngle) {

  val generation = generation0 - 1

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/structures/fragment.f3d", FxWorld.random3DScale(size))

  // set the position velocity
  $dPosition.x = FxWorld.rand(-speed, speed)
  $dPosition.y = FxWorld.rand(0, speed * 3d)
  $dPosition.z = FxWorld.rand(-speed, speed)

  override def die() {
    super.die()

    if (generation > 0) {
      (1 to 5) foreach { n =>
        new Fragment(world, size / 2d, position, size, generation - 1, dPosition.y * 0.5, 3)
      }
    }
  }

  override def update(dt: Double) {
    super.update(dt)

    // apply the affects of gravity
    applyGravity(dt)

    // if the fragment has hit the ground, die.
    if ($position.y < 0) {
      $position.y = size
      die()
    }
  }

}

/**
 * Fragment Companion Object
 * @author lawrence.daniels@gmail.com
 */
object Fragment {

  private def randomPoint(origin: FxPoint3D, spread: Double) = FxPoint3D(
    origin.x + spread * (random() - 0.5),
    origin.y + spread * (random() - 0.5),
    origin.z + spread * (random() - 0.5))

  private def randomAngle = FxAngle3D(random() * 3d, random() * 3d, random() * 3d)

}
