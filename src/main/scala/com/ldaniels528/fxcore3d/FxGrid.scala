package com.ldaniels528.fxcore3d

import scala.collection.mutable.ListBuffer

/**
 * Represents a grid in FxMap
 * @see [[FxMap]]
 */
class FxGrid() {
  private val theMovingObjects = ListBuffer[FxObject]()
  private val theStaticObjects = ListBuffer[FxObject]()

  /**
   * Retrieve all the objects within the specified radius into the supplied vector.
   */
  def getAllObjectsInRadius(p: FxPoint3D, radiusSq: Double): Seq[FxObject] = {
    val listA = theMovingObjects filter (_.position.distanceToPoint(p) <= radiusSq)
    val listB = theStaticObjects filter (_.position.distanceToPoint(p) <= radiusSq)
    listA ++ listB
  }

  /**
   * Inserts an object into this zone
   */
  def insertObject(obj: FxObject) {
    val bucket = if (obj.isInstanceOf[FxMovingObject]) theMovingObjects else theStaticObjects
    bucket += obj
  }

  /**
   * Removes an object from this zone
   */
  def removeObject(obj: FxObject) {
    val bucket = if (obj.isInstanceOf[FxMovingObject]) theMovingObjects else theStaticObjects
    bucket -= obj
  }

  /**
   * Removes all moving object from this zone
   */
  def removeAllMovingObjects(): Unit = theMovingObjects.clear()

  /**
   * Detects all collisions in this grid.
   */
  def detectCollisions(dt: Double) {
    // check for collision between moving objects if there is more than
    // one moving object in this grid
    theMovingObjects foreach { objA =>
      // check for collisions with other moving objects
      theMovingObjects foreach { objB =>
        if (objA != objB) {
          // check if object A is intersected in a collision with object B (or vice versa)
          val a_in_b = objA.interestedOfCollisionWith(objB)
          val b_in_a = objB.interestedOfCollisionWith(objA)
          if (a_in_b || b_in_a) {
            if (objA.checkForCollisionWith(objB, dt)) {
              // add the collision to the interested parts.
              if (a_in_b) objA.collisionWith(objB, dt)
              if (b_in_a) objB.collisionWith(objA, dt)
            }
          }
        }
      }

      // check for collisions with stationary objects
      theStaticObjects foreach { objB =>
        // check if the moving object is intersected in a collision with the static object
        val a_in_b = objA.interestedOfCollisionWith(objB)
        val b_in_a = objB.interestedOfCollisionWith(objA)
        if (a_in_b || b_in_a) {
          // if either is true ...
          if (objA.checkForCollisionWith(objB, dt)) {
            // add the collision to the interested parts.
            if (a_in_b) objA.collisionWith(objB, dt)
            if (b_in_a) objB.collisionWith(objA, dt)
          }
        }
      }
    }

  }

}
