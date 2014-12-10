package com.luyuan.mobile.production;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.JSONHelper;
import com.luyuan.mobile.model.ReturnJson;
import com.luyuan.mobile.model.SuccessData;
import com.luyuan.mobile.model.WarehouseBinInventoryDetaillist_detail;
import com.luyuan.mobile.model.WhBinInventoryCheckSaveForList;
import com.luyuan.mobile.model.WhBinInventoryChecks;
import com.luyuan.mobile.model.http;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarehouseBinInventoryCheckDetailActivity extends Activity  {

    private String tab = "function";
	private EditText editText1;
	private Button serch,close,baocun;
	private TextView text;
	public static  CustomLinearLayout ly1;
	public WarehouseBinInventoryDetaillist_detail listViewAdapter;
	public static ListView listView;
	private ProgressDialog progressDialog;
	public static WhBinInventoryChecks li = null ;
	private List<BasicNameValuePair> list;
	private List<String> list1;
	private List<Map<String, Object>> listItems;
	private Context c;
	private String wbName;
	private static final int ITEM_MODIFY = 1;
	private static final int ITEM_DELETE = 2;
	private String str;
	private ProgressDialog dialog;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(R.string.function_whbincheck);
		setContentView(R.layout.warehouse_whbininventorycheckdetail_activity);
		text = (TextView) findViewById(R.id.textView_kuweihao);
		close = (Button)findViewById(R.id.button_close);
		baocun = (Button)findViewById(R.id.button_baocun);

		ly1 = (CustomLinearLayout)findViewById(R.id.ly12);

		c = this;
		close.setOnClickListener(closecilck);
		baocun.setOnClickListener(baocuncilck);
		String s = "";

		//List<BasicNameValuePair>  list = new ArrayList<BasicNameValuePair>();

		//list.add( new BasicNameValuePair("UnitID",MyGlobal.getUser().getunitId()));

//		try {
//			s = http.PostData(list, "/modules/An.Warehouse.Web/Ajax/whBinInventoryCheckHandler" +
//					".ashx?fn=getlist");
//			li = JSONHelper.parseObject(s, WhBinInventoryChecks.class);
//		} catch (Exception e) {
//			e.printStackTrace();
//		};
		StringBuffer url = new StringBuffer(MyGlobal.API_WAREHOUSE_BININVENTORYCHECK_DETAIL);
		url.append("&UnitID=" + MyGlobal.getUser().getUnitId());
		if (MyGlobal.checkNetworkConnection(WarehouseBinInventoryCheckDetailActivity.this)) {
			dialog = new ProgressDialog(WarehouseBinInventoryCheckDetailActivity.this);
			dialog.setMessage(getText(R.string.search_loading));
			dialog.setCancelable(true);
			dialog.show();

			GsonRequest gsonObjRequest = new GsonRequest<WhBinInventoryChecks>(Request.Method.GET, url.toString(), WhBinInventoryChecks.class,
					new Response.Listener<WhBinInventoryChecks>() {

						@Override
						public void onResponse(WhBinInventoryChecks response) {
							dialog.dismiss();
							if (response.getSuccess().equals(true)) {
								li = response;

								listItems = new ArrayList<Map<String, Object>>();
								for (int i =0;i< li.getData().size();i++){
									Map<String, Object> map = new HashMap<String, Object>();
									map.put("ProductCode", li.getData().get(i).getProductCode());
									map.put("PrefixName", li.getData().get(i).getPrefixName());

									map.put("itemSpec", li.getData().get(i).getItemSpec());
									map.put("Qty", li.getData().get(i).getQty());
									map.put("ActualQty", li.getData().get(i).getActualQty());
									listItems.add(map);
									wbName = li.getData().get(i).getWbName();

								}
								text.setText("库位号："+wbName);
								listViewAdapter = new WarehouseBinInventoryDetaillist_detail(c, listItems);
								ly1.setCustomAdapter(listViewAdapter);


							}
						}

					}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					dialog.dismiss();

				}
			});

			RequestManager.getRequestQueue().add(gsonObjRequest);
		}





	};
	Button.OnClickListener baocuncilck = new Button.OnClickListener (){
		@SuppressLint("NewApi")
		public void onClick(View v) {
	//		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
		//			.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
			WhBinInventoryCheckSaveForList wb = new WhBinInventoryCheckSaveForList();
			//String json="";
			//String aa="";
			str="";
			//str +="[";

			for (int i = 0; i< li.getData().size();i++){

				if (((EditText) ly1.getChildAt(i).findViewById(R.id.ActualQtyItem1)).getText().toString().equals( li.getData().get(i).getActualQty() )){
				}else{
					wb.setHeID(MyGlobal.getUser().getHeId());
					wb.setUnitID(MyGlobal.getUser().getUnitId());
					wb.setActualQty(((EditText) ly1.getChildAt(i).findViewById(R.id.ActualQtyItem1)).getText().toString());
					wb.setVoucherid(li.getData().get(i).getVoucherid());
					wb.setWbID(li.getData().get(i).getWbID());
					wb.setWbName(li.getData().get(i).getWbName());
					wb.setProductCode(li.getData().get(i).getProductCode());
					wb.setPrefixName(li.getData().get(i).getPrefixName());
					wb.setItemSpec(li.getData().get(i).getItemSpec());
					wb.setQty(li.getData().get(i).getQty());
					wb.setActualQtyBack(li.getData().get(i).getActualQtyBack());
					//json = JSONHelper.toJSON(wb).replace("\"[", "[").replace("]\"", "]").replace("'", "\"");
					//str+=json+",";
				}
			}

			//str = str.substring(0,str.length()-1)+"]";

            str= new Gson().toJson(wb);
            str ="["+str+"]";
			if(str.length()>5){
				//String s = "";
				//List<BasicNameValuePair>  list = new ArrayList<BasicNameValuePair>();
				//list.add( new BasicNameValuePair("json",str));
//			    try{
//					s = http.PostData(list,
//							"/modules/An.Warehouse.Web/Ajax/whBinInventoryCheckHandler.ashx?fn=actualqtychange4lwp");
//					if (JSONHelper.parseObject(s, ReturnJson.class).getSuccess()) {
//
//						new Builder(WarehouseBinInventoryCheckDetailActivity.this)
//								.setTitle("提示").setMessage("操作成功!").setNeutralButton("知道了", new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//							}
//						}).show();
//
//					}else{
//
//
//						new Builder(WarehouseBinInventoryCheckDetailActivity.this).setTitle("提示").setMessage(JSONHelper.parseObject(s, ReturnJson.class).getData().get(0).getInfo().toString()).setNeutralButton("知道了", new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//							}
//						}).show();
//					}
//				}catch(Exception e){
//					e.printStackTrace();
//				}

                StringBuffer url = new StringBuffer(MyGlobal.API_WAREHOUSE_BININFO);
                url.append("&json=" +str);
                if (MyGlobal.checkNetworkConnection(WarehouseBinInventoryCheckDetailActivity.this)) {
                    dialog = new ProgressDialog(WarehouseBinInventoryCheckDetailActivity.this);
                    dialog.setMessage(getText(R.string.search_loading));
                    dialog.setCancelable(true);
                    dialog.show();

                    GsonRequest gsonObjRequest = new GsonRequest<SuccessData>(Request.Method.GET, url.toString(), SuccessData.class,
                            new Response.Listener<SuccessData>() {

                                @Override
                                public void onResponse(SuccessData response) {
                                    dialog.dismiss();
                                    if (response.getSuccess().equals("true")) {
                                        new AlertDialog.Builder(WarehouseBinInventoryCheckDetailActivity.this).setMessage(R.string.save_success).setTitle(R.string.dialog_hint)
                                                .setPositiveButton(R.string.dialog_confirm, null).create().show();

                                    }
                                    else
                                    {
                                        new AlertDialog.Builder(WarehouseBinInventoryCheckDetailActivity.this).setMessage(response.getData().get(0).getInfo()).setTitle(R.string.dialog_hint)
                                                .setPositiveButton(R.string.dialog_confirm, null).create().show();
                                    }
                                }

                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse( VolleyError error) {
                            dialog.dismiss();

                            new AlertDialog.Builder(WarehouseBinInventoryCheckDetailActivity.this).setMessage(error.getMessage().toString()).setTitle(R.string.dialog_hint)
                                    .setPositiveButton(R.string.dialog_confirm, null).create().show();
                        }
                    });

                    RequestManager.getRequestQueue().add(gsonObjRequest);
                }
			}else{

				new Builder(WarehouseBinInventoryCheckDetailActivity.this).setTitle("提示").setMessage("盘点数量未发生变化").setNeutralButton("知道了", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
			}
		}
	};
//	public boolean onItemLongClick(AdapterView parent, View view, int position,
//	                               long id) {
//
//		Toast t = Toast.makeText(this, position + " is long clicked",
//				Toast.LENGTH_LONG);
//		t.show();
//		return true;
//	}
//	OnItemClickListener itemListener = new OnItemClickListener() {
//		@Override
//		public void onItemClick(AdapterView<?> parent, View view, int position,
//		                        long id) {
//			LocationInventoryAdd.wbName = li.getData().get(position).getWbName();
//			LocationInventoryAdd.ProductCode = li.getData().get(position).getProductCode();
//			LocationInventoryAdd.PrefixName = li.getData().get(position).getPrefixName();
//			LocationInventoryAdd.Qty = li.getData().get(position).getQty();
//			LocationInventoryAdd.wbID = li.getData().get(position).getWbID();
//			Bundle bundle = new Bundle();
//			bundle.putString("wbName",
//					li.getData().get(position).getWbName());
//			bundle.putString("ProductCode",
//					li.getData().get(position).getProductCode());
//			bundle.putString("PrefixName",
//					li.getData().get(position).getPrefixName());
//			Intent intent = new Intent(WhBinInventoryCheckDetail.this,LocationInventoryAdd.class);
//			intent.putExtras(bundle);
//			startActivity(intent);
//
//		}
//	};
	Button.OnClickListener closecilck = new Button.OnClickListener (){
		public void onClick(View v) {
			WarehouseBinInventoryCheckDetailActivity.this.finish();
		}
	};

	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
			case ITEM_MODIFY:
				// 编辑数据
				listItems = new ArrayList<Map<String, Object>>();
				for (int i =0;i< li.getData().size();i++){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("wbName", li.getData().get(i).getWbName());
					map.put("ProductCode", li.getData().get(i).getProductCode());
					map.put("PrefixName", li.getData().get(i).getPrefixName());

					listItems.add(map);
				}
				toastShow("修改" + li.getData());


				break;
			case ITEM_DELETE:
				// 删除数据
				break;
			default:
				break;
		}
		return false;
	}


	// 长按时显示的菜单
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
		menu.setHeaderTitle("请选择操作");
		menu.add(0, ITEM_MODIFY, 0, "修改");
		menu.add(0, ITEM_DELETE, 1, "删除");
	}
	public void toastShow(String text) {
		Toast.makeText(WarehouseBinInventoryCheckDetailActivity.this, text, 1000).show();
	}


	Button.OnClickListener modifycilck = new Button.OnClickListener (){
		public void onClick(View v) {
			progressDialog = ProgressDialog.show(
					WarehouseBinInventoryCheckDetailActivity.this, "请稍等...", "正在查询数据....",
					true, false);
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
//					String s = "";
//
//					List<BasicNameValuePair>  list = new ArrayList<BasicNameValuePair>();
//					list.add( new BasicNameValuePair("wbCode",""));
//					list.add( new BasicNameValuePair("wbName",editText1
//							.getText().toString() ));
//					list.add( new BasicNameValuePair("ProductCode", "" ));
//					list.add( new BasicNameValuePair("pageIndex", "0" ));
//					list.add( new BasicNameValuePair("pageSize", "50" ));
//					try {
//						s = http.PostData(list, "/modules/An.Warehouse.Web/Ajax/wbProductCheckHandler.ashx?fn=getdetaillist");
//
//					} catch (Exception e) {
//						e.printStackTrace();
//					};
//					listItems = new ArrayList<Map<String, Object>>();
//					for (int i =0;i< li.getData().size();i++){
//						Map<String, Object> map = new HashMap<String, Object>();
//
//						map.put("Qty", li.getData().get(i).getQty());
//						listItems.add(map);
//					}
//
//					progressDialog.dismiss();

                    StringBuffer url = new StringBuffer(MyGlobal.API_WAREHOUSE_BININFOMODIFY);
                    url.append("&wbName=" + editText1.getText().toString());

                    if (MyGlobal.checkNetworkConnection(WarehouseBinInventoryCheckDetailActivity.this)) {
                        dialog = new ProgressDialog(WarehouseBinInventoryCheckDetailActivity.this);
                        dialog.setMessage(getText(R.string.search_loading));
                        dialog.setCancelable(true);
                        dialog.show();

                        GsonRequest gsonObjRequest = new GsonRequest<SuccessData>(Request.Method.GET, url.toString(), SuccessData.class,
                                new Response.Listener<SuccessData>() {

                                    @Override
                                    public void onResponse(SuccessData response) {
                                        dialog.dismiss();
                                        if (response.getSuccess().equals("true")) {
                                            new AlertDialog.Builder(WarehouseBinInventoryCheckDetailActivity.this).setMessage(R.string.save_success).setTitle(R.string.dialog_hint)
                                                    .setPositiveButton(R.string.dialog_confirm, null).create().show();

                                        }
                                        else
                                        {
                                            new AlertDialog.Builder(WarehouseBinInventoryCheckDetailActivity.this).setMessage(response.getData().get(0).getInfo()).setTitle(R.string.dialog_hint)
                                                    .setPositiveButton(R.string.dialog_confirm, null).create().show();
                                        }
                                    }

                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse( VolleyError error) {
                                dialog.dismiss();

                                new AlertDialog.Builder(WarehouseBinInventoryCheckDetailActivity.this).setMessage(error.getMessage().toString()).setTitle(R.string.dialog_hint)
                                        .setPositiveButton(R.string.dialog_confirm, null).create().show();
                            }
                        });

                        RequestManager.getRequestQueue().add(gsonObjRequest);
                    }




				}
			});
			thread.start();


		}
	};
	Button.OnClickListener deletecilck = new Button.OnClickListener (){
		public void onClick(View v) {
			progressDialog = ProgressDialog.show(
					WarehouseBinInventoryCheckDetailActivity.this, "请稍等...", "正在查询数据....",
					true, false);
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					String s = "";

					List<BasicNameValuePair>  list = new ArrayList<BasicNameValuePair>();
					list.add( new BasicNameValuePair("wbCode",""));
					list.add( new BasicNameValuePair("wbName",editText1
							.getText().toString() ));
					list.add( new BasicNameValuePair("ProductCode", "" ));
					list.add( new BasicNameValuePair("pageIndex", "0" ));
					list.add( new BasicNameValuePair("pageSize", "50" ));
					try {
						s = http.PostData(list, "/modules/An.Warehouse.Web/Ajax/wbProductCheckHandler.ashx?fn=getdetaillist");

					} catch (Exception e) {
						e.printStackTrace();
					};
					listItems = new ArrayList<Map<String, Object>>();
					for (int i =0;i< li.getData().size();i++){
						Map<String, Object> map = new HashMap<String, Object>();

						map.put("Qty", li.getData().get(i).getQty());
						listItems.add(map);
					}
					progressDialog.dismiss();
				}
			});
			thread.start();


		}
	};
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Intent intent = new Intent(getApplicationContext(), WarehouseBinInventoryCheckActivity.class);
            //intent.putExtra("stId", MyGlobal.getUser().getStId());
           // intent.putExtra("tab", "home");
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onOptionsItemSelected( MenuItem item) {

        Intent intent = new Intent(getApplicationContext(), WarehouseBinInventoryCheckActivity.class);
        //intent.putExtra("stId", MyGlobal.getUser().getStId());
        // intent.putExtra("tab", "home");
        startActivity(intent);
        return true;
    }
}
