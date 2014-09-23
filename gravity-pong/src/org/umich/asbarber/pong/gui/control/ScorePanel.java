package org.umich.asbarber.pong.gui.control;

import org.umich.asbarber.pong.gui.settings.Customization;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;

/**
 * A panel controlling score
 * @author Aaron
 */
public class ScorePanel extends JPanel implements Customization {
    //Variables
        /**
         * Labels showing the player and their corresponding score
         */
        private static JLabel playerLeft, playerRight, playerScoreLeft, playerScoreRight;
    
        
    //Constructor
        /**
         * Creates a ScorePanel displaying scores of zero
         */
        public ScorePanel(){
            //Panel Control
            setBackground(sPanel);
            setLayout(new GridLayout(1, 9, 1, 1));
            setBorder(new LineBorder(sBorder, borderSize));

            //Layout
            JSeparator space = new JSeparator();
            space.setVisible(false);

            //Label Creation
            playerLeft = new JLabel("Player 1: ");
            playerRight = new JLabel("Player 2: ");
            playerScoreLeft = new JLabel("0");
            playerScoreRight = new JLabel("0");

            //Label Format
            playerLeft.setForeground(labelText);
            playerRight.setForeground(labelText);
            playerScoreLeft.setForeground(scoreText);
            playerScoreRight.setForeground(scoreText);

            //Adding Components
            add(space);
            add(playerLeft);
            add(playerScoreLeft);
            add(playerRight);
            add(playerScoreRight);    
        }  
        /**
         * Sets the label text to the score from PongTable
         */
        public static void updateScore(){
            playerScoreLeft.setText(Integer.toString(GameFrame.getPongTable().getScoreLeft()));
            playerScoreRight.setText(Integer.toString(GameFrame.getPongTable().getScoreRight()));
        }
}
