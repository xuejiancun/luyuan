package com.luyuan.mobile.function;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.luyuan.mobile.R;
import com.luyuan.mobile.ui.MainActivity;
import com.luyuan.mobile.util.MyGlobal;

public class AccountAboutMeActivity extends Activity {

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.account_about_me_activity);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.function_about_me);

        ((EditText) findViewById(R.id.edittext_account)).setText(MyGlobal.getUser().getUsername());
        ((EditText) findViewById(R.id.edittext_contact)).setText(MyGlobal.getUser().getContact());
        ((EditText) findViewById(R.id.edittext_email)).setText(MyGlobal.getUser().getEmail());
        ((EditText) findViewById(R.id.edittext_webchat)).setText("webchat2014");
        ((EditText) findViewById(R.id.edittext_job)).setText(MyGlobal.getUser().getDeptname());

        ((Button) findViewById(R.id.button_modify)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });

//        if (MyGlobal.checkNetworkConnection(getActivity())) {
//
//            dialog = new ProgressDialog(getActivity());
//            dialog.setMessage(getText(R.string.loading));
//            dialog.setCancelable(true);
//            dialog.show();
//
//            GsonRequest gsonObjRequest = new GsonRequest<ChannelData>(Request.Method.GET, MyGlobal.API_FETCH_CHANNEL,
//                    ChannelData.class, new Response.Listener<ChannelData>() {
//                @Override
//                public void onResponse(ChannelData response) {
//                    dialog.dismiss();
//
//                    if (response != null && response.getSuccess().equals("true")) {
//                        channelData = response;
//
//                        // TODO
//
//                    } else {
//                        new AlertDialog.Builder(getActivity())
//                                .setMessage(R.string.interact_data_error)
//                                .setTitle(R.string.dialog_hint)
//                                .setPositiveButton(R.string.dialog_confirm, null)
//                                .create()
//                                .show();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    dialog.dismiss();
//
//                    new AlertDialog.Builder(getActivity())
//                            .setMessage(R.string.interact_data_error)
//                            .setTitle(R.string.dialog_hint)
//                            .setPositiveButton(R.string.dialog_confirm, null)
//                            .create()
//                            .show();
//                }
//            }
//            );
//
//            RequestManager.getRequestQueue().add(gsonObjRequest);
//        }

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