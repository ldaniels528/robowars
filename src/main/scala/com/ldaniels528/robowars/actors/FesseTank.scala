package com.ldaniels528.robowars.actors

import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.fxcore3d.{FxPoint3D, FxVelocityVector, FxWorld}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.actors.FesseTank._
import com.ldaniels528.robowars.structures.GenericFragment
import com.ldaniels528.robowars.weapons.{MiniCannon, MissileLauncher}

/**
 * Fesse Tank
 * @author lawrence.daniels@gmail.com
 */
class FesseTank(world: FxWorld, p: FxPoint3D)
  extends AbstractActor(
    world,
    new FxPoint3D(p.x, p.y + SCALE.y, p.z),
    FxVelocityVector(Math.PI, 0, 0),
    TURNING_RATE, pitchRate = 0, ACCELERATION, BRAKE_RATE, MAX_VELOCITY,
    climbRate = 0, decentRate = 0, pitchClimbRateFactor = 0, INITIAL_HEALTH) {

  usePolyhedronInstance(new FxPolyhedronInstance(MODEL, SCALE))
  this += new MiniCannon(this, new FxPoint3D(0, SCALE.y, 0))
  this +=new MissileLauncher(this, new FxPoint3D(0, SCALE.y, 0))
  selectWeapon(0)

  override def die() {
    super.die()
    (1 to FRAGMENTS_WHEN_DEAD) foreach { n =>
      new GenericFragment(world, FRAGMENT_SIZE, getPosition(),
        FRAGMENT_SPREAD, FRAGMENT_GENERATIONS, FRAGMENT_SPEED, 3)
    }
    new FesseTankRemains(world, this)
    ()
  }

}

/**
 * FesseTank Companion Object
 * @author lawrence.daniels@gmail.com
 */
object FesseTank {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/actors/fesseTank.f3d")
  val SCALE = new FxPoint3D(1.50d, 0.75d, 2.00d)
  val INITIAL_HEALTH: Double = 5d
  val TURNING_RATE: Double = 1.25d
  val ACCELERATION: Double = 3d
  val BRAKE_RATE: Double = 10d
  val MAX_VELOCITY: Double = 20d
  val FRAGMENT_SIZE: Double = 0.25d
  // 1d
  val FRAGMENT_SPEED: Double = 25d
  val FRAGMENT_SPREAD: Double = 2d
  val FRAGMENTS_WHEN_DEAD: Int = 15
  val FRAGMENT_GENERATIONS: Int = 1

}
