package Blackjack;
class Kaart {
    /*
     * creeert een kaart
     */
    private int rank;//rank van de kaarten
    private int bundel;//van welke bundel het is,
    private int waarde;//represents the value of a card
    private static String[] ranks = {"Joker","Aas","2","3","4","5","6","7","8","9","10","Boer","Dame","Koning"};
    private static String[] suits = {"♣","♦","♥ ","♠"};
    /*
     * Creeert een integer dat een plek representateert de rank en de bundel
     */
    Kaart(int bundel, int waarde)
    {
        this.rank=waarde;
        this.bundel=bundel;
    }
    /*
     * Geeft de complete kaart
     */
    public String toString()
    {
        return ranks[rank]+suits[bundel];
    }
    /*
     * Geeft de rank van de kaart
     */
    public int getRank()
    {
        return rank;
    }
    /*
     * Geeft de bundel van de kaart.
     */
    public int getBundel()
    {
        return bundel;
    }
    //Geeft de waarde van de kaart. Als het een Joker, boer, dame of koning is de waarde 10, Aas is 11.
    public int getWaarde()
    {
        if(rank>10)
        {
            waarde =10;
        }
        else if(rank==1)
        {
            waarde =11;
        }
        else
        {
            waarde =rank;
        }
        return waarde;
    }
    /*
     * Zet de waarde van een kaart
     */
    public void setWaarde(int set)
    {
        waarde = set;
    }
}