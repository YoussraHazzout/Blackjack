package Blackjack;
import java.util.ArrayList;
import java.util.Random;
    /*
     * Maakt een deck 52 kaarten
     */
    class Deck {
        private ArrayList<Kaart> deck;// Arraylist die het deck voorstelt
        Deck()
        {
            deck = new ArrayList<Kaart>();
            for(int i=0; i<4; i++)
            {
                for(int j=1; j<=13; j++)
                {
                    deck.add(new Kaart(i,j));
                }
            }
        }
        /*
         * Schudt de kaarten, d.v.m. random getal, totaal 200 verschillende combinaties
         */
        public void schudden()
        {
            Random random = new Random();
            Kaart temp;
            for(int i=0; i<200; i++)
            {
                int index1 = random.nextInt(deck.size()-1);
                int index2 = random.nextInt(deck.size()-1);
                temp = deck.get(index2);
                deck.set(index2, deck.get(index1));
                deck.set(index1, temp);
            }
        }
        /*
         * Haalt een kaart uit de deck.
         */
        public Kaart neemKaart()
        {
            return deck.remove(0);
        }
}
