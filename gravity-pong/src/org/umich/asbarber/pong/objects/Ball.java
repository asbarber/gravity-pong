package org.umich.asbarber.pong.objects;

import org.umich.asbarber.pong.gui.settings.Customization;
import org.umich.asbarber.pong.gui.settings.Settings;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * Creates a ball to bounce around within the boundaries
 * @author Aaron
 */
public class Ball implements Customization{
    //Instance Variables  
        /**
         * Initial Conditions (i = initial; x,y = axis; c = center; s = speed; a = acceleration; r = radius)
         */
        private double ixC, iyC, ixS, iyS, ixA, iyA;
        private int ir; 
        /**
         * Center of Ball
         */
        private double xCenter, yCenter;
        /**
         * Speed of Ball
         */
        private double xSpeed, ySpeed;
        /**
         * Acceleration of Ball
         */
        private double xAccel, yAccel;
        /**
         * Radius of Ball
         */
        private int radius;
    
        
    //Class Variables
        /**
         * Table boundaries
         */
        private static int tableTop, tableBottom, tableLeft, tableRight;
    
        
    //Constructors
        /**
         * Creates default non-moving ball off the table and it's initial state
         */
        public Ball(){
            //Postion and Motion
            xCenter = -1;
            yCenter = -1;
            xSpeed = 0;
            ySpeed = 0;
            xAccel = 0;
            yAccel = 0;
            radius = 1;

            //Creates 'Initial State'
            setStateAsInit();
        }
        /**
         * Creates ball with given speed and size in the middle of the boundaries with an initial state
         * @param r
         * @param xMove
         * @param yMove 
         */
        public Ball(int r, int xMove, int yMove){        
            //Position and Motion
            xCenter = (tableRight - tableLeft) / 2;
            yCenter = (tableBottom - tableTop) / 2;
            xSpeed = xMove;
            ySpeed = yMove;
            xAccel = 0;
            yAccel = 0;
            radius = r;     

            //Creates 'Initial State'
            setStateAsInit();        
        }
    
        
    //Accessor
        /**
         * Returns the ball as the rectangle bounding it
         * @return Ball as a rectangle (ball inscribed in rectangle)
         */
        public Rectangle getAsRectangle(){
            Rectangle semicircle = new Rectangle();
            semicircle.setSize(2*radius, 2*radius);                      //Width, Height
            semicircle.setLocation((int)xCenter - radius, (int)yCenter - radius);  //TopLeft Corner 
            
            return semicircle;
        }
        /**
         * Returns x direction of ball
         * @return x direction of the ball (-1, 0, 1)
         */
        public int getDirectionX(){          
            if (xSpeed < 0){
                return -1;
            }     
            else if (xSpeed > 0){
                return 1;
            }
            else{
                return 0;
            }        
        }
        /**
         * Returns y direction of ball
         * @return y direction of the ball (-1, 0, 1)
         */
        public int getDirectionY(){
            if (ySpeed < 0){
                return -1;
            }          
            else if (ySpeed > 0){
                return 1;
            }
            else{
                return 0;
            }
        }
        /**
         * Gets the center of this ball.
         * @return The location of the ball as a Point
         */
        public Point getLocation(){
            return new Point((int)xCenter, (int)yCenter);
        }
      
    //Modifiers
        /**
         * Sets boundaries of the table to restrict ball movement
         * @param tblLeft x-coordinate of left side
         * @param tblRight x-coordinate of right side
         * @param tblTop y-coordinate of top side
         * @param tblBottom y-coordinate of bottom side
         */
        public static void setTableBounds(int tblLeft, int tblRight, int tblTop, int tblBottom){
            tableLeft = tblLeft;
            tableRight = tblRight;
            tableTop = tblTop;
            tableBottom = tblBottom;
        }
        /**
         * Reverses x and y speed of the ball
         */
        public void reverseDirection(){
            xSpeed *= -1;
            ySpeed *= -1;
        }
        /**
         * Sets the velocity of the ball
         * @param x x speed
         * @param y y speed
         */
        public void setSpeed(int x, int y){
            xSpeed = x;
            ySpeed = y;
        }
        /**
         * Sets center location of ball
         * @param x x-coordinate of center
         * @param y y-coordinate of center
         */
        public void setLocation(int x, int y){
            xCenter = x;
            yCenter = y;
        }
        /**
         * Resets to initial state
         */
        public void reset(){
            xCenter = ixC;
            yCenter = iyC;
            xSpeed = ixS;
            ySpeed = iyS;
            xAccel = ixA;
            yAccel = iyA;
            radius = ir;
        }
        /**
         * Sets the speed of the ball to 0 (stops it)
         */
        public void stop(){
            xSpeed = 0;
            ySpeed = 0;
        }
        /**
         * Sets current state as initial state
         */
        private void setStateAsInit(){
            ixC = xCenter;
            iyC = yCenter;
            ixS = xSpeed;
            iyS = ySpeed;
            ixA = xAccel;
            iyA = yAccel;
            ir  = radius;
        }    
      
      
    //Movement
        /**
         * Moves the ball
         */
        public void move(){ 
            speedLimitCheck();
            
            xCenter += xSpeed;
            yCenter += ySpeed;
        } 
        /**
         * Random increase in x and y speed in the direction of motion (0-3)
         */
        private void randomSpeedChange(){              
              //Random Speed Increase in Direction of Motion
              xSpeed += getDirectionX() * (int)(Math.random() * Settings.getBallRandomness());
              ySpeed += getDirectionY() * (int)(Math.random() * Settings.getBallRandomness());   
              
              //Prevents Fly-Thru effect
              int max = Settings.getMaxSpeed();
              if (xSpeed > max){
                  xSpeed = max;
              }
              if (ySpeed > max){
                  ySpeed = max;
              }
        } 
        /**
         * Random start direction and speed of ball
         */
        public void randomizeStartDirection(){
                //Either 1 or -1
                int xRand = 1;
                int yRand = 1;

                //Random for (-) x
                if (Math.random() < .5){
                    xRand = -1;
                }

                //Random for (-) y
                if (Math.random() < .5){
                    yRand = -1;
                }

                //Updates Speed
                xSpeed = xSpeed * xRand;
                ySpeed = ySpeed * yRand;

                //Random Change in Speeds
                randomSpeedChange();
          }   
        public void speedLimitCheck(){
              //Prevents Fly-Thru effect
              int max = Settings.getMaxSpeed();
              
              if (xSpeed > max){
                  xSpeed = max;
              }
              if (ySpeed > max){
                  ySpeed = max;
              }
        }
        
    //Collision
        /**
         * Handles the ball collision with the paddle (reverses x speed)
         * @param p Paddle to check collision with
         */
        public void collisionPaddle(Paddle p){
            if (collidesWithPaddle(p)){
                //Speed and Direction Change
                randomSpeedChange();              
                xSpeed *= -1;   
              
                //Keeps moving until out of collision zone
                do{
                  move();
                }while (collidesWithPaddle(p));
            }
        }
        /**
         * Handles the ball collision with the top/bottom boundary (reverses y speed)
         */
        public void collisionBoundaryHorizontal(){
          if (collidesWithBoundaryHorizontal()){
                //Direction Change
                ySpeed *= -1;
              
                //Keeps moving until out of collision zone
                do{
                  move();
                }while (collidesWithBoundaryHorizontal());
            }
        }
        /**
         * Handles the ball collision with the left/right boundary (resets to initial state)
         */
        public void collisionBoundaryVertical(){
            if (collidesWithBoundaryVertical()){              
                //Restarts Moving
                reset();
                randomizeStartDirection();
            }                  
        }
  
        //Collision Booleans
        /**
         * Boolean describing if the ball collides with the paddle
         * @param p Paddle to check collision
         * @return Boolean describing if collision with paddle
         */
        public boolean collidesWithPaddle(Paddle p){            
            //Intersects
            if (getAsRectangle().intersects(p)){
                return true;
            }
                return false;
        }
        /**
         * Boolean describing if the ball collides with the top/bottom boundary
         * @return If ball collides with a top/bottom boundary
         */
        public boolean collidesWithBoundaryHorizontal(){
            if ((yCenter + radius >= tableBottom) || (yCenter - radius <= tableTop)){
                return true;
            }
                return false;
        }
        /**
         * Boolean describing if the ball collides with the left/right boundary
         * @return If ball collides with a left/right boundary
         */
        public boolean collidesWithBoundaryVertical(){
            if ((xCenter - radius <= tableLeft) || (xCenter + radius >= tableRight)){
                return true;
            }
                return false;
        }
    
        
    //Methods Extended with Gravity
        public void move(ArrayList<GravityField> g){
            double cX = 0, cY = 0;

            //Net Gravitational Force
            for (GravityField i: g){
                cX += i.getAccelerationX(this);
                cY += i.getAccelerationY(this);
            }

            //Update Acceleration
            xAccel = cX;
            yAccel = cY;

            //Update Velocity
            xSpeed += xAccel;
            ySpeed += yAccel;

            speedLimitCheck();
            
            //Update Position
            xCenter += xSpeed;
            yCenter += ySpeed;
        }
    
    //Graphics
        /**
         * Draws the ball on the graphic
         * @param g Graphics
         */
        public void draw(Graphics g){
            //Upper Left corner
            int xCorner = (int)xCenter - radius;
            int yCorner = (int)yCenter - radius;
          
            //Draws Ball
            g.setColor(ballColor);            
            g.fillOval(xCorner, yCorner, 2*radius, 2*radius);
      }
}
