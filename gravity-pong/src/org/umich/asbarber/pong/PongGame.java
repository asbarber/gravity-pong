package org.umich.asbarber.pong;

import org.umich.asbarber.pong.gui.control.GameFrame;

/**
 *
 * @author Aaron Barber
 */
public class PongGame {
    public GameFrame window;
    
    public void run(){
        window = new GameFrame();
    }
    public void displayControls(){
        window.displayControls();
    }
    public void close(){
        window.close();
    }
    public void reset(){
        window.reset();
    }
}
