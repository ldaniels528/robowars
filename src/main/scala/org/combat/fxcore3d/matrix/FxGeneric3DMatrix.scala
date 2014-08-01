package org.combat.fxcore3d.matrix

import org.combat.fxcore3d._

/**
 * A 3D matrix that hides the making of the different transforms
 * @author lawrence.daniels@gmail.com
 */
class FxGeneric3DMatrix() {
   var xx, xy, xz, xo: Double = _
   var yx, yy, yz, yo: Double = _
   var zx, zy, zz, zo: Double = _
   
   makeIdentity()
   
   /**
    * Resets the matrix
    */
   def makeIdentity() {
      xx = 1; xy = 0; xz = 0; xo = 0;
      yx = 0; yy = 1; yz = 0; yo = 0; 
      zx = 0; zy = 0; zz = 1; zo = 0;
   }

  /**
   * "Smart" multiplies a rotation about Z-axis
   */
  def concatRz(az: Double) {
    val ct = Math.cos(az);
    val st = Math.sin(az);

    val Nyx = (yx * ct + xx * st);
    val Nyy = (yy * ct + xy * st);
    val Nyz = (yz * ct + xz * st);
    val Nyo = (yo * ct + xo * st);
    val Nxx = (xx * ct - yx * st);
    val Nxy = (xy * ct - yy * st);
    val Nxz = (xz * ct - yz * st);
    val Nxo = (xo * ct - yo * st);

    xx = Nxx; xy = Nxy; xz = Nxz; xo = Nxo;
    yx = Nyx; yy = Nyy; yz = Nyz; yo = Nyo;
  }
   
  /**
   * "Smart" multiplies a rotation about Y-axis
   */
  def concatRy(ay: Double) {
    val ct = Math.cos(ay);
    val st = Math.sin(ay);

    val Nxx = (xx * ct + zx * st);
    val Nxy = (xy * ct + zy * st);
    val Nxz = (xz * ct + zz * st);
    val Nxo = (xo * ct + zo * st);

    val Nzx = (zx * ct - xx * st);
    val Nzy = (zy * ct - xy * st);
    val Nzz = (zz * ct - xz * st);
    val Nzo = (zo * ct - xo * st);

    xx = Nxx; xy = Nxy; xz = Nxz; xo = Nxo;
    zx = Nzx; zy = Nzy; zz = Nzz; zo = Nzo;
  }
   
  /**
   * "Smart" multiplies a rotation about X-axis
   */
  def concatRx(ax: Double) {
    val ct = Math.cos(ax)
    val st = Math.sin(ax)

    val Nyx = (yx * ct + zx * st);
    val Nyy = (yy * ct + zy * st);
    val Nyz = (yz * ct + zz * st);
    val Nyo = (yo * ct + zo * st);

    val Nzx = (zx * ct - yx * st);
    val Nzy = (zy * ct - yy * st);
    val Nzz = (zz * ct - yz * st);
    val Nzo = (zo * ct - yo * st);

    yx = Nyx; yy = Nyy; yz = Nyz; yo = Nyo;
    zx = Nzx; zy = Nzy; zz = Nzz; zo = Nzo;
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
    xx *= sx; xy *= sx; xz *= sx; xo *= sx;
    yx *= sy; yy *= sy; yz *= sy; yo *= sy;
    zx *= sz; zy *= sz; zz *= sz; zo *= sz;
  }

  /**
   * Multiplies the vector "ps" of 3d points and stores the result
   * in "pd".
   */
  def transform(ps: FxArrayOf3DPoints, pd: FxArrayOf3DPoints) {
    for (i <- 0 to (ps.npoints - 1)) {
      val x = ps.x(i)
      val y = ps.y(i)
      val z = ps.z(i)

      pd.x(i) = x * xx + y * xy + z * xz + xo
      pd.y(i) = x * yx + y * yy + z * yz + yo
      pd.z(i) = x * zx + y * zy + z * zz + zo
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

  def rotate(ps: FxArrayOf3DPoints, pd: FxArrayOf3DPoints) {
    for (i <- 0 to (ps.npoints - 1)) {
      val x = ps.x(i)
      val y = ps.y(i)
      val z = ps.z(i)

      pd.x(i) = x * xx + y * xy + z * xz
      pd.y(i) = x * yx + y * yy + z * yz
      pd.z(i) = x * zx + y * zy + z * zz
    }
  }

  //-- special transform
  def transformWithTranslation(ps: FxArrayOf3DPoints, pd: FxArrayOf3DPoints, et: FxPoint3D) {
    val xt = et.x
    val yt = et.y
    val zt = et.z

    for (i <- 0 to (ps.npoints - 1)) {
      val x = ps.x(i) + xt
      val y = ps.y(i) + yt
      val z = ps.z(i) + zt

      pd.x(i) = x * xx + y * xy + z * xz + xo
      pd.y(i) = x * yx + y * yy + z * yz + yo
      pd.z(i) = x * zx + y * zy + z * zz + zo
    }
  }
}
