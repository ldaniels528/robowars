package com.ldaniels528.robowars.objects.weapons

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.FxModelInstance
import com.ldaniels528.robowars.objects.structures.moving.AbstractMovingScenery

/**
 * Bomb
 * @author lawrence.daniels@gmail.com
 */
case class Bomb(w: FxWorld, p: FxPoint3D, a: FxAngle3D, dp: FxPoint3D, strength: Double)
  extends AbstractMovingScenery(w, p, a, new FxVelocityVector(), FxWorld.random3DAngle(rotation = 3d)) {

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/weapons/bomb1.f3d", FxScale3D(0.5d, 0.5d, 1d))

  override def die() {
    super.die()

    // create an explosion that is proportional to the strength of the bomb
    new GenericExplosion(world, position, strength * 0.25, 0.5, strength, 0.6, strength * 0.1)

    // create an explosion round since
    val explosion = new Explosion(world, strength = 10 * strength)

    // get all objects within the blast radius and check feed them with the impact of the bomb
    val objects = world.getAllObjectsInRadius(position, radius = 30d)

    // check for collisions
    objects foreach { obj =>
      if (obj.interestedOfCollisionWith(explosion)) {
        obj.collisionWith(explosion, dt = 1)
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

    val p = position
    if (p.y < 0) {
      die()
    }
  }

}

/**
 * Represents an Explosion
 * @author lawrence.daniels@gmail.com
 */
case class Explosion(w: FxWorld, strength: Double)
  extends AbstractProjectile(w, null, new FxPoint3D(), FxVelocityVector(0, 0, 0), strength) {

  val maxVelocity = 30d

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/weapons/bomb1.f3d", FxScale3D(strength, strength, strength))

}

