package com.fcc.adapter;

import java.text.NumberFormat;
import java.util.List;

import com.fcc.R;
import com.fcc.model.Cost;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CostListArrayAdapter extends ArrayAdapter<Cost> {
	
	private static NumberFormat formatter = NumberFormat.getNumberInstance();

	public CostListArrayAdapter(Context context, int resource, List<Cost> objects) {
		super(context, resource, objects);
		formatter.setMinimumFractionDigits(2);
	}
	
	 @Override
     public View getView(int position, View convertView, ViewGroup parent) {
		 ViewHolder holder = null;
		 
		 Cost cost = getItem(position);
		 
		 if(convertView == null){
			 LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             convertView = mInflater.inflate(R.layout.budget_item, null);
             holder = new ViewHolder();
             
             holder.title = (TextView)convertView.findViewById(R.id.budgetItemTitle);
             holder.plan = (TextView)convertView.findViewById(R.id.budgetItemPlan);
             holder.fact = (TextView)convertView.findViewById(R.id.budgetItemFact);
             
             convertView.setTag(holder);
		 } else {
			 holder = (ViewHolder)convertView.getTag();
		 }
		 
		 holder.title.setText(cost.getTitle());
		 holder.plan.setText(formatter.format(cost.getPlan()));
		 holder.fact.setText(formatter.format(cost.getFact()));
		 
		 return convertView;
	 }
	
	static class ViewHolder {
		TextView title;
		TextView plan;
		TextView fact;
	}
}
