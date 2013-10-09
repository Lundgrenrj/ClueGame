/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Robby
 */
public class Player implements Serializable{
    private ArrayList<String> playersHandList = new ArrayList<String>();
    private String playerName;
    
    public Player(String playerName){
        this.playerName = playerName;
    }
    
    public void addToHand(String card){
        playersHandList.add(card);
    }
    
    public ArrayList<String> getPlayerHand(){
        return playersHandList;
    }
    public void setPlayerName(String name){
        this.playerName = name;
    } 
    
    public String getPlayerName(){
        return playerName;
    }
   
}
