package es.npatarino.android.gotchallenge.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import javax.inject.Inject

import es.npatarino.android.gotchallenge.GoTApplication
import es.npatarino.android.gotchallenge.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_list.*

class GoTCharacterListFragment : Fragment() {

    @Inject lateinit var presenter: HomePresenter
    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_list, container, false)
        (activity!!.application as GoTApplication).gotComponent.inject(this)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val goTAdapter = GoTAdapter()
        listFragmentRecyclerView.layoutManager = LinearLayoutManager(activity)
        listFragmentRecyclerView.setHasFixedSize(true)
        listFragmentRecyclerView.adapter = goTAdapter
        presenter.getCharacters()
                .subscribe(
                        { characters ->
                            goTAdapter.addAll(characters)
                            goTAdapter.notifyDataSetChanged()
                            listProgressBar.hide()
                        },
                        { Log.e(TAG, it.message, it) }
                )
                .addTo(disposables)
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "GoTCharacterListFragmen"
    }
}