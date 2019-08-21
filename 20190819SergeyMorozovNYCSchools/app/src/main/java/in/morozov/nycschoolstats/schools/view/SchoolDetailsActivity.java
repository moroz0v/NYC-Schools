package in.morozov.nycschoolstats.schools.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;

import in.morozov.nycschoolstats.R;
import in.morozov.nycschoolstats.databinding.SchoolDetailsActivityBinding;
import in.morozov.nycschoolstats.schools.model.SchoolDetailsRepositoryImpl;
import in.morozov.nycschoolstats.schools.viewmodel.SchoolDetailsViewModel;
import in.morozov.nycschoolstats.schools.viewmodel.factory.SchoolDetailsViewModelFactory;
import in.morozov.nycschoolstats.utils.DisplayError;

public class SchoolDetailsActivity extends AppCompatActivity {

    //TODO: use dependency injection
    private SchoolDetailsViewModelFactory viewModelFactory = new SchoolDetailsViewModelFactory( new SchoolDetailsRepositoryImpl() );

    private SchoolDetailsActivityBinding binding;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        binding = DataBindingUtil.setContentView( this, R.layout.school_details_activity );

        SchoolDetailsViewModel viewModel = ViewModelProviders.of( this, viewModelFactory ).get( SchoolDetailsViewModel.class );

        viewModel.schoolDetails().observe(
                this,
                schoolDetails -> {
                    if ( schoolDetails.getSchoolName() == null ) {
                        //TODO: should go thorough VM
                        error( new DisplayError( DisplayError.ERROR_NETWORK, getString( R.string.error_school_not_found ) ) );
                    } else {
                        binding.setDetails( schoolDetails );
                    }
                }
        );

        viewModel.error().observe( this, this::error );

        viewModel.loading().observe( this, this::loading );

        viewModel.schoolDetails( getIntent() != null ? getIntent().getExtras() : null );
    }

    private void loading( Boolean loading ) {
        binding.setLoading( loading );
    }

    //TODO: consolidate error handling in UI package
    private void error( @NonNull DisplayError error ) {
        if ( !error.errorType().equalsIgnoreCase( DisplayError.ERROR_NONE ) ) {
            Snackbar.make( binding.getRoot(),
                           error.errorMessage() != null ? error.errorMessage() : getString( R.string.error_general ), Snackbar.LENGTH_LONG )
                    .show();
        }
    }

}
