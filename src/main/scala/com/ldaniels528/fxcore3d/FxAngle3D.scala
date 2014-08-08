package com.ldaniels528.fxcore3d

/**
 * Represents an angle in 3D space
 * @author lawrence.daniels@gmail.com
 */
case class FxAngle3D(var x: Double = 0, var y: Double = 0, var z: Double = 0) {

  /**
   * @return a cloned copy of this instance
   */
  def makeClone = this.copy()

  def *(factor: Double) = FxAngle3D(x * factor, y * factor, z * factor)

  def *=(factor: Double): FxAngle3D = {
    x *= factor
    y *= factor
    z *= factor
    this
  }

  def negate() {
    x = -x
    y = -y
    z = -z
  }

  def +=(p: FxAngle3D): FxAngle3D = {
    x += p.x
    y += p.y
    z += p.z
    this
  }

  def plus(p: FxAngle3D, factor: Double) {
    x += p.x * factor
    y += p.y * factor
    z += p.z * factor
  }

  def set(a: FxAngle3D) {
    x = a.x
    y = a.y
    z = a.z
  }

  def set(x0: Double, y0: Double, z0: Double) {
    x = x0
    y = y0
    z = z0
  }

  override def toString = f"($x%.1f, $y%.1f, $z%.1f)"

}

