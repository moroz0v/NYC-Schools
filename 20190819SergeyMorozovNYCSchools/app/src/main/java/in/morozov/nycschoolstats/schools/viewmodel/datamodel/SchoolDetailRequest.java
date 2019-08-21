package in.morozov.nycschoolstats.schools.viewmodel.datamodel;

import androidx.annotation.NonNull;

public class SchoolDetailRequest {

    @NonNull
    private String schoolId;

    public SchoolDetailRequest( @NonNull String schoolId ) {
        this.schoolId = schoolId;
    }

    @NonNull
    public String getSchoolId() {
        return schoolId;
    }

}
