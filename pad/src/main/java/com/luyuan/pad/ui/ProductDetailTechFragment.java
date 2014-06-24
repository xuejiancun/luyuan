package com.luyuan.pad.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.pad.R;
import com.luyuan.pad.model.ProductDetailData;
import com.luyuan.pad.util.GlobalConstantValues;
import com.luyuan.pad.util.GsonRequest;
import com.luyuan.pad.util.RequestManager;

public class ProductDetailTechFragment extends Fragment {

    private TextView arrivaldate;
    private TextView footsteplength;
    private TextView liftofflength;
    private TextView endurance;
    private TextView tyreform;
    private TextView tyrespec;
    private TextView brake;
    private TextView suspension;
    private TextView enginespec;
    private TextView batteryspec;
    private TextView charger;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_product_detail_tech, null);

        arrivaldate = (TextView) view.findViewById(R.id.textview_product_detail_tech_arrivaldate);
        footsteplength = (TextView) view.findViewById(R.id.textview_product_detail_tech_footsteplength);
        liftofflength = (TextView) view.findViewById(R.id.textview_product_detail_tech_liftofflength);
        endurance = (TextView) view.findViewById(R.id.textview_product_detail_tech_endurance);
        tyreform = (TextView) view.findViewById(R.id.textview_product_detail_tech_tyreform);
        tyrespec = (TextView) view.findViewById(R.id.textview_product_detail_tech_tyrespec);
        brake = (TextView) view.findViewById(R.id.textview_product_detail_tech_brake);
        suspension = (TextView) view.findViewById(R.id.textview_product_detail_tech_suspension);
        enginespec = (TextView) view.findViewById(R.id.textview_product_detail_tech_enginespec);
        batteryspec = (TextView) view.findViewById(R.id.textview_product_detail_tech_batteryspec);
        charger = (TextView) view.findViewById(R.id.textview_product_detail_tech_charger);

        String model = "";
        Bundle args = getArguments();
        if (args != null && args.getString(GlobalConstantValues.PARAM_CAR_MODEL) != null) {
            model = args.getString(GlobalConstantValues.PARAM_CAR_MODEL);

            if (GlobalConstantValues.checkNetworkConnection(getActivity())) {
                fetchProductDetailData(GlobalConstantValues.API_PRODUCT_DETAIL + "&model=" + model.trim());
            }
        } else {
            Dialog alertDialog = new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.app_param_error)
                    .setTitle(R.string.dialog_hint)
                    .setPositiveButton(R.string.dialog_confirm, null)
                    .create();
            alertDialog.show();
        }

        return view;
    }

    public void fetchProductDetailData(String url) {
        GsonRequest gsonObjRequest = new GsonRequest<ProductDetailData>(Request.Method.GET, url,
                ProductDetailData.class, new Response.Listener<ProductDetailData>() {
            @Override
            public void onResponse(ProductDetailData response) {
                if (response != null && response.getSuccess().equals("true")) {
                    arrivaldate.setText(response.getArrivaldate());
                    footsteplength.setText(response.getFootsteplength());
                    liftofflength.setText(response.getLiftofflength());
                    endurance.setText(response.getEndurance());
                    tyreform.setText(response.getTyreform());
                    tyrespec.setText(response.getTyrespec());
                    brake.setText(response.getBrake());
                    suspension.setText(response.getSuspension());
                    enginespec.setText(response.getEnginespec());
                    batteryspec.setText(response.getBatteryspec());
                    charger.setText(response.getCharger());
                } else {
                    Dialog alertDialog = new AlertDialog.Builder(getActivity())
                            .setMessage(R.string.fetch_data_error)
                            .setTitle(R.string.dialog_hint)
                            .setPositiveButton(R.string.dialog_confirm, null)
                            .create();
                    alertDialog.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Dialog alertDialog = new AlertDialog.Builder(getActivity())
                        .setMessage(R.string.fetch_data_error)
                        .setTitle(R.string.dialog_hint)
                        .setPositiveButton(R.string.dialog_confirm, null)
                        .create();
                alertDialog.show();
            }
        }
        );

        RequestManager.getRequestQueue().add(gsonObjRequest);
        gsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                GlobalConstantValues.CONNECTION_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}