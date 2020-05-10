package softeng2.teamhortons.myxa;

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

        final FirebaseFirestore db = FirebaseFirestore.getInstance();


        DocumentReference docRef = db.collection("recipes").document("recipeToGet");
        final CollectionReference ingredientsRef = db.collection("ingredients");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        try {
                            // get from from DocumentSnapshot
                            recipeName = document.getString("name");
                            ingredients = (HashMap<String, String>) document.get("recipe_ingredients");
                            procedure = (List<String>) document.get("procedure");
                            price = document.getDouble("price").toString();

                            // --image
                            // set recipe image
                            Picasso.get()
                                    .load("https://firebasestorage.googleapis.com/v0/b/teamhortons-myxa.appspot.com/o/recipe-img%2Fegg-omelette.jpg?alt=media&token=2895b6c6-88f3-4dea-b266-bf5e99d161f1")
                                    .fit()
                                    .centerCrop()
                                    .into(recipeImage);

                            // --recipe_name
                            // set recipe name
                            recipeTextView.setText(recipeName);


                            // --ingredients (used as reference by recipe_ingredients)
                            //      use keys of recipe_ingredients to get ingredient name from ingredients collection
                            //      long ass piece of headache since we decided to let users customize ingredients *angry react*
                            ingredientId = new String[ingredients.size()];
                            ingredientName = new ArrayList<String>();
                            ingredientQty = new ArrayList<String>();

                            //      get ID
                            int idIndex = 0;
                            for(String key : ingredients.keySet()) {
                                ingredientId[idIndex] = key;
                                idIndex++;
                            }

                            //      get qty
                            for(String value : ingredients.values()) {
                                ingredientQty.add(value);
                            }

                            //      get name
                            DocumentReference ingredientDR;
                            List<Task<DocumentSnapshot>> ingredientsTasks = new ArrayList<Task<DocumentSnapshot>>();
                            int safetyLimit = 100;
                            for(int i = 0; i < ingredients.size(); i++) {
                                if(i > safetyLimit) {
                                    Log.d("ViewRecipe", "exceeded 100 reads, loop will break now");
                                    break;
                                }
                                ingredientDR = ingredientsRef.document(ingredientId[i]);

                                ingredientsTasks.add(ingredientDR.get());

                                if(i == ingredients.size()-1) {
                                    for(Task<DocumentSnapshot> t : ingredientsTasks) {
                                        t.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot document = task.getResult();
                                                    if (document.exists()) {
                                                        Log.d("ViewRecipe", "DocumentSnapshot data: " + document.getData());

                                                        ingredientName.add(document.getString("name"));
                                                    } else {
                                                        Log.d("ViewRecipe", "No such document");
                                                    }
                                                } else {
                                                    Log.d("ViewRecipe", "get failed with ", task.getException());
                                                }
                                            }
                                        });
                                    }

                                    Task all = Tasks.whenAllSuccess(ingredientsTasks).addOnCompleteListener(new OnCompleteListener<List<Object>>() {
                                        @Override
                                        public void onComplete(@NonNull Task<List<Object>> task) {
                                            // set ingredients list
                                            for(int i = 0; i < ingredients.size(); i++){
                                                ingredientsTextView.setText(ingredientsTextView.getText() + ingredientName.get(i) + " " + ingredientQty.get(i) + "\n");
                                            }
                                        }
                                    });
                                }
                            }

                            // --procedure
                            // set procedure list
                            for(int i = 0; i < procedure.size(); i++) {
                                procedureTextView.setText(procedureTextView.getText() + "\n" + procedure.get(i));
                            }

                            priceTextView.setText(price);
                            Log.d("testEND", ".");
                        }catch (Exception e) {
                            Log.d("ViewRecipeDocumentError", e.getMessage());
                            Toast.makeText(getApplicationContext(), "Failed getting recipe data.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        Log.d("ViewRecipe", "No such document");
                    }
                } else {
                    Log.d("ViewRecipe", "get failed with ", task.getException());
                }
            }
        });

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