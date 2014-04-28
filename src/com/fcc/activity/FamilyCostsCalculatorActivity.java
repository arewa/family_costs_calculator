package com.fcc.activity;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;

import com.fcc.R;
import com.fcc.db.DatabaseHelper;
import com.fcc.model.Cost;

import com.fcc.eula.Eula;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class FamilyCostsCalculatorActivity extends Activity {
	// Current year
	private int year = Calendar.getInstance().get(Calendar.YEAR);
	// Current month
	private int month = Calendar.getInstance().get(Calendar.MONTH);
	
	// Total costs amounts
	private Cost total;
	
	// Total income amount
	private double income = 0;
	
	// Total save amount
	private double save = 0;
	
	// Result balance
	private double balance = 0;
	
	private float textSize = 18;
	
	private int numericColumnsWidth = 110;
	
	private int backgroundColor = Color.BLACK;
	
	private int planTextColor = Color.parseColor("#99D9EA");
	
	private int gridColor = Color.parseColor("#FF909090");
	
	private SQLiteDatabase db;
	
	private NumberFormat formatter = NumberFormat.getNumberInstance();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        formatter.setMinimumFractionDigits(2);
        
        Eula.show(this);
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	Intent intent;
    	switch (item.getItemId()) {
    		case R.id.add_new_cost:
    			intent = new Intent(this, AddNewCostActivity.class);
    			intent.putExtra("month", month);
    			intent.putExtra("year", year);
    			startActivity(intent);
    			return true;
    		case R.id.set_income_amount:
    			intent = new Intent(this, SetIncomeAmountActivity.class);
    			intent.putExtra("month", month);
    			intent.putExtra("year", year);
    			startActivity(intent);
    			return true;
    		case R.id.set_save_amount:
    			intent = new Intent(this, SetSaveAmountActivity.class);
    			intent.putExtra("month", month);
    			intent.putExtra("year", year);
    			startActivity(intent);
    			return true;
    		case R.id.change_month:
    			intent = new Intent(this, ChangeMonthActivity.class);
    			intent.putExtra("month", month);
    			intent.putExtra("year", year);
    			startActivityForResult(intent, 1);
    			return true;
    		default: 
    			return super.onOptionsItemSelected(item);
    	}
	}

    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	
		if (resultCode == RESULT_OK && requestCode == 1) {
			// Change month and year
			month = data.getExtras().getInt("newMonth");
			year = data.getExtras().getInt("newYear");
		}
	}

	@Override
	protected void onResume() {
    	load();
		super.onResume();
	}

	private void load() {
    	DatabaseHelper dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();
        
        Cursor cur = db.query(DatabaseHelper.FCC_DATA_TABLE, 
        		new String[]{ "ROWID", "COST", "PLAN", "FACT" }, 
        		"DATE='" + year + "-" + month + "'", null, null, null, null);
        
        TableLayout mainLayout = (TableLayout)findViewById(R.id.mainLayout);
        mainLayout.removeAllViews();
        
        TextView monthYearTitle = (TextView)findViewById(R.id.month_year_title);
        monthYearTitle.setText(new StringBuilder().append(getMonthFromNum(month)).append(" ").append(year).toString());
        monthYearTitle.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				Intent intent = new Intent(v.getContext(), ChangeMonthActivity.class);
				intent.putExtra("month", month);
    			intent.putExtra("year", year);
				startActivityForResult(intent, 1);
    			return true;
			}
    	});
        
        addHeaderRow();
        
        cur.moveToFirst();
        
        total = new Cost("", 0, 0);
        
        while (cur.isAfterLast() == false) {
        	Cost nextCost = new Cost(cur.getString(1), cur.getDouble(2), cur.getDouble(3));
        	nextCost.setRowid(cur.getInt(0));
        	addNewCostRow(nextCost);
        	total.addPlan(nextCost.getPlan());
        	total.addFact(nextCost.getFact());
        	cur.moveToNext();
        }
        
        income = 0;
        save = 0;
        cur = db.query(DatabaseHelper.FCC_INCOME_SAVE_TABLE, 
        		new String[]{ "INCOME", "SAVE" }, 
        		"DATE='" + year + "-" + month + "'", null, null, null, null);
        cur.moveToFirst();
        while (cur.isAfterLast() == false) {
        	income = cur.getDouble(0);
        	save = cur.getDouble(1);
        	cur.moveToNext();
        }
        
        cur.close();
        
        balance = income - total.getFact() - save;
        
        addTotalRow();
        addIncomeRow();
        addSaveRow();
        addBalanceRow();
        
        db.close();
    }

	private String getMonthFromNum(int month) {
    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.month_names, 0);
    	return (String) adapter.getItem(month);
    }
    
    private void addHeaderRow() {
    	TableLayout mainLayout = (TableLayout)findViewById(R.id.mainLayout);
    	
    	TableRow newRow = new TableRow(this);
    	newRow.setBackgroundColor(gridColor);
    	newRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    	newRow.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				Intent intent = new Intent(v.getContext(), AddNewCostActivity.class);
				intent.putExtra("month", month);
    			intent.putExtra("year", year);
    			startActivity(intent);
    			return true;
			}
    	});
    	
    	// Cost text
    	TextView costText = new TextView(this);
    	costText.setText(R.string.cost_title);
    	costText.setBackgroundColor(backgroundColor);
    	costText.setTextSize(textSize);
    	TableRow.LayoutParams param1 = new TableRow.LayoutParams();
    	param1.setMargins(0, 0, 0, 2);
    	costText.setLayoutParams(param1);
    	costText.setTypeface(null,Typeface.BOLD);
    	costText.setGravity(Gravity.CENTER);
    	newRow.addView(costText);

    	// Plan text
    	TextView planText = new TextView(this);
    	planText.setText(R.string.plan_title);
    	planText.setBackgroundColor(backgroundColor);
    	planText.setTextSize(textSize);
    	planText.setTextColor(planTextColor);
    	planText.setGravity(Gravity.CENTER);
    	TableRow.LayoutParams param2 = new TableRow.LayoutParams();
    	param2.setMargins(1, 0, 0, 2);
    	planText.setLayoutParams(param2);
    	planText.setWidth(numericColumnsWidth);
    	planText.setTypeface(null,Typeface.BOLD);
    	newRow.addView(planText);
    	
    	// Fact text
    	TextView factText = new TextView(this);
    	factText.setText(R.string.fact_title);
    	factText.setBackgroundColor(backgroundColor);
    	factText.setTextSize(textSize);
    	TableRow.LayoutParams param3 = new TableRow.LayoutParams();
    	param3.setMargins(1, 0, 0, 2);
    	factText.setLayoutParams(param3);
    	factText.setGravity(Gravity.CENTER);
    	factText.setWidth(numericColumnsWidth);
    	factText.setTypeface(null,Typeface.BOLD);
    	newRow.addView(factText);
    	
    	mainLayout.addView(newRow);
    }
    
    private void addNewCostRow(final Cost cost) {	
    	TableLayout mainLayout = (TableLayout)findViewById(R.id.mainLayout);
    	
    	TableRow newRow = new TableRow(this);
    	newRow.setBackgroundColor(gridColor);
    	newRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    	newRow.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				Intent intent = new Intent(v.getContext(), AddNewCostActivity.class);
				intent.putExtra("rowid", cost.getRowid());
				intent.putExtra("month", month);
    			intent.putExtra("year", year);
    			startActivity(intent);
    			return true;
			}
    	});
    	
    	// Cost text
    	TextView costText = new TextView(this);
    	costText.setText(cost.getName());
    	costText.setBackgroundColor(backgroundColor);
    	costText.setTextSize(textSize);
    	TableRow.LayoutParams param1 = new TableRow.LayoutParams();
    	param1.setMargins(0, 0, 0, 1);
    	costText.setLayoutParams(param1);
    	newRow.addView(costText);

    	// Plan text
    	TextView planText = new TextView(this);
    	planText.setText(String.valueOf(formatter.format(cost.getPlan())) + " ");
    	planText.setBackgroundColor(backgroundColor);
    	planText.setTextSize(textSize);
    	planText.setTextColor(planTextColor);
    	planText.setGravity(Gravity.RIGHT);
    	TableRow.LayoutParams param2 = new TableRow.LayoutParams();
    	param2.setMargins(1, 0, 0, 1);
    	planText.setLayoutParams(param2);
    	planText.setWidth(numericColumnsWidth);
    	newRow.addView(planText);
    	
    	// Fact text
    	TextView factText = new TextView(this);
    	factText.setText(String.valueOf(formatter.format(cost.getFact())) + " ");
    	factText.setBackgroundColor(backgroundColor);
    	factText.setTextSize(textSize);
    	TableRow.LayoutParams param3 = new TableRow.LayoutParams();
    	param3.setMargins(1, 0, 0, 1);
    	factText.setLayoutParams(param3);
    	factText.setGravity(Gravity.RIGHT);
    	factText.setWidth(numericColumnsWidth);
    	newRow.addView(factText);
    	
    	mainLayout.addView(newRow);
    }
    
    private void addTotalRow() {
    	TableLayout mainLayout = (TableLayout)findViewById(R.id.mainLayout);
    	
    	TableRow newRow = new TableRow(this);
    	newRow.setBackgroundColor(gridColor);
    	newRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    	
    	// Total title
    	TextView costText = new TextView(this);
    	costText.setText(R.string.total_title);
    	costText.setBackgroundColor(backgroundColor);
    	costText.setTextSize(textSize);
    	TableRow.LayoutParams param1 = new TableRow.LayoutParams();
    	param1.setMargins(0, 1, 0, 1);
    	costText.setLayoutParams(param1);
    	costText.setTypeface(null,Typeface.BOLD);
    	newRow.addView(costText);

    	// Plan text
    	TextView planText = new TextView(this);
    	planText.setText(String.valueOf(formatter.format(total.getPlan())) + " ");
    	planText.setBackgroundColor(backgroundColor);
    	planText.setTextSize(textSize);
    	planText.setTextColor(planTextColor);
    	planText.setGravity(Gravity.RIGHT);
    	TableRow.LayoutParams param2 = new TableRow.LayoutParams();
    	param2.setMargins(1, 1, 0, 1);
    	planText.setLayoutParams(param2);
    	planText.setWidth(numericColumnsWidth);
    	planText.setTypeface(null,Typeface.BOLD);
    	newRow.addView(planText);
    	
    	// Fact text
    	TextView factText = new TextView(this);
    	factText.setText(String.valueOf(formatter.format(total.getFact())) + " ");
    	factText.setBackgroundColor(backgroundColor);
    	factText.setTextSize(textSize);
    	TableRow.LayoutParams param3 = new TableRow.LayoutParams();
    	param3.setMargins(1, 1, 0, 1);
    	factText.setLayoutParams(param3);
    	factText.setGravity(Gravity.RIGHT);
    	factText.setWidth(numericColumnsWidth);
    	factText.setTypeface(null,Typeface.BOLD);
    	newRow.addView(factText);
    	
    	mainLayout.addView(newRow);
    }
    
    private void addIncomeRow() {
    	TableLayout mainLayout = (TableLayout)findViewById(R.id.mainLayout);
    	
    	TableRow newRow = new TableRow(this);
    	newRow.setBackgroundColor(gridColor);
    	newRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    	newRow.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				Intent intent = new Intent(v.getContext(), SetIncomeAmountActivity.class);
				intent.putExtra("month", month);
    			intent.putExtra("year", year);
    			startActivity(intent);
    			return true;
			}
    	});
    	
    	// Cost text
    	TextView costText = new TextView(this);
    	costText.setText(R.string.income_title);
    	costText.setBackgroundColor(backgroundColor);
    	costText.setTextSize(textSize);
    	TableRow.LayoutParams param1 = new TableRow.LayoutParams();
    	param1.span = 2;
    	param1.setMargins(0, 0, 0, 1);
    	costText.setLayoutParams(param1);
    	costText.setTypeface(null,Typeface.BOLD);
    	newRow.addView(costText);
    	
    	// Fact text
    	TextView factText = new TextView(this);
    	factText.setText(String.valueOf(formatter.format(income)) + " ");
    	factText.setBackgroundColor(backgroundColor);
    	factText.setTextSize(textSize);
    	TableRow.LayoutParams param3 = new TableRow.LayoutParams();
    	param3.setMargins(1, 0, 0, 1);
    	factText.setLayoutParams(param3);
    	factText.setGravity(Gravity.RIGHT);
    	factText.setWidth(numericColumnsWidth);
    	factText.setTypeface(null,Typeface.BOLD);
    	newRow.addView(factText);
    	
    	mainLayout.addView(newRow);
    }
    
    private void addSaveRow() {
    	TableLayout mainLayout = (TableLayout)findViewById(R.id.mainLayout);
    	
    	TableRow newRow = new TableRow(this);
    	newRow.setBackgroundColor(gridColor);
    	newRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    	newRow.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				Intent intent = new Intent(v.getContext(), SetSaveAmountActivity.class);
				intent.putExtra("month", month);
    			intent.putExtra("year", year);
    			startActivity(intent);
    			return true;
			}
    	});
    	
    	// Cost text
    	TextView costText = new TextView(this);
    	costText.setText(R.string.save_title);
    	costText.setBackgroundColor(backgroundColor);
    	costText.setTextSize(textSize);
    	TableRow.LayoutParams param1 = new TableRow.LayoutParams();
    	param1.span = 2;
    	param1.setMargins(0, 0, 0, 1);
    	costText.setLayoutParams(param1);
    	costText.setTypeface(null,Typeface.BOLD);
    	newRow.addView(costText);
    	
    	// Fact text
    	TextView factText = new TextView(this);
    	factText.setText(String.valueOf(formatter.format(save)) + " ");
    	factText.setBackgroundColor(backgroundColor);
    	factText.setTextSize(textSize);
    	TableRow.LayoutParams param3 = new TableRow.LayoutParams();
    	param3.setMargins(1, 0, 0, 1);
    	factText.setLayoutParams(param3);
    	factText.setGravity(Gravity.RIGHT);
    	factText.setWidth(numericColumnsWidth);
    	factText.setTypeface(null,Typeface.BOLD);
    	newRow.addView(factText);
    	
    	mainLayout.addView(newRow);
    }
    
    private void addBalanceRow() {
    	TableLayout mainLayout = (TableLayout)findViewById(R.id.mainLayout);
    	
    	TableRow newRow = new TableRow(this);
    	newRow.setBackgroundColor(gridColor);
    	newRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    	
    	// Cost text
    	TextView costText = new TextView(this);
    	costText.setText(R.string.balance_title);
    	costText.setBackgroundColor(backgroundColor);
    	costText.setTextSize(textSize);
    	TableRow.LayoutParams param1 = new TableRow.LayoutParams();
    	param1.span = 2;
    	param1.setMargins(0, 0, 0, 2);
    	costText.setLayoutParams(param1);
    	costText.setTypeface(null,Typeface.BOLD);
    	newRow.addView(costText);
    	
    	// Fact text
    	TextView factText = new TextView(this);
    	factText.setText(String.valueOf(formatter.format(balance)) + " ");
    	factText.setBackgroundColor(backgroundColor);
    	factText.setTextSize(textSize);
    	TableRow.LayoutParams param3 = new TableRow.LayoutParams();
    	param3.setMargins(1, 0, 0, 2);
    	factText.setLayoutParams(param3);
    	factText.setGravity(Gravity.RIGHT);
    	factText.setWidth(numericColumnsWidth);
    	factText.setTypeface(null,Typeface.BOLD);
    	newRow.addView(factText);
    	
    	mainLayout.addView(newRow);
    }
}