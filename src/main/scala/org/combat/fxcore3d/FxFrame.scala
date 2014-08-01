package org.combat.fxcore3d

import javax.swing.{ JFrame, JPanel }
import java.awt.{ Dimension, GridLayout }

/**
 * FxEngine Window Frame
 * @author lawrence.daniels@gmail.com
 */
class FxFrame extends JFrame("Combat Machines") {
  super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  super.setContentPane({
    val cp = new JPanel(true)
    cp.setLayout(new GridLayout(1, 1))
    cp.setPreferredSize(new Dimension(1024, 768))
    cp
  })
  super.pack()
  super.setVisible(true)

}
