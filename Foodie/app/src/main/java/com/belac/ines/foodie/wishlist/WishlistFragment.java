package com.belac.ines.foodie.wishlist;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.belac.ines.foodie.R;
import com.belac.ines.foodie.classes.Restoran;
import com.belac.ines.foodie.helper.PagerAdapter;
import com.belac.ines.foodie.helper.WishlistAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ines on 17.1.2018..
 */

public class WishlistFragment extends Fragment{

        public static final int CONNECTION_TIMEOUT=10000;
        public static final int READ_TIMEOUT=15000;

        private List<Restoran> restoranList = new ArrayList<>();
        private WishlistAdapter wAdapter;
        private EditText search;
        private FrameLayout frameLayout;

        public WishlistFragment() {}

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
            frameLayout = (FrameLayout) view.findViewById(R.id.frame_layout);
            wAdapter = new WishlistAdapter(restoranList, getContext());

            TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
            tabLayout.addTab(tabLayout.newTab().setText("RESTORAN"));
            tabLayout.addTab(tabLayout.newTab().setText("MENU"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
            final PagerAdapter pagerAdapter = new PagerAdapter(
                    getFragmentManager(), tabLayout.getTabCount());
            viewPager.setAdapter(pagerAdapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) { }

                @Override
                public void onTabReselected(TabLayout.Tab tab) { }
            });
            return view;
        }
}
