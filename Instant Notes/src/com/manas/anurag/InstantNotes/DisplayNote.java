package com.manas.anurag.InstantNotes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DisplayNote extends Activity{

	String notename;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		notename = getIntent().getStringExtra("notename");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaynoteview);
		setTitle(notename);
		final EditText targetEditText = (EditText)findViewById(R.id.hidden_edit_view); 
		targetEditText.setCursorVisible(false);

		targetEditText.setText(getNoteValue(notename));
		Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Comfortaa Regular.ttf"); 
		targetEditText.setTypeface(type);
		RelativeLayout rl = (RelativeLayout)findViewById(R.id.rl_view);
		rl.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Log.d("tag", "msg");
				targetEditText.requestFocus();
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(targetEditText, InputMethodManager.SHOW_FORCED);
				targetEditText.setCursorVisible(true);
				targetEditText.setSelection(targetEditText.getText().length());
				return true;
			}
		});

		targetEditText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				targetEditText.setCursorVisible(true);
			}
		});
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	public String getNoteValue(String name){
		SharedPreferences sp = getSharedPreferences("myprefs",MODE_PRIVATE);
		return(sp.getString(name, ""));
	}
	
	public void commitChangesOfCurrentNote(String value){
		SharedPreferences settings = getSharedPreferences("myprefs",MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		Log.d("tag", value);
		editor.putString(notename, value);
		editor.commit();

	}

	public void doneEditing() {
		EditText targetEditText = (EditText)findViewById(R.id.hidden_edit_view); 
		targetEditText.setCursorVisible(false);
		String value = targetEditText.getText().toString(); 
		commitChangesOfCurrentNote(value);
	}

	public void invokeSMSApp() {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
		EditText targetEditText = (EditText)findViewById(R.id.hidden_edit_view); 
		String msg = targetEditText.getText().toString();
        smsIntent.putExtra("sms_body", msg); 
        smsIntent.setType("vnd.android-dir/mms-sms");

        startActivity(smsIntent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.displaynotemenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.doneediting:
			EditText targetEditText = (EditText)findViewById(R.id.hidden_edit_view); 
			((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(targetEditText.getWindowToken(), 0);
			doneEditing();
			return true;
		case R.id.sms:
			invokeSMSApp();
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_in_back, R.anim.slide_out_back);
	}
}
