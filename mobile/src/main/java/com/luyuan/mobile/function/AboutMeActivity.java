package com.luyuan.mobile.function;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.SuccessData;
import com.luyuan.mobile.ui.MainActivity;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

public class AboutMeActivity extends Activity {

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.about_me_activity);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.function_about_me);

        ((EditText) findViewById(R.id.edittext_account)).setText(MyGlobal.getUser().getUsername());
        ((EditText) findViewById(R.id.edittext_contact)).setText(MyGlobal.getUser().getContact());
        ((EditText) findViewById(R.id.edittext_email)).setText(MyGlobal.getUser().getEmail());
        ((EditText) findViewById(R.id.edittext_job)).setText(MyGlobal.getUser().getJob());

        ((Button) findViewById(R.id.button_modify)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (MyGlobal.checkNetworkConnection(AboutMeActivity.this)) {

                    dialog = new ProgressDialog(AboutMeActivity.this);
                    dialog.setMessage(getText(R.string.submitting));
                    dialog.setCancelable(true);
                    dialog.show();

                    String url = MyGlobal.API_MODIFY_CONTACT + "&userid=" + MyGlobal.getUser().getId() + "&contract=" + ((EditText) findViewById(R.id.edittext_contact)).getText().toString().trim();

                    GsonRequest gsonObjRequest = new GsonRequest<SuccessData>(Request.Method.GET, url,
                            SuccessData.class, new Response.Listener<SuccessData>() {
                        @Override
                        public void onResponse(SuccessData response) {
                            dialog.dismiss();

                            if (response != null && response.getSuccess().equals("true")) {
                                MyGlobal.getUser().setContact(((EditText) findViewById(R.id.edittext_contact)).getText().toString().trim());
                                new AlertDialog.Builder(AboutMeActivity.this)
                                        .setMessage(R.string.submitted_success)
                                        .setTitle(R.string.dialog_hint)
                                        .setPositiveButton(R.string.dialog_confirm, null)
                                        .create()
                                        .show();

                            } else {
                                new AlertDialog.Builder(AboutMeActivity.this)
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

                            new AlertDialog.Builder(AboutMeActivity.this)
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

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("stId", MyGlobal.getUser().getStId());
            intent.putExtra("tab", "account");
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}