package com.manas.anurag.InstantNotes;

import java.util.Vector;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MainListAdapter extends ArrayAdapter<String> {
	
	Vector<String> values = new Vector<String>();
	Context ctx;
	int textViewResourceId;
	
	public MainListAdapter(Context context, int textViewResourceId, Vector<String> values){
		super(context, textViewResourceId);
		ctx = context;
		this.values = values;
		this.textViewResourceId = textViewResourceId;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return values.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return values.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
		TextView textView = (TextView) rowView.findViewById(textViewResourceId);
		
		textView.setText(values.get(position));
		Typeface type = Typeface.createFromAsset(ctx.getAssets(),"fonts/CWMAGIK_.TTF"); 
		textView.setTypeface(type);
		textView.setTextSize(30);
//		textView.setText(listItems[position]+"					"+batches.get(keys.get(position)).getRunTime().split(" ")[0]);
//		if(batches.get(keys.get(position)).getStatus().equalsIgnoreCase("FINISHED") || batches.get(keys.get(position)).getStatus().equalsIgnoreCase("SUCCESS")){
//			GradientDrawable gd = new GradientDrawable(
//		            GradientDrawable.Orientation.TOP_BOTTOM,
//		            new int[] {ctx.getResources().getColor(R.color.green),ctx.getResources().getColor(R.color.white),ctx.getResources().getColor(R.color.green)});
//		    gd.setCornerRadius(1f);
//		    rowView.setBackgroundDrawable(gd);
//		}

	return rowView;
	}
  
}