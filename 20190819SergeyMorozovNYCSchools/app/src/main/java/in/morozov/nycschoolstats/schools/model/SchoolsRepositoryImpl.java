
package in.morozov.nycschoolstats.schools.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import in.morozov.nycschoolstats.schools.model.datasource.SchoolRepositoryService;
import in.morozov.nycschoolstats.schools.model.datasource.SchoolServiceProvider;
import in.morozov.nycschoolstats.schools.viewmodel.datamodel.School;
import in.morozov.nycschoolstats.schools.viewmodel.datamodel.SchoolsResponse;
import in.morozov.nycschoolstats.schools.viewmodel.repo.SchoolsRepository;
import in.morozov.nycschoolstats.utils.DisplayError;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchoolsRepositoryImpl implements SchoolsRepository {
    //TODO use dependency injection to pass this in instead of creating objects in constructor

    @NonNull
    private SchoolRepositoryService schoolService;

    @Nullable
    private Call<List<School>> schools;


    public SchoolsRepositoryImpl() {
        this.schoolService = SchoolServiceProvider.createService( SchoolRepositoryService.class );
    }

    @Override
    public LiveData<SchoolsResponse> schools() {
        MutableLiveData<SchoolsResponse> schoolDetailsLiveData = new MutableLiveData<>();

        cancel();

        schools = schoolService.schools();

        //TODO: can abstract the callback
        schools.enqueue( new Callback<List<School>>() {
            @Override
            public void onResponse( @NonNull Call<List<School>> call, @NonNull Response<List<School>> response ) {
                if( response.isSuccessful() && response.body() != null ){
                    schoolDetailsLiveData.setValue( new SchoolsResponse( response.body() ) );

                    Log.e("SchoolsRepositoryImpl"," made a networking call");

                    return;
                }

                schoolDetailsLiveData.setValue(
                        new SchoolsResponse( new ArrayList<>(  ), new DisplayError( DisplayError.ERROR_NETWORK ) ) );
            }

            @Override
            public void onFailure( @NonNull Call<List<School>> call, @NonNull Throwable t ) {
                schoolDetailsLiveData.setValue(
                        new SchoolsResponse(
                                new ArrayList<>(),
                                new DisplayError( DisplayError.ERROR_NETWORK, t.getLocalizedMessage() ) ) );
            }
        } );

        return schoolDetailsLiveData;
    }

    @Override
    public void cancel(){
        if ( schools != null ) {
            schools.cancel();

            schools = null;
        }
    }
}
