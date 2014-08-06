package com.ldaniels528.robowars.objects.ai

import com.ldaniels528.fxcore3d.FxEvent
import com.ldaniels528.robowars.objects.vehicles.AbstractVehicle

import scala.collection.mutable.Buffer

/**
 * Represents an artificial intelligence
 * @param theHost the host actor
 */
abstract class AbstractAI(val theHost: AbstractVehicle) {
  protected val theWorld = theHost.world
  private val myEvents = Buffer[FxEvent]()

  // set the host's cpu
  theHost.cpu = Some(this)

  def +=(event: FxEvent) = myEvents += event

  def handleEvents() {
    myEvents.reverse foreach (handleEvent)
    myEvents.clear
  }

  def handleEvent(event: FxEvent): Boolean = true

  def update(dt: Double) = handleEvents()

  def abortJob() {}

  protected def getUniqueId(): Long = System.nanoTime()

}
