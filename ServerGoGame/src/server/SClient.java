package server;

/**
 *
 * @author talha
 */
import Message.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SClient {

    int id;
    public String name = "NoName";
    public int turn = 1;
    Socket soket;
    ObjectOutputStream sOutput;
    ObjectInputStream sInput;
    Listen listenThread; // thread for listening from client
    PairingThread pairThread; // pair the clients
    SClient rival;
    public boolean paired = false;

    public SClient(Socket gelenSoket, int id) {
        this.soket = gelenSoket;
        this.id = id;
        try {
            this.sOutput = new ObjectOutputStream(this.soket.getOutputStream());
            this.sInput = new ObjectInputStream(this.soket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        //thread nesneleri
        this.listenThread = new Listen(this);
        this.pairThread = new PairingThread(this);

    }

    public void Send(Message message) {  // sending message to client
        try {
            this.sOutput.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // thread for listening to clients
    class Listen extends Thread {

        SClient TheClient;

        Listen(SClient TheClient) {
            this.TheClient = TheClient;
        }

        public void run() {
            //while client is connected
            while (TheClient.soket.isConnected()) {
                try {
                    //waiting for message
                    Message received = (Message) (TheClient.sInput.readObject());
                    //if message came, check the type of message
                    switch (received.type) {
                        case Name:
                            TheClient.name = received.content.toString();
                            // after send name, start to search rival
                            TheClient.pairThread.start();
                            break;
                        case Text:
                            //send message to rival directly
                            Server.Send(TheClient.rival, received);
                            break;
                        case Turn:
                            Server.Send(TheClient.rival, received);
                        case Movement:
                            //send move to rival client
                            Server.Send(TheClient.rival, received);
                            break;
                        case Score:
                            Server.Send(TheClient.rival, received);
                            break;
                        case Result:
                            Server.Send(TheClient.rival, received);
                            break;
                    }

                } catch (IOException ex) {
                    Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                    //if client closed, remove from list
                    Server.Clients.remove(TheClient);

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                    //if client closed, remove from list
                    Server.Clients.remove(TheClient);
                }
            }

        }
    }

    // pairing thread for clients
    class PairingThread extends Thread {

        SClient TheClient;

        PairingThread(SClient TheClient) {
            this.TheClient = TheClient;
        }

        public void run() {
            //if client connected and not paired yet
            while (TheClient.soket.isConnected() && TheClient.paired == false) {
                try {
                    // lock
                    Server.pairTwo.acquire(1);
                    //if client is not paired
                    if (!TheClient.paired) {
                        SClient crival = null;
                        //until it find a pair
                        while (crival == null && TheClient.soket.isConnected()) {
                            //search pair in list
                            for (SClient clnt : Server.Clients) {
                                if (TheClient != clnt && clnt.rival == null) {
                                    //found pair
                                    crival = clnt;
                                    crival.paired = true;
                                    crival.turn = 2;
                                    crival.rival = TheClient;
                                    TheClient.rival = crival;
                                    TheClient.paired = true;
                                    break;
                                }
                            }
                            //every 1 second
                            sleep(1000);
                        }
                        //pairing success, send message to both of client
                        Message msg1 = new Message(Message.Message_Type.RivalConnected);
                        msg1.content = TheClient.name;
                        Server.Send(TheClient.rival, msg1);

                        Message msg2 = new Message(Message.Message_Type.RivalConnected);
                        msg2.content = TheClient.rival.name;
                        Server.Send(TheClient, msg2);
                        
                        // define the colors of both clients
                        Message msg3 = new Message(Message.Message_Type.Color);
                        msg3.content = "b";
                        Server.Send(TheClient.rival, msg3);

                        Message msg4 = new Message(Message.Message_Type.Color);
                        msg4.content = "w";
                        Server.Send(TheClient, msg4);
                        
                        // define first turn
                        Message msg5 = new Message(Message.Message_Type.Turn);
                        msg5.content = "p"; //play
                        Server.Send(TheClient.rival, msg5);
                    }
                    //release lock
                    Server.pairTwo.release(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PairingThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
