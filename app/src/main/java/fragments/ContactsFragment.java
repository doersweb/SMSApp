package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.doersweb.smsapp.ContactInfoActivity;
import data.Data;
import com.doersweb.smsapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapters.ContactsListAdapter;

/**
 * Created by doersweb on 11/07/17.
 * This fragment displays the contacts in a list create from a JSON using static string arrays.
 */

public class ContactsFragment extends Fragment {


    //static arrays to create a JSON out of them to show in the listview
    String contactsFirstName[] = {"Alok", "Hardik", "Sachin", "Virat", "Amit"};
    String contactsLastName[] = {"Singh", "Pandya", "Tendulakar", "Kohli", "Mishra"};
    String mobNumbers[] = {"7838720865", "9971792703", "9873956293", "9818761784", "987654321"};

    //mandatory constructor to setup fragment in the activity layout
    public ContactsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //using LayoutInflater to inflate the layout created for this frgament
        View result=inflater.inflate(R.layout.contacts_list_fragment, container, false);


        final JSONArray jsonArray = new JSONArray();

        //creating JSON array from the static string arrays we have taken.

        for(int index = 0; index<contactsFirstName.length  ; index++){
            JSONObject jsonObject = new JSONObject() ;
            try {
                jsonObject.put(Data.FIRST_NAME, contactsFirstName[index]);
                jsonObject.put(Data.LAST_NAME, contactsLastName[index]);
                jsonObject.put(Data.MOB_NUMBERS, mobNumbers[index]);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsonArray.put(jsonObject);
        }

        //Seetting up adapter with the acitivity context and JSONArray we just created
        ContactsListAdapter contactsListAdapter = new ContactsListAdapter(getActivity(), jsonArray);

        //Capturing listview from fragments layout and setting our adapter to it
        ListView listView = (ListView) result.findViewById(R.id.listView);
        listView.setAdapter(contactsListAdapter);


        //This is to handle click events on individual listitems. OnItemClickListener will return us
        //the item's position in the list and we can take appropriate action based on that
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getActivity(), ContactInfoActivity.class);
                try {
                    intent.putExtra(Data.FIRST_NAME, jsonArray.getJSONObject(position).getString(Data.FIRST_NAME));
                    intent.putExtra(Data.LAST_NAME, jsonArray.getJSONObject(position).getString(Data.LAST_NAME));
                    intent.putExtra(Data.MOB_NUMBERS, jsonArray.getJSONObject(position).getString(Data.MOB_NUMBERS));
                }catch (JSONException e){
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });


        return result;
    }
}
