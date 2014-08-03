package com.ldaniels528.robowars.actors

import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.fxcore3d.{FxPoint3D, FxVelocityVector, FxWorld}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.actors.Glider._
import com.ldaniels528.robowars.structures.GenericFragment
import com.ldaniels528.robowars.weapons.{BombBay, MiniCannon, MissileLauncher}

/**
 * Glider Vehicle
 * @author lawrence.daniels@gmail.com
 */
class Glider(world: FxWorld, p: FxPoint3D)
  extends AbstractActor(
    world,
    new FxPoint3D(p.x, p.y + SCALE.y, p.z),
    FxVelocityVector(Math.PI, 0, 0),
    TURNING_RATE, PITCH_RATE, ACCELERATION,
    BRAKE_RATE, MAX_VELOCITY, CLIMB_RATE, DECENT_RATE, 1, INITIAL_HEALTH) {

  // -- use the default polyhedron instance
  usePolyhedronInstance(new FxPolyhedronInstance(MODEL, SCALE))

  // -- add the weapons
  addWeapon(new MiniCannon(this, new FxPoint3D(0, SCALE.y, 0)))
  addWeapon(new MissileLauncher(this, new FxPoint3D(0, SCALE.y, 0)))
  addWeapon(new BombBay(this, new FxPoint3D(0, SCALE.y, 0)))
  selectWeapon(0)

  override def die() {
    super.die()
    (1 to FRAGMENTS_WHEN_DEAD) foreach { n =>
      new GenericFragment(getWorld(), FRAGMENT_SIZE, getPosition(),
        FRAGMENT_SPREAD, FRAGMENT_GENERATIONS, FRAGMENT_SPEED, 3)
    }
    new GliderRemains(world, this)
    ()
  }

  override def update(dt: Double) {
    super.update(dt)

    // -- check collision with ground
    val p = getPosition()
    if (p.y < SCALE.y) {
      p.y = SCALE.y
      setPosition(p)

      // update the velocity
      setdPosition({
        val dp = getdPosition()
        dp.setAngleAboutXaxis(0)
        dp.setVelocity(0)
        dp
      })

      // -- some damage depending on the speed
      val vel = getdPosition().getVelocity()
      damageHealth(vel)
    }
    ()
  }

}

/**
 * Glider Companion Object
 * @author lawrence.daniels@gmail.com
 */
object Glider {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/actors/glider.f3d")
  val SCALE = new FxPoint3D(8d, 1d, 4d)
  val INITIAL_HEALTH: Double = 5d
  val TURNING_RATE: Double = 0.6d
  val ACCELERATION: Double = 3d
  val BRAKE_RATE: Double = 3d
  val MAX_VELOCITY: Double = 150d
  val CLIMB_RATE: Double = 3d
  val DECENT_RATE: Double = 4d
  val PITCH_RATE: Double = 0.5d
  val FRAGMENT_SIZE: Double = 0.25d
  val FRAGMENT_SPEED: Double = 7d
  val FRAGMENT_SPREAD: Double = 1d
  val FRAGMENTS_WHEN_DEAD: Int = 15
  val FRAGMENT_GENERATIONS: Int = 1

}
