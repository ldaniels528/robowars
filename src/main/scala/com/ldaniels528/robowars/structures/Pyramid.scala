package com.ldaniels528.robowars.structures

import Pyramid._
import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.ContentManager

/**
 * Represents a Pyramid Structure
 * @author ldaniels
 */
class Pyramid(
  world: FxWorld,
  x: Double, z: Double,
  agl: FxAngle3D,
  myWidth: Double, myBredth: Double, myHeight: Double)
  extends AbstractStaticStructure(world, x, myHeight, z, agl, INITIAL_HEIGHT) {

  /**
   * Causes this virtual object to die
   */
  override def die() {
    super.die()
    val pos = getPosition()
    new PyramidRubble(world, pos.x, pos.z, getAngle(), myWidth, myBredth, myHeight * .2)
    val fragCount = (REL_FRAG_WHEN_DEAD * myHeight).toInt
    for (n <- 1 to fragCount) {
      new GenericFragment(world, REL_FRAG_SIZE * myHeight,
        getPosition(), REL_FRAG_SPREAD * myHeight,
        (myHeight * REL_FRAG_GENERATIONS).toInt, myHeight * REL_FRAG_SPEED, myHeight * REL_FRAG_ROTATION)
    }
  }

}

/**
 * Pyramid
 * @author ldaniels
 */
object Pyramid {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/structures/pyramid.f3d")
  val REL_FRAG_SIZE: Double = 0.6d
  val REL_FRAG_SPEED: Double = 2d
  val REL_FRAG_SPREAD: Double = 1d
  val REL_FRAG_WHEN_DEAD: Double = 1d
  val REL_FRAG_GENERATIONS: Double = 0.01d
  val REL_FRAG_ROTATION: Double = 0.2d
  val INITIAL_HEIGHT = 250d
}