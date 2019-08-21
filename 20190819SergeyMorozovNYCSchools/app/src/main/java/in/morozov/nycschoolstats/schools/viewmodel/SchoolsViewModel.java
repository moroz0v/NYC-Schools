package in.morozov.nycschoolstats.schools.viewmodel;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import in.morozov.nycschoolstats.schools.viewmodel.datamodel.School;
import in.morozov.nycschoolstats.schools.viewmodel.datamodel.SchoolsRequest;
import in.morozov.nycschoolstats.schools.viewmodel.datamodel.SchoolsResponse;
import in.morozov.nycschoolstats.schools.viewmodel.repo.SchoolsRepository;
import in.morozov.nycschoolstats.utils.DisplayError;

public class SchoolsViewModel extends ViewModel {

    @NonNull
    private final SchoolsRepository repository;

    @NonNull
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    @NonNull
    private final MutableLiveData<School> selected = new MutableLiveData<>();

    @NonNull
    private final MutableLiveData<SchoolsRequest> schoolsRequest = new MutableLiveData<>();

    @NonNull
    private final LiveData<SchoolsResponse> schoolsResponse =
            Transformations.switchMap( schoolsRequest, new Function<SchoolsRequest, LiveData<SchoolsResponse>>() {
                @Override
                public LiveData<SchoolsResponse> apply( SchoolsRequest input ) {
                    loading.setValue( false );

                    return repository.schools();
                }
            } );

    @NonNull
    private final LiveData<List<School>> schools =
            Transformations.map( schoolsResponse, SchoolsResponse::resource );

    @NonNull
    private final LiveData<DisplayError> networkError =
            Transformations.map( schoolsResponse, SchoolsResponse::error );

    public SchoolsViewModel( @NonNull SchoolsRepository repository ) {
        this.repository = repository;

        // only loading data from the constructor lets us leverage VM's ability to keep state
        // through rotations. Not ideal but will work for now
        this.loadSchools();
    }

    //TODO: should paginate here - repo supports it
    @NonNull
    public LiveData<List<School>> schools() {
        return schools;
    }

    @NonNull
    public LiveData<School> schoolSelected() {
        return selected;
    }

    @NonNull
    public LiveData<DisplayError> error() {
        return networkError;
    }

    @Override
    public void onCleared() {
        repository.cancel();
    }

    public LiveData<Boolean> loading() {
        return loading;
    }

    public void loadSchools() {
        loading.setValue( true );

        schoolsRequest.setValue( new SchoolsRequest() );
    }

    public void schoolSelected( @NonNull School school ) {
        selected.setValue( school );
    }

}
