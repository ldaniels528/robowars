package com.ldaniels528.robowars.ai

import com.ldaniels528.fxcore3d.FxEvent
import com.ldaniels528.robowars.actors.AbstractActor

import scala.collection.mutable.Buffer

/**
 * Represents an artificial intelligence
 * @param theHost the host actor
 */
abstract class AbstractAI(val theHost: AbstractActor) {
  val theWorld = theHost.getWorld()
  val myEvents = Buffer[FxEvent]()
  var idCount: Int = _

  // set the host's brain
  theHost.brain = Some(this)

  def addEvent(event: FxEvent) = myEvents += event

  def handleEvents() {
    myEvents.reverse foreach (handleEvent)
    myEvents.clear
  }

  def handleEvent(event: FxEvent): Boolean = true

  def update(dt: Double) = handleEvents()

  def abortJob() {}

  protected def getUniqueId(): Int = {
    idCount += 1
    idCount
  }

}
