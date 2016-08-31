package com.jfilipovic.countrylist.tasks;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.jfilipovic.countrylist.activities.MainActivity;
import com.jfilipovic.countrylist.models.Country;
import com.jfilipovic.countrylist.rest.RestService;


public class GetSelectedCountryTask extends AsyncTask<String, Void, Country> {

    private Activity activity;
    private RestService restService;
    private Dialog dialog;
    private Handler handler = new Handler();
    private Runnable runnable;
    private String selectedCountry;


    public GetSelectedCountryTask(final Activity activity, RestService restService, String selectedCountry) {
        this.activity = activity;
        this.restService = restService;
        this.selectedCountry = selectedCountry;
        runnable = new Runnable() {
            @Override
            public void run() {
                dialog = ProgressDialog.show(activity, null, "fetching Country Data", true);
            }
        };
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showDialog();
    }

    @Override
    protected Country doInBackground(String... params) {
        return restService.getSelectedCountry(selectedCountry);
    }

    @Override
    protected void onPostExecute(Country country) {

        if (activity instanceof MainActivity && country != null) {
            dismissDialog();
            Log.d("TASK", country.getCapital());
            ((MainActivity) activity).fillInCountryData(country);
        }
    }


    private void showDialog(){
        handler.postDelayed(runnable, 500);
    }

    private void dismissDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }else {
                handler.removeCallbacks(runnable);
            }
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

    }
}
