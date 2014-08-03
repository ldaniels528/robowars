package com.ldaniels528.fxcore3d.polygon

import java.awt.Graphics2D

import com.ldaniels528.fxcore3d.camera.FxCamera
import com.ldaniels528.fxcore3d.{FxColor, FxPoint3D, FxProjectedPoints}

/**
 * FxEngine Shaded Polygon
 * @author lawrence.daniels@gmail.com
 */
class FxShadedPolygon(myIndices: Seq[Int], myColor: FxColor) extends FxClippingFilledPolygon(myIndices, myColor) {

  var myNormal: FxPoint3D = _

  def calculateIntensityForLight(light: FxPoint3D) {
    myColor.setIntensity(light.dotProduct(myNormal))
  }

  override def clipAndPaintWithShading(g: Graphics2D, p: FxProjectedPoints, camera: FxCamera, intensity: Double) {
    myColor.setIntensity(intensity)
    super.clipAndPaint(g, p, camera)
  }

  override def makeClone(): FxShadedPolygon = {
    val dst = new Array[Int](myIndices.length)
    System.arraycopy(myIndices, 0, dst, 0, myIndices.length)
    new FxShadedPolygon(dst, myColor.copy())
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

  def apply(myIndices: Seq[Int], color: FxColor): FxShadedPolygon = {
    new FxShadedPolygon(myIndices, color)
  }

}