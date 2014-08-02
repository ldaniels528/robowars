package com.ldaniels528.fxcore3d

/**
 * FXEngine Velocity Vector
 * @author lawrence.daniels@gmail.com
 */
class FxVelocityVector() extends FxPoint3D() {
  var Ay: Double = _
  var Ax: Double = _
  var velocity: Double = _

  def setVelocity(nv: Double) {
    velocity = nv
    calculateComponents()
  }

  def getVelocity(): Double = velocity

  def getAngle(): FxAngle3D = new FxAngle3D(Ax, Ay, 0)

  def getAngleAboutYaxis(): Double = Ay

  def setAngleAboutYaxis(a: Double) {
    Ay = a
    calculateComponents()
  }

  def setAngleAboutXaxis(a: Double) {
    Ax = a
    calculateComponents()
  }

  def increaseAngleAboutYaxis(da: Double) {
    Ay += da
    calculateComponents()
  }

  def increaseVelocity(dv: Double) {
    velocity += dv
    calculateComponents()
  }

  def increasePitch(da: Double) {
    Ax += da
    calculateComponents()
  }

  def synchronizeAngle(a: FxAngle3D) = a.set(Ax, Ay, 0)

  override def makeClone() = FxVelocityVector(Ay, Ax, velocity, x, y, z)

  override def negate() {
    velocity = -velocity
    super.negate()
  }

  override def set(p: FxPoint3D) {
    super.set(p)

    p match {
      case vv: FxVelocityVector =>
        Ay = vv.Ay
        Ax = vv.Ax
        velocity = vv.velocity
      case _ =>
    }
  }

  private def calculateComponents() {
    z = -velocity
    x = 0
    y = 0
    rotateAboutXaxis(-Ax)
    rotateAboutYaxis(Ay)
  }

  override def toString = s"${super.toString} $Ay $Ax $velocity"

}

/**
 * FxVelocityVector Companion Object
 * @author lawrence.daniels@gmail.com
 */
object FxVelocityVector {

  def apply(Ay: Double, Ax: Double, velocity: Double): FxVelocityVector = {
    val vv = new FxVelocityVector()
    vv.Ax = Ax
    vv.Ay = Ay
    vv.velocity = velocity
    vv.calculateComponents()
    vv
  }

  def apply(Ay: Double, Ax: Double, velocity: Double, x: Double, y: Double, z: Double): FxVelocityVector = {
    val vv = new FxVelocityVector()
    vv.Ax = Ax
    vv.Ay = Ay
    vv.velocity = velocity
    vv.x = x
    vv.y = y
    vv.z = z
    vv
  }
}

