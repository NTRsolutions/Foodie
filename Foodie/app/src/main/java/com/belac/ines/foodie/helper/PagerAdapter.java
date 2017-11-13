package com.belac.ines.foodie.helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.belac.ines.foodie.registration.RestoranFragment;
import com.belac.ines.foodie.registration.UserFragment;

/**
 * Created by Ines on 13.11.2017..
 */

public class PagerAdapter extends FragmentStatePagerAdapter{

    int numPages;

    public PagerAdapter(FragmentManager fm, int numPages) {
        super(fm);
        this.numPages = numPages;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                UserFragment tab1 = new UserFragment();
                return tab1;
            case 1:
                RestoranFragment tab2 = new RestoranFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numPages;
    }
}
