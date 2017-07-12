package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import data.Data;
import com.doersweb.smsapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by doersweb on 11/07/17.
 */

public class ContactsListAdapter extends BaseAdapter {

    JSONArray jsonArray;
    Context context;

    public ContactsListAdapter(Context context, JSONArray jsonArray) {
        this.jsonArray = jsonArray;
        this.context = context;

    }

    @Override
    public int getCount() {
        //This gives the number of elements to be displayed in a list
        return jsonArray.length();
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
        if (view == null) {

            //Layout inflater is used to inflate the adapter layout
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.contacts_list_adapter_layout, null);

            //Getting the views
            TextView name = (TextView) view.findViewById(R.id.name);
            TextView num = (TextView) view.findViewById(R.id.mobileNum);
            TextView initials = (TextView) view.findViewById(R.id.initials);


            //Setting the values from the JSON Array initialized in contructor
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(position);
                initials.setText(jsonObject.getString(Data.FIRST_NAME).substring(0,1));
                name.setText(jsonObject.getString(Data.FIRST_NAME) + " " + jsonObject.getString(Data.LAST_NAME));
                num.setText(jsonObject.getString("mobile"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return view;
    }
}
