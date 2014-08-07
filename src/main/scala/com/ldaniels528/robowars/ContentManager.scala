package com.ldaniels528.robowars

import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.{FileNotFoundException, InputStream}
import javax.imageio.ImageIO

import com.ldaniels528.fxcore3d.polygon.{FxConvexPolyhedron, FxPolyhedronLoader}

/**
 * Content Manager
 * @author lawrence.daniels@gmail.com
 */
object ContentManager {

  /**
   * Retrieves an image from the classpath
   * @param path the given resource path
   * @return a [[BufferedImage]]
   */
  @throws[java.io.FileNotFoundException]
  def loadImage(path: String): BufferedImage = {
    Option(getClass.getResource(path)) match {
      case Some(url) => ImageIO.read(url)
      case None =>
        throw new FileNotFoundException(s"Image '$path' not found")
    }
  }

  /**
   * Retrieves a scaled image from the classpath
   * @param path the given resource path
   * @param width the desired width
   * @param height the desired height
   * @return the scaled [[BufferedImage]]
   */
  @throws[java.io.FileNotFoundException]
  def loadImage(path: String, width: Int, height: Int): BufferedImage = {
    scaleImage(loadImage(path), width, height)
  }

  /**
   * Scales the given image to the given width and height
   * @param image the source image
   * @param width the desired width
   * @param height the desired height
   * @return the scaled [[BufferedImage]]
   */
  private def scaleImage(image: BufferedImage, width: Int, height: Int): BufferedImage = {
    // create the scaled image buffer
    val scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR)

    // determine the X- and Y-axis scale
    val sx = width.toDouble / image.getWidth.toDouble
    val sy = height.toDouble / image.getHeight.toDouble

    // render the new image
    val g = scaledImage.getGraphics.asInstanceOf[Graphics2D]
    g.setTransform({
      val tx = g.getTransform
      tx.scale(sx, sy)
      tx
    })
    g.drawImage(image, 0, 0, null)
    scaledImage
  }

  /**
   * Loads a model from the class path
   * @param path the resource path of the model
   */
  @throws[java.io.IOException]
  def loadModel(path: String): FxConvexPolyhedron = {
    FxPolyhedronLoader.readConvexPolyhedron(getResource(path))
  }

  /**
   * Retrieves a virtual model
   * @param path the given resource path
   * @return a [[VirtualWorld]]
   */
  def loadWorld(path: String): VirtualWorld = {
    VirtualWorldReader.load(path)
  }

  /**
   * Returns an input stream representing the data of the given resource path
   * @param path the given resource path
   * @return an [[InputStream]]
   */
  @throws[java.io.FileNotFoundException]
  def getResource(path: String): InputStream = {
    Option(getClass.getResource(path)) match {
      case Some(url) => url.openStream()
      case None =>
        throw new FileNotFoundException(s"Resource '$path' not found")
    }
  }

}