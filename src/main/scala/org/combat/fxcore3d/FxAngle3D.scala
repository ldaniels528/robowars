package org.combat.fxcore3d

/**
 * Represents an angle in 3D space
 * @author lawrence.daniels@gmail.com
 */
case class FxAngle3D(var x: Double = 0, var y: Double = 0, var z: Double = 0) {

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

  def times(s: Double) {
    x *= s
    y *= s
    z *= s
  }

  def makeClone(): FxAngle3D = this.copy()

  def negate() {
    x = -x
    y = -y
    z = -z
  }

  def plus(p: FxAngle3D) {
    x += p.x
    y += p.y
    z += p.z
  }

  def plus(p: FxAngle3D, factor: Double) {
    x += p.x * factor
    y += p.y * factor
    z += p.z * factor
  }

  override def toString() = f"($x%.1f, $y%.1f, $z%.1f)"

}

