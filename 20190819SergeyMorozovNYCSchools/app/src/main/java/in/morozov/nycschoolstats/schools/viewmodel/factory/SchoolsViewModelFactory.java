package in.morozov.nycschoolstats.schools.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import in.morozov.nycschoolstats.schools.viewmodel.SchoolsViewModel;
import in.morozov.nycschoolstats.schools.viewmodel.repo.SchoolsRepository;

public class SchoolsViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    private final SchoolsRepository schoolDetailsRepository;

    public SchoolsViewModelFactory( @NonNull SchoolsRepository schoolDetailsRepository ) {
        this.schoolDetailsRepository = schoolDetailsRepository;
    }

    @NonNull
    public <T extends ViewModel> T create( Class<T> modelClass ) {
        if ( modelClass.isAssignableFrom( SchoolsViewModel.class ) ) {
            return ( T ) new SchoolsViewModel( schoolDetailsRepository );
        }

        throw new IllegalArgumentException( "Unknown ViewModel class" );
    }

}