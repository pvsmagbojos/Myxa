package softeng2.teamhortons.myxa.ui;

import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import softeng2.teamhortons.myxa.R;

public class ViewRecipeActivity extends AppCompatActivity {
    public static int REQUEST_CODE = 1;
    public String recipeToGet = "egg-omelette";

    // ------RECIPE PROPERTIES------
    final HashMap<String, String> names_and_ingredients =  new HashMap<>();

    public String recipeName;

    public Map<String, String> ingredients;
    public String[] ingredientId;
    public List<String> ingredientName;
    public List<String> ingredientQty;

    public List<String> procedure;
    public String price;
    // -----------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        final ImageView recipeImage = findViewById(R.id.imageView4);
        final TextView recipeTextView = findViewById(R.id.textView6);
        final TextView ingredientsTextView = findViewById(R.id.textView11);
        final TextView procedureTextView = findViewById(R.id.textView13);
        final TextView priceTextView = findViewById(R.id.textView14);
        final Button backButton = findViewById(R.id.backButton);
        final Button addToCartButton = findViewById(R.id.addToCartButton);
        final Button faveButton = findViewById(R.id.faveButton);

        // set image
        Picasso.get()
                .load(getIntent().getStringExtra("recipeImage"))
                .fit()
                .centerCrop()
                .into(recipeImage);

        recipeTextView.setText(getIntent().getStringExtra("recipeName"));

        // set ingredients
        HashMap<String, String> recipe_ingredients = (HashMap<String, String>) getIntent().getSerializableExtra("recipeIngredients");

        ingredientsTextView.setText("");
        for(Map.Entry<String, String> ingredient : recipe_ingredients.entrySet()) {
            ingredientsTextView.setText(ingredientsTextView.getText()
                    + ingredient.getValue() + " " + ingredient.getKey() + "\n");
        }

        // set procedure
        ArrayList<String> procedures = getIntent().getStringArrayListExtra("recipeProcedure");
        String procedureText = "";
        for(int i =0;i<procedures.size();i++){
            procedureText += (i+1) + ". " + procedures.get(i) + "\n\n";
        }
        procedureTextView.setText(procedureText);

        // set price
        double price = getIntent().getDoubleExtra("recipePrice", 0.0);
        priceTextView.setText(Double.toString(price));

        //buttons
        backButton.setOnClickListener(v -> finish());

        addToCartButton.setOnClickListener(v -> {
            //add to cart function
        });

        faveButton.setOnClickListener(v -> {
            //add to favorites function
        });
    }
}