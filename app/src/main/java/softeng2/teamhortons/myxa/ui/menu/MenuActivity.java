package softeng2.teamhortons.myxa.ui.menu;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.data.model.Recipe;
import softeng2.teamhortons.myxa.ui.menu.fragment.showcase.adapter.RecipeListAdapter;


public class MenuActivity extends AppCompatActivity implements RecipeListAdapter.OnItemClickListener {
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
        ArrayList<String> rProcedure = recipe.getProcedure();
        HashMap<String, String> rIngredients = recipe.getRecipe_ingredients();

        //set rName to modal
        //set rPrice to modal
        //set rProcedure to modal
        //set rIngredients to modal

        //show modal


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
        finish();
    }
}
