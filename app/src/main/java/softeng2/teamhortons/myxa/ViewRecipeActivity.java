package softeng2.teamhortons.myxa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Double price = getIntent().getDoubleExtra("recipePrice", 0.0);
        priceTextView.setText(price.toString());

        //buttons
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add to cart function
            }
        });

        faveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add to favorites function
            }
        });
    }
}