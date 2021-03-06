package com.ldaniels528.fxcore3d

import com.ldaniels528.fxcore3d.FxBoundingVolume._
import com.ldaniels528.fxcore3d.polygon.FxModelInstance

/**
 * FxEngine Bounding Volume
 * @author lawrence.daniels@gmail.com
 */
case class FxBoundingVolume(theHostPolyInst: FxModelInstance, myScale: FxScale3D) {
  val boundingRadius = myScale.magnitude()
  protected val myBox = FxArrayOf3DPoints(8)
  protected val myNormals = FxArrayOf3DPoints(6)

  def checkForCollisionWith(volume: FxBoundingVolume): Boolean = {
    val pp = theHostPolyInst.position.makeClone
    pp.negate()
    pp += volume.theHostPolyInst.position

    // check if the bounding spheres collide
    // so: if outside collision radius. no need for further checking
    if (pp.magnitude() > (boundingRadius + volume.boundingRadius)) false
    else {
      // update the world coordinates of the box
      this.updateBox()
      volume.updateBox()

      // rewrite 
      pointInMyVolume(volume.myBox) || volume.pointInMyVolume(myBox)
    }
  }

  def makeClone: FxBoundingVolume = this.copy()

  private def pointInMyVolume(otherBox: FxArrayOf3DPoints): Boolean = {
    // start checking if any point is within my volume
    val point = FxPoint3D()
    val vector = FxPoint3D()

    otherBox.points exists { box =>
      point.set(box.x, box.y, box.z)
      !isPointOutSideVolume(point, vector)
    }
  }

  private def isPointOutSideVolume(point: FxPoint3D, vector: FxPoint3D): Boolean = {
    myNormals.points exists { normal =>
      // make the vector from my normal to other point
      val vp = myBox(normal.index)
      vector.set(vp.x, vp.y, vp.z)
      vector.makeVectorTo(point)

      // use the dot product to determine whether the point is
      // "in-front" of one of the planes. no collision
      normal.dotProduct(vector) > 0
    }
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

  // the vertices of our box
  val ourBox = {
    //                     0   1   2  3  4   5  6   7
    val px = Array[Double](1, -1, -1, 1, -1, 1, 1, -1)
    val py = Array[Double](1, -1, 1, -1, 1, -1, 1, -1)
    val pz = Array[Double](1, -1, 1, -1, -1, 1, -1, 1)
    FxArrayOf3DPoints(px, py, pz)
  }

  // the normals
  val ourNormals = {
    //                     0  1   2  3  4  5
    val nx = Array[Double](0, 0, -1, 1, 0, 0)
    val ny = Array[Double](1, -1, 0, 0, 0, 0)
    val nz = Array[Double](0, 0, 0, 0, -1, 1)
    FxArrayOf3DPoints(nx, ny, nz)
  }

}
