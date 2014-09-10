package com.luyuan.mobile.function;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.SuccessData;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

public class UploadMaterialDedicatedQueryFragment extends Fragment {

    private String CANCELED = "已取消";

    private String id = "";
    private String location = "";
    private String area = "";
    private String brand = "";
    private String udf = "";
    private String submitDate = "";
    private String submitBy = "";
    private String attachment = "";
    private String status = "";

    private ProgressDialog dialog;
    private Button buttonCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upload_material_dedicated_query_fragment, null);

        Bundle args = getArguments();
        id = args.getString("id");
        location = args.getString("location");
        area = args.getString("area");
        brand = args.getString("brand");
        udf = args.getString("udf");
        submitDate = args.getString("submitDate");
        submitBy = args.getString("submitBy");
        attachment = args.getString("attachment");
        status = args.getString("status");

        ((EditText) view.findViewById(R.id.edittext_location_place)).setText(location);
        ((EditText) view.findViewById(R.id.edittext_area)).setText(area);
        ((EditText) view.findViewById(R.id.edittext_spinner_brand)).setText(brand);
        ((EditText) view.findViewById(R.id.edittext_udf_brand)).setText(udf);
        ((EditText) view.findViewById(R.id.edittext_submit_date)).setText(submitDate);
        ((EditText) view.findViewById(R.id.edittext_submit_by)).setText(submitBy);
        ((TextView) view.findViewById(R.id.textview_material_attachment)).setText(attachment);

        // cancel material
        buttonCancel = (Button) view.findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyGlobal.checkNetworkConnection(getActivity())) {

                    dialog = new ProgressDialog(getActivity());
                    dialog.setMessage(getText(R.string.loading));
                    dialog.setCancelable(true);
                    dialog.show();

                    GsonRequest gsonObjRequest = new GsonRequest<SuccessData>(Request.Method.GET, MyGlobal.API_CANCEL_DEDICATED_MATERIAL + "&id=" + id,
                            SuccessData.class, new Response.Listener<SuccessData>() {

                        @Override
                        public void onResponse(SuccessData response) {
                            dialog.dismiss();

                            if (response != null && response.getSuccess().equals("true")) {
                                new AlertDialog.Builder(getActivity())
                                        .setMessage(R.string.cancel_success)
                                        .setTitle(R.string.dialog_hint)
                                        .setPositiveButton(R.string.dialog_confirm, null)
                                        .create()
                                        .show();

                                buttonCancel.setText(CANCELED);
                                buttonCancel.setEnabled(false);

                            } else {
                                new AlertDialog.Builder(getActivity())
                                        .setMessage(R.string.interact_data_error)
                                        .setTitle(R.string.dialog_hint)
                                        .setPositiveButton(R.string.dialog_confirm, null)
                                        .create()
                                        .show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.dismiss();

                            new AlertDialog.Builder(getActivity())
                                    .setMessage(R.string.interact_data_error)
                                    .setTitle(R.string.dialog_hint)
                                    .setPositiveButton(R.string.dialog_confirm, null)
                                    .create()
                                    .show();
                        }
                    }
                    );

                    RequestManager.getRequestQueue().add(gsonObjRequest);
                }
            }
        });

        ((Button) view.findViewById(R.id.button_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_content, new UploadMaterialDedicatedFragment());
                fragmentTransaction.commit();
            }
        });

        if (status.equals(CANCELED)) {
            buttonCancel.setText(CANCELED);
            buttonCancel.setEnabled(false);
        }

        return view;
    }

}