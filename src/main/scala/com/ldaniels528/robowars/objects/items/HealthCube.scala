package com.ldaniels528.robowars.objects.items

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.objects.items.HealthCube._

/**
 * Represents a Health Cube
 * @author lawrence.daniels@gmail.com
 */
case class HealthCube(w: FxWorld, p: FxPoint3D) extends AbstractRewardItem(w, FxPoint3D(p.x, p.y + SCALE.h, p.z)) {

  // set the default polyhedron instance
  lazy val modelInstance = new FxPolyhedronInstance(MODEL, SCALE)

}

/**
 * Health Cube Companion Object
 * @author lawrence.daniels@gmail.com
 */
object HealthCube {
  private val MODEL: FxPolyhedron = ContentManager.loadModel("/models/items/cube.f3d")
  private val SCALE = FxScale3D(1, 1, 1)

}
