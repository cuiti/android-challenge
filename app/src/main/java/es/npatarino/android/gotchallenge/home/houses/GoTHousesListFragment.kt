package es.npatarino.android.gotchallenge.home.houses

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import es.npatarino.android.gotchallenge.GoTApplication

import es.npatarino.android.gotchallenge.R
import es.npatarino.android.gotchallenge.home.HomePresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class GoTHousesListFragment : Fragment() {

    @Inject lateinit var presenter: HomePresenter
    private val disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity!!.application as GoTApplication).gotComponent.inject(this)
        val adapter = HousesAdapter()
        listFragmentRecyclerView.layoutManager = LinearLayoutManager(activity)
        listFragmentRecyclerView.setHasFixedSize(true)
        listFragmentRecyclerView.adapter = adapter

        presenter.getHouses()
                .subscribe(
                        {
                            adapter.addAll(it)
                            listProgressBar.hide()
                        },
                        { Log.e(TAG, it.message, it) })
                .addTo(disposables)
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "GoTHousesListFragment"
    }
}