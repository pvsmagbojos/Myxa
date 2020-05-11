package softeng2.teamhortons.myxa.ui.menu.fragment.showcase;

import androidx.lifecycle.ViewModel;

import softeng2.teamhortons.myxa.data.repository.ShowcaseRepository;

class ShowcaseViewModel extends ViewModel {
    private ShowcaseRepository showcaseRepository;

    ShowcaseViewModel(ShowcaseRepository showcaseRepository) {
        this.showcaseRepository = showcaseRepository;
    }
}
