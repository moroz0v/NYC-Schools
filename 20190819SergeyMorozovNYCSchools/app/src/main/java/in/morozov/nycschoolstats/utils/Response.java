package in.morozov.nycschoolstats.utils;

import androidx.annotation.NonNull;

public abstract class Response<T> {

    @NonNull
    public abstract DisplayError error();

    @NonNull
    public abstract T resource();

}
