package es.npatarino.android.gotchallenge.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import es.npatarino.android.gotchallenge.DetailActivity;
import es.npatarino.android.gotchallenge.GoTApplication;
import es.npatarino.android.gotchallenge.GoTCharacter;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.extensions.ImageViewKt;
import es.npatarino.android.gotchallenge.model.Character;
import io.reactivex.disposables.CompositeDisposable;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class HomeActivity extends AppCompatActivity {

    SectionsPagerAdapter spa;
    ViewPager vp;
    Toolbar toolbar;
    TabLayout tabLayout;
    @Inject HomePresenter homePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ((GoTApplication)getApplication()).gotComponent.inject(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSpa(new SectionsPagerAdapter(getSupportFragmentManager()));

        setVp((ViewPager) findViewById(R.id.container));
        getVp().setAdapter(getSpa());

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(getVp());
    }

    public SectionsPagerAdapter getSpa() {
        return spa;
    }

    public void setSpa(SectionsPagerAdapter spa) {
        this.spa = spa;
    }

    public ViewPager getVp() {
        return vp;
    }

    public void setVp(ViewPager vp) {
        this.vp = vp;
    }

    public static class GoTCharacterListFragment extends Fragment {

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
            final GoTAdapter goTAdapter = new GoTAdapter(getActivity());
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

    public static class GoTHousesListFragment extends Fragment {

        private static final String TAG = "GoTHousesListFragment";

        public GoTHousesListFragment() {
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_list, container, false);
            final ContentLoadingProgressBar pb = (ContentLoadingProgressBar) rootView.findViewById(R.id.pb);
            RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv);

            final GoTHouseAdapter adp = new GoTHouseAdapter(getActivity());
            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            rv.setHasFixedSize(true);
            rv.setAdapter(adp);

            new Thread(new Runnable() {

                @Override
                public void run() {
                    String url = "https://project-8424324399725905479.firebaseio.com/characters.json?print=pretty";

                    URL obj = null;
                    try {
                        obj = new URL(url);
                        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                        con.setRequestMethod("GET");
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();
                        Type listType = new TypeToken<ArrayList<Character>>() {
                        }.getType();
                        final List<Character> characters = new Gson().fromJson(response.toString(), listType);
                        GoTHousesListFragment.this.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<GoTCharacter.GoTHouse> houses = new ArrayList<>();
                                for (int i = 0; i < characters.size(); i++) {
                                    boolean b = false;
                                    for (int j = 0; j < houses.size(); j++) {
                                        if (houses.get(j).getHouseName().equalsIgnoreCase(characters.get(i).getHouseName())) {
                                            b = true;
                                        }
                                    }
                                    if (!b) {
                                        if (characters.get(i).getHouseId() != null && !characters.get(i).getHouseId().isEmpty()) {
                                            GoTCharacter.GoTHouse h = new GoTCharacter.GoTHouse();
                                            h.setHouseId(characters.get(i).getHouseId());
                                            h.setHouseName(characters.get(i).getHouseName());
                                            h.setHouseImageUrl(characters.get(i).getHouseImageUrl());
                                            houses.add(h);
                                            b = false;
                                        }
                                    }
                                }
                                adp.addAll(houses);
                                adp.notifyDataSetChanged();
                                pb.hide();
                            }
                        });
                    } catch (IOException e) {
                        Log.e(TAG, e.getLocalizedMessage());
                    }
                }
            }).start();
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new GoTCharacterListFragment();
            } else {
                return new GoTHousesListFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Characters";
                case 1:
                    return "Houses";
            }
            return null;
        }
    }

    static class GoTAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final List<Character> characters;
        private Activity a;

        public GoTAdapter(Activity activity) {
            this.characters = new ArrayList<>();
            a = activity;
        }

        void addAll(Collection<Character> collection) {
            for (int i = 0; i < collection.size(); i++) {
                characters.add((Character) collection.toArray()[i]);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GotCharacterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.got_character_row, parent, false));
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            GotCharacterViewHolder gotCharacterViewHolder = (GotCharacterViewHolder) holder;
            gotCharacterViewHolder.render(characters.get(position));
            ((GotCharacterViewHolder) holder).characterImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    Intent intent = new Intent(((GotCharacterViewHolder) holder).itemView.getContext(), DetailActivity.class);
                    intent.putExtra("description", characters.get(position).getDescription());
                    intent.putExtra("name", characters.get(position).getName());
                    intent.putExtra("imageUrl", characters.get(position).getImageUrl());
                    ((GotCharacterViewHolder) holder).itemView.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return characters.size();
        }

        class GotCharacterViewHolder extends RecyclerView.ViewHolder {

            private static final String TAG = "GotCharacterViewHolder";
            ImageView characterImageView;
            TextView nameTextView;

            public GotCharacterViewHolder(View itemView) {
                super(itemView);
                characterImageView = itemView.findViewById(R.id.characterItemImage);
                nameTextView = itemView.findViewById(R.id.characterItemName);
            }

            public void render(final Character goTCharacter) {
                ImageViewKt.loadUrl(characterImageView, goTCharacter.getImageUrl());
                nameTextView.setText(goTCharacter.getName());
            }
        }

    }

    static class GoTHouseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final List<GoTCharacter.GoTHouse> gcs;
        private Activity a;

        public GoTHouseAdapter(Activity activity) {
            this.gcs = new ArrayList<>();
            a = activity;
        }

        void addAll(Collection<GoTCharacter.GoTHouse> collection) {
            for (int i = 0; i < collection.size(); i++) {
                gcs.add((GoTCharacter.GoTHouse) collection.toArray()[i]);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GotCharacterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.got_house_row, parent, false));
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            GotCharacterViewHolder gotCharacterViewHolder = (GotCharacterViewHolder) holder;
            gotCharacterViewHolder.render(gcs.get(position));
        }

        @Override
        public int getItemCount() {
            return gcs.size();
        }

        class GotCharacterViewHolder extends RecyclerView.ViewHolder {

            private static final String TAG = "GotCharacterViewHolder";
            ImageView imp;
            TextView tvn;

            public GotCharacterViewHolder(View itemView) {
                super(itemView);
                imp = itemView.findViewById(R.id.houseItemImage);
                tvn = itemView.findViewById(R.id.tv_name);
            }

            public void render(final GoTCharacter.GoTHouse goTHouse) {
                tvn.setText(goTHouse.getHouseName());
                ImageViewKt.loadUrl(imp, goTHouse.getHouseImageUrl());
            }
        }

    }
}
