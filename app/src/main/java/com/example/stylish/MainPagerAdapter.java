package com.example.stylish;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainPagerAdapter extends FragmentStateAdapter {

    public MainPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new HomeFragment();
            case 1: return new WishlistFragment();
            case 2: return new CartFragment();    // 3rd Page: Cart (Majhkhaner FAB)
            case 3: return new ChatFragment();    // 4th Page: Chat (Ager Search)
            case 4: return new SettingFragment(); // 5th Page: Setting
            default: return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}