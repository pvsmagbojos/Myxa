package softeng2.teamhortons.myxa.ui.menu.home.delivery;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

import softeng2.teamhortons.myxa.data.model.Order;
import softeng2.teamhortons.myxa.data.repository.DeliveryRepository;
import softeng2.teamhortons.myxa.generic.DeliveryType;

class DeliveryViewModel extends ViewModel {

    private DeliveryRepository deliveryRepository;

    private MutableLiveData<OrderResult> queryResult = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Order>> pastOrdersList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Order>> ongoingOrdersList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Order>> scheduledOrdersList = new MutableLiveData<>();

    DeliveryViewModel(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
        reload();
    }

    LiveData<OrderResult> getQueryResult() {
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
                ArrayList<ArrayList<Order>> orderLists = splitOrders(this.queryResult.getValue().getSuccess());

                pastOrdersList.setValue(orderLists.get(DeliveryType.DELIVERY_PAST));
                ongoingOrdersList.setValue(orderLists.get(DeliveryType.DELIVERY_ONGOING));
                scheduledOrdersList.setValue(orderLists.get(DeliveryType.DELIVERY_SCHEDULED));
            }
        } else {
            deliveryRepository.fetchDeliveryListFromRemote()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    ArrayList<Order> orders = new ArrayList<>();

                    for(DocumentSnapshot document : queryDocumentSnapshots) {
                        orders.add(document.toObject(Order.class));
                    }

                    queryResult.setValue(new OrderResult(orders));

                    ArrayList<ArrayList<Order>> orderLists = splitOrders(orders);
                    pastOrdersList.setValue(orderLists.get(DeliveryType.DELIVERY_PAST));
                    ongoingOrdersList.setValue(orderLists.get(DeliveryType.DELIVERY_ONGOING));
                    scheduledOrdersList.setValue(orderLists.get(DeliveryType.DELIVERY_SCHEDULED));

                }).addOnFailureListener(e -> queryResult.setValue(new OrderResult(e)));
        }
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
                } else if(order.getRiderRef() != null) {
                    orderLists.get(DeliveryType.DELIVERY_ONGOING).add(order);
                } else {
                    orderLists.get(DeliveryType.DELIVERY_SCHEDULED).add(order);
                }
            }
        }

        return orderLists;
    }
}

