package com.votors.runningx;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by lenovo on 2017/10/16.
 */

public class EditpetFragment extends Fragment {

    EditText ednametx,edbreddtx;
    ImageView edpetimage;
    RadioGroup edgendergp,edstergp;
    RadioButton genderbt,sterbt,malebt,femalebt,yesbt,nobt;
    Button edit;
    TextView tvbirthday,tvarrive;



    public EditpetFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_editpet, container, false);

        edpetimage=(ImageView)rootView.findViewById(R.id.petimgview);
        ednametx=(EditText) rootView.findViewById(R.id.ededtname);
        edbreddtx=(EditText) rootView.findViewById(R.id.ededtbreed);
        tvbirthday=(TextView)rootView.findViewById(R.id.edtvbir) ;
        tvarrive=(TextView)rootView.findViewById(R.id.edtvarv) ;
        edgendergp=(RadioGroup)rootView.findViewById(R.id.edrbggender);
        edstergp=(RadioGroup)rootView.findViewById(R.id.edrbgster);
        malebt=(RadioButton)rootView.findViewById(R.id.edrbmale);
        femalebt=(RadioButton)rootView.findViewById(R.id.edrbfemale);
        yesbt=(RadioButton)rootView.findViewById(R.id.edrbYes);
        nobt=(RadioButton)rootView.findViewById(R.id.edrbNo);
        edit=(Button) rootView.findViewById(R.id.edpet);


        SharedPreferences editor=this.getActivity().getSharedPreferences("Pet",MODE_PRIVATE);
        String breed=editor.getString("BREED","");
        String birthday=editor.getString("BIRTHDAY","");
        String arrivetime=editor.getString("ARRIVETIME","");
        String gender=editor.getString("GENDER","");
        String ster=editor.getString("STER","");
        String name= editor.getString("NAME","");
        String picture=editor.getString("PICTURE","");
        Bitmap bitmap = null;
        try
        {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(picture, Base64.DEFAULT);
            bitmap =
                    BitmapFactory.decodeByteArray(bitmapArray, 0,
                            bitmapArray.length);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        if(!name.equalsIgnoreCase(null)&&!name.equalsIgnoreCase("")){
            edpetimage.setImageBitmap(bitmap);

            int genderid,sterid;
            if (gender.equalsIgnoreCase("male")) {
                genderid= R.id.edrbmale;
            }else{
                genderid= R.id.edrbfemale;
            }
            if(ster.equalsIgnoreCase("No")){
                sterid= R.id.edrbNo;
            }else{
                sterid= R.id.edrbYes;
            }
            genderbt=(RadioButton)rootView.findViewById(genderid);
            genderbt.toggle();
            sterbt=(RadioButton)rootView.findViewById(sterid);
            sterbt.toggle();

        }
        edbreddtx.setText(breed);
        ednametx.setText(name);
        tvbirthday.setText(birthday);
        tvarrive.setText(arrivetime);
        unchange();

        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(),addpet.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

    public void unchange(){
        ednametx.setFocusable(false);
        ednametx.setFocusableInTouchMode(false);
        edbreddtx.setFocusable(false);
        edbreddtx.setFocusableInTouchMode(false);
        tvbirthday.setFocusable(false);
        tvbirthday.setFocusableInTouchMode(false);
        tvarrive.setFocusable(false);
        tvarrive.setFocusableInTouchMode(false);
        malebt.setClickable(false);
        femalebt.setClickable(false);
        yesbt.setClickable(false);
        nobt.setClickable(false);
    }

}
