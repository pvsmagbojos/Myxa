package softeng2.teamhortons.myxa.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.data.model.CartItem;
import softeng2.teamhortons.myxa.data.model.Recipe;
import softeng2.teamhortons.myxa.ui.ViewRecipeActivity;
import softeng2.teamhortons.myxa.ui.menu.home.cart.adapter.CartItemAdapter;
import softeng2.teamhortons.myxa.ui.menu.home.showcase.adapter.RecipeListAdapter;


public class MenuActivity extends AppCompatActivity implements RecipeListAdapter.OnItemClickListener, CartItemAdapter.OnItemClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public void viewModal(Recipe recipe) {
        String rName = recipe.getName();
        Double rPrice = recipe.getPrice();
        HashMap<String, String> rIngredients = recipe.getRecipe_ingredients();
        ArrayList<String> rProcedure = recipe.getProcedure();
        String imgPreview = recipe.getImgUriPreview();
        String img = recipe.getImgUri();


        //pass values
        Intent intent = new Intent(this, ViewRecipeActivity.class);
        intent.putExtra("recipeName", rName);
        intent.putExtra("recipeIngredients", rIngredients);

        intent.putExtra("recipeProcedure",rProcedure);

        intent.putExtra("recipePrice", rPrice);
        intent.putExtra("recipeImagePreview", imgPreview);
        intent.putExtra("recipeImage", img);
        //show modal
        startActivity(intent);
    }

    @Override
    public void removeRecipe(CartItem cartItem) {
        //test
        Log.d("MenuActivity", cartItem.getName());
    }

    @Override
    public void plusQuantity(CartItem cartItem) {
        //test
        Log.d("MenuActivity", cartItem.getName());
    }

    @Override
    public void minusQuantity(CartItem cartItem) {
        //test
        Log.d("MenuActivity", cartItem.getName());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
        finish();
    }


}
