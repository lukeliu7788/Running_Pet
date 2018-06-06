package com.votors.runningx;

/**
 * Created by mengzhang on 2017/10/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by lenovo on 2017/8/30.
 */

public class addpet extends Activity {
    private static final String TAG = "addpet";
    private static final int MY_PERMISSIONS_REQUEST_READ_PHOTOS=1;
    private static final int MY_PERMISSIONS_REQUEST_OPEN_CAMERA=2;
    private TableRow birthday,arrivedate;
    private TextView tvbir,tvarv;
    private CustomDatePicker birthdaypick,arrivedatepick;
    EditText nametx,breddtx;
    ImageView petimage;
    ListView Takepicture;
    PopupWindow window;
    RadioGroup gendergp,stergp;
    RadioButton genderbt,sterbt;
    Button addbutton;
    MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpet);

        petimage = (ImageView) findViewById(R.id.imageView);
        birthday=(TableRow)findViewById(R.id.trbsd);
        arrivedate=(TableRow)findViewById(R.id.trArve);
        nametx= (EditText)findViewById(R.id.edtname);
        breddtx= (EditText)findViewById(R.id.edtbreed);
        tvbir = (TextView) findViewById(R.id.tvbir);
        tvarv=(TextView) findViewById(R.id.tvarv);
        gendergp=(RadioGroup)findViewById(R.id.rbggender);
        stergp=(RadioGroup)findViewById(R.id.rbgster);
        addbutton=(Button)findViewById(R.id.addpet);

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birthdaypick.show(tvbir.getText().toString());
            }
        });
        arrivedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrivedatepick.show(tvarv.getText().toString());
            }
        });
        SharedPreferences editor=getSharedPreferences("Pet",MODE_PRIVATE);
        String name=editor.getString("NAME","");

        if(!name.equalsIgnoreCase(null)&&!name.equalsIgnoreCase("")){
            getData();
        }
        initDatePicker();
    }

    public void ImageChange(View v) {
        String[] datas = {"Take Picture", "Load Picture"};
        String[] strings = {"picbutton"};
        int[] ids = {R.id.picbutton};
        View popupView = addpet.this.getLayoutInflater().
                inflate(R.layout.picpopupwindow, null);
        HashMap<String, String> map1 = new HashMap<String, String>();
        HashMap<String, String> map2 = new HashMap<String, String>();
        map1.put("picbutton", "Take Picture");
        map2.put("picbutton", "Load Picture");
        ArrayList<HashMap<String, String>> applist = new ArrayList<>();
        applist.add(map1);
        applist.add(map2);
        SimpleAdapter picbutnap = new SimpleAdapter(addpet.this,
                applist, R.layout.lines, strings, ids);
        Takepicture = (ListView) popupView.findViewById(R.id.pictakelist);
        Takepicture.setAdapter(picbutnap);
        window = new PopupWindow(popupView, 1080, 240);
        window.setFocusable(true);
        window.setOutsideTouchable(false);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        window.showAtLocation(findViewById(R.id.addpet),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        window.setOnDismissListener(new PopupWindow.OnDismissListener(){
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        } );
        setupListViewListener();
    }

    private void setupListViewListener() {
        Takepicture.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Log.i(TAG, "onItemClick: "+position);
                    if (!marshMallowPermission.checkPermissionForCamera()
                            || !marshMallowPermission.checkPermissionForExternalStorage()){
                        marshMallowPermission.requestPermissionForCamera();
                    } else {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePictureIntent, MY_PERMISSIONS_REQUEST_OPEN_CAMERA);
                    }
                }else if (position==1){
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, MY_PERMISSIONS_REQUEST_READ_PHOTOS);

                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == MY_PERMISSIONS_REQUEST_OPEN_CAMERA) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                petimage.setImageBitmap(bitmap);
                window.dismiss();
            } else if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHOTOS) {
                Uri photoUri = data.getData();
                Bitmap selectedImage;
                try {
                    selectedImage = MediaStore.Images.Media.getBitmap(
                            this.getContentResolver(), photoUri);
                    petimage.setImageBitmap(selectedImage);
                    petimage.setVisibility(View.VISIBLE);
                    window.dismiss();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        tvbir.setText(now.split(" ")[0]);
        tvarv.setText(now.split(" ")[0]);
        birthdaypick = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                tvbir.setText(time.split(" ")[0]);
            }
        }, "2000-01-01 00:00", now);
        birthdaypick.showSpecificTime(false);
        birthdaypick.setIsLoop(true);
        arrivedatepick = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                tvarv.setText(time.split(" ")[0]);
            }
        }, "2010-01-01 00:00", now);
        arrivedatepick.showSpecificTime(false);
        arrivedatepick.setIsLoop(true);
    }

    public void adpcancelclik(View v){
        finish();
    }

    public void adpaddclik(View v){
        String name=nametx.getText().toString();
        if(name.length()!=0){
            Bitmap tembit=((BitmapDrawable)petimage.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            tembit.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] appicon = baos.toByteArray();
            String bitmapstr=Base64.encodeToString(appicon, Base64.DEFAULT);
            genderbt=(RadioButton)findViewById(gendergp.getCheckedRadioButtonId());
            sterbt=(RadioButton)findViewById(stergp.getCheckedRadioButtonId());
            SharedPreferences.Editor editor=getSharedPreferences("Pet",MODE_PRIVATE).edit();
            String breed=breddtx.getText().toString();
            String birthday=tvbir.getText().toString();
            String arrivetime=tvarv.getText().toString();
            String gender=genderbt.getText().toString();
            String ster=sterbt.getText().toString();
            editor.putString("NAME",name);
            editor.putString("BREED",breed);
            editor.putString("BIRTHDAY",birthday);
            editor.putString("ARRIVETIME",arrivetime);
            editor.putString("STER",ster);
            editor.putString("GENDER",gender);
            editor.putString("PICTURE",bitmapstr);
            editor.apply();
            finish();
        }
        else{
            Log.i(TAG, "adpaddclik: 2"+name);
            Toast.makeText(addpet.this,"Your per need a name",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void getData(){
        addbutton.setText("Confirm");
        SharedPreferences editor=getSharedPreferences("Pet",MODE_PRIVATE);
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
        petimage.setImageBitmap(bitmap);
        int genderid,sterid;
        if (gender.equalsIgnoreCase("male")) {
            genderid=R.id.rbmale;
        }else{
            genderid=R.id.rbfemale;
        }
        if(ster.equalsIgnoreCase("No")){
            sterid=R.id.rbNo;
        }else{
            sterid=R.id.rbYes;
        }
        genderbt=(RadioButton)findViewById(genderid);
        genderbt.toggle();
        sterbt=(RadioButton)findViewById(sterid);
        sterbt.toggle();
        breddtx.setText(breed);
        nametx.setText(name);
        tvbir.setText(birthday);
        tvarv.setText(arrivetime);
    }
}