package in.morozov.nycschoolstats.schools.viewmodel.repo;

import androidx.lifecycle.LiveData;

import in.morozov.nycschoolstats.schools.viewmodel.datamodel.SchoolsResponse;

public interface SchoolsRepository {

    LiveData<SchoolsResponse> schools();

    void cancel();

}
