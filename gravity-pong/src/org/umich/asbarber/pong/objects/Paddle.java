package org.umich.asbarber.pong.objects;

import org.umich.asbarber.pong.gui.settings.Customization;
import org.umich.asbarber.pong.gui.settings.Settings;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * A moving paddle described as a rectangle
 * @author Aaron
 */
public class Paddle extends Rectangle implements Customization{
  //Static Variables
      /**
       * Table Boundaries
       */  
      private static int tableLeft, tableRight, tableTop, tableBottom;  
      /**
       * Paddle Size
       */
      private static int WIDTH = Settings.getPaddleWidth(), HEIGHT = Settings.getPaddleHeight();
      /**
       * If paddle can move
       */
      private boolean enabled;
      
 //Constructors 
      /**
       * Creates a Paddle, 10x20, off the table, and enabled
       */
      public Paddle(){
          //Uses Rectangle Methods
          super.setSize(WIDTH, HEIGHT);
          super.setLocation(-WIDTH, -HEIGHT);
          
          //Can move
          enabled = true;
      }
      /**
       * Creates a custom paddle at the location and with the given dimensions. Able to move.
       * @param width Width of the paddle
       * @param height Height of the paddle
       * @param xLoc x-coordinate of the upper left hand corner of the paddle
       * @param yLoc y-coordinate of the upper left hand corner of the paddle
       */
      public Paddle(int width, int height, int xLoc, int yLoc){
          //Uses Rectangle Methods
          super.setSize(width, height);
          super.setLocation(xLoc, yLoc);
          
          //Can move
          enabled = true;
      }
  
      
  //Modifiers
      /**
         * Sets the upper left hand corner location of the paddle
         * @param xLoc x-Coordinate of the top left of the paddle
         * @param yLoc y-Coordinate of the top left of the paddle
         */
      @Override
      public void setLocation(int xLoc, int yLoc){
          x = xLoc;
          y = yLoc;
      }
      /**
     * Sets the size of the paddle
     * @param w Width
     * @param h Height
     */
      public static void setSizePaddle(int w, int h){
          WIDTH = w;
          HEIGHT = h;
    }
      /**
       * Sets (static) table boundaries to restrict paddle movement
       * @param left x-Coordinate describing left side
       * @param right x-Coordinate describing right side
       * @param top y-Coordinate describing top side
       * @param bottom y-Coordinate describing bottom side
       */
      public static void setTableBounds(int left, int right, int top, int bottom){
        tableLeft = left;
        tableRight = right;
        tableTop = top;
        tableBottom = bottom;
      }
      /**
       * Sets enabled status (can move or not)
       * @param b Whether enabled or not
       */
      public void setEnabled(boolean b){
          enabled = b;
      }
      
  //Movement
      /**
       * If able to move without exceeding boundaries, moves the distance, else moves to the boundary.
       * Only moves if enabled.
       * @distance Pixels to move, + is down, - is up
       */
      public void move(int distance){
          if (enabled){
              //Would Exceed Bottom Bound
              if (y + height + distance >= tableBottom){
                  //y = tableBottom - height;
                  y = tableBottom - height;
              }
              //Would Exceed Top Bound
              else if (y + distance <= tableTop){
                  y = tableTop;
              }
              //Valid Move
              else{
                  y += distance;
              }
          }
      }
      /**
       * Moves the paddle up 1/15th of the table
       */
      public void moveUp(){
        int tableHeight = tableBottom - tableTop;
        int step = -tableHeight/paddleStep;

        move(step);
      }  
      /**
       * Moves the paddle down 1/15th of the table
       */
      public void moveDown(){
        int tableHeight = tableBottom - tableTop;
        int step = tableHeight/paddleStep;

        move(step);      
      }
 
      
  //Graphical Interface
      /**
       * Draws the paddle on the graphic
       * @param g Graphics
       */
      public void draw(Graphics g){
        g.setColor(paddleColor);
        g.fillRect(x, y, width, height);
      }    
}
