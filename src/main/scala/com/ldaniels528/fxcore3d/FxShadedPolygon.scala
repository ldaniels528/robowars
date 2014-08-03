package com.ldaniels528.fxcore3d

import java.awt.Graphics2D

import com.ldaniels528.fxcore3d.camera.FxGenericCamera

/**
 * FxEngine Shaded Polygon
 * @author lawrence.daniels@gmail.com
 */
class FxShadedPolygon(myIndices: Array[Int], nbrIndices: Int, myColor: FxColor)
  extends FxClippableFilledPolygon(myIndices, nbrIndices, myColor) {

  var myNormal: FxPoint3D = _

  def calculateIntensityForLight(light: FxPoint3D) {
    myColor.setIntensity(light.dotProduct(myNormal))
  }

  override def clipAndPaintWithShading(g: Graphics2D, p: FxProjectedPoints, camera: FxGenericCamera, intensity: Double) {
    myColor.setIntensity(intensity)
    super.clipAndPaint(g, p, camera)
  }

  override def makeClone(): FxShadedPolygon = {
    val dst = new Array[Int](nbrIndices)
    System.arraycopy(myIndices, 0, dst, 0, nbrIndices)
    new FxShadedPolygon(dst, nbrIndices, myColor.copy())
  }

  override def paintWithShading(g: Graphics2D, x: Array[Int], y: Array[Int], intensity: Double) {
    myColor.setIntensity(intensity)
    super.paint(g, x, y)
  }

}

/**
 * FxShadedPolygon (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object FxShadedPolygon {

  def apply(myIndices: Array[Int], nbrIndices: Int, color: FxColor): FxShadedPolygon = {
    new FxShadedPolygon(myIndices, nbrIndices, color)
  }

}