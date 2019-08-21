package in.morozov.nycschoolstats.schools.viewmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import in.morozov.nycschoolstats.schools.viewmodel.datamodel.SchoolDetailRequest;
import in.morozov.nycschoolstats.schools.viewmodel.datamodel.SchoolDetails;
import in.morozov.nycschoolstats.schools.viewmodel.datamodel.SchoolDetailsResponse;
import in.morozov.nycschoolstats.schools.viewmodel.repo.SchoolDetailsRepository;
import in.morozov.nycschoolstats.utils.DisplayError;

public class SchoolDetailsViewModel extends ViewModel {

    @NonNull
    private SchoolDetailsRepository repository;

    @NonNull
    private final MutableLiveData<SchoolDetailRequest> detailsRequest = new MutableLiveData<>();

    @NonNull
    private final MutableLiveData<DisplayError> uiError = new MutableLiveData<>();

    @NonNull
    private final MediatorLiveData<DisplayError> error = new MediatorLiveData<>();

    @NonNull
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    @NonNull
    private final LiveData<SchoolDetailsResponse> detailsResponse =
            Transformations.switchMap( detailsRequest, new Function<SchoolDetailRequest, LiveData<SchoolDetailsResponse>>() {
                @Override
                public LiveData<SchoolDetailsResponse> apply( @NonNull SchoolDetailRequest input ) {
                    return repository.schoolDetails( input );
                }
            } );

    @NonNull
    private final LiveData<SchoolDetails> details =
            Transformations.map( detailsResponse, input -> {
                //not idea to cancel loading here
                loading.setValue( false );

                return input.resource();
            } );

    @NonNull
    private final LiveData<DisplayError> networkError =
            Transformations.map( detailsResponse, SchoolDetailsResponse::error );


    public SchoolDetailsViewModel( @NonNull SchoolDetailsRepository repository, @Nullable String schoolId ) {
        this.repository = repository;

        init( schoolId );
    }

    private void init( @Nullable String schoolId ){
        this.error.addSource( networkError, error::setValue );

        this.error.addSource( uiError, error::setValue );

        if( schoolId != null && !schoolId.isEmpty() ) {
            // only loading data from the constructor lets us leverage VM's ability to keep state
            // through rotations. Not ideal but will work for now
            this.loadSchoolDetails( schoolId );
        } else {
            uiError.setValue( new DisplayError( DisplayError.ERROR_INTERNAL ) );
        }
    }

    @NonNull
    public LiveData<SchoolDetails> schoolDetails() {
        return details;
    }

    @NonNull
    public LiveData<DisplayError> error() {
        return error;
    }

    @NonNull
    public LiveData<Boolean> loading() {
        return loading;
    }

    private void loadSchoolDetails( @NonNull String schoolId ){
        loading.setValue( true );

        detailsRequest.setValue( new SchoolDetailRequest( schoolId ) );
    }

    @Override
    public void onCleared(){
        repository.cancel();
    }
}
