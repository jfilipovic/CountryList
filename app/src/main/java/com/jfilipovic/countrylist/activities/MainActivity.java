package com.jfilipovic.countrylist.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.jfilipovic.countrylist.R;
import com.jfilipovic.countrylist.models.CountriesListHolder;
import com.jfilipovic.countrylist.models.Country;
import com.jfilipovic.countrylist.rest.RestService;
import com.jfilipovic.countrylist.tasks.GetCountriesListTask;
import com.jfilipovic.countrylist.tasks.GetSelectedCountryTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> countriesNames;
    private CountriesSpinnerAdapter spinnerAdapter;
    private TextView countryNameTextView, capitalTextView, subregionTextView, populationTextView;
    private ImageView flagView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countryNameTextView = (TextView) findViewById(R.id.country_name_value);
        capitalTextView = (TextView) findViewById(R.id.capital_value);
        subregionTextView = (TextView) findViewById(R.id.subregion_value);
        populationTextView = (TextView) findViewById(R.id.population_value);
        flagView = (ImageView) findViewById(R.id.flag_view);

        countriesNames = new ArrayList<String>();
        countriesNames.add("- none -");
        new GetCountriesListTask(MainActivity.this, new RestService(this)).execute();

    }

    public void fillInList(final CountriesListHolder countriesListHolder){

        for (Country country: countriesListHolder.getCountriesList()){
            countriesNames.add(country.getName());
        }

        final Spinner countrySpinner = (Spinner) findViewById(R.id.country_spinner);

        spinnerAdapter = new CountriesSpinnerAdapter(this, countriesNames);
        countrySpinner.setAdapter(spinnerAdapter);
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if (position > 0){
                    new GetSelectedCountryTask(MainActivity.this, new RestService(MainActivity.this), countriesNames.get(position)).execute();
                } else fillInNoData();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

    }

    public void fillInCountryData(Country country){

        if (country != null){
            countryNameTextView.setText(country.getName());
            capitalTextView.setText(country.getCapital());
            subregionTextView.setText(country.getSubregion());
            populationTextView.setText(country.getPopulation());
            flagView.setImageResource(getFlagId(MainActivity.this, country.getAlpha2Code().toLowerCase()));

            flagView.setVisibility(View.VISIBLE);
        } else fillInNoData();
    }

    public void fillInNoData(){
        countryNameTextView.setText("");
        capitalTextView.setText("");
        subregionTextView.setText("");
        populationTextView.setText("");
        flagView.setVisibility(View.INVISIBLE);
    }

    public class CountriesSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
        private Activity activity;
        private List<String> cNames;

        public CountriesSpinnerAdapter(Activity activity, List<String> countriesNames){
            this.activity = activity;
            this.cNames = countriesNames;
        }

        public int getCount() {
            return cNames.size();
        }

        public Object getItem(int position) {
            return cNames.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            View spinView;
            if( convertView == null ){
                LayoutInflater inflater = activity.getLayoutInflater();
                spinView = inflater.inflate(R.layout.spinner_row, null);
            } else {
                spinView = convertView;
            }
            TextView title = (TextView) spinView.findViewById(R.id.spinner_row_title);
            title.setText(cNames.get(position));

            return spinView;
        }
    }

    public static int getFlagId(Context context, String flagCode) {
        if (flagCode.equals("do")) flagCode = "do_";
        int id = context.getResources().getIdentifier(flagCode, "drawable", context.getPackageName());;
        Log.d("FLAGID", Integer.toString(id));
        return id;
    }
}
