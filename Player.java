// Player objects represent each player within the game
import java.util.*;

public class Player {  
   private String name;
   private int money;
   private int position;
   private boolean jailed;
   private List<PropertySpace> ownedProperties;
   
   // Constructs a Player. Individual Players must have unique names 
   public Player(String name) {
      this.name = name;
      this.money = 1500;
      this.position = 0;
      this.jailed = false;
      this.ownedProperties = new ArrayList<PropertySpace>();
   }
   
   // Returns a String representing the name of this player
   public String getName() {
      return name;
   }
   
   // Returns an integer representing the amount of money this player has
   public int getMoney() {
      return money;
   }
   
   // Returns a List of the properties this player owns
   public List<PropertySpace> getProperties() {
      return ownedProperties;
   }
   
   // Rolls the dice for this player's turn
   // Removes player from jail if player is in jail and doubles are rolled
   // Returns the sum of the players two dice rolls
   public int roll() {
      int dice1 = (int)(Math.random() * 6 + 1);
      int dice2 = (int)(Math.random() * 6 + 1);
      if (jailed == true) {
         if (dice1 == dice2) {
            jailed = false;
         }
      }
      return dice1 + dice2;
   }
   
   // Moves the player along the board if they are not in jail
   // Gives player $200 each time they complete a lap around the board and pass Go
   // Returns the current position of the player on the board
   public int move() {
      if (jailed == false) {
         position += roll();
         if (position > 39) {
            position -= 40;
            money += 200;
         }
      }
      return position;
   }
   
   // Puts the player in jail
   public void jailAction() {
      jailed = true;
      position = 10;
   }
   
   // Causes the player to exit jail by paying $50
   public void leaveJail() {
      money -= 50;
      jailed = false;
   }
   
   // Buys the passed in property and subtracts its cost from the player's money
   public void buyProperty(PropertySpace property) {
      money -= property.getPrice();
      ownedProperties.add(property);
      property.addOwner(name);
   }
   
   // Pays rent to the owner of the property the player landed on
   // If the player has more money than the cost of the rent, rent is paid using this money
   // If the player does not have enough money to pay rent, first the player attempts to mortgage 
   // properties in order to gain enough money to pay the rent
   // If the player does not have enough money or mortgage value to pay rent, the player loses
   // and is no longer alive, and thus removed from the game
   // Passed in the property which rent is being paid on and the Player who owns the property
   // Returns a boolean representing whether or not the player is alive
   public boolean payRent(PropertySpace property, Player propertyOwner) {
      // Currently set up so that all rent is paid using money first, then by mortgages properties
      // since user input has not been implemented yet to determine exactly how a user would like 
      // to pay rent
      boolean isAlive = true;
      if (money > property.getRent()) {
         money -= property.getRent();
         propertyOwner.addMoney(property.getRent());
      } else if (!ownedProperties.isEmpty()) {
         ownedProperties.get(0).makeMortgaged(propertyOwner);
         payRent(property, propertyOwner);
      } else {
         money -= property.getRent();
         isAlive = false;
      }
      return isAlive;          
   }
   
   // Adds the passed in amount of money to the player
   public void addMoney(int amount) {
      money += amount;
   }
}
   
   
      