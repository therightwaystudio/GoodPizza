package com.example.romanovsky_m.testproject.Utils;

import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.romanovsky_m.testproject.Fragments.PopularFragment;
import com.example.romanovsky_m.testproject.R;


public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private static final Section[] SECTIONS = {
            new Section("Пицца", R.drawable.r1),
            new Section("Лапша", R.drawable.r3),
            new Section("Суши", R.drawable.r5),
            new Section("Роллы", R.drawable.r18),
            new Section("Теплое", R.drawable.r8),
            new Section("Десерты", R.drawable.r10)
    };

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return PopularFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return SECTIONS.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getSection(position).getTitle();
    }

    @DrawableRes
    public int getImageId(int position) {
        return getSection(position).getDrawableId();
    }

    private Section getSection(int position) {
        return SECTIONS[position];
    }

    private static class Section {
        private final String title;
        private final @DrawableRes int drawableId;

        private Section(String title, int drawableId) {
            this.title = title;
            this.drawableId = drawableId;
        }

        public String getTitle() {
            return title;
        }

        public int getDrawableId() {
            return drawableId;
        }
    }
}

