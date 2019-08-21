package in.morozov.nycschoolstats.schools.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import in.morozov.nycschoolstats.schools.viewmodel.SchoolDetailsViewModel;
import in.morozov.nycschoolstats.schools.viewmodel.repo.SchoolDetailsRepository;

public class SchoolDetailsViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    private final SchoolDetailsRepository schoolDetailsRepository;

    public SchoolDetailsViewModelFactory( @NonNull SchoolDetailsRepository schoolDetailsRepository ) {
        this.schoolDetailsRepository = schoolDetailsRepository;
    }

    @NonNull
    public <T extends ViewModel> T create( Class<T> modelClass ) {
        if ( modelClass.isAssignableFrom( SchoolDetailsViewModel.class ) ) {
            return ( T ) new SchoolDetailsViewModel( schoolDetailsRepository );
        }

        throw new IllegalArgumentException( "Unknown ViewModel class" );
    }

}
