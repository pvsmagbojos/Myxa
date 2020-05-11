package softeng2.teamhortons.myxa.ui.main;

import androidx.lifecycle.ViewModel;

import softeng2.teamhortons.myxa.data.repository.AuthRepository;

class MainViewModel extends ViewModel {

    private AuthRepository authRepository;

    MainViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    boolean isLoggedIn() {
        return authRepository.isLoggedIn();
    }
}
