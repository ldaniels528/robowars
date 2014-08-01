package org.combat.fxcore3d

import org.combat.fxcore3d.camera.FxCamera
import java.awt.Graphics2D

/**
 * Abstract class that represents a moving object
 * @author lawrence.daniels@gmail.com
 */
class FxMovingObject(
  world: FxWorld,
  pos: FxPoint3D,
  agl: FxAngle3D,
  dpos: FxVelocityVector,
  dagl: FxAngle3D)
  extends FxObject(world, pos, agl) {
  
  var dPos: FxVelocityVector = dpos.makeClone()
  var dAgl: FxAngle3D = dagl.makeClone()

  /**
   * Overrides.
   */
  override def checkForCollisionWith(obj: FxObject, dt: Double): Boolean = {
    // update the position and angle of the polyhedron
    polyhedronInstance.setOrientation(Pos, Agl)
    
    // if the other object is a moving object. updates it too.
    if (obj.isInstanceOf[FxMovingObject]) {
      obj.polyhedronInstance.setOrientation(obj.Pos, obj.Agl)
    }
    
    // let the overridden method check the collision
    super.checkForCollisionWith(obj, dt)
  }

  override def paint(g: Graphics2D, cam: FxCamera) {
    polyhedronInstance.setOrientation(Pos, Agl)
    super.paint(g, cam)
  }

  override def paintWithShading(g: Graphics2D, cam: FxCamera, light: FxPoint3D) {
    polyhedronInstance.setOrientation(Pos, Agl)
    super.paintWithShading(g, cam, light)
  }

  /**
   * Overrides. Updates the moving object. It extends the regular static object.
   */
  override def update(dt: Double) {
    super.update(dt)
    
    // since this is a moving object it's physics must be updated.
    updatePhysics(dt)
    
    // must be updated each round since this object might have left the old grids and entered new ones.
    updateTheOccupiedGrids()
  }

  /**
   * Updates the physical state of this object.
   */
  protected def updatePhysics(dt: Double) {
    Pos.plus(dPos, dt)
    Agl.plus(dAgl, dt)
  }

  def getdAngle(): FxAngle3D = dAgl.makeClone()

  def getdPosition(): FxVelocityVector = dPos.makeClone()

  def setdPosition(p: FxPoint3D) = dPos.set(p)

  def setPosition(p: FxPoint3D) {
    Pos.set(p)
    updateTheOccupiedGrids()
  }

  def setdAngle(a: FxAngle3D) = dAgl.set(a)

  def setAngle(a: FxAngle3D) = Agl.set(a)

}