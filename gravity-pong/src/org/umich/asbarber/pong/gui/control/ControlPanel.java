package org.umich.asbarber.pong.gui.control;

import org.umich.asbarber.pong.gui.settings.Customization;
import org.umich.asbarber.pong.gui.settings.Settings;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * Creates a panel for PongTable
 * @author Aaron
 */
public class ControlPanel extends JPanel implements ActionListener, Customization {
    //Variables
        private JButton start;
        private JButton reset;  
        private JButton settings;
        
        private boolean initialized = false;
        
    //Constructors
        /**
     * Constructs a control table with a button for starting and resetting the PongTable
     */
        public ControlPanel(){
            //Panel
            setLayout(new GridLayout(0, 3, 140, 0));
            setBackground(cPanel);
            setBorder(new LineBorder(cBorder, borderSize));
            
            //Buttons
            start = new JButton("Start Game"); 
            reset = new JButton("Reset");
            settings = new JButton("Change Settings");
                start.setForeground(buttonForeground);
                reset.setForeground(buttonForeground);
                settings.setForeground(buttonForeground);
                start.setBackground(buttonBackground);
                reset.setBackground(buttonBackground);
                settings.setBackground(buttonBackground);
            
            //Initial Conditions
            reset.setEnabled(false);

            //Action Control
            start.addActionListener(this); 
            reset.addActionListener(this);
            settings.addActionListener(this);
            
            //Adding Components
            add(start, BorderLayout.WEST);
            add(settings, BorderLayout.CENTER);
            add(reset, BorderLayout.EAST);
        }
    
        
    //Action Control
        /**
         * Handles actions
         * @param e ActionEvent
         */
        public void actionPerformed(ActionEvent e){
            //Button start clicked
            if (e.getSource() == start){
                startClick();
            }
            //Button reset clicked
            else if (e.getSource() == reset){
                resetClick();
            }
            else if (e.getSource() == settings){
                settingsClick();
            }
        }    
    
        
    //Action Methods
        /**
         * Starts the PongTable game
         */
        public void startClick(){
            Settings.updateSettings();
            
            //Only on first start: To set bounds
            if (!initialized){
                GameFrame.getPongTable().resetGame();
                initialized = true;
            }
            
            //Starts PongTable game
            GameFrame.getPongTable().startGame();
            
            //Buttons
            start.setEnabled(false);
            settings.setEnabled(false);
            reset.setEnabled(true);
        }
        /**
         * Resets the PongTable game
         */
        public void resetClick(){
            //Resets PongTable game
            GameFrame.getPongTable().resetGame();

            //Updates Score
            ScorePanel.updateScore();
            
            //Buttons
            start.setEnabled(true);
            settings.setEnabled(true);
            reset.setEnabled(false);
        }
        /**
         * Updates the settings of the game
         */
        public void settingsClick(){
            //Updates Settings
            GameFrame.setSettings();
            
            start.grabFocus();
        }
}
