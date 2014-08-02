package com.ldaniels528.robowars.actors

import JetCraft._
import com.ldaniels528.fxcore3d.FxPoint3D
import com.ldaniels528.fxcore3d.FxPolyhedron
import com.ldaniels528.fxcore3d.FxPolyhedronInstance
import com.ldaniels528.fxcore3d.FxVelocityVector
import com.ldaniels528.fxcore3d.FxWorld
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.structures.GenericFragment
import com.ldaniels528.robowars.weapons.BombBay
import com.ldaniels528.robowars.weapons.MiniCannon
import com.ldaniels528.robowars.weapons.MissileLauncher

/**
 * Jet Fighter
 * @author ldaniels
 */
class JetCraft(world: FxWorld, p: FxPoint3D)
  extends AbstractPlayer(
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
    for (n <- 1 to FRAGMENTS_WHEN_DEAD) {
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
 * JetCraft Companion Object
 * @author lawrence.daniels@gmail.com
 */
object JetCraft {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/actors/bomber.f3d")
  val SCALE = new FxPoint3D(8d, 1d, 4d)
  val INITIAL_HEALTH: Double = 5d
  val TURNING_RATE: Double = 1.6d
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
