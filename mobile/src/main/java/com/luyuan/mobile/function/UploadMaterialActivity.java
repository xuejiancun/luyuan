package com.luyuan.mobile.function;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.DedicatedData;
import com.luyuan.mobile.model.DedicatedInfo;
import com.luyuan.mobile.model.Material;
import com.luyuan.mobile.model.MaterialData;
import com.luyuan.mobile.ui.MainActivity;
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
    private DedicatedData dedicatedData;
    private int dedicatedIndex;
    private String tab = "home";
    private int choose = 0;
    private String url = "";
    private String queryInfo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.function_upload_material);

        setContentView(R.layout.upload_material_activity);

        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra("tab") != null) {
            tab = intent.getStringExtra("tab");
        }

        if (savedInstanceState == null) {
            Fragment fragment = new UploadMaterialChannelFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_content, fragment);
            fragmentTransaction.commit();
        }
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
        String[] options = new String[]{"自定义通道", "门店基础资料通道"};
        this.queryInfo = query;

        new AlertDialog.Builder(UploadMaterialActivity.this)
                .setTitle(R.string.choose_query_type)
                .setSingleChoiceItems(options, 0, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        choose = which;
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (choose == 0) {
                            url = new StringBuffer(MyGlobal.API_QUERY_MATERIAL).toString();
                        } else if (choose == 1) {
                            url = new StringBuffer(MyGlobal.API_QUERY_DEDICATED).toString();
                        }
                        try {
                            url = url + "&query=" + URLEncoder.encode(queryInfo, "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        if (MyGlobal.checkNetworkConnection(UploadMaterialActivity.this)) {

                            dialog = new ProgressDialog(UploadMaterialActivity.this);
                            dialog.setMessage(getText(R.string.loading));
                            dialog.setCancelable(true);
                            dialog.show();

                            if (choose == 0) {
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
                                                CharSequence[] list = new CharSequence[materialData.getMaterials().size()];
                                                for (int i = 0; i < count; i++) {
                                                    list[i] = materialData.getMaterials().get(i).getName();
                                                }

                                                new AlertDialog.Builder(UploadMaterialActivity.this)
                                                        .setTitle(R.string.dialog_choose_material)
                                                        .setSingleChoiceItems(list, 0, new DialogInterface.OnClickListener() {
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

                                        new AlertDialog.Builder(UploadMaterialActivity.this)
                                                .setMessage(R.string.interact_data_error)
                                                .setTitle(R.string.dialog_hint)
                                                .setPositiveButton(R.string.dialog_confirm, null)
                                                .create()
                                                .show();
                                    }
                                }
                                );

                                RequestManager.getRequestQueue().add(gsonObjRequest);
                            } else if (choose == 1) {
                                GsonRequest gsonObjRequest = new GsonRequest<DedicatedData>(Request.Method.GET, url,
                                        DedicatedData.class, new Response.Listener<DedicatedData>() {

                                    @Override
                                    public void onResponse(DedicatedData response) {
                                        dialog.dismiss();

                                        if (response != null && response.getSuccess().equals("true")) {
                                            dedicatedData = response;
                                            int count = dedicatedData.getDedicatedInfos().size();
                                            if (count == 1) {
                                                dedicatedIndex = 0;
                                                replaceContentForQuery();

                                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

                                            } else if (count > 1) {
                                                CharSequence[] list = new CharSequence[dedicatedData.getDedicatedInfos().size()];
                                                for (int i = 0; i < count; i++) {
                                                    list[i] = dedicatedData.getDedicatedInfos().get(i).getLocation();
                                                }

                                                new AlertDialog.Builder(UploadMaterialActivity.this)
                                                        .setTitle(R.string.dialog_choose_material)
                                                        .setSingleChoiceItems(list, 0, new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dedicatedIndex = which;
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

                                        new AlertDialog.Builder(UploadMaterialActivity.this)
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
                    }
                })
                .setCancelable(false)
                .create()
                .show();

        return true;
    }

    private void replaceContentForQuery() {
        if (choose == 0) {
            Fragment fragment = new UploadMaterialUDFQueryFragment();
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

            fragmentTransaction.replace(R.id.frame_content, fragment);
            fragmentTransaction.commit();
        } else if (choose == 1) {
            Fragment fragment = new UploadMaterialDedicatedQueryFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

            DedicatedInfo dedicatedInfo = dedicatedData.getDedicatedInfos().get(dedicatedIndex);

            Bundle args = new Bundle();
            args.putString("id", dedicatedInfo.getId());
            args.putString("location", dedicatedInfo.getLocation());
            args.putString("area", dedicatedInfo.getArea());
            args.putString("brand", dedicatedInfo.getBrand());
            args.putString("udf", dedicatedInfo.getUdf());
            args.putString("submitDate", dedicatedInfo.getSubmitDate());
            args.putString("submitBy", dedicatedInfo.getSubmitBy());
            args.putString("attachment", dedicatedInfo.getAttachment());
            args.putString("status", dedicatedInfo.getStatus());
            fragment.setArguments(args);

            fragmentTransaction.replace(R.id.frame_content, fragment);
            fragmentTransaction.commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("stId", MyGlobal.getUser().getStId());
            intent.putExtra("tab", tab);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
