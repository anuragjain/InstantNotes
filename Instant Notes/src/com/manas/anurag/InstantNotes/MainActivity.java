package com.manas.anurag.InstantNotes;

import java.util.Map;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {

	ListView listView;
	Vector<String> values = new Vector<String>();
	MainListAdapter adapter ;
	Map<String, ?> key_values;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.mylist);

		refreshListView();
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getApplicationContext(), DisplayNote.class);
				i.putExtra("notename", values.get(position));
				startActivity(i);
				overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
			}
		}); 
		registerForContextMenu(listView);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	private void addNote() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final EditText input = new EditText(this);
	
		alert
		.setTitle("Set Title")
		.setView(input)
		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString(); 
				SharedPreferences settings = getSharedPreferences("myprefs",MODE_PRIVATE);
				SharedPreferences.Editor editor = settings.edit();
				String content = settings.getString(value, "");
				editor.putString(value, content);
				editor.commit();
				refreshListView();
			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Do nothing.
			}
		}).show();
//		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//		imm.showSoftInput(input, InputMethodManager.SHOW_FORCED);
	}

	private void delete(MenuItem item) {
		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		String tabname = values.get(info.position);
		SharedPreferences.Editor editor = getSharedPreferences("myprefs", MODE_PRIVATE).edit();
		editor.remove(tabname);
		editor.commit();
		refreshListView();
	}

	private void refreshListView() {
		SharedPreferences sp  = getSharedPreferences("myprefs", MODE_PRIVATE);
		key_values = sp.getAll();

		values.clear();
		for(String key : key_values.keySet()){
			values.add(key);
		}

		adapter = new MainListAdapter(this, android.R.id.text1, values);
		listView.setAdapter(adapter); 
	}

	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		if(v.getId() == R.id.mylist){
			inflater.inflate(R.menu.listcontextmenu, menu);
		}
	}

	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.rename:
			renameListItem(item);
			break;
		
		case R.id.delete:
			delete(item);
			break;
		}
		
		return true;
	}


	private void renameListItem(MenuItem item) {
		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final EditText input = new EditText(this);
		alert
		.setTitle("Update Name")
		.setView(input)
		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String newname = input.getText().toString(); 
				SharedPreferences settings = getSharedPreferences("myprefs",MODE_PRIVATE);
				SharedPreferences.Editor editor = settings.edit();
			
				String content = settings.getString(values.get(info.position), "");
				editor.remove(values.get(info.position));
				
				editor.putString(newname, content);
				editor.commit();
				refreshListView();
			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Do nothing.
			}
		}).show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.addnote:	
			addNote();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
