package softeng2.teamhortons.myxa.ui.menu.home.showcase;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import softeng2.teamhortons.myxa.data.repository.ShowcaseRepository;

public class ShowcaseViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ShowcaseViewModel.class)) {
            return (T) new ShowcaseViewModel(ShowcaseRepository.getInstance());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
