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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static client.Client.sInput;
import server.Message;

/**
 *
 * @author INSECT
 */
// serverdan gelecek mesajları dinleyen thread
class Listen extends Thread {

    public void run() {
        //soket bağlı olduğu sürece dön
        while (Client.socket.isConnected()) {
            try {
                //mesaj gelmesini bloking olarak dinyelen komut
                Message received = (Message) (sInput.readObject());
                //mesaj gelirse bu satıra geçer
                //mesaj tipine göre yapılacak işlemi ayır.
                switch (received.type) {
                    case Name:
                        break;
                    case RivalConnected:
                        String name = received.content.toString();
                        GoGame.goGame.txtb_player2Name.setText(name);
                        GoGame.goGame.btn_send.setEnabled(true);
                        GoGame.goGame.txtb_sendMessage.setEditable(true);
                        //Game.ThisGame.btn_pick.setEnabled(true);
                        //Game.ThisGame.btn_send_message.setEnabled(true);
                        //Game.ThisGame.tmr_slider.start();
                        break;
                    case Disconnect:
                        break;
                    case Color:
                        String pl = received.content.toString();
                        if (pl.equals("b")) {
                            GoGame.goGame.player = 1;
                        } else {
                            GoGame.goGame.player = 2;
                        }
                        break;
                    case Turn:
                        String trn = received.content.toString();
                        if(trn.equals("p")) {
                            GoGame.goGame.turn = 1;
                        }
                        break;
                    case Text:
                        String rivalNick = GoGame.goGame.txtb_player2Name.getText();
                        String oldChat = GoGame.goGame.txta_chat.getText();
                        String newChat = oldChat + rivalNick + ": " + received.content.toString() + "\n";
                        GoGame.goGame.txta_chat.setText(newChat);
                        //Game.ThisGame.txt_receive.setText(received.content.toString());
                        break;
                    case Selected:
                        //Game.ThisGame.RivalSelection = (int) received.content;

                        break;

                    case Bitis:
                        break;

                }

            } catch (IOException ex) {

                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                //Client.Stop();
                break;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                //Client.Stop();
                break;
            }
        }

    }
}

public class Client {

    //her clientın bir soketi olmalı
    public static Socket socket;

    //verileri almak için gerekli nesne
    public static ObjectInputStream sInput;
    //verileri göndermek için gerekli nesne
    public static ObjectOutputStream sOutput;
    //serverı dinleme thredi 
    public static Listen listenMe;

    public static void Start(String ip, int port) {
        try {
            // Client Soket nesnesi
            Client.socket = new Socket(ip, port);
            Client.Display("Servera bağlandı");
            // input stream
            Client.sInput = new ObjectInputStream(Client.socket.getInputStream());
            // output stream
            Client.sOutput = new ObjectOutputStream(Client.socket.getOutputStream());
            Client.listenMe = new Listen();
            Client.listenMe.start();

            //ilk mesaj olarak isim gönderiyorum
            Message msg = new Message(Message.Message_Type.Name);
            msg.content = GoGame.goGame.nickName;
            Client.Send(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //client durdurma fonksiyonu
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

    //mesaj gönderme fonksiyonu
    public static void Send(Message msg) {
        try {
            Client.sOutput.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
