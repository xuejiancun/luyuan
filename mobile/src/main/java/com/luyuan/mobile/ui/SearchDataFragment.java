package com.luyuan.mobile.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.luyuan.mobile.R;
import com.luyuan.mobile.util.MyGlobal;

public class SearchDataFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_search_data, container, false);
        final ListView listView = (ListView) view.findViewById(R.id.listview_search_data);

        String[] values = new String[]{"=== Item A ===", "=== Item B ===", "=== Item C ===",
                "=== Item D ===", "=== Item E ===", "=== Item F ===", "=== Item G ===", "=== Item H ===",
                "=== Item I ==="};

        String api = "";
        Bundle args = getArguments();
        api = args.getString(MyGlobal.PARAM_API_URL);
//        if (args != null && args.getString(MyGlobal.PARAM_API_URL) != null) {
//            api = args.getString(MyGlobal.PARAM_API_URL);
//
//            if (!api.isEmpty() && MyGlobal.checkNetworkConnection(getActivity())) {
//
//                GsonRequest gsonObjRequest = new GsonRequest<ImagePager>(Request.Method.GET, api,
//                        ImagePager.class, new Response.Listener<ImagePager>() {
//                    @Override
//                    public void onResponse(ImagePager response) {
//                        if (response != null && response.getSuccess().equals("true")) {
//
//
//                        } else {
//                            Dialog alertDialog = new AlertDialog.Builder(getActivity())
//                                    .setMessage(R.string.fetch_data_error)
//                                    .setTitle(R.string.dialog_hint)
//                                    .setPositiveButton(R.string.dialog_confirm, null)
//                                    .create();
//                            alertDialog.show();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Dialog alertDialog = new AlertDialog.Builder(getActivity())
//                                .setMessage(R.string.fetch_data_error)
//                                .setTitle(R.string.dialog_hint)
//                                .setPositiveButton(R.string.dialog_confirm, null)
//                                .create();
//                        alertDialog.show();
//                    }
//                }
//                );
//
//                RequestManager.getRequestQueue().add(gsonObjRequest);
//                gsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
//                        MyGlobal.CONNECTION_TIMEOUT_MS,
//                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            }
//
//        } else {
//            Dialog alertDialog = new AlertDialog.Builder(getActivity())
//                    .setMessage(R.string.app_param_error)
//                    .setTitle(R.string.dialog_hint)
//                    .setPositiveButton(R.string.dialog_confirm, null)
//                    .create();
//            alertDialog.show();
//        }

        if (api.contains("query")) {
            values = new String[]{"=== Item A ===", "=== Item B ===", "=== Item C ==="};
        }

        ArrayAdapter<String> files = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);

        listView.setAdapter(files);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AccountFragment accountFragment = new AccountFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.frame_content_warehouse_voucher_manager, accountFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}