package Controller;

import Model.Card.NumberCard;
import Model.Card.SorryCard;
import Model.Deck.Deck;
import Model.Pawn.Pawn;
import Model.Pawn.Position;
import Model.Player.Player;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

/**
 * Controller controls the game and its operations
 * @author ioannis kapetanakis
 */

public class Controller {
    private Player player1;
    private Player player2;
    private Pawn pawn11;
    private Pawn pawn12;
    private Pawn pawn21;
    private Pawn pawn22;
    private String lastcardValue;
    private int Turn;
    private Deck deck;
    private boolean gameStarted;
    private int board[][];
    private boolean round_ended;
    private boolean sliderUsed;

    /**
     * <b>Constructor</b>
     * <b>postcondition</b>: Sets the game ready to play by initializing the players, their pawns and the cards
     */

    public Controller(){
        player1 = new Player("Player 1", 1, Color.RED);
        player2 = new Player("Player 2", 2, Color.YELLOW);
        pawn11 = new Pawn(player1, 1, "pawn11");
        pawn12 = new Pawn(player1, 2, "pawn12");
        pawn21 = new Pawn(player2, 1, "pawn21");
        pawn22 = new Pawn(player2, 2, "pawn22");
        player1.setPawn1(pawn11);
        player1.setPawn2(pawn12);
        player2.setPawn1(pawn21);
        player2.setPawn2(pawn22);
        this.setDeck(new Deck());
        deck.init_cards();
        Random random = new Random();
        Turn = random.nextInt(2)+1;
        lastcardValue = null;
        round_ended = false;
        sliderUsed = false;
        init_board();
    }

    /**
     * <b>Transformer</b>: Changes the turn between the players
     * <b>Postcondition</b>: If turn equals 1 then sets turn to 2 and opposite (turn=1 for player1 and turn=2 for player2)
     */
    public void changeTurn(){
        if(this.Turn == 1){
            this.Turn = 2;
        }
        else{
            this.Turn = 1;
        }
    }

    /**
     * <b>Accessor</b>: Returns the turn
     * @return the turn
     */
    public int getTurn(){
        return Turn;
    }

    /**
     * @param value
     * <b>Transformer</b>: Changes the value of the last card that is picked
     * <b>Postcondition</b>: Last card value = param value
     */
    public void setLastCardValue(String value){
        this.lastcardValue = value;
    }

    /**
     * <b>Accessor</b>: Returns the value of the last card that is picked
     * @return the turn value of the last card that is picked
     */
    public String getLastCardValue(){
        return lastcardValue;
    }

    /**
     * <b>Accessor</b>: Returns the card with index 0 from the card collection (its shuffled, so it is considered as "random")
     * @return a random card from the card collection
     */
    public String getRandomCard(){
        if(deck.deckIsEmpty()) {
            deck.refill_deck();
        }
        setLastCardValue(deck.getUnusedCards().get(0).getValue());
        deck.remove_card();
        return getLastCardValue();
    }

    /**
     * <b>Observer</b>: Checks if the fold button is pressed
     * @return true if the fold button is pressed, else false
     */
    public boolean FoldPressed(){

        return false;
    }

    /**
     * <b>Observer</b>: Checks if the player can fold
     * @return true if the player can fold, else false
     */
    public boolean CanFold(){
        Player player;
        Player other_player;

        if(getTurn() == 1){
            player = player1;
            other_player = player2;
        }
        else{
            player = player2;
            other_player = player1;
        }

        if((player.getPawn1().getPosition() == Position.Start && player.getPawn2().getPosition() == Position.Start)
        && (!getLastCardValue().equals("1") && !getLastCardValue().equals("2"))){
            return true;
        }
        else if(getLastCardValue().equals("1") || getLastCardValue().equals("2")){
            if(Turn == 1){
                if(board[0][4]!=1){
                    return true;
                }
            }
            else{
                if(board[15][11]!=1){
                    return true;
                }
            }
        }

        else if(getLastCardValue().equals("Sorry")){
            if(Turn == 1){
                if(pawn11.getPosition() != Position.Start && pawn12.getPosition() != Position.Start){
                    return true;
                }
                else if(pawn21.getPosition() != Position.SimplePosition && pawn22.getPosition() != Position.SimplePosition){
                    return true;
                }
            }
            else{
                if(pawn21.getPosition() != Position.Start && pawn22.getPosition() != Position.Start){
                    return true;
                }
                else if(pawn11.getPosition() != Position.SimplePosition && pawn12.getPosition() != Position.SimplePosition){
                    return true;
                }
            }
        }

        else if(getLastCardValue().equals("8") || getLastCardValue().equals("12")){
            return true;
        }
        return false;
    }

    /**
     * <b>Observer</b>: Checks if the game has finished
     * @return true if the game has finished, else false
     */
    public boolean gameFinished(){
        if(player1.has_finished() || player2.has_finished()){
            return true;
        }
        return false;
    }

    /**
     * @param deck
     * <b>Transformer</b>: Initializes the deck
     * <b>Postcondition</b>: Initializes the deck
     */
    public void setDeck(Deck deck){
        this.deck = deck;
    }

    /**
     * <b>Observer</b>: Initializes the board
     * <b>Postcondition:</b> initializes the board
     */
    public void init_board(){
        board = new int[16][16];
        for(int i=0; i<16; i++){
            for(int j=0; j<16; j++){
                if(i == 0 || i == 15 || j == 0 || j == 15){
                    board[i][j] = 1;
                }
                else {
                    if (i == 14 && j == 13) {
                        board[i][j] = 1;
                    } else if (i == 13 && j == 13) {
                        board[i][j] = 1;
                    } else if (i == 12 && j == 13) {
                        board[i][j] = 1;
                    } else if (i == 11 && j == 13) {
                        board[i][j] = 1;
                    } else if (i == 10 && j == 13) {
                        board[i][j] = 1;
                    } else if (i == 1 && j == 2) {
                        board[i][j] = 1;
                    } else if (i == 2 && j == 2) {
                        board[i][j] = 1;
                    } else if (i == 3 && j == 2) {
                        board[i][j] = 1;
                    } else if (i == 4 && j == 2) {
                        board[i][j] = 1;
                    } else if (i == 5 && j == 2) {
                        board[i][j] = 1;
                    } else{
                        board[i][j] = 0;
                    }
                }
            }
        }
    }

    /**
     * <b>Observer</b>: Implements the movement of the pawns
     * <b>Postcondition:</b> Implements the movement of the pawns
     */
    public void MovePawn(Pawn pawn, int x, int y){
        board[pawn.getXY()[0]][pawn.getXY()[1]] = 1;

        pawn.setX(x);
        pawn.setY(y);

        if(pawn == player1.getPawn1()){
            board[x][y] = 11;
        }

        else if(pawn == player1.getPawn2()){
            board[x][y] = 12;
        }

        else if(pawn == player2.getPawn1()){
            board[x][y] = 21;
        }

        else{
            board[x][y] = 22;
        }
    }

    /**
     * <b>Accessor</b>: Offers access to the red player's first pawn
     * @return red player's first pawn
     */
    public Pawn getPawn11(){
        return pawn11;
    }

    /**
     * <b>Accessor</b>: Offers access to the red player's second pawn
     * @return red player's second pawn
     */
    public Pawn getPawn12(){
        return pawn12;
    }

    /**
     * <b>Accessor</b>: Offers access to the yellow player's first pawn
     * @return yellow player's first pawn
     */
    public Pawn getPawn21(){
        return pawn21;
    }

    /**
     * <b>Accessor</b>: Offers access to the yellow player's second pawn
     * @return yellow player's second pawn
     */
    public Pawn getPawn22(){
        return pawn22;
    }

    /**
     * <b>Accessor</b>: Offers access to a pawn's position
     * @param pawn
     * @return a specific pawn's position type
     */
    public Position getPosition(Pawn pawn){
        return pawn.getPosition();
    }

    /**
     * <b>Transformer</b>: Offers access to a pawn's position
     * @param pawn
     * <b>Postcondition:</b> Pawn's new position type = position of parameters
     */
    public void setPosition(Pawn pawn, Position position){
        pawn.setPosition(position);
    }

    /**
     * <b>Accessor</b>: Returns the remained number of cards
     * @return the remained number of cards
     */
    public int numOfCards(){
        return deck.deckSize();
    }

    /**
     * <b>Accessor</b>: Returns the image of the last card drawn by a player
     * @return Returns the image of the last card drawn by a player
     */
    public ImageIcon LastCardImage() throws IOException {
        switch(lastcardValue){
            case "1":
                return new NumberCard("1").getImage();

            case "2":
                return new NumberCard("2").getImage();

            case "3":
                return new NumberCard("3").getImage();

            case "4":
                return new NumberCard("4").getImage();

            case "5":
                return new NumberCard("5").getImage();

            case "7":
                return new NumberCard("7").getImage();

            case "8":
                return new NumberCard("8").getImage();

            case "10":
                return new NumberCard("10").getImage();

            case "11":
                return new NumberCard("11").getImage();

            case "12":
                return new NumberCard("12").getImage();

            case "Sorry":
                return new SorryCard().getImage();
        }
        return null;
    }

    /**
     * <b>Observer</b>: Calculates the new coordinates of the pawn on the board, given by the last card drawn by a player
     * @return the calculated coordinates of the pawn on the board, given by the last card drawn by a player
     */
    public int[] CalculateMove(String value, Pawn pawn){
        int i = pawn.getXY()[0];
        int j = pawn.getXY()[1];
        int valueInt;

        switch(value){
            case "1":
                if(lastcardValue.equals("10")){
                    valueInt = -1;
                }
                else{
                    valueInt = 1;
                }
                break;

            case "2":
                valueInt = 2;
                break;

            case "3":
                valueInt = 3;
                break;

            case "4":
                if(lastcardValue.equals("7") || sliderUsed){
                    valueInt = 4;
                }
                else{
                    valueInt = -4;
                }
                break;

            case "5":
                valueInt = 5;
                break;

            case "6":
                valueInt = 6;
                break;

            case "7":
                valueInt = 7;
                break;

            case "8":
                valueInt = 8;
                break;

            case "10":
                valueInt = 10;
                break;

            case "11":
                valueInt = 11;
                break;

            case "12":
                valueInt = 12;
                break;

            default:
                valueInt = 0;
                break;
        }

        if(i == 0){
            if(Turn == 1 && j<=2){
                i = valueInt - i - 2;
                j = 2;
            }
            else {
                j = j + valueInt;
                if (j > 15) {
                    i = j - 15;
                    j = 15;
                } else if (j < 0) {
                    i = -1 * j;
                    j = 0;
                }
            }
        }

        else if(i == 15) {
            if(Turn == 2 && j>=13){
                i = j - valueInt + 2;
                j = 13;
            }
            else {
                j = j - valueInt;
                if (j < 0) {
                    i = 15 + j;
                    j = 0;
                }
                if (j > 15) {
                    i = 15 - (j - 15);
                    j = 15;
                }
            }
        }

        else if(j == 0){
            i = i - valueInt;
            if(i<0){
                if(Turn == 1 && i<-2){
                    i = -1*i - 2;
                    j = 2;
                }
                else {
                    j = -1 * i;
                    i = 0;
                }
            }
            else if(i > 15){
                j = i-15;
                i = 15;
            }
        }

        else if(j == 15){
            i = i + valueInt;
            if (i > 15) {
                if(Turn == 2 && i > 17){
                    i = i - valueInt + 4;
                    j = 13;
                }
                else {
                    j = j - (i - 15);
                    i = 15;
                }
            } else if (i < 0) {
                j = j + i;
                i = 0;
            }
        }

        else if((i==1 || i==2 || i==3 || i==4 || i==5) && j == 2){
            i = i+valueInt;
        }

        else if((i == 14 || i == 13 || i == 12 || i == 11 || i == 10) && j == 13){
            i = i - valueInt;
        }

        return new int[]{i, j};
    }

    /**
     * <b>Accessor</b>: Returns the board of the game
     * @return the board of the game that
     */
    public int[][] getBoard(){
        return board;
    }

    /**
     * <b>Observer</b>: Checks if the current round has ended
     * @return true if the current round has ended, else false
     */
    public boolean isRoundEnded(){
        return round_ended;
    }

    /**
     * <b>Transformer</b>: Determines whether a round is over or not
     * @param round_ended
     * <b>Postcondition:</b> If round is over, then set round_ended = true, else false
     */
    public void setRoundEnded(boolean round_ended) {
        this.round_ended = round_ended;
    }

    /**
     * <b>Accessor</b>: Returns the pawn of a specific index
     * @param i
     * @param j
     * @return Returns the pawn of index i,j
     */
    public Pawn getPawnOnIndex(int i, int j){
        if(board[i][j] == 11){
            return pawn11;
        }
        else if(board[i][j] == 12){
            return pawn12;
        }
        else if(board[i][j] == 21){
            return pawn21;
        }
        else{
            return pawn22;
        }
    }

    /**
     * <b>Transformer</b>: Returns a pawn back to start position on the board
     * @param pawn
     * <b>Postcondition:</b> Returns the param's pawn back to start position on the board
     */
    public void moveToStart_onBoard(Pawn pawn){
        if(pawn == pawn11 || pawn == pawn12){
            MovePawn(pawn, 1, 4);
        }

        else if(pawn == pawn21 || pawn == pawn22){
            MovePawn(pawn, 15, 11);
        }
    }

    /**
     * <b>Observer</b>: Returns the winner of the game
     * @return the winner of the game
     */
    public Player returnWinner(){
        if(player1.has_finished()){
            return player1;
        }

        else{
            return player2;
        }
    }
}