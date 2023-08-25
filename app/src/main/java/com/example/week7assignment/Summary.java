package com.example.week7assignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.AlertDialog;
import android.widget.EditText;


import androidx.fragment.app.Fragment;

public class Summary extends Fragment {

    Button view, deleteAll,addExerciseButton;
    DBHelper DB;
    Spinner spinnerLastName;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_summary, container, false);

        // Find views within the inflated layout
        view = rootView.findViewById(R.id.btnView);
        deleteAll = rootView.findViewById(R.id.btnDeleteAll); // Add this line



        DB = new DBHelper(getContext());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getuserdata();
                if (res.getCount() == 0) {
                    Toast.makeText(getContext(), "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Group Name :" + res.getString(0) + "\n");
                    buffer.append("Exercise Name :" + res.getString(1) + "\n");
                    buffer.append("ID number :" + res.getString(2) + "\n");
                    buffer.append("Sets :" + res.getString(3) + "\n");
                    buffer.append("Reps :" + res.getString(4) + "\n");
                    buffer.append("Weight :" + res.getString(5) + "\n");
                    buffer.append("Date :" + res.getString(6) + "\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDeleted = DB.deleteAllEntries();
                if (isDeleted) {
                    Toast.makeText(getContext(), "All entries deleted", Toast.LENGTH_SHORT).show();
                    // Reset the idnumber EditText to 1


                    // Open the MainActivity

                } else {
                    Toast.makeText(getContext(), "Entries Deleted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });



        // Inflate the layout for this fragment
        return rootView;
    }



}
