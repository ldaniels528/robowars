package org.combat.fxcore3d

import java.io.InputStream
import java.io.StreamTokenizer

/**
 * FxEngine Polyhedron Loader
 * @author ldaniels
 */
object FxPolyhedronLoader {

  /**
   * construct a polyhedron from a stream.
   */
  def readConvexPolyhedron(is: InputStream): FxConvexPolyhedron = {
    // make a stream reader
    val stream = getTokenizer(is)

    // get the points
    val vertices = readArrayOf3DPoints(is)

    // get the # polygons
    stream.nextToken()
    val nbrPolygons = stream.nval.toInt

    // create the vector
    val myPolygons = new Array[FxIndexingPolygon](nbrPolygons)

    // read each polygon
    for (n <- 0 to (nbrPolygons - 1)) {
      myPolygons(n) = readShadedPolygon(is)
    }

    new FxConvexPolyhedron(vertices, myPolygons, myPolygons.length)
  }

  /**
   * construct an array of 3d points from a stream
   */
  def readArrayOf3DPoints(is: InputStream): FxArrayOf3DPoints = {
    // make a stream reader
    val stream = getTokenizer(is)

    // get the # points
    stream.nextToken()
    val npoints = stream.nval.toInt

    // create the vectors
    val x = new Array[Double](npoints)
    val y = new Array[Double](npoints)
    val z = new Array[Double](npoints)

    // read the coordinates
    for (n <- 0 to (npoints - 1)) {
      stream.nextToken()
      x(n) = stream.nval.toDouble
      stream.nextToken()
      y(n) = stream.nval.toDouble
      stream.nextToken()
      z(n) = stream.nval.toDouble
    }
    new FxArrayOf3DPoints(x, y, z, npoints)
  }

  def readClippableFilledPolygon(is: InputStream): FxClippableFilledPolygon = {
    createColoredPolygon(is, FxClippableFilledPolygon.apply _)
  }

  def readFilledPolygon(is: InputStream): FxFilledPolygon = {
    createColoredPolygon(is, FxFilledPolygon.apply _)
  }

  def readShadedPolygon(is: InputStream): FxShadedPolygon = {
    createColoredPolygon(is, FxShadedPolygon.apply _)
  }

  def readWirePolygon(is: InputStream): FxWirePolygon = {
    createPolygon(is, FxWirePolygon.apply _)
  }

  /**
   * constructs a color from a stream
   */
  def readColor(is: InputStream): FxColor = {
    // make a stream reader
    val stream = getTokenizer(is)

    // read the RGB triple
    stream.nextToken()
    val r = stream.nval.toInt
    stream.nextToken()
    val g = stream.nval.toInt
    stream.nextToken()
    val b = stream.nval.toInt
    new FxColor(r, g, b)
  }

  private def createPolygon[T <: FxIndexingPolygon](is: InputStream, fx: (Array[Int], Int) => T): T = {
    // make a stream reader
    val stream = getTokenizer(is)
    
    // get the # of indicies in this polygon
    stream.nextToken()
    val nbrIndices = stream.nval.toInt

    // allocate the vector
    val myIndices = new Array[Int](nbrIndices)

    // read all indices
    for (i <- 0 to (nbrIndices - 1)) {
      stream.nextToken()
      myIndices(i) = stream.nval.toInt
    }

    // create the polygon
    fx(myIndices, nbrIndices)
  }

  private def createColoredPolygon[T <: FxIndexingPolygon](is: InputStream, fx: (Array[Int], Int, FxColor) => T): T = {
    // make a stream reader
    val stream = getTokenizer(is)

    // get the # of indices in this polygon
    stream.nextToken()
    val nbrIndices = stream.nval.toInt

    // allocate the vector
    val myIndices = new Array[Int](nbrIndices)

    // read all indices
    for (n <- 0 to (nbrIndices - 1)) {
      stream.nextToken()
      myIndices(n) = stream.nval.toInt
    }

    // create the polygon
    fx(myIndices, nbrIndices, readColor(is))
  }

  private def getTokenizer(is: InputStream): StreamTokenizer = {
    val stream = new StreamTokenizer(is)
    stream.commentChar('#')
    stream
  }

}