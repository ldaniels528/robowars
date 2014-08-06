package com.ldaniels528.robowars.objects.items

import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.objects.AbstractMovingObject

/**
 * Represents a reward item
 * @author lawrence.daniels@gmail.com
 */
abstract class AbstractRewardItem(w: FxWorld, p: FxPoint3D)
  extends AbstractMovingObject(w, p, FxAngle3D(), FxVelocityVector(), FxAngle3D()) {

  override def update(dt: Double) {
    super.update(dt)

    // the item must continually spin
    setAngle({
      val agl = angle
      agl.y += dt
      agl
    })
  }

}
