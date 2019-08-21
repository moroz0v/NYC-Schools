package in.morozov.nycschoolstats.schools.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import in.morozov.nycschoolstats.schools.viewmodel.SchoolDetailsViewModel;
import in.morozov.nycschoolstats.schools.viewmodel.repo.SchoolDetailsRepository;

public class SchoolDetailsViewModelFactory implements ViewModelProvider.Factory {

    //use Factory Provider to only provide the factory in the create method to avoid this class
    //being instantiated many times with the activity
    @NonNull
    private final SchoolDetailsRepository schoolDetailsRepository;

    @Nullable
    private String schoolId;

    public SchoolDetailsViewModelFactory( @NonNull SchoolDetailsRepository schoolDetailsRepository, @Nullable String schooldId ) {
        this.schoolDetailsRepository = schoolDetailsRepository;

        this.schoolId = schooldId;
    }

    @NonNull
    public <T extends ViewModel> T create( Class<T> modelClass ) {
        if ( modelClass.isAssignableFrom( SchoolDetailsViewModel.class ) ) {
            return ( T ) new SchoolDetailsViewModel( schoolDetailsRepository, schoolId );
        }

        throw new IllegalArgumentException( "Unknown ViewModel class" );
    }

}
