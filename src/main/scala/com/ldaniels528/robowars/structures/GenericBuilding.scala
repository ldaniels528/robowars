package com.ldaniels528.robowars.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.structures.GenericBuilding._

/**
 * Generic Building
 * @author lawrence.daniels@gmail.com
 */
class GenericBuilding(
  world: FxWorld,
  x: Double, z: Double,
  agl: FxAngle3D,
  myWidth: Double, myBredth: Double, myHeight: Double)
  extends AbstractStaticStructure(world, x, myHeight, z, agl, INITIAL_HEIGHT) {

  usePolyhedronInstance(new FxPolyhedronInstance(MODEL, new FxPoint3D(myWidth, myHeight, myBredth)))

  override def die() {
    super.die()
    val pos = getPosition()
    new GenericBuildingRuin(world, pos.x, pos.z, getAngle(), myWidth, myBredth, myHeight * .2)
    val fragCount = (REL_FRAG_WHEN_DEAD * myHeight).toInt
    for (n <- 1 to fragCount) {
      new GenericFragment(world, REL_FRAG_SIZE * myHeight,
        getPosition(), REL_FRAG_SPREAD * myHeight,
        (myHeight * REL_FRAG_GENERATIONS).toInt, myHeight * REL_FRAG_SPEED, myHeight * REL_FRAG_ROTATION)
    }
  }
}

/**
 * Generic Building
 * @author lawrence.daniels@gmail.com
 */
object GenericBuilding {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/structures/build2.f3d")
  val REL_FRAG_SIZE: Double = 0.6d
  val REL_FRAG_SPEED: Double = 2d
  val REL_FRAG_SPREAD: Double = 1d
  val REL_FRAG_WHEN_DEAD: Double = 1d
  val REL_FRAG_GENERATIONS: Double = 0.01d
  val REL_FRAG_ROTATION: Double =  0.2d
  val INITIAL_HEIGHT: Double = 20d

  /**
   * Generic building with the default 0,0,0 angle.
   */
  def apply(world: FxWorld, x: Double, z: Double, w: Double, b: Double, h: Double): GenericBuilding = {
    new GenericBuilding(world, x, z, new FxAngle3D(0, 0, 0), w, b, h)
  }

  /**
   * Generic building with the default 0,0,0 angle.
   */
  def apply(world: FxWorld, pos: FxPoint3D, dims: FxDimensions3D): GenericBuilding = {
    new GenericBuilding(world, pos.x, pos.z, new FxAngle3D(0, 0, 0), dims.width, dims.breadth, dims.height)
  }

}