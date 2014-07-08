package com.luyuan.mobile.function;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.Material;
import com.luyuan.mobile.model.MaterialData;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UploadMaterialActivity extends Activity implements SearchView.OnQueryTextListener {

    private SearchView searchView;
    private ProgressDialog dialog;
    private MaterialData materialData;
    private int materialIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.function_upload_material);

        setContentView(R.layout.activity_upload_material);

        Fragment fragment = new UploadMaterialChannelFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content_upload_material, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getResources().getString(R.string.hint_enter_query));
        searchView.setOnQueryTextListener(this);

        return true;
    }

    public boolean onQueryTextChange(String newText) {
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        String url = "";
        try {
            url = new StringBuffer(MyGlobal.API_QUERY_MATERIAL).toString() + "&query=" + URLEncoder.encode(query, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }

        if (MyGlobal.checkNetworkConnection(this)) {

            dialog = new ProgressDialog(this);
            dialog.setMessage(getText(R.string.loading));
            dialog.setCancelable(true);
            dialog.show();

            GsonRequest gsonObjRequest = new GsonRequest<MaterialData>(Request.Method.GET, url,
                    MaterialData.class, new Response.Listener<MaterialData>() {

                @Override
                public void onResponse(MaterialData response) {
                    dialog.dismiss();

                    if (response != null && response.getSuccess().equals("true")) {

                        materialData = response;
                        int count = materialData.getMaterials().size();
                        if (count == 1) {
                            materialIndex = 0;
                            replaceContentForQuery();

                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

                        } else if (count > 1) {
                            CharSequence[] jobList = new CharSequence[materialData.getMaterials().size()];
                            for (int i = 0; i < count; i++) {
                                jobList[i] = materialData.getMaterials().get(i).getName();
                            }

                            new AlertDialog.Builder(UploadMaterialActivity.this)
                                    .setTitle(R.string.dialog_choose_material)
                                    .setSingleChoiceItems(jobList, 0, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            materialIndex = which;
                                        }
                                    })
                                    .setNegativeButton(R.string.cancel, null)
                                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            replaceContentForQuery();
                                        }
                                    })
                                    .create()
                                    .show();
                        } else {
                            new AlertDialog.Builder(UploadMaterialActivity.this)
                                    .setMessage(R.string.fetch_data_empty)
                                    .setTitle(R.string.dialog_hint)
                                    .setPositiveButton(R.string.dialog_confirm, null)
                                    .create()
                                    .show();
                        }
                    } else {
                        new AlertDialog.Builder(UploadMaterialActivity.this)
                                .setMessage(R.string.fetch_data_error)
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

                    new AlertDialog.Builder(UploadMaterialActivity.this)
                            .setMessage(R.string.fetch_data_error)
                            .setTitle(R.string.dialog_hint)
                            .setPositiveButton(R.string.dialog_confirm, null)
                            .create()
                            .show();
                }
            }
            );

            RequestManager.getRequestQueue().add(gsonObjRequest);
        }

        return true;
    }

    private void replaceContentForQuery() {
        Fragment fragment = new UploadMaterialQueryFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        Material material = materialData.getMaterials().get(materialIndex);

        Bundle args = new Bundle();
        args.putString("id", material.getId());
        args.putString("name", material.getName());
        args.putString("remark", material.getRemark());
        args.putString("transactsate", material.getTransactState());
        args.putString("transactdate", material.getTransactDate());
        args.putString("submitdate", material.getSubmitDate());
        args.putString("feedback", material.getFeedback());
        args.putString("attachment", material.getAttachment());
        fragment.setArguments(args);

        fragmentTransaction.replace(R.id.frame_content_upload_material, fragment);
        fragmentTransaction.commit();
    }

}
