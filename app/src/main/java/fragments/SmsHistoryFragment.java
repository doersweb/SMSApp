package fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import util.ContactsDataBaseHelper;
import com.doersweb.smsapp.R;
import data.SmsHistoryData;

import java.util.ArrayList;

import adapters.SmsHistoryAdapter;

/**
 * Created by doersweb on 11/07/17.
 * This fragment displays the list of all otp messages sent with Name, time and otp data in
 * descending order
 */

public class SmsHistoryFragment extends Fragment {


    //mandatory constructor to setup fragment in activity's layout
    public SmsHistoryFragment() {

    }


    ContactsDataBaseHelper contactsDataBaseHelper;

    SmsHistoryAdapter smsHistoryAdapter;

    LinearLayout headingLayout;
    TextView noMessgTxt;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.sms_history_fragment, container, false);

        //Creating ContactsDataBaseHelper class to use it's method to fetch data stored in database
        contactsDataBaseHelper = new ContactsDataBaseHelper(getActivity());

        //capturing layout elements
        headingLayout = (LinearLayout) result.findViewById(R.id.headingLayout);
        noMessgTxt = (TextView) result.findViewById(R.id.noMesgTv);
        listView = (ListView) result.findViewById(R.id.listView);

        Cursor cursor = contactsDataBaseHelper.getSmsHistoryData();

        if (cursor.getCount() == 0) {
            //To display fallbac text and hide listview and heading layout
            headingLayout.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
            noMessgTxt.setVisibility(View.VISIBLE);
        } else {

            //hiding fallback text and showing heading layout and list ;
            headingLayout.setVisibility(View.VISIBLE);
            listView.setVisibility(View.VISIBLE);
            noMessgTxt.setVisibility(View.GONE);

            //An arraylist to use and process data fetched from the database
            ArrayList<SmsHistoryData> arrayList = new ArrayList<SmsHistoryData>();

            //This runs until we have the next row. It's index start with -1 hence we move to the next row.
            while (cursor.moveToNext()) {

                SmsHistoryData smsHistoryData = new SmsHistoryData();
                smsHistoryData.setName(cursor.getString(cursor.getColumnIndex(ContactsDataBaseHelper.NAME)));
                smsHistoryData.setTime(cursor.getString(cursor.getColumnIndex(ContactsDataBaseHelper.TIME)));
                smsHistoryData.setContact(cursor.getString(cursor.getColumnIndex(ContactsDataBaseHelper.MOB_NUM)));
                smsHistoryData.setOtp(cursor.getString(cursor.getColumnIndex(ContactsDataBaseHelper.OTP)));

                //This adds SmsHistoryData object to the top of arraylist
                arrayList.add(0, smsHistoryData);


            }

            //Setting up adapter with the data to populate in the list
            smsHistoryAdapter = new SmsHistoryAdapter(getActivity(), arrayList);

            //setting the adpater with the information provided above to our listview
            listView.setAdapter(smsHistoryAdapter);

        }


        return result;
    }
}
