package com.mathiasvp.streetviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.insideContentTextView) TextView cu;
    @BindView(R.id.edtTextLat) EditText edtLat;
    @BindView(R.id.edtTextLong) EditText edtLong;
    @BindView(R.id.locRadioGroup) RadioGroup radioGroup;
    @BindView(R.id.switchInputType) Switch switchIput;
    @BindViews({R.id.edtTextLat,R.id.edtTextLong}) List<EditText> requiredFields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //figuring out whats wrong with Android Studio
    }

    private void initComponents(){
        disableLocations();
        switchIput.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchIput.isChecked()){
                    enableLocations();
                    //radioGroup.setEnabled(true);
                }
                else{
                    disableLocations();
                    //radioGroup.setEnabled(false);
                }
            }
        });
    }

    //@OnClick(R.id.fab)
    public void openStreetView(){
        //Intent streetViewIntent = new Intent(this, StreetViewActivity.class);
        if (!switchIput.isChecked()){
            if(validateForm()){
                double lat = Double.valueOf(edtLat.getText().toString());
                double lng = Double.valueOf(edtLong.getText().toString());
                LatLng inputLocation = new LatLng(lat,lng);
                //passar inputLocation para a activity do StreetView
                //streetViewIntent.putExtra("COORDINATES",inputLocation);
            }
        }
        else if (radioGroup.getCheckedRadioButtonId()!=-1){
            //pegar o valor de LatLng correspondente ao id selecionado
            Toast.makeText(this,String.valueOf(radioGroup.getCheckedRadioButtonId()),Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Cu",Toast.LENGTH_LONG).show();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String checkInput(){



        if(edtLat.getText().length()>0 &&
                edtLong.getText().length()>0 ){
            return "USER";
        }
        else if (edtLat.getText().toString().isEmpty() && edtLong.getText().toString().isEmpty()){
            return "RADIO";
        }
        return null;
    }

    private void enableLocations(){
        edtLat.setEnabled(false);
        edtLong.setEnabled(false);
        for (int i=0;i<radioGroup.getChildCount();i++){
            radioGroup.getChildAt(i).setEnabled(true);
        }
    }

    private void disableLocations(){
        edtLat.setEnabled(true);
        edtLong.setEnabled(true);
        for (int i=0;i<radioGroup.getChildCount();i++){
            radioGroup.getChildAt(i).setEnabled(false);
        }
    }

    private boolean validateForm(){
        int countEmpty = 0;
        for (EditText edt: requiredFields) {
            if (edt.getText().toString().trim().equals("")) {
                edt.setError(getString(R.string.label_field_required));
                countEmpty++;
            }
        }
        return countEmpty==0;
    }
}
