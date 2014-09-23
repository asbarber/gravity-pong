package org.umich.asbarber.pong.objects;

import org.umich.asbarber.pong.gui.control.GameFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Aaron
 */
public class GravityField{
    //Formula In Use
    //a = (Gm/r^2)
    
    //Object Properties
    private double mass;    //represents scalar * Gravitational Constant
    
    //Position Values
    private Point location;    
    private double x, y, dx, dy, dxx, dyy;
    
    //Object Constraints
    private static int upperBoundX, upperBoundY, lowerBoundX, lowerBoundY;
    
    //Drawing Variables
    private double thetaLimit, thetaInit;
    
    //Special Properties
    private boolean constrainsPull; //defaults to true
    
    
    
    //Constructor
    public GravityField(){
        createGalaxyBounds();
        randomLocation();
        randomSpeed();
        randomAcceleration();
        randomMass();
        constrainsPull = true;
    } 
    
    
    //Modifier
    public static void createGalaxyBounds(){
        int h = GameFrame.getPongTable().getHeight();
        int w = GameFrame.getPongTable().getWidth();
        
        lowerBoundX = 3;
        upperBoundX = w - 3;
        
        lowerBoundY = 3;
        upperBoundY = h - 3;            
    }
    public void setLocation(int i, int j){
        location = new Point(i, j);
        x = i;
        y = j;
    }
    public void setMass(double d){
        mass = d;
    }
    public final void setPullConstraint(boolean b){
        constrainsPull = b;
    }
    public final void randomLocation(){
        x = (int)(Math.random() * (upperBoundX - lowerBoundX) + lowerBoundX);
        y = (int)(Math.random() * (upperBoundY - lowerBoundY) + lowerBoundY);
        
        location = new Point((int) x, (int) y);
    }
    public final void randomMass(){
        mass = 50 + Math.random() * 50;
    }
    public final void randomSpeed(){
        dx = Math.random();
        dy = Math.random();
        
        dx *= randomDirection();
        dy *= randomDirection();        
    }
    public final void randomAcceleration(){
        /*dxx = Math.random() * 1;
        dyy = Math.random() * 1;
        
        dxx *= randomDirection();
        dyy *= randomDirection();*/
        
        dxx = 0; dyy = 0;

    }
    public int randomDirection(){
        //Random Direction
        if (Math.random() < .5){
            return -1;
        }   
        else{
            return 1;
        }
    }
    
    //Affecting Ball
            //Accessor
            public double getDistance(Ball b){
                double deltaX = calculateDistanceFromFieldX(b);
                double deltaY = calculateDistanceFromFieldY(b);

                return distanceFormula(deltaX, deltaY);
            }
            /**
             * Finds the magnitude of a the vector representing the acceleration of the ball
             * @param b The object emitting gravitational ball
             * @return Magnitude of acceleration
             */
            public double getAccelerationMagnitude(Ball b){
                double g;
                double r = getDistance(b);

                g = calculateAcceleration(mass, r);

                return g;
            }
            public double getAccelerationX(Ball b){
                double gx;
                double deltaX = calculateDistanceFromFieldX(b);
                double deltaY = calculateDistanceFromFieldY(b);        
                double theta = calculateAngle(deltaX, deltaY);

                //Force * cos(theta) = force in x direction
                gx = Math.pow(getAccelerationMagnitude(b), .5) * Math.cos(theta);

                //In appropriate direction
                gx *= forceDirectionX(b);

                return gx;
            }
            public double getAccelerationY(Ball b){
                double gy;
                double deltaY = calculateDistanceFromFieldY(b);
                double deltaX = calculateDistanceFromFieldX(b);
                double theta = calculateAngle(deltaX, deltaY);

                //Reverse distance formula for 1-1-root 2 triangle
                gy = Math.pow(getAccelerationMagnitude(b), .5) * Math.sin(theta);

                //In appropriate direction
                gy *= forceDirectionY(b);

                return gy;
            }    


            //Helper
            private double calculateAcceleration(double m, double r){
                //Prevents Death
                if (r < 1){
                    r = 10;
                }
                
                //Special Property: Accelerates only within field
                if (constrainsPull && r > 2 * spiralFunction(thetaLimit, 1, 1.2)){
                    return 0;
                }
                
                return ( m / Math.pow(r, 2) );
            }
            private double calculateAngle(double xSide, double ySide){
                return Math.atan(ySide/xSide);
            }

            private double calculateDistanceFromFieldX(Ball b){
                return b.getLocation().getX() - location.getX();        
            }
            private double calculateDistanceFromFieldY(Ball b){
                return b.getLocation().getY() - location.getY();        
            }    
            private double distanceFormula(double i, double i2){
                return Math.pow(i*i + i2*i2, .5);
            }

            private int forceDirectionX(Ball b){
                if (location.getX() - b.getLocation().getX() > 0){
                    return 1;
                }
                else{
                    return -1;
                }
            }
            private int forceDirectionY(Ball b){
                if (location.getY() - b.getLocation().getY() > 0){
                    return 1;
                }
                else{
                    return -1;
                }
            }    
    
    
            
            
    //Movement
    public void move(){
        randomAcceleration();
        
        centerStar();
        
        dx += dxx;
        dy += dyy;
        
        
        x += dx/2;
        y += dy/2;
        
        keepInBounds();
        increaseThetaLimit();
        rotate();
        
        location = new Point((int)x, (int)y);      
    }
    public void keepInBounds(){  
        boolean needsMove = false;
        
        if (x >= upperBoundX || x <= lowerBoundX){
            dx *= -1;
            needsMove = true;
        }

        if (y >= upperBoundY || y <= lowerBoundY){
            dy *= -1;
            needsMove = true;
        }    
        
        if (needsMove){
            move();
        }
    }
    public void centerStar(){
        //New Gravity Field Representing a Center of the Galaxy
        //      Location:   Center of bounds,   Special Properties:     No restraint on it's pull
        //      **Only Affects other Gravity Fields, not any balls
        GravityField c = new GravityField();
        c.setLocation( (int)((upperBoundX - lowerBoundX) / 2), (int)((upperBoundY - lowerBoundY) / 2) );
        c.setMass(800);
        c.setPullConstraint(false);
        
        //Converts 'this' to a ball to calculate acceleration
        Ball tmp = new Ball();
        tmp.setLocation((int)this.x, (int)this.y);
        
        //Acceleration of this GravityField
        dxx = c.getAccelerationX(tmp) / 10;
        dyy = c.getAccelerationY(tmp) / 10;
    }
    
    //Graphics
        public void draw(Graphics g){
            //Draws Source
            g.setColor(Color.BLUE);            
            //g.fillOval(location.x, location.y, 5, 5);
            
            double theta = 0;
            double r;
            
            do{
                r = spiralFunction(theta, 1, 1.2);
                g.fillOval((int)(x + r*Math.cos(theta + thetaInit)), (int)(y + r*Math.sin(theta + thetaInit)), 2, 2);
                theta += 0.23;
            }while(theta < thetaLimit);
        }
        private double spiralFunction(double theta, double a, double b){
            return a + Math.pow(b, theta);
        }
        
        private void increaseThetaLimit(){
            if (thetaLimit < 8 * Math.PI){
                thetaLimit += 0.10;                
            }
        }
        private void rotate(){
            thetaInit += .1;
        }
        
        public static void drawCenterStar(Graphics g){
            g.setColor(Color.YELLOW.darker().darker());
            g.fillOval( (int)((upperBoundX - lowerBoundX) / 2), (int)((upperBoundY - lowerBoundY) / 2) , 40, 40);
            
            
            g.setColor(Color.YELLOW.darker());
            g.fillOval( (int)((upperBoundX - lowerBoundX) / 2), (int)((upperBoundY - lowerBoundY) / 2) , 20, 20);
             
            g.setColor(Color.YELLOW);
            g.fillOval( (int)((upperBoundX - lowerBoundX) / 2), (int)((upperBoundY - lowerBoundY) / 2) , 10, 10);
        }
        
    
}
