package com.labs.taqwa;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
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
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Setting extends Activity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btn_gallery = findViewById(R.id.btn_gallery);
        btn_simpan = findViewById(R.id.btn_simpan);

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

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validasiForm(edt_mesjid)){

                }
                else if (validasiForm(edt_adzan_shubuh)){

                }
                else if (validasiForm(edt_adzan_dhuha)){

                }
                else if (validasiForm(edt_adzan_dzuhur)){

                }
                else if (validasiForm(edt_adzan_ashr)){

                }
                else if (validasiForm(edt_adzan_magrib)){

                }
                else if (validasiForm(edt_adzan_isya)){

                }
                else if (validasiForm(edt_iqomah_shubuh)){

                }
                else if (validasiForm(edt_iqomah_dzuhur)){

                }
                else if (validasiForm(edt_iqomah_ashr)){

                }
                else if (validasiForm(edt_iqomah_magrib)){

                }
                else if (validasiForm(edt_iqomah_isya)){

                }
                else if (validasiForm(edt_text_berjalan)){

                }
                else {
                    Toast.makeText(getApplicationContext(), "Pasitikan semua sudah terisi", Toast.LENGTH_SHORT).show();
                    return;
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
}
