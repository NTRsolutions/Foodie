package com.belac.ines.foodie.helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.belac.ines.foodie.wishlist.WishlistMenuFragment;
import com.belac.ines.foodie.wishlist.WishlistRestoranFragment;
import com.belac.ines.foodie.registration.RestoranFragment;
import com.belac.ines.foodie.registration.UserFragment;

/**
 * Created by Ines on 13.11.2017..
 */

public class PagerAdapter extends FragmentStatePagerAdapter{

    int numPages;
    String action;

    public PagerAdapter(FragmentManager fm, int numPages, String action) {
        super(fm);
        this.numPages = numPages;
        this.action = action;
    }

    @Override
    public Fragment getItem(int position) {

        if(action.equalsIgnoreCase("registration")){
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
        }else if(action.equalsIgnoreCase("wishlist")){
            switch (position){
                case 0:
                    WishlistRestoranFragment tab1 = new WishlistRestoranFragment();
                    return tab1;
                case 1:
                    WishlistMenuFragment tab2 = new WishlistMenuFragment();
                    return tab2;
                default:
                    return null;
            }
        }else{ return null; }
    }

    @Override
    public int getCount() {
        return numPages;
    }
}
