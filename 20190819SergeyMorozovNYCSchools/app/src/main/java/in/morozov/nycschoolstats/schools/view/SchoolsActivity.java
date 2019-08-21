package in.morozov.nycschoolstats.schools.view;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import in.morozov.nycschoolstats.R;
import in.morozov.nycschoolstats.databinding.SchoolsActivityBinding;
import in.morozov.nycschoolstats.schools.model.SchoolsRepositoryImpl;
import in.morozov.nycschoolstats.schools.viewmodel.SchoolsViewModel;
import in.morozov.nycschoolstats.schools.viewmodel.factory.SchoolsViewModelFactory;
import in.morozov.nycschoolstats.utils.Constants;
import in.morozov.nycschoolstats.utils.DisplayError;

public class SchoolsActivity extends AppCompatActivity {

    // TODO: use dependency injection and a provider to ensure we aren't creating a new factory
    // every time system instantiates this activity
    private SchoolsViewModelFactory viewModelFactory = new SchoolsViewModelFactory( new SchoolsRepositoryImpl() );

    private SchoolsAdapter adapter;

    private SchoolsActivityBinding binding;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        binding = DataBindingUtil.setContentView( this, R.layout.schools_activity );

        SchoolsViewModel viewModel = ViewModelProviders.of( this, viewModelFactory ).get( SchoolsViewModel.class );

        adapter = new SchoolsAdapter( viewModel );

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( this );

        linearLayoutManager.setOrientation( RecyclerView.VERTICAL );

        binding.schools.setLayoutManager( linearLayoutManager );

        binding.schools.setAdapter( adapter );

        viewModel.schools().observe( this, schools -> adapter.schools( schools ) );

        viewModel.schoolSelected().observe( this, school -> {
            Intent detailsIntent = new Intent( this, SchoolDetailsActivity.class );

            detailsIntent.putExtra( Constants.EXTRA_SELECTED_SCHOOL_ID, school.id() );

            startActivity( detailsIntent );
        } );

        viewModel.error().observe( this, this::error );

        viewModel.loading().observe( this, this::showLoading );
    }

    private void showLoading( boolean loading ) {
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
