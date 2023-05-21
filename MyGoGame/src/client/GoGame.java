package client;

import Message.Message;
import com.sun.tools.javac.Main;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 *
 * @author talha
 */
public class GoGame extends javax.swing.JFrame {

    public int boardSize = -1; // size of board
    int[][] boardArray;        // board array for plays
    public int lblIndex = -1;
    public int player = 0;     // player id 
    public int turn = 0;       // for decide the turn
    ArrayList<JLabel> listOfLabel = new ArrayList<>(); // list for labels
    // game area
    int startX = 150;
    int startY = 50;
    int endX = 870;
    int endY = 710;

    public class boardComp extends JComponent {

        public boardComp() {
        }

        // painting the area of game
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setPaint(Color.RED);
            g2d.setStroke(new BasicStroke(5.f));
            for (int i = startY; i <= endY; i += 110) {
                g2d.drawLine(startX, i, endX, i);
            }
            for (int i = startX; i <= endX; i += 120) {
                g2d.drawLine(i, startY, i, endY);
            }
        }
    }

    public static GoGame goGame; // global variable of our game class
    String nickName;             // holding nickname of user
    boardComp boardPaint;

    public GoGame() {
        initComponents();
        boardPaint = new boardComp();
        pnl_game.setLayout(new BorderLayout());
        pnl_game.add(boardPaint, BorderLayout.CENTER);
        goGame = this;

    }

    // listening the clicks
    private class Clicklistener implements MouseListener {

        int moveIndex = 0;

        public void mouseClicked(MouseEvent e) {
            Object source = e.getSource();                          // get clicked object
            if (source instanceof JLabel && turn == 1) {            // if its label and its our turn
                JLabel clickedLabel = (JLabel) e.getSource();       // get this label
                for (int i = 0; i < listOfLabel.size(); i++) {
                    if (clickedLabel == listOfLabel.get(i)) {
                        lblIndex = i;                               // find this label's index from list
                    }
                }
                int rowValue = lblIndex / boardArray.length;        // find row value from array which mentioned lbl index
                int columnValue = lblIndex % boardArray[0].length; // find col value from array which mentioned lbl index

                if (boardArray[rowValue][columnValue] == 0) {      // if clicked label is empty
                    addPicture(player, lblIndex);                  // add the piece of our color
                    int columns = (endX - startX) / 120;
                    int rows = (endY - startY) / 110;
                    int index = 0;
                    for (int i = 0; i <= rows; i++) {
                        for (int j = 0; j <= columns; j++) {
                            int x = j * 120 + startX - 15;
                            int y = i * 110 + startY - 15;
                            listOfLabel.get(index).setLocation(x, y);   // relocate the labels
                            index++;
                        }
                    }
                    updateArray(lblIndex);                          // update the play from array
                    int captured = checkCapturing();                // check if there is captured pieces
                    if (captured != 0) {
                        addScore(captured);                         // if there is captured, change score
                    }
                    boolean isFinished = isGameFinished();          // check if game is finished
                    if (!isFinished) {                              // if its not finished
                        turn = 0;                                   // assign 0 to turn, because its not our turn
                        btn_pas.setEnabled(false);
                        txtb_turn.setText("Wait for other player");
                        Message msg = new Message(Message.Message_Type.Turn);
                        msg.content = "p"; //play
                        Client.Send(msg);                           // send message to other client because turn changed
                    } else {
                        btn_pas.setEnabled(false);                  // if game is finished, disable the pas button
                        btn_play.setEnabled(true);
                    }

                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e
        ) {
        }

        @Override
        public void mouseReleased(MouseEvent e
        ) {
        }

        @Override
        public void mouseEntered(MouseEvent e
        ) {
        }

        @Override
        public void mouseExited(MouseEvent e
        ) {
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_main = new javax.swing.JPanel();
        pnl_settings = new javax.swing.JPanel();
        lbl_nickname = new javax.swing.JLabel();
        txtb_nickname = new javax.swing.JTextField();
        btn_play = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txta_chat = new javax.swing.JTextArea();
        lbl_chat = new javax.swing.JLabel();
        f = new javax.swing.JTextField();
        txtb_player1Name = new javax.swing.JTextField();
        txtb_player2Name = new javax.swing.JTextField();
        lbl_vs = new javax.swing.JLabel();
        lbl_gameName = new javax.swing.JLabel();
        txtb_sendMessage = new javax.swing.JTextField();
        btn_send = new javax.swing.JButton();
        txtb_turn = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        player1_score = new javax.swing.JTextField();
        player2_score = new javax.swing.JTextField();
        btn_pas = new javax.swing.JButton();
        pnl_game = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1440, 760));
        setMinimumSize(new java.awt.Dimension(1440, 760));
        setName("GameFrame"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        pnl_main.setBackground(new java.awt.Color(255, 204, 204));
        pnl_main.setMaximumSize(new java.awt.Dimension(1440, 760));
        pnl_main.setMinimumSize(new java.awt.Dimension(1440, 760));
        pnl_main.setPreferredSize(new java.awt.Dimension(1440, 760));
        pnl_main.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_settings.setBackground(new java.awt.Color(0, 204, 204));
        pnl_settings.setMaximumSize(new java.awt.Dimension(120, 760));
        pnl_settings.setMinimumSize(new java.awt.Dimension(120, 760));

        lbl_nickname.setForeground(new java.awt.Color(0, 0, 0));
        lbl_nickname.setText("Nickname");

        txtb_nickname.setMinimumSize(new java.awt.Dimension(65, 25));

        btn_play.setText("Play");
        btn_play.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_playActionPerformed(evt);
            }
        });

        txta_chat.setEditable(false);
        txta_chat.setColumns(20);
        txta_chat.setRows(5);
        jScrollPane1.setViewportView(txta_chat);

        lbl_chat.setForeground(new java.awt.Color(0, 0, 0));
        lbl_chat.setText("Chat");

        f.setEditable(false);

        txtb_player1Name.setEditable(false);

        txtb_player2Name.setEditable(false);
        txtb_player2Name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtb_player2NameActionPerformed(evt);
            }
        });

        lbl_vs.setForeground(new java.awt.Color(0, 0, 0));
        lbl_vs.setText("VS");

        lbl_gameName.setFont(new java.awt.Font("Helvetica Neue", 0, 36)); // NOI18N
        lbl_gameName.setForeground(new java.awt.Color(255, 51, 51));
        lbl_gameName.setText("GO GAME");

        txtb_sendMessage.setEditable(false);
        txtb_sendMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtb_sendMessageActionPerformed(evt);
            }
        });

        btn_send.setText("Send");
        btn_send.setEnabled(false);
        btn_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sendActionPerformed(evt);
            }
        });

        txtb_turn.setText("Wait for other player");

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Turn");

        player1_score.setText("0");

        player2_score.setText("0");

        btn_pas.setText("Pas");
        btn_pas.setEnabled(false);
        btn_pas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_settingsLayout = new javax.swing.GroupLayout(pnl_settings);
        pnl_settings.setLayout(pnl_settingsLayout);
        pnl_settingsLayout.setHorizontalGroup(
            pnl_settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_settingsLayout.createSequentialGroup()
                .addGroup(pnl_settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_settingsLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(lbl_gameName))
                    .addGroup(pnl_settingsLayout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addComponent(lbl_chat))
                    .addGroup(pnl_settingsLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(pnl_settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_settingsLayout.createSequentialGroup()
                                .addComponent(txtb_sendMessage)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_send))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnl_settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnl_settingsLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(lbl_nickname)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtb_nickname, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnl_settingsLayout.createSequentialGroup()
                            .addGap(27, 27, 27)
                            .addGroup(pnl_settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(f, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_play, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(pnl_settingsLayout.createSequentialGroup()
                            .addGap(21, 21, 21)
                            .addGroup(pnl_settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(pnl_settingsLayout.createSequentialGroup()
                                    .addGroup(pnl_settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtb_player1Name, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                                        .addComponent(player1_score))
                                    .addGap(18, 18, 18)
                                    .addGroup(pnl_settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnl_settingsLayout.createSequentialGroup()
                                            .addGap(9, 9, 9)
                                            .addComponent(jLabel1))
                                        .addGroup(pnl_settingsLayout.createSequentialGroup()
                                            .addComponent(lbl_vs)
                                            .addGap(18, 18, 18)
                                            .addComponent(txtb_player2Name, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addComponent(player2_score, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnl_settingsLayout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(txtb_turn, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_settingsLayout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(btn_pas)))
                .addContainerGap(88, Short.MAX_VALUE))
        );
        pnl_settingsLayout.setVerticalGroup(
            pnl_settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_settingsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_gameName)
                .addGap(28, 28, 28)
                .addGroup(pnl_settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtb_nickname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_nickname))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_play)
                .addGap(18, 18, 18)
                .addGroup(pnl_settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_settingsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pnl_settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_vs)
                            .addComponent(txtb_player1Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtb_player2Name))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(player1_score, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(player2_score, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtb_turn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_pas)
                .addGap(47, 47, 47)
                .addComponent(lbl_chat)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_send)
                    .addComponent(txtb_sendMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(204, 204, 204)
                .addComponent(f, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        pnl_main.add(pnl_settings, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnl_game.setBackground(new java.awt.Color(255, 204, 102));
        pnl_game.setMaximumSize(new java.awt.Dimension(99999, 99999));
        pnl_game.setMinimumSize(new java.awt.Dimension(1067, 1000));
        pnl_game.setPreferredSize(new java.awt.Dimension(1445, 1200));

        javax.swing.GroupLayout pnl_gameLayout = new javax.swing.GroupLayout(pnl_game);
        pnl_game.setLayout(pnl_gameLayout);
        pnl_gameLayout.setHorizontalGroup(
            pnl_gameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1075, Short.MAX_VALUE)
        );
        pnl_gameLayout.setVerticalGroup(
            pnl_gameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );

        pnl_main.add(pnl_game, new org.netbeans.lib.awtextra.AbsoluteConstraints(371, 0, 1075, 881));

        getContentPane().add(pnl_main);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    void updatePicturesWithArray() { // update pictures with checking array
        ImageIcon icon = null;
        JLabel myLbl;
        for (int i = 0; i < boardArray.length; i++) {
            for (int j = 0; j < boardArray[0].length; j++) {
                int lblidx = (i * boardArray.length) + j;
                if (boardArray[i][j] == 0) { // empty
                    myLbl = listOfLabel.get(lblidx);
                    icon = new ImageIcon("src/images/empty.png"); 
                    myLbl.setIcon(icon);
                    myLbl.getParent().setComponentZOrder(myLbl, 0);
                } else if (boardArray[i][j] == 1) { // black
                    myLbl = listOfLabel.get(lblidx);
                    icon = new ImageIcon("src/images/black.png");
                    myLbl.setIcon(icon);
                    myLbl.getParent().setComponentZOrder(myLbl, 0);
                }
                if (boardArray[i][j] == 2) { // white
                    myLbl = listOfLabel.get(lblidx);
                    icon = new ImageIcon("src/images/white.png");
                    myLbl.setIcon(icon);                             // set icon to label 
                    myLbl.getParent().setComponentZOrder(myLbl, 0);
                }
            }
        }
    }

    public void addScore(int captured) {  // add point 
        int currScore = Integer.parseInt(player1_score.getText()); // get current score
        currScore += captured;                                     // add new score
        player1_score.setText(Integer.toString(currScore));        // set the new score
        Message score = new Message(Message.Message_Type.Score);  
        score.content = Integer.toString(captured);
        Client.Send(score);                                        // send score and do same in other clients
    }

    public void addPicture(int currPlayer, int lblidx) { // add picture to the given address
        ImageIcon icon = null;
        JLabel myLbl = listOfLabel.get(lblidx);

        if (currPlayer == 1) {
            icon = new ImageIcon("src/images/black.png");
        } else if (currPlayer == 2) {
            icon = new ImageIcon("src/images/white.png");
        }
        myLbl.setIcon(icon);
        myLbl.getParent().setComponentZOrder(myLbl, 0);
    }

    public boolean isGameFinished() { // check if the game is finished
        boolean isFinished = true;
        for (int i = 0; i < boardArray.length; i++) {
            for (int j = 0; j < boardArray[0].length; j++) {
                if (boardArray[i][j] == 0) {            // if there is empty places game is not finished
                    isFinished = false;
                    break;
                }
                if (!isFinished) {
                    break;
                }
            }
        }
        return isFinished;
    }

    public void updateArray(int index) {                            // update array with given index
        int rowValue = index / boardArray.length;                   // find row value of index
        int columnValue = index % boardArray.length;                // find col value of index
        boardArray[rowValue][columnValue] = player;                 // update the value with player index
        Message move = new Message(Message.Message_Type.Movement);
        move.content = Integer.toString(index);
        Client.Send(move);                                          // send move to server
    }

    public int checkCapturing() {       // checking if there is captured piece
        int captured = 0;
        int rowValue = boardArray.length;
        int columnValue = boardArray[0].length;
        int currentVal = -1;
        int tempVal = -1;
        for (int i = 0; i < rowValue; i++) {
            for (int j = 0; j < columnValue; j++) {
                currentVal = boardArray[i][j];
                if (i == 0 && j == 0 && currentVal != 0) { // left top corner
                    if (currentVal == 1) {
                        tempVal = 2;
                    } else {
                        tempVal = 1;
                    }
                    int check1 = boardArray[0][1];
                    int check2 = boardArray[1][0];
                    if (tempVal == check1 && tempVal == check2) {
                        if (boardArray[i][j] == player) {
                            captured--;
                        } else {
                            captured++;
                        }
                        boardArray[i][j] = 0;
                    }
                } else if (i == 0 && j == columnValue - 1 && currentVal != 0) { // right top corner
                    if (currentVal == 1) {
                        tempVal = 2;
                    } else {
                        tempVal = 1;
                    }
                    int check1 = boardArray[0][j - 1];
                    int check2 = boardArray[i + 1][j];
                    if (tempVal == check1 && tempVal == check2) {
                        if (boardArray[i][j] == player) {
                            captured--;
                        } else {
                            captured++;
                        }
                        boardArray[i][j] = 0;
                    }
                } else if (i == rowValue - 1 && j == 0 && currentVal != 0) { // left bottom corner
                    if (currentVal == 1) {
                        tempVal = 2;
                    } else {
                        tempVal = 1;
                    }
                    int check1 = boardArray[i - 1][j];
                    int check2 = boardArray[i][j + 1];
                    if (tempVal == check1 && tempVal == check2) {
                        if (boardArray[i][j] == player) {
                            captured--;
                        } else {
                            captured++;
                        }
                        boardArray[i][j] = 0;
                    }
                } else if (i == rowValue - 1 && j == columnValue - 1 && currentVal != 0) { // right bottom corner
                    if (currentVal == 1) {
                        tempVal = 2;
                    } else {
                        tempVal = 1;
                    }
                    int check1 = boardArray[i - 1][j];
                    int check2 = boardArray[i][j - 1];
                    if (tempVal == check1 && tempVal == check2) {
                        if (boardArray[i][j] == player) {
                            captured--;
                        } else {
                            captured++;
                        }
                        boardArray[i][j] = 0;
                    }
                } else if (i == 0 && currentVal != 0) { // top side but not corner
                    if (currentVal == 1) {
                        tempVal = 2;
                    } else {
                        tempVal = 1;
                    }
                    int check1 = boardArray[i][j - 1];
                    int check2 = boardArray[i][j + 1];
                    int check3 = boardArray[i + 1][j];
                    if (tempVal == check1 && tempVal == check2 && tempVal == check3) {
                        if (boardArray[i][j] == player) {
                            captured--;
                        } else {
                            captured++;
                        }
                        boardArray[i][j] = 0;
                    }
                } else if (j == 0 && currentVal != 0) { // left side but not corner
                    if (currentVal == 1) {
                        tempVal = 2;
                    } else {
                        tempVal = 1;
                    }
                    int check1 = boardArray[i - 1][j];
                    int check2 = boardArray[i + 1][j];
                    int check3 = boardArray[i][j + 1];
                    if (tempVal == check1 && tempVal == check2 && tempVal == check3) {
                        if (boardArray[i][j] == player) {
                            captured--;
                        } else {
                            captured++;
                        }
                        boardArray[i][j] = 0;
                    }
                } else if (j == columnValue - 1 && currentVal != 0) { // right side but not corner
                    if (currentVal == 1) {
                        tempVal = 2;
                    } else {
                        tempVal = 1;
                    }
                    int check1 = boardArray[i - 1][j];
                    int check2 = boardArray[i + 1][j];
                    int check3 = boardArray[i][j - 1];
                    if (tempVal == check1 && tempVal == check2 && tempVal == check3) {
                        if (boardArray[i][j] == player) {
                            captured--;
                        } else {
                            captured++;
                        }
                        boardArray[i][j] = 0;
                    }
                } else if (i == rowValue - 1 && currentVal != 0) { // bottom side but not corner
                    if (currentVal == 1) {
                        tempVal = 2;
                    } else {
                        tempVal = 1;
                    }
                    int check1 = boardArray[i][j + 1];
                    int check2 = boardArray[i][j - 1];
                    int check3 = boardArray[i - 1][j];
                    if (tempVal == check1 && tempVal == check2 && tempVal == check3) {
                        if (boardArray[i][j] == player) {
                            captured--;
                        } else {
                            captured++;
                        }
                        boardArray[i][j] = 0;
                    }
                } else if (currentVal != 0) {
                    if (currentVal == 1) {
                        tempVal = 2;
                    } else {
                        tempVal = 1;
                    }
                    int check1 = boardArray[i][j + 1];
                    int check2 = boardArray[i][j - 1];
                    int check3 = boardArray[i - 1][j];
                    int check4 = boardArray[i + 1][j];
                    if (tempVal == check1 && tempVal == check2 && tempVal == check3 && tempVal == check4) {
                        if (boardArray[i][j] == player) {
                            captured--;
                        } else {
                            captured++;
                        }
                        boardArray[i][j] = 0;
                    }
                }
            }
        }
        updatePicturesWithArray(); // update pictures after check capturing
        return captured;
    }
    private void btn_playActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_playActionPerformed
        // TODO add your handling code here:

        if (txtb_nickname.getText().equals("")) {
            txtb_nickname.setText("choose nickname");
        } else {
            btn_play.setEnabled(false);             // disable play button after click
            txtb_nickname.setEnabled(false);        // can't change name after click
            nickName = txtb_nickname.getText();
            txtb_player1Name.setText(nickName);     // set name after click

            // adding labels to the playable lines
            int labelX = 40, labelY = 40;
            int columns = (endX - startX) / 120 + 1;
            int rows = (endY - startY) / 110 + 1;
            Clicklistener click = new Clicklistener();  // create click listener
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    JLabel lbl = new JLabel();
                    listOfLabel.add(lbl);               // add labels to list
                    lbl.addMouseListener(click);        // add click listener to all labels
                    int x = j * 120 + startX - 15;
                    int y = i * 110 + startY - 15;
                    ImageIcon icon = new ImageIcon("src/images/empty.png"); // first set empty picture
                    lbl.setIcon(icon);
                    pnl_game.add(lbl); // x = 150 - 870 = 120 ; y = 50 - 710 = 110

                    lbl.setBounds(x, y, labelX, labelY);

                }
            }
            boardArray = new int[rows][columns];
            // start client
            Client.Start("52.204.83.128", 2000); //you can replace "52.204.83.128" with localhost if u want to play in
                                                 // your computer
        }
    }//GEN-LAST:event_btn_playActionPerformed

    private void txtb_player2NameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtb_player2NameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtb_player2NameActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        Client.Stop();
    }//GEN-LAST:event_formWindowClosing

    private void txtb_sendMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtb_sendMessageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtb_sendMessageActionPerformed

    private void btn_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sendActionPerformed
        // TODO add your handling code here:
        String msg = txtb_sendMessage.getText();
        String oldMsg = txta_chat.getText();
        String newMsg = oldMsg + nickName + ": " + msg + "\n";
        txta_chat.setText(newMsg);
        txtb_sendMessage.setText("");
        Message text = new Message(Message.Message_Type.Text);
        text.content = msg;
        Client.Send(text);
    }//GEN-LAST:event_btn_sendActionPerformed

    private void btn_pasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pasActionPerformed
        // TODO add your handling code here:
        turn = 0;
        txtb_turn.setText("Wait for other player");
        Message msg = new Message(Message.Message_Type.Turn);
        msg.content = "p"; //play
        Client.Send(msg);
    }//GEN-LAST:event_btn_pasActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GoGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GoGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GoGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GoGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GoGame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btn_pas;
    public javax.swing.JButton btn_play;
    public javax.swing.JButton btn_send;
    public javax.swing.JTextField f;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_chat;
    private javax.swing.JLabel lbl_gameName;
    private javax.swing.JLabel lbl_nickname;
    private javax.swing.JLabel lbl_vs;
    public javax.swing.JTextField player1_score;
    public javax.swing.JTextField player2_score;
    private javax.swing.JPanel pnl_game;
    private javax.swing.JPanel pnl_main;
    private javax.swing.JPanel pnl_settings;
    public javax.swing.JTextArea txta_chat;
    private javax.swing.JTextField txtb_nickname;
    public javax.swing.JTextField txtb_player1Name;
    public javax.swing.JTextField txtb_player2Name;
    public javax.swing.JTextField txtb_sendMessage;
    public javax.swing.JTextField txtb_turn;
    // End of variables declaration//GEN-END:variables
}
