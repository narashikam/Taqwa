package com.labs.taqwa;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.labs.taqwa.apihelper.SetGetTime;
import com.labs.taqwa.apihelper.UtilsApi;
import com.labs.taqwa.database.DBManager;
import com.labs.taqwa.database.TableMain;
import com.labs.taqwa.database.TableSecond;
import com.labs.taqwa.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Setting extends Activity {
    private static final String TAG = "Setting";
    private static final int PERMISSION_TO_SELECT_IMAGE_FROM_GALLERY = 100;
    private static final int PICK_IMAGE_MULTIPLE = 200;

    String imageEncoded;
    List<String> imagesEncodedList;

    private Button btn_gallery, btn_simpan;
    private EditText edt_adzan_shubuh;
    private EditText edt_adzan_dhuha;
    private EditText edt_adzan_dzuhur;
    private EditText edt_adzan_ashr;
    private EditText edt_adzan_magrib;
    private EditText edt_adzan_isya;

    private EditText edt_iqomah_shubuh;
    private EditText edt_iqomah_dzuhur;
    private EditText edt_iqomah_ashr;
    private EditText edt_iqomah_magrib;
    private EditText edt_iqomah_isya;

    private EditText edt_mesjid;
    private EditText edt_text_berjalan;

    private Switch btn_switch;

    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btn_gallery = findViewById(R.id.btn_gallery);
        btn_simpan = findViewById(R.id.btn_simpan);
        btn_switch = findViewById(R.id.btn_switch);

        edt_adzan_shubuh = findViewById(R.id.edt_adzan_shubuh);
        edt_adzan_dhuha = findViewById(R.id.edt_adzan_dhuha);
        edt_adzan_dzuhur = findViewById(R.id.edt_adzan_dzuhur);
        edt_adzan_ashr = findViewById(R.id.edt_adzan_ashr);
        edt_adzan_magrib = findViewById(R.id.edt_adzan_magrib);
        edt_adzan_isya = findViewById(R.id.edt_adzan_isya);

        edt_iqomah_shubuh = findViewById(R.id.edt_iqomah_shubuh);
        edt_iqomah_dzuhur = findViewById(R.id.edt_iqomah_dzuhur);
        edt_iqomah_ashr = findViewById(R.id.edt_iqomah_ashr);
        edt_iqomah_magrib = findViewById(R.id.edt_iqomah_magrib);
        edt_iqomah_isya = findViewById(R.id.edt_iqomah_isya);

        edt_mesjid = findViewById(R.id.edt_mesjid);
        edt_text_berjalan = findViewById(R.id.edt_text_berjalan);

        dbManager = new DBManager(getApplicationContext());

        btn_switch.setChecked(PreferencesUtil.getAutoTime(getApplicationContext()));
        btn_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                PreferencesUtil.setAutoTime(getApplicationContext(), b);
                if (b){
                    setTimeByApi();
                }
            }
        });

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_TO_SELECT_IMAGE_FROM_GALLERY);
                } else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
                }
            }
        });

        Cursor cursorMain =  dbManager.fetch(TableMain.TABLE_MAIN, TableMain.TABLE_FIELDS, null, null, null, null);
        Cursor cursorSecond =  dbManager.fetch(TableSecond.TABLE_SECOND, TableSecond.TABLE_FIELDS, null, null, null, null);

        if (cursorMain != null){
            if (cursorMain.getCount() > 0){
                while (cursorMain.moveToNext()){
                    edt_adzan_shubuh.setText(cursorMain.getString(1));
                    edt_adzan_dhuha.setText(cursorMain.getString(2));
                    edt_adzan_dzuhur.setText(cursorMain.getString(3));
                    edt_adzan_ashr.setText(cursorMain.getString(4));
                    edt_adzan_magrib.setText(cursorMain.getString(5));
                    edt_adzan_isya.setText(cursorMain.getString(6));
                }
            }
        }

        if (cursorSecond != null){
            if (cursorSecond.getCount() > 0){
                while (cursorSecond.moveToNext()){
                    edt_mesjid.setText(cursorSecond.getString(1));
                    edt_iqomah_shubuh.setText(cursorSecond.getString(2));
                    edt_iqomah_dzuhur.setText(cursorSecond.getString(3));
                    edt_iqomah_ashr.setText(cursorSecond.getString(4));
                    edt_iqomah_magrib.setText(cursorSecond.getString(5));
                    edt_iqomah_isya.setText(cursorSecond.getString(6));

                    edt_text_berjalan.setText(cursorSecond.getString(7));
                }
            }
        }

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validasiForm(edt_mesjid)){
                    Toast.makeText(getApplicationContext(), "Pasitikan Nama Mesjid Terisi", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (!validasiFormatTime(edt_adzan_shubuh)){
                    Toast.makeText(getApplicationContext(), "Pasitikan Nama Waktu Adzan Shubuh Terisi dan Format Benar", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (!validasiFormatTime(edt_adzan_dhuha)){
                    Toast.makeText(getApplicationContext(), "Pasitikan Nama Waktu Dhuha Terisi dan Format Benar", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (!validasiFormatTime(edt_adzan_dzuhur)){
                    Toast.makeText(getApplicationContext(), "Pasitikan Nama Waktu Adzan Dzuhur Terisi dan Format Benar", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (!validasiFormatTime(edt_adzan_ashr)){
                    Toast.makeText(getApplicationContext(), "Pasitikan Nama Waktu Adzan Ashr Terisi dan Format Benar", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (!validasiFormatTime(edt_adzan_magrib)){
                    Toast.makeText(getApplicationContext(), "Pasitikan Nama Waktu Adzan Magrib Terisi dan Format Benar", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (!validasiFormatTime(edt_adzan_isya)){
                    Toast.makeText(getApplicationContext(), "Pasitikan Nama Waktu Adzan Isya Terisi dan Format Benar", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (!validasiForm(edt_iqomah_shubuh)){
                    Toast.makeText(getApplicationContext(), "Pasitikan Nama Waktu Iqomah Shubuh Terisi", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (!validasiForm(edt_iqomah_dzuhur)){
                    Toast.makeText(getApplicationContext(), "Pasitikan Nama Waktu Iqomah Dzuhur Terisi", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (!validasiForm(edt_iqomah_ashr)){
                    Toast.makeText(getApplicationContext(), "Pasitikan Nama Waktu Iqomah Ashr Terisi", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (!validasiForm(edt_iqomah_magrib)){
                    Toast.makeText(getApplicationContext(), "Pasitikan Nama Waktu Iqomah Magrib Terisi", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (!validasiForm(edt_iqomah_isya)){
                    Toast.makeText(getApplicationContext(), "Pasitikan Nama Waktu Iqomah Isya Terisi", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (!validasiForm(edt_text_berjalan)){
                    Toast.makeText(getApplicationContext(), "Pasitikan Text Berjalan Terisi", Toast.LENGTH_SHORT).show();
                    return;

                }

                ContentValues contentValuesMain = new ContentValues();
                ContentValues contentValuesSecond = new ContentValues();
                contentValuesSecond.put(TableMain.KEY_ID, 123);

                contentValuesMain.put(TableMain.KEY_ID, 123);
                contentValuesSecond.put(TableSecond.KEY_NAME_MESJID, edt_mesjid.getText().toString());
                contentValuesSecond.put(TableSecond.KEY_TEXT_BERJALAN, edt_text_berjalan.getText().toString());

                contentValuesMain.put(TableMain.KEY_ADZAN_SHUBUH, edt_adzan_shubuh.getText().toString());
                contentValuesMain.put(TableMain.KEY_ADZAN_DHUHA, edt_adzan_dhuha.getText().toString());
                contentValuesMain.put(TableMain.KEY_ADZAN_DZUHUR, edt_adzan_dzuhur.getText().toString());
                contentValuesMain.put(TableMain.KEY_ADZAN_ASHR, edt_adzan_ashr.getText().toString());
                contentValuesMain.put(TableMain.KEY_ADZAN_MAGRIB, edt_adzan_magrib.getText().toString());
                contentValuesMain.put(TableMain.KEY_ADZAN_ISYA, edt_adzan_isya.getText().toString());

                contentValuesSecond.put(TableSecond.KEY_IQOMAH_SHUBUH, edt_iqomah_shubuh.getText().toString());
                contentValuesSecond.put(TableSecond.KEY_IQOMAH_DZUHUR, edt_iqomah_dzuhur.getText().toString());
                contentValuesSecond.put(TableSecond.KEY_IQOMAH_ASHR, edt_iqomah_ashr.getText().toString());
                contentValuesSecond.put(TableSecond.KEY_IQOMAH_MAGRIB, edt_iqomah_magrib.getText().toString());
                contentValuesSecond.put(TableSecond.KEY_IQOMAH_ISYA, edt_iqomah_isya.getText().toString());

                long a = dbManager.insert(TableMain.TABLE_MAIN, contentValuesMain, true);
                long b = dbManager.insert(TableSecond.TABLE_SECOND, contentValuesSecond, true);

                if (a > 0 && b > 0){
                    finish();
                }
            }
        });
    }

    private boolean validasiForm(EditText edt){
        if (edt.getText().toString().isEmpty()){
            return false;
        }
        else {
            return true;
        }
    }

    private boolean validasiFormatTime(EditText edt){
        if (edt.getText().toString().trim().matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
                // Get the Image from data

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagesEncodedList = new ArrayList<String>();

                if (data.getData() != null) {
                    //on Single image selected

                    Uri mImageUri = data.getData();

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor.getString(columnIndex);
                    cursor.close();

                    imagesEncodedList.add(imageEncoded);
                    Utils.setListImage(imagesEncodedList);
                } else {
                    //on multiple image selected
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                        }
                        Utils.setListImage(imagesEncodedList);
                        Log.v("MainActivity", "Selected Images" + mArrayUri.size());
                    }
                }
            } else {
                Toast.makeText(this, "Tolong Pilih Gambar", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setTimeByApi(){

        SetGetTime setGetTime;

        setGetTime = UtilsApi.getWaktuSholat(getApplicationContext());

        setGetTime.getTime().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        JSONObject rslt = new JSONObject(jsonRESULTS.getString("results"));
                        JSONArray jaDateTime = new JSONArray(rslt.getString("datetime"));
                        JSONObject joTimes = new JSONObject(jaDateTime.getJSONObject(0).getString("times"));

                        edt_adzan_shubuh.setText(joTimes.getString("Fajr"));
                        edt_adzan_dhuha.setText(joTimes.getString("Sunrise"));
                        edt_adzan_dzuhur.setText(joTimes.getString("Dhuhr"));
                        edt_adzan_ashr.setText(joTimes.getString("Asr"));
                        edt_adzan_magrib.setText(joTimes.getString("Maghrib"));
                        edt_adzan_isya.setText(joTimes.getString("Isha")) ;

                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
