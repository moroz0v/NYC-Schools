package in.morozov.nycschoolstats.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DisplayError {

    @Retention( RetentionPolicy.SOURCE )
    @StringDef( {
            ERROR_NONE,
            ERROR_NETWORK,
            ERROR_INTERNAL
    } )
    public @interface ErrorType {}

    public static final String ERROR_NETWORK = "ERROR_NETWORK";

    public static final String ERROR_INTERNAL = "ERROR_INTERNAL";

    public static final String ERROR_NONE = "ERROR_NONE";

    @NonNull
    private @ErrorType
    String errorType;

    @Nullable
    private String errorMessage;

    public DisplayError( @NonNull String errorType ) {
        this.errorType = errorType;
    }

    public DisplayError( @NonNull String errorType, @Nullable String errorMessage ) {
        this.errorType = errorType;

        this.errorMessage = errorMessage;
    }

    @NonNull
    @ErrorType
    public String errorType() {
        return errorType;
    }

    @Nullable
    public String errorMessage() {
        return errorMessage;
    }

}
