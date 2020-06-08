package softeng2.teamhortons.myxa.ui.rider;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import softeng2.teamhortons.myxa.data.repository.AuthRepository;
import softeng2.teamhortons.myxa.data.repository.UserRepository;

public class RiderViewModelFactory implements ViewModelProvider.Factory{
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
        if (modelClass.isAssignableFrom(RiderViewModel.class)){
            return (T) new RiderViewModel(
                    AuthRepository.getInstance(), UserRepository.getInstance());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
