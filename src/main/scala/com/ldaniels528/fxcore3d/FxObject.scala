package com.ldaniels528.fxcore3d

import java.awt.Graphics2D

import com.ldaniels528.fxcore3d.camera.FxCamera
import com.ldaniels528.fxcore3d.polygon.FxModelInstance

/**
 * Abstract class the represents a virtual object.
 * @author lawrence.daniels@gmail.com
 */
abstract class FxObject(var world: FxWorld, myPos: FxPoint3D, myAngle: FxAngle3D) {
  private var events: List[FxEvent] = Nil
  private var occupiedGrids: Seq[FxGrid] = Seq.empty
  private var alive: Boolean = true

  protected [fxcore3d] val Pos: FxPoint3D = myPos.makeClone
  protected [fxcore3d] val Agl: FxAngle3D = myAngle.makeClone
  protected [fxcore3d] var age: Double = _

  // setup the model's orientation
  modelInstance.setOrientation(Pos, Agl)

  // insert object into the world
  world += this

  // update the occupied grids
  updateTheOccupiedGrids()

  /**
   * Causes this object to die
   */
  def die() {
    alive = false

    // remove the dead object from the map
    occupiedGrids.foreach(_.removeObject(this))
  }

  /**
   * Indicates whether the object is alive
   */
  def isAlive: Boolean = alive

  /**
   * Returns a clone of the angle.
   */
  def angle: FxAngle3D = Agl.makeClone

  /**
   * Returns the object's angle
   * @return the object's angle
   */
  protected[fxcore3d] def $angle = Agl

  /**
   * Returns a clone of the position.
   */
  def position: FxPoint3D = Pos.makeClone

  /**
   * Returns the object's position
   * @return the object's position
   */
  protected[fxcore3d] def $position: FxPoint3D = Pos

  /**
   * Returns the polyhedron instance
   * @return the [[FxModelInstance]]
   */
  def modelInstance: FxModelInstance

  /**
   * Checks collision with another object. Only implemented by checking the bounding circles.
   */
  def checkForCollisionWith(obj: FxObject, dt: Double): Boolean = {
    modelInstance.checkForCollisionWith(obj.modelInstance)
  }

  /**
   * Creates a collision event which is added to the event list.
   */
  def collisionWith(obj: FxObject, dt: Double) {
    events = FxEventCollision(age, obj, dt) :: events
    ()
  }

  /**
   * Handles a collision with a object. Returns false if there is no point in
   * checking more collisions. I.e. the object is dead.
   */
  def handleCollisionWith(obj: FxObject, dt: Double): Boolean = false

  /**
   * The core will ask this object if it is interested of collision with some
   * other object. Return true if the object is interested.
   */
  def interestedOfCollisionWith(obj: FxObject): Boolean = false

  /**
   * Returns the distance of this object to some other point.
   */
  def distanceToPoint(p: FxPoint3D): Double = Math.sqrt(Pos.distanceToPoint(p))

  /**
   * Calls handleEvent for every event in the list.
   *
   * @see #handleEvent
   */
  def handleEvents() {
    // process the events in reverse order
    events.reverse.foreach(handleEvent)

    // all collisions are handled. clear the list.
    events = Nil
  }

  /**
   * Associates an event with this object
   */
  def +=(event: FxEvent) = events = event :: events

  /**
   * Paints this object on camera.
   */
  def paint(g: Graphics2D, cam: FxCamera) {
    modelInstance.clipAndPaint(g, cam)
  }

  def paintWithShading(g: Graphics2D, cam: FxCamera, light: FxPoint3D) {
    modelInstance.clipAndPaintWithShading(g, cam, light)
  }

  /**
   * Updates this object by delta-time seconds.
   */
  def update(dt: Double) {
    age += dt
  }

  /**
   * Updates the list of occupied grids. Moving objects will do this every
   * frame while static objects do it once.
   */
  protected def updateTheOccupiedGrids() {
    // get the new grids.
    occupiedGrids = world.map.getGridsForSphere(Pos, modelInstance.boundingRadius)

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

}
