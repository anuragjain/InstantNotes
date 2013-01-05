package com.manas.anurag.InstantNotes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class DisplayNote extends Activity{

	String notename;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaynoteview);

		notename = getIntent().getStringExtra("notename");
		TextView tv = (TextView) findViewById(R.id.note);
		tv.setText(getNoteValue(notename));

		EditText targetEditText = (EditText)findViewById(R.id.hidden_edit_view); 
		targetEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					doneEditing();
				}
				return false;
			}
		});
	}

	public String getNoteValue(String name){
		SharedPreferences sp = getSharedPreferences("myprefs",MODE_PRIVATE);
		return(sp.getString(name, ""));
	}

	public void commitChangesOfCurrentNote(String value){
		SharedPreferences settings = getSharedPreferences("myprefs",MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(notename, value);
		editor.commit();

	}

	public void doneEditing() {
		String value = ((EditText)findViewById(R.id.hidden_edit_view)).getText().toString(); 
		commitChangesOfCurrentNote(value);
		ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.my_switcher);
		switcher.showPrevious();
		TextView tv = (TextView) findViewById(R.id.note);
		tv.setText(getNoteValue(notename));
	}

	public void textViewClicked(View v) {
		ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.my_switcher);
		switcher.showNext();
		EditText targetEditText = (EditText)findViewById(R.id.hidden_edit_view); 
		targetEditText.setText(getNoteValue(notename));
		targetEditText.requestFocus();
		((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
        .showSoftInput(targetEditText, InputMethodManager.SHOW_FORCED);
	}

}
