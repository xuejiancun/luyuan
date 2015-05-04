package com.luyuan.mobile.ui;


import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.FunctionData;
import com.luyuan.mobile.model.JobData;
import com.luyuan.mobile.model.SuccessData;
import com.luyuan.mobile.model.User;
import com.luyuan.mobile.util.AppUtil;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MD5Util;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;
import com.luyuan.mobile.widget.GestureContentView;
import com.luyuan.mobile.widget.GestureDrawline.GestureCallBack;

/**
 * 
 * 手势绘制/校验界面
 *
 */
public class GestureVerifyActivity extends Activity implements View.OnClickListener {
	/** 手机号码*/
	public static final String PARAM_PHONE_NUMBER = "PARAM_PHONE_NUMBER";
	/** 意图 */
	public static final String PARAM_INTENT_CODE = "PARAM_INTENT_CODE";
	private RelativeLayout mTopLayout;
	private TextView mTextTitle;
	private TextView mTextCancel;
	private ImageView mImgUserLogo;
	private TextView mTextPhoneNumber;
	private TextView mTextTip;
	private FrameLayout mGestureContainer;
	private GestureContentView mGestureContentView;
	private TextView mTextForget;
	private TextView mTextOther;
	private String mParamPhoneNumber;
	private long mExitTime = 0;
	private int mParamIntentCode;
    private ProgressDialog dialog;
    private JobData jobData;
    private int jobIndex;
    private String currentVersion;
    private String sob;
    private String username;
    private String password;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        Intent intent  =  getIntent();
        String  option =   intent.getStringExtra("option");
        if(!"login".equals(option)){
            final ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(R.string.back);
        }else{
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
             requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        setContentView(R.layout.activity_gesture_verify);
		ObtainExtraData();
        //设置校验密码界面
		setUpViews();
		setUpListeners();
	}
	
	private void ObtainExtraData() {
		mParamPhoneNumber = getIntent().getStringExtra(PARAM_PHONE_NUMBER);
		mParamIntentCode = getIntent().getIntExtra(PARAM_INTENT_CODE, 0);
	}
	
	private void setUpViews() {
		mTopLayout = (RelativeLayout) findViewById(R.id.top_layout);
		mTextTitle = (TextView) findViewById(R.id.text_title);
		mTextCancel = (TextView) findViewById(R.id.text_cancel);
		mImgUserLogo = (ImageView) findViewById(R.id.user_logo);
		mTextPhoneNumber = (TextView) findViewById(R.id.text_phone_number);
		mTextTip = (TextView) findViewById(R.id.text_tip);
		mGestureContainer = (FrameLayout) findViewById(R.id.gesture_container);
		mTextForget = (TextView) findViewById(R.id.text_forget_gesture);
		mTextOther = (TextView) findViewById(R.id.text_other_account);
		//获取用户设置的手势密码
        String lockPwd  =   AppUtil.getPwd(this);
        //获取用户操作手势
        Intent intent = getIntent();
       final  String option = intent.getStringExtra("option");



		// 初始化一个显示各个点的viewGroup
		mGestureContentView = new GestureContentView(this, true, lockPwd,
				new GestureCallBack() {
					@Override
					public void onGestureCodeInput(String inputCode) {
					}
					@Override
					public void checkedSuccess() {
						mGestureContentView.clearDrawlineState(0L);
						Toast.makeText(GestureVerifyActivity.this, "密码正确", Toast.LENGTH_SHORT).show();
                        if("reset".equals(option)){
                             //核对完原始密码后跳转到设置密码用户界面
                            Intent intent = new Intent(GestureVerifyActivity.this , GestureEditActivity.class);
                            startActivity(intent);
                            GestureVerifyActivity.this.finish();
                        }else  if("login".equals(option)){
                            //向服务器发送登陆请求
                            login();
                        }
					//	GestureVerifyActivity.this.finish();
					}

					@Override
					public void checkedFail() {
						mGestureContentView.clearDrawlineState(1300L);
						mTextTip.setVisibility(View.VISIBLE);
						mTextTip.setText(Html
								.fromHtml("<font color='#c70c1e'>密码错误</font>"));
						// 左右移动动画
						Animation shakeAnimation = AnimationUtils.loadAnimation(GestureVerifyActivity.this, R.anim.shake);
						mTextTip.startAnimation(shakeAnimation);
					}
				});
		// 设置手势解锁显示到哪个布局里面
		mGestureContentView.setParentView(mGestureContainer);
	}
	
	private void setUpListeners() {
		mTextCancel.setOnClickListener(this);
		mTextForget.setOnClickListener(this);
		mTextOther.setOnClickListener(this);
	}
	
	private String getProtectedMobile(String phoneNumber) {
		if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 11) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		builder.append(phoneNumber.subSequence(0,3));
		builder.append("****");
		builder.append(phoneNumber.subSequence(7,11));
		return builder.toString();
	}
	
	

	@Override
	public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_cancel:
                this.finish();
                break;
            default:
                break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    //获取本地用户信息登陆

    public void login (){
        {
            // 置空 用户＋功能列表 数据
            MyGlobal.setUser(new User());
            MyGlobal.setFunctionData(new FunctionData());

       //     sob = "";
        //    username ="";
        //    password =

         //   sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        //    editor = sharedPreferences.edit();

        //    editor.putString("username", username);
        //    editor.commit();

//        username = "xuejiancun";
//        password = "123456";

//        username = "ceshi2";
//        password = "Abc123456";

//        username = "123";
//        password = "Wlwdsa12";

//        sob = "230";
//        username = "adminhh";
//        password = "654321";
          //获取本地保存的密码
            SharedPreferences sharedPreferences = this.getSharedPreferences("lockInfo", Context.MODE_APPEND);
            sob =sharedPreferences.getString("sob", "");//status:on;off 开关
            username =sharedPreferences.getString("username", "");//status:on;off 开关
            password =sharedPreferences.getString("pwd", "");//status:on;off 开关
//          sob = "230";
//          username = "xuejiancun";
//          password = "123456";


            if (username.isEmpty()) {
                new AlertDialog.Builder(GestureVerifyActivity.this)
                        .setMessage(R.string.username_empty)
                        .setTitle(R.string.dialog_hint)
                        .setPositiveButton(R.string.dialog_confirm, null)
                        .create()
                        .show();

                return;
            }

            if (password.isEmpty()) {
                new AlertDialog.Builder(GestureVerifyActivity.this)
                        .setMessage(R.string.password_empty)
                        .setTitle(R.string.dialog_hint)
                        .setPositiveButton(R.string.dialog_confirm, null)
                        .create()
                        .show();

                return;
            }
            try {
                PackageInfo packageInfo = GestureVerifyActivity.this.getPackageManager().getPackageInfo(GestureVerifyActivity.this.getPackageName(), 0);
                currentVersion = packageInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            StringBuffer url = new StringBuffer(MyGlobal.API_FETCH_LOGIN);
            url.append("&sob=" + sob + "&username=" + username + "&password=" + MD5Util.encode(password + username + "089") + "&currentversion=" + currentVersion);

            if (MyGlobal.checkNetworkConnection(this)) {

                dialog = new ProgressDialog(this);
                dialog.setMessage(getText(R.string.validating));
                dialog.setCancelable(true);
                dialog.show();

                GsonRequest gsonObjRequest = new GsonRequest<JobData>(Request.Method.GET, url.toString(),
                        JobData.class, new Response.Listener<JobData>() {

                    @Override
                    public void onResponse(JobData response) {
                       // dialog.dismiss();

                        if (response != null && response.getSuccess().equals("true")) {
                            jobData = response;

                            // 把用户信息缓存到MyGlobal
                            User user = new User();
                            user.setId(jobData.getUserId());
                            user.setSob(sob);
                            user.setUsername(username);
                            user.setPassword(password);
                            user.setSessionId(jobData.getSessionId());
                            user.setEmail(jobData.getEmail());
                            user.setContact(jobData.getContact());

                            MyGlobal.setUser(user);

                            int count = jobData.getJobInfos().size();
                            // 如果用户对应只有一个岗位，直接登录系统
                            if (count == 1) {
                                jobIndex = 0;

                                MyGlobal.getUser().setStId(jobData.getJobInfos().get(jobIndex).getStId());
                                MyGlobal.getUser().setJob(jobData.getJobInfos().get(jobIndex).getDeptName() + jobData.getJobInfos().get(jobIndex).getJobName());
                                MyGlobal.getUser().setUnitId(jobData.getJobInfos().get(jobIndex).getUnitId());
                                MyGlobal.getUser().setHeId(jobData.getJobInfos().get(jobIndex).getHeId());
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("tab", "home");
                                startActivity(intent);

                                // 否则，让用户选择岗位
                            } else if (count > 1) {
                                final CharSequence[] jobList = new CharSequence[response.getJobInfos().size()];
                                for (int i = 0; i < count; i++) {
                                    jobList[i] = jobData.getJobInfos().get(i).getJobName();
                                }

                                new AlertDialog.Builder(GestureVerifyActivity.this)
                                        .setTitle(R.string.dialog_choose_job)
                                        .setSingleChoiceItems(jobList, 0, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                jobIndex = which;
                                            }
                                        })
                                        .setNegativeButton(R.string.cancel, null)
                                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                MyGlobal.getUser().setStId(jobData.getJobInfos().get(jobIndex).getStId());
                                                MyGlobal.getUser().setJob(jobData.getJobInfos().get(jobIndex).getDeptName());
                                                MyGlobal.getUser().setUnitId(jobData.getJobInfos().get(jobIndex).getUnitId());
                                                MyGlobal.getUser().setHeId(jobData.getJobInfos().get(jobIndex).getHeId());

                                                GsonRequest gsonObjRequest = new GsonRequest<SuccessData>(Request.Method.GET, MyGlobal.API_FETCH_ROLE+"&code=" + MyGlobal.getUser().getStId(),
                                                        SuccessData.class, new Response.Listener<SuccessData>() {

                                                    @Override
                                                    public void onResponse(SuccessData response) {
                                                        if (response != null && response.getSuccess().equals("true")) {
                                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                            intent.putExtra("tab", "home");
                                                            startActivity(intent);
                                                        } else {
                                                            new AlertDialog.Builder(GestureVerifyActivity.this)
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

                                                        new AlertDialog.Builder(GestureVerifyActivity.this)
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
                                        })
                                        .create()
                                        .show();
                            }

                        } else if (response != null && response.getSuccess().equals("false_username_error")) {
                            new AlertDialog.Builder(GestureVerifyActivity.this)
                                    .setMessage(R.string.username_error)
                                    .setTitle(R.string.dialog_hint)
                                    .setPositiveButton(R.string.dialog_confirm, null)
                                    .create()
                                    .show();
                        } else if (response != null && response.getSuccess().equals("false_password_error")) {
                            new AlertDialog.Builder(GestureVerifyActivity.this)
                                    .setMessage(R.string.password_error)
                                    .setTitle(R.string.dialog_hint)
                                    .setPositiveButton(R.string.dialog_confirm, null)
                                    .create()
                                    .show();
                            // 无语!!!
                        } else if (response != null && response.getSuccess().equals("false_account_10nlatter")) {
                            new AlertDialog.Builder(GestureVerifyActivity.this)
                                    .setMessage(R.string.account_10nlatter)
                                    .setTitle(R.string.dialog_hint)
                                    .setPositiveButton(R.string.dialog_confirm, null)
                                    .create()
                                    .show();
                        } else if (response != null && response.getSuccess().equals("false_account_disabled")) {
                            new AlertDialog.Builder(GestureVerifyActivity.this)
                                    .setMessage(R.string.false_account_disabled)
                                    .setTitle(R.string.dialog_hint)
                                    .setPositiveButton(R.string.dialog_confirm, null)
                                    .create()
                                    .show();
                        } else {
                            new AlertDialog.Builder(GestureVerifyActivity.this)
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

                        new AlertDialog.Builder(GestureVerifyActivity.this)
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











}
