package in.morozov.nycschoolstats.schools.model.datasource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import in.morozov.nycschoolstats.schools.viewmodel.datamodel.SchoolDetails;

public final class SchoolDetailMapperImpl {

    @NonNull
    public SchoolDetails map( @Nullable List<SchoolDetails> input ) {
        if ( input == null || input.isEmpty() ) {
            return new SchoolDetails();
        }

        //TODO: can find correct school by id but going to trust API in this case
        return input.get( 0 );
    }

}
