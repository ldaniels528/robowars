package com.ldaniels528.robowars.objects.ai

import com.ldaniels528.fxcore3d.FxObject

/**
 * Represents an autonomous actor
 * @author lawrence.daniels@gmail.com
 */
trait AICapability {
  host: FxObject =>

  def getWorld = host.world

  var brain: Option[AbstractAI] = None

  override def update(dt: Double) {
    host.update(dt)
    brain.foreach(_.update(dt))
  }

}
