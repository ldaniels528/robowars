package org.combat.fxcore3d

import FxBoundingVolume._

/**
 * FxEngine Bounding Volume
 * @author lawrence.daniels@gmail.com
 */
case class FxBoundingVolume(theHostPolyInst: FxPolyhedronInstance, myScale: FxPoint3D) {
  protected val myRadius = myScale.magnitude()
  protected val myBox = FxArrayOf3DPoints(8)
  protected val myNormals = FxArrayOf3DPoints(6)

  if (ourBox == null) initClass()

  def checkForCollisionWith(volume: FxBoundingVolume): Boolean = {
    val pp = theHostPolyInst.getPosition().makeClone()
    pp.negate()
    pp.plus(volume.theHostPolyInst.getPosition())

    // check if the bounding spheres collide
    // so: if outside collision radius. no need for further checking
    if (pp.magnitude() > (myRadius + volume.myRadius)) false
    else {
      // update the world coordinates of the box
      this.updateBox()
      volume.updateBox()

      // rewrite 
      pointInMyVolume(volume.myBox) || volume.pointInMyVolume(myBox)
    }
  }

  def getBoundingRadius(): Double = myRadius

  def makeClone(): FxBoundingVolume = new FxBoundingVolume(theHostPolyInst, myScale)

  private def pointInMyVolume(otherBox: FxArrayOf3DPoints): Boolean = {
    // start checking if any point is within my volume
    val normal = new FxPoint3D()
    val point = new FxPoint3D()
    val vector = new FxPoint3D()

    for (p <- 0 to 7) {
      point.set(otherBox.x(p), otherBox.y(p), otherBox.z(p))
      var outside = false
      var n = 0
      while ((n < 6) && !outside) {
        // make the normal
        normal.set(myNormals.x(n), myNormals.y(n), myNormals.z(n))

        // make the vector from my normal to other point
        vector.set(myBox.x(n), myBox.y(n), myBox.z(n))
        vector.makeVectorTo(point)

        // check the dot product
        if (normal.dotProduct(vector) > 0) {
          // "in-front" of one of the planes. no collision
          outside = true
        }
        n += 1
      }

      if (!outside) return true
    }
    false
  }

  private def updateBox() {
    theHostPolyInst.transformPoints(ourBox, myBox)
    theHostPolyInst.rotateNormals(ourNormals, myNormals)
  }

}

/**
 * FxEngine Bounding Volume
 * @author lawrence.daniels@gmail.com
 */
object FxBoundingVolume {
  var ourBox: FxArrayOf3DPoints = _
  var ourNormals: FxArrayOf3DPoints = _

  def initClass() {
    // the vertices of our box
    //          0  1  2  3  4  5  6  7
    val px = Array[Double](1, -1, -1, 1, -1, 1, 1, -1)
    val py = Array[Double](1, -1, 1, -1, 1, -1, 1, -1)
    val pz = Array[Double](1, -1, 1, -1, -1, 1, -1, 1)
    ourBox = new FxArrayOf3DPoints(px, py, pz, 8)

    // the normals
    //          0  1  2  3  4  5
    val nx = Array[Double](0, 0, -1, 1, 0, 0)
    val ny = Array[Double](1, -1, 0, 0, 0, 0)
    val nz = Array[Double](0, 0, 0, 0, -1, 1)
    ourNormals = new FxArrayOf3DPoints(nx, ny, nz, 6)
  }

}
