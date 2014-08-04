package com.ldaniels528.fxcore3d

import com.ldaniels528.fxcore3d.camera.FxCamera
import com.ldaniels528.fxcore3d.polygon.FxPolyhedronInstance

/**
 * Abstract class the represents a virtual object.
 * @author lawrence.daniels@gmail.com
 */
abstract class FxObject(theWorld: FxWorld, myPos: FxPoint3D, myAngle: FxAngle3D) {

  import java.awt.Graphics2D

import scala.collection.mutable.ArrayBuffer

  private val events = ArrayBuffer[FxEvent]()
  private val occupiedGrids = ArrayBuffer[FxGrid]()

  var Pos: FxPoint3D = myPos.makeClone
  var Agl: FxAngle3D = myAngle.makeClone
  var alive: Boolean = true

  var age: Double = 0.0
  var flags: Int = _
  var polyhedronInstance: FxPolyhedronInstance = _

  // insert object into the world
  theWorld.insertObject(this)

  /**
   * Indicates whether the object is alive
   */
  def isAlive: Boolean = alive

  /**
   * Returns a clone of the position.
   */
  def position: FxPoint3D = Pos.makeClone

  /**
   * Returns a clone of the angle.
   */
  def angle: FxAngle3D = Agl.makeClone

  /**
   * Returns the world in which this object lives in.
   */
  def world: FxWorld = theWorld

  /**
   * Checks collision with another object. Only implemented by checking the bounding circles.
   */
  def checkForCollisionWith(obj: FxObject, dt: Double): Boolean = {
    polyhedronInstance.checkForCollisionWith(obj.polyhedronInstance)
  }

  /**
   * Updates this object by delta-time seconds.
   */
  def update(dt: Double) {
    age += dt
  }

  /**
   * Calls handleEvent for every event in the list.
   *
   * @see #handleEvent
   */
  def handleEvents() {
    // process the events in reverse order
    events.reverse.foreach(handleEvent)

    // all collisions are handled. clear the list.
    events.clear()
  }

  /**
   * The core will ask this object if it is interested of collision with some
   * other object. Return true if the object is interested.
   */
  def interestedOfCollisionWith(obj: FxObject): Boolean = false

  /**
   * Creates a collision event which is added to the event list.
   */
  def collisionWith(obj: FxObject, dt: Double) {
    events += FxEventCollision(age, obj, dt)
    ()
  }

  /**
   * Associates an event with this object
   */
  def +=(event: FxEvent) = events += event

  /**
   * Paints this object on camera.
   */
  def paint(g: Graphics2D, cam: FxCamera) {
    polyhedronInstance.clipAndPaint(g, cam)
  }

  def paintWithShading(g: Graphics2D, cam: FxCamera, light: FxPoint3D) {
    polyhedronInstance.clipAndPaintWithShading(g, cam, light)
  }

  /**
   * Returns the distance of this object to some other point.
   */
  def distanceToPoint(toPoint: FxPoint3D): Double = Math.sqrt(Pos.distanceToPoint(toPoint))

  /**
   * Returns the world coordinate for a position relative this object.
   */
  def getWorldCoordForRelativePoint(relpos: FxPoint3D): FxPoint3D = {
    val pwcs = new FxPoint3D()
    polyhedronInstance.transformPoint(relpos, pwcs)
    pwcs
  }

  /**
   * Updates the list of occupied grids. Moving objects will do this every
   * frame while static objects do it once.
   */
  protected def updateTheOccupiedGrids() {
    // clear the list.
    occupiedGrids.clear()

    // get the new grids.
    theWorld.map.getGridsForSphere(Pos, polyhedronInstance.getBoundingRadius(), occupiedGrids)

    // insert this object in all occupied grids.
    occupiedGrids.foreach(_.insertObject(this))
  }

  /**
   * Handles an event. If it is a known event it will be cracked and the
   * "handle" method for this event will be called. Returns false if the
   * object doesn't want any more events. I.e. the object has died.
   * @see #handleEvents
   */
  protected def handleEvent(event: FxEvent): Boolean = {
    event match {
      case ev: FxEventCollision => handleCollisionWith(ev.theObject, ev.dt)
      case _ => true
    }
  }

  /**
   * Handles a collision with a object. Returns false if there is no point in
   * checking more collisions. I.e. the object is dead.
   */
  def handleCollisionWith(obj: FxObject, dt: Double): Boolean = false

  /**
   * Instructs the object to use this FxPolyhedronInstance.
   */
  protected def usePolyhedronInstance(poly: FxPolyhedronInstance) {
    polyhedronInstance = poly
    polyhedronInstance.setOrientation(Pos, Agl)
    updateTheOccupiedGrids()
  }

  /**
   * Kills this object.
   */
  def die() {
    alive = false

    // remove the dead object from the map
    occupiedGrids.foreach(_.removeObject(this))
  }

}
