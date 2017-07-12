package adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragments.ContactsFragment;
import fragments.SmsHistoryFragment;

/**
 * Created by doersweb on 11/07/17.
 */


public class SamplePagerAdapter extends FragmentPagerAdapter {

    //Mandatory constructor which takes in fragment manager as argument
    public SamplePagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        //Loading fragments in tabs
        if(position == 0){
            return new ContactsFragment();
        } else {
            return new SmsHistoryFragment();
        }
    }

    @Override
    public int getCount() {
        //number of tabs
        return 2;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        //To set the title for each tab
        if(position == 0){
            return "Contacts";
        }else {
            return "SMS History";
        }
    }
}
