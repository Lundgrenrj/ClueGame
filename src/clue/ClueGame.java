package clue;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.Random;

class ClueGame implements Serializable {

    public boolean gameStarted = false;
    public boolean gameFinished = false;
    public int winner;
    public static Map<Integer,String> playersMap = new HashMap<Integer,String>();
    private Map<Integer,String> messageMap = new HashMap<Integer,String>();
    private String currentGameMessage = "";
    
    private ArrayList<String> deckOfCards = new ArrayList<String>();
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<String> suggestionString = new ArrayList<String>();
    private Random rand = new Random();
    private int numPlayers = 0;
    private int cardsPerPlayer;
    private String[] suspects = {"Scarlet", "Plum", "Mustard", "Peacock", "White", "Green"};
    private String[] weapons = {"Knife", "Candlestick", "Revolver", "Rope", "LeadPipe", "Wrench"};
    private String[] rooms = {"Hall", "Lounge", "DiningRoom", "Kitchen", "BallRoom", "Conservatory", "BilliardRoom", "Library", "Study"};
    private String card;
    private String murderer, murderWeapon, murderRoom;
    private String chosenCard;
    private int randomSusp;
    private int randomWeapon;
    private int randomRoom;
    private int currPlayerIndex = 0;
    private int playerTurn = 0;
    
    private int playersJoined = 0;
    private boolean gameFull = false;
    private String testMessage="Please wait untit everybody joins...";
    public boolean suggested = false;
    public boolean playerDisconnected;
    

    
    public Map<Integer,String> getMessageMap(){
        return this.messageMap;
    }
    
    public void setMessageMap(int messageInt, String messageString){
        this.messageMap.put(messageInt, messageString);
    }
       
    public String getTestMessage(){
        
        return this.testMessage;
    }
    
     public void setTestMessage(String message){
        this.testMessage=message;
    }
            
    public String[] getSuspects() {
        return this.suspects;
    }

    public String[] getWeapons() {
        return this.weapons;
    }

    public String[] getRooms() {
        return this.rooms;
    }

    public boolean getGameStatus() {
        return this.gameStarted;
    }

    public boolean getGameFullStatus() {
        return this.gameFull;
    }

    public void setGameFullStatus(boolean b) {
        this.gameFull = b;
    }

    public void setMurder() {
        
        playersMap.put( 0, suspects[0]);
        playersMap.put( 1, suspects[1] );
        playersMap.put( 2, suspects[2] );
        playersMap.put( 3, suspects[3] );
        playersMap.put( 4, suspects[4] );
        playersMap.put( 5, suspects[5] );
        
        int index;
        gameStarted = true;
        setCurrentGameMessage("The Game Has Begun!");
        makeDeck();
        randomSusp = rand.nextInt(6);
        randomWeapon = rand.nextInt(6);
        randomRoom = rand.nextInt(9);
        murderer = suspects[randomSusp];
        murderWeapon = weapons[randomWeapon];
        murderRoom = rooms[randomRoom];

        index = deckOfCards.indexOf(murderer);
        deckOfCards.remove(index);

        index = deckOfCards.indexOf(murderWeapon);
        deckOfCards.remove(index);

        index = deckOfCards.indexOf(murderRoom);
        deckOfCards.remove(index);

    }

    public String returnMurderer() {
        return murderer;
    }

    public String returnmurderWeapon() {
        return murderWeapon;
    }

    public String returnmurderRoom() {
        return murderRoom;
    }

    public void setNumPlayers(Object size) {
        this.numPlayers = (Integer) size;
        // this.players = new String[numPlayers];
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void playerHasJoined() {
        ++this.playersJoined;
    }

    public int getPlayersJoined() {
        return playersJoined;
    }

    public void addPlayer(String playerName) {
        //  this.players[this.currPlayerIndex] = playerName;
        //  this.currPlayerIndex++;
    }

    public int getCurrPlayerIndex() {
        return currPlayerIndex;
    }

    public void incrementPlayerIndex() {
        this.currPlayerIndex++;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    synchronized public void playerMove() {
        System.out.println("Player turn was: "+playerTurn);
        suggested = false;
        if (playerTurn == numPlayers - 1) {
            playerTurn = 0;
        } else {
            playerTurn++;
        }
        System.out.println("Player turn is now: "+playerTurn);
        System.out.println("SuggestionStringSizeShouldBe more is: "+suggestionString.size());
        if(!suggestionString.isEmpty()) {
            suggestionString.clear();
        }
        System.out.println("SuggestionStringSizeShouldBe 0 is: "+suggestionString.size());
    }

    public int getPlayerIndex(String[] array, String specificValue) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(specificValue)) {
                return i;
            }
        }
        return -1;

    }

    public boolean isFull() {
        if (currPlayerIndex == numPlayers) {
            return true;
        }
        return false;
    }

    public void setCurrentGameMessage(String message) {
        this.currentGameMessage = message;
    }

    public String getCurrentGameMessage() {
        return currentGameMessage;
    }

    public void makeDeck() {
        // Create an unshuffled deck of cards.
        for (int i = 0; i < suspects.length; i++) {
            deckOfCards.add(suspects[i]);
        }
        for (int i = 0; i < weapons.length; i++) {
            deckOfCards.add(weapons[i]);
        }

        for (int i = 0; i < rooms.length; i++) {
            deckOfCards.add(rooms[i]);
        }
    }

    public void shuffleCards() {
        int i = 17;
        while (i > 0) {
            int rand = (int) (Math.random() * (i + 1));
            card = deckOfCards.get(i);
            deckOfCards.set(i, deckOfCards.get(rand));
            deckOfCards.set(rand, card);
            i--;
        }
    }

    public void dealCards2() {
        cardsPerPlayer = deckOfCards.size() / numPlayers;
        int cardRemainder = deckOfCards.size() % numPlayers;

        //System.out.println("Number Players: " + numPlayers);
        //System.out.println("Cards Per Player: " + cardsPerPlayer);
        //System.out.println("Cards remainder: " + cardRemainder);

        int k = 0;
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player(""));

            for (int j = 0; j < cardsPerPlayer; j++) {
                players.get(i).addToHand(deckOfCards.get(k));
                k++;
            }
        }

        for (int i = 0; i < cardRemainder; i++) {
            players.get(i).addToHand(deckOfCards.get(k));
            k++;
        }
        if (cardRemainder > 0) {
            cardsPerPlayer++;
        }
    }

    public ArrayList getDeck() {
        return deckOfCards;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getCardsPerPlayer() {
        return cardsPerPlayer;
    }

    public boolean suggestion(String murdererS, String weaponS, String roomS, int playerIndex) {
        int nextPlayer = playerIndex + 1;

        if (playerIndex == numPlayers - 1) {
            nextPlayer = 0;
        }

        for (int i = 0; i < cardsPerPlayer; i++) {
            //if (playersHand[nextPlayer][i] != null) {
                if (players.get(nextPlayer).getPlayerHand().get(i).equals(murdererS) || players.get(nextPlayer).getPlayerHand().get(i).equals(weaponS) || players.get(nextPlayer).getPlayerHand().get(i).equals(roomS)) {
                    //return "Suggestion: Player " + nextPlayer + " has atleast 1 suggested card";
                    return true;
                }
            //}
        }

        //return "Suggestion: Player doesn't have card";
        return false;
    }

    public void setSuggestionString(String s){
        suggestionString.add(s);
        suggested = true;
    }
     
    public ArrayList<String> getSuggestionString(){
        return this.suggestionString;
    }
    
    public void setChosenCard(String c){
        this.chosenCard = c;
    }
    public String getChosenCard(){
        return this.chosenCard;
    }
            
    public boolean accuse(String murdererA, String weaponA, String roomA, int playerIndex) {
        if (murdererA.equals(this.murderer) && weaponA.equals(this.murderWeapon) && roomA.equals(this.murderRoom)) {
            this.gameFinished = true;
            return true;
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
        

        
        DataInputStream dis = new DataInputStream(System.in);
        String str1, str2, str3;
        ClueGame cGame = new ClueGame();
        cGame.setNumPlayers(3);
        cGame.setMurder();
        System.out.println("Murder:");
        System.out.println(cGame.returnMurderer() + ":" + cGame.returnmurderWeapon() + ":" + cGame.returnmurderRoom());
        System.out.println();
        cGame.shuffleCards();
        cGame.dealCards2();

        for (int i = 0; i < cGame.getPlayers().size(); i++) {
            System.out.println(cGame.getPlayers().get(i).getPlayerHand());
        }
        
        
        if(cGame.suggestion("Scarlet", "Knife", "Conservatory", 0)){
            System.out.println("True");
        }
        else{
            System.out.println("False");
        }
        
        //System.out.println();
        //str1 = dis.readLine();
        /*
        str2 = dis.readLine();
        str3 = dis.readLine();
        
        if (cGame.accuse(str1, str2, str3, 3)) {
        System.out.println("You Got it Right!!");
        } else {
        System.out.println("You got it wrong");
        }
         */
        //   }
    }
}