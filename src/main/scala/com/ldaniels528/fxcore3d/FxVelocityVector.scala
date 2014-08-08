package com.ldaniels528.fxcore3d

/**
 * FXEngine Velocity Vector
 * @author lawrence.daniels@gmail.com
 */
class FxVelocityVector() extends FxPoint3D() {
  var Ay: Double = _
  var Ax: Double = _
  private var myVelocity: Double = _

  override def makeClone: FxVelocityVector = FxVelocityVector(Ay, Ax, myVelocity, x, y, z)

  def setVelocity(nv: Double) {
    myVelocity = nv
    calculateComponents()
  }

  def velocity: Double = myVelocity

  def angle: FxAngle3D = new FxAngle3D(Ax, Ay, 0)

  def getAngleAboutAxisY(): Double = Ay

  def setAngleAboutAxisY(a: Double) {
    Ay = a
    calculateComponents()
  }

  def setAngleAboutAxisX(a: Double) {
    Ax = a
    calculateComponents()
  }

  def increaseAngleAboutAxisY(da: Double) {
    Ay += da
    calculateComponents()
  }

  def increaseVelocity(dv: Double) {
    myVelocity += dv
    calculateComponents()
  }

  def increasePitch(da: Double) {
    Ax += da
    calculateComponents()
  }

  def synchronizeAngle(a: FxAngle3D) = a.set(Ax, Ay, 0)

  override def negate(): FxVelocityVector = {
    myVelocity = -myVelocity
    super.negate()
    this
  }

  override def set(p: FxPoint3D) {
    super.set(p)

    p match {
      case vv: FxVelocityVector =>
        Ay = vv.Ay
        Ax = vv.Ax
        myVelocity = vv.myVelocity
      case _ =>
    }
  }

  private def calculateComponents() {
    z = -myVelocity
    x = 0
    y = 0
    rotateAboutAxisX(-Ax)
    rotateAboutAxisY(Ay)
  }

  override def toString = s"${super.toString} $Ay $Ax $myVelocity"

}

/**
 * FxVelocityVector Companion Object
 * @author lawrence.daniels@gmail.com
 */
object FxVelocityVector {

  def apply(): FxVelocityVector = new FxVelocityVector()

  def apply(Ay: Double, Ax: Double, velocity: Double): FxVelocityVector = {
    val vv = new FxVelocityVector()
    vv.Ax = Ax
    vv.Ay = Ay
    vv.myVelocity = velocity
    vv.calculateComponents()
    vv
  }

  def apply(Ay: Double, Ax: Double, velocity: Double, x: Double, y: Double, z: Double): FxVelocityVector = {
    val vv = new FxVelocityVector()
    vv.Ax = Ax
    vv.Ay = Ay
    vv.myVelocity = velocity
    vv.x = x
    vv.y = y
    vv.z = z
    vv
  }
}

