package com.ldaniels528.fxcore3d

import java.awt.Graphics2D

import com.ldaniels528.fxcore3d.camera.FxCamera

/**
 * Abstract class that represents a moving object
 * @author lawrence.daniels@gmail.com
 */
abstract class FxMovingObject(world: FxWorld, pos: FxPoint3D, agl: FxAngle3D, dpos: FxVelocityVector, dagl: FxAngle3D)
  extends FxObject(world, pos, agl) {

  val dPos: FxVelocityVector = dpos.makeClone
  val dAgl: FxAngle3D = dagl.makeClone

  /**
   * Apply the affects the gravity
   * @param dt the given delta time
   */
  def applyGravity(dt: Double) {
    $dPosition.y += world.gravity * dt
  }

  def angle_=(a: FxAngle3D) = Agl.set(a)

  def position_=(p: FxPoint3D) {
    Pos.set(p)
    updateTheOccupiedGrids()
  }

  def dAngle: FxAngle3D = dAgl.makeClone

  def dAngle_=(a: FxAngle3D) = dAgl.set(a)

  def dPosition: FxVelocityVector = dPos.makeClone

  def dPosition_=(p: FxPoint3D) = dPos.set(p)

  protected def $dPosition = dPos

  /**
   * Overrides.
   */
  override def checkForCollisionWith(obj: FxObject, dt: Double): Boolean = {
    // update the position and angle of the polyhedron
    modelInstance.setOrientation(Pos, Agl)

    // if the other object is a moving object then update it too.
    if (obj.isInstanceOf[FxMovingObject]) {
      obj.modelInstance.setOrientation(obj.Pos, obj.Agl)
    }

    // let the overridden method check the collision
    super.checkForCollisionWith(obj, dt)
  }

  override def paint(g: Graphics2D, cam: FxCamera) {
    modelInstance.setOrientation(Pos, Agl)
    super.paint(g, cam)
  }

  override def paintWithShading(g: Graphics2D, cam: FxCamera, light: FxPoint3D) {
    modelInstance.setOrientation(Pos, Agl)
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
  private[fxcore3d] def updatePhysics(dt: Double) {
    Pos.plus(dPos, dt)
    Agl.plus(dAgl, dt)
  }

}