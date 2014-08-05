package com.ldaniels528.fxcore3d

import com.ldaniels528.robowars.actors.AbstractActor

/**
 * Represents the virtual world in which all objects reside.
 * @author lawrence.daniels@gmail.com
 */
trait FxWorld {

  def Xmin: Double

  def Ymin: Double

  def size: Double

  def rows: Int

  /**
   * This world's map.
   */
  def map: FxMap

  /**
   * The time in this world.
   */
  def time: Double

  /**
   * The active player
   */
  def activePlayer: AbstractActor

  /**
   * The world's gravity
   */
  def gravity: Double

  /**
   * Reset the simulation.
   */
  def reset()

  /**
   * Inserts an object into the world. The object will be pushed into the stack
   * and then inserted first thing next round.
   */
  def +=(obj: FxObject): Unit

  /**
   * Update the world by delta-time seconds.
   */
  def update(dt: Double): Unit

  /**
   * Inserts all objects that are within radius into collection.
   */
  def getAllObjectsInRadius(pos: FxPoint3D, radius: Double): Seq[FxObject]

  /**
   * Inserts all objects that are within radius into collection.
   */
  def getAllObjectsInSphere(pos: FxPoint3D, radius: Double, sphereIsVisible: (FxPoint3D, Double) => Boolean): Seq[FxObject]

  /**
   * Inserts all objects that are within radius and in-front of the plane
   * specified by a point (pos) and a normal (norm).
   */
  def getAllObjectsInRadiusAndInfront(point: FxPoint3D, norm: FxPoint3D, radius: Double): Seq[FxObject]

}

/**
 * FxWorld Companion Object
 * @author lawrence.daniels@gmail.com
 */
object FxWorld {

  /**
   * Returns a point in a random position in space
   * @param range the range of each coordinate value
   * @return a [[FxPoint3D]]
   */
  def random3DPoint(range: Double): FxPoint3D = {
    FxPoint3D(rand(0, range), rand(0, range), rand(0, range))
  }

  /**
   * Returns a point in a random rotation in space
   * @param rotation the range of each rotational axis
   * @return a [[FxAngle3D]]
   */
  def random3DAngle(rotation: Double): FxAngle3D = {
    FxAngle3D(rand(-rotation, rotation), rand(-rotation, rotation), rand(-rotation, rotation))
  }

  /**
   * Returns a random number between b and e.
   */
  def rand(b: Double, e: Double): Double = (e - b) * Math.random() + b

  /**
   * Prints the class name and a string to stout.
   */
  def debug(source: Any, message: String) {
    System.out.println(s"***${source.getClass().getName()} : $message")
  }

}
