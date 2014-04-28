package com.fcc.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.fcc.R;
import com.fcc.db.DatabaseHelper;

public class AddNewCostActivity extends Activity implements OnClickListener {
	
	private SQLiteDatabase db;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.add_new_cost);
//        
//        Button button = (Button)findViewById(R.id.save_button);
//        button.setOnClickListener(this);
//        
//        DatabaseHelper dbHelper = new DatabaseHelper(this);
//        db = dbHelper.getWritableDatabase();
//        
//        if (getIntent().getExtras().containsKey("rowid")) {
//        	TextView costDescr = (TextView)findViewById(R.id.cost_description);
//    		TextView planAmount = (TextView)findViewById(R.id.plan_amount);
//    		TextView factAcount = (TextView)findViewById(R.id.fact_amount);
//        	Cursor cur = db.query(DatabaseHelper.FCC_DATA_TABLE, 
//             		new String[]{ "COST", "PLAN", "FACT" }, 
//             		"ROWID="+getIntent().getExtras().getInt("rowid"), null, null, null, null);
//            cur.moveToFirst();
//            while (cur.isAfterLast() == false) {
//            	costDescr.setText(cur.getString(0));
//            	planAmount.setText(String.valueOf(cur.getDouble(1)));
//            	factAcount.setText(String.valueOf(cur.getDouble(2)));
//             	cur.moveToNext();
//            }
//             
//            cur.close();
//            
//            setTitle(R.string.app_edit_cost);
//        }
    }

	@Override
	public void onClick(View v) {
//		TextView costDescr = (TextView)findViewById(R.id.cost_description);
//		TextView planAmount = (TextView)findViewById(R.id.plan_amount);
//		TextView factAcount = (TextView)findViewById(R.id.fact_amount);
//		
//		String costDescrText = SpannableStringBuilder.valueOf(costDescr.getText()).toString();
//		
//		if ((costDescrText != null) && (!"".equals(costDescrText.trim()))) {
//			ContentValues vals = new ContentValues();
//			vals.put("COST", costDescrText.trim());
//			vals.put("PLAN", SpannableStringBuilder.valueOf(planAmount.getText()).toString());
//			vals.put("FACT", SpannableStringBuilder.valueOf(factAcount.getText()).toString());
//			
//			boolean isUpdate = false;
//			
//			if (getIntent().getExtras().containsKey("rowid")) {
//				Cursor cur = db.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.FCC_DATA_TABLE + " WHERE ROWID = " + getIntent().getExtras().getInt("rowid"), null);
//				cur.moveToFirst();
//				if (cur.getInt(0) > 0) {
//					isUpdate = true;
//				}
//				cur.close();
//			}
//			
//			if (isUpdate) {
//				// Update
//				db.update(DatabaseHelper.FCC_DATA_TABLE, vals, "ROWID = " + getIntent().getExtras().getInt("rowid"), null);
//			} else {
//				// Insert
//				vals.put("DATE", getIntent().getExtras().getInt("year") + "-" + getIntent().getExtras().getInt("month"));
//				db.insert(DatabaseHelper.FCC_DATA_TABLE, null, vals);
//			}
//		}
//		
//		db.close();
//		
//		finish();
	}

	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}
}
