package softeng2.teamhortons.myxa.ui.menu.home.cart;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.firestore.FirebaseFirestore;

import softeng2.teamhortons.myxa.data.repository.CartRepository;

public class CartViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CartViewModel.class)) {
            return (T) new CartViewModel(CartRepository.getInstance(FirebaseFirestore.getInstance()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
