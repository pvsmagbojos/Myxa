package softeng2.teamhortons.myxa.data.repository;

import com.google.firebase.firestore.FirebaseFirestore;

public class ShowcaseRepository {
    private static volatile ShowcaseRepository instance;

    private FirebaseFirestore dataSource;

    // private constructor : singleton access
    private ShowcaseRepository(FirebaseFirestore dataSource) {
        this.dataSource = dataSource;
    }

    public static ShowcaseRepository getInstance(FirebaseFirestore dataSource) {
        if(instance == null){
            instance = new ShowcaseRepository(dataSource);
        }
        return instance;
    }
}
