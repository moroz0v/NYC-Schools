package in.morozov.nycschoolstats.schools.viewmodel.datamodel;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class SchoolDetails {

    @Nullable
    @SerializedName( "dbn" )
    private String schoolId;

    @Nullable
    @SerializedName( "school_name" )
    private String schoolName;

    @SerializedName( "sat_critical_reading_avg_score" )
    private int satReadingScore;

    @SerializedName( "sat_writing_avg_score" )
    private int satWritingScore;

    @SerializedName( "sat_math_avg_score" )
    private int satMathScore;

    @Nullable
    public String getSchoolId() {
        return schoolId;
    }

    @Nullable
    public String getSchoolName() {
        return schoolName;
    }

    public int getSatReadingScore() {
        return satReadingScore;
    }

    public int getSatWritingScore() {
        return satWritingScore;
    }

    public int getSatMathScore() {
        return satMathScore;
    }

}
