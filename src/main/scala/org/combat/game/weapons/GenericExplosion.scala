package org.combat.game.weapons

import GenericExplosion._
import org.combat.fxcore3d._
import org.combat.game.ContentManager
import org.combat.game.structures.AbstractMovingScenery
import org.combat.game.structures.GenericFragment
import scala.beans.BeanProperty

/**
 * Generic Explosion
 * @author lawrence.daniels@gmail.com
 */
class GenericExplosion(
  world: FxWorld,
  pos: FxPoint3D,
  strength0: Double,
  time0: Double,
  @BeanProperty strength: Double,
  time1: Double,
  strength1: Double)
  extends AbstractMovingScenery(world,
    new FxPoint3D(pos.x, strength0, pos.z),
    new FxAngle3D(0, 0, 0),
    new FxVelocityVector(),
    new FxAngle3D(0, 3, 0)) {
  var dScale1: Double = _
  var dScale2: Double = _

  // set the polyhedron instance
  usePolyhedronInstance(new FxPolyhedronInstance(ourDefaultPolyhedron, new FxPoint3D(strength0 * 2, strength0 * 0.33, strength0 * 2)))

  // create some fragments
  for (n <- 1 to (nbrOfFragments * strength).toInt) {
    new GenericFragment(getWorld(), strength * fragmentsSize, pos, strength0
      * fragmentsSpeed, 1, strength * fragmentsSpeed, strength0
      * fragmentsRotation)

    // calculate the delta scaling
    dScale1 = (strength - strength0) / time0
    dScale2 = (strength1 - strength) / time1
  }

  override def update(dt: Double) {
    super.update(dt)
    val age = getAge()

    // adjust the scaling of the polyehdron
    val scale = getPolyhedronInstance().getScalingFactor()
    if (age > (time0 + time1)) {
      die()
    } else if (age > time0) {
      scale.plus(dScale2 * dt)
    } else {
      scale.plus(dScale1 * dt)
    }
    getPolyhedronInstance().setScalingFactor(scale)

    // adjust the position so that the bottom always touches the ground
    val p = getPosition()
    p.y = scale.y
    setPosition(p)
  }

}

/**
 * Generic Explosion (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object GenericExplosion {
  val ourDefaultPolyhedron: FxPolyhedron = ContentManager.loadModel("f3d/expl1.f3d")
  val nbrOfFragments: Double = 1d
  val fragmentsSpeed: Double = 1d
  val fragmentsSize: Double = 0.2d
  val fragmentsRotation: Double =  0.3d

}