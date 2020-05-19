package softeng2.teamhortons.myxa.ui.menu.home.delivery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import softeng2.teamhortons.myxa.data.model.Order;
import softeng2.teamhortons.myxa.data.repository.DeliveryRepository;

class DeliveryViewModel extends ViewModel {

    private DeliveryRepository deliveryRepository;

    private MutableLiveData<QueryResult> queryResult = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Order>> pastOrdersList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Order>> ongoingOrdersList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Order>> scheduledOrdersList = new MutableLiveData<>();

    DeliveryViewModel(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    LiveData<QueryResult> getQueryResult() {
        reload();
        return queryResult;
    }

    LiveData<ArrayList<Order>> getPastOrdersList() {
        return pastOrdersList;
    }

    LiveData<ArrayList<Order>> getOngoingOrdersList() {
        return ongoingOrdersList;
    }

    LiveData<ArrayList<Order>> getScheduledOrdersList() {
        return scheduledOrdersList;
    }

    void reload() {
        if (this.queryResult.getValue() != null) {
            if(queryResult.getValue().getSuccess() != null) {
                queryResult.setValue(this.queryResult.getValue());
            }
        } else {
            deliveryRepository.fetchDeliveryListFromRemote().addOnSuccessListener(
                    queryDocumentSnapshots -> {

            }).addOnFailureListener(e -> queryResult.setValue(new QueryResult(e)));
        }
    }
}

