package org.combat.fxcore3d

/**
 * A class that encapsulates and array of 3d points.
 * @author lawrence.daniels@gmail.com
 */
case class FxArrayOf3DPoints(x: Array[Double], y: Array[Double], z: Array[Double], var npoints: Int) {

  /**
   * Returns a clone.
   */
  def makeClone(): FxArrayOf3DPoints = {
    val xnew = new Array[Double](npoints)
    val ynew = new Array[Double](npoints)
    val znew = new Array[Double](npoints)
    System.arraycopy(x, 0, xnew, 0, npoints)
    System.arraycopy(y, 0, ynew, 0, npoints)
    System.arraycopy(z, 0, znew, 0, npoints)
    new FxArrayOf3DPoints(xnew, ynew, znew, npoints)
  }

  /**
   * ovrrides the Object method
   */
  override def toString = {
    val sb = new StringBuilder(npoints * 10)
    for (n <- 0 to (npoints - 1)) {
      sb.append(",(%.1f, %.1f, %.1f)".format(x(n), y(n), z(n)))
    }
    sb.substring(1)
  }
}

/**
 * FxArrayOf3DPoints Companion Object
 * @author lawrence.daniels@gmail.com
 */
object FxArrayOf3DPoints {

  /**
   * Constructs an empty array of 3d points with size "n"
   */
  def apply(npoints: Int): FxArrayOf3DPoints = {
    val x = new Array[Double](npoints)
    val y = new Array[Double](npoints)
    val z = new Array[Double](npoints)
    new FxArrayOf3DPoints(x, y, z, npoints)
  }

}