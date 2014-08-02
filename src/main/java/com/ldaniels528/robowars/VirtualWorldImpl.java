package com.ldaniels528.robowars;

import com.ldaniels528.fxcore3d.FxAngle3D;
import com.ldaniels528.fxcore3d.FxPoint3D;
import com.ldaniels528.robowars.actors.AbstractActor;
import com.ldaniels528.robowars.actors.AntiTankCannon;
import com.ldaniels528.robowars.actors.FesseTank;
import com.ldaniels528.robowars.actors.Glider;
import com.ldaniels528.robowars.ai.AttackAI;
import com.ldaniels528.robowars.ai.MotionAI;
import com.ldaniels528.robowars.structures.GenericBuilding;
import com.ldaniels528.robowars.structures.GenericPillar;
import com.ldaniels528.robowars.structures.GenericWall;
import com.ldaniels528.robowars.structures.MainGate;

import java.io.IOException;

/**
 * Represents a virtual world implementation
 *
 * @author lawrence.daniels@gmail.com
 */
@SuppressWarnings("serial")
public class VirtualWorldImpl extends VirtualWorld {
    private final AbstractActor activePlayer;

    public VirtualWorldImpl() throws IOException {
        super(-500, -500, 500, 20, -10d);

        // -- the active player
        activePlayer = new FesseTank(this, new FxPoint3D(0, 0, -290));
        //activePlayer = new Glider(this, new FxPoint3D(20, -400, 0));

        // populate the world
        populateWorld(activePlayer);
    }

    public void populateWorld(final AbstractActor activePlayer) {
        // -- the front wall
        new GenericPillar(this, -410, -300, new FxAngle3D(0, 0, 0), 2, 3, 12);
        for (int x = -400; x <= 400; x += 20) {
            if (x == 0) {
                new MainGate(this, new FxPoint3D(x, 0, -300), new FxAngle3D(0, 0, 0));
                new GenericPillar(this, x + 10, -300, new FxAngle3D(0, 0, 0),
                        2, 3, 12);
            } else {
                new GenericWall(this, x, -300, new FxAngle3D(0, 0, 0), 8, 2, 10);
                new GenericPillar(this, x + 10, -300, new FxAngle3D(0, 0, 0),
                        2, 3, 12);
            }
        }
        // -- the right wall
        new GenericPillar(this, -414, -295, new FxAngle3D(0, Math.PI / 2, 0),
                2, 3, 12);
        for (int y = -285; y <= 400; y += 20) {
            new GenericWall(this, -414, y, new FxAngle3D(0, Math.PI / 2, 0), 8,
                    2, 10);
            new GenericPillar(this, -414, y + 10, new FxAngle3D(0, Math.PI / 2,
                    0), 2, 3, 12);
        }
        // -- the left wall
        new GenericPillar(this, 414, -295, new FxAngle3D(0, -Math.PI / 2, 0),
                2, 3, 12);
        for (int y = -285; y <= 400; y += 20) {
            new GenericWall(this, 414, y, new FxAngle3D(0, -Math.PI / 2, 0), 8,
                    2, 10);
            new GenericPillar(this, 414, y + 10, new FxAngle3D(0, -Math.PI / 2,
                    0), 2, 3, 12);
        }
        // -- the back wall
        new GenericPillar(this, -410, 410, new FxAngle3D(0, Math.PI, 0), 2, 3,
                12);
        for (int x = -400; x <= 400; x += 20) {
            new GenericWall(this, x, 410, new FxAngle3D(0, Math.PI, 0), 8, 2,
                    10);
            new GenericPillar(this, x + 10, 410, new FxAngle3D(0, Math.PI, 0),
                    2, 3, 12);
        }

        // -- the "South-west" area
        for (int y = -255; y < -130; y += 30) {
            for (int x = 385; x > 40; x -= 30) {
                GenericBuilding.apply(this, x, y, 5, 5, 5);
            }
        }
        for (int y = -225; y < -130; y += 30) {
            GenericBuilding.apply(this, 25, y, 5, 5, 5);
        }
        GenericBuilding.apply(this, 25, -255, 5, 5, 5); // TOWER
        GenericBuilding.apply(this, 25, -105, 5, 5, 5); // TOWER

        for (int x = 385; x > 130; x -= 30)
            GenericBuilding.apply(this, x, -75, 5, 5, 5);
        for (int x = 385; x > 100; x -= 30)
            GenericBuilding.apply(this, x, -105, 5, 5, 5);

        for (int y = 0; y > -50; y -= 40) {
            for (int x = 150; x < 360; x += 40) {
                GenericBuilding.apply(this, x, y, 10, 10, 10);
            }
        }
        GenericBuilding.apply(this, 385, -45, 5, 5, 5);
        GenericBuilding.apply(this, 385, 5, 5, 5, 5);

        // -- the "South-east" area
        for (int x = -75; x > -100; x -= 20) {
            GenericBuilding.apply(this, x, -255, 5, 5, 5);
            GenericBuilding.apply(this, x, -235, 5, 5, 5);
            GenericBuilding.apply(this, x, -225, 5, 5, 5);
            GenericBuilding.apply(this, x, -205, 5, 5, 5);
            GenericBuilding.apply(this, x, -195, 5, 5, 5);
            GenericBuilding.apply(this, x, -175, 5, 5, 5);
            GenericBuilding.apply(this, x, -165, 5, 5, 5);
            GenericBuilding.apply(this, x, -145, 5, 5, 5);
            GenericBuilding.apply(this, x, -135, 5, 5, 5);
            GenericBuilding.apply(this, x, -115, 5, 5, 5);
        }
        for (int x = -235; x > -260; x -= 20) {
            GenericBuilding.apply(this, x, -255, 5, 5, 5);
            GenericBuilding.apply(this, x, -235, 5, 5, 5);
            GenericBuilding.apply(this, x, -225, 5, 5, 5);
            GenericBuilding.apply(this, x, -205, 5, 5, 5);
            GenericBuilding.apply(this, x, -195, 5, 5, 5);
            GenericBuilding.apply(this, x, -175, 5, 5, 5);
            GenericBuilding.apply(this, x, -165, 5, 5, 5);
            GenericBuilding.apply(this, x, -145, 5, 5, 5);
            GenericBuilding.apply(this, x, -135, 5, 5, 5);
            GenericBuilding.apply(this, x, -115, 5, 5, 5);
        }
        for (int y = -115; y < -90; y += 20) {
            GenericBuilding.apply(this, -115, y, 5, 5, 5);
            GenericBuilding.apply(this, -125, y, 5, 5, 5);
            GenericBuilding.apply(this, -145, y, 5, 5, 5);
            GenericBuilding.apply(this, -155, y, 5, 5, 5);
            GenericBuilding.apply(this, -175, y, 5, 5, 5);
            GenericBuilding.apply(this, -185, y, 5, 5, 5);
            GenericBuilding.apply(this, -205, y, 5, 5, 5);
            GenericBuilding.apply(this, -215, y, 5, 5, 5);
        }

        GenericBuilding.apply(this, -115, -255, 5, 5, 5);
        GenericBuilding.apply(this, -125, -255, 5, 5, 5);
        GenericBuilding.apply(this, -145, -255, 5, 5, 5);
        GenericBuilding.apply(this, -155, -255, 5, 5, 5);
        GenericBuilding.apply(this, -175, -255, 5, 5, 5);
        GenericBuilding.apply(this, -185, -255, 5, 5, 5);
        GenericBuilding.apply(this, -205, -255, 5, 5, 5);
        GenericBuilding.apply(this, -215, -255, 5, 5, 5);

        GenericBuilding.apply(this, -235, -95, 5, 5, 5);
        GenericBuilding.apply(this, -245, -95, 5, 5, 5);
        GenericBuilding.apply(this, -255, -95, 5, 5, 5);
        GenericBuilding.apply(this, -255, -105, 5, 5, 5);

        for (int y = -230; y < -130; y += 90) {
            for (int x = -120; x > -220; x -= 30) {
                GenericBuilding.apply(this, x, y, 10, 10, 10);
            }
        }
        GenericBuilding.apply(this, -120, -170, 10, 10, 10);
        GenericBuilding.apply(this, -120, -200, 10, 10, 10);
        GenericBuilding.apply(this, -210, -170, 10, 10, 10);
        GenericBuilding.apply(this, -210, -200, 10, 10, 10);

        for (int y = -225; y < -130; y += 30) {
            GenericBuilding.apply(this, -25, y, 5, 5, 5);
        }
        GenericBuilding.apply(this, -25, -105, 5, 5, 5); // TOWER
        GenericBuilding.apply(this, -25, -255, 5, 5, 5); // TOWER

        for (int y = -245; y < 60; y += 30) {
            for (int x = -295; x > -350; x -= 50) {
                GenericBuilding.apply(this, x, y, 5, 5, 5);
            }
        }

        for (int y = -35; y < 20; y += 30) {
            for (int x = -115; x > -270; x -= 30) {
                GenericBuilding.apply(this, x, y, 5, 5, 5);
            }
        }
        GenericBuilding.apply(this, -375, -5, 5, 5, 5);
        GenericBuilding.apply(this, -375, -35, 5, 5, 5);

        // -- the "North-east" area
        // -- the left wall
        new GenericPillar(this, 55, 405, new FxAngle3D(0, -Math.PI / 2, 0), 2,
                3, 12);
        for (int y = 395; y > 185; y -= 20) {
            new GenericWall(this, 55, y, new FxAngle3D(0, -Math.PI / 2, 0), 8,
                    2, 10);
            new GenericPillar(this, 55, y - 10, new FxAngle3D(0, -Math.PI / 2,
                    0), 2, 3, 12);
        }

        // -- the front wall
        new GenericPillar(this, -410, 180, new FxAngle3D(0, 0, 0), 2, 3, 12);
        for (int x = -400; x <= 50; x += 20) {
            if ((x == 0) || (x == -320)) {
                new MainGate(this, new FxPoint3D(x, 0, 180), new FxAngle3D(0, 0, 0));
                new GenericPillar(this, x + 10, 180, new FxAngle3D(0, 0, 0), 2,
                        3, 12);
            } else {
                new GenericWall(this, x, 180, new FxAngle3D(0, 0, 0), 8, 2, 10);
                new GenericPillar(this, x + 10, 180, new FxAngle3D(0, 0, 0), 2,
                        3, 12);
            }
        }

        // -- the inner front wall
        new GenericPillar(this, -50, 260, new FxAngle3D(0, 0, 0), 2, 3, 12);
        for (int x = -40; x <= 50; x += 20) {
            if (x == 0) {
                new MainGate(this, new FxPoint3D(x, 0, 260), new FxAngle3D(0, 0, 0));
                new GenericPillar(this, x + 10, 260, new FxAngle3D(0, 0, 0), 2,
                        3, 12);
            } else {
                new GenericWall(this, x, 260, new FxAngle3D(0, 0, 0), 8, 2, 10);
                new GenericPillar(this, x + 10, 260, new FxAngle3D(0, 0, 0), 2,
                        3, 12);
            }
        }
        // the inner right wall
        for (int y = 255; y > 190; y -= 20) {
            new GenericWall(this, -54, y, new FxAngle3D(0, Math.PI / 2, 0), 8,
                    2, 10);
            new GenericPillar(this, -54, y - 10, new FxAngle3D(0, Math.PI / 2,
                    0), 2, 3, 12);
        }

        for (int y = 215; y < 260; y += 20) {
            for (int x = -135; x > -240; x -= 20) {
                GenericBuilding.apply(this, x, y, 5, 5, 5);
            }
        }

        GenericBuilding.apply(this, -255, 215, 5, 5, 5);
        GenericBuilding.apply(this, -255, 255, 5, 5, 5);
        GenericBuilding.apply(this, -265, 245, 5, 5, 5);
        GenericBuilding.apply(this, -275, 235, 5, 5, 5);

        for (int y = 235; y < 380; y += 20) {
            for (int x = -355; x > -380; x -= 20) {
                GenericBuilding.apply(this, x, y, 5, 5, 5);
            }
        }

        for (int y = 290; y < 380; y += 40) {
            for (int x = -140; x > -230; x -= 40) {
                GenericBuilding.apply(this, x, y, 10, 10, 10);
            }
        }

        for (int x = 0; x > -90; x -= 40) {
            GenericBuilding.apply(this, x, 360, 10, 10, 10);
        }
        for (int x = 20; x > -70; x -= 40) {
            GenericBuilding.apply(this, x, 300, 10, 10, 10);
        }
        GenericBuilding.apply(this, 35, 275, 5, 5, 5);
        GenericBuilding.apply(this, 35, 285, 5, 5, 5);
        GenericBuilding.apply(this, -35, 275, 5, 5, 5);
        GenericBuilding.apply(this, -35, 285, 5, 5, 5);

        // the middle area
        for (int y = 90; y < 140; y += 40) {
            for (int x = 30; x < 80; x += 40) {
                GenericBuilding.apply(this, x, y, 10, 10, 10);
            }
        }
        for (int y = 90; y < 140; y += 40) {
            for (int x = -30; x > -80; x -= 40) {
                GenericBuilding.apply(this, x, y, 10, 10, 10);
            }
        }

        for (int x = -105; x > -230; x -= 30) {
            GenericBuilding.apply(this, x, 125, 5, 5, 5);
        }
        for (int x = -245; x > -340; x -= 30) {
            GenericBuilding.apply(this, x, 125, 5, 5, 5);
        }
        for (int x = -195; x > -260; x -= 30) {
            GenericBuilding.apply(this, x, 25, 5, 5, 5);
        }
        GenericBuilding.apply(this, -135, 25, 5, 5, 5);

        GenericBuilding.apply(this, -105, 65, 5, 5, 5);
        GenericBuilding.apply(this, -105, 95, 5, 5, 5);
        GenericBuilding.apply(this, -135, 95, 5, 5, 5);

        GenericBuilding.apply(this, -195, 95, 5, 5, 5);
        GenericBuilding.apply(this, -225, 55, 5, 5, 5);

        // -- the "corridor"
        for (int i = 0; i <= 50; i += 10) {
            GenericBuilding.apply(this, -205 - i, 115 - i, 5, 5, 5);
            GenericBuilding.apply(this, -235 - i, 115 - i, 5, 5, 5);
        }
        GenericBuilding.apply(this, -265, 55, 5, 5, 5);
        GenericBuilding.apply(this, -275, 45, 5, 5, 5);
        GenericBuilding.apply(this, -285, 35, 5, 5, 5);

        GenericBuilding.apply(this, -305, 105, 5, 5, 5);
        GenericBuilding.apply(this, -305, 85, 5, 5, 5);
        GenericBuilding.apply(this, -335, 105, 5, 5, 5);
        GenericBuilding.apply(this, -335, 85, 5, 5, 5);

        // -- the circle
        GenericBuilding.apply(this, -55, -95, 5, 5, 5);
        GenericBuilding.apply(this, -85, -85, 5, 5, 5);
        GenericBuilding.apply(this, -105, -65, 5, 5, 5);
        GenericBuilding.apply(this, -105, 25, 5, 5, 5);
        GenericBuilding.apply(this, -85, 45, 5, 5, 5);
        GenericBuilding.apply(this, -55, 55, 5, 5, 5);
        GenericBuilding.apply(this, -25, 65, 5, 5, 5);

        GenericBuilding.apply(this, 55, -95, 5, 5, 5);
        GenericBuilding.apply(this, 85, -85, 5, 5, 5);
        GenericBuilding.apply(this, 105, -65, 5, 5, 5);
        GenericBuilding.apply(this, 105, 25, 5, 5, 5);
        GenericBuilding.apply(this, 85, 45, 5, 5, 5);
        GenericBuilding.apply(this, 55, 55, 5, 5, 5);
        GenericBuilding.apply(this, 25, 65, 5, 5, 5);

        GenericBuilding.apply(this, 115, -5, 5, 5, 5);
        GenericBuilding.apply(this, 115, -35, 5, 5, 5);

        // -- the cannons
        AttackAI ab;

        ab = new AttackAI(new AntiTankCannon(this, new FxPoint3D(-165, 0, -185)));
        ab.selectTarget(activePlayer);


        ab = new AttackAI(new AntiTankCannon(this, new FxPoint3D(-43.6, 0, -104.8)));
        ab.selectTarget(activePlayer);


        ab = new AttackAI(new AntiTankCannon(this, new FxPoint3D(-295, 0, 165)));
        ab.selectTarget(activePlayer);


        ab = new AttackAI(new AntiTankCannon(this, new FxPoint3D(-345, 0, 165)));
        ab.selectTarget(activePlayer);


        ab = new AttackAI(new AntiTankCannon(this, new FxPoint3D(-295, 0, 215)));
        ab.selectTarget(activePlayer);


        ab = new AttackAI(new AntiTankCannon(this, new FxPoint3D(-345, 0, 215)));
        ab.selectTarget(activePlayer);


        ab = new AttackAI(new AntiTankCannon(this, new FxPoint3D(25, 0, 225)));
        ab.selectTarget(activePlayer);


        ab = new AttackAI(new AntiTankCannon(this, new FxPoint3D(-25, 0, 225)));
        ab.selectTarget(activePlayer);


        ab = new AttackAI(new AntiTankCannon(this, new FxPoint3D(15, 0, 335)));
        ab.selectTarget(activePlayer);


        ab = new AttackAI(new AntiTankCannon(this, new FxPoint3D(-15, 0, 335)));
        ab.selectTarget(activePlayer);


        ab = new AttackAI(new AntiTankCannon(this, new FxPoint3D(-255, 0, 295)));
        ab.selectTarget(activePlayer);


        ab = new AttackAI(new AntiTankCannon(this, new FxPoint3D(-255, 0, 365)));
        ab.selectTarget(activePlayer);


        ab = new AttackAI(new AntiTankCannon(this, new FxPoint3D(-305, 0, 325)));
        ab.selectTarget(activePlayer);


        // add a couple enemies
        ab = new AttackAI(new FesseTank(this, new FxPoint3D(0, 0, -500)));
        ab.selectTarget(activePlayer);

        ab = new AttackAI(new FesseTank(this, new FxPoint3D(0, 0, -360)));
        ab.selectTarget(activePlayer);

        MotionAI b = new MotionAI(new Glider(this, new FxPoint3D(-10, -370, 0)));
        b.gotoPosition(new FxPoint3D(0, 30, 100), 0.01);
    }

}
