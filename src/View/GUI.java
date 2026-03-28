package View;

import Controller.Controller;
import Model.Card.SorryCard;
import Model.Pawn.Pawn;
import Model.Pawn.Position;
import Model.Player.Player;
import Model.Square.EndSlideSquare;
import Model.Square.InternalSlideSquare;
import Model.Square.StartSlideSquare;
import javafx.geometry.Pos;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * Set up user interface
 * @author ioannis kapetanakis
 */
public class GUI extends JFrame {
    private boolean iconSelected;
    private JButton selectedButton;
    public JButton buttons[][];
    public Controller game;
    private JButton fold;
    private JDesktopPane table;
    private JDesktopPane backcard;
    private gameDesktopPane gamePanel;
    private Image backgroundImage;
    private Image sorryImage;
    private Image backCardImage;
    private Image currCardImage;
    private JLabel receiveCardFont;
    private JLabel currCardFont;
    private JButton homeRedButton;
    private JButton homeYellowButton;
    private JButton startRedButton;
    private JButton startYellowButton;
    public JButton startRedPawnButton1; // its public for junit tests purposes
    public JButton startRedPawnButton2;
    public JButton startYellowPawnButton1;
    private JButton startYellowPawnButton2;
    private JButton homeRedPawnButton1;
    private JButton homeRedPawnButton2;
    private JButton homeYellowPawnButton1;
    private JButton homeYellowPawnButton2;
    private JDesktopPane infoBox;
    private JLabel label;
    private JLabel space;
    private JLabel turn_label;
    private JLabel cards_label;
    private JDesktopPane currcard;
    private Icon tmp_image1;
    private ImageIcon tmp;
    private Icon icons[];
    private String str;
    private Icon tmp_icon;
    private int i,j,i1,i2,j1,j2;
    private Pawn lastPlayedPawn;
    int [][] board;
    private int movesLeft;
    private Player winner;

    /**
     * <b>Constructor</b>
     * <b>postcondition: </b>Initializes the user interface
     * @throws IOException
     */
    public GUI() throws IOException {
        game = new Controller();
        buttons = new JButton[16][16];
        icons = new Icon[4];
        infoBox = new JDesktopPane();
        table = new JDesktopPane(){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(sorryImage, 350, 300, this);
            }
        };
        backcard = new JDesktopPane(){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backCardImage, 0, 0, this);
            }
        };

        currcard = new JDesktopPane(){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(currCardImage, 0, 0, this);
            }
        };

        gamePanel = new gameDesktopPane();
        setSize(1400,800);
        init_components();
    }

    /**
     * <b>Transformer</b>: initializes many of the components on the board
     * <b>Postcondition</b>: initializes the background image, cardlistener, game panel, background image and buttons
     */
    public void init_components() throws IOException {
        this.setTitle("Sorry Game");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        iconSelected = false;
        i1 = i2 = j1 = j2 = -1;
        movesLeft = 7;
        backcard.setBounds(1030, 180, 135, 185);
        currcard.setBounds(1205, 180, 135, 185);
        ImageIcon background = new ImageIcon(ImageIO.read(getClass().getResource("/images/background.png")).getScaledInstance(1400,800,Image.SCALE_SMOOTH));
        backgroundImage = background.getImage();
        ImageIcon sorry = new ImageIcon(ImageIO.read(getClass().getResource("/images/sorryImage.png")).getScaledInstance(300, 100, Image.SCALE_SMOOTH));
        ImageIcon bcard = new ImageIcon(ImageIO.read(getClass().getResource("/images/cards/backCard.png")).getScaledInstance(135, 185, Image.SCALE_SMOOTH));
        backCardImage = bcard.getImage();
        sorryImage = sorry.getImage();
        init_buttons();
        fold = new JButton("Fold Button");
        fold.setFont(new Font("Fold Button", Font.BOLD, 15));
        fold.setBounds(1030,410,300,50);
        game.getRandomCard();
        currCardImage = game.LastCardImage().getImage().getScaledInstance(135, 185, Image.SCALE_SMOOTH);
        fold.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               /**
                * <b>Transformer</b>: Pressing the fold button
                * <b>Postcondition</b>: Doing some action after fold button has been pushed
                */
               if(game.CanFold()){
                   if(!game.getLastCardValue().equals("12") && !game.getLastCardValue().equals("8")){
                       game.changeTurn();
                   }
                   game.getRandomCard();
                   try {
                       currCardImage = game.LastCardImage().getImage().getScaledInstance(135, 185, Image.SCALE_SMOOTH);
                   } catch (IOException ex) {
                       ex.printStackTrace();
                   }
                   turn_label.setText("Turn: "+turn_function(game.getTurn()));
                   cards_label.setText("Cards Left: "+game.numOfCards());
                   Graphics g1 = gamePanel.getGraphics();
                   gamePanel.paintComponent(g1);
                   gamePanel.repaint();
               }
           }
        } );
        fold.setBackground(Color.RED);
        receiveCardFont = new JLabel("Receive Card");
        receiveCardFont.setBounds(1045,340,1000,100);
        receiveCardFont.setFont(new Font("Receive Card", Font.ROMAN_BASELINE, 18));
        currCardFont = new JLabel("Current Card");
        currCardFont.setBounds(1220,340,1000,100);
        currCardFont.setFont(new Font("Current Card", Font.ROMAN_BASELINE, 18));

        homeRedButton = new JButton("Home");
        homeRedButton.setBounds(94,278,130,100);
        homeRedButton.setBackground(Color.WHITE);
        homeRedButton.setVerticalAlignment(SwingConstants.BOTTOM);
        homeRedButton.setBorder(BorderFactory.createLineBorder(Color.RED,5));
        homeRedButton.setFont(new Font("Home", Font.TRUETYPE_FONT, 25));
        homeRedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * <b>Transformer</b>: Pressing the red home button
                 * <b>Postcondition</b>: Doing some action after red home button has been pushed
                 */
                if(game.getTurn() == 1 && !game.gameFinished()){
                    if(selectedButton.equals(game.getPawn11())){
                        lastPlayedPawn = game.getPawn11();
                    }
                    else{
                        lastPlayedPawn = game.getPawn12();
                    }
                    if(game.CalculateMove(game.getLastCardValue(), lastPlayedPawn)[0] == 6 && game.CalculateMove(game.getLastCardValue(), lastPlayedPawn)[1] == 2){
                        lastPlayedPawn.setPosition(Position.Home);
                        game.MovePawn(lastPlayedPawn, 6, 2);
                        if(lastPlayedPawn.getName().equals("pawn11")){
                            homeRedPawnButton1.setVisible(true);
                        }
                        else{
                            homeRedPawnButton2.setVisible(true);
                        }
                    }
                    game.changeTurn();
                    iconSelected = false;
                    selectedButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                }
            }
        });

        homeYellowButton = new JButton("Home");
        homeYellowButton.setBounds(775,362,130,100);
        homeYellowButton.setBackground(Color.WHITE);
        homeYellowButton.setVerticalAlignment(SwingConstants.TOP);
        homeYellowButton.setBorder(BorderFactory.createLineBorder(Color.YELLOW,5));
        homeYellowButton.setFont(new Font("Home", Font.TRUETYPE_FONT, 25));
        homeYellowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * <b>Transformer</b>: Pressing the yellow home button
                 * <b>Postcondition</b>: Doing some action after yellow home button has been pushed
                 */
                if(game.getTurn() == 2 && !game.gameFinished()){
                    if(selectedButton.equals(game.getPawn21())){
                        lastPlayedPawn = game.getPawn21();
                    }
                    else{
                        lastPlayedPawn = game.getPawn22();
                    }
                    if(game.CalculateMove(game.getLastCardValue(), lastPlayedPawn)[0] == 9 && game.CalculateMove(game.getLastCardValue(), lastPlayedPawn)[1] == 15){
                        lastPlayedPawn.setPosition(Position.Home);
                        game.MovePawn(lastPlayedPawn, 9, 15);
                        if(lastPlayedPawn.getName().equals("pawn11")){
                            homeYellowPawnButton1.setVisible(true);
                        }
                        else{
                            homeYellowPawnButton2.setVisible(true);
                        }
                    }
                    game.changeTurn();
                    iconSelected = false;
                    selectedButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                }
            }
        });

        startRedButton = new JButton("Start");
        startRedButton.setBounds(217, 48, 130,100);
        startRedButton.setBackground(Color.WHITE);
        startRedButton.setVerticalAlignment(SwingConstants.BOTTOM);
        startRedButton.setBorder(BorderFactory.createLineBorder(Color.RED,5));
        startRedButton.setFont(new Font("Start", Font.TRUETYPE_FONT, 25));

        startYellowButton = new JButton("Start");
        startYellowButton.setBounds(652,592,130,100);
        startYellowButton.setBackground(Color.WHITE);
        startYellowButton.setVerticalAlignment(SwingConstants.TOP);
        startYellowButton.setBorder(BorderFactory.createLineBorder(Color.YELLOW,5));
        startYellowButton.setFont(new Font("Start", Font.TRUETYPE_FONT, 25));

        startRedPawnButton1 = new JButton();
        startRedPawnButton1.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/images/pawns/redPawn1.png")).getScaledInstance(50, 40, Image.SCALE_SMOOTH)));
        startRedPawnButton1.setBounds(225,70,50,40);
        startRedPawnButton1.setBackground(Color.WHITE);
        startRedPawnButton1.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        startRedPawnButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /**
                 * <b>Transformer</b>: Pressing the first red button inside the start red button
                 * <b>Postcondition</b>: Doing some action after first red button inside the start red button has been pushed
                 */
                if (game.getTurn() == 1 && game.getLastCardValue()!=null && !game.gameFinished()) {
                    if ((game.getLastCardValue().equals("1") || game.getLastCardValue().equals("2")) && game.getPosition(game.getPawn11()) == Position.Start) {
                        startRedPawnButton1.setVisible(false);
                        game.setPosition(game.getPawn11(), Position.SimplePosition);
                        tmp = (ImageIcon) buttons[0][4].getIcon();
                        buttons[0][4].setIcon(game.getPawn11().getImage());
                        buttons[0][4].setName("pawn11");
                        game.MovePawn(game.getPawn11(), 0, 4);
                        if (!game.getLastCardValue().equals("2")) {
                            game.changeTurn();
                        }
                        game.getRandomCard();
                        try {
                            currCardImage = game.LastCardImage().getImage().getScaledInstance(135, 185, Image.SCALE_SMOOTH);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        turn_label.setText("Turn: " + turn_function(game.getTurn()));
                        cards_label.setText("Cards Left: " + game.numOfCards());
                        Graphics g1 = gamePanel.getGraphics();
                        gamePanel.paintComponent(g1);
                        gamePanel.repaint();
                    }
                     else if (game.getLastCardValue().equals("Sorry")) {
                        i1=-1;
                        i2=-1;
                        j1=-1;
                        j2=-1;
                        board = game.getBoard();
                        for (int i = 0; i < 16; i++) {
                            for (int j = 0; j < 16; j++) {
                                if (board[i][j] == 21 || board[i][j] == 22) {
                                    if (i1 != -1 && j1 != -1) {
                                        i2 = i;
                                        j2 = j;
                                    } else {
                                        i1 = i;
                                        j1 = j;
                                    }
                                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                                }
                            }
                        }
                        iconSelected = true;
                        selectedButton = startRedPawnButton1;
                        turn_label.setText("Turn: "+turn_function(game.getTurn()));
                        cards_label.setText("Cards Left: "+game.numOfCards());
                        Graphics g1 = gamePanel.getGraphics();
                        gamePanel.paintComponent(g1);
                        gamePanel.repaint();
                    }
                }
            }
        } );

        startRedPawnButton2 = new JButton();
        startRedPawnButton2.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/images/pawns/redPawn2.png")).getScaledInstance(50, 40, Image.SCALE_SMOOTH)));
        startRedPawnButton2.setBounds(290,70,50,40);
        startRedPawnButton2.setBackground(Color.WHITE);
        startRedPawnButton2.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        startRedPawnButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /**
                 * <b>Transformer</b>: Pressing the second red button inside the start red button
                 * <b>Postcondition</b>: Doing some action after second red button inside the start red button has been pushed
                 */
                if (game.getTurn() == 1 && game.getLastCardValue()!=null && !game.gameFinished()){
                    if ((game.getLastCardValue().equals("1") || game.getLastCardValue().equals("2")) && game.getPosition(game.getPawn12()) == Position.Start) {
                        startRedPawnButton2.setVisible(false);
                        game.setPosition(game.getPawn12(), Position.SimplePosition);
                        tmp = (ImageIcon) buttons[0][4].getIcon();
                        buttons[0][4].setIcon(game.getPawn12().getImage());
                        buttons[0][4].setName("pawn12");
                        game.MovePawn(game.getPawn12(), 0, 4);
                        if(!game.getLastCardValue().equals("2")){
                            game.changeTurn();
                        }
                        game.getRandomCard();
                        try {
                            currCardImage = game.LastCardImage().getImage().getScaledInstance(135, 185, Image.SCALE_SMOOTH);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        turn_label.setText("Turn: "+turn_function(game.getTurn()));
                        cards_label.setText("Cards Left: "+game.numOfCards());
                        Graphics g1 = gamePanel.getGraphics();
                        gamePanel.paintComponent(g1);
                        gamePanel.repaint();
                    }
                    else if (game.getLastCardValue().equals("Sorry")) {
                        i1=-1;
                        i2=-1;
                        j1=-1;
                        j2=-1;
                        board = game.getBoard();
                        for (int i = 0; i < 16; i++) {
                            for (int j = 0; j < 16; j++) {
                                if (board[i][j] == 21 || board[i][j] == 22) {
                                    if (i1 != -1 && j1 != -1) {
                                        i2 = i;
                                        j2 = j;
                                    } else {
                                        i1 = i;
                                        j1 = j;
                                    }
                                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                                }
                            }
                        }
                        iconSelected = true;
                        selectedButton = startRedPawnButton2;
                        turn_label.setText("Turn: "+turn_function(game.getTurn()));
                        cards_label.setText("Cards Left: "+game.numOfCards());
                        Graphics g1 = gamePanel.getGraphics();
                        gamePanel.paintComponent(g1);
                        gamePanel.repaint();
                    }
                }
            }
        } );

        startYellowPawnButton1 = new JButton();
        startYellowPawnButton1.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/images/pawns/yellowPawn1.png")).getScaledInstance(50, 40, Image.SCALE_SMOOTH)));
        startYellowPawnButton1.setBounds(660,630,50,40);
        startYellowPawnButton1.setBackground(Color.WHITE);
        startYellowPawnButton1.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        startYellowPawnButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /**
                 * <b>Transformer</b>: Pressing the first yellow button inside the start yellow button
                 * <b>Postcondition</b>: Doing some action after first yellow button inside the start yellow button has been pushed
                 */
                if (game.getTurn() == 2 && game.getLastCardValue()!=null && !game.gameFinished()) {
                    if ((game.getLastCardValue().equals("1") || game.getLastCardValue().equals("2")) && game.getPosition(game.getPawn21()) == Position.Start) {
                        startYellowPawnButton1.setVisible(false);
                        game.setPosition(game.getPawn21(), Position.SimplePosition);
                        tmp = (ImageIcon) buttons[15][11].getIcon();
                        buttons[15][11].setIcon(game.getPawn21().getImage());
                        buttons[15][11].setName("pawn21");
                        game.MovePawn(game.getPawn21(), 15, 11);
                        if(!game.getLastCardValue().equals("2")){
                            game.changeTurn();
                        }
                        game.getRandomCard();
                        try {
                            currCardImage = game.LastCardImage().getImage().getScaledInstance(135, 185, Image.SCALE_SMOOTH);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        turn_label.setText("Turn: "+turn_function(game.getTurn()));
                        cards_label.setText("Cards Left: "+game.numOfCards());
                        Graphics g1 = gamePanel.getGraphics();
                        gamePanel.paintComponent(g1);
                        gamePanel.repaint();
                    }
                    else if (game.getLastCardValue().equals("Sorry")) {
                        i1=-1;
                        i2=-1;
                        j1=-1;
                        j2=-1;
                        board = game.getBoard();
                        for (int i = 0; i < 16; i++) {
                            for (int j = 0; j < 16; j++) {
                                if (board[i][j] == 11 || board[i][j] == 12) {
                                    if (i1 != -1 && j1 != -1) {
                                        i2 = i;
                                        j2 = j;
                                    } else {
                                        i1 = i;
                                        j1 = j;
                                    }
                                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                                }
                            }
                        }
                        iconSelected = true;
                        selectedButton = startYellowPawnButton1;
                        turn_label.setText("Turn: "+turn_function(game.getTurn()));
                        cards_label.setText("Cards Left: "+game.numOfCards());
                        Graphics g1 = gamePanel.getGraphics();
                        gamePanel.paintComponent(g1);
                        gamePanel.repaint();
                    }
                }
            }
        } );

        startYellowPawnButton2 = new JButton();
        startYellowPawnButton2.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/images/pawns/yellowPawn2.png")).getScaledInstance(50, 40, Image.SCALE_SMOOTH)));
        startYellowPawnButton2.setBounds(725,630,50,40);
        startYellowPawnButton2.setBackground(Color.WHITE);
        startYellowPawnButton2.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        startYellowPawnButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /**
                 * <b>Transformer</b>: Pressing the second yellow button inside the start yellow button
                 * <b>Postcondition</b>: Doing some action after second yellow button inside the start yellow button has been pushed
                 */
                if (game.getTurn() == 2 && game.getLastCardValue()!=null && !game.gameFinished()) {
                    if ((game.getLastCardValue().equals("1") || game.getLastCardValue().equals("2")) && game.getPosition(game.getPawn22()) == Position.Start) {
                        startYellowPawnButton2.setVisible(false);
                        game.setPosition(game.getPawn22(), Position.SimplePosition);
                        tmp = (ImageIcon) buttons[15][11].getIcon();
                        buttons[15][11].setIcon(game.getPawn22().getImage());
                        buttons[15][11].setName("pawn22");
                        game.MovePawn(game.getPawn22(), 15, 11);
                        if(!game.getLastCardValue().equals("2")){
                            game.changeTurn();
                        }
                        game.getRandomCard();
                        try {
                            currCardImage = game.LastCardImage().getImage().getScaledInstance(135, 185, Image.SCALE_SMOOTH);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        turn_label.setText("Turn: "+turn_function(game.getTurn()));
                        cards_label.setText("Cards Left: "+game.numOfCards());
                        Graphics g1 = gamePanel.getGraphics();
                        gamePanel.paintComponent(g1);
                        gamePanel.repaint();
                    }
                    else if (game.getLastCardValue().equals("Sorry")) {
                        i1=-1;
                        i2=-1;
                        j1=-1;
                        j2=-1;
                        board = game.getBoard();
                        for (int i = 0; i < 16; i++) {
                            for (int j = 0; j < 16; j++) {
                                if (board[i][j] == 11 || board[i][j] == 12) {
                                    if (i1 != -1 && j1 != -1) {
                                        i2 = i;
                                        j2 = j;
                                    } else {
                                        i1 = i;
                                        j1 = j;
                                    }
                                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                                }
                            }
                        }
                        iconSelected = true;
                        selectedButton = startYellowPawnButton2;
                        turn_label.setText("Turn: "+turn_function(game.getTurn()));
                        cards_label.setText("Cards Left: "+game.numOfCards());
                        Graphics g1 = gamePanel.getGraphics();
                        gamePanel.paintComponent(g1);
                        gamePanel.repaint();
                    }
                }
            }
        } );

        homeRedPawnButton1 = new JButton();
        homeRedPawnButton1.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/images/pawns/redPawn1.png")).getScaledInstance(50, 40, Image.SCALE_SMOOTH)));
        homeRedPawnButton1.setBounds(102,299,50,40);
        homeRedPawnButton1.setBackground(Color.WHITE);
        homeRedPawnButton1.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        homeRedPawnButton1.setVisible(false);

        homeRedPawnButton2 = new JButton();
        homeRedPawnButton2.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/images/pawns/redPawn2.png")).getScaledInstance(50, 40, Image.SCALE_SMOOTH)));
        homeRedPawnButton2.setBounds(167,299,50,40);
        homeRedPawnButton2.setBackground(Color.WHITE);
        homeRedPawnButton2.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        homeRedPawnButton2.setVisible(false);

        homeYellowPawnButton1 = new JButton();
        homeYellowPawnButton1.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/images/pawns/yellowPawn1.png")).getScaledInstance(50, 40, Image.SCALE_SMOOTH)));
        homeYellowPawnButton1.setBounds(783,404,50,40);
        homeYellowPawnButton1.setBackground(Color.WHITE);
        homeYellowPawnButton1.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        homeYellowPawnButton1.setVisible(false);

        homeYellowPawnButton2 = new JButton();
        homeYellowPawnButton2.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/images/pawns/yellowPawn2.png")).getScaledInstance(50, 40, Image.SCALE_SMOOTH)));
        homeYellowPawnButton2.setBounds(848,404,50,40);
        homeYellowPawnButton2.setBackground(Color.WHITE);
        homeYellowPawnButton2.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        homeYellowPawnButton2.setVisible(false);

        label = new JLabel("Info Box");
        space = new JLabel(" ");
        turn_label = new JLabel("Turn: "+turn_function(game.getTurn()));
        cards_label = new JLabel("Cards Left: "+game.numOfCards());
        infoBox.setLayout(new BoxLayout(infoBox, BoxLayout.Y_AXIS));
        infoBox.setBackground(Color.WHITE);
        infoBox.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
        infoBox.setBounds(1030,480,300,150);
        label.setFont(new Font("Info Box", Font.BOLD, 23));
        turn_label.setFont(new Font("Turn", Font.BOLD, 23));
        cards_label.setFont(new Font("Cards Left", Font.BOLD, 23));
        infoBox.add(label);
        infoBox.add(space);
        infoBox.add(turn_label);
        infoBox.add(cards_label);

        gamePanel.add(backcard, JLayeredPane.DEFAULT_LAYER);
        gamePanel.add(currcard, JLayeredPane.DEFAULT_LAYER);
        gamePanel.add(homeRedButton, JLayeredPane.PALETTE_LAYER);
        gamePanel.add(homeYellowButton, JLayeredPane.PALETTE_LAYER);
        gamePanel.add(startRedButton, JLayeredPane.PALETTE_LAYER);
        gamePanel.add(startYellowButton, JLayeredPane.PALETTE_LAYER);
        gamePanel.add(startRedPawnButton1, JLayeredPane.MODAL_LAYER);
        gamePanel.add(startRedPawnButton2, JLayeredPane.MODAL_LAYER);
        gamePanel.add(startYellowPawnButton1, JLayeredPane.MODAL_LAYER);
        gamePanel.add(startYellowPawnButton2, JLayeredPane.MODAL_LAYER);
        gamePanel.add(homeRedPawnButton1, JLayeredPane.MODAL_LAYER);
        gamePanel.add(homeRedPawnButton2, JLayeredPane.MODAL_LAYER);
        gamePanel.add(homeYellowPawnButton1, JLayeredPane.MODAL_LAYER);
        gamePanel.add(homeYellowPawnButton2, JLayeredPane.MODAL_LAYER);
        gamePanel.add(infoBox, JLayeredPane.PALETTE_LAYER);
        gamePanel.add(receiveCardFont);
        gamePanel.add(currCardFont);
        gamePanel.add(fold);
        add(gamePanel);
        setVisible(true);
    }

    /**
     * <b>Observer</b>: Returns the turn as a string
     * @return the turn as a string
     */
    public String turn_function(int turn) {
        if(turn == 1){
            return "Player 1 (Red)";
        }
        return "Player 2 (Yellow)";
    }

    /**
     * <b>Transformer</b>: initializes the buttons on the board
     * <b>Postcondition</b>: initializes each button and adds it on game panel
     */
    public void init_buttons() throws IOException {
        PawnListener cl = new PawnListener();
        gamePanel.add(table, JLayeredPane.DEFAULT_LAYER);
        table.setBounds(0, 0, 1000, 740);
        table.setBackground(Color.CYAN);
        table.setLayout(new GridLayout(16, 16));
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setName("Square");
                buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                buttons[i][j].addMouseListener(cl);
                buttons[i][j].setVisible(true);
                if (i == 0 || i == 15 || j == 0 || j == 15) {
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                    if (i == 0 && j == 1) {
                        buttons[i][j].setIcon(new StartSlideSquare(Color.RED).getImage());
                    }
                    else if(i == 0 && j == 2){
                        buttons[i][j].setIcon(new InternalSlideSquare(Color.RED).getImage());
                    }
                    else if(i == 0 && j == 3){
                        buttons[i][j].setIcon(new InternalSlideSquare(Color.RED).getImage());
                    }
                    else if(i == 0 && j == 4){
                        buttons[i][j].setIcon(new EndSlideSquare(Color.RED).getImage());
                    }
                    else if(i == 0 && j == 9){
                        buttons[i][j].setIcon(new StartSlideSquare(Color.RED).getImage());
                    }
                    else if(i == 0 && j == 10){
                        buttons[i][j].setIcon(new InternalSlideSquare(Color.RED).getImage());
                    }
                    else if(i == 0 && j == 11){
                        buttons[i][j].setIcon(new InternalSlideSquare(Color.RED).getImage());
                    }
                    else if(i == 0 && j == 12){
                        buttons[i][j].setIcon(new InternalSlideSquare(Color.RED).getImage());
                    }
                    else if(i == 0 && j == 13){
                        buttons[i][j].setIcon(new EndSlideSquare(Color.RED).getImage());
                    }
                    else if(i == 1 && j == 15){
                        buttons[i][j].setIcon(new StartSlideSquare(Color.BLUE).getImage());
                    }
                    else if(i == 2 && j == 15){
                        buttons[i][j].setIcon(new InternalSlideSquare(Color.BLUE).getImage());
                    }
                    else if(i == 3 && j == 15){
                        buttons[i][j].setIcon(new InternalSlideSquare(Color.BLUE).getImage());
                    }
                    else if(i == 4 && j == 15) {
                        buttons[i][j].setIcon(new EndSlideSquare(Color.BLUE).getImage());
                    }
                    else if(i == 9 && j == 15){
                        buttons[i][j].setIcon(new StartSlideSquare(Color.BLUE).getImage());
                    }
                    else if(i == 10 && j == 15){
                        buttons[i][j].setIcon(new InternalSlideSquare(Color.BLUE).getImage());
                    }
                    else if(i == 11 && j == 15){
                        buttons[i][j].setIcon(new InternalSlideSquare(Color.BLUE).getImage());
                    }
                    else if(i == 12 && j == 15){
                        buttons[i][j].setIcon(new InternalSlideSquare(Color.BLUE).getImage());
                    }
                    else if(i == 13 && j == 15) {
                        buttons[i][j].setIcon(new EndSlideSquare(Color.BLUE).getImage());
                    }
                    else if(i == 15 && j == 14){
                        buttons[i][j].setIcon(new StartSlideSquare(Color.YELLOW).getImage());
                    }
                    else if(i == 15 && j == 13){
                        buttons[i][j].setIcon(new InternalSlideSquare(Color.YELLOW).getImage());
                    }
                    else if(i == 15 && j == 12){
                        buttons[i][j].setIcon(new InternalSlideSquare(Color.YELLOW).getImage());
                    }
                    else if(i == 15 && j == 11){
                        buttons[i][j].setIcon(new EndSlideSquare(Color.YELLOW).getImage());
                    }
                    else if(i == 15 && j == 6){
                        buttons[i][j].setIcon(new StartSlideSquare(Color.YELLOW).getImage());
                    }
                    else if(i == 15 && j == 5){
                        buttons[i][j].setIcon(new InternalSlideSquare(Color.YELLOW).getImage());
                    }
                    else if(i == 15 && j == 4){
                        buttons[i][j].setIcon(new InternalSlideSquare(Color.YELLOW).getImage());
                    }
                    else if(i == 15 && j == 3){
                        buttons[i][j].setIcon(new InternalSlideSquare(Color.YELLOW).getImage());
                    }
                    else if(i == 15 && j == 2){
                        buttons[i][j].setIcon(new EndSlideSquare(Color.YELLOW).getImage());
                    }
                    else if(i == 14 && j == 0){
                        buttons[i][j].setIcon(new StartSlideSquare(Color.GREEN).getImage());
                    }
                    else if(i == 13 && j == 0){
                        buttons[i][j].setIcon(new InternalSlideSquare(Color.GREEN).getImage());
                    }
                    else if(i == 12 && j == 0){
                        buttons[i][j].setIcon(new InternalSlideSquare(Color.GREEN).getImage());
                    }
                    else if(i == 11 && j == 0){
                        buttons[i][j].setIcon(new EndSlideSquare(Color.GREEN).getImage());
                    }
                    else if(i == 6 && j == 0){
                        buttons[i][j].setIcon(new StartSlideSquare(Color.GREEN).getImage());
                    }
                    else if(i == 5 && j == 0){
                        buttons[i][j].setIcon(new InternalSlideSquare(Color.GREEN).getImage());
                    }
                    else if(i == 4 && j == 0){
                        buttons[i][j].setIcon(new InternalSlideSquare(Color.GREEN).getImage());
                    }
                    else if(i == 3 && j == 0){
                        buttons[i][j].setIcon(new InternalSlideSquare(Color.GREEN).getImage());
                    }
                    else if(i == 2 && j == 0){
                        buttons[i][j].setIcon(new EndSlideSquare(Color.GREEN).getImage());
                    }
                    else {
                        buttons[i][j].setBackground(Color.WHITE);
                    }
                    buttons[i][j].setVisible(true);
                }
                else{
                    if(i == 14 && j == 13){
                        buttons[i][j].setBackground(Color.YELLOW);
                    }
                    else if(i == 13 && j == 13){
                        buttons[i][j].setBackground(Color.YELLOW);
                    }
                    else if(i == 12 && j == 13){
                        buttons[i][j].setBackground(Color.YELLOW);
                    }
                    else if(i == 11 && j == 13){
                        buttons[i][j].setBackground(Color.YELLOW);
                    }
                    else if(i == 10 && j == 13){
                        buttons[i][j].setBackground(Color.YELLOW);
                    }
                    else if(i == 1 && j == 2){
                        buttons[i][j].setBackground(Color.RED);
                    }
                    else if(i == 2 && j == 2){
                        buttons[i][j].setBackground(Color.RED);
                    }
                    else if(i == 3 && j == 2){
                        buttons[i][j].setBackground(Color.RED);
                    }
                    else if(i == 4 && j == 2){
                        buttons[i][j].setBackground(Color.RED);
                    }
                    else if(i == 5 && j == 2){
                        buttons[i][j].setBackground(Color.RED);
                    }
                    else{
                        buttons[i][j].setEnabled(false);
                        buttons[i][j].setVisible(false);
                    }
                }
                table.add(buttons[i][j]);
            }
        }
    }

    /* Class that extends JDesktopPane that is used for initializing background image */
    class gameDesktopPane extends JDesktopPane {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, this);
        }
    }

    /**
     * <b>Transformer</b>: αuxiliary function for the case where a card has been drawn 7
     * <b>Postcondition</b>: calculates the distribution of positions in the event that the pawns split the moves
     */
    public void cardSeven_Movement(JButton but, Pawn pawn){
        i1 = Integer.parseInt(game.getLastCardValue());
        while(i1!=0) {
            i2 = game.CalculateMove(String.valueOf(i1), pawn)[0];
            j2 = game.CalculateMove(String.valueOf(i1), pawn)[1];
            if(but.equals(buttons[i2][j2])){
                i = i2;
                j = j2;
                movesLeft = 7 - i1;
            }
            i1 = i1-1;
            buttons[i2][j2].setBorder(BorderFactory.createLineBorder(Color.black));
        }
    }

    /**
     * <b>Transformer</b>: corrects the images above the view buttons
     * <b>Postcondition</b>: corrects the images above the view buttons
     */
    public void images_fix() throws IOException {
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                if(buttons[i][j].getName().equals("pawn11") || buttons[i][j].getName().equals("pawn12") || buttons[i][j].getName().equals("pawn21") || buttons[i][j].getName().equals("pawn22")){
                    continue;
                }
                else{
                    if (i == 0 || i == 15 || j == 0 || j == 15) {
                        buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                        if (i == 0 && j == 1) {
                            buttons[i][j].setIcon(new StartSlideSquare(Color.RED).getImage());
                        }
                        else if(i == 0 && j == 2){
                            buttons[i][j].setIcon(new InternalSlideSquare(Color.RED).getImage());
                        }
                        else if(i == 0 && j == 3){
                            buttons[i][j].setIcon(new InternalSlideSquare(Color.RED).getImage());
                        }
                        else if(i == 0 && j == 4){
                            buttons[i][j].setIcon(new EndSlideSquare(Color.RED).getImage());
                        }
                        else if(i == 0 && j == 9){
                            buttons[i][j].setIcon(new StartSlideSquare(Color.RED).getImage());
                        }
                        else if(i == 0 && j == 10){
                            buttons[i][j].setIcon(new InternalSlideSquare(Color.RED).getImage());
                        }
                        else if(i == 0 && j == 11){
                            buttons[i][j].setIcon(new InternalSlideSquare(Color.RED).getImage());
                        }
                        else if(i == 0 && j == 12){
                            buttons[i][j].setIcon(new InternalSlideSquare(Color.RED).getImage());
                        }
                        else if(i == 0 && j == 13){
                            buttons[i][j].setIcon(new EndSlideSquare(Color.RED).getImage());
                        }
                        else if(i == 1 && j == 15){
                            buttons[i][j].setIcon(new StartSlideSquare(Color.BLUE).getImage());
                        }
                        else if(i == 2 && j == 15){
                            buttons[i][j].setIcon(new InternalSlideSquare(Color.BLUE).getImage());
                        }
                        else if(i == 3 && j == 15){
                            buttons[i][j].setIcon(new InternalSlideSquare(Color.BLUE).getImage());
                        }
                        else if(i == 4 && j == 15) {
                            buttons[i][j].setIcon(new EndSlideSquare(Color.BLUE).getImage());
                        }
                        else if(i == 9 && j == 15){
                            buttons[i][j].setIcon(new StartSlideSquare(Color.BLUE).getImage());
                        }
                        else if(i == 10 && j == 15){
                            buttons[i][j].setIcon(new InternalSlideSquare(Color.BLUE).getImage());
                        }
                        else if(i == 11 && j == 15){
                            buttons[i][j].setIcon(new InternalSlideSquare(Color.BLUE).getImage());
                        }
                        else if(i == 12 && j == 15){
                            buttons[i][j].setIcon(new InternalSlideSquare(Color.BLUE).getImage());
                        }
                        else if(i == 13 && j == 15) {
                            buttons[i][j].setIcon(new EndSlideSquare(Color.BLUE).getImage());
                        }
                        else if(i == 15 && j == 14){
                            buttons[i][j].setIcon(new StartSlideSquare(Color.YELLOW).getImage());
                        }
                        else if(i == 15 && j == 13){
                            buttons[i][j].setIcon(new InternalSlideSquare(Color.YELLOW).getImage());
                        }
                        else if(i == 15 && j == 12){
                            buttons[i][j].setIcon(new InternalSlideSquare(Color.YELLOW).getImage());
                        }
                        else if(i == 15 && j == 11){
                            buttons[i][j].setIcon(new EndSlideSquare(Color.YELLOW).getImage());
                        }
                        else if(i == 15 && j == 6){
                            buttons[i][j].setIcon(new StartSlideSquare(Color.YELLOW).getImage());
                        }
                        else if(i == 15 && j == 5){
                            buttons[i][j].setIcon(new InternalSlideSquare(Color.YELLOW).getImage());
                        }
                        else if(i == 15 && j == 4){
                            buttons[i][j].setIcon(new InternalSlideSquare(Color.YELLOW).getImage());
                        }
                        else if(i == 15 && j == 3){
                            buttons[i][j].setIcon(new InternalSlideSquare(Color.YELLOW).getImage());
                        }
                        else if(i == 15 && j == 2){
                            buttons[i][j].setIcon(new EndSlideSquare(Color.YELLOW).getImage());
                        }
                        else if(i == 14 && j == 0){
                            buttons[i][j].setIcon(new StartSlideSquare(Color.GREEN).getImage());
                        }
                        else if(i == 13 && j == 0){
                            buttons[i][j].setIcon(new InternalSlideSquare(Color.GREEN).getImage());
                        }
                        else if(i == 12 && j == 0){
                            buttons[i][j].setIcon(new InternalSlideSquare(Color.GREEN).getImage());
                        }
                        else if(i == 11 && j == 0){
                            buttons[i][j].setIcon(new EndSlideSquare(Color.GREEN).getImage());
                        }
                        else if(i == 6 && j == 0){
                            buttons[i][j].setIcon(new StartSlideSquare(Color.GREEN).getImage());
                        }
                        else if(i == 5 && j == 0){
                            buttons[i][j].setIcon(new InternalSlideSquare(Color.GREEN).getImage());
                        }
                        else if(i == 4 && j == 0){
                            buttons[i][j].setIcon(new InternalSlideSquare(Color.GREEN).getImage());
                        }
                        else if(i == 3 && j == 0){
                            buttons[i][j].setIcon(new InternalSlideSquare(Color.GREEN).getImage());
                        }
                        else if(i == 2 && j == 0){
                            buttons[i][j].setIcon(new EndSlideSquare(Color.GREEN).getImage());
                        }
                        else {
                            buttons[i][j].setIcon(null);
                            buttons[i][j].setBackground(Color.WHITE);
                        }
                    }
                }
            }
        }
        startRedPawnButton1.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/images/pawns/redPawn1.png")).getScaledInstance(50, 40, Image.SCALE_SMOOTH)));
        startRedPawnButton2.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/images/pawns/redPawn2.png")).getScaledInstance(50, 40, Image.SCALE_SMOOTH)));
        startYellowPawnButton1.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/images/pawns/yellowPawn1.png")).getScaledInstance(50, 40, Image.SCALE_SMOOTH)));
        startYellowPawnButton2.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/images/pawns/yellowPawn2.png")).getScaledInstance(50, 40, Image.SCALE_SMOOTH)));
    }

    /**
     * <b>Transformer</b>: makes the buttons that are in the start positions visible
     * <b>Postcondition</b>: makes the buttons that are in the start positions visible
     */
    public void returnToStart(Pawn pawn){
        if(pawn == game.getPawn11()){
            startRedPawnButton1.setVisible(true);
        }
        else if(pawn == game.getPawn12()){
            startRedPawnButton2.setVisible(true);
        }
        else if(pawn == game.getPawn21()){
            startYellowPawnButton1.setVisible(true);
        }
        else if(pawn == game.getPawn22()){
            startYellowPawnButton2.setVisible(true);
        }
    }

    /**
     * <b>Transformer</b>: represent the movement above the slider
     * <b>Postcondition</b>: moves the pawn up the slider and eliminates existing pawns in between
     */
    public void moveOnSlider() {
        if(!buttons[1][15].getName().equals("Square")){
            for(int i=2; i<=4; i++){
                if(!buttons[i][15].getName().equals("Square")){
                    game.getPawnOnIndex(i, 15).setPosition(Position.Start);
                    returnToStart(game.getPawnOnIndex(i, 15));
                    buttons[i][15].setIcon(null);
                    game.moveToStart_onBoard(game.getPawnOnIndex(i, 15));
                    buttons[i][15].setName("Square");
                }
            }
            buttons[4][15].setName(game.getPawnOnIndex(1, 15).getName());
            buttons[1][15].setName("Square");
            game.MovePawn(game.getPawnOnIndex(1, 15), 4, 15);
            buttons[4][15].setIcon(buttons[1][15].getIcon());
            buttons[1][15].setIcon(null);
        }

        if(!buttons[9][15].getName().equals("Square")){
            for(int i=10; i<=13; i++){
                if(!buttons[i][15].getName().equals("Square")){
                    game.getPawnOnIndex(i, 15).setPosition(Position.Start);
                    returnToStart(game.getPawnOnIndex(i, 15));
                    buttons[i][15].setIcon(null);
                    game.moveToStart_onBoard(game.getPawnOnIndex(i, 15));
                    buttons[i][15].setName("Square");
                }
            }
            buttons[13][15].setName(game.getPawnOnIndex(9, 15).getName());
            buttons[9][15].setName("Square");
            game.MovePawn(game.getPawnOnIndex(9, 15), 13, 15);
            buttons[13][15].setIcon(buttons[9][15].getIcon());
            buttons[9][15].setIcon(null);
        }

        if((!buttons[15][14].getName().equals("Square") && !buttons[15][14].getName().equals("pawn21") && !buttons[15][14].getName().equals("pawn22"))){
            for(int j=13; j>=11; j--){
                if(!buttons[15][j].getName().equals("Square")){
                    game.getPawnOnIndex(15, j).setPosition(Position.Start);
                    returnToStart(game.getPawnOnIndex(15, j));
                    buttons[15][j].setIcon(null);
                    game.moveToStart_onBoard(game.getPawnOnIndex(15, j));
                    buttons[15][j].setName("Square");
                }
            }
            buttons[15][11].setName(game.getPawnOnIndex(15, 14).getName());
            buttons[15][14].setName("Square");
            game.MovePawn(game.getPawnOnIndex(15, 14), 15, 11);
            buttons[15][11].setIcon(buttons[15][11].getIcon());
            buttons[15][14].setIcon(null);
        }

        if((!buttons[15][6].getName().equals("Square") && !buttons[15][6].getName().equals("pawn21") && !buttons[15][6].getName().equals("pawn22"))){
            for(int j=5; j>=2; j--){
                if(!buttons[15][j].getName().equals("Square")){
                    game.getPawnOnIndex(15, j).setPosition(Position.Start);
                    returnToStart(game.getPawnOnIndex(15, j));
                    buttons[15][j].setIcon(null);
                    game.moveToStart_onBoard(game.getPawnOnIndex(15, j));
                    buttons[15][j].setName("Square");
                }
            }
            buttons[15][2].setName(game.getPawnOnIndex(15, 6).getName());
            buttons[15][6].setName("Square");
            game.MovePawn(game.getPawnOnIndex(15, 6), 15, 2);
            buttons[15][2].setIcon(buttons[15][6].getIcon());
            buttons[15][6].setIcon(null);
        }

        if((!buttons[14][0].getName().equals("Square"))){
            for(int i=13; i>=11; i--){
                if(!buttons[i][0].getName().equals("Square")){
                    game.getPawnOnIndex(i, 0).setPosition(Position.Start);
                    returnToStart(game.getPawnOnIndex(i, 0));
                    buttons[i][0].setIcon(null);
                    game.moveToStart_onBoard(game.getPawnOnIndex(i, 0));
                    buttons[i][0].setName("Square");
                }
            }
            buttons[11][0].setName(game.getPawnOnIndex(14, 0).getName());
            buttons[14][0].setName("Square");
            game.MovePawn(game.getPawnOnIndex(14, 0), 11, 0);
            buttons[11][0].setIcon(buttons[14][0].getIcon());
            buttons[14][0].setIcon(null);
        }

        if((!buttons[6][0].getName().equals("Square"))){
            for(int i=5; i>=2; i--){
                if(!buttons[i][0].getName().equals("Square")){
                    game.getPawnOnIndex(i, 0).setPosition(Position.Start);
                    returnToStart(game.getPawnOnIndex(i, 0));
                    buttons[i][0].setIcon(null);
                    game.moveToStart_onBoard(game.getPawnOnIndex(i, 0));
                    buttons[i][0].setName("Square");
                }
            }
            buttons[2][0].setName(game.getPawnOnIndex(6, 0).getName());
            buttons[6][0].setName("Square");
            game.MovePawn(game.getPawnOnIndex(6, 0), 2, 0);
            buttons[2][0].setIcon(buttons[6][0].getIcon());
            buttons[6][0].setIcon(null);
        }

        if((!buttons[0][1].getName().equals("Square")) && !buttons[0][1].getName().equals("pawn11") && !buttons[0][1].getName().equals("pawn12")){
            for(int j=2; j<=4; j++){
                if(!buttons[0][j].getName().equals("Square")){
                    game.getPawnOnIndex(0, j).setPosition(Position.Start);
                    returnToStart(game.getPawnOnIndex(0, j));
                    buttons[0][j].setIcon(null);
                    game.moveToStart_onBoard(game.getPawnOnIndex(0, j));
                    buttons[0][j].setName("Square");
                }
            }
            buttons[0][4].setName(game.getPawnOnIndex(0, 1).getName());
            buttons[0][1].setName("Square");
            game.MovePawn(game.getPawnOnIndex(0, 1), 0, 4);
            buttons[0][4].setIcon(buttons[0][1].getIcon());
            buttons[0][1].setIcon(null);
        }

        if((!buttons[0][9].getName().equals("Square")) && !buttons[0][9].getName().equals("pawn11") && !buttons[0][9].getName().equals("pawn12")){
            for(int j=10; j<=13; j++){
                if(!buttons[0][j].getName().equals("Square")){
                    game.getPawnOnIndex(0, j).setPosition(Position.Start);
                    returnToStart(game.getPawnOnIndex(0, j));
                    buttons[0][j].setIcon(null);
                    game.moveToStart_onBoard(game.getPawnOnIndex(0, j));
                    buttons[0][j].setName("Square");
                }
            }
            buttons[0][13].setName(game.getPawnOnIndex(0, 9).getName());
            buttons[0][9].setName("Square");
            game.MovePawn(game.getPawnOnIndex(0, 9), 0, 13);
            buttons[0][13].setIcon(buttons[0][9].getIcon());
            buttons[0][9].setIcon(null);
        }
    }

    /* Class that performs some actions when a button is pressed */
    private class PawnListener implements MouseListener {
        /**
         * <b>Transformer</b>: this function represents the movement of the pawns over the view buttons,
         * the exchange of the card, the calculation of the moves and in general most of the functions of the game
         *
         * <b>Postcondition</b>: implementation of the player's movement over the view buttons
         */

        @Override
        public void mouseClicked(MouseEvent e) {
            JButton but = ((JButton) e.getSource());
                if ((iconSelected && !but.equals(selectedButton) && (but.equals(buttons[i][j]) || game.getLastCardValue().equals("7") || game.getLastCardValue().equals("10") || game.getLastCardValue().equals("11"))) || (iconSelected && game.getLastCardValue().equals("Sorry"))) {
                    if(game.getLastCardValue().equals("Sorry") && !game.isRoundEnded()){
                        game.setRoundEnded(true);
                        if(game.getTurn() == 1){
                            if(but.getName().equals("pawn21")){
                                startYellowPawnButton1.setVisible(true);
                                game.getPawn21().setPosition(Position.Start);
                                i = game.getPawn21().getXY()[0];
                                j = game.getPawn21().getXY()[1];
                                game.MovePawn(game.getPawn21(), 14, 11);
                                tmp = (ImageIcon) buttons[i][j].getIcon();
                                if (selectedButton.equals(startRedPawnButton1)) {
                                    but.setIcon(game.getPawn11().getImage());
                                    but.setName("pawn11");
                                    game.setPosition(game.getPawn11(), Position.SimplePosition);
                                    game.MovePawn(game.getPawn11(), i, j);
                                }
                                else if (selectedButton.equals(startRedPawnButton2)) {
                                    but.setIcon(game.getPawn12().getImage());
                                    but.setName("pawn12");
                                    game.setPosition(game.getPawn12(), Position.SimplePosition);
                                    game.MovePawn(game.getPawn12(), i, j);
                                }
                            }
                            else if(but.getName().equals("pawn22")){
                                startYellowPawnButton2.setVisible(true);
                                game.getPawn22().setPosition(Position.Start);
                                i = game.getPawn22().getXY()[0];
                                j = game.getPawn22().getXY()[1];
                                game.MovePawn(game.getPawn22(), 15, 11);
                                tmp = (ImageIcon) buttons[i][j].getIcon();
                                if (selectedButton.equals(startRedPawnButton1)) {
                                    but.setIcon(game.getPawn11().getImage());
                                    but.setName("pawn11");
                                    game.setPosition(game.getPawn11(), Position.SimplePosition);
                                    game.MovePawn(game.getPawn11(), i, j);
                                }
                                else if (selectedButton.equals(startRedPawnButton2)) {
                                    but.setIcon(game.getPawn12().getImage());
                                    but.setName("pawn12");
                                    game.setPosition(game.getPawn12(), Position.SimplePosition);
                                    game.MovePawn(game.getPawn12(), i, j);
                                }
                            }
                        }
                        if(game.getTurn() == 2) {
                            if (but.getName().equals("pawn11")) {
                                startRedPawnButton1.setVisible(true);
                                game.getPawn11().setPosition(Position.Start);
                                i = game.getPawn11().getXY()[0];
                                j = game.getPawn11().getXY()[1];
                                game.MovePawn(game.getPawn11(), 6, 2);
                                tmp = (ImageIcon) buttons[i][j].getIcon();
                                if (selectedButton.equals(startYellowPawnButton1)) {
                                    but.setIcon(game.getPawn21().getImage());
                                    but.setName("pawn21");
                                    game.setPosition(game.getPawn21(), Position.SimplePosition);
                                    game.MovePawn(game.getPawn21(), i, j);
                                } else if (selectedButton.equals(startYellowPawnButton2)) {
                                    but.setIcon(game.getPawn22().getImage());
                                    but.setName("pawn22");
                                    game.setPosition(game.getPawn22(), Position.SimplePosition);
                                    game.MovePawn(game.getPawn22(), i, j);
                                }
                            } else if (but.getName().equals("pawn12")) {
                                startRedPawnButton2.setVisible(true);
                                game.getPawn12().setPosition(Position.Start);
                                i = game.getPawn12().getXY()[0];
                                j = game.getPawn12().getXY()[1];
                                game.MovePawn(game.getPawn12(), 6, 2);
                                tmp = (ImageIcon) buttons[i][j].getIcon();
                                if (selectedButton.equals(startYellowPawnButton1)) {
                                    but.setIcon(game.getPawn21().getImage());
                                    but.setName("pawn21");
                                    game.setPosition(game.getPawn21(), Position.SimplePosition);
                                    game.MovePawn(game.getPawn21(), i, j);
                                } else if (selectedButton.equals(startYellowPawnButton2)) {
                                    but.setIcon(game.getPawn22().getImage());
                                    but.setName("pawn22");
                                    game.setPosition(game.getPawn22(), Position.SimplePosition);
                                    game.MovePawn(game.getPawn22(), i, j);
                                }
                            }
                        }
                        if(i1!=-1 && j1!=-1){
                            buttons[i1][j1].setBorder(BorderFactory.createLineBorder(Color.black));
                        }
                        if(i2!=-1 && j2!=-1){
                            buttons[i2][j2].setBorder(BorderFactory.createLineBorder(Color.black));
                        }
                        game.changeTurn();
                        i1 = i2 = j1 = j2 = -1;
                        movesLeft = 7;
                        selectedButton.setVisible(false);
                    }
                    else if(game.getLastCardValue().equals("10") && !game.isRoundEnded()){
                        if(but.equals(buttons[i1][j1])){
                            i = i1;
                            j = j1;
                        }
                        else if(but.equals(buttons[i2][j2])){
                            i = i2;
                            j = j2;
                        }
                        buttons[i1][j1].setBorder(BorderFactory.createLineBorder(Color.black));
                        buttons[i2][j2].setBorder(BorderFactory.createLineBorder(Color.black));
                    }
                    if (str!=null && str.equals(game.getPawn11().getName()) && !game.getLastCardValue().equals("Sorry") && !game.isRoundEnded()) {
                        game.setRoundEnded(true);
                        if(game.getLastCardValue().equals("3") || game.getLastCardValue().equals("5")){
                            i = game.CalculateMove(game.getLastCardValue(), game.getPawn11())[0];
                            j = game.CalculateMove(game.getLastCardValue(), game.getPawn11())[1];
                            game.getPawn11().setPlayed(true);
                            if(game.getPawn11().hasPlayed() && game.getPawn12().hasPlayed()){
                                game.changeTurn();
                                game.getRandomCard();
                                game.getPawn11().setPlayed(false);
                                game.getPawn12().setPlayed(false);
                                movesLeft = 7;
                                i1 = i2 = j1 = j2 = -1;
                            }
                        }
                        else if(game.getLastCardValue().equals("7")){
                            if(game.getPawn12().hasPlayed()){
                                i = game.CalculateMove(String.valueOf(movesLeft), game.getPawn11())[0];
                                j = game.CalculateMove(String.valueOf(movesLeft), game.getPawn11())[1];
                                game.getPawn11().setPlayed(true);
                            }
                            else{
                                cardSeven_Movement(but, game.getPawn11());
                                game.getPawn11().setPlayed(true);
                            }
                            if((game.getPawn11().hasPlayed() && game.getPawn12().hasPlayed()) || movesLeft == 0){
                                game.changeTurn();
                                game.getRandomCard();
                                game.getPawn11().setPlayed(false);
                                game.getPawn12().setPlayed(false);
                                movesLeft = 7;
                                i1 = i2 = j1 = j2 = -1;
                            }
                        }
                        else if(game.getLastCardValue().equals("11")){
                            if(i1!=-1 && j1!=-1){
                                buttons[i1][j1].setBorder(BorderFactory.createLineBorder(Color.black));
                            }
                            if(i2!=-1 && j2!=-1){
                                buttons[i2][j2].setBorder(BorderFactory.createLineBorder(Color.black));
                            }
                            buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                            if((i1!=-1 && j1!=-1) && but.equals(buttons[i1][j1])){
                                i = i1;
                                j = j1;
                                if(buttons[i1][j1].getName().equals("pawn21")){
                                    game.MovePawn(game.getPawn21(), 14, 11);
                                    game.getPawn21().setPosition(Position.Start);
                                    startYellowPawnButton1.setVisible(true);
                                }
                                else if(buttons[i1][j1].getName().equals("pawn22")){
                                    game.MovePawn(game.getPawn22(), 14, 11);
                                    game.getPawn22().setPosition(Position.Start);
                                    startYellowPawnButton2.setVisible(true);
                                }
                            }
                            else if((i2!=-1 && j2!=-1) && but.equals(buttons[i2][j2])){
                                i = i2;
                                j = j2;
                                if(buttons[i2][j2].getName().equals("pawn21")){
                                    game.MovePawn(game.getPawn21(), 14, 11);
                                    game.getPawn21().setPosition(Position.Start);
                                    startYellowPawnButton1.setVisible(true);
                                }
                                else if(buttons[i2][j2].getName().equals("pawn22")){
                                    game.MovePawn(game.getPawn22(), 14, 11);
                                    game.getPawn22().setPosition(Position.Start);
                                    startYellowPawnButton2.setVisible(true);
                                }
                            }
                            else{
                                i = game.CalculateMove(game.getLastCardValue(), game.getPawn11())[0];
                                j = game.CalculateMove(game.getLastCardValue(), game.getPawn11())[1];
                            }
                            buttons[i][j].setName(str);
                            game.MovePawn(game.getPawn11(), i, j);
                            game.changeTurn();
                            game.getRandomCard();
                            i1 = i2 = j1 = j2 = -1;
                        }
                        else{
                            if(!game.getLastCardValue().equals("2")){
                                game.changeTurn();
                            }
                            i1 = i2 = j1 = j2 = -1;
                            movesLeft = 7;
                            game.getRandomCard();
                        }
                        tmp_icon = icons[0];
                        icons[0] = but.getIcon();
                        game.MovePawn(game.getPawn11(), i, j);
                    }
                    else if (str!=null && str.equals(game.getPawn12().getName()) && !game.getLastCardValue().equals("Sorry") && !game.isRoundEnded()) {
                        game.setRoundEnded(true);
                        if(game.getLastCardValue().equals("3") || game.getLastCardValue().equals("5")){
                            i = game.CalculateMove(game.getLastCardValue(), game.getPawn12())[0];
                            j = game.CalculateMove(game.getLastCardValue(), game.getPawn12())[1];
                            game.getPawn12().setPlayed(true);
                            if(game.getPawn11().hasPlayed() && game.getPawn12().hasPlayed()){
                                game.changeTurn();
                                game.getRandomCard();
                                game.getPawn11().setPlayed(false);
                                game.getPawn12().setPlayed(false);
                                movesLeft = 7;
                                i1 = i2 = j1 = j2 = -1;
                            }
                        }
                        else if(game.getLastCardValue().equals("7")){
                            if(game.getPawn11().hasPlayed()){
                                i = game.CalculateMove(String.valueOf(movesLeft), game.getPawn12())[0];
                                j = game.CalculateMove(String.valueOf(movesLeft), game.getPawn12())[1];
                                game.getPawn12().setPlayed(true);
                            }
                            else{
                                cardSeven_Movement(but, game.getPawn12());
                                game.getPawn12().setPlayed(true);
                            }
                            if((game.getPawn11().hasPlayed() && game.getPawn12().hasPlayed()) || movesLeft == 0){
                                game.changeTurn();
                                game.getRandomCard();
                                game.getPawn11().setPlayed(false);
                                game.getPawn12().setPlayed(false);
                                movesLeft = 7;
                                i1 = i2 = j1 = j2 = -1;
                            }
                        }
                        else if(game.getLastCardValue().equals("11")){
                            if(i1!=-1 && j1!=-1){
                                buttons[i1][j1].setBorder(BorderFactory.createLineBorder(Color.black));
                            }
                            if(i2!=-1 && j2!=-1){
                                buttons[i2][j2].setBorder(BorderFactory.createLineBorder(Color.black));
                            }
                            buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                            if((i1!=-1 && j1!=-1) && but.equals(buttons[i1][j1])){
                                i = i1;
                                j = j1;
                                if(buttons[i1][j1].getName().equals("pawn21")){
                                    game.MovePawn(game.getPawn21(), 14, 11);
                                    game.getPawn21().setPosition(Position.Start);
                                    startYellowPawnButton1.setVisible(true);
                                }
                                else if(buttons[i1][j1].getName().equals("pawn22")){
                                    game.MovePawn(game.getPawn22(), 14, 11);
                                    game.getPawn22().setPosition(Position.Start);
                                    startYellowPawnButton2.setVisible(true);
                                }
                            }
                            else if((i2!=-1 && j2!=-1) && but.equals(buttons[i2][j2])){
                                i = i2;
                                j = j2;
                                if(buttons[i2][j2].getName().equals("pawn21")){
                                    game.MovePawn(game.getPawn21(), 14, 11);
                                    game.getPawn21().setPosition(Position.Start);
                                    startYellowPawnButton1.setVisible(true);
                                }
                                else if(buttons[i2][j2].getName().equals("pawn22")){
                                    game.MovePawn(game.getPawn22(), 14, 11);
                                    game.getPawn22().setPosition(Position.Start);
                                    startYellowPawnButton2.setVisible(true);
                                }
                            }
                            else{
                                i = game.CalculateMove(game.getLastCardValue(), game.getPawn12())[0];
                                j = game.CalculateMove(game.getLastCardValue(), game.getPawn12())[1];
                            }
                            game.MovePawn(game.getPawn12(), i, j);
                            game.changeTurn();
                            game.getRandomCard();
                            i1 = i2 = j1 = j2 = -1;
                        }
                        else{
                            if(!game.getLastCardValue().equals("2")){
                                game.changeTurn();
                            }
                            i1 = i2 = j1 = j2 = -1;
                            movesLeft = 7;
                            game.getRandomCard();
                        }
                        tmp_icon = icons[1];
                        icons[1] = but.getIcon();
                        game.MovePawn(game.getPawn12(), i, j);
                    }
                    else if (str!=null && str.equals(game.getPawn21().getName()) && !game.getLastCardValue().equals("Sorry") && !game.isRoundEnded()) {
                        game.setRoundEnded(true);
                        if(game.getLastCardValue().equals("3") || game.getLastCardValue().equals("5")){
                            i = game.CalculateMove(game.getLastCardValue(), game.getPawn21())[0];
                            j = game.CalculateMove(game.getLastCardValue(), game.getPawn21())[1];
                            game.getPawn21().setPlayed(true);
                            if(game.getPawn21().hasPlayed() && game.getPawn22().hasPlayed()){
                                game.changeTurn();
                                game.getRandomCard();
                                game.getPawn22().setPlayed(false);
                                game.getPawn21().setPlayed(false);
                                movesLeft = 7;
                                i1 = i2 = j1 = j2 = -1;
                            }
                        }
                        else if(game.getLastCardValue().equals("7")){
                            if(game.getPawn22().hasPlayed()){
                                i = game.CalculateMove(String.valueOf(movesLeft), game.getPawn21())[0];
                                j = game.CalculateMove(String.valueOf(movesLeft), game.getPawn21())[1];
                                game.getPawn21().setPlayed(true);
                            }
                            else{
                                cardSeven_Movement(but, game.getPawn21());
                                game.getPawn21().setPlayed(true);
                            }
                            if((game.getPawn21().hasPlayed() && game.getPawn22().hasPlayed()) || movesLeft == 0){
                                game.changeTurn();
                                game.getRandomCard();
                                game.getPawn21().setPlayed(false);
                                game.getPawn22().setPlayed(false);
                                movesLeft = 7;
                                i1 = i2 = j1 = j2 = -1;
                            }
                        }
                        else if(game.getLastCardValue().equals("11")){
                            if(i1!=-1 && j1!=-1){
                                buttons[i1][j1].setBorder(BorderFactory.createLineBorder(Color.black));
                            }
                            if(i2!=-1 && j2!=-1){
                                buttons[i2][j2].setBorder(BorderFactory.createLineBorder(Color.black));
                            }
                            buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                            if((i1!=-1 && j1!=-1) && but.equals(buttons[i1][j1])){
                                i = i1;
                                j = j1;
                                if(buttons[i1][j1].getName().equals("pawn11")){
                                    game.MovePawn(game.getPawn11(), 1, 4);
                                    game.getPawn11().setPosition(Position.Start);
                                    startRedPawnButton1.setVisible(true);
                                }
                                else if(buttons[i1][j1].getName().equals("pawn12")){
                                    game.MovePawn(game.getPawn12(), 1, 4);
                                    game.getPawn12().setPosition(Position.Start);
                                    startRedPawnButton2.setVisible(true);
                                }
                            }
                            else if((i2!=-1 && j2!=-1) && but.equals(buttons[i2][j2])){
                                i = i2;
                                j = j2;
                                if(buttons[i2][j2].getName().equals("pawn11")){
                                    game.MovePawn(game.getPawn11(), 1, 4);
                                    game.getPawn11().setPosition(Position.Start);
                                    startRedPawnButton1.setVisible(true);
                                }
                                else if(buttons[i2][j2].getName().equals("pawn12")){
                                    game.MovePawn(game.getPawn12(), 1, 4);
                                    game.getPawn12().setPosition(Position.Start);
                                    startRedPawnButton2.setVisible(true);
                                }
                            }
                            else{
                                i = game.CalculateMove(game.getLastCardValue(), game.getPawn21())[0];
                                j = game.CalculateMove(game.getLastCardValue(), game.getPawn21())[1];
                            }
                            game.MovePawn(game.getPawn21(), i, j);
                            game.changeTurn();
                            game.getRandomCard();
                            i1 = i2 = j1 = j2 = -1;
                        }
                        else{
                            if(!game.getLastCardValue().equals("2")){
                                game.changeTurn();
                            }
                            i1 = i2 = j1 = j2 = -1;
                            movesLeft = 7;
                            game.getRandomCard();
                        }
                        tmp_icon = icons[2];
                        icons[2] = but.getIcon();
                        game.MovePawn(game.getPawn21(), i, j);
                    }
                    else if (str!=null && str.equals(game.getPawn22().getName()) && !game.getLastCardValue().equals("Sorry") && !game.isRoundEnded()) {
                        game.setRoundEnded(true);
                        if(game.getLastCardValue().equals("3") || game.getLastCardValue().equals("5")){
                            i = game.CalculateMove(game.getLastCardValue(), game.getPawn22())[0];
                            j = game.CalculateMove(game.getLastCardValue(), game.getPawn22())[1];
                            game.getPawn22().setPlayed(true);
                            if(game.getPawn21().hasPlayed() && game.getPawn22().hasPlayed()){
                                game.changeTurn();
                                game.getRandomCard();
                                game.getPawn21().setPlayed(false);
                                game.getPawn22().setPlayed(false);
                                movesLeft = 7;
                                i1 = i2 = j1 = j2 = -1;
                            }
                        }
                        else if(game.getLastCardValue().equals("7")){
                            if(game.getPawn21().hasPlayed()){
                                i = game.CalculateMove(String.valueOf(movesLeft), game.getPawn22())[0];
                                j = game.CalculateMove(String.valueOf(movesLeft), game.getPawn22())[1];
                                game.getPawn22().setPlayed(true);
                            }
                            else{
                                cardSeven_Movement(but, game.getPawn22());
                                game.getPawn22().setPlayed(true);
                            }
                            if((game.getPawn21().hasPlayed() && game.getPawn22().hasPlayed()) || movesLeft == 0){
                                game.changeTurn();
                                game.getRandomCard();
                                game.getPawn21().setPlayed(false);
                                game.getPawn22().setPlayed(false);
                                movesLeft = 7;
                                i1 = i2 = j1 = j2 = -1;
                            }
                        }
                        else if(game.getLastCardValue().equals("11")){
                            if(i1!=-1 && j1!=-1){
                                buttons[i1][j1].setBorder(BorderFactory.createLineBorder(Color.black));
                            }
                            if(i2!=-1 && j2!=-1){
                                buttons[i2][j2].setBorder(BorderFactory.createLineBorder(Color.black));
                            }
                            buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                            if((i1!=-1 && j1!=-1) && but.equals(buttons[i1][j1])){
                                i = i1;
                                j = j1;
                                if(buttons[i1][j1].getName().equals("pawn11")){
                                    game.MovePawn(game.getPawn11(), 1, 4);
                                    game.getPawn11().setPosition(Position.Start);
                                    startRedPawnButton1.setVisible(true);
                                }
                                else if(buttons[i1][j1].getName().equals("pawn12")){
                                    game.MovePawn(game.getPawn12(), 1, 4);
                                    game.getPawn12().setPosition(Position.Start);
                                    startRedPawnButton2.setVisible(true);
                                }
                            }
                            else if((i2!=-1 && j2!=-1) && but.equals(buttons[i2][j2])){
                                i = i2;
                                j = j2;
                                if(buttons[i2][j2].getName().equals("pawn11")){
                                    game.MovePawn(game.getPawn11(), 1, 4);
                                    game.getPawn11().setPosition(Position.Start);
                                    startRedPawnButton1.setVisible(true);
                                }
                                else if(buttons[i2][j2].getName().equals("pawn12")){
                                    i = i2;
                                    j = j2;
                                    game.MovePawn(game.getPawn12(), 1, 4);
                                    game.getPawn12().setPosition(Position.Start);
                                    startRedPawnButton2.setVisible(true);
                                }
                                else{
                                    i = game.CalculateMove(game.getLastCardValue(), game.getPawn22())[0];
                                    j = game.CalculateMove(game.getLastCardValue(), game.getPawn22())[1];
                                }
                                game.MovePawn(game.getPawn22(), i, j);
                            }
                            game.changeTurn();
                            game.getRandomCard();
                            i1 = i2 = j1 = j2 = -1;
                        }
                        else{
                            if(!game.getLastCardValue().equals("2")){
                                game.changeTurn();
                            }
                            i1 = i2 = j1 = j2 = -1;
                            movesLeft = 7;
                            game.getRandomCard();
                        }
                        tmp_icon = icons[3];
                        icons[3] = but.getIcon();
                        game.MovePawn(game.getPawn22(), i, j);
                    }
                    but.setIcon(selectedButton.getIcon());
                    if(!(game.getLastCardValue().equals("Sorry") && (selectedButton.equals(startRedPawnButton1) || selectedButton.equals(startRedPawnButton2) || selectedButton.equals(startYellowPawnButton1) || selectedButton.equals(startYellowPawnButton2)))) {
                        if (selectedButton.getName().equals("pawn11") || selectedButton.getName().equals("pawn12")) {
                            if (but.getName().equals("pawn21")) {
                                game.getPawn21().setPosition(Position.Start);
                                if(str.equals("pawn11")){
                                    game.MovePawn(game.getPawn11(), game.getPawn21().getXY()[0], game.getPawn21().getXY()[1]);
                                }
                                else if(str.equals("pawn12")){
                                    game.MovePawn(game.getPawn12(), game.getPawn21().getXY()[0], game.getPawn21().getXY()[1]);
                                }
                                game.MovePawn(game.getPawn21(), 14, 11);
                                startYellowPawnButton1.setVisible(true);
                            } else if (but.getName().equals("pawn22")) {
                                game.getPawn22().setPosition(Position.Start);
                                if(str.equals("pawn11")){
                                    game.MovePawn(game.getPawn11(), game.getPawn22().getXY()[0], game.getPawn22().getXY()[1]);
                                }
                                else if(str.equals("pawn12")){
                                    game.MovePawn(game.getPawn12(), game.getPawn22().getXY()[0], game.getPawn22().getXY()[1]);
                                }
                                game.MovePawn(game.getPawn22(), 14, 11);
                                startYellowPawnButton2.setVisible(true);
                            }
                            selectedButton.setName("Square");
                        }
                        else if(selectedButton.getName().equals("pawn21") || selectedButton.getName().equals("pawn22")) {
                            if (but.getName().equals("pawn11")) {
                                game.getPawn11().setPosition(Position.Start);
                                if(str.equals("pawn21")){
                                    game.MovePawn(game.getPawn21(), game.getPawn11().getXY()[0], game.getPawn11().getXY()[1]);
                                }
                                else if(str.equals("pawn22")){
                                    game.MovePawn(game.getPawn22(), game.getPawn11().getXY()[0], game.getPawn11().getXY()[1]);
                                }
                                game.MovePawn(game.getPawn11(), 6, 2);
                                startRedPawnButton1.setVisible(true);
                            } else if (but.getName().equals("pawn12")) {
                                game.getPawn12().setPosition(Position.Start);
                                if(str.equals("pawn21")){
                                    game.MovePawn(game.getPawn21(), game.getPawn12().getXY()[0], game.getPawn12().getXY()[1]);
                                }
                                else if(str.equals("pawn22")){
                                    game.MovePawn(game.getPawn22(), game.getPawn12().getXY()[0], game.getPawn12().getXY()[1]);
                                }
                                game.MovePawn(game.getPawn12(), 6, 2);
                                startRedPawnButton2.setVisible(true);
                            }
                            else{
                                selectedButton.setName("Square");
                            }
                        }
                        but.setName(str);
                    }
                    selectedButton.setIcon(tmp_icon);
                    selectedButton.setBorder(BorderFactory.createLineBorder(Color.black));
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                    iconSelected = false;
                    if(game.getLastCardValue().equals("Sorry") && (selectedButton.equals(startRedPawnButton1) || selectedButton.equals(startRedPawnButton2) || selectedButton.equals(startYellowPawnButton1) || selectedButton.equals(startYellowPawnButton2))){
                        game.getRandomCard();
                    }

                    moveOnSlider();

                    try {
                        currCardImage = game.LastCardImage().getImage().getScaledInstance(135, 185, Image.SCALE_SMOOTH);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    try {
                        images_fix();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    game.setRoundEnded(false);

                    if(game.gameFinished()){
                        winner = game.returnWinner();
                        turn_label.setText("Winner is " + winner.getName());
                        cards_label.setText(" ");
                    }
                    else{
                        turn_label.setText("Turn: "+turn_function(game.getTurn()));
                        cards_label.setText("Cards Left: "+game.numOfCards());
                    }
                    Graphics g1 = gamePanel.getGraphics();
                    gamePanel.paintComponent(g1);
                    gamePanel.repaint();
                }
                else if (!iconSelected && !but.getName().equals("Square") && !game.gameFinished()){
                    if(game.getTurn() == 1 && (but.getName().equals(game.getPawn11().getName()) || but.getName().equals(game.getPawn12().getName()))){
                        iconSelected = true;
                        selectedButton = but;
                        str = but.getName();
                        if(game.getLastCardValue().equals("3") || game.getLastCardValue().equals("5")){
                            if(game.getPawn11().getPosition() != Position.Start && game.getPawn11().getPosition() != Position.Home && !game.getPawn11().hasPlayed()){
                                i1 = game.CalculateMove(game.getLastCardValue(), game.getPawn11())[0];
                                j1 = game.CalculateMove(game.getLastCardValue(), game.getPawn11())[1];
                            }
                            else{
                                i1 = -1;
                                j1 = -1;
                                game.getPawn11().setPlayed(true);
                            }

                            if(game.getPawn12().getPosition() != Position.Start && game.getPawn12().getPosition() != Position.Home && !game.getPawn12().hasPlayed()) {
                                i2 = game.CalculateMove(game.getLastCardValue(), game.getPawn12())[0];
                                j2 = game.CalculateMove(game.getLastCardValue(), game.getPawn12())[1];
                            }
                            else{
                                i2 = -1;
                                j2 = -1;
                                game.getPawn12().setPlayed(true);
                            }
                            if(i1!=-1 && j1!=-1 && but.getName().equals("pawn11")){
                                buttons[i1][j1].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                            }
                            else if(i2!=-1 && j2!=-1 && but.getName().equals("pawn12")){
                                buttons[i2][j2].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                            }
                            if(i1!=-1 && j1!=-1 && str.equals("pawn11")) {
                                i = i1;
                                j = j1;
                                game.getPawn11().setPlayed(true);
                            }
                            else if(i2!=-1 && j2!=-1 && str.equals("pawn12")){
                                i = i2;
                                j = j2;
                                game.getPawn12().setPlayed(true);
                            }
                        }
                        else if(game.getLastCardValue().equals("7")){
                            if(game.getPawn11().getPosition() == Position.Start || game.getPawn11().getPosition() == Position.Home){
                                game.getPawn11().setPlayed(true);
                            }

                            if(game.getPawn12().getPosition() == Position.Start || game.getPawn12().getPosition() == Position.Home){
                                game.getPawn12().setPlayed(true);
                            }

                            if(but.getName().equals("pawn11")){
                                if(game.getPawn12().hasPlayed()){
                                    i = game.CalculateMove(String.valueOf(movesLeft), game.getPawn11())[0];
                                    j = game.CalculateMove(String.valueOf(movesLeft), game.getPawn11())[1];
                                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                                    game.getPawn11().setPlayed(true);
                                }
                                else{
                                    i1 = Integer.parseInt(game.getLastCardValue());
                                    while(i1!=0) {
                                        i = game.CalculateMove(String.valueOf(i1), game.getPawn11())[0];
                                        j = game.CalculateMove(String.valueOf(i1), game.getPawn11())[1];
                                        i1 = i1-1;
                                        buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                                    }
                                    game.getPawn11().setPlayed(true);
                                }
                            }
                            else if(but.getName().equals("pawn12")){
                                if(game.getPawn11().hasPlayed()){
                                    i = game.CalculateMove(String.valueOf(movesLeft), game.getPawn12())[0];
                                    j = game.CalculateMove(String.valueOf(movesLeft), game.getPawn12())[1];
                                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                                    game.getPawn12().setPlayed(true);
                                }
                                else{
                                    i1 = Integer.parseInt(game.getLastCardValue());
                                    while(i1!=0) {
                                        i = game.CalculateMove(String.valueOf(i1), game.getPawn12())[0];
                                        j = game.CalculateMove(String.valueOf(i1), game.getPawn12())[1];
                                        i1 = i1-1;
                                        buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                                    }
                                    game.getPawn12().setPlayed(true);
                                }
                            }
                        }
                        else if(game.getLastCardValue().equals("10")){
                            if(str.equals("pawn11")){
                                i1 = game.CalculateMove(game.getLastCardValue(), game.getPawn11())[0];
                                j1 = game.CalculateMove(game.getLastCardValue(), game.getPawn11())[1];
                                buttons[i1][j1].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));

                                i2 = game.CalculateMove("1", game.getPawn11())[0];
                                j2 = game.CalculateMove("1", game.getPawn11())[1];
                                buttons[i2][j2].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                            }

                            else if(str.equals("pawn12")){
                                i1 = game.CalculateMove(game.getLastCardValue(), game.getPawn12())[0];
                                j1 = game.CalculateMove(game.getLastCardValue(), game.getPawn12())[1];
                                buttons[i1][j1].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));

                                i2 = game.CalculateMove("1", game.getPawn12())[0];
                                j2 = game.CalculateMove("1", game.getPawn12())[1];
                                buttons[i2][j2].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                            }
                        }
                        else if(game.getLastCardValue().equals("11")){
                            int[][] board = game.getBoard();
                            for(int i=0; i<16; i++){
                                for(int j=0; j<16; j++){
                                    if((board[i][j] == 21 && game.getPawn21().getPosition()!=Position.Start && game.getPawn21().getPosition()!=Position.Home) ||
                                            (board[i][j] == 22 && game.getPawn22().getPosition()!=Position.Start && game.getPawn22().getPosition()!=Position.Home)){
                                        if(i1!=-1 && j1!=-1){
                                            i2 = i;
                                            j2 = j;
                                        }
                                        else{
                                            i1 = i;
                                            j1 = j;
                                        }
                                    }
                                }
                            }
                            if(i1!=-1 && j1!=-1){
                                buttons[i1][j1].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                            }
                            if(i2!=-1 && j2!=-1){
                                buttons[i2][j2].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                            }
                            if (but.getName().equals(game.getPawn11().getName())) {
                                i = game.CalculateMove(game.getLastCardValue(), game.getPawn11())[0];
                                j = game.CalculateMove(game.getLastCardValue(), game.getPawn11())[1];
                            } else if (but.getName().equals(game.getPawn12().getName())) {
                                i = game.CalculateMove(game.getLastCardValue(), game.getPawn12())[0];
                                j = game.CalculateMove(game.getLastCardValue(), game.getPawn12())[1];
                            }
                            buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                        }
                        else {
                            if (but.getName().equals(game.getPawn11().getName())) {
                                i = game.CalculateMove(game.getLastCardValue(), game.getPawn11())[0];
                                j = game.CalculateMove(game.getLastCardValue(), game.getPawn11())[1];
                            } else if (but.getName().equals(game.getPawn12().getName())) {
                                i = game.CalculateMove(game.getLastCardValue(), game.getPawn12())[0];
                                j = game.CalculateMove(game.getLastCardValue(), game.getPawn12())[1];
                            }
                            buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                        }
                        but.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                    }
                    else if(game.getTurn() == 2 && (but.getName().equals(game.getPawn21().getName()) || but.getName().equals(game.getPawn22().getName()))){
                        iconSelected = true;
                        selectedButton = but;
                        str = but.getName();
                        if(game.getLastCardValue().equals("3") || game.getLastCardValue().equals("5")){
                            if(game.getPawn21().getPosition() != Position.Start && game.getPawn21().getPosition() != Position.Home && !game.getPawn21().hasPlayed()){
                                i1 = game.CalculateMove(game.getLastCardValue(), game.getPawn21())[0];
                                j1 = game.CalculateMove(game.getLastCardValue(), game.getPawn21())[1];
                            }
                            else{
                                i1 = -1;
                                j1 = -1;
                                game.getPawn21().setPlayed(true);
                            }

                            if(game.getPawn22().getPosition() != Position.Start && game.getPawn22().getPosition() != Position.Home && !game.getPawn22().hasPlayed()) {
                                i2 = game.CalculateMove(game.getLastCardValue(), game.getPawn22())[0];
                                j2 = game.CalculateMove(game.getLastCardValue(), game.getPawn22())[1];
                            }
                            else{
                                i2 = -1;
                                j2 = -1;
                                game.getPawn22().setPlayed(true);
                            }

                            if(i1!=-1 && j1!=-1 && but.getName().equals("pawn21")){
                                buttons[i1][j1].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                            }
                            else if(i2!=-1 && j2!=-1 && but.getName().equals("pawn22")){
                                buttons[i2][j2].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                            }

                            if(i1!=-1 && j1!=-1 && str.equals("pawn21")) {
                                i = i1;
                                j = j1;
                                game.getPawn21().setPlayed(true);
                            }
                            else if(i2!=-1 && j2!=-1 && str.equals("pawn22")){
                                i = i2;
                                j = j2;
                                game.getPawn22().setPlayed(true);
                            }
                        }
                        else if(game.getLastCardValue().equals("7")){
                            if(game.getPawn21().getPosition() == Position.Start || game.getPawn21().getPosition() == Position.Home){
                                game.getPawn21().setPlayed(true);
                            }

                            if(game.getPawn22().getPosition() == Position.Start || game.getPawn22().getPosition() == Position.Home){
                                game.getPawn22().setPlayed(true);
                            }

                            if(but.getName().equals("pawn21")){
                                if(game.getPawn22().hasPlayed()){
                                    i = game.CalculateMove(String.valueOf(movesLeft), game.getPawn21())[0];
                                    j = game.CalculateMove(String.valueOf(movesLeft), game.getPawn21())[1];
                                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                                    game.getPawn21().setPlayed(true);
                                }
                                else{
                                    i1 = Integer.parseInt(game.getLastCardValue());
                                    while(i1!=0) {
                                        i = game.CalculateMove(String.valueOf(i1), game.getPawn21())[0];
                                        j = game.CalculateMove(String.valueOf(i1), game.getPawn21())[1];
                                        i1 = i1-1;
                                        buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                                        game.getPawn21().setPlayed(true);
                                    }
                                }
                            }
                            else if(but.getName().equals("pawn22")){
                                if(game.getPawn21().hasPlayed()){
                                    i = game.CalculateMove(String.valueOf(movesLeft), game.getPawn22())[0];
                                    j = game.CalculateMove(String.valueOf(movesLeft), game.getPawn22())[1];
                                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                                    game.getPawn22().setPlayed(true);
                                }
                                else{
                                    i1 = Integer.parseInt(game.getLastCardValue());
                                    while(i1!=0) {
                                        i = game.CalculateMove(String.valueOf(i1), game.getPawn22())[0];
                                        j = game.CalculateMove(String.valueOf(i1), game.getPawn22())[1];
                                        i1 = i1-1;
                                        buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                                        game.getPawn22().setPlayed(true);
                                    }
                                }
                            }
                        }
                        else if(game.getLastCardValue().equals("10")){
                            if(str.equals("pawn21")){
                                i1 = game.CalculateMove(game.getLastCardValue(), game.getPawn21())[0];
                                j1 = game.CalculateMove(game.getLastCardValue(), game.getPawn21())[1];
                                buttons[i1][j1].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));

                                i2 = game.CalculateMove("1", game.getPawn21())[0];
                                j2 = game.CalculateMove("1", game.getPawn21())[1];
                                buttons[i2][j2].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                            }

                            else if(str.equals("pawn22")){
                                i1 = game.CalculateMove(game.getLastCardValue(), game.getPawn22())[0];
                                j1 = game.CalculateMove(game.getLastCardValue(), game.getPawn22())[1];
                                buttons[i1][j1].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));

                                i2 = game.CalculateMove("1", game.getPawn22())[0];
                                j2 = game.CalculateMove("1", game.getPawn22())[1];
                                buttons[i2][j2].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                            }
                        }
                        else if(game.getLastCardValue().equals("11")){
                            int[][] board = game.getBoard();
                            for(int i=0; i<16; i++){
                                for(int j=0; j<16; j++){
                                    if((board[i][j] == 11 && game.getPawn11().getPosition()!=Position.Start && game.getPawn11().getPosition()!=Position.Home) ||
                                            (board[i][j] == 12 && game.getPawn12().getPosition()!=Position.Start && game.getPawn12().getPosition()!=Position.Home)){
                                        if(i1!=-1 && j1!=-1){
                                            i2 = i;
                                            j2 = j;
                                        }
                                        else{
                                            i1 = i;
                                            j1 = j;
                                        }
                                    }
                                }
                            }
                            if(i1!=-1 && j1!=-1){
                                buttons[i1][j1].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                            }
                            if(i2!=-1 && j2!=-1){
                                buttons[i2][j2].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                            }
                            if (but.getName().equals(game.getPawn21().getName())) {
                                i = game.CalculateMove(game.getLastCardValue(), game.getPawn21())[0];
                                j = game.CalculateMove(game.getLastCardValue(), game.getPawn21())[1];
                            } else if (but.getName().equals(game.getPawn22().getName())) {
                                i = game.CalculateMove(game.getLastCardValue(), game.getPawn22())[0];
                                j = game.CalculateMove(game.getLastCardValue(), game.getPawn22())[1];
                            }
                            buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                        }
                        else {
                            if (but.getName().equals(game.getPawn21().getName())) {
                                lastPlayedPawn = game.getPawn21();
                                i = game.CalculateMove(game.getLastCardValue(), game.getPawn21())[0];
                                j = game.CalculateMove(game.getLastCardValue(), game.getPawn21())[1];
                            } else if (but.getName().equals(game.getPawn22().getName())) {
                                lastPlayedPawn = game.getPawn22();
                                i = game.CalculateMove(game.getLastCardValue(), game.getPawn22())[0];
                                j = game.CalculateMove(game.getLastCardValue(), game.getPawn22())[1];
                            }
                            buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                        }
                        but.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                    }
                }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}