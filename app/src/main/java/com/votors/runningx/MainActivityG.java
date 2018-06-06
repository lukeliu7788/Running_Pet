package com.votors.runningx;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by mengzhang on 2017/10/16.
 */

public class MainActivityG extends Fragment {
    View rootView;

    public MainActivityG() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main_game, container, false);

        Button btn1 = (Button)rootView.findViewById(R.id.btnGame);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
        return rootView;
    }






    public void startGame(){
        Intent intent = new Intent(getActivity(), GameActivity.class);
        startActivity(intent);
    }
}
