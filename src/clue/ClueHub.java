package clue;

import java.io.IOException;
import common.Hub;

public class ClueHub extends Hub {

    ClueGame cGame = new ClueGame();
    int[] numPlayers;
    int playerTurn;
    String message2;

    public ClueHub(int port) throws IOException {
        super(port);
        setAutoreset(true);
    }

    protected void messageReceived(int playerID, Object message) {

        numPlayers = getPlayerList();
        if (message instanceof Integer) {
            cGame.setNumPlayers(message);
            cGame.setMurder();
            cGame.shuffleCards();
            cGame.dealCards2();
            cGame.setCurrentGameMessage("Not Yet");
            System.out.println("Murderer from Hub is: "+cGame.returnMurderer());
            sendToAll(cGame);

        } else if (message instanceof String) {
            message2 = (String) message;
            if (message2.equals("New Client")) {
                playerTurn = cGame.getPlayerTurn();

                if (cGame.getNumPlayers() != numPlayers.length) {
                    System.out.println("Waiting for other Players...");
                    cGame.setCurrentGameMessage("Not Yet");
                    sendToAll(cGame);
                } else if (cGame.getNumPlayers() == numPlayers.length) {
                    cGame.setGameFullStatus(true);
                    for (int i = 0; i < numPlayers.length; i++) {
                        if (i == playerTurn) {
                            cGame.setMessageMap(i, "Go");
                            System.out.println(i+" messageMapIS: "+cGame.getMessageMap().get(i));
                        }
                        if (i != playerTurn) {
                            cGame.setMessageMap(i, "Not your turn");
                            System.out.println(i+" messageMapIS: "+cGame.getMessageMap().get(i));         
                        }
                    }
                    sendToAll(cGame);
                }

            }


        } else if (message instanceof ClueGame) {
            cGame = (ClueGame) message;
            if (cGame.getCurrentGameMessage().equals("playerMoved")) {
                if (playerID == 1) {
                }
                cGame.playerMove();
                playerTurn = cGame.getPlayerTurn();
                System.out.println("Player Turn: " + playerTurn);
                for (int i = 0; i < numPlayers.length; i++) {
                    if (i == playerTurn) {
                        cGame.setMessageMap(i,"Go");
                        sendToAll(cGame);

                    }
                    if (i != playerTurn) {
                        cGame.setMessageMap(i,"Not your turn");
                        sendToAll(cGame);
                    }
                }
            }

            if (cGame.suggested) {
                System.out.println("Grabbing the Suggestion!!");
                for (int i = 0; i < numPlayers.length; i++) {
                    if (i == playerTurn) {
                        System.out.println("S1: " + cGame.getSuggestionString().get(0));
                        System.out.println("S2: " + cGame.getSuggestionString().get(1));
                        System.out.println("S3: " + cGame.getSuggestionString().get(2));
                        System.out.println("PlayerID: " + playerID);

                        System.out.println("Player1 Hand: " + cGame.getPlayers().get(0).getPlayerHand());
                        System.out.println("Player2 Hand: " + cGame.getPlayers().get(1).getPlayerHand());

                        if (cGame.suggestion(cGame.getSuggestionString().get(0), cGame.getSuggestionString().get(1), cGame.getSuggestionString().get(2), playerID)) {  
                            cGame.setMessageMap(i,"PlayerHasCard");
                            System.out.println("Player" + playerTurn + " has suggested");
                            sendToAll(cGame);
                        } else {
                            cGame.setMessageMap(i,"PlayerHasNoCard");
                            System.out.println("Player" + playerTurn + " has suggested");
                            sendToAll(cGame);
                        }
                        if(cGame.getChosenCard() != null){
                            sendToAll(cGame);
                        }
                        


                    } else {
                        if (cGame.suggestion(cGame.getSuggestionString().get(0), cGame.getSuggestionString().get(1), cGame.getSuggestionString().get(2), playerID)) {
                            System.out.println("Player" + playerTurn + " has suggested");
                            cGame.setMessageMap(i,"YouHaveCard");
                            sendToAll(cGame);
                        }

                    }
                }

            }


        }

    }
}
