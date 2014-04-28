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

public class SetSaveAmountActivity extends Activity implements OnClickListener {

	private SQLiteDatabase db;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_save_amount);
        
        Button button = (Button)findViewById(R.id.save_button);
        button.setOnClickListener(this);
        
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        
        TextView saveAmount = (TextView)findViewById(R.id.save_amount_description);
        Cursor cur = db.query(DatabaseHelper.FCC_INCOME_SAVE_TABLE, 
        		new String[]{"SAVE"}, 
        		"DATE='" + getIntent().getExtras().getInt("year") + "-" + getIntent().getExtras().getInt("month") + "'", null, null, null, null);
        cur.moveToFirst();
        while (cur.isAfterLast() == false) {
        	saveAmount.setText(String.valueOf(cur.getDouble(0)));
        	cur.moveToNext();
        }
        
        cur.close();
    }

	@Override
	public void onClick(View v) {
		TextView saveAmount = (TextView)findViewById(R.id.save_amount_description);
		ContentValues vals = new ContentValues();
		Cursor cur = db.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.FCC_INCOME_SAVE_TABLE + " WHERE DATE = '" + getIntent().getExtras().getInt("year") + "-" + getIntent().getExtras().getInt("month") + "'", null);
		cur.moveToFirst();
		if (cur.getInt(0) > 0) {
			// Update
			vals.put("SAVE", SpannableStringBuilder.valueOf(saveAmount.getText()).toString());
			db.update(DatabaseHelper.FCC_INCOME_SAVE_TABLE, vals, "DATE = '" + getIntent().getExtras().getInt("year") + "-" + getIntent().getExtras().getInt("month") + "'", null);
		} else {
			// Insert
			vals.put("DATE", String.valueOf(getIntent().getExtras().getInt("year") + "-" + getIntent().getExtras().getInt("month")));
			vals.put("SAVE", SpannableStringBuilder.valueOf(saveAmount.getText()).toString());
			vals.put("INCOME", "0");
			db.insert(DatabaseHelper.FCC_INCOME_SAVE_TABLE, null, vals);
		}
		
		cur.close();
		
		db.close();
		
		finish();
	}
	
	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}
	
}

