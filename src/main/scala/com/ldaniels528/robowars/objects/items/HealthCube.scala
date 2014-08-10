package com.ldaniels528.robowars.objects.items

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.FxModelInstance
import com.ldaniels528.robowars.objects.items.HealthCube._

/**
 * Represents a Health Cube
 * @author lawrence.daniels@gmail.com
 */
case class HealthCube(w: FxWorld, p: FxPoint3D)
  extends FxMovingObject(w, FxPoint3D(p.x, p.y + SCALE.h, p.z), FxAngle3D(), FxVelocityVector(), FxAngle3D())
  with RewardItem {

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/items/cube.f3d", SCALE)

  override def update(dt: Double) {
    // the item must continually spin
    spin(dt)

    // allow super-class to update
    super.update(dt)
  }

}

/**
 * Health Cube Companion Object
 * @author lawrence.daniels@gmail.com
 */
object HealthCube {
  private val SCALE = FxScale3D(1, 1, 1)

}
