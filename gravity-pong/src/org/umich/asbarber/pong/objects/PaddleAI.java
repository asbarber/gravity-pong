package org.umich.asbarber.pong.objects;

import org.umich.asbarber.pong.gui.settings.Customization;
import org.umich.asbarber.pong.gui.control.GameFrame;
import org.umich.asbarber.pong.gui.control.PongTable;
import org.umich.asbarber.pong.gui.settings.Settings;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Aaron
 */
public class PaddleAI extends Paddle implements ActionListener, Customization{
    /**
     * Determines when Paddle should move
     */
    private Timer checkPos;
    /**
     * Step of time (dt)
     */
    private static int dt;  
    /**
     * Postion (r) and dr: index 0 is Previous Position, 1 is Current Position
     */
    private Point[] r;
    /**
     * Velocity (dr): index 0 is x, 1 is y
     */
    private double[] dr;
    
    
    //Constructor
    /**
     * Creates an Paddle that responds to the ball location
     */
    public PaddleAI(){   
        //Position
        r = new Point[2];           //init, final
        dr = new double[2];         //x, y
        
            //r init
            r[0] = getBallPos();
            r[1] = getBallPos();
            
        //Timer
        dt = Settings.getDifficulty();  
        checkPos = new Timer(dt, this);
    }
    
    
    //Control
    /**
     * Starts the timer
     * Starts moving in response of ball
     */
    public void startAI(){
        checkPos.start();
        updateBallPosition();
    }
    /**
     * Stops the clock
     * Stops responding to ball movement
     */
    public void stopAI(){
        checkPos.stop();
    }
    
    
    //Accessor
    /**
     * Returns location of the ball
     * @return The balls location
     */
    private Point getBallPos(){
        return GameFrame.getBall().getLocation();
    }

    
    //Modifier
    /**
     * Changes the speed in which the paddle responds to ball movements.
     * @param i Speed to check for updates
     */
    public static void setDifficulty(int i){
        dt = i;
    }
    
    

    //Helper (Movement)
    private void updateBallPosition(){
        r[0] = r[1];
        r[1] = getBallPos();
    }
    private void updateBallVelocity(){
        dr[0] = ( r[1].getX() - r[0].getX() )/dt;
        dr[1] = ( r[1].getY() - r[0].getY() )/dt;        
    }
    public void updateSequence(){
        updateBallPosition();
        updateBallVelocity();        
    }    
    
    //Helper (AI)
    private Point predictBallLocation(){        
        double distance = super.getLocation().getX()- r[1].x;
        double time = distance / dr[0];

        
        //Not Moving Away From Paddle
        if (time >= 0){
            //Xpaddle, Yi + dy * t
            return new Point((int)(r[1].x + distance), (int)(r[1].y + dr[1]*time));
        }
        //Moving Away From Paddle
        else{
            //Paddles Current Location
            return super.getLocation();
        }
    }
    private double findDistanceToBlock(){
        //Predicted y - Paddle y center
        return  super.y - predictBallLocation().getY();
    }    
    private int findDirectionToBlock(){
        //Need to Move...
        //Up
        if (findDistanceToBlock() > 0){
            return 1;
        }
        //Down
        else if (findDistanceToBlock() < -super.height){
            return -1;
        }
        //Stay still
        else{
            return 0;
        }
    }
    
    public void actionPerformed(ActionEvent e){
      //Updates Position and Velocity
      updateSequence();
      
      //Moves in Appropriate Direction
      switch (findDirectionToBlock()){
          case -1: super.moveDown();    break;
          case  0:                      break;
          case  1: super.moveUp();      break;
      }  
    }
    
    public void output(){
      System.out.println("xi: " + r[0].x + ", xf: " + r[1].x);
      System.out.println("yi: " + r[0].y + ", yf: " + r[1].y);      
      System.out.println("vx: " + dr[0] + ", vy: " + dr[1]);
      System.out.println("Predicted x:" + predictBallLocation().x + ", Predicted y: " + predictBallLocation().y);
      System.out.println("Distance to block: " + findDistanceToBlock());
      System.out.println("Direction to block: " + findDirectionToBlock());
      System.out.println();      
    }
}
