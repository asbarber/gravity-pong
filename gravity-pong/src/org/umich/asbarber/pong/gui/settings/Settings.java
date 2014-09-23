package org.umich.asbarber.pong.gui.settings;

import org.umich.asbarber.pong.gui.control.GameFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.umich.asbarber.pong.objects.GravityField;
import org.umich.asbarber.pong.objects.PaddleAI;

/**
 *
 * @author Aaron
 */
public class Settings extends JOptionPane implements Customization{
    //User Editable                             //Values it will take
        private static int numPlayers = 0;      //0, 1, 2                               //Number of User Controlled Paddles
        private static int difficulty = 20;     //100, 200, 300 (possible +100/+200)    //Timer tick of PaddleAI
        private static int speed = 6;           //2, 4, 6                               //Pixels moved per timer tick in ball
        private static int gravity = 200;       //50, 100, 200                          //Mass of stars

    //Code editable
        //Ball
        private static int maxSpeed = 10;
        private static int ballRandomChange = 1;  
        
        //Paddle
        private static int paddleWidth = 10;
        private static int paddleHeight = 110;
        
        //AI
        private static int tableTick = 10;

    
    
    public static int getNumPlayers(){
        return numPlayers;
    }
    public static int getDifficulty(){
        return difficulty;
    }
    public static int getSpeed(){
        return speed;
    }
    public static int getGravity(){
        return gravity;
    }
    
    public static void askNumPlayers(){
        do{
            numPlayers = Integer.parseInt(showInputDialog(new JLabel("Number of players: (0, 1, 2)")));
        }while(!isValidPlayers(numPlayers));        
    }
    public static void askSpeed(){
        String input;
        
        //Loops until valid
        do{
            input = showInputDialog(new JLabel("Speed Level: (Slow, Medium, Fast)"));
        }while(!isValidSpeed(input));
        
        speed = convertSpeed(input);
    }
    public static void askGravity(){
        String input;
        do{
            input = showInputDialog(new JLabel("Gravity Strength: (Weak, Medium, Strong)"));
        }while(!isValidGravity(input));
        
        gravity = convertGravity(input);
    }
    public static void askDifficulty(){
        //One or No Player(s) Only
        if (numPlayers == 1 || numPlayers == 0){
            String input;
            
            //Loops until valid
            do{
                input = showInputDialog(new JLabel("Difficulty Level: (Easy, Medium, Hard)"));
            }while(!isValidDifficulty(input));
            
            difficulty = convertDifficulty(input);
        }
    }
    
    public static void changeSettings(){
        askNumPlayers();
        askDifficulty();
        askSpeed();      
        askGravity();
        
        updateSettings();
    }
    public static void updateSettings(){        
        GameFrame.getPongTable().setNumPlayers(numPlayers);
        PaddleAI.setDifficulty(difficulty);
        GameFrame.getBall().setSpeed(speed, speed);
        
        for (GravityField g: GameFrame.getGravityFields()){
            g.setMass(gravity);
        }
    }
    
    private static boolean isValidPlayers(int i){
        if (i != 0 && i != 1 && i != 2){
            showMessageDialog(null, "Please enter valid input!", "Error!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else{
            return true;
        }
    }   
    private static boolean isValidSpeed(String i){
        if (convertSpeed(i) == -1){
            showMessageDialog(null, "Please enter valid input!", "Error!", JOptionPane.ERROR_MESSAGE);            
            return false;
        }
        else {
            return true;
        }
    }    
    private static boolean isValidDifficulty(String i){
        if (convertDifficulty(i) == -1){
            showMessageDialog(null, "Please enter valid input!", "Error!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else{
            return true;
        }
    }
    private static boolean isValidGravity(String i){
        if (convertGravity(i) == -1){
            showMessageDialog(null, "Please enter valid input!", "Error!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else{
            return true;
        }
    }
    
    private static int convertSpeed(String i){
        if (i.toLowerCase().equals("slow")){
            return 2;
        }
        else if (i.toLowerCase().equals("medium")){
            return 4;
        }
        else if (i.toLowerCase().equals("fast")){
            return 6;
        }
        else{
            return -1;
        }        
    }    
    private static int convertDifficulty(String i){  
        int d;
        
        if (i.toLowerCase().equals("easy")){
            d = 300;
        }
        else if (i.toLowerCase().equals("medium")){
            d = 200;
        }
        else if (i.toLowerCase().equals("hard")){
            d = 100;
        }
        else{
            d = -1;
        }
        
        switch (speed){
            case 2: d += 200;   break;
            case 4: d += 100;    break;
        }
        
        return d;
    }
    private static int convertGravity(String i){
        if (i.toLowerCase().equals("weak")){
            return 20;
        }
        else if (i.toLowerCase().equals("medium")){
            return 100;
        }
        else if (i.toLowerCase().equals("strong")){
            return 200;
        }
        else{
            return -1;
        }          
    }
    
    
    public static int getMaxSpeed(){
        switch (speed){
            case 2: maxSpeed = 5;   break;
            case 4: maxSpeed = 7;   break;
            case 6: maxSpeed = 10;  break;
        }
        
        if (maxSpeed > getPaddleWidth()){
            maxSpeed = getPaddleWidth();
        }
        
        return maxSpeed;
    }  
    public static int getBallRandomness(){
        return ballRandomChange;
    }    
    public static int getPaddleWidth(){
        return paddleWidth;
    }
    public static int getPaddleHeight(){
        return paddleHeight;
    } 
    public static int getTimerTick(){
        return tableTick;
    }
}
