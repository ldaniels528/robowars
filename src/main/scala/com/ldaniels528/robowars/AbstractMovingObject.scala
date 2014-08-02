package com.ldaniels528.robowars

import com.ldaniels528.fxcore3d._
import scala.beans.BeanProperty

/**
 * Abstract Moving Object
 * @author lawrence.daniels@gmail.com
 */
class AbstractMovingObject(
  theWorld: FxWorld,
  val pos: FxPoint3D,
  val agl: FxAngle3D,
  val dpos: FxVelocityVector,
  val dagl: FxAngle3D,
  @BeanProperty var health: Double = Double.MaxValue)
  extends FxMovingObject(theWorld, pos, agl, dpos, dagl) {

  def damageHealth(delta: Double): Double = {
    health -= delta
    health
  }

}

/**
 * AbstractMovingObject Companion Object
 * @author lawrence.daniels@gmail.com
 */
object AbstractMovingObject {

  def apply(w: FxWorld, pos: FxPoint3D, agl: FxAngle3D, dpos: FxVelocityVector, dagl: FxAngle3D): AbstractMovingObject = {
    new AbstractMovingObject(w, pos, agl, dpos, dagl)
  }

}