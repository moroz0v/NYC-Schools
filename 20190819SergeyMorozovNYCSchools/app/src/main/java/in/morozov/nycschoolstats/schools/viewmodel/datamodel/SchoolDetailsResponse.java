package in.morozov.nycschoolstats.schools.viewmodel.datamodel;

import androidx.annotation.NonNull;

import in.morozov.nycschoolstats.utils.DisplayError;
import in.morozov.nycschoolstats.utils.Response;

public class SchoolDetailsResponse extends Response<SchoolDetails> {

    @NonNull
    private SchoolDetails schoolDetails;

    @NonNull
    private DisplayError displayError = new DisplayError( DisplayError.ERROR_NONE );

    public SchoolDetailsResponse( @NonNull SchoolDetails schoolDetails ) {
        this.schoolDetails = schoolDetails;
    }

    public SchoolDetailsResponse( @NonNull SchoolDetails schoolDetails, @NonNull DisplayError displayError ) {
        this( schoolDetails );

        this.displayError = displayError;
    }

    @NonNull
    @Override
    public DisplayError error() {
        return displayError;
    }

    @NonNull
    @Override
    public SchoolDetails resource() {
        return schoolDetails;
    }

}
