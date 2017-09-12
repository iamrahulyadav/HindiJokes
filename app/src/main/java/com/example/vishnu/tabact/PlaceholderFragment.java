package com.example.vishnu.tabact;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private View rootView;
    Cursor cursor;
    ListView listView;

    private static final String ARG_SECTION_NUMBER = "section_number";
    Window window;

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        listView= (ListView) rootView.findViewById(R.id.listView);
        //getting colors for the statusBar for every tabs
        int[] colorStatusBar= new int[]{R.color.first_statusBar,R.color.second_statusBar,R.color.third_statusBar,R.color.fourth_statusBar,R.color.fifth_statusBar,R.color.six_statusBar};

        if(android.os.Build.VERSION.SDK_INT>=21) {
            window =getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(colorStatusBar[0])); // change color for first tab initial
        }
        DatabaseHelper myDbHelper = new DatabaseHelper(getActivity());

        int [] img=new int[]{R.drawable.ic_menu_white,R.drawable.back_white};
        int singleImg=img[0];
        String[] tableName= new String[]{"SANTA","CLASS_ROOM","COUPLES","SANTA","CLASS_ROOM","COUPLES"};
        String[] colorImg= new String[]{"#FF239BEA","#aa14f5","#f51445","#f77f0e","#14d0f5","#f4235b"};
        String singleColor=colorImg[0];

        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

        if(getArguments().getInt(ARG_SECTION_NUMBER)==1){
            // Sat up to check internet connection availability
            ConnectivityManager connectivityManager =(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            if(networkInfo.isConnected()){
                // Action for internet is available
                textView.setText("Network available");
            }
            else{
                // Action for internet is not available
                textView.setText("Network not available");
            }
            cursor = myDbHelper.query1(tableName[0], null, null, null, null, null,null);
            singleImg= img[0];
            singleColor=colorImg[0];
            if(android.os.Build.VERSION.SDK_INT>=21){
                window.setStatusBarColor(getResources().getColor(colorStatusBar[0])); // status bar color change for every tabs
            }
        }

        if(getArguments().getInt(ARG_SECTION_NUMBER)==2){
            textView.setText("Test");
            cursor = myDbHelper.query2(tableName[1], null, null, null, null, null, null);
            singleImg= img[0];
            singleColor=colorImg[1];
        }

        if(getArguments().getInt(ARG_SECTION_NUMBER)==3){
            textView.setText("Hello Ravi");
            cursor = myDbHelper.query3(tableName[2], null, null, null, null, null, null);
            singleImg= img[1];
            singleColor=colorImg[2];
        }
        if(getArguments().getInt(ARG_SECTION_NUMBER)==4){
            textView.setText("Test");
            cursor = myDbHelper.query4(tableName[3], null, null, null, null, null, null);
            singleImg= img[0];
            singleColor=colorImg[3];
        }
        if(getArguments().getInt(ARG_SECTION_NUMBER)==5){
            textView.setText("Hello Ravi");
            cursor = myDbHelper.query5(tableName[4], null, null, null, null, null, null);
            singleImg= img[1];
            singleColor=colorImg[4];
        }

        if(getArguments().getInt(ARG_SECTION_NUMBER)==6){
            textView.setText("it's a Quiz Master");
            cursor = myDbHelper.query6(tableName[5], null, null, null, null, null, null);
            singleImg= img[0];
            singleColor=colorImg[5];

        }
        ArrayList<String> data =new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int indexID = cursor.getColumnIndex("_id");
                int indexDATE = cursor.getColumnIndex("JOKES");
                int i = cursor.getInt(indexID);
                data.add(cursor.getString(indexDATE));

            } while (cursor.moveToNext());
            Collections.shuffle(data);
            data.toArray();
            String[] data2=new String[data.size()];
            data2=data.toArray(data2);
            ListViewAdaptor adapter = new ListViewAdaptor(getActivity(),data2,singleImg,singleColor);
            listView.setAdapter(adapter);
        }

        return rootView;
    }

}
/*ArrayList<String> data=new ArrayList<>();

        if(getArguments().getInt(ARG_SECTION_NUMBER)==1){
            textView.setText("Test");
            data.equals(myDbHelper.getCursor(tableName[0]));
        }
        if(getArguments().getInt(ARG_SECTION_NUMBER)==2){
            textView.setText("Test");
            data.equals(myDbHelper.getCursor(tableName[1]));
        }
        if(getArguments().getInt(ARG_SECTION_NUMBER)==3){
            textView.setText("Test");
            data.equals(myDbHelper.getCursor(tableName[2]));
        }
        if(getArguments().getInt(ARG_SECTION_NUMBER)==4){
            textView.setText("Test");
            data.equals(myDbHelper.getCursor(tableName[3]));
        }
        if(getArguments().getInt(ARG_SECTION_NUMBER)==5){
            textView.setText("Test");
            data.equals(myDbHelper.getCursor(tableName[4]));
        }
        if(getArguments().getInt(ARG_SECTION_NUMBER)==6){
            textView.setText("Test");
            data.equals(myDbHelper.getCursor(tableName[5]));
            Collections.shuffle(data);
            data.toArray();
            ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, data);
            listView.setAdapter(adapter);
        }*/