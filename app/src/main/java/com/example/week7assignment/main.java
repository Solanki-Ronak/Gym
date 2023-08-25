package com.example.week7assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class main extends AppCompatActivity {

    private Spinner categorySpinner;
    private GridView logoGrid;

    Button next;


    private Integer[] footballLogos = {R.drawable.logo1, R.drawable.logo2, R.drawable.logo3, R.drawable.logo4, R.drawable.logo5, R.drawable.logo6};
    private Integer[] carImages = {R.drawable.car1, R.drawable.car2, R.drawable.car3, R.drawable.car4, R.drawable.car5, R.drawable.car6};
    private Integer[] universityLogos = {R.drawable.ulogo11, R.drawable.ulogo12, R.drawable.ulogo13, R.drawable.ulogo14, R.drawable.ulogo15, R.drawable.ulogo16};
    private Integer[] countryImages = {R.drawable.country1, R.drawable.country2, R.drawable.country3, R.drawable.country4, R.drawable.country5, R.drawable.country6};
    private Integer[] dishImages = {R.drawable.dish1, R.drawable.dish2, R.drawable.dish3, R.drawable.dish4, R.drawable.dish5, R.drawable.dish6};
    private Integer[] phoneImages = {R.drawable.phone1, R.drawable.phone2, R.drawable.phone3, R.drawable.phone4, R.drawable.phone5, R.drawable.phone6};

    private String[] footballNames = {"Pushups", "Bench Press", "Dumbbell Flyes", "Pull Ups", "Cable Chest Press", "Close Dumbbell Press"};
    private String[] carNames = {"Leg Extensions", "Lunges", "Leg Press", "Squats", "Calf Raises", "Leg Curls"};
    private String[] universityNames = {"Bicep Curls", "Tricep Extension", "Dips", "Hammer Curls", "Overhead Extension", "Lateral Raises"};
    private String[] countryNames = {"Rows", "Deadlift", "Back Extensions", "Y-Raises", "Pulldown Exercises", "FacePull"};
    private String[] dishNames = {"Side Lateral Raises", "Reverse Flyes", "Overhead Press", "Shoulder Shrug", "Rear Delt Flyes", "Z-Press"};
    private String[] phoneNames = {"Jumping Jacks", "Jogging", "Skipping", "Planks", "Cycling", "Running"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//getApplicationContext()
                startActivity(new Intent(main.this, MainActivity.class));
            }
        });

        categorySpinner = findViewById(R.id.spinner);
        logoGrid = findViewById(R.id.logo_grid);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
                displayImages(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });


    }

    private void displayImages(String category) {
        Integer[] imageArray;
        String[] nameArray;
        switch (category) {
            case "Chest":
                imageArray = footballLogos;
                nameArray = footballNames;
                break;
            case "Legs":
                imageArray = carImages;
                nameArray = carNames;
                break;
            case "Arms":
                imageArray = universityLogos;
                nameArray = universityNames;
                break;
            case "Back":
                imageArray = countryImages;
                nameArray = countryNames;
                break;
            case "Shoulders":
                imageArray = dishImages;
                nameArray = dishNames;
                break;
            case "Cardio":
                imageArray = phoneImages;
                nameArray = phoneNames;
                break;
            default:
                // Default to an empty array
                imageArray = new Integer[0];
                nameArray = new String[0];
                break;
        }

        ImageAdapter imageAdapter = new ImageAdapter(imageArray, nameArray);
        logoGrid.setAdapter(imageAdapter);
    }

    private class ImageAdapter extends ArrayAdapter<Integer> {
        private Integer[] images;
        private String[] names;

        public ImageAdapter(Integer[] images, String[] names) {
            super(main.this, 0, images);
            this.images = images;
            this.names = names;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = getLayoutInflater().inflate(R.layout.grid_item, parent, false);
            } else {
                view = convertView;
            }

            ImageView imageView = view.findViewById(R.id.image_view);
            TextView textView = view.findViewById(R.id.text_view);

            imageView.setImageResource(images[position]);
            textView.setText(names[position]);

            return view;
        }

    }
}
