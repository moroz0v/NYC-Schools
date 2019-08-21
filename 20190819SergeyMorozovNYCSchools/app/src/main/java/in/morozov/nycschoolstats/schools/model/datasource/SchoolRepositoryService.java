package in.morozov.nycschoolstats.schools.model.datasource;

import androidx.annotation.NonNull;

import java.util.List;

import in.morozov.nycschoolstats.schools.viewmodel.datamodel.School;
import in.morozov.nycschoolstats.schools.viewmodel.datamodel.SchoolDetails;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SchoolRepositoryService {

    @NonNull
    @GET("resource/s3k6-pzi2.json")
    Call<List<School>> schools();

    @NonNull
    @GET("resource/f9bf-2cp4.json")
    Call<List<SchoolDetails>> schoolDetails( @Query("dbn") @NonNull String schoolId );

}
