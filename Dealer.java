package Blackjack;

import java.util.ArrayList;

/*
 * Dealer waar de gebruiker tegen speelt.
 */
class Dealer {
    ArrayList<Kaart> hand;//hand van de dealer
    private int handWaardeDealer = 0;//handwaarde van de dealer begint op 0
    private Kaart[] aHand;//Array die hand van de dealer voorstelt
    private int aasTellerDealer;//telt het aantal azen in de dealers hand.

    Dealer(Deck deck) {
        hand = new ArrayList<>();
        aHand = new Kaart[]{};
        int aasTellerDealer = 0;
        for (int i = 0; i < 2; i++) {
            hand.add(deck.neemKaart());
        }
        aHand = hand.toArray(aHand);
        for (int i = 0; i < aHand.length; i++) {
            handWaardeDealer += aHand[i].getWaarde();
            if (aHand[i].getWaarde() == 11) {
                aasTellerDealer++;
            }
            while (aasTellerDealer > 0 && handWaardeDealer > 21) {
                handWaardeDealer -= 10;
                aasTellerDealer--;
            }
        }
    }

    /*
     * Prints de dealers eerste kaart
     */
    public void showEersteKaart() {
        Kaart[] eersteKaart = new Kaart[]{};
        eersteKaart = hand.toArray(eersteKaart);
        System.out.println("[" + eersteKaart[0] + "]");
    }

    /*
     * Geeft de dealer een extra kaart, berekent de handwaarde inclusief aas.
     */
    public void ExtraKaart(Deck deck) {
        hand.add(deck.neemKaart());
        aHand = hand.toArray(aHand);
        handWaardeDealer = 0;
        for (int i = 0; i < aHand.length; i++) {
            handWaardeDealer += aHand[i].getWaarde();
            if (aHand[i].getWaarde() == 11) {
                aasTellerDealer++;
            }
            while (aasTellerDealer > 0 && handWaardeDealer > 21) {
                handWaardeDealer -= 10;
                aasTellerDealer--;
            }
        }
    }

    /*
     * Besluit of de Dealer een kaart wilt nemen als de waarde onder 17 is.
     */
    public boolean wilExtraKaart() {
        if (handWaardeDealer < 17) {
            return true;
        }
        return false;
    }

    /*
     * Controleert of de dealer BlackJack heeft.
     */
    public boolean hasBlackJack() {
        if (hand.size() == 2 && handWaardeDealer == 21) {
            System.out.println("De dealer heeft blackjack!");
            return true;
        }
        return false;
    }

    /*
     * Prints de dealers hand.
     */
    public void showHand() {
        System.out.println(hand);
    }

    /*
     * Geeft waarde van dealers  hand
     */
    public int getHandWaarde() {
        return handWaardeDealer;
    }

    /*
     * Controleert of de dealer dood is.
     */
    public boolean busted(int handvalue) {
        if (handvalue > 21) {
            System.out.println("De dealer is dood! (X_X)");
            return true;
        }
        return false;
    }

    /*
     * Beurt van de dealer, neemt een kaart.
     */
    public int takeBeurt(Deck deck) {
        while (wilExtraKaart()) {
            System.out.println("De dealer neemt een kaart.");
            ExtraKaart(deck);
            if (busted(handWaardeDealer)) {
                break;
            }
        }
        if (handWaardeDealer <= 21) {
            System.out.print("De dealer kiest voor passen.");
        }
        return handWaardeDealer;
    }
}
