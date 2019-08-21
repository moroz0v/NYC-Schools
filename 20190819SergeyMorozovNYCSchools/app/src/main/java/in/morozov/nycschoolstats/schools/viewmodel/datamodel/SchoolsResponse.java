package in.morozov.nycschoolstats.schools.viewmodel.datamodel;

import androidx.annotation.NonNull;

import java.util.List;

import in.morozov.nycschoolstats.utils.DisplayError;
import in.morozov.nycschoolstats.utils.Response;

public class SchoolsResponse extends Response<List<School>> {

    @NonNull
    private List<School> schools;

    @NonNull
    private DisplayError error = new DisplayError( DisplayError.ERROR_NONE );

    public SchoolsResponse( @NonNull List<School> schools ) {
        this.schools = schools;
    }

    public SchoolsResponse( @NonNull List<School> schools, @NonNull DisplayError error ) {
        this( schools );

        this.error = error;
    }

    @NonNull
    @Override
    public DisplayError error() {
        return error;
    }

    @NonNull
    @Override
    public List<School> resource() {
        return schools;
    }

}
