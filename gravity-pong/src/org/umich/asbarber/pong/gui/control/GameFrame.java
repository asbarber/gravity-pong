/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.umich.asbarber.pong.gui.control;

import org.umich.asbarber.pong.gui.settings.Customization;
import org.umich.asbarber.pong.gui.settings.Settings;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.umich.asbarber.pong.objects.Ball;
import org.umich.asbarber.pong.objects.GravityField;

/**
 *
 * @author Aaron
 */
public class GameFrame extends JFrame implements Customization{
    private static PongTable myGame;
    private static ScorePanel score;
    private static ControlPanel control;
    private JPanel overall;
    
    /**
     * Creates and shows GUI
     */
    public GameFrame(){
        //Frame Settings
        super("Pong");
        setBounds(10, 10, windowWidth, windowHeight);
        setBackground(windowColor);
        
            //Main Panel
            overall = new JPanel();
            overall.setLayout(new BorderLayout());
            overall.setBorder(new EmptyBorder(0, 5, 0, 5));

                //Pong Panel
                myGame = new PongTable();

                //Score Panel
                score = new ScorePanel();
                
                //Control Panel
                control = new ControlPanel();
                
            //Add components to overall overall
            overall.add(myGame, BorderLayout.CENTER);
            overall.add(control, BorderLayout.NORTH);
            overall.add(score, BorderLayout.SOUTH);
            
        //Adds to GameFrame
        add(overall);
        
        
        //Frame Settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);             
    }    
    
    
    //Accessors
    public static PongTable getPongTable(){
        return myGame;
    }
    public static ScorePanel getScorePanel(){
        return score;
    }
    public static ControlPanel getControlPanel(){
        return control;
    }
    public static Ball getBall(){
        return myGame.getBall();
    }
    public static ArrayList<GravityField> getGravityFields(){
        return myGame.getGravityFields();
    }
    
    //Modifier
    public static void setSettings(){
        Settings.changeSettings();
    }
    public void close(){
        dispose();
    }
    public void reset(){
        control.resetClick();
    }
    public void displayControls(){
        String message;
        message = "'Q' and 'A' move the left paddle.\n";
        message += "The Up and Down arrow keys move the right paddle (Two-Player only). \n\n";
        message += "Starting the game without changing the settings will be an AI demonstration. \n";
        message += "Good Luck!";
        JOptionPane.showMessageDialog(null, message);
    }
}
