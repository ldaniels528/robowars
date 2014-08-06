package com.ldaniels528.robowars.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.structures.Pyramid._

/**
 * Represents a Pyramid Structure
 * @author lawrence.daniels@gmail.com
 */
case class Pyramid(theWorld: FxWorld, pos: FxPoint3D, agl: FxAngle3D, size: FxScale3D)
  extends AbstractStaticStructure(theWorld, FxPoint3D(pos.x, 0, pos.z), agl, health = 50) {

  // set the default polyhedron instance
  lazy val polyhedronInstance = new FxPolyhedronInstance(MODEL, size.toPoint)

  /**
   * Causes this virtual object to die
   */
  override def die() {
    super.die()
    val pos = position

    new PyramidRubble(world, pos, angle, FxScale3D(size.w, size.h * 0.2d, size.d))

    // destroy the pyramid
    destruct(size.h)
  }

}

/**
 * Pyramid Companion Object
 * @author lawrence.daniels@gmail.com
 */
object Pyramid {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/structures/pyramid.f3d")
}