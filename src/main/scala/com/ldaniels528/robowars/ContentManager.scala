package com.ldaniels528.robowars

import java.awt.image.BufferedImage
import java.io._
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
  def loadImage(path: String): BufferedImage = {
    Option(getClass.getResource(path)) match {
      case Some(url) => ImageIO.read(url)
      case None =>
        throw new IllegalStateException(s"Image '$path' not found")
    }
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
  def getResource(path: String): InputStream = {
    Option(getClass.getResource(path)) match {
      case Some(url) => url.openStream()
      case None =>
        throw new IllegalStateException(s"Resource '$path' not found")
    }
  }

}