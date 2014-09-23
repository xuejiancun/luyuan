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

// 资料上传自定义通道搜索结果页面
public class UploadMaterialUDFQueryFragment extends Fragment {

    private String CANCELED = "已取消";

    private String id = "";
    private String name = "";
    private String remark = "";
    private String transactsate = "";
    private String transactdate = "";
    private String submitdate = "";
    private String feedback = "";
    private String attachment = "";

    private ProgressDialog dialog;
    private EditText editTextTransactState;
    private Button buttonCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upload_material_udf_query_fragment, null);

        Bundle args = getArguments();
        id = args.getString("id");
        name = args.getString("name");
        remark = args.getString("remark");
        transactsate = args.getString("transactsate");
        transactdate = args.getString("transactdate");
        submitdate = args.getString("submitdate");
        feedback = args.getString("feedback");
        attachment = args.getString("attachment");

        ((EditText) view.findViewById(R.id.edittext_material_name)).setText(name);
        ((EditText) view.findViewById(R.id.edittext_material_remark)).setText(remark);
        editTextTransactState = (EditText) view.findViewById(R.id.edittext_material_transact_state);
        editTextTransactState.setText(transactsate);
        ((EditText) view.findViewById(R.id.edittext_material_transact_date)).setText(transactdate);
        ((EditText) view.findViewById(R.id.edittext_material_submit_date)).setText(submitdate);
        ((EditText) view.findViewById(R.id.edittext_material_feedback)).setText(feedback);
        ((TextView) view.findViewById(R.id.textview_material_attachment)).setText(attachment);

        // 资料状态变成取消
        buttonCancel = (Button) view.findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyGlobal.checkNetworkConnection(getActivity())) {

                    dialog = new ProgressDialog(getActivity());
                    dialog.setMessage(getText(R.string.loading));
                    dialog.setCancelable(true);
                    dialog.show();

                    GsonRequest gsonObjRequest = new GsonRequest<SuccessData>(Request.Method.GET, MyGlobal.API_CANCEL_UDF_MATERIAL + "&id=" + id,
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

                                editTextTransactState.setText(CANCELED);
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

        // 返回新增资料
        ((Button) view.findViewById(R.id.button_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_content, new UploadMaterialChannelFragment());
                fragmentTransaction.commit();
            }
        });

        if (transactsate.equals(CANCELED)) {
            buttonCancel.setText(CANCELED);
            buttonCancel.setEnabled(false);
        }

        return view;
    }

}