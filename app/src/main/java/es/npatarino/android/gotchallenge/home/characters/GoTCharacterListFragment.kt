package es.npatarino.android.gotchallenge.home.characters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import javax.inject.Inject

import es.npatarino.android.gotchallenge.GoTApplication
import es.npatarino.android.gotchallenge.R
import es.npatarino.android.gotchallenge.extensions.gone
import es.npatarino.android.gotchallenge.extensions.show
import es.npatarino.android.gotchallenge.extensions.visible
import es.npatarino.android.gotchallenge.home.HomePresenter
import es.npatarino.android.gotchallenge.home.SearchView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_list.*

class GoTCharacterListFragment : Fragment() {

    @Inject lateinit var presenter: HomePresenter
    private val disposables: CompositeDisposable = CompositeDisposable()
    private val charactersAdapter = CharactersAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_list, container, false)
        (activity!!.application as GoTApplication).gotComponent.inject(this)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpList()
        handleSearch()
        fetchCharacterList()
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

    private fun fetchCharacterList(){
        presenter.getCharacters()
                .doAfterTerminate { listProgressBar.hide() }
                .subscribe(
                        {
                            listFragmentRecyclerView.scheduleLayoutAnimation()
                            charactersAdapter.addAll(it)
                        },
                        { showErrorState() }
                )
                .addTo(disposables)
    }

    private fun handleSearch(){
        (activity as? SearchView)?.run {
            presenter.observeSearch(this)
                    .doOnNext { listFragmentNoResultsText.show(it.isEmpty()) }
                    .subscribe(
                            { charactersAdapter.replace(it) },
                            { Log.e(TAG, it.message, it) })
                    .addTo(disposables)
        }
    }

    private fun setUpList() {
        listFragmentRecyclerView.layoutManager = LinearLayoutManager(activity)
        listFragmentRecyclerView.setHasFixedSize(true)
        listFragmentRecyclerView.adapter = charactersAdapter
    }

    private fun showErrorState(){
        listFragmentErrorText.visible()
        listFragmentErrorText.setOnClickListener {
            it.gone()
            listProgressBar.show()
            fetchCharacterList()
        }
    }

    companion object {
        private const val TAG = "GoTCharacterListFragmen"
    }
}