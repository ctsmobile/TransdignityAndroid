package com.cts.removalspecialist.fragments.rating;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cts.removalspecialist.R;
import com.cts.removalspecialist.adapters.RatingListAdapter;
import com.cts.removalspecialist.models.auth.LoginResponse;
import com.cts.removalspecialist.models.rating.RatignLIstResponseModel;
import com.cts.removalspecialist.network.ApiClients;
import com.cts.removalspecialist.network.ApiInterface;
import com.cts.removalspecialist.utilities.GlobalValues;
import com.cts.removalspecialist.utilities.PreferenceManager;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RatingFragment extends Fragment {
    View view;
    Context context;
    Activity activity;
    LoginResponse loginResponse;
    RecyclerView recycler_rating;
    String userID,groupId;
    List<RatignLIstResponseModel.Datum> dataItems = new ArrayList<>();
    RatingListAdapter ratingListAdapter;
    LinearLayoutManager linearLayoutManager;
    TextView tvToolbar;

    public RatingFragment() {
        // Required empty public constructor
    }


    public static RatingFragment newInstance(String param1, String param2) {
        RatingFragment fragment = new RatingFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_rating, container, false);
        tvToolbar = activity.findViewById(R.id.tv_toolbar);
        tvToolbar.setText("Ratings");
        recycler_rating=view.findViewById(R.id.recycler_rating);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);
        if (loginResponse != null) {
            userID = loginResponse.getData().getId();
            groupId = loginResponse.getData().getUserGroupId();
        }
        getRatingListApi();
        if (userID != null) {
            linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            ratingListAdapter = new RatingListAdapter(getActivity(),dataItems);
            recycler_rating.setLayoutManager(linearLayoutManager);
            recycler_rating.setAdapter(ratingListAdapter);
        }
        return view;
    }
    void getRatingListApi() {
        dataItems.clear();
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading.....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();


        ApiInterface apiInterface = ApiClients.getClient(getActivity()).create(ApiInterface.class);
        Call<RatignLIstResponseModel> call = null;

        call = apiInterface.getDriverRatingApi(loginResponse.getData().getToken(),groupId,userID);

        call.enqueue(new Callback<RatignLIstResponseModel>() {
            @Override
            public void onResponse(Call<RatignLIstResponseModel> call, Response<RatignLIstResponseModel> response) {
                try {
                    progressDialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {

                        if (response.body().getData().size() > 0) {
                            dataItems.addAll(response.body().getData());
                            ratingListAdapter.notifyDataSetChanged();



                        } else {


                        }
                        if (response.body().getSuccess().equals("true")) {
                            Log.e(TAG, "onResponse: list size>>>>>" + response.body().getData().size());

                            /*if (dataItems.size() > 0) {
                                event_recycler_view.setVisibility(View.VISIBLE);
                               // binding.emptyLayout.setVisibility(View.GONE);
                            } else {
                                event_recycler_view.setVisibility(View.GONE);
                                //binding.emptyLayout.setVisibility(View.VISIBLE);
                            }*/
                        } else {
                            // event_recycler_view.setVisibility(View.GONE);
                            //binding.emptyLayout.setVisibility(View.VISIBLE);
                            //Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        //GlobalValues.getPreferenceManager().setloginPref(false);
                        BufferedReader reader = null;
                        StringBuilder sb = new StringBuilder();
                        try {
                            reader = new BufferedReader(new InputStreamReader(response.errorBody().byteStream()));
                            String line;
                            try {
                                while ((line = reader.readLine()) != null) {
                                    sb.append(line);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            String finallyError = sb.toString();
                            JSONObject jsonObjectError = new JSONObject(finallyError);
                            String message = jsonObjectError.optString("message");
                            JSONObject jsonArray = jsonObjectError.getJSONObject("data");
                            String error =jsonArray.optString("error");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<RatignLIstResponseModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });

    }

}