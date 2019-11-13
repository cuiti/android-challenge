package es.npatarino.android.gotchallenge.home

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import es.npatarino.android.gotchallenge.home.characters.GoTCharacterListFragment
import es.npatarino.android.gotchallenge.home.houses.GoTHousesListFragment

class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = if (position == 0)
        GoTCharacterListFragment()
                                         else
        GoTHousesListFragment()

    override fun getCount() = 2

    override fun getPageTitle(position: Int) = when (position) {
        0 -> "Characters"
        1 -> "Houses"
        else -> null
    }
}