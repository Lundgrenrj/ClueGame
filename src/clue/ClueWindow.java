/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import common.Client;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.*;

/**
 *
 * @author Robby
 */
public class ClueWindow {

    private ClueGame cGame = new ClueGame();
    private ClueClient connection;
    private Integer myID;
    private ArrayList<JLabel> listOfCards = new ArrayList<JLabel>();
    private JFrame frame;
    private JPanel topArea, bottomArea, rightArea, middlePannel, clueImage;
    private JButton startButton, quitButton, button1, button2, button3, turnOver, makeSuggestionButton, suggestButton, crapButton;
    private ArrayList<JButton> suspectButtons = new ArrayList<JButton>();
    private ArrayList<JButton> weaponButtons = new ArrayList<JButton>();
    private ArrayList<JButton> roomButtons = new ArrayList<JButton>();
    private ArrayList<JButton> cardsChosen = new ArrayList<JButton>();
    private int suspectButtonPosition, weaponButtonPosition, roomButtonPosition, cardsChosenPosition;
    private JLabel label1, suspectLabel, weaponLabel, roomLabel, yourCards;
    private JTextField textField, textField2, ipAddressField, amountField;
    private ImageIcon clueIcon, greenIcon, checkOffIcon;
    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private GraphicsDevice vc;
    private String cluePicture, host, message2;
    private ClueActionHandler listener;
    private SuspectActionHandler suspectActionH;
    private WeaponActionHandler weaponActionH;
    private RoomActionHandler roomActionH;
    private ChooseCardHandler chooseCardH;
    private boolean gameStarted = false;
    private int j, k;

    public ClueWindow() {
        windowInit();
    }

    public void windowInit() {
        
        frame = new JFrame("First GUI");
        frame.setLayout(new AbsoluteLayout());
        Container content = frame.getContentPane();
        content.setBackground(Color.green);

        cluePicture = "images/Clue4.jpg";
        URL imageURL = this.getClass().getClassLoader().getResource(cluePicture);
        clueIcon = new ImageIcon(imageURL);

        topArea = new JPanel();
        topArea.setLayout(new AbsoluteLayout());
        topArea.setBackground(Color.black);

        middlePannel = new JPanel();
        middlePannel.setLayout(new AbsoluteLayout());
        middlePannel.setBackground(Color.black);

        bottomArea = new JPanel();
        bottomArea.setLayout(new AbsoluteLayout());
        bottomArea.setBackground(Color.black);

        frame.add(topArea, new AbsoluteConstraints(0, 0, 1024, 75));
        frame.add(middlePannel, new AbsoluteConstraints(0, 75, 1024, 600));
        frame.add(bottomArea, new AbsoluteConstraints(0, 675, 1024, 93));

        //topArea.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
        //middlePannel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
        //bottomArea.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));

        listener = new ClueActionHandler();
        suspectActionH = new SuspectActionHandler();
        weaponActionH = new WeaponActionHandler();
        roomActionH = new RoomActionHandler();
        chooseCardH = new ChooseCardHandler();

        startButton = new JButton("Single Player");
        button1 = new JButton("Host");
        button2 = new JButton("Join");
        button3 = new JButton("Credits");
        quitButton = new JButton("Quit");

        crapButton = new JButton("TestButton");

        label1 = new JLabel("Clue", JLabel.CENTER);
        label1.setFont(new Font("Arial", Font.PLAIN, 40));
        label1.setForeground(Color.GREEN);
        topArea.add(label1, new AbsoluteConstraints(0, 10, 1024, 50));

        clueImage = new ImagePanel(clueIcon.getImage());
        //middlePannel.add(clueImage, new AbsoluteConstraints(0, 0, 1024, 600));
        middlePannel.setBackground(Color.BLACK);
        middlePannel.setBorder(BorderFactory.createLineBorder(Color.white));
        
        bottomArea.add(startButton, new AbsoluteConstraints(25, 0, 150, 30));
        startButton.setBackground(Color.yellow);
        startButton.setFont(new Font("Arial", Font.PLAIN, 15));
        startButton.setForeground(Color.RED);
        startButton.addActionListener(listener);

        bottomArea.add(button1, new AbsoluteConstraints(225, 0, 150, 30));
        button1.setBackground(Color.yellow);
        button1.setFont(new Font("Arial", Font.PLAIN, 15));
        button1.setForeground(Color.RED);
        button1.addActionListener(listener);

        bottomArea.add(button2, new AbsoluteConstraints(425, 0, 150, 30));
        button2.setBackground(Color.yellow);
        button2.setFont(new Font("Arial", Font.PLAIN, 15));
        button2.setForeground(Color.RED);
        button2.addActionListener(listener);

        bottomArea.add(button3, new AbsoluteConstraints(625, 0, 150, 30));
        button3.setBackground(Color.yellow);
        button3.setFont(new Font("Arial", Font.PLAIN, 15));
        button3.setForeground(Color.RED);
        button3.addActionListener(listener);

        bottomArea.add(quitButton, new AbsoluteConstraints(825, 0, 150, 30));
        quitButton.setBackground(Color.yellow);
        quitButton.setFont(new Font("Arial", Font.PLAIN, 15));
        quitButton.setForeground(Color.RED);
        quitButton.addActionListener(listener);

        makeSuggestionButton = new JButton("MakeSuggestion");
        makeSuggestionButton.addActionListener(listener);

        suggestButton = new JButton("Suggest");
        suggestButton.addActionListener(listener);

        frame.setUndecorated(false);
        frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
        frame.setLocation(dim.width/10, dim.height/100);

  
  
  
        frame.pack();
        frame.setVisible(true);
        
        /*GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        vc = ge.getDefaultScreenDevice();
        DisplayMode dmCurrent = vc.getDisplayMode();
        DisplayMode dmNormal = new DisplayMode(1024, 768, 32, DisplayMode.REFRESH_RATE_UNKNOWN);
        DisplayMode dmSmaller = new DisplayMode(1024, 768, 32, DisplayMode.REFRESH_RATE_UNKNOWN);
        try {
            vc.setFullScreenWindow(frame);
  
            if (dmSmaller != null && vc.isDisplayChangeSupported()) {
                try {
                    vc.setDisplayMode(dmNormal);
                } catch (Exception e) {
                }
            }



        } catch (Exception e) {
            System.err.println("Exception: " + e);
            vc.setFullScreenWindow(null);
        }

        while (frame.isVisible()) {
            try {
                Thread.currentThread().sleep(1280);
            } catch (InterruptedException e) {
            }
        }
        vc.setFullScreenWindow(null);
        * */
    }

    public void hostGame() {
        middlePannel.removeAll();
        bottomArea.removeAll();
        bottomArea.add(quitButton, new AbsoluteConstraints(825, 0, 150, 30));

        label1.setText("How many players?(2-6)");
        textField = new JTextField(20);
        middlePannel.add(textField, new AbsoluteConstraints(480, 64, 64, 20));
        textField.setBackground(Color.yellow);
        textField.addActionListener(new GetText());
        frame.repaint();

    }

    public void joinGame() {
        middlePannel.removeAll();
        bottomArea.removeAll();
        bottomArea.add(quitButton, new AbsoluteConstraints(825, 0, 150, 30));

        label1.setText("Enter IP address of host");
        ipAddressField = new JTextField(20);
        ipAddressField.setBackground(Color.yellow);
        ipAddressField.addActionListener(new GetIPAddress());
        middlePannel.add(ipAddressField, new AbsoluteConstraints(480, 64, 64, 20));
        frame.repaint();
    }

    private class GetText implements ActionListener, Runnable {

        public void actionPerformed(ActionEvent e) {

            new Thread(new GetText()).start();
            middlePannel.remove(textField);
            frame.repaint();
            //label1.setText("Please Enter Your Name");
            //textField2 = new JTextField(20);
            //middlePannel.add(textField2, new AbsoluteConstraints(480, 64, 64, 20));
        }

        @Override
        public void run() {
            try {
                ClueHub clueHub = new ClueHub(2000);

                connection = new ClueClient("127.0.0.1", 2000);
                Integer numPlayers = new Integer(Integer.parseInt(textField.getText()));
                connection.send(numPlayers);

            } catch (IOException ex) {
                Logger.getLogger(ClueWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class GetIPAddress implements ActionListener, Runnable {

        @Override
        public void actionPerformed(ActionEvent e) {
            host = ipAddressField.getText();
            new Thread(new GetIPAddress()).start();
            middlePannel.remove(ipAddressField);

            frame.repaint();
        }

        @Override
        public void run() {
            try {
                connection = new ClueClient(host, 2000);
                message2 = "New Client";
                connection.send(message2);
            } catch (IOException ex) {
                Logger.getLogger(ClueWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class ClueActionHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent a) {
            if (a.getSource() == startButton) {
                System.out.println("Start button was pressed...");
                label1.setText("You pressed Single Player. Under Construction..");
                //singlePlayer();
                //label1.setText("Murderer: " + cGame.returnMurderer() + " MurderWeapon: " + cGame.returnmurderWeapon() + " MurderRoom: " + cGame.returnmurderRoom());
            }
            if (a.getSource() == button1) {
                System.out.println("button1 button was pressed...");
                label1.setText("Hehe you pressed button1");
                hostGame();
                //frame.repaint();          
            }

            if (a.getSource() == button2) {
                System.out.println("button3 button was pressed...");
                label1.setText("Hehe you pressed button2");
                joinGame();
                frame.repaint();
            }

            if (a.getSource() == button3) {
                System.out.println("button3 button was pressed...");
                label1.setText("Hehe you pressed button3");
                frame.repaint();
            }

            if (a.getSource() == quitButton) {
                System.out.println("Stop button was pressed...");
                System.exit(0);
            }

            if (a.getSource() == turnOver) {
                cGame.setCurrentGameMessage("playerMoved");
                connection.send(cGame);
            }

            if (a.getSource() == makeSuggestionButton) {
                bottomArea.remove(makeSuggestionButton);
                cGame.setCurrentGameMessage("suggestStarted1");
                System.out.println("You pressed the makeSuggestionButton");
                label1.setText("You just clicked Suggest");
                suspectButtonPosition = 100;
                for (int i = 0; i < cGame.getSuspects().length; i++) {

                    suspectButtons.add(new JButton("CardLabel " + i));
                    middlePannel.add(suspectButtons.get(i), new AbsoluteConstraints(450, suspectButtonPosition, 150, 25));
                    suspectButtons.get(i).setText(cGame.getSuspects()[i]);
                    suspectButtons.get(i).addActionListener(suspectActionH);
                    suspectButtonPosition = suspectButtonPosition + 30;

                }
                frame.repaint();
                //connection.send(cGame);
            }

            if (a.getSource() == suggestButton) {
                System.out.println("You pressed the SuggestButton!!!!!!!!");
                suggestButton.setEnabled(false);
                System.out.println("The messageMap to This myID is: "+cGame.getMessageMap().get(myID));
                label1.setText(cGame.getMessageMap().get(myID));
                connection.send(cGame);
                
            }



        }
    }

    private class SuspectActionHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent a) {

            for (int i = 0; i < cGame.getSuspects().length; i++) {

                System.out.println("a.getSource(): "+a.getSource());
                System.out.println("suspectButtons.get(i): "+suspectButtons.get(i));
                
                if (a.getSource() == suspectButtons.get(i)) {
                    for (int j = 0; j < cGame.getSuspects().length; j++) {
                        middlePannel.remove(suspectButtons.get(j));
                    }
                    //frame.repaint();
                    label1.setText("You presssed: " + cGame.getSuspects()[i]);
                    cGame.setSuggestionString(cGame.getSuspects()[i]);
                    weaponButtonPosition = 100;
                    for (j = 0; j < cGame.getWeapons().length; j++) {
                        weaponButtons.add(new JButton("CardLabel " + j));
                        middlePannel.add(weaponButtons.get(j), new AbsoluteConstraints(450, weaponButtonPosition, 150, 25));
                        weaponButtons.get(j).setText(cGame.getWeapons()[j]);
                        weaponButtons.get(j).addActionListener(weaponActionH);
                        weaponButtonPosition = weaponButtonPosition + 30;

                    }
                }
            }

        }
    }

    private class WeaponActionHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent a) {

            for (int i = 0; i < cGame.getWeapons().length; i++) {


                if (a.getSource() == weaponButtons.get(i)) {
                    for (int j = 0; j < cGame.getWeapons().length; j++) {
                        middlePannel.remove(weaponButtons.get(j));
                    }
                    //frame.repaint();
                    label1.setText("You presssed: " + cGame.getWeapons()[i]);
                    cGame.setSuggestionString(cGame.getWeapons()[i]);
                    roomButtonPosition = 100;
                    for (j = 0; j < cGame.getRooms().length; j++) {
                        roomButtons.add(new JButton("CardLabel " + j));
                        middlePannel.add(roomButtons.get(j), new AbsoluteConstraints(450, roomButtonPosition, 150, 25));
                        roomButtons.get(j).setText(cGame.getRooms()[j]);
                        roomButtons.get(j).addActionListener(roomActionH);
                        roomButtonPosition = roomButtonPosition + 30;

                    }
                }
            }

        }
    }

    private class RoomActionHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent a) {
            for (int i = 0; i < cGame.getRooms().length; i++) {
                if (a.getSource() == roomButtons.get(i)) {
                    for (int j = 0; j < cGame.getRooms().length; j++) {
                        middlePannel.remove(roomButtons.get(j));
                    }
                    //frame.repaint();
                    label1.setText("You presssed: " + cGame.getRooms()[i]);
                    cGame.setSuggestionString(cGame.getRooms()[i]);

                    bottomArea.add(suggestButton, new AbsoluteConstraints(0, 0, 130, 30));

                }
            }
            frame.repaint();
        }
    }

    private class ChooseCardHandler implements ActionListener {

        String chosenCardText;

        @Override
        public void actionPerformed(ActionEvent a) {
            System.out.println();
            
 
            
         
            for (int i = 0; i < cardsChosen.size(); i++) {
                System.out.println(a.getSource());
                System.out.println(cardsChosen.get(i));
                
                if (a.getSource() == cardsChosen.get(i)) {
                    System.out.println("cardsChosen.get(i): "+cardsChosen.get(i).getText());
                    chosenCardText = cardsChosen.get(i).getText();
                    label1.setText("You chose to show: "+chosenCardText);
                    cGame.setChosenCard(chosenCardText);
                    connection.send(cGame);
                    break;
                }
            }
            
            for (int i = 0; i < cardsChosen.size(); i++) {
                middlePannel.remove(cardsChosen.get(i));
            }
            frame.repaint();
        }
    }

    private class ClueClient extends Client {

        /**
         * Connect to the hub at a specified host name and port number.
         */
        public ClueClient(String hubHostName, int hubPort) throws IOException {
            super(hubHostName, hubPort);
            //
            
        }

        /**
         * Responds to a message received from the Hub.  The only messages that
         * are supported are TicTacToeGameState objects.  When one is received,
         * the newState() method in the TicTacToeWindow class is called.  To avoid
         * problems with synchronization, that method is called using
         * SwingUtilities.invokeLater() so that it will run in the GUI event thread.
         */
        protected void messageReceived(final Object message) {
            
            if(myID == null){
                myID=connection.getID();
                System.out.println("My ID is: "+connection.getID());
            }

            if (message instanceof ClueGame) {
                cGame = (ClueGame) message;
                System.out.println("It IS A CLUEGAME..");
                System.out.println("Murderer from client is: "+cGame.returnMurderer());
                System.out.println("Size of messageMap is: "+cGame.getMessageMap().size());
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            if (cGame.getGameFullStatus() && gameStarted == false) {
                                loadStart(connection.getID());
                                gameStarted = true;

                            }

                            //Game Started
                            if (cGame.getGameFullStatus() == true) {

                                //Your Turn
                                if (cGame.getPlayerTurn() == connection.getID()) {
                                    label1.setText("It is your turn");
                                    turnOver.setEnabled(true);
                                    makeSuggestionButton.setEnabled(true);
                                    frame.repaint();
                                    System.out.println("0 messageMapIS: " + cGame.getMessageMap().get(0));
                                    System.out.println("1 messageMapIS: " + cGame.getMessageMap().get(1));
                                    frame.repaint();
                                    if (cGame.getChosenCard() != null) {
                                       
                                     
                                        label1.setText("The other player showed you: " + cGame.getChosenCard());                    
                                        frame.repaint();
                                        cGame.setChosenCard(null);
                                    }
                                }

                                //Not Your Turn                                                                     
                                    if (cGame.getPlayerTurn() != connection.getID()) {
                                        label1.setText("Not your turn");
                                        turnOver.setEnabled(false);
                                        System.out.println("0 messageMapIS: "+cGame.getMessageMap().get(0));
                                        System.out.println("1 messageMapIS: "+cGame.getMessageMap().get(1));
                                        
                                        if(cGame.getMessageMap().get(1).equals("YouHaveCard")){

                                            label1.setText("You have a card to show");
                                            cardsChosenPosition = 20;
                                            cardsChosen.clear();
                                            System.out.println("Before cGame.getSuggestionStringSize: "+cGame.getSuggestionString().size());
                                            System.out.println("Before cardsChosen.size: "+cardsChosen.size());
                                            
                                            for (int i = 0; i < cGame.getSuggestionString().size(); i++) {
                                                if (cGame.getPlayers().get(connection.getID()).getPlayerHand().contains(cGame.getSuggestionString().get(i))) {
                                                    System.out.println("You have these card to choose From: " + cGame.getSuggestionString().get(i));
                                                    cardsChosen.add(new JButton("cardsChosen " + i));
                                                }
                                            }
                                            System.out.println("After cGame.getSuggestionStringSize: "+cGame.getSuggestionString().size());
                                            System.out.println("After cardsChosen.size: "+cardsChosen.size());
                                            
                                            for (int i = 0; i < cardsChosen.size(); i++) {
                                               
                                                System.out.println("Index is: "+i+": cardsChosenText is: " + cGame.getSuggestionString().get(i));
                                                middlePannel.add(cardsChosen.get(i), new AbsoluteConstraints(500, cardsChosenPosition, 150, 25));
                                                cardsChosen.get(i).setText(cGame.getSuggestionString().get(i));
                                                cardsChosen.get(i).addActionListener(chooseCardH);
                                                cardsChosenPosition = cardsChosenPosition + 30;

                                            }
                                            
                                                                
                                            //middlePannel.add(crapButton, new AbsoluteConstraints(500, 20, 150, 25));
                                            //crapButton.addActionListener(chooseCardH);

                                        }


                                        //if (cGame.getPlayers().get(connection.getID()).getPlayerHand().get(i).contains(cGame.getSuggestionString()[i])) {
                                        //    System.out.println("MatchFound!! "+i);
                                        //cardsChosen.add(new JButton("cardsChosen " + i));

                                        //middlePannel.add(cardsChosen.get(i), new AbsoluteConstraints(450, cardsChosenPosition, 150, 25));
                                        //cardsChosen.get(i).setText(cGame.getSuspects()[i]);
                                        //cardsChosen.get(i).addActionListener(suspectActionH);
                                        //cardsChosenPosition = cardsChosenPosition + 30;


                                        //




                                    }
                            
                            } else {
                                if (cGame.getCurrentGameMessage().equalsIgnoreCase("Not yet")) {
                                    label1.setText("Please wait untit everybody joins...");
                                    //turnOver.setEnabled(true);
                                }
                            }
                            //try {
                            //label1.setText("Starting Game");
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ClueWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } //catch (InterruptedException ex) {
                    //   Logger.getLogger(ClueWindow.class.getName()).log(Level.SEVERE, null, ex);
                    //}
                    //}
                });
            } else if (message instanceof String) {

                message2 = (String) message;
                System.out.println("Recieved String: " + message2);
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        if (cGame.getGameFullStatus() == true) {

                            //Your Turn
                            if (cGame.getPlayerTurn() == connection.getID()) {
                                if (message2.equalsIgnoreCase("PlayerHasCard")) {
                                    
                                    
                                    System.out.println("Message is: PlayerHasCard");
                                    label1.setText("The NextPlayer has a card you suggested");                         
                                    frame.repaint();
                                } else if (message2.equalsIgnoreCase("PlayerHasNoCard")) {
                                 
                                    System.out.println("The NextPlayer doesn't have a card suggested");
                                    label1.setText("The NextPlayer doesn't have a card you suggested");
                                    frame.repaint();
                                }


                            }
                            
                            else {
                                
                                
                                
                            } 
                        }





                    }
                });




            }

            //if (message instanceof TicTacToeGameState) {
            //    SwingUtilities.invokeLater(new Runnable() {
            //         public void run() {  // calls a method at the end of the TicTacToeWindow class
            //             newState((TicTacToeGameState) message);
        }
        //     });
        // }
    }

    /**
     * If a shutdown message is received from the Hub, the user is notified
     * and the program ends.
     */
    
    protected void serverShutdown(String message) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                // JOptionPane.showMessageDialog(TicTacToeWindow.this,
                //        "Your opponent has disconnected.\nThe game is ended.");
                // System.exit(0);
            }
        });
    }

    public void loadStart(int thisThreadIndex) throws InterruptedException {
        frame.remove(middlePannel);
        frame.remove(topArea);
        frame.remove(bottomArea);
        middlePannel.removeAll();

        rightArea = new DrawLine();
        rightArea.setLayout(new AbsoluteLayout());
        rightArea.setBackground(Color.black);

        //frame.add(topArea, new AbsoluteConstraints(0, 0, 1024, 75));
        //frame.add(middlePannel, new AbsoluteConstraints(0, 75, 680, 455));
        //frame.add(rightArea, new AbsoluteConstraints(720, 75, 310, 455));
        //frame.add(bottomArea, new AbsoluteConstraints(0, 530, 1024, 70));

        frame.add(topArea, new AbsoluteConstraints(0, 0, 1024, 75));
        frame.add(middlePannel, new AbsoluteConstraints(0, 75, 680, 600));
        frame.add(rightArea, new AbsoluteConstraints(680, 75, 344, 600));
        frame.add(bottomArea, new AbsoluteConstraints(0, 675, 1024, 93));



        topArea.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 4));
        rightArea.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 4));
        middlePannel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 4));
        bottomArea.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 4));

        //label1.setText("Your Name is: "+cGame.getPlayers().get(thisThreadIndex).getPlayerName());



        turnOver = new JButton("Done with Turn");
        turnOver.addActionListener(listener);
        turnOver.setEnabled(false);
        makeSuggestionButton.setEnabled(false);
        bottomArea.add(turnOver, new AbsoluteConstraints(500, 0, 130, 30));

        bottomArea.add(makeSuggestionButton, new AbsoluteConstraints(0, 0, 130, 30));

        //label1.setText(cGame.returnMurderer() + ":" + cGame.returnmurderWeapon() + ":" + cGame.returnmurderRoom());
        bottomArea.add(quitButton, new AbsoluteConstraints(150, 0, 130, 30));

        yourCards = new JLabel("Here are the cards that you have:");
        yourCards.setFont(new Font("Arial", Font.PLAIN, 25));
        yourCards.setForeground(Color.RED);
        middlePannel.add(yourCards, new AbsoluteConstraints(128, 0, 400, 50));


        int labelPosition = 50;

        for (int i = 0; i < cGame.getCardsPerPlayer(); i++) {

            listOfCards.add(new JLabel("CardLabel " + i));
            listOfCards.get(i).setFont(new Font("Arial", Font.PLAIN, 15));
            listOfCards.get(i).setForeground(Color.GREEN);
            middlePannel.add(listOfCards.get(i), new AbsoluteConstraints(200, labelPosition, 400, 50));
            labelPosition = labelPosition + 10;
            listOfCards.get(i).setText(cGame.getPlayers().get(thisThreadIndex).getPlayerHand().get(i));
            labelPosition = labelPosition + 15;

        }

        suspectLabel = new JLabel("Suspects");
        suspectLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        suspectLabel.setForeground(Color.RED);
        JTable suspectTable = new JTable(new SuspectsTable(cGame, thisThreadIndex));
        suspectTable.setFont(new Font("Serif", Font.BOLD, 16));
        suspectTable.setRowHeight(24);
        suspectTable.setBackground(Color.WHITE);
        TableColumnModel columnModelS = suspectTable.getColumnModel();
        TableColumn columnS0 = columnModelS.getColumn(0);
        columnS0.setPreferredWidth(85);
        TableColumn columnS1 = columnModelS.getColumn(1);
        columnS1.setPreferredWidth(10);
        TableColumn columnS2 = columnModelS.getColumn(2);
        columnS2.setPreferredWidth(200);
        rightArea.add(suspectTable, new AbsoluteConstraints(0, 30, 342, 144));
        rightArea.add(suspectLabel, new AbsoluteConstraints(40, 0, 342, 30));


        weaponLabel = new JLabel("Weapons");
        weaponLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        weaponLabel.setForeground(Color.RED);

        JTable weaponsTable = new JTable(new WeaponsTable(cGame, thisThreadIndex));
        weaponsTable.setFont(new Font("Serif", Font.BOLD, 16));
        weaponsTable.setRowHeight(24);
        weaponsTable.setBackground(Color.WHITE);
        TableColumnModel columnModelW = weaponsTable.getColumnModel();
        TableColumn columnW0 = columnModelW.getColumn(0);
        columnW0.setPreferredWidth(85);
        TableColumn columnW1 = columnModelW.getColumn(1);
        columnW1.setPreferredWidth(10);
        TableColumn columnW2 = columnModelW.getColumn(2);
        columnW2.setPreferredWidth(200);
        rightArea.add(weaponsTable, new AbsoluteConstraints(0, 205, 342, 144));
        rightArea.add(weaponLabel, new AbsoluteConstraints(40, 175, 342, 30));


        roomLabel = new JLabel("Rooms");
        roomLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        roomLabel.setForeground(Color.RED);

        JTable roomsTable = new JTable(new RoomsTable(cGame, thisThreadIndex));
        roomsTable.setFont(new Font("Serif", Font.BOLD, 16));
        roomsTable.setRowHeight(24);
        suspectTable.setBackground(Color.WHITE);
        TableColumnModel columnModelR = roomsTable.getColumnModel();
        TableColumn columnR0 = columnModelR.getColumn(0);
        columnR0.setPreferredWidth(85);
        TableColumn columnR1 = columnModelR.getColumn(1);
        columnR1.setPreferredWidth(10);
        TableColumn columnR2 = columnModelR.getColumn(2);
        columnR2.setPreferredWidth(200);
        rightArea.add(roomsTable, new AbsoluteConstraints(0, 382, 342, 216));
        rightArea.add(roomLabel, new AbsoluteConstraints(45, 350, 342, 30));



    }
}
