package com.ldaniels528.robowars.objects.vehicles

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.FxModelInstance
import com.ldaniels528.robowars.objects.vehicles.GliderRemains._

/**
 * Glider Remains
 * @author lawrence.daniels@gmail.com
 */
class GliderRemains(world: FxWorld, deadActor: AbstractVehicle)
  extends FxMovingObject(world, deadActor.position, deadActor.angle, deadActor.dPosition, deadActor.dAngle) {

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/vehicles/gliderRemains.f3d", SCALE)

  // -- set a random rotation on the remaining glider
  dAngle = FxWorld.random3DAngle(rotation = 1d)

  override def update(dt: Double) {
    super.update(dt)

    // check for collision with ground
    if ($position.y < SCALE.h) {
      $position.y = SCALE.h

      // level out the ruins
      $angle.set(0, $angle.y, 0)

      // reset the positional and angular velocities
      $dAngle.reset
      $dPosition.reset
    }

    // apply the effects of gravity
    else if ($position.y > SCALE.h) applyGravity(dt)
  }

}

object GliderRemains {
  val SCALE = new FxScale3D(8d, 0.2d, 4d)

}