package lk.waplak.invoice.scanner;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lk.waplak.invoice.R;
import lk.waplak.invoice.scanner.fragment.OtherDetailsFragment;
import lk.waplak.invoice.scanner.fragment.AddInvoiceFragment;

import static android.content.ContentValues.TAG;

public class InvoiceFragment extends Fragment {

    public static String getTAG() {
        return "Create Invoice";
    }
    private  ViewPager mViewPager;
    private Adapter viewPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.fragment_scanner, container, false);
            mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);


            Log.d(TAG, "onCreateView");
        } catch (Exception e) {
            Log.e(TAG, "onCreateView: " + e.toString());
        }
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        setupViewPager(mViewPager);
        getActivity().setTitle("Create Invoice");
    }



    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new Adapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new AddInvoiceFragment(), "Add Item");
        //viewPagerAdapter.addFragment(new OtherDetailsFragment(), "Invoice Details");
        viewPager.setAdapter(viewPagerAdapter);
    }

    private static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

}