package com.ldaniels528.fxcore3d

/**
 * A 3D matrix that hides the making of the different transforms
 * @author lawrence.daniels@gmail.com
 */
class FxMatrix3D() {
  var xx, xy, xz, xo: Double = _
  var yx, yy, yz, yo: Double = _
  var zx, zy, zz, zo: Double = _

  setIdentity()

  /**
   * "Smart" multiplies a rotation about Z-axis
   */
  def concatRz(az: Double) {
    val ct = Math.cos(az)
    val st = Math.sin(az)

    val nyx = (yx * ct + xx * st)
    val nyy = (yy * ct + xy * st)
    val nyz = (yz * ct + xz * st)
    val nyo = (yo * ct + xo * st)

    val nxx = (xx * ct - yx * st)
    val nxy = (xy * ct - yy * st)
    val nxz = (xz * ct - yz * st)
    val nxo = (xo * ct - yo * st)

    xx = nxx; xy = nxy; xz = nxz; xo = nxo
    yx = nyx; yy = nyy; yz = nyz; yo = nyo
  }

  /**
   * "Smart" multiplies a rotation about Y-axis
   */
  def concatRy(ay: Double) {
    val ct = Math.cos(ay)
    val st = Math.sin(ay)

    val nxx = (xx * ct + zx * st)
    val nxy = (xy * ct + zy * st)
    val nxz = (xz * ct + zz * st)
    val nxo = (xo * ct + zo * st)

    val nzx = (zx * ct - xx * st)
    val nzy = (zy * ct - xy * st)
    val nzz = (zz * ct - xz * st)
    val nzo = (zo * ct - xo * st)

    xx = nxx; xy = nxy; xz = nxz; xo = nxo
    zx = nzx; zy = nzy; zz = nzz; zo = nzo
  }

  /**
   * "Smart" multiplies a rotation about X-axis
   */
  def concatRx(ax: Double) {
    val ct = Math.cos(ax)
    val st = Math.sin(ax)

    val nyx = (yx * ct + zx * st)
    val nyy = (yy * ct + zy * st)
    val nyz = (yz * ct + zz * st)
    val nyo = (yo * ct + zo * st)

    val nzx = (zx * ct - yx * st)
    val nzy = (zy * ct - yy * st)
    val nzz = (zz * ct - yz * st)
    val nzo = (zo * ct - yo * st)

    yx = nyx; yy = nyy; yz = nyz; yo = nyo
    zx = nzx; zy = nzy; zz = nzz; zo = nzo
  }

  /**
   * "Smart" multiplies a translation
   */
  def concatT(x: Double, y: Double, z: Double) {
    xo += x
    yo += y
    zo += z
  }

  /**
   * "Smart" multiplies scaling
   */
  def concatS(sx: Double, sy: Double, sz: Double) {
    xx *= sx; xy *= sx; xz *= sx; xo *= sx
    yx *= sy; yy *= sy; yz *= sy; yo *= sy
    zx *= sz; zy *= sz; zz *= sz; zo *= sz
  }

  def rotate(ps: FxArrayOf3DPoints, pd: FxArrayOf3DPoints) {
    (0 to (ps.npoints - 1)) foreach { n =>
      val (x,y,z) = ps(n)
      pd.x(n) = x * xx + y * xy + z * xz
      pd.y(n) = x * yx + y * yy + z * yz
      pd.z(n) = x * zx + y * zy + z * zz
    }
  }

  /**
   * Multiplies the vector "ps" of 3d points and stores the result
   * in "pd".
   */
  def transform(ps: FxArrayOf3DPoints, pd: FxArrayOf3DPoints) {
    (0 to (ps.npoints - 1)) foreach { n =>
      val (x,y,z) = ps(n)
      pd.x(n) = x * xx + y * xy + z * xz + xo
      pd.y(n) = x * yx + y * yy + z * yz + yo
      pd.z(n) = x * zx + y * zy + z * zz + zo
    }
  }

  def transformPoint(ps: FxPoint3D, pd: FxPoint3D) {
    val x = ps.x
    val y = ps.y
    val z = ps.z

    pd.x = x * xx + y * xy + z * xz + xo
    pd.y = x * yx + y * yy + z * yz + yo
    pd.z = x * zx + y * zy + z * zz + zo
  }

  //-- special transform
  def transformWithTranslation(ps: FxArrayOf3DPoints, pd: FxArrayOf3DPoints, et: FxPoint3D) {
    val xt = et.x
    val yt = et.y
    val zt = et.z

    (0 to (ps.npoints - 1)) foreach { n =>
      val x = ps.x(n) + xt
      val y = ps.y(n) + yt
      val z = ps.z(n) + zt

      pd.x(n) = x * xx + y * xy + z * xz + xo
      pd.y(n) = x * yx + y * yy + z * yz + yo
      pd.z(n) = x * zx + y * zy + z * zz + zo
    }
  }

  /**
   * Resets the matrix
   */
  def setIdentity() {
    xx = 1; xy = 0; xz = 0; xo = 0
    yx = 0; yy = 1; yz = 0; yo = 0
    zx = 0; zy = 0; zz = 1; zo = 0
  }

  /**
   * Setup a transform from the MCS to the WCS
   * @param pos
   * @param agl
   * @param scale
   */
  def setTransformMCStoWCS(pos: FxPoint3D, agl: FxAngle3D, scale: FxPoint3D) {
    setIdentity()
    concatS(scale.x, scale.y, scale.z)
    concatRx(agl.x)
    concatRy(agl.y)
    concatRz(agl.z)
    concatT(pos.x, pos.y, pos.z)
  }

  /**
   * Setup a transform from the WCS to the MCS
   * @param pos
   * @param agl
   */
  def setTransformWCStoVCS(pos: FxPoint3D, agl: FxAngle3D) {
    setIdentity()
    concatT(-pos.x, -pos.y, -pos.z)
    concatRz(-agl.z)
    concatRy(-agl.y)
    concatRx(-agl.x)
  }

  /**
   * Setup a transform to look along a vector
   * @param p0 the initial point of the vector
   * @param p1 the terminal point of the vector
   */
  def setTransformToLookAtVector(p0: FxPoint3D, p1: FxPoint3D) {
    // create the Z-axis vector
    val vectorAxisZ = p0.vectorTo(p1)
    vectorAxisZ.normalize(1)

    // create the X-axis vector
    val vectorAxisX = FxPoint3D()
    vectorAxisX.vectorProduct(FxPoint3D(0, 1, 0), p1)
    vectorAxisX.normalize(1)

    // create the Y-axis vector
    val vectorAxisY = new FxPoint3D()
    vectorAxisY.vectorProduct(vectorAxisZ, vectorAxisX)

    xo = -p0.x
    yo = -p0.y
    zo = -p0.z

    xx = vectorAxisX.x; xy = vectorAxisX.y; xz = vectorAxisX.z
    yx = vectorAxisY.x; yy = vectorAxisY.y; yz = vectorAxisY.z
    zx = vectorAxisZ.x; zy = vectorAxisZ.y; zz = vectorAxisZ.z
  }

}