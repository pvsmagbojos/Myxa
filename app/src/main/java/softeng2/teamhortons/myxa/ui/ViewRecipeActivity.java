package softeng2.teamhortons.myxa.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Map;

import softeng2.teamhortons.myxa.R;

public class ViewRecipeActivity extends AppCompatActivity {
    public static int REQUEST_CODE = 1;
    public String recipeToGet = "egg-omelette";

    // ------RECIPE PROPERTIES------
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

        getIntent();

        //recipeName - fix autosizing
        recipeTextView.setText(getIntent().getStringExtra("recipeName"));

        //ingredients - fix
        ingredientsTextView.setText(getIntent().getSerializableExtra("recipeIngredients").toString());

        //procedure - fix
//        Bundle args = getIntent().getBundleExtra("BUNDLE");
//        ArrayList<String> procedures = (ArrayList<String>) args.getSerializable("ARRAYLIST");
//        procedureTextView.setText(procedures.toString());

        //price
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