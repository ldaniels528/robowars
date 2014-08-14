package com.ldaniels528.robowars.objects.items

import com.ldaniels528.fxcore3d._

/**
 * Represents a reward item
 * @author lawrence.daniels@gmail.com
 */
trait RewardItem {
  self: FxMovingObject =>

  def spin(dt: Double): Unit = $angle.y += dt

}
