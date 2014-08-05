package com.ldaniels528.robowars.weapons

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars._
import com.ldaniels528.robowars.actors.AbstractVehicle
import com.ldaniels528.robowars.structures.GenericFragment
import com.ldaniels528.robowars.weapons.MiniCannonRound._

/**
 * MiniCannon Round
 * @author lawrence.daniels@gmail.com
 */
class MiniCannonRound(world: FxWorld, shooter: AbstractVehicle, pos: FxPoint3D, agl: FxAngle3D)
  extends AbstractRound(world, shooter, pos, FxVelocityVector(agl.y, agl.x, MAX_VELOCITY), impactDamage) {

  protected var dieNextUpdate: Boolean = _
  protected var deadOfAge: Boolean = _
  val maxVelocity: Double = MAX_VELOCITY

  // set the default polyhedron instance
  lazy val polyhedronInstance = new FxPolyhedronInstance(MODEL, SCALE)

  // -- create an empty shell
  new MiniCannonShell(world, pos, agl, shooter.getdPosition())

  override def die() {
    super.die()

    // -- if this round has died by hitting something
    if (!deadOfAge) {
      for (n <- 1 to fragmentsWhenDead) {
        new GenericFragment(world, fragmentSize, position,
          fragmentSpread, fragmentGenerations, fragmentSpeed,
          fragmentRotation)
      }
    }
  }

  override def update(dt: Double) {
    super.update(dt)

    // -- check if it is time to die
    if (dieNextUpdate) die()
    else {
      // -- if bullet hits the ground then die
      // -- next round so that a collision detection
      // -- can be done
      val p = position
      if (p.y < 0) {
        p.y = 0
        setPosition(p)
        dieNextUpdate = true
      }

      // -- if life is out
      if (age > lifeTime) {
        deadOfAge = true
        die()
      }
    }
  }

}

/**
 * MiniCannon Round (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object MiniCannonRound {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/weapons/bullet.f3d")
  val SCALE = new FxPoint3D(0.1d, 0.1d, 0.5d)

  val MAX_VELOCITY: Double = 70d
  val fragmentSize: Double = 0.5d
  val fragmentSpeed: Double = 2d
  val fragmentSpread: Double = 2d
  val fragmentsWhenDead: Int = 1
  val fragmentGenerations: Int = 1
  val fragmentRotation: Double = 3d
  val lifeTime: Double = 3d
  val impactDamage: Double = 1d

}
