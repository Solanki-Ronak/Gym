package com.example.week7assignment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new Registration();
        } else {
            return new Summary();
        }
    }

    @Override
    public int getCount() {
        return 2; // Return 2 instead of 3
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Start";
        } else {
            return "Summary";
        }
    }
}
