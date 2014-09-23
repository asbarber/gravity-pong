package org.umich.asbarber.pong.gui.control;

import org.umich.asbarber.pong.gui.settings.Customization;
import org.umich.asbarber.pong.gui.settings.Settings;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import org.umich.asbarber.pong.objects.Ball;
import org.umich.asbarber.pong.objects.GravityField;
import org.umich.asbarber.pong.objects.Paddle;
import org.umich.asbarber.pong.objects.PaddleAI;

public class PongTable extends JPanel implements ActionListener, KeyListener, Customization{ 
    //Objects
        /**
         * Paddles on the table
         * 0: Left Paddle
         * 1: Right Paddle
         */
        private ArrayList<Paddle> p = new ArrayList<Paddle>();
        /**
         * Balls on the table
         */
        private Ball b = new Ball();    
        /**
         * Set of Gravity Fields
         */
        private ArrayList<GravityField> g = new ArrayList<GravityField>();
        
    //Score and Control
        /**
         * Indicates Keys Pressed
         */
        private boolean p1UpPress, p1DownPress, p2UpPress, p2DownPress;  
        /**
         * Whether one paddle is AI or human
         */
        private static boolean userRight = isHumanRight;
        private static boolean userLeft = isHumanLeft;
        /**
         * Score Counters
         */
        private int playerScoreLeft, playerScoreRight; 
    
    //Timer
    private Timer clock;
    private int timerTick = Settings.getTimerTick();   
    
    
    //Constructor
    public PongTable(){
        
        //Panel
        setFocusable(true);
        setBackground(pTable); 
        setBorder(new LineBorder(pBorder, borderSize));    
        
        //Paddles
        p.add(0, new Paddle());
        p.add(1, new Paddle());
        
        //Timer
        clock = new Timer(timerTick, this);
        
        addKeyListener(this);
    }
    
    //Start/Stop Control
    public void startGame(){   
        //Enables 
        p.get(0).setEnabled(true);
        p.get(1).setEnabled(true);
        
        grabFocus();
        clock.start();
    }   
    public final void resetGame(){
        resetBounds();
        resetKeys();
        resetScore();
        resetPaddles();
        resetGravity();
        resetBalls();
        resetTimer();
        repaint();
    }

    //Resets
    private void resetBounds(){
        //Bounds
        Ball.setTableBounds(3, getWidth() - 3, 3, getHeight() - 3); 
        Paddle.setTableBounds(3, getWidth() - 3, 3, getHeight() - 3);              
    }
    private void resetKeys(){
        //Key Press
        p1UpPress = false;
        p1DownPress = false;
        p2UpPress = false;
        p2DownPress = false;             
    }
    private void resetScore(){
        playerScoreLeft = 0;
        playerScoreRight = 0;
    }
    private void resetPaddles(){
        //Sets Left Paddle As AI
        if (!userLeft){
            PaddleAI pLeft = new PaddleAI();
            pLeft.startAI();
            
            p.set(0, pLeft);
        } 
        else{
            p.set(0, new Paddle());
        }
        
        //Sets Right Paddle As AI
        if (!userRight){
            PaddleAI pRight = new PaddleAI();
            pRight.startAI();
            
            p.set(1, pRight);
        }
        else{
            p.set(1, new Paddle());
        }
        
        //Left
        p.get(0).setEnabled(false);
        p.get(0).y = getHeight() / 2 - Settings.getPaddleHeight() / 2;
        p.get(0).x = 5;
        p.get(0).setSize(Settings.getPaddleWidth(), Settings.getPaddleHeight());       
        
        //Right
        p.get(1).setEnabled(false);
        p.get(1).y = getHeight() / 2 - Settings.getPaddleHeight() / 2;
        p.get(1).x = getWidth() - Settings.getPaddleWidth() - 5;
        p.get(1).setSize(Settings.getPaddleWidth(), Settings.getPaddleHeight());   
        
    }    
    private void resetBalls(){
        b = new Ball(ballRadius, Settings.getSpeed(), Settings.getSpeed());
        b.randomizeStartDirection();
    }
    private void resetGravity(){
        g.clear();
        
        int num = (int)(Math.random() * 6 + 1);
        
        for (int i = 0; i < num; i++){
            g.add(i, new GravityField());
            g.get(i).setMass(Settings.getGravity());
        }
    }
    private void resetTimer(){
        clock.stop();
        clock.setInitialDelay(0);            
    }    

    //Accessor
    public int getScoreLeft(){
        return playerScoreLeft;
    }
    public int getScoreRight(){
        return playerScoreRight;
    }
    public Ball getBall(){
        return b;
    }
    public ArrayList<GravityField> getGravityFields(){
        return g;
    }
    
    //Modifier
    public void setNumPlayers(int i){
        switch (i){
            case 0: userLeft = false;   userRight = false;  break;
            case 1: userLeft = true;    userRight = false;  break;
            case 2: userLeft = true;    userRight = true;   break;
        }

        resetKeys();
        resetPaddles();
    }
    
    
    //Graphics
        /**
         * Paints PongTable with Balls and Paddles
         * @param g Graphics
         */
        @Override 
        public void paintComponent(Graphics canvas){
              super.paintComponent(canvas);
              
              //Draws Gravity Fields
              for (GravityField i: g){
                  i.draw(canvas);
              }
              
              //GravityField.drawCenterStar(canvas);
              
              //Draws Paddles
              for(Paddle e: p){
                  e.draw(canvas);
              }    
              
              //Draws Balls
              b.draw(canvas);
              

        }
        
        
        
        
    //Key
        public void keyTyped(KeyEvent e){            
            switch (e.getKeyCode()){
                case p1Up:      if(userLeft) {    p.get(0).moveUp();      }       break;
                case p1Down:    if(userLeft) {    p.get(0).moveDown();    }       break;   
                case p2Up:      if(userRight){    p.get(1).moveUp();      }       break;
                case p2Down:    if(userRight){    p.get(1).moveDown();    }       break;                     
            }
        }
        public void keyReleased(KeyEvent e){
            //Key no longer Pressed
            switch(e.getKeyCode()){
                case p1Up:      p1UpPress   = false; break;
                case p1Down:    p1DownPress = false; break;
                case p2Up:      p2UpPress   = false; break;
                case p2Down:    p2DownPress = false; break;
            }              
        }
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case p1Up:      p1UpPress   = true; break;
                case p1Down:    p1DownPress = true; break;
                case p2Up:      p2UpPress   = true; break;
                case p2Down:    p2DownPress = true; break;
            }          
            keyAction();
        }
        /**
         * Movement corresponding to a key press.
         */
        public void keyAction(){
            //Conditions: key pressed and left is not AI
            if (p1UpPress && userLeft){
                p.get(0).moveUp();              
            }
            
            if (p1DownPress && userLeft){
                p.get(0).moveDown();               
            }
            
            //Condtions: key pressed and right is not AI
            if (p2UpPress && userRight){
                p.get(1).moveUp();              
            }
            
            if (p2DownPress && userRight){
                p.get(1).moveDown();               
            }
            
            repaint();
        }     
        
        
        
    //Action
        public void actionPerformed(ActionEvent e){
            gravityAction();
            ballAction();
            repaint();
        }
        public void ballAction(){
            b.collisionBoundaryHorizontal();
            b.collisionPaddle(p.get(0));
            b.collisionPaddle(p.get(1));
            
            if (b.collidesWithBoundaryVertical()){
                scoreSequence();
            }
          
            b.move(g);   
        }
        public void gravityAction(){
            for (GravityField i: g){
                i.move();
            }
        }
        public void scoreSequence(){
            //Timer Restart
            clock.setInitialDelay(pTableTimerDelay);
            clock.restart();
            
            //Side Scored
            if (b.getDirectionX() == 1){
                playerScoreLeft++;
            }
            else{
                playerScoreRight++;
            } 
            
            //Score Updates in Panel    
            ScorePanel.updateScore();       
            
            //Resets
            resetBalls();
            resetGravity();
        }
}