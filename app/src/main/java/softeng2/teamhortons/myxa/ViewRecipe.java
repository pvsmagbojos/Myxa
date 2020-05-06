package softeng2.teamhortons.myxa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ViewRecipe extends AppCompatActivity {
    public static int REQUEST_CODE = 1;

    public static String recipe_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        final TextView recipeTextView = findViewById(R.id.textView6);
        final TextView ingredientsTextView = findViewById(R.id.textView11);
        final TextView procedureTextView = findViewById(R.id.textView13);
        final TextView priceTextView = findViewById(R.id.textView14);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("recipes").document("egg-omelette");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TEST1", "DocumentSnapshot data: " + document.getString("name"));
                        List<String> group = (List<String>) document.get("procedure");
                        recipeTextView.setText(document.getString("name"));
                        Log.d("TEST1.5", group.listIterator().next());

                        ingredientsTextView.setText("");
                        for(int i = 0; i < group.size(); i++) {
                            ingredientsTextView.setText(ingredientsTextView.getText() + "\n" + group.get(i));
                        }

                        procedureTextView.setText("");
                        for(int i = 0; i < group.size(); i++) {
                            procedureTextView.setText(procedureTextView.getText() + "\n" + group.get(i));
                        }

                        priceTextView.setText(document.getDouble("price").toString());
                        Log.d("TESTEND", ".");
                    } else {
                        Log.d("TEST2", "No such document");
                    }
                } else {
                    Log.d("TEST3", "get failed with ", task.getException());
                }
            }
        });
    }
}
