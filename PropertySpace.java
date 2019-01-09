// PropertySpace objects extend Space objects, and represent each of the properties along the board
public class PropertySpace {
   private int position;
   private int price;
   private int rent;
   private int mortgage;
   private boolean owned;
   private String owner;
   
   // Constructs a PropertySpace
   public PropertySpace(int position, int price, int rent, int mortgage) {
      this.position = position;
      this.price = price;
      this.rent = rent;
      this.mortgage = mortgage;
      this.owned = false;
   }
   
   // Returns an integer representing the price of this property
   public int getPrice() {
      return price;
   }
   
   // Returns an integer representing the rent of this property
   public int getRent() {
      return rent;
   }
   
   // Returns an integer representing the mortgage value of this property
   public int getMortgage() {
      return mortgage;
   }
   
   // Returns a boolean representing whether or not this property is owned
   public boolean isOwned() {
      return owned;
   }
   
   // Adds the name of the owner to this property
   // Passed in a String representing a name
   public void addOwner(String name) {
      owner = name;
   }
   
   // Returns a String representing the owner of this property
   public String getOwner() {
      return owner;
   }
   
   // Mortgages this property
   // Players no longer pay rent for landing on this property
   // The Player who owns this property gains money equal to the mortgage value
   public void makeMortgaged(Player propertyOwner) {
      rent = 0;
      propertyOwner.addMoney(mortgage);
   }
}

   