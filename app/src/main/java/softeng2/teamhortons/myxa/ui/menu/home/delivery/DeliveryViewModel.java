package softeng2.teamhortons.myxa.ui.menu.home.delivery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
        return queryResult;
    }
}

