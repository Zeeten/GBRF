package com.gbrf;

import java.text.DateFormatSymbols;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Admin on 14-08-2015.
 */
public class Fragment2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        if(container==null){
            return null;
        }
        View v = (RelativeLayout) inflater.inflate(R.layout.fragment2_layout, container, false);

        Intent i = getActivity().getIntent();
		String releaseon = i.getStringExtra("releasedon");
		String[] token = releaseon.split(" ");
		String datetoken = token[0];
		String timetoken = token[1];
		String[] token1 = datetoken.split("-");
		int yeartoken = Integer.parseInt(token1[0]);
		int monthtoken = Integer.parseInt(token1[1]);
		int daytoken = Integer.parseInt(token1[2]);
        TextView txt1 = (TextView) v.findViewById(R.id.FirstTime);
        Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Aller_Bd.ttf");
        txt1.setTypeface(font1);

        TextView txt2 = (TextView) v.findViewById(R.id.PublicationWorld);
        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Aller_Bd.ttf");
        txt2.setTypeface(font2);

        Typeface font3 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/chaparral_pro_light_subhead.otf");

        TextView txt3 = (TextView) v.findViewById(R.id.ParaLine1);
        txt3.setTypeface(font3);

        TextView txt4 = (TextView) v.findViewById(R.id.ParaLine2);
//        Typeface font4 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/chaparral_pro_light_subhead.otf");
        txt4.setTypeface(font3);
        txt4.setText("over 100 countries on"+new DateFormatSymbols().getMonths()[monthtoken - 1]);

        TextView txt5 = (TextView) v.findViewById(R.id.ParaLine3);
//        Typeface font5 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/chaparral_pro_light_subhead.otf");
        txt5.setTypeface(font3);
        txt5.setText(daytoken+"th 4:00 PM in all standard");
        
        TextView txt6 = (TextView) v.findViewById(R.id.ParaLine4);
//        Typeface font6 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/chaparral_pro_light_subhead.otf");
        txt6.setTypeface(font3);

        TextView txt7 = (TextView) v.findViewById(R.id.ParaLine5);
//        Typeface font7 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/chaparral_pro_light_subhead.otf");
        txt7.setTypeface(font3);

        TextView txt8 = (TextView) v.findViewById(R.id.ParaLine6);
//        Typeface font8 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/chaparral_pro_light_subhead.otf");
        txt8.setTypeface(font3);

        TextView txt9 = (TextView) v.findViewById(R.id.ParaLine7);
        Typeface font9 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Segoe_Script_light.ttf");
        txt9.setTypeface(font9);

        TextView txt10 = (TextView) v.findViewById(R.id.Para2Line1);
        Typeface font10 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Aller_Lt.ttf");
        txt10.setTypeface(font10);

        TextView txt11 = (TextView) v.findViewById(R.id.Para2Line2);
//        Typeface font11 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Segoe_Script_light.ttf");
        txt11.setTypeface(font10);

        TextView txt12 = (TextView) v.findViewById(R.id.Para2Line3);
//        Typeface font12 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Segoe_Script_light.ttf");
        txt12.setTypeface(font10);

        TextView txt13 = (TextView) v.findViewById(R.id.Para2Line4);
//        Typeface font12 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Segoe_Script_light.ttf");
        txt13.setTypeface(font10);

        TextView txt14 = (TextView) v.findViewById(R.id.Para2Line5);
//        Typeface font12 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Segoe_Script_light.ttf");
        txt14.setTypeface(font10);

        TextView txt15 = (TextView) v.findViewById(R.id.Para2Line6);
//        Typeface font12 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Segoe_Script_light.ttf");
        txt15.setTypeface(font10);

        TextView txt16 = (TextView) v.findViewById(R.id.Para2Line7);
        Typeface font16 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Segoe_Script_light.ttf");
        txt16.setTypeface(font16);

        return v;
    }
}
