package in.morozov.nycschoolstats.schools.viewmodel;

import android.os.Bundle;

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
import in.morozov.nycschoolstats.utils.Constants;
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


    public SchoolDetailsViewModel( @NonNull SchoolDetailsRepository repository ) {
        this.repository = repository;

        this.error.addSource( networkError, error::setValue );

        this.error.addSource( uiError, error::setValue );
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

    public void schoolDetails( @Nullable Bundle extras ) {
        String schoolId = null;

        if ( extras != null && extras.containsKey( Constants.EXTRA_SELECTED_SCHOOL_ID ) ) {
            schoolId = extras.getString( Constants.EXTRA_SELECTED_SCHOOL_ID );
        }

        if ( schoolId == null || schoolId.isEmpty() ) {
            uiError.setValue( new DisplayError( DisplayError.ERROR_INTERNAL ) );

            return;
        }

        loading.setValue( true );

        detailsRequest.setValue( new SchoolDetailRequest( schoolId ) );
    }

    @Override
    public void onCleared(){
        repository.cancel();
    }
}
