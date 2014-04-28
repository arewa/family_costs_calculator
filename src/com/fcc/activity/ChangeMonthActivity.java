package com.fcc.activity;

import java.util.Calendar;

import com.fcc.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ChangeMonthActivity extends Activity implements OnClickListener {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_month);
        
        Button button = (Button)findViewById(R.id.save_button);
        button.setOnClickListener(this);
        
        prepareMonthSpinner();
        prepareYearSpinner();
    }
	
	@Override
	public void onClick(View v) {
		Spinner year = (Spinner) findViewById(R.id.spinner_year);
		Spinner month = (Spinner) findViewById(R.id.spinner_month);
		
		Intent intent = new Intent();
		intent.putExtra("newMonth", (int)month.getSelectedItemId());
		intent.putExtra("newYear", Integer.valueOf((Integer)year.getSelectedItem()));
		
		setResult(RESULT_OK, intent);
		
		finish();
	}
	
	private void prepareMonthSpinner() {
		// Prepare month list
	    Spinner month = (Spinner) findViewById(R.id.spinner_month);
	    @SuppressWarnings("rawtypes")
		ArrayAdapter adapter = ArrayAdapter.createFromResource(
	            this, R.array.month_names, android.R.layout.simple_spinner_item);
	    month.setAdapter(adapter);
	    month.setSelection(getIntent().getExtras().getInt("month"));
	}
	
	@SuppressWarnings("unchecked")
	private void prepareYearSpinner() {
		// Prepare year list
		Spinner year = (Spinner) findViewById(R.id.spinner_year);
		Integer yearsArr[] = new Integer[5];
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int selPosition = 0;
		for (int i = 0; i < 5; i ++) {
	    	yearsArr[i] = Integer.valueOf(currentYear + i);
	    	if (yearsArr[i] == getIntent().getExtras().getInt("year")) {
	    		selPosition = i;
	    	}
		}
		@SuppressWarnings("rawtypes")
		ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, yearsArr);
		year.setAdapter(adapter);
		year.setSelection(selPosition);
	}
}
