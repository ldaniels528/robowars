package org.combat.game

import java.io._
import org.combat.fxcore3d._

/**
 * Content Manager
 * @author lawrence.daniels@gmail.com
 */
object ContentManager {
  val RESOURCE_DIR = new File("./src/main/resources")

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
    new FileInputStream(new File(RESOURCE_DIR, path).getCanonicalFile())
  }

}