package com.gn.translateseas;

import android.os.Bundle;
import android.view.View;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.gn.translateseas.ui.tabs.SectionsPagerAdapter;
import com.gn.translateseas.databinding.ActivityIntroduccionBinding;

public class Introduccion extends AppCompatActivity {

    private ActivityIntroduccionBinding binding;

    private static ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityIntroduccionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
    }

    public static void next(){
        viewPager.arrowScroll(View.FOCUS_RIGHT);
    }

}