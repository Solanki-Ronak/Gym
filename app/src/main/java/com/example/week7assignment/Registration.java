package com.example.week7assignment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class Registration extends Fragment {

    EditText idnumber, major, gender,date, weight, group, exname;
    Button insert, update, delete, view;

    DBHelper DB;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_registration, container, false);
        DB = new DBHelper(getContext()); // Move this line above any DB operations

        idnumber = rootView.findViewById(R.id.idnumber);
        int nextIdNumber = DB.getNextAvailableIdNumber();
        idnumber.setText(String.valueOf(nextIdNumber));

        EditText group = rootView.findViewById(R.id.group);
        EditText exname = rootView.findViewById(R.id.exname);
        Spinner spinnerLastName = rootView.findViewById(R.id.spinnerLastName);




        major = rootView.findViewById(R.id.major);
        gender = rootView.findViewById(R.id.gender);
        weight = rootView.findViewById(R.id.weight);
        date = rootView.findViewById(R.id.date);
        final Calendar cal = Calendar.getInstance();
        date.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    // Format the text as DD/MM/YYYY with slashes
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    // Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + "DDMMYYYY".substring(clean.length());
                    } else {
                        // This part makes sure that when we finish entering numbers
                        // the date is correct, fixing it otherwise
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        mon = mon < 1 ? 1 : Math.min(mon, 12);
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : Math.min(year, Calendar.getInstance().get(Calendar.YEAR));
                        cal.set(Calendar.YEAR, year);

                        day = Math.min(day, cal.getActualMaximum(Calendar.DATE));
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = Math.max(sel, 0);
                    current = clean;
                    date.setText(current);
                    date.setSelection(Math.min(sel, current.length()));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        insert = rootView.findViewById(R.id.btnInsert);
        update = rootView.findViewById(R.id.btnUpdate);
        delete = rootView.findViewById(R.id.btnDelete);
        view = rootView.findViewById(R.id.btnClear);

        DB = new DBHelper(getContext());
        ArrayAdapter<CharSequence> initialLastNameAdapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.sample_lastnames_choose,
                R.layout.custom_spinner_dropdown_item_lastname
        );
        spinnerLastName.setAdapter(initialLastNameAdapter);
        Spinner spinnerFirstname = rootView.findViewById(R.id.spinnerFirstname);

        ArrayAdapter<CharSequence> firstNameAdapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.sample_firstnames,
                R.layout.custom_spinner_item
        );
        spinnerFirstname.setAdapter(firstNameAdapter);

        spinnerFirstname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedFirstname = parent.getItemAtPosition(position).toString();
                group.setText(selectedFirstname);
                ArrayAdapter<CharSequence> lastNameAdapter;
                switch (selectedFirstname) {
                    case "Legs":
                        lastNameAdapter = ArrayAdapter.createFromResource(
                                getContext(),
                                R.array.sample_lastnames_legs,
                                android.R.layout.simple_spinner_item
                        );
                        break;
                    case "Chest":
                        lastNameAdapter = ArrayAdapter.createFromResource(
                                getContext(),
                                R.array.sample_lastnames_chest,
                                android.R.layout.simple_spinner_item
                        );
                        break;
                    case "Arms":
                        lastNameAdapter = ArrayAdapter.createFromResource(
                                getContext(),
                                R.array.sample_lastnames_arms,
                                android.R.layout.simple_spinner_item
                        );
                        break;
                    case "Back":
                        lastNameAdapter = ArrayAdapter.createFromResource(
                                getContext(),
                                R.array.sample_lastnames_back,
                                android.R.layout.simple_spinner_item
                        );
                        break;
                    case "Shoulders":
                        lastNameAdapter = ArrayAdapter.createFromResource(
                                getContext(),
                                R.array.sample_lastnames_shoulder,
                                android.R.layout.simple_spinner_item
                        );
                        break;
                    default:
                        lastNameAdapter = ArrayAdapter.createFromResource(
                                getContext(),
                                R.array.sample_lastnames_cardio,
                                android.R.layout.simple_spinner_item
                        );
                        break;
                }
                spinnerLastName.setAdapter(lastNameAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerLastName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected last name
                String selectedLastName = parent.getItemAtPosition(position).toString();
                // Set the selected last name as the text for the exname EditText
                exname.setText(selectedLastName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstnameTXT = group.getText().toString();
                String lastnameTXT = exname.getText().toString();
                String idnumberTXT = idnumber.getText().toString();
                String majorTXT = major.getText().toString();
                String genderTXT = gender.getText().toString();
                String weightTXT = weight.getText().toString();
                String dateTXT = date.getText().toString();

                int nextIdNumber = DB.getNextAvailableIdNumber();
                // Increment the idnumber by 1 for the new entry
                int newIdNumber = nextIdNumber + 1;
                // Set the new idnumber to the idnumber EditText
                idnumber.setText(String.valueOf(newIdNumber));

                Boolean checkinsertdata = DB.insertuserdata(firstnameTXT, lastnameTXT, idnumberTXT, majorTXT, genderTXT,weightTXT,dateTXT);
                if (checkinsertdata) {
                    Toast.makeText(getContext(), "New Entry Inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
                }



                major.setText("");
                gender.setText("");
                weight.setText("");
                date.setText("");





            }

        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstnameTXT = group.getText().toString();
                String lastnameTXT = exname.getText().toString();
                String idnumberTXT = idnumber.getText().toString();
                String majorTXT = major.getText().toString();
                String genderTXT = gender.getText().toString();
                String weightTXT = weight.getText().toString();
                String dateTXT = date.getText().toString();
                int nextIdNumber = DB.getNextAvailableIdNumber();
                // Increment the idnumber by 1 for the new entry
                int newIdNumber = nextIdNumber + 1;
                // Set the new idnumber to the idnumber EditText
                idnumber.setText(String.valueOf(newIdNumber));
                Boolean checkupdatedata = DB.updateuserdata(firstnameTXT, lastnameTXT, idnumberTXT, majorTXT, genderTXT,weightTXT,dateTXT);
                if (checkupdatedata) {
                    Toast.makeText(getContext(), "New Entry Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Entry Updated", Toast.LENGTH_SHORT).show();
                }

                major.setText("");
                gender.setText("");
                weight.setText("");
                date.setText("");


            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idnumberTXT = idnumber.getText().toString();

                Boolean checkdeletedata = DB.deleteuserdata(idnumberTXT);
                if (checkdeletedata) {
                    Toast.makeText(getContext(), "Entry Deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Entry Deleted", Toast.LENGTH_SHORT).show();
                }

                major.setText("");
                gender.setText("");
                weight.setText("");
                date.setText("");


            }
        });

        Button btnClear = rootView.findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearData();

            }
        });

        return rootView;
    }





    private void clearData() {

        major.setText("");

        gender.setText("");
        date.setText("");
        weight.setText("");

        Spinner spinnerFirstname = getView().findViewById(R.id.spinnerFirstname);
        Spinner spinnerLastName = getView().findViewById(R.id.spinnerLastName);
        spinnerFirstname.setSelection(0);
        spinnerLastName.setSelection(0);

        Toast.makeText(getContext(), "Data Cleared", Toast.LENGTH_SHORT).show();
    }



}
