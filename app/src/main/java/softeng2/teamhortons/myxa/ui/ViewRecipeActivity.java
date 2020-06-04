package softeng2.teamhortons.myxa.ui;

import android.app.ProgressDialog;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.data.model.Recipe;
import softeng2.teamhortons.myxa.data.repository.CartRepository;

public class ViewRecipeActivity extends AppCompatActivity {
    public static int REQUEST_CODE = 1;

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
            final ProgressDialog dialog = new ProgressDialog(ViewRecipeActivity.this);
            dialog.setMessage("Adding to cart: " + getIntent().getStringExtra("recipeName"));
            dialog.show();

            final ArrayList<String> recipeDocId = new ArrayList<>();

            // check if has cart document (users >  cart > cartItemID > qty,docRef)
            FirebaseFirestore.getInstance().collection("recipes")
                    .whereEqualTo("name", getIntent().getStringExtra("recipeName"))
                    .limit(1)
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    Log.d("TESTVIEW", String.valueOf(queryDocumentSnapshots.getDocuments().size()));

                    for(DocumentSnapshot docSnap2 : queryDocumentSnapshots.getDocuments()) {
                        recipeDocId.add(docSnap2.getId());

                        HashMap<String, Object> newDoc = new HashMap<>();
                        newDoc.put("recipe_id", (DocumentReference) docSnap2.getReference());
                        newDoc.put("qty", (String) "1");

                        FirebaseFirestore.getInstance().collection("users")
                                .document(FirebaseAuth.getInstance().getUid())
                                .collection("cart")
                                .add(newDoc).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                dialog.hide();
                            }
                        });
                    }
                }
            });
        });

        faveButton.setOnClickListener(v -> {
            //add to favorites function
        });
    }
}