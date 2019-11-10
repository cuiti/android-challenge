package es.npatarino.android.gotchallenge.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import es.npatarino.android.gotchallenge.GoTApplication;
import es.npatarino.android.gotchallenge.R;

public class GoTCharacterListFragment extends Fragment {

    @Inject HomePresenter presenter;

    private static final String TAG = "GoTCharacterListFragmen";

    public GoTCharacterListFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        final ContentLoadingProgressBar pb =  rootView.findViewById(R.id.pb);
        RecyclerView rv = rootView.findViewById(R.id.rv);
        ((GoTApplication)getActivity().getApplication()).gotComponent.inject(this);
        final HomeActivity.GoTAdapter goTAdapter = new HomeActivity.GoTAdapter(getActivity());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
        rv.setAdapter(goTAdapter);
        presenter.getCharacters()
                .subscribe(
                        characters -> {
                            goTAdapter.addAll(characters);
                            goTAdapter.notifyDataSetChanged();
                            pb.hide();
                        },
                        error -> Log.e(TAG, error.toString())
                );
        return rootView;
    }
}