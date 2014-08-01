package org.combat.fxcore3d

import scala.collection.mutable.ArrayBuffer

/**
 * Represents a grid in FxMap
 * @see FxMap
 */
class FxGrid() {
  private val theMovingObjects = ArrayBuffer[FxMovingObject]()
  private val theStaticObjects = ArrayBuffer[FxObject]()

  /**
   * Retrieve all the objects within the specified radius into the supplied vector.
   */
  def getAllObjectsInRadius(p: FxPoint3D, radius: Double): Seq[FxObject] = {
    val radiusSq = radius * radius
    val listA = theMovingObjects filter (_.getPosition().distanceToPoint(p) <= radiusSq)
    val listB = theStaticObjects filter (_.getPosition().distanceToPoint(p) <= radiusSq)
    listA ++ listB
  }

  /**
   * Inserts an object into this zone
   */
  def insertObject(obj: FxObject) {
    obj match {
      case mo: FxMovingObject => theMovingObjects += mo
      case _ => theStaticObjects += obj
    }
    ()
  }

  /**
   * Removes an object from this zone
   */
  def removeObject(obj: FxObject) {
    obj match {
      case mo: FxMovingObject => theMovingObjects -= mo
      case _ => theStaticObjects -= obj
    }
    ()
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
