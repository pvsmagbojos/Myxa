package softeng2.teamhortons.myxa.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.IgnoreExtraProperties;

@SuppressWarnings("unused")
@IgnoreExtraProperties
public class Order extends Model {

    DocumentReference userRef;
    DocumentReference riderRef;
    double totalPrice;
    Timestamp datePosted;
    Timestamp dateCompleted;

    public Order() {
        // Default constructor required for calls to DataSnapshot.toObject(Order.class)
    }

    public DocumentReference getUserRef() {
        return userRef;
    }

    public DocumentReference getRiderRef() {
        return riderRef;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Timestamp getDatePosted() {
        return datePosted;
    }

    public Timestamp getDateCompleted() {
        return dateCompleted;
    }
}
