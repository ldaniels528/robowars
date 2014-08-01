package org.combat.game.weapons

import MissileRound._

import org.combat.fxcore3d._
import org.combat.game.ContentManager
import org.combat.game.structures.GenericFragment

/**
 * Missile Round
 * @author lawrence.daniels@gmail.com
 */
class MissileRound(w: FxWorld, shooter: FxObject, pos: FxPoint3D, agl: FxAngle3D)
  extends AbstractRound(w, shooter, pos, FxVelocityVector(agl.y, agl.x, 0), 0, 0,
    ACCELERATION, 0, MAX_VELOCITY, 0, 0, 1, IMPACT_DAMAGE) {

  usePolyhedronInstance(new FxPolyhedronInstance(MODEL, SCALE))

  override def update(dt: Double) {
    super.update(dt)
    increaseVelocity(1, dt)

    val p = getPosition()
    if ((age > 4) || (p.y < 0)) {
      p.y = 0
      setPosition(p)
      die()
    }
  }

  override def die() {
    super.die()
    for (n <- 1 to FRAGMENTS_WHEN_DEAD) {
      new GenericFragment(
        getWorld(), FRAGMENT_SIZE, getPosition(),
        FRAGMENT_SPREAD, FRAGMENT_GENERATIONS, FRAGMENT_SPEED, 3)
    }
  }

}

/**
 * Generic Missile (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object MissileRound {
  val MODEL: FxPolyhedron = ContentManager.loadModel("f3d/missile.f3d")
  val SCALE = new FxPoint3D(0.25d, 0.25d, 1.5d)
  val ACCELERATION: Double = 30d
  val MAX_VELOCITY: Double = 50d
  val IMPACT_DAMAGE: Double = 20d
  val FRAGMENT_SIZE: Double = 2d
  val FRAGMENT_SPEED: Double = 7d
  val FRAGMENT_SPREAD: Double = 1d
  val FRAGMENTS_WHEN_DEAD: Int = 15
  val FRAGMENT_GENERATIONS: Int = 1

}