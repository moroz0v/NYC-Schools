package in.morozov.nycschoolstats.schools.viewmodel.datamodel;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class School {

    @Nullable
    @SerializedName( "dbn" )
    private String schoolId;

    @Nullable
    @SerializedName( "school_name" )
    private String schoolName;

    @Nullable
    public String id() {
        return schoolId;
    }

    @Nullable
    public String schoolName() {
        return schoolName;
    }

}
