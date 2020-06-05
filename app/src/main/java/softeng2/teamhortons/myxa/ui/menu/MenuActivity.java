package softeng2.teamhortons.myxa.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.data.model.CartItem;
import softeng2.teamhortons.myxa.data.model.Recipe;
import softeng2.teamhortons.myxa.ui.ViewRecipeActivity;
import softeng2.teamhortons.myxa.ui.menu.home.cart.CartViewModel;
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
    public Task removeRecipe(DocumentReference recipeRef) {
        return FirebaseFirestore.getInstance().collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("cart")
                .whereEqualTo("recipe_id", recipeRef)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot docSnap : queryDocumentSnapshots.getDocuments()) {
                            docSnap.getReference().delete();
                        }
                    }
                });
    }

    @Override
    public Task plusQuantity(DocumentReference recipeRef) {
        return FirebaseFirestore.getInstance().collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("cart")
                .whereEqualTo("recipe_id", recipeRef)
                .limit(1)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot docSnap : queryDocumentSnapshots.getDocuments()) {
                            int quantity = Integer.parseInt(docSnap.getString("qty"));
                            quantity++;
                            docSnap.getReference().update("qty", String.valueOf(quantity));
                        }
                    }
                });
    }

    @Override
    public Task minusQuantity(DocumentReference recipeRef) {
        return FirebaseFirestore.getInstance().collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("cart")
                .whereEqualTo("recipe_id", recipeRef)
                .limit(1)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot docSnap : queryDocumentSnapshots.getDocuments()) {
                            int quantity = Integer.parseInt(docSnap.getString("qty"));
                            if(quantity > 1) {
                                quantity--;
                                docSnap.getReference().update("qty", String.valueOf(quantity));
                            }
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
        finish();
    }


}
