package com.ldaniels528.robowars.ai

import com.ldaniels528.fxcore3d.FxEvent
import com.ldaniels528.robowars.actors.AbstractPlayer
import scala.collection.mutable.Buffer

abstract class AbstractAI(val theHost: AbstractPlayer) {
  val theWorld = theHost.getWorld()
  val myEvents = Buffer[FxEvent]()
  var idCount: Int = _

  // set the host's brain
  theHost.setBrain(this)

  def handleEvents() {
    myEvents.reverse foreach (handleEvent)
    myEvents.clear
  }

  def addEvent(event: FxEvent) = myEvents += event

  def handleEvent(event: FxEvent): Boolean = true

  def update(dt: Double) = handleEvents()

  def abortJob() {}

  protected def getUniqueId(): Int = {
    idCount += 1
    idCount
  }

}
