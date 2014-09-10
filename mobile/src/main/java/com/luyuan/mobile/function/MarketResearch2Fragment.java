package com.luyuan.mobile.function;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.luyuan.mobile.R;

public class MarketResearch2Fragment extends Fragment {

    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    private Spinner spinner4;
    private Spinner spinner5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.market_research_2_fragment, null);

        spinner1 = (Spinner) view.findViewById(R.id.no1);
        spinner2 = (Spinner) view.findViewById(R.id.no2);
        spinner3 = (Spinner) view.findViewById(R.id.no3);
        spinner4 = (Spinner) view.findViewById(R.id.no4);
        spinner5 = (Spinner) view.findViewById(R.id.no5);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.no1_1, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);
        spinner4.setAdapter(adapter);
        spinner5.setAdapter(adapter);

        ((Button) view.findViewById(R.id.button_prev)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new MarketResearch1Fragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.frame_content, fragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}