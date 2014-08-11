package com.ldaniels528.fxcore3d

/**
 * Represents a virtual map of the world divided into grids which are populated
 * by objects. The map is only used to optimize collision detection.
 *
 * minX,minY ----+--| ----+--| ----+----minX+size,minY+size
 * @param rows Number of rows and columns in this map. Rows = columns => map should be a square.
 * @param xmin  My left-,topmost point.
 * @param ymin  My left-,topmost point.
 * @see [[FxGrid]]
 */
case class FxMap(xmin: Double, ymin: Double, size: Double, rows: Int) {

  /**
   * Number of grids in this map.
   */
  private val nbrGrids: Int = rows * rows

  /**
   * My grids.
   */
  private val myGrids: Seq[FxGrid] = (1 to nbrGrids) map (n => new FxGrid())

  /**
   * The width of a grid in world coordinates.
   */
  private val gridSize: Double = size / rows.toDouble

  /**
   * Inserts the grids that are within the sphere into the supplied vector.
   */
  def getGridsForSphere(p: FxPoint3D, radius: Double): Seq[FxGrid] = {
    val x = p.x
    val y = p.z

    // calculate the number of grids needed to cover the radius
    val xStart = getGridX(x - radius)
    val xEnd = getGridX(x + radius)

    // calculate the starting y position in the map
    val yStart = getGridY(y - radius)
    val yEnd = getGridY(y + radius)

    // insert all those grids into the array provided
    for {
      yy <- yStart to yEnd
      xx <- xStart to xEnd
    } yield myGrids(yy * rows + xx)
  }

  /**
   * Retrieve all the objects within the specified radius into the supplied vector.
   */
  def getAllObjectsInRadius(p: FxPoint3D, radius: Double): Seq[FxObject] = {
    // calculate the number of grids needed to cover the radius
    val xStart = getGridX(p.x - radius)
    val xEnd = getGridX(p.x + radius)

    // calculate the starting y position in the map
    val yStart = getGridY(p.y - radius)
    val yEnd = getGridY(p.y + radius)

    // compute the square radius
    val radiusSq = radius * radius

    // filter for only the objects within the square radius
    (yStart to yEnd) flatMap { yy =>
      (xStart to xEnd) flatMap { xx =>
        myGrids(yy * rows + xx).getAllObjectsInRadius(p, radiusSq)
      }
    }
  }

  def getAllObjectsInSphere(p: FxPoint3D, radius: Double, sphereIsVisible: (FxPoint3D, Double) => Boolean): Seq[FxObject] = {
    // calculate the number of grids needed to cover the radius
    val xStart = getGridX(p.x - radius)
    val xEnd = getGridX(p.x + radius)

    // calculate the starting y position in the map
    val yStart = getGridY(p.y - radius)
    val yEnd = getGridY(p.y + radius)

    // compute the square radius
    val radiusSq = radius * radius

    // filter for only the objects within the square radius
    (yStart to yEnd) flatMap { yy =>
      (xStart to xEnd) flatMap { xx =>
        myGrids(yy * rows + xx).getAllObjectsInRadius(p, radiusSq) filter { obj =>
          val inst = obj.modelInstance
          sphereIsVisible(inst.position, inst.boundingRadius)
        }
      }
    }
  }

  /**
   * Retrieve all the objects within the specified radius into the supplied vector and in-front of the plane
   */
  def getAllObjectsInRadiusAndInFront(p: FxPoint3D, dir: FxPoint3D, radius: Double): Seq[FxObject] = {
    // calculate the number of grids needed to cover the radius
    val xStart = getGridX(p.x - radius)
    val xEnd = getGridX(p.x + radius)

    // calculate the starting y position in the map
    val yStart = getGridY(p.y - radius)
    val yEnd = getGridY(p.y + radius)

    // compute the square radius
    val radiusSq = radius * radius

    // filter for only the objects within the square radius
    (yStart to yEnd) flatMap { yy =>
      (xStart to xEnd) flatMap { xx =>
        myGrids(yy * rows + xx).getAllObjectsInRadius(p, radiusSq) filter { obj =>
          val vector = p.vectorTo(obj.position)
          vector.dotProduct(dir) < 0
        }
      }
    }
  }

  /**
   * Detects all collisions between objects in this map.
   */
  def detectCollisions(dt: Double) {
    myGrids.foreach(_.detectCollisions(dt))
  }

  /**
   * Update the map by "dt" seconds.
   */
  def update(dt: Double) {
    myGrids.foreach(_.removeAllMovingObjects())
  }

  private def getGridX(x: Double): Int = {
    val xx = ((x - xmin) / gridSize).toInt
    Math.min(Math.max(0, xx), rows - 1)
  }

  private def getGridY(y: Double): Int = {
    val yy = ((y - ymin) / gridSize).toInt
    Math.min(Math.max(0, yy), rows - 1)
  }

}
