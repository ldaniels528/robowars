package com.ldaniels528.fxcore3d

/**
 * Represents a point in 3D space
 * @author ldaniels
 */
case class FxPoint3D(var x: Double = 0, var y: Double = 0, var z: Double = 0) {

  def distanceToPoint(p: FxPoint3D): Double = {
    val dx = p.x - x
    val dy = p.y - y
    val dz = p.z - z
    dx * dx + dy * dy + dz * dz
  }

  def dotProduct(p: FxPoint3D): Double = p.x * x + p.y * y + p.z * z

  def magnitude(): Double = Math.sqrt(x * x + y * y + z * z)

  def makeClone(): FxPoint3D = new FxPoint3D(x, y, z)

  def makeVectorTo(p: FxPoint3D) {
    x = p.x - x
    y = p.y - y
    z = p.z - z
  }

  protected def maxComponent(): Double = Math.max(Math.max(x, y), z)

  def negate() {
    x = -x
    y = -y
    z = -z
  }

  def normalize(length: Double) {
    val t = length / Math.sqrt(x * x + y * y + z * z)
    x = t * x
    y = t * y
    z = t * z
  }

  def +=(a: Double) {
    x += a
    y += a
    z += a
  }

  def +=(p: FxPoint3D) {
    x += p.x
    y += p.y
    z += p.z
  }

  def plus(p: FxPoint3D, factor: Double) {
    x += p.x * factor
    y += p.y * factor
    z += p.z * factor
  }

  def rotateAboutYaxis(a: Double) {
    val ca = Math.cos(a)
    val sa = Math.sin(a)
    val nx = ca * x + sa * z
    val nz = -sa * x + ca * z
    x = nx
    z = nz
  }

  def rotateAboutXaxis(a: Double) {
    val ca = Math.cos(a)
    val sa = Math.sin(a)
    val ny = ca * y - sa * z
    val nz = sa * y + ca * z
    y = ny
    z = nz
  }

  def rotateAboutZaxis(a: Double) {
    val ca = Math.cos(a)
    val sa = Math.sin(a)
    val nx = ca * x - sa * y
    val ny = sa * x + ca * y
    y = nx
    y = ny
  }

  def set(p: FxPoint3D) {
    x = p.x
    y = p.y
    z = p.z
  }

  def set(x0: Double, y0: Double, z0: Double) {
    x = x0
    y = y0
    z = z0
  }

  def *=(s: Double) = {
    x *= s
    y *= s
    z *= s
  }
  
  def vectorTo(p: FxPoint3D): FxPoint3D = {
    new FxPoint3D(p.x - x, p.y - y, p.z - z)
  }

  def vectorProduct(p1: FxPoint3D, p2: FxPoint3D) {
    x = p1.y * p2.z - p1.z * p2.y
    y = p1.z * p2.x - p1.x * p2.z
    z = p1.x * p2.y - p1.y * p2.x
  }

  override def toString() = f"($x%.1f, $y%.1f, $z%.1f)"

}
