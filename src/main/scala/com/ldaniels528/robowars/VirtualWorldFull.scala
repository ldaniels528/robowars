package com.ldaniels528.robowars

import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.actors._
import com.ldaniels528.robowars.actors.ai._
import com.ldaniels528.robowars.structures._

/**
 * VirtualWorldFull
 * Created by ldaniels on 8/5/14.
 */
class VirtualWorldFull() extends VirtualWorld(-500, -500, 500, 20, -10d) {
  val world: FxWorld = this
  activePlayer = new HoverTank(world, FxPoint3D(0, 0, -290))
  //activePlayer = new Glider(world, FxPoint3D(20, -400, 0));

  // populate the world
  populateWorld
  attachActors

  def populateWorld {
    new GenericPillar(world, -410, -300, new FxAngle3D(0, 0, 0), 2, 3, 12)

    section1

    new GenericPillar(world, -414, -295, new FxAngle3D(0, Math.PI / 2, 0), 2, 3, 12)

    section2

    new GenericPillar(world, 414, -295, new FxAngle3D(0, -Math.PI / 2, 0), 2, 3, 12)

    section3

    new GenericPillar(world, -410, 410, new FxAngle3D(0, Math.PI, 0), 2, 3, 12)

    section4

    section5

    section6

    GenericBuilding(world, 25, -255, 5, 5, 5)
    GenericBuilding(world, 25, -105, 5, 5, 5)

    section7

    section8

    section9

    GenericBuilding(world, 385, -45, 5, 5, 5)
    GenericBuilding(world, 385, 5, 5, 5, 5)

    section10

    section11

    section12

    GenericBuilding(world, -115, -255, 5, 5, 5)
    GenericBuilding(world, -125, -255, 5, 5, 5)
    GenericBuilding(world, -145, -255, 5, 5, 5)
    GenericBuilding(world, -155, -255, 5, 5, 5)
    GenericBuilding(world, -175, -255, 5, 5, 5)
    GenericBuilding(world, -185, -255, 5, 5, 5)
    GenericBuilding(world, -205, -255, 5, 5, 5)
    GenericBuilding(world, -215, -255, 5, 5, 5)
    GenericBuilding(world, -235, -95, 5, 5, 5)
    GenericBuilding(world, -245, -95, 5, 5, 5)
    GenericBuilding(world, -255, -95, 5, 5, 5)
    GenericBuilding(world, -255, -105, 5, 5, 5)

    section13

    GenericBuilding(world, -120, -170, 10, 10, 10)
    GenericBuilding(world, -120, -200, 10, 10, 10)
    GenericBuilding(world, -210, -170, 10, 10, 10)
    GenericBuilding(world, -210, -200, 10, 10, 10)

    section14

    GenericBuilding(world, -25, -105, 5, 5, 5)
    GenericBuilding(world, -25, -255, 5, 5, 5)

    section15

    section16

    GenericBuilding(world, -375, -5, 5, 5, 5)
    GenericBuilding(world, -375, -35, 5, 5, 5)

    new GenericPillar(world, 55, 405, new FxAngle3D(0, -Math.PI / 2, 0), 2, 3, 12)

    section17

    new GenericPillar(world, -410, 180, new FxAngle3D(0, 0, 0), 2, 3, 12)

    section18

    new GenericPillar(world, -50, 260, new FxAngle3D(0, 0, 0), 2, 3, 12)

    section19

    section20

    section21

    GenericBuilding(world, -255, 215, 5, 5, 5)
    GenericBuilding(world, -255, 255, 5, 5, 5)
    GenericBuilding(world, -265, 245, 5, 5, 5)
    GenericBuilding(world, -275, 235, 5, 5, 5)

    section22

    section23

    section24

    section25

    GenericBuilding(world, 35, 275, 5, 5, 5)
    GenericBuilding(world, 35, 285, 5, 5, 5)
    GenericBuilding(world, -35, 275, 5, 5, 5)
    GenericBuilding(world, -35, 285, 5, 5, 5)

    section26

    section27

    section28

    section29

    section30

    GenericBuilding(world, -135, 25, 5, 5, 5)
    GenericBuilding(world, -105, 65, 5, 5, 5)
    GenericBuilding(world, -105, 95, 5, 5, 5)
    GenericBuilding(world, -135, 95, 5, 5, 5)
    GenericBuilding(world, -195, 95, 5, 5, 5)
    GenericBuilding(world, -225, 55, 5, 5, 5)

    section31

    GenericBuilding(world, -265, 55, 5, 5, 5)
    GenericBuilding(world, -275, 45, 5, 5, 5)
    GenericBuilding(world, -285, 35, 5, 5, 5)
    GenericBuilding(world, -305, 105, 5, 5, 5)
    GenericBuilding(world, -305, 85, 5, 5, 5)
    GenericBuilding(world, -335, 105, 5, 5, 5)
    GenericBuilding(world, -335, 85, 5, 5, 5)
    GenericBuilding(world, -55, -95, 5, 5, 5)
    GenericBuilding(world, -85, -85, 5, 5, 5)
    GenericBuilding(world, -105, -65, 5, 5, 5)
    GenericBuilding(world, -105, 25, 5, 5, 5)
    GenericBuilding(world, -85, 45, 5, 5, 5)
    GenericBuilding(world, -55, 55, 5, 5, 5)
    GenericBuilding(world, -25, 65, 5, 5, 5)
    GenericBuilding(world, 55, -95, 5, 5, 5)
    GenericBuilding(world, 85, -85, 5, 5, 5)
    GenericBuilding(world, 105, -65, 5, 5, 5)
    GenericBuilding(world, 105, 25, 5, 5, 5)
    GenericBuilding(world, 85, 45, 5, 5, 5)
    GenericBuilding(world, 55, 55, 5, 5, 5)
    GenericBuilding(world, 25, 65, 5, 5, 5)
    GenericBuilding(world, 115, -5, 5, 5, 5)
    GenericBuilding(world, 115, -35, 5, 5, 5)
  }

  def section31 {

    var i: Int = 0
    while (i <= 50) {
      {
        GenericBuilding(world, -205 - i, 115 - i, 5, 5, 5)
        GenericBuilding(world, -235 - i, 115 - i, 5, 5, 5)
      }
      i += 10
    }

  }

  def section30 {

    var x: Int = -195
    while (x > -260) {
      {
        GenericBuilding(world, x, 25, 5, 5, 5)
      }
      x -= 30
    }

  }

  def section29 {

    var x: Int = -245
    while (x > -340) {
      {
        GenericBuilding(world, x, 125, 5, 5, 5)
      }
      x -= 30
    }

  }

  def section28 {

    var x: Int = -105
    while (x > -230) {
      {
        GenericBuilding(world, x, 125, 5, 5, 5)
      }
      x -= 30
    }

  }

  def section27 {

    var y: Int = 90
    while (y < 140) {
      {
        {
          var x: Int = -30
          while (x > -80) {
            {
              GenericBuilding(world, x, y, 10, 10, 10)
            }
            x -= 40
          }
        }
      }
      y += 40
    }

  }

  def section26 {

    var y: Int = 90
    while (y < 140) {
      {
        {
          var x: Int = 30
          while (x < 80) {
            {
              GenericBuilding(world, x, y, 10, 10, 10)
            }
            x += 40
          }
        }
      }
      y += 40
    }

  }

  def section25 {

    var x: Int = 20
    while (x > -70) {
      {
        GenericBuilding(world, x, 300, 10, 10, 10)
      }
      x -= 40
    }

  }

  def section24 {

    var x: Int = 0
    while (x > -90) {
      {
        GenericBuilding(world, x, 360, 10, 10, 10)
      }
      x -= 40
    }

  }

  def section23 {

    var y: Int = 290
    while (y < 380) {
      {
        {
          var x: Int = -140
          while (x > -230) {
            {
              GenericBuilding(world, x, y, 10, 10, 10)
            }
            x -= 40
          }
        }
      }
      y += 40
    }

  }

  def section22 {

    var y: Int = 235
    while (y < 380) {
      {
        {
          var x: Int = -355
          while (x > -380) {
            {
              GenericBuilding(world, x, y, 5, 5, 5)
            }
            x -= 20
          }
        }
      }
      y += 20
    }

  }

  def section21 {

    var y: Int = 215
    while (y < 260) {
      {
        {
          var x: Int = -135
          while (x > -240) {
            {
              GenericBuilding(world, x, y, 5, 5, 5)
            }
            x -= 20
          }
        }
      }
      y += 20
    }

  }

  def section20 {

    var y: Int = 255
    while (y > 190) {
      {
        new GenericWall(world, -54, y, new FxAngle3D(0, Math.PI / 2, 0), 8, 2, 10)
        new GenericPillar(world, -54, y - 10, new FxAngle3D(0, Math.PI / 2, 0), 2, 3, 12)
      }
      y -= 20
    }

  }

  def section19 {

    var x: Int = -40
    while (x <= 50) {
      {
        if (x == 0) {
          new MainGate(world, FxPoint3D(x, 0, 260), new FxAngle3D(0, 0, 0))
          new GenericPillar(world, x + 10, 260, new FxAngle3D(0, 0, 0), 2, 3, 12)
        }
        else {
          new GenericWall(world, x, 260, new FxAngle3D(0, 0, 0), 8, 2, 10)
          new GenericPillar(world, x + 10, 260, new FxAngle3D(0, 0, 0), 2, 3, 12)
        }
      }
      x += 20
    }

  }

  def section18 {

    var x: Int = -400
    while (x <= 50) {
      {
        if ((x == 0) || (x == -320)) {
          new MainGate(world, FxPoint3D(x, 0, 180), new FxAngle3D(0, 0, 0))
          new GenericPillar(world, x + 10, 180, new FxAngle3D(0, 0, 0), 2, 3, 12)
        }
        else {
          new GenericWall(world, x, 180, new FxAngle3D(0, 0, 0), 8, 2, 10)
          new GenericPillar(world, x + 10, 180, new FxAngle3D(0, 0, 0), 2, 3, 12)
        }
      }
      x += 20
    }

  }

  def section17 {

    var y: Int = 395
    while (y > 185) {
      {
        new GenericWall(world, 55, y, new FxAngle3D(0, -Math.PI / 2, 0), 8, 2, 10)
        new GenericPillar(world, 55, y - 10, new FxAngle3D(0, -Math.PI / 2, 0), 2, 3, 12)
      }
      y -= 20
    }

  }

  def section16 {

    var y: Int = -35
    while (y < 20) {
      {
        {
          var x: Int = -115
          while (x > -270) {
            {
              GenericBuilding(world, x, y, 5, 5, 5)
            }
            x -= 30
          }
        }
      }
      y += 30
    }

  }

  def section15 {

    var y: Int = -245
    while (y < 60) {
      {
        {
          var x: Int = -295
          while (x > -350) {
            {
              GenericBuilding(world, x, y, 5, 5, 5)
            }
            x -= 50
          }
        }
      }
      y += 30
    }

  }

  def section14 {

    var y: Int = -225
    while (y < -130) {
      {
        GenericBuilding(world, -25, y, 5, 5, 5)
      }
      y += 30
    }

  }

  def section13 {

    var y: Int = -230
    while (y < -130) {
      {
        {
          var x: Int = -120
          while (x > -220) {
            {
              GenericBuilding(world, x, y, 10, 10, 10)
            }
            x -= 30
          }
        }
      }
      y += 90
    }

  }

  def section12 {

    var y: Int = -115
    while (y < -90) {
      {
        GenericBuilding(world, -115, y, 5, 5, 5)
        GenericBuilding(world, -125, y, 5, 5, 5)
        GenericBuilding(world, -145, y, 5, 5, 5)
        GenericBuilding(world, -155, y, 5, 5, 5)
        GenericBuilding(world, -175, y, 5, 5, 5)
        GenericBuilding(world, -185, y, 5, 5, 5)
        GenericBuilding(world, -205, y, 5, 5, 5)
        GenericBuilding(world, -215, y, 5, 5, 5)
      }
      y += 20
    }

  }

  def section11 {

    var x: Int = -235
    while (x > -260) {
      {
        GenericBuilding(world, x, -255, 5, 5, 5)
        GenericBuilding(world, x, -235, 5, 5, 5)
        GenericBuilding(world, x, -225, 5, 5, 5)
        GenericBuilding(world, x, -205, 5, 5, 5)
        GenericBuilding(world, x, -195, 5, 5, 5)
        GenericBuilding(world, x, -175, 5, 5, 5)
        GenericBuilding(world, x, -165, 5, 5, 5)
        GenericBuilding(world, x, -145, 5, 5, 5)
        GenericBuilding(world, x, -135, 5, 5, 5)
        GenericBuilding(world, x, -115, 5, 5, 5)
      }
      x -= 20
    }

  }

  def section10 {

    var x: Int = -75
    while (x > -100) {
      {
        GenericBuilding(world, x, -255, 5, 5, 5)
        GenericBuilding(world, x, -235, 5, 5, 5)
        GenericBuilding(world, x, -225, 5, 5, 5)
        GenericBuilding(world, x, -205, 5, 5, 5)
        GenericBuilding(world, x, -195, 5, 5, 5)
        GenericBuilding(world, x, -175, 5, 5, 5)
        GenericBuilding(world, x, -165, 5, 5, 5)
        GenericBuilding(world, x, -145, 5, 5, 5)
        GenericBuilding(world, x, -135, 5, 5, 5)
        GenericBuilding(world, x, -115, 5, 5, 5)
      }
      x -= 20
    }

  }

  def section9 {

    var y: Int = 0
    while (y > -50) {
      {
        {
          var x: Int = 150
          while (x < 360) {
            {
              GenericBuilding(world, x, y, 10, 10, 10)
            }
            x += 40
          }
        }
      }
      y -= 40
    }

  }

  def section8 {

    var x: Int = 385
    while (x > 100) {
      GenericBuilding(world, x, -105, 5, 5, 5)
      x -= 30
    }

  }

  def section7 {

    var x: Int = 385
    while (x > 130) {
      GenericBuilding(world, x, -75, 5, 5, 5)
      x -= 30
    }

  }

  def section6 {

    var y: Int = -225
    while (y < -130) {
      {
        GenericBuilding(world, 25, y, 5, 5, 5)
      }
      y += 30
    }

  }

  def section5 {

    var y: Int = -255
    while (y < -130) {
      {
        {
          var x: Int = 385
          while (x > 40) {
            {
              GenericBuilding(world, x, y, 5, 5, 5)
            }
            x -= 30
          }
        }
      }
      y += 30
    }

  }

  def section4 {

    var x: Int = -400
    while (x <= 400) {
      {
        new GenericWall(world, x, 410, new FxAngle3D(0, Math.PI, 0), 8, 2, 10)
        new GenericPillar(world, x + 10, 410, new FxAngle3D(0, Math.PI, 0), 2, 3, 12)
      }
      x += 20
    }

  }

  def section3 {
    var y: Int = -285
    while (y <= 400) {
      {
        new GenericWall(world, 414, y, new FxAngle3D(0, -Math.PI / 2, 0), 8, 2, 10)
        new GenericPillar(world, 414, y + 10, new FxAngle3D(0, -Math.PI / 2, 0), 2, 3, 12)
      }
      y += 20
    }
  }

  def section2 {
    var y: Int = -285
    while (y <= 400) {
      {
        new GenericWall(world, -414, y, new FxAngle3D(0, Math.PI / 2, 0), 8, 2, 10)
        new GenericPillar(world, -414, y + 10, new FxAngle3D(0, Math.PI / 2, 0), 2, 3, 12)
      }
      y += 20
    }
  }

  def section1 {
    var x: Int = -400
    while (x <= 400) {
      {
        if (x == 0) {
          new MainGate(world, FxPoint3D(x, 0, -300), new FxAngle3D(0, 0, 0))
          new GenericPillar(world, x + 10, -300, new FxAngle3D(0, 0, 0), 2, 3, 12)
        }
        else {
          new GenericWall(world, x, -300, new FxAngle3D(0, 0, 0), 8, 2, 10)
          new GenericPillar(world, x + 10, -300, new FxAngle3D(0, 0, 0), 2, 3, 12)
        }
      }
      x += 20
    }
  }

  private def attachActors {
    var ab: AttackAI = null
    ab = new AttackAI(new AntiTankCannon(world, FxPoint3D(-165, 0, -185)))
    ab.selectTarget(activePlayer)
    ab = new AttackAI(new AntiTankCannon(world, FxPoint3D(-43.6, 0, -104.8)))
    ab.selectTarget(activePlayer)
    ab = new AttackAI(new AntiTankCannon(world, FxPoint3D(-295, 0, 165)))
    ab.selectTarget(activePlayer)
    ab = new AttackAI(new AntiTankCannon(world, FxPoint3D(-345, 0, 165)))
    ab.selectTarget(activePlayer)
    ab = new AttackAI(new AntiTankCannon(world, FxPoint3D(-295, 0, 215)))
    ab.selectTarget(activePlayer)
    ab = new AttackAI(new AntiTankCannon(world, FxPoint3D(-345, 0, 215)))
    ab.selectTarget(activePlayer)
    ab = new AttackAI(new AntiTankCannon(world, FxPoint3D(25, 0, 225)))
    ab.selectTarget(activePlayer)
    ab = new AttackAI(new AntiTankCannon(world, FxPoint3D(-25, 0, 225)))
    ab.selectTarget(activePlayer)
    ab = new AttackAI(new AntiTankCannon(world, FxPoint3D(15, 0, 335)))
    ab.selectTarget(activePlayer)
    ab = new AttackAI(new AntiTankCannon(world, FxPoint3D(-15, 0, 335)))
    ab.selectTarget(activePlayer)
    ab = new AttackAI(new AntiTankCannon(world, FxPoint3D(-255, 0, 295)))
    ab.selectTarget(activePlayer)
    ab = new AttackAI(new AntiTankCannon(world, FxPoint3D(-255, 0, 365)))
    ab.selectTarget(activePlayer)
    ab = new AttackAI(new AntiTankCannon(world, FxPoint3D(-305, 0, 325)))
    ab.selectTarget(activePlayer)
    ab = new AttackAI(new FesseTank(world, FxPoint3D(0, 0, -500)))
    ab.selectTarget(activePlayer)
    ab = new AttackAI(new FesseTank(world, FxPoint3D(0, 0, -360)))
    ab.selectTarget(activePlayer)
    val b: MotionAI = new MotionAI(new Glider(world, FxPoint3D(-10, -370, 0)))
    b.navigateTo(FxPoint3D(0, 30, 100), 0.01)
  }
}
