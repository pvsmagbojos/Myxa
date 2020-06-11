package softeng2.teamhortons.myxa.ui.rider.orders.ui.riderorders;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import softeng2.teamhortons.myxa.data.model.Order;
import softeng2.teamhortons.myxa.data.repository.DeliveryRepository;
import softeng2.teamhortons.myxa.data.repository.UserRepository;
import softeng2.teamhortons.myxa.generic.DeliveryType;

public class RiderOrdersViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<ArrayList<Order>> pastOrdersList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Order>> ongoingOrdersList = new MutableLiveData<>();

    public LiveData<ArrayList<Order>> getPastOrdersList() {
        return pastOrdersList;
    }

    public LiveData<ArrayList<Order>> getOngoingOrdersList() {
        return ongoingOrdersList;
    }

    RiderOrdersViewModel(FirebaseFirestore db, FirebaseAuth mAuth) {

        db.collection("orders").whereEqualTo("riderRef",
                db.collection("riders").document(mAuth.getCurrentUser().getUid()))
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    Log.d("query", queryDocumentSnapshots.toString());
                    ArrayList<Order> orders = new ArrayList<>();

                    for(DocumentSnapshot document : queryDocumentSnapshots) {
                        Log.d("query2", document.toString());
                        orders.add(document.toObject(Order.class));

                    }

                    ArrayList<ArrayList<Order>> orderLists = splitOrders(orders);
                    pastOrdersList.setValue(orderLists.get(DeliveryType.DELIVERY_PAST));
                    ongoingOrdersList.setValue(orderLists.get(DeliveryType.DELIVERY_ONGOING));

                });
    }

    private ArrayList<ArrayList<Order>> splitOrders(ArrayList<Order> orders) {
        ArrayList<ArrayList<Order>> orderLists = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            orderLists.add(new ArrayList<>());
        }

        for(Order order : orders) {
            if(order != null) {
                if(order.getDateCompleted() != null) {
                    orderLists.get(DeliveryType.DELIVERY_PAST).add(order);
                } else {
                    orderLists.get(DeliveryType.DELIVERY_ONGOING).add(order);
                }
            }
        }

        return orderLists;
    }
}
