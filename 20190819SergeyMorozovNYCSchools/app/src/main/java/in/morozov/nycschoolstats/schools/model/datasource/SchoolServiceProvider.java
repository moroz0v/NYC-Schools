package in.morozov.nycschoolstats.schools.model.datasource;

import androidx.annotation.NonNull;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/* TODO:
    - replace with dependency injection
    - remove static fields
    - add client token interceptor for more requests */
public final class SchoolServiceProvider {

    private static final String BASE_URL = "https://data.cityofnewyork.us/";

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor().setLevel( HttpLoggingInterceptor.Level.BODY );

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .addConverterFactory( GsonConverterFactory.create() )
                    .client( httpClient.build() )
                    .baseUrl( BASE_URL );

    private static Retrofit retrofit = builder.build();

    @NonNull
    public static <S> S createService( @NonNull Class<S> serviceClass ) {
        return retrofit.create( serviceClass );
    }

}
