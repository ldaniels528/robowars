package com.ldaniels528.robowars.objects.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.objects.structures.Pyramid._

/**
 * Represents a Pyramid Structure
 * @author lawrence.daniels@gmail.com
 */
case class Pyramid(w: FxWorld, pos: FxPoint3D, agl: FxAngle3D, scale: FxScale3D)
  extends AbstractStaticStructure(w, FxPoint3D(pos.x, 0, pos.z), agl, health = 50) {

  // set the default polyhedron instance
  lazy val modelInstance = new FxPolyhedronInstance(MODEL, scale)

  /**
   * Causes this virtual object to die
   */
  override def die() {
    super.die()

    // leave ruins behind
    new PyramidRubble(world, position, angle, scale.reducedHeight(0.2d))

    // destroy the pyramid
    destruct(scale.h)
  }

}

/**
 * Pyramid Companion Object
 * @author lawrence.daniels@gmail.com
 */
object Pyramid {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/structures/pyramid.f3d")

}