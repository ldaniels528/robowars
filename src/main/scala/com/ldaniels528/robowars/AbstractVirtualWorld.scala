package com.ldaniels528.robowars

import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.objects.vehicles.AbstractVehicle

import scala.collection.mutable.ListBuffer

/**
 * Represents the virtual world in which all objects reside.
 * @author lawrence.daniels@gmail.com
 */
abstract class AbstractVirtualWorld(Xmin: Double, Ymin: Double, size: Double, rows: Int) extends FxWorld {

  /**
   * This world's map.
   */
  val map = new FxMap(Xmin, Ymin, size, rows)

  /**
   * All objects in this world.
   */
  val myObjects = ListBuffer[FxObject]()

  /**
   * Stack of new objects that should enter the world next round.
   */
  private val newObjects = ListBuffer[FxObject]()

  /**
   * The time in this world.
   */
  var time: Double = 0

  /**
   * The active player
   */
  def activePlayer: AbstractVehicle

  /**
   * The world's gravity
   */
  def gravity: Double

  /**
   * Reset the simulation.
   */
  def reset() {
    myObjects.clear()
    newObjects.clear()
    time = 0.0
  }

  /**
   * Inserts an object into the world. The object will be pushed into the stack
   * and then inserted first thing next round.
   */
  def +=(obj: FxObject) = {
    newObjects += obj
    ()
  }

  /**
   * Update the world by delta-time seconds.
   */
  def update(dt: Double) {
    time += dt

    // -- insert new objects.
    insertNewObjects()

    // -- let extended worlds make their intervention.
    devineIntervention()

    // -- clear map
    map.update(dt)

    // -- update all objects.
    updateObjects(dt)

    // -- detect collisions.
    map.detectCollisions(dt)

    // -- let objects handle their events.
    handleEvents()
  }

  /**
   * Here is a chance for extended worlds to affect their objects.
   */
  protected def devineIntervention() {
    // do nothing by default
  }

  /**
   * Updates all objects.
   */
  private def updateObjects(dt: Double) {
    // create a container for removing dead objects
    val deadObjects = ListBuffer[FxObject]()

    // update all living objects
    myObjects.foreach { obj =>
      if (obj.isAlive) obj.update(dt)
      else {
        // schedule the object for removal
        deadObjects += obj
      }
    }

    // remove the dead objects
    if (deadObjects.nonEmpty) {
      myObjects --= deadObjects
    }
    ()
  }

  /**
   * Instructs all objects to handle their collisions.
   */
  private def handleEvents() {
    myObjects.foreach(_.handleEvents())
  }

  /**
   * Inserts all new objects from the stack into the world.
   */
  private def insertNewObjects() {
    if (newObjects.nonEmpty) {
      myObjects ++= newObjects
      newObjects.clear()
    }
  }

  /**
   * Inserts all objects that are within radius into collection.
   */
  def getAllObjectsInRadius(pos: FxPoint3D, radius: Double): Seq[FxObject] = {
    map.getAllObjectsInRadius(pos, radius)
  }

  /**
   * Inserts all objects that are within radius into collection.
   */
  def getAllObjectsInSphere(pos: FxPoint3D, radius: Double, sphereIsVisible: (FxPoint3D, Double) => Boolean): Seq[FxObject] = {
    map.getAllObjectsInSphere(pos, radius, sphereIsVisible)
  }

  /**
   * Inserts all objects that are within radius and in-front of the plane
   * specified by a point (pos) and a normal (norm).
   */
  def getAllObjectsInRadiusAndInFront(point: FxPoint3D, norm: FxPoint3D, radius: Double): Seq[FxObject] = {
    map.getAllObjectsInRadiusAndInFront(point, norm, radius)
  }

}