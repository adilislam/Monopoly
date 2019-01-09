// Monopoly class which contains the board and handles each of the player's turns
import java.util.*;

public class Monopoly {
   private List<Player> players;
   private Map<String, Player> names;
   private Map<Integer, PropertySpace> properties;
   private int[] propertySpaces;
   private int[] propertyPrices;
   private int[] propertyRents;
   private int[] propertyMortgages;
   private int goToJailSpace;
   
   // Constructs a Monopoly, including setting up the initial board state, players, and properties
   public Monopoly() {
      this.players = new ArrayList<Player>();
      this.names = new HashMap<String, Player>();
      for (Player person: players) {
         names.put(person.getName(), new Player(person.getName()));
      }
      this.propertySpaces = new int[] {1,3,6,8,9,11,13,14,16,18,19,21,23,24,26,27,29,31,32,34,37,39};
      this.propertyPrices = new int[] {60,60,100,100,120,140,140,160,180,180,200,220,220,240,260,260,280,300,300,320,350,400};
      this.propertyRents = new int[] {2,4,6,6,8,10,10,12,14,14,16,18,18,20,22,22,24,26,26,28,35,50};
      this.propertyMortgages = new int[] {30,30,50,50,60,70,70,80,90,90,100,100,100,120,130,130,140,150,150,160,200,200};
      this.properties = new HashMap<Integer, PropertySpace>();
      for (int i = 0; i < propertySpaces.length; i++) {
         properties.put(i, new PropertySpace(i, propertyPrices[i], propertyRents[i], propertyMortgages[i]));
      }
      this.goToJailSpace = 30;
   } 
     
   // Moves a player to a given space based on a dice roll.
   // Passed in a String representing the name of the player to move 
   // Performs the neccessary action on the space if it is a property or go to jail space. 
   public void move(String name) {
      if (names.containsKey(name)) {
         Player person = names.get(name);
         int position = person.move();
         if (position == goToJailSpace) {
            person.jailAction();
         } else {
            for (int i = 0; i < propertySpaces.length; i++) {
               if (position == propertySpaces[i]) {
                  propertyAction(i, person);
               }
            } 
         }   
      }
   }
   
   // Performs the necessary action if a player lands on a property space.
   // If the property is unowned, the player can buy it.
   // If the property is owned by another player, the player must pay rent. 
   // Passed in the position of the property and the current player
   public void propertyAction(int position, Player person) {
      PropertySpace property = properties.get(position);
      if (property.isOwned()) {
         if (!person.getProperties().contains(property)) {
            String owner = property.getOwner();
            Player propertyOwner = names.get(owner);
            if (!person.payRent(property, propertyOwner)) {
               // Since the player cannot pay rent they are removed from the list of players and lose the game
               players.remove(person);
            }
         }
      } else if (property.getPrice() < person.getMoney()) {
         // Currently set up so that all properties are bought if landed on and can be afforded  
         // by the player, since user input has not been implemented yet to determine whether or 
         // not a player would like to buy a property or not. 
         person.buyProperty(property);
      }       
   }     
}
   
