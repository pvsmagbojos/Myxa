package softeng2.teamhortons.myxa.data.model;

import java.util.ArrayList;

public class Cart {
    private ArrayList<CartItem> cartRecipes;

    @SuppressWarnings("unused")
    public Cart(){}

    public ArrayList<CartItem> getCartRecipes() {return cartRecipes;}

    public void setCartRecipes(ArrayList<CartItem> cartRecipes) {this.cartRecipes = cartRecipes;}
}
