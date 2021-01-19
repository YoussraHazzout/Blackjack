package Blackjack;
import java.util.*;
public class Blackjack {
    private static int geld;// Hoeveel geld de gebruiker heeft
    private static int inzet;// Hoeveel de gebruiker wilt gokken.
    private static int aasTeller;//hoeveel azen er in
    private static ArrayList<Kaart> hand;//Arraylist als hand van de gebruiker
    private static int handWaarde;//the value of the user's hand
    private static String naam;//naam van de gebruiker
    public static void main(String[] args){
        System.out.printf("%n――――――――――――――――――――――――――――――――――%n");
        System.out.print("♤♥♣♦ Welkom bij het spel BlackJack in ons casino! ♦♣♥♤");
        System.out.printf("%n――――――――――――――――――――――――――――――――――%n");
        System.out.println("Hallo, wat is uw naam?");
        Scanner scan = new Scanner(System.in);
        naam = scan.nextLine();
        System.out.println("Hallo, "+ naam +", laten we een potje BlackJack spelen!");
        System.out.println("Hoeveel geld heeft u in uw portomonee?");
        Scanner money = new Scanner(System.in);
        geld = money.nextInt();
        System.out.println("U heeft zo veel in uw portomonee: €"+ geld);
        while(geld >0){
            Deck deck = new Deck();//initialiseert deck, de dealer, handen, en de gok.
            deck.shuffle();
            aasTeller =0;
            Dealer dealer = new Dealer(deck);
            List<Kaart> hand = new ArrayList<>();
            hand.add(deck.neemKaart());
            hand.add(deck.neemKaart());
            System.out.println("Hoeveel wilt u inzetten?");
            inzet =Bet(geld);
            System.out.println("Uw inzet is  €"+ inzet);
            System.out.println("Uw heeft nog: €"+(geld - inzet));
            System.out.println("Dit zijn uw kaarten: ");
            System.out.println(hand);
            int handWaarde = calcHandWaarde(hand);
            System.out.println("De dealer laat zien: ");
            dealer.showEersteKaart();
            if(hasBlackJack(handWaarde) && dealer.hasBlackJack())//controleert of de gebruiker of dealer BlackJack hebben.
            {
                Gelijkspel();
            }
            else if(hasBlackJack(handWaarde))//Controleert of de gebruiker BlackJack heeft.
            {
                System.out.println("$$$ Gefeliciteerd,u heeft BlackJack $$$");
                System.out.println("Uw geld verdubbelt!");
                geld = geld + inzet;
                Gewonnen();
            }
            else if(dealer.hasBlackJack())//Controleert of de dealer BlackJack heeft.
            {
                System.out.println("Dit is de hand van de dealer.:");
                dealer.showHand();
                Verloren();
            }
            else
            {
                if(2* inzet < geld)//Controleert of je inzet kan verdubbelen?
                {
                    System.out.println("Wilt u uw inzet verdubbelen?");//staat toe dat gebruiker inzet verdubbelt.
                    Scanner doubledown = new Scanner(System.in);
                    String doubled = doubledown.nextLine();
                    while(!JaofNee(doubled))
                    {
                        System.out.println("Voer ja of nee");
                        doubled = doubledown.nextLine();
                    }
                    if(doubled.equalsIgnoreCase("ja"))
                    {
                        System.out.println("U heeft gekozen om uw inzet te dubbelen!");
                        inzet =2* inzet;
                        System.out.println("U heeft in uw portomonee: €"+(geld - inzet));
                        System.out.println("Het bedrag op tafel is: €"+ inzet);
                    }
                }
                System.out.println("Wilt u een extra kaart of passen?");// vraagt of de gebruiker een extra kaart wilt
                System.out.println("Voer in 'kaart' or 'passen'.");
                Scanner kaartofniet = new Scanner(System.in);
                String extraKaart = kaartofniet.nextLine();
                while(!isKaartOfNiet(extraKaart))
                {
                    extraKaart = kaartofniet.nextLine();
                }
                while(extraKaart.equals("kaart"))//De gebruiker krijgt zo veel kaarten als hij/zij wilt
                {
                    ExtraKaart(deck, hand);
                    System.out.println("U heeft nu deze kaarten:");
                    System.out.println(hand);
                    handWaarde = calcHandWaarde(hand);
                    if(checkDood(handWaarde))//controleert of de gebruiker dood is.
                    {
                        Verloren();
                        break;
                    }
                    if(handWaarde<=21 && hand.size()==5)//controleert 5 kaarten truuk
                    {
                        vijfKaartenTruuk();
                        break;
                    }
                    System.out.println("Wilt u een extra kaart of passen?");
                    System.out.println("Voer in kaart of passen");
                    extraKaart = kaartofniet.nextLine();
                }
                if(extraKaart.equals("passen"))//lets the user stand.
                {
                    int dealerHand = dealer.takeBeurt(deck);//zorgt voor de hand van de dealer
                    System.out.println("");
                    System.out.println("Dit zijn de kaarten van de Dealer:");
                    dealer.showHand();
                    if(dealerHand>21)//als dealer dood is, wint de gebruiker
                    {
                        Gewonnen();
                    }
                    else
                    {
                        int kaartenGebruiker = 21-handWaarde;//controleert wie dichter bij 21 is
                        int kaartenDealer = 21-dealerHand;
                        if(kaartenGebruiker==kaartenDealer)
                        {
                            Gelijkspel();
                        }
                        if(kaartenGebruiker<kaartenDealer)
                        {
                            Gewonnen();
                        }
                        if(kaartenDealer<kaartenGebruiker)
                        {
                            Verloren();
                            break;
                        }
                    }
                }
            }
            System.out.println("Wilt u verder spelen?");// vraagt of de speler verder wilt spelen
            System.out.println("Voer Ja of Nee in");
            Scanner jaofNee = new Scanner(System.in);
            String answer = jaofNee.nextLine();
            while(!JaofNee(answer))
            {

                answer = jaofNee.nextLine();
            }
            if(answer.equalsIgnoreCase("Nee"))
            {
                break;
            }
        }
        System.out.println("U gaat naar huis met: €"+ geld);// stoppen of geen geld meer
        if(geld ==0)
        {
            System.out.println("U heeft geen cent meer, tijd om naar huis te gaan! ");
        }
        else
        {
            System.out.println("Geniet van uw geld, "+ naam +"!");
        }
    }
    /*
     * Controleert of de gebruiker BlackJack heeft
     */
    public static boolean hasBlackJack(int handWaarde)
    {
        if(handWaarde==21)
        {
            return true;
        }
        return false;
    }
    /*
     * Berekend de waarde van de gebruikerskaarten
     */
    public static int calcHandWaarde(List<Kaart> hand)
    {
        Kaart[] aHand = new Kaart[]{};
        aHand = hand.toArray(aHand);
        int handvalue=0;
        for(int i=0; i<aHand.length; i++)
        {
            handvalue += aHand[i].getWaarde();
            if(aHand[i].getWaarde()==11)
            {
                aasTeller++;
            }
            while(aasTeller >0 && handvalue>21)
            {
                handvalue-=10;
                aasTeller--;
            }
        }
        return handvalue;
    }
    /*
     * Hoeveel wil je inzetten?
     */
    public static int Bet(int cash)
    {
        Scanner sc=new Scanner(System.in);
        int bet=sc.nextInt();
        while(bet>cash)
        {
            System.out.println("U kunt niet meer inzetten dan u heeft!");
            System.out.println("Hoeveel wilt u inzetten?");
            bet=sc.nextInt();
        }
        return bet;
    }
    /*
     * Gebruiker wins wins.
     */
    public static void Gewonnen()
    {
        System.out.println("Gefeliciteerd, u heeft gewonnen!");
        geld = geld + inzet;
        System.out.println("U heeft: €"+ geld);
    }
    /*
     * Gebruiker verliest
     */
    public static void Verloren()
    {
        System.out.println("Sorry, u heeft verloren!");
        geld = geld - inzet;
        System.out.println("U heeft: €"+ geld);
    }
    /*
     * Gelijk spel?
     */
    public static void Gelijkspel()
    {
        System.out.println("Het is gelijk spel!");
        System.out.println("U krijgt uw geld terug.");
        System.out.println("U heeft: €"+ geld);
    }
    /*
     * Voegt een kaart toe aan de gebruikers hand, berekent de waarde van de hand inclusief Aas.
     */
    public static void ExtraKaart(Deck deck, List<Kaart> hand)
    {
        hand.add(deck.neemKaart());
        Kaart[] aHand = new Kaart[]{};
        aHand = hand.toArray(aHand);
        handWaarde = 0;
        for(int i=0; i<aHand.length; i++)
        {
            handWaarde += aHand[i].getWaarde();
            if(aHand[i].getWaarde()==11)
            {
                aasTeller++;
            }
            while(aasTeller >0 && handWaarde>21)
            {
                handWaarde-=10;
                aasTeller--;
            }
        }
    }
    /*
     * Determines if a user has input hit or stand.
     */
    public static boolean isKaartOfNiet(String hitter)
    {
        if(hitter.equalsIgnoreCase("kaart") || hitter.equalsIgnoreCase("passen"))
        {
            return true;
        }
        return false;
    }
    /*
     * Beslist of de gebruiker dood is
     */
    public static boolean checkDood(int handvalue)
    {
        if(handvalue>21)
        {
            System.out.println("U bent dood! (X_X)");
            return true;
        }
        return false;
    }
    /*
     * Beslist over de Ja of Nee van de gebruiker.
     */
    public static boolean JaofNee(String answer)
    {
        if(answer.equalsIgnoreCase("Ja") || answer.equalsIgnoreCase("Nee"))
        {
            return true;
        }
        return false;
    }
    /*
     * Controleert of de gebruiker de 5 kaarten truuk heeft
     */
    public static void vijfKaartenTruuk()
    {
        System.out.println("U heeft de 5 kaarten truuk!");
        Gewonnen();
    }
    }