package edu.aku.hassannaqvi.kmc_screening.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.aku.hassannaqvi.kmc_screening.R;
import edu.aku.hassannaqvi.kmc_screening.contracts.FormsContract;
import edu.aku.hassannaqvi.kmc_screening.core.DatabaseHelper;
import edu.aku.hassannaqvi.kmc_screening.core.MainApp;
import edu.aku.hassannaqvi.kmc_screening.databinding.ActivityABinding;
import edu.aku.hassannaqvi.kmc_screening.other.DiseaseCode;
import edu.aku.hassannaqvi.kmc_screening.util.Util;
import edu.aku.hassannaqvi.kmc_screening.validation.ValidatorClass;

import static edu.aku.hassannaqvi.kmc_screening.core.MainApp.fc;

public class CRFAActivity extends AppCompatActivity {

    ActivityABinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        bi = DataBindingUtil.setContentView(this, R.layout.activity_a);
        bi.setCallback(this);

//        setTitle(R.string.f9aHeading);
        List<String> Dieascodelist = new ArrayList<>(DiseaseCode.HmDiseaseCode.keySet());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, Dieascodelist);
        bi.cra11.setThreshold(1); //will start working from first character
        bi.cra11.setAdapter(adapter);


        setupViews();
    }

    private void setupViews() {

    }

    public void BtnContinue() {
        if (formValidation()) {

            try {
                SaveDraft();
                if (UpdateDB()) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), EndingActivity.class).putExtra("complete", true));
                } else {
                    Toast.makeText(this, "Error in updating db!!", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean UpdateDB() {

        DatabaseHelper db = new DatabaseHelper(this);

        // 2. insert form
        Long rowId;
        rowId = db.addForm(fc);
        if (rowId > 0) {
            fc.set_ID(String.valueOf(rowId));
            fc.setUID((fc.getDeviceID() + fc.get_ID()));
            db.updateFormID(fc);
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;

        }
    }

    private void SaveDraft() throws JSONException {

        fc = new FormsContract();
        SharedPreferences sharedPref = getSharedPreferences("tagName", MODE_PRIVATE);
        fc.setTagID(sharedPref.getString("tagName", null));
        fc.setFormDate((DateFormat.format("dd-MM-yyyy HH:mm", new Date())).toString());
        fc.setDeviceID(MainApp.deviceId);
        fc.setUser(MainApp.userName);

        fc.setAppversion(MainApp.versionName + "." + MainApp.versionCode);

        JSONObject f1 = new JSONObject();
        Util.setGPS(this);

        JSONObject CRFA = new JSONObject();


        CRFA.put("cra01", bi.cra01.getText().toString());

        CRFA.put("cra02", bi.cra02.getText().toString());

        CRFA.put("cra03a", bi.cra03a.getText().toString());
        CRFA.put("cra03b", bi.cra03b.getText().toString());
        CRFA.put("cra03c", bi.cra03c.getText().toString());

        CRFA.put("cra04", bi.cra04.getText().toString());

        CRFA.put("cra05", bi.cra05.getText().toString());

        CRFA.put("cra06a", bi.cra06a.getText().toString());
        CRFA.put("cra06b", bi.cra06b.getText().toString());
        CRFA.put("cra06c", bi.cra06c.getText().toString());
        CRFA.put("cra06d", bi.cra06d.getText().toString());

        CRFA.put("cra07", bi.cra07.getText().toString());

        CRFA.put("cra08a", bi.cra08a.getText().toString());
        CRFA.put("cra08b", bi.cra08b.getText().toString());
        CRFA.put("cra08c", bi.cra08c.getText().toString());

        CRFA.put("cra09",
                bi.cra09a.isChecked() ? "1"
                        : bi.cra09b.isChecked() ? "2"
                        : "0");

        CRFA.put("cra10", bi.cra10.getText().toString());

        CRFA.put("cra11", DiseaseCode.HmDiseaseCode.get(bi.cra11.getText().toString()));


        CRFA.put("cra12",
                bi.cra12a.isChecked() ? "1"
                        : bi.cra12b.isChecked() ? "2"
                        : bi.cra12c.isChecked() ? "3"
                        : bi.cra12d.isChecked() ? "4"
                        : "0");
        fc.setCRFA(String.valueOf(CRFA));
        fc.setFormType("CRFA");
        fc.setstudyid(bi.cra01.getText().toString());


    }

    private boolean formValidation() {

        return ValidatorClass.EmptyCheckingContainer(this, bi.GrpCRFA);


    }


    public void BtnEnd() {

        MainApp.endActivity(this, this);
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back.", Toast.LENGTH_SHORT).show();
    }
}
