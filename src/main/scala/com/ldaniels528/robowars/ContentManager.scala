package com.ldaniels528.robowars

import java.io._

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxConvexPolyhedron, FxPolyhedronLoader}

/**
 * Content Manager
 * @author lawrence.daniels@gmail.com
 */
object ContentManager {

  /**
   * Loads a model from the class path
   * @param path the resource path of the model
   */
  @throws[java.io.IOException]
  def loadModel(path: String): FxConvexPolyhedron = {
    FxPolyhedronLoader.readConvexPolyhedron(getResource(path))
  }

  def loadWorld(path: String) = {

  }

  def getResource(path: String): InputStream = {
    Option(getClass.getResource(path)) match {
      case Some(url) => url.openStream()
      case None =>
        throw new IllegalStateException(s"Resource '$path' not found")
    }
  }

}