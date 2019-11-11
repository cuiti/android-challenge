package es.npatarino.android.gotchallenge.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE

import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding3.widget.textChanges

import javax.inject.Inject

import es.npatarino.android.gotchallenge.GoTApplication
import es.npatarino.android.gotchallenge.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity(), SearchView {
    @Inject lateinit var homePresenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        (application as GoTApplication).gotComponent.inject(this)
        setSupportActionBar(homeToolbar)
        homeViewPager.adapter = SectionsPagerAdapter(supportFragmentManager)
        homeTabLayout.setupWithViewPager(homeViewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        homeSearchInput.visibility = if (item.itemId == R.id.search) VISIBLE else GONE
        return true
    }

    override fun getSearchObservable() = homeSearchInput.textChanges()
}