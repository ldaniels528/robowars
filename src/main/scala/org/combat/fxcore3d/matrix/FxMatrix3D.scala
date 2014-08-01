package org.combat.fxcore3d.matrix

import org.combat.fxcore3d._

/**
 * A 3D matrix that hides the making of the different transforms
 */
class FxMatrix3D() extends FxGeneric3DMatrix() {

  /**
   * let matrix contain the MCS to WCS transform
   */
  def makeMCStoWCStransform(pos: FxPoint3D, agl: FxAngle3D, scale: FxPoint3D) {
    makeIdentity()
    concatS(scale.x, scale.y, scale.z)
    concatRx(agl.x)
    concatRy(agl.y)
    concatRz(agl.z)
    concatT(pos.x, pos.y, pos.z)
  }

  /**
   * let matrix contain the WCS to MCS transform
   */
  def makeWCStoVCStransform(pos: FxPoint3D, agl: FxAngle3D) {
    makeIdentity()
    concatT(-pos.x, -pos.y, -pos.z)
    concatRz(-agl.z)
    concatRy(-agl.y)
    concatRx(-agl.x)
  }

  def makeLookAtPointTransform(p0: FxPoint3D, p1: FxPoint3D) {
    val vecZaxis = p0.vectorTo(p1)
    vecZaxis.normalize(1)

    val vecXaxis = new FxPoint3D()
    vecXaxis.vectorProduct(new FxPoint3D(0, 1, 0), p1)
    vecXaxis.normalize(1)

    val vecYaxis = new FxPoint3D()
    vecYaxis.vectorProduct(vecZaxis, vecXaxis)

    xo = -p0.x
    yo = -p0.y
    zo = -p0.z
      
    xx = vecXaxis.x; xy = vecXaxis.y; xz = vecXaxis.z;
    yx = vecYaxis.x; yy = vecYaxis.y; yz = vecYaxis.z;
    zx = vecZaxis.x; zy = vecZaxis.y; zz = vecZaxis.z;
  }

}