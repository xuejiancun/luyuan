package com.luyuan.mobile.function;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.Material;
import com.luyuan.mobile.model.MaterialData;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

import java.util.ArrayList;

public class UploadMaterialActivity extends Activity implements SearchView.OnQueryTextListener {

    private SearchView searchView;
    private ProgressDialog dialog;
    private MaterialData materialData;

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

        replaceTabContent(new UploadMaterialChannelFragment());

        // init data
        Material material1 = new Material();
        material1.setChannelId("ID00001");
        material1.setId("XX000001");
        material1.setName("NAME000001");
        material1.setRemark("REMARK000001");
        material1.setTransactState("STATE000001");
        material1.setTransactDate("2014-07-13");
        material1.setSubmitDate("2014-06-13");
        material1.setFeedback("FEEDBACK00001");
        material1.setFeedback("XXX000001.zip");

        Material material2 = new Material();
        material2.setChannelId("ID00002");
        material2.setId("XX000002");
        material2.setName("NAME000002");
        material2.setRemark("REMARK000002");
        material2.setTransactState("STATE000002");
        material2.setTransactDate("2014-07-13");
        material2.setSubmitDate("2014-06-13");
        material2.setFeedback("FEEDBACK00002");
        material2.setFeedback("XXX000002.zip");

        ArrayList<Material> materials = new ArrayList<Material>();
        materials.add(material1);
        materials.add(material2);

        materialData = new MaterialData();
        materialData.setMaterials(materials);
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
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

        StringBuffer url = new StringBuffer(MyGlobal.API_FETCH_LOGIN);
        url.append("&query=" + query);

        if (MyGlobal.checkNetworkConnection(this)) {

            dialog = new ProgressDialog(this);
            dialog.setMessage(getText(R.string.validating));
            dialog.setCancelable(true);
            dialog.show();

            GsonRequest gsonObjRequest = new GsonRequest<MaterialData>(Request.Method.GET, url.toString(),
                    MaterialData.class, new Response.Listener<MaterialData>() {

                @Override
                public void onResponse(MaterialData response) {
                    dialog.dismiss();

                    if (response != null && response.getSuccess().equals("true")) {

//                        materialData = response;
//                        int count = materialData.getMaterials().size();
//                        if (count == 1) {
//                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                            intent.putExtra("stId", response.getMaterials().get(0).getId());
//                            startActivity(intent);
//
//                        } else if (count > 1) {
//                            CharSequence[] jobList = new CharSequence[response.getJobInfos().size()];
//                            for (int i = 0; i < count; i++) {
//                                jobList[i] = jobData.getJobInfos().get(i).getJobName();
//                            }
//
//                            new AlertDialog.Builder(LoginActivity.this)
//                                    .setTitle(R.string.dialog_choose_job)
//                                    .setSingleChoiceItems(jobList, 0, new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            jobIndex = which;
//                                        }
//                                    })
//                                    .setNegativeButton(R.string.cancel, null)
//                                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                            intent.putExtra("stId", jobData.getJobInfos().get(jobIndex).getStId());
//                                            startActivity(intent);
//                                        }
//                                    })
//                                    .create()
//                                    .show();
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
            gsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                    MyGlobal.CONNECTION_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }

        replaceTabContent(new UploadMaterialQueryFragment());

        return true;
    }

    private void replaceTabContent(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.frame_content_upload_material, fragment);
        fragmentTransaction.commit();
    }

}
