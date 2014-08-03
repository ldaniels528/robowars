package com.ldaniels528.fxcore3d.camera

import java.awt.Graphics2D
import scala.collection.mutable.ArrayBuffer

import com.ldaniels528.fxcore3d._

/**
 * FxEngine Basic Camera
 * @author lawrence.daniels@gmail.com
 */
class FxCamera(world: FxWorld, viewAngle: Double, viewDistance: Double, pos: FxPoint3D, agl: FxAngle3D)
  extends FxGenericCamera(viewAngle) {

  // create the light vector
  private var light = new FxPoint3D(-1, 0, 0)
  light.rotateAboutXaxis(Math.PI / 5)
  light.rotateAboutYaxis(Math.PI / 3)
  light.normalize(1)

  // set the camera's orientation
  setOrientation(pos, agl)

  /**
   * Sets the ambient light
   */
  def setAmbientLight(vect: FxPoint3D): Unit = light = vect.copy()

  def paint(g: Graphics2D) {
    // filter for only the objects within our bounding sphere
    val visibleObjects = world.getAllObjectsInSphere(myPosition, viewDistance, sphereIsVisible)

    // sort the objects from the closest to farthest Z-axis
    val sortedObjects = visibleObjects.sortBy(-_.distanceToPoint(myPosition))

    // paint the objects
    sortedObjects.foreach(_.paintWithShading(g, this, light))
  }

  /**
   * Updates the camera
   */
  override def update(dt: Double) = ()

  private def sphereIsVisible(pos: FxPoint3D, radius: Double): Boolean = {
    val p = new FxPoint3D()
    updateMatrix()
    myWCStoVCSmatrix.transformPoint(pos, p)
    (p.z - radius) <= Zclip
  }

}