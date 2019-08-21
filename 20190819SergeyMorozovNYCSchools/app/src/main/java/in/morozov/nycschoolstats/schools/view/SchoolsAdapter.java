package in.morozov.nycschoolstats.schools.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.morozov.nycschoolstats.databinding.SchoolsCellBinding;
import in.morozov.nycschoolstats.schools.viewmodel.SchoolsViewModel;
import in.morozov.nycschoolstats.schools.viewmodel.datamodel.School;
import in.morozov.nycschoolstats.utils.ViewHolder;

public class SchoolsAdapter extends RecyclerView.Adapter<SchoolsAdapter.SchoolViewHolder> {

    @NonNull
    private SchoolsViewModel viewModel;

    @Nullable
    private List<School> schools;

    SchoolsAdapter( @NonNull SchoolsViewModel viewModel ) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public SchoolViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        LayoutInflater layoutInflater = LayoutInflater.from( parent.getContext() );

        SchoolsCellBinding itemBinding = SchoolsCellBinding.inflate( layoutInflater, parent, false );

        return new SchoolViewHolder( viewModel, itemBinding );
    }

    @Override
    public void onBindViewHolder( @NonNull SchoolViewHolder holder, int position ) {
        School school = schools().get( position );

        holder.bind( school );
    }

    @Override
    public int getItemCount() {
        return schools().size();
    }

    public void schools( @NonNull List<School> schools ) {
        List<School> currentSchools = schools();

        currentSchools.clear();

        currentSchools.addAll( schools );

        notifyDataSetChanged();
    }

    @NonNull
    private List<School> schools() {
        if ( schools == null ) {
            schools = new ArrayList<>();
        }

        return schools;
    }

    public static class SchoolViewHolder extends ViewHolder<School> {

        @NonNull
        private SchoolsCellBinding binding;

        SchoolViewHolder( @NonNull SchoolsViewModel viewModel, @NonNull SchoolsCellBinding binding ) {
            super( binding.getRoot() );

            this.binding = binding;

            this.binding.setViewModel( viewModel );
        }

        public void bind( @NonNull School model ) {
            binding.setSchool( model );
        }

    }

}
