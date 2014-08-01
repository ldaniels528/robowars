package org.combat.game.weapons

import GenericBomb._
import scala.collection.mutable.ArrayBuffer

import org.combat.fxcore3d._
import org.combat.game.ContentManager
import org.combat.game.VirtualWorldImpl
import org.combat.game.structures.AbstractMovingScenery

/**
 * Generic Bomb
 * @author lawrence.daniels@gmail.com
 */
class GenericBomb(world: FxWorld, p: FxPoint3D, a: FxAngle3D, dp: FxPoint3D, strength: Double)
  extends AbstractMovingScenery(
    world, p, a, new FxVelocityVector(),
    new FxAngle3D(
      FxWorld.rand(-ROTATION, ROTATION),
      FxWorld.rand(-ROTATION, ROTATION),
      FxWorld.rand(-ROTATION, ROTATION))) {

  // set the model for the bomb
  usePolyhedronInstance(new FxPolyhedronInstance(MODEL, SCALE))

  override def die() {
    super.die()

    // create an explosion that is proportional to the strength of the bomb
    new GenericExplosion(world, getPosition(), strength * 0.25, 0.5, strength, 0.6, strength * 0.1)

    // create an explosion round since
    val explosion = new Explosion(world, 10 * strength)

    // get all objects within a radius and check feed them with the impact of the bomb
    val objects = world.getAllObjectsInRadius(getPosition(), BLAST_RADIUS)

    // check for collisions
    objects foreach { obj =>
      if (obj.interestedOfCollisionWith(explosion)) {
        obj.collisionWith(explosion, 1)
      }
    }

    // allow the explosion to die
    explosion.die()
  }

  override def update(dt: Double) {
    super.update(dt)

    setdPosition({
      val v = getdPosition()
      v.y += world.gravity
      v
    })

    val p = getPosition()
    if (p.y < 0) {
      die()
    }
  }

}

/**
 * Generic Bomb (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object GenericBomb {
  val MODEL: FxPolyhedron = ContentManager.loadModel("f3d/bomb1.f3d")
  val SCALE = new FxPoint3D(0.5d, 0.5d, 1d)
  val START_ANGLE: Double = 1d
  val ROTATION: Double = 3d
  val BLAST_RADIUS = 30d
  
}
