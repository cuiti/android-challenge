package es.npatarino.android.gotchallenge.home

import android.os.Bundle

import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity

import javax.inject.Inject

import es.npatarino.android.gotchallenge.GoTApplication
import es.npatarino.android.gotchallenge.R
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {
    @Inject lateinit var homePresenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        (application as GoTApplication).gotComponent.inject(this)
        setSupportActionBar(homeToolbar)
        homeViewPager.adapter = SectionsPagerAdapter(supportFragmentManager)
        homeTabLayout.setupWithViewPager(homeViewPager)
    }
}