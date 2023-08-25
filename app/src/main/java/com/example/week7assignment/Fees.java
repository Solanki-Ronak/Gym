package com.example.week7assignment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class Fees extends Fragment {
    EditText idnumber, totalfees, feespaid, feesbalance, clearancedate;
    Button insert, update, delete, view;
    DBHelper DB;

    public Fees() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fees, container, false);

        // Find views within the inflated layout
        idnumber = rootView.findViewById(R.id.idnumber);
        totalfees = rootView.findViewById(R.id.totalfees);
        feespaid = rootView.findViewById(R.id.feespaid);
        feesbalance = rootView.findViewById(R.id.feesbalance);
        clearancedate = rootView.findViewById(R.id.clearancedate);

        insert = rootView.findViewById(R.id.btnInsert);
        update = rootView.findViewById(R.id.btnUpdate);
        delete = rootView.findViewById(R.id.btnDelete);
        view = rootView.findViewById(R.id.btnView);

        DB = new DBHelper(getContext());



        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idnumberTXT = idnumber.getText().toString();
                String totalfeesTXT = totalfees.getText().toString();
                String feespaidTXT = feespaid.getText().toString();
                String feesbalanceTXT = feesbalance.getText().toString();
                String clearancedateTXT = clearancedate.getText().toString();


                Boolean checkinsertdata = DB.insertfeesdata(idnumberTXT, totalfeesTXT, feespaidTXT, feesbalanceTXT, clearancedateTXT);
                if (checkinsertdata == true) {
                    Toast.makeText(getContext(), "New Entry Inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idnumberTXT = idnumber.getText().toString();
                String totalfeesTXT = totalfees.getText().toString();
                String feespaidTXT = feespaid.getText().toString();
                String feesbalanceTXT = feesbalance.getText().toString();
                String clearancedateTXT = clearancedate.getText().toString();

                Boolean checkupdatedata = DB.updatefeesdata(idnumberTXT, totalfeesTXT, feespaidTXT, feesbalanceTXT, clearancedateTXT);
                if (checkupdatedata == true) {
                    Toast.makeText(getContext(), "New Entry Updated", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "Entry Not Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idnumberTXT = idnumber.getText().toString();

                Boolean checkdeletedata = DB.deletefeesdata(idnumberTXT);
                if (checkdeletedata == true) {
                    Toast.makeText(getContext(), "New Deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Entry Not Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getfeesdata();
                if (res.getCount() == 0) {
                    Toast.makeText(getContext(), "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("ID number :" + res.getString(0) + "\n");
                    buffer.append("Total Fees :" + res.getString(1) + "\n");
                    buffer.append("Fees Paid :" + res.getString(2) + "\n");
                    buffer.append("Fees balance :" + res.getString(3) + "\n");
                    buffer.append("Clearance Date :" + res.getString(4) + "\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Fees Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }
}
