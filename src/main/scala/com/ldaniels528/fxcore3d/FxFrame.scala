package com.ldaniels528.fxcore3d

import java.awt.{Dimension, GridLayout}
import javax.swing.{JFrame, JPanel}

/**
 * FxEngine Window Frame
 * @author lawrence.daniels@gmail.com
 */
class FxFrame(title:String) extends JFrame(title) {
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
