package Model.Deck;

import Model.Card.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Contains the methods signatures needed for
 * creating a deck
 * @author ioannis kapetanakis
 */

public class Deck {
    private ArrayList<Card> UnusedCards;
    private ArrayList<Card> UsedCards;
    private int totalCards;

    /**
     * <b>Constructor</b>
     * <b>postcondition</b>: Initializes the arraylists of unused cards and used cards and sets the total cards to 44
     */
    public Deck(){
        UnusedCards = new ArrayList<>();
        UsedCards = new ArrayList<>();
        totalCards = 44;
    }

    /**
     * <b>Observer</b>: Initializes the cards of the deck
     * <b>Postcondition:</b> initializes the cards of the deck
     */
    public void init_cards(){
        for(int i=0; i<4; i++){
            UnusedCards.add(new NumberCard("1"));
        }
        for(int i=0; i<4; i++){
            UnusedCards.add(new NumberCard("2"));
        }
        for(int i=0; i<4; i++){
            UnusedCards.add(new NumberCard("3"));
        }
        for(int i=0; i<4; i++){
            UnusedCards.add(new NumberCard("4"));
        }
        for(int i=0; i<4; i++){
            UnusedCards.add(new NumberCard("5"));
        }
        for(int i=0; i<4; i++){
            UnusedCards.add(new NumberCard("7"));
        }
        for(int i=0; i<4; i++){
            UnusedCards.add(new NumberCard("8"));
        }
        for(int i=0; i<4; i++){
            UnusedCards.add(new NumberCard("10"));
        }
        for(int i=0; i<4; i++){
            UnusedCards.add(new NumberCard("11"));
        }
        for(int i=0; i<4; i++){
            UnusedCards.add(new NumberCard("12"));
        }
        for(int i=0; i<4; i++){
            UnusedCards.add(new SorryCard());
        }
        Collections.shuffle(UnusedCards);
    }

    /**
     * <b>Transformer</b>: Remove a card from the deck
     * <b>Postconditions</b>: Remove a card from the UnusedCards arraylist, add it to UsedCards arraylist
     *                        and decrease the number of total cards
     */
    public void remove_card(){
        UsedCards.add(UnusedCards.get(0));
        UnusedCards.remove(0);
        Collections.shuffle(UnusedCards);
        totalCards--;
    }

    /**
     * <b>Accessor</b>: Check if the deck is empty
     * @return true if its empty, else false
     */
    public boolean deckIsEmpty() {
        return UnusedCards.isEmpty();
    }

    /**
     * <b>Transformer</b>: Reinitialise the deck of the cards if its empty
     * <b>Preconditions</b>: Check if the deck of cards is empty
     * <b>Postconditions</b>: Reinitialise the deck of the cards
     */
    public void refill_deck() {
        if (deckIsEmpty()) {
            UnusedCards = new ArrayList<>(UsedCards);
            Collections.shuffle(UnusedCards);
            totalCards = UnusedCards.size();
            UsedCards.clear();
        }
    }

    /**
     * <b>Accessor</b>: Returns number of the remained cards
     * @return the number of cards that arraylist "UnusedCards" contains
     */
    public int deckSize(){
        return UnusedCards.size();
    }

    /**
     * <b>Accessor</b>: Returns the card of a specific index
     * @return the card of index = parameter value
     */
    public Card getCard(int index){
        return UnusedCards.get(index);
    }

    /**
     * <b>Accessor</b>: Returns a list with the cards that are were not used
     * @return a list with the cards that are were not used
     */
    public ArrayList<Card> getUnusedCards(){
        return UnusedCards;
    }

    /**
     * <b>Accessor</b>: Returns a list with the cards that are were used
     * @return a list with the cards that are were used
     */
    public ArrayList<Card> getUsedCards(){
        return UsedCards;
    }

    /**
     * <b>Transformer</b>: Changes the number of the total cards of the deck
     * <b>Postconditions</b>: Changes the number of the total cards of the deck to parameter value
     */
    public void setTotalCards(int totalCards) {
        this.totalCards = totalCards;
    }

    /**
     * <b>Accessor</b>: Returns the number of the remained total cards
     * @return the number of the remained total cards
     */
    public int getTotalCards() {
        return totalCards;
    }
}
