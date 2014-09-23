package org.umich.asbarber.pong.gui.settings;

import java.awt.Color;
import java.awt.event.KeyEvent;

/**
 *
 * @author Aaron
 */
public interface Customization {
    //Color and Format Scheme
        //General
        public static int borderSize = 2;
        //Window
        public static Color windowColor = Color.DARK_GRAY;
        //Pong Panel
        public static Color pTable = Color.BLACK;
        public static Color pBorder = Color.GREEN.darker();
        //Score Panel
        public static Color sPanel = Color.DARK_GRAY;
        public static Color sBorder = Color.BLACK;
            public static Color labelText = Color.WHITE.brighter();
            public static Color scoreText = Color.WHITE.brighter();
        //Control Panel
        public static Color cPanel = Color.DARK_GRAY;
        public static Color cBorder = Color.BLACK;
            public static Color buttonBackground = Color.GREEN;
            public static Color buttonForeground = Color.BLACK;
        //Ball and Paddle
        public static Color ballColor = Color.WHITE.brighter();
        public static Color paddleColor = Color.WHITE;
        
    //Size and Speed
        public static int windowWidth = 800;
        public static int windowHeight = 500;  
        public static int paddleStep = 7;        
        public static int ballRadius = 6;

        public static int pTableTimerDelay = 1500;
        
        
    //Controls
        public static int p1Up = KeyEvent.VK_Q;
        public static int p1Down = KeyEvent.VK_A;
        public static int p2Up = KeyEvent.VK_UP;
        public static int p2Down = KeyEvent.VK_DOWN;
        
        public static boolean isHumanRight = false;
        public static boolean isHumanLeft = false;
        
}
