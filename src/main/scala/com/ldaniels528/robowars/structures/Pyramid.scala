package com.ldaniels528.robowars.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.structures.Pyramid._

/**
 * Represents a Pyramid Structure
 * @author lawrence.daniels@gmail.com
 */
class Pyramid(world: FxWorld,
              x: Double, z: Double,
              agl: FxAngle3D,
              width: Double, breadth: Double, height: Double)
  extends AbstractStaticStructure(world, x, 0, z, agl) {

  // set the default polyhedron instance
  lazy val polyhedronInstance = new FxPolyhedronInstance(MODEL, FxPoint3D(width, height, breadth))

  /**
   * Causes this virtual object to die
   */
  override def die() {
    super.die()
    val pos = position

    new PyramidRubble(world, pos.x, pos.z, angle, width, breadth, height * .2)

    // destroy the building
    destruct(height)
  }

}

/**
 * Pyramid Companion Object
 * @author lawrence.daniels@gmail.com
 */
object Pyramid {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/structures/pyramid.f3d")
}