package com.gbrf;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class AwardDetailFragment extends Fragment {

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        if(container==null){
            return null;
        }
        View v = (RelativeLayout) inflater.inflate(R.layout.award_detail_fragment, container, false);
        return v;
	}


}
