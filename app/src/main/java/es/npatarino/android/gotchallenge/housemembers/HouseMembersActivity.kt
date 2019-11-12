package es.npatarino.android.gotchallenge.housemembers

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import es.npatarino.android.gotchallenge.GoTApplication
import es.npatarino.android.gotchallenge.R
import es.npatarino.android.gotchallenge.home.GoTAdapter
import es.npatarino.android.gotchallenge.model.House
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

import kotlinx.android.synthetic.main.activity_house_list.*
import javax.inject.Inject

class HouseMembersActivity : AppCompatActivity() {

    @Inject lateinit var presenter: HouseMembersPresenter
    private val charactersAdapter = GoTAdapter()
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as GoTApplication).gotComponent.inject(this)
        setContentView(R.layout.activity_house_list)
        setSupportActionBar(houseListToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setUpList()

        intent.extras?.getSerializable("house")?.let {
            showHouseData(it as House)
        }

    }

    private fun showHouseData(house: House){
        supportActionBar?.title = house.name
        presenter.getCharactersByHouse(house)
                .subscribe(
                        { charactersAdapter.replace(it) },
                        { Log.e(TAG, it.message, it)}
                )
                .addTo(disposables)
    }

    private fun setUpList(){
        houseListRecyclerView.layoutManager = LinearLayoutManager(this)
        houseListRecyclerView.setHasFixedSize(true)
        houseListRecyclerView.adapter = charactersAdapter
    }

    companion object{
        const val TAG = "HouseMembersActivity"
    }
}