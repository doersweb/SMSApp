package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.doersweb.smsapp.R;
import data.SmsHistoryData;

import java.util.ArrayList;

/**
 * Created by doersweb on 12/07/17.
 */

public class SmsHistoryAdapter extends BaseAdapter {

    Context context;
    ArrayList<SmsHistoryData> arrayList;

    public SmsHistoryAdapter(Context context, ArrayList<SmsHistoryData> arrayList){
        this.arrayList = arrayList;
        this.context = context;
    }
    @Override
    public int getCount() {
        //This gives the number of elements to be displayed in a list
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null){

            //Layout inflater is used to inflate the adapter layout
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.sms_history_adapter_layout, null);


            //Getting the views
            TextView name = (TextView) view.findViewById(R.id.name);
            TextView otp = (TextView) view.findViewById(R.id.otp);
            TextView time = (TextView) view.findViewById(R.id.time);


            //Setting the values from the arraylist initialized in contructor
            name.setText(arrayList.get(position).getName());
            otp.setText(arrayList.get(position).getOtp());
            time.setText(arrayList.get(position).getTime());


        }

        return view;
    }
}
