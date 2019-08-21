package in.morozov.nycschoolstats.schools.viewmodel.repo;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import in.morozov.nycschoolstats.schools.viewmodel.datamodel.SchoolDetailRequest;
import in.morozov.nycschoolstats.schools.viewmodel.datamodel.SchoolDetailsResponse;

public interface SchoolDetailsRepository {

    LiveData<SchoolDetailsResponse> schoolDetails( @NonNull SchoolDetailRequest request );

    void cancel();

}
