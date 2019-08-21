package in.morozov.nycschoolstats.schools.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import in.morozov.nycschoolstats.schools.model.datasource.SchoolDetailMapperImpl;
import in.morozov.nycschoolstats.schools.model.datasource.SchoolRepositoryService;
import in.morozov.nycschoolstats.schools.model.datasource.SchoolServiceProvider;
import in.morozov.nycschoolstats.schools.viewmodel.datamodel.SchoolDetailRequest;
import in.morozov.nycschoolstats.schools.viewmodel.datamodel.SchoolDetails;
import in.morozov.nycschoolstats.schools.viewmodel.datamodel.SchoolDetailsResponse;
import in.morozov.nycschoolstats.schools.viewmodel.repo.SchoolDetailsRepository;
import in.morozov.nycschoolstats.utils.DisplayError;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchoolDetailsRepositoryImpl implements SchoolDetailsRepository {

    //TODO use dependency injection instead of creating objects in constructor
    @NonNull
    private SchoolRepositoryService schoolService;

    @Nullable
    private Call<List<SchoolDetails>> schools;

    //TODO: generalize
    @NonNull
    private SchoolDetailMapperImpl mapper;


    public SchoolDetailsRepositoryImpl() {
        this.schoolService = SchoolServiceProvider.createService( SchoolRepositoryService.class );

        this.mapper = new SchoolDetailMapperImpl();
    }

    @Override
    public LiveData<SchoolDetailsResponse> schoolDetails( @NonNull SchoolDetailRequest request ) {
        MutableLiveData<SchoolDetailsResponse> schoolListLiveData = new MutableLiveData<>();

        cancel();

        schools = schoolService.schoolDetails( request.getSchoolId() );

        //TODO: can abstract the callback
        schools.enqueue( new Callback<List<SchoolDetails>>() {
            @Override
            public void onResponse( @NonNull Call<List<SchoolDetails>> call, @NonNull Response<List<SchoolDetails>> response ) {
                if ( response.isSuccessful() && response.body() != null ) {
                    schoolListLiveData.setValue(
                            new SchoolDetailsResponse( mapper.map( response.body() ) ) );
                } else {
                    schoolListLiveData.setValue(
                            new SchoolDetailsResponse( new SchoolDetails(), new DisplayError( DisplayError.ERROR_NETWORK ) ) );
                }
            }

            @Override
            public void onFailure( @NonNull Call<List<SchoolDetails>> call, @NonNull Throwable t ) {
                schoolListLiveData.setValue(
                        new SchoolDetailsResponse(
                                new SchoolDetails(), new DisplayError( DisplayError.ERROR_NETWORK, t.getLocalizedMessage() ) ) );
            }
        } );

        return schoolListLiveData;
    }

    @Override
    public void cancel() {
        if ( schools != null ) {
            schools.cancel();

            schools = null;
        }
    }

}