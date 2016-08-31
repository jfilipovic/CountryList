package com.jfilipovic.countrylist.tasks;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;

import com.jfilipovic.countrylist.activities.MainActivity;
import com.jfilipovic.countrylist.models.CountriesListHolder;
import com.jfilipovic.countrylist.rest.RestService;


public class GetCountriesListTask extends AsyncTask<String, Void, CountriesListHolder> {

    private Activity activity;
    private RestService restService;
    private Dialog dialog;
    private Handler handler = new Handler();
    private Runnable runnable;


    public GetCountriesListTask(final Activity activity, RestService restService) {
        this.activity = activity;
        this.restService = restService;
        runnable = new Runnable() {
            @Override
            public void run() {
                dialog = ProgressDialog.show(activity, null, "fetching Countries List", true);
            }
        };
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showDialog();
    }

    @Override
    protected CountriesListHolder doInBackground(String... params) {
        return restService.getCountriesList();
    }

    @Override
    protected void onPostExecute(CountriesListHolder countryList) {
        if (activity instanceof MainActivity && countryList != null && countryList.getCountriesList().size()>0) {
            dismissDialog();
            ((MainActivity) activity).fillInList(countryList);
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
