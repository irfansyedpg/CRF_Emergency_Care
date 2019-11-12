package edu.aku.hassannaqvi.kmc_screening.validation;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.edittextpicker.aliazaz.EditTextPicker;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.lang.reflect.Field;

import edu.aku.hassannaqvi.kmc_screening.R;

import static android.content.Context.MODE_PRIVATE;


/**
 * created by irfan syed 11/11/2019

 */

public abstract class UIirfan {

    public  static SharedPreferences prefs;


    public static void findViews(View v,Context mcn) {

        prefs=mcn.getSharedPreferences("desing", MODE_PRIVATE);

        int Qlabel = prefs.getInt("qtxt", 0); //0 is the default value.

        int rblabel = prefs.getInt("rbtxt", 0); //0 is the default value.

        int cblable = prefs.getInt("cbtxt", 0); //0 is the default value.
        int edlable = prefs.getInt("edtxt", 0); //0 is the default value.
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    // recursively call this method
                    findViews(child,mcn);
                }
            }

            else if(v instanceof RadioButton)
            {
                RadioButton rb = (RadioButton) v;
                rb.setTextSize(rblabel);
            }

            else if(v instanceof CheckBox)
            {
                CheckBox cb = (CheckBox) v;
                cb.setTextSize(cblable);
            }

            else if(v instanceof EditText)
            {
                EditText ed = (EditText) v;
                ed.setTextSize(edlable);
            }
                else if (v instanceof TextView)
            {
                //do whatever you want ...

                TextView tx = (TextView) v;
                tx.setTextSize(Qlabel);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }







}
