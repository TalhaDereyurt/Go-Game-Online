/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author talha
 */
import Message.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static client.Client.sInput;

// thread which is listening messages from server
class Listen extends Thread {

    public void run() {
        while (Client.socket.isConnected()) {
            try {
                //waiting for message
                Message received = (Message) (sInput.readObject());
                //if message came, check the type of message
                switch (received.type) {
                    case RivalConnected:
                        String name = received.content.toString();
                        GoGame.goGame.txtb_player2Name.setText(name); // set rival name
                        GoGame.goGame.btn_send.setEnabled(true); // enable the chat
                        GoGame.goGame.txtb_sendMessage.setEditable(true);
                        break;
                    case Color:
                        String pl = received.content.toString(); // for color pick 
                        if (pl.equals("b")) {
                            GoGame.goGame.player = 1; // if content b it's meaning player is 1 and black
                        } else {
                            GoGame.goGame.player = 2; // if content other it's meaning player is 2 and white
                        }
                        break;
                    case Turn:
                        String trn = received.content.toString();
                        if (trn.equals("p")) {
                            GoGame.goGame.turn = 1;                         // change turn to 1 for playing
                            GoGame.goGame.txtb_turn.setText("Your Turn");
                            GoGame.goGame.btn_pas.setEnabled(true);         // enable pas button for pas
                        }
                        break;
                    case Text:
                        String rivalNick = GoGame.goGame.txtb_player2Name.getText(); // get rival nick
                        String oldChat = GoGame.goGame.txta_chat.getText(); // hold old chat
                        String newChat = oldChat + rivalNick + ": " + received.content.toString() + "\n";
                        GoGame.goGame.txta_chat.setText(newChat); // set new text messages 
                        break;
                    case Movement:
                        String mesage = received.content.toString();
                        int moveIdx = Integer.parseInt(mesage); // which label is clicked 
                        int rowValue = moveIdx / GoGame.goGame.boardArray.length; // find row of label
                        int columnValue = moveIdx % GoGame.goGame.boardArray.length; // find column of label
                        int curPlayer = 0;
                        if (GoGame.goGame.player == 1) { // if this client is 1 then rival is 2
                            curPlayer = 2;
                        } else if (GoGame.goGame.player == 2) {// if this client is 2 then rival is 1
                            curPlayer = 1;
                        }
                        GoGame.goGame.boardArray[rowValue][columnValue] = curPlayer; // update array
                        GoGame.goGame.updatePicturesWithArray(); // update color for board
                        GoGame.goGame.checkCapturing(); // check if there is a capture
                        boolean isFinished = GoGame.goGame.isGameFinished(); // check if game is finished
                        if (isFinished) { // if finished send result to other client
                            Message rslt = new Message(Message.Message_Type.Result);
                            int clientScore = Integer.parseInt(GoGame.goGame.player1_score.getText());
                            int rivalScore = Integer.parseInt(GoGame.goGame.player2_score.getText());
                            if (clientScore > rivalScore) {
                                GoGame.goGame.txtb_turn.setText("YOU WON :)");
                                rslt.content = 0;
                            } else if (rivalScore > clientScore) {
                                GoGame.goGame.txtb_turn.setText("YOU LOST :(");
                                rslt.content = 1;
                            } else {
                                GoGame.goGame.txtb_turn.setText("TIE !");
                                rslt.content = 2;
                            }
                            Client.Send(rslt);
                        }
                        break;
                    case Score:
                        String message = received.content.toString();
                        String currScore = GoGame.goGame.player2_score.getText(); // get old score
                        int score = Integer.parseInt(currScore);
                        int adScore = Integer.parseInt(message);
                        int newScore = score + adScore; // add new score 

                        if (GoGame.goGame.turn == 0) { // if turn is 0 so other player's move
                            GoGame.goGame.player2_score.setText(Integer.toString(newScore));
                        } else { // if turn is 1 so its my move
                            GoGame.goGame.player1_score.setText(Integer.toString(newScore));
                        }
                        break;
                    case Result:
                        String rslt = received.content.toString(); // get result from server
                        int result = Integer.parseInt(rslt);
                        GoGame.goGame.btn_pas.setEnabled(false);
                        GoGame.goGame.btn_play.setEnabled(true);
                        if (result == 0) {
                            GoGame.goGame.txtb_turn.setText("YOU LOST :(");
                        } else if (result == 1) {
                            GoGame.goGame.txtb_turn.setText("YOU WON :)");
                        } else {
                            GoGame.goGame.txtb_turn.setText("TIE !");
                        }
                        break;
                }

            } catch (IOException ex) {

                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                break;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }
        }

    }
}

public class Client {

    public static Socket socket;              // every clients need socket
    public static ObjectInputStream sInput;   // for get the content
    public static ObjectOutputStream sOutput; // for send the content
    public static Listen listenMe;            // thread of listening the server

    public static void Start(String ip, int port) {
        try {
            Client.socket = new Socket(ip, port);
            Client.Display("Servera bağlandı");
            Client.sInput = new ObjectInputStream(Client.socket.getInputStream());
            Client.sOutput = new ObjectOutputStream(Client.socket.getOutputStream());
            Client.listenMe = new Listen();
            Client.listenMe.start(); // start listening thread

            //sending name to server
            Message msg = new Message(Message.Message_Type.Name);
            msg.content = GoGame.goGame.nickName;
            Client.Send(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // for stop the client
    public static void Stop() {
        try {
            if (Client.socket != null) {
                Client.listenMe.stop();
                Client.socket.close();
                Client.sOutput.flush();
                Client.sOutput.close();

                Client.sInput.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Display(String msg) {
        System.out.println(msg);
    }

    public static void Send(Message msg) { // message sending function
        try {
            Client.sOutput.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
