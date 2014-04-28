package com.fcc.activity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.fcc.R;
import com.fcc.adapter.CostListArrayAdapter;
import com.fcc.db.DatabaseHelper;
import com.fcc.eula.Eula;
import com.fcc.model.Cost;

public class FamilyCostsCalculatorActivity extends ListActivity {
	
	final Context context = this;
	private Button buttonAdd;
	
//	// Current year
//	private int year = Calendar.getInstance().get(Calendar.YEAR);
//	// Current month
//	private int month = Calendar.getInstance().get(Calendar.MONTH);
//	
//	// Total costs amounts
//	private Cost total;
//	
//	// Total income amount
//	private double income = 0;
//	
//	// Total save amount
//	private double save = 0;
//	
//	// Result balance
//	private double balance = 0;
//	
//	private float textSize = 18;
//	
//	private int numericColumnsWidth = 110;
//	
//	private int backgroundColor = Color.BLACK;
//	
//	private int planTextColor = Color.parseColor("#99D9EA");
//	
//	private int gridColor = Color.parseColor("#FF909090");
//	
//	private SQLiteDatabase db;
//	
//	private NumberFormat formatter = NumberFormat.getNumberInstance();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        List<Cost> costItems = new ArrayList<Cost>();
//        for (int i = 0; i < 30; i ++) {
//        	costItems.add(new Cost("Test not long cost", 2000, 1000));
//        }
        
        CostListArrayAdapter adapter = new CostListArrayAdapter(this, R.layout.budget_item, costItems);
        setListAdapter(adapter);
        
        buttonAdd = (Button)findViewById(R.id.buttonAdd);
        
        buttonAdd.setOnClickListener(new OnClickListener() {
        	@Override
			public void onClick(View view) {
        		final Dialog dialog = new Dialog(context);
        		dialog.setContentView(R.layout.add_new_cost);
        		dialog.setTitle(getString(R.string.add_new_cost_dialog_title));
        		
        		final TextView costTitle = (TextView)dialog.findViewById(R.id.costTitle);
        		costTitle.setText("");
        		
        		final TextView planAmount = (TextView)dialog.findViewById(R.id.planAmount);
        		final TextView factAmount = (TextView)dialog.findViewById(R.id.factAmount);
        		
        		Button buttonSave = (Button)dialog.findViewById(R.id.buttonSave);
        		
        		buttonSave.setOnClickListener(new OnClickListener() {
    				@Override
    				public void onClick(View v) {
    					
    					String costTitleStr = costTitle.getText().toString();
    					String planAmountStr = planAmount.getText().toString();
    					String factAmountStr = factAmount.getText().toString();
    					
    					boolean validationPass = true;
    					
    					if ("".equals(costTitleStr.trim())) {
    						validationPass = false;
    						Toast.makeText(context, getString(R.string.cost_title_validation_error), Toast.LENGTH_LONG).show();
    					}
    					
    					if ("".equals(planAmountStr.trim())) {
    						validationPass = false;
    						Toast.makeText(context, getString(R.string.plan_amount_validation_error), Toast.LENGTH_LONG).show();
    					}
    					
    					if ("".equals(factAmountStr.trim())) {
    						validationPass = false;
    						Toast.makeText(context, getString(R.string.fact_amount_validation_error), Toast.LENGTH_LONG).show();
    					}
    					
    					
    					
    					if (validationPass) {
    						dialog.dismiss();
    					}
    				}
    			});
        		
        		dialog.show();
			}
        });
//        prepareMonthSpinner();
//        prepareYearSpinner();
                
        Eula.show(this);
    }
    
//    private void prepareMonthSpinner() {
//		// Prepare month list
//	    Spinner month = (Spinner)findViewById(R.id.spinnerMonth);
//	    @SuppressWarnings("rawtypes")
//		ArrayAdapter adapter = ArrayAdapter.createFromResource(
//	            this, R.array.month_names, android.R.layout.simple_spinner_item);
//	    month.setAdapter(adapter);
//	}
//	
//	@SuppressWarnings("unchecked")
//	private void prepareYearSpinner() {
//		// Prepare year list
//		Spinner year = (Spinner)findViewById(R.id.spinnerYear);
//		Integer yearsArr[] = new Integer[5];
//		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
//		int selPosition = 0;
//		for (int i = 0; i < 5; i ++) {
//	    	yearsArr[i] = Integer.valueOf(currentYear + i);
//		}
//		@SuppressWarnings("rawtypes")
//		ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, yearsArr);
//		year.setAdapter(adapter);
//	}
    
//    @Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//    	MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_menu, menu);
//        return true;
//	}
//    
//    @Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//    	Intent intent;
//    	
//    	if (item.getItemId() == R.id.add_new_cost) {
//			intent = new Intent(this, AddNewCostActivity.class);
//			intent.putExtra("month", month);
//			intent.putExtra("year", year);
//			startActivity(intent);
//			return true;
//		} else if (item.getItemId() == R.id.set_income_amount) {
//			intent = new Intent(this, SetIncomeAmountActivity.class);
//			intent.putExtra("month", month);
//			intent.putExtra("year", year);
//			startActivity(intent);
//			return true;
//		} else if (item.getItemId() == R.id.set_save_amount) {
//			intent = new Intent(this, SetSaveAmountActivity.class);
//			intent.putExtra("month", month);
//			intent.putExtra("year", year);
//			startActivity(intent);
//			return true;
//		} else if (item.getItemId() == R.id.change_month) {
//			intent = new Intent(this, ChangeMonthActivity.class);
//			intent.putExtra("month", month);
//			intent.putExtra("year", year);
//			startActivityForResult(intent, 1);
//			return true;
//		} else {
//			return super.onOptionsItemSelected(item);
//		}
//	}
//
//    @Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//	
//		if (resultCode == RESULT_OK && requestCode == 1) {
//			// Change month and year
//			month = data.getExtras().getInt("newMonth");
//			year = data.getExtras().getInt("newYear");
//		}
//	}
//
//	@Override
//	protected void onResume() {
//    	load();
//		super.onResume();
//	}
//	
//	@Override
//	protected void onDestroy() {
//		db.close();
//		super.onDestroy();
//	}
//
//	private void load() {
//    	DatabaseHelper dbHelper = new DatabaseHelper(this);
//        db = dbHelper.getReadableDatabase();
//        
//        Cursor cur = db.query(DatabaseHelper.FCC_DATA_TABLE, 
//        		new String[]{ "ROWID", "COST", "PLAN", "FACT" }, 
//        		"DATE='" + year + "-" + month + "'", null, null, null, null);
//        
//        TableLayout mainLayout = (TableLayout)findViewById(R.id.mainLayout);
//        mainLayout.removeAllViews();
//        
//        TextView monthYearTitle = (TextView)findViewById(R.id.month_year_title);
//        monthYearTitle.setText(new StringBuilder().append(getMonthFromNum(month)).append(" ").append(year).toString());
//        monthYearTitle.setOnLongClickListener(new OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View v) {
//				Intent intent = new Intent(v.getContext(), ChangeMonthActivity.class);
//				intent.putExtra("month", month);
//    			intent.putExtra("year", year);
//				startActivityForResult(intent, 1);
//    			return true;
//			}
//    	});
//        
//        addHeaderRow();
//        
//        cur.moveToFirst();
//        
//        total = new Cost("", 0, 0);
//        
//        while (cur.isAfterLast() == false) {
//        	Cost nextCost = new Cost(cur.getString(1), cur.getDouble(2), cur.getDouble(3));
//        	nextCost.setRowid(cur.getInt(0));
//        	addNewCostRow(nextCost);
//        	total.addPlan(nextCost.getPlan());
//        	total.addFact(nextCost.getFact());
//        	cur.moveToNext();
//        }
//        
//        income = 0;
//        save = 0;
//        cur = db.query(DatabaseHelper.FCC_INCOME_SAVE_TABLE, 
//        		new String[]{ "INCOME", "SAVE" }, 
//        		"DATE='" + year + "-" + month + "'", null, null, null, null);
//        cur.moveToFirst();
//        while (cur.isAfterLast() == false) {
//        	income = cur.getDouble(0);
//        	save = cur.getDouble(1);
//        	cur.moveToNext();
//        }
//        
//        cur.close();
//        
//        balance = income - total.getFact() - save;
//        
//        addTotalRow();
//        addIncomeRow();
//        addSaveRow();
//        addBalanceRow();
//        
//        db.close();
//    }
//
//	private String getMonthFromNum(int month) {
//    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.month_names, 0);
//    	return (String) adapter.getItem(month);
//    }
//    
//    private void addHeaderRow() {
//    	TableLayout mainLayout = (TableLayout)findViewById(R.id.mainLayout);
//    	
//    	TableRow newRow = new TableRow(this);
//    	newRow.setBackgroundColor(gridColor);
//    	newRow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
//    	newRow.setOnLongClickListener(new OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View v) {
//				Intent intent = new Intent(v.getContext(), AddNewCostActivity.class);
//				intent.putExtra("month", month);
//    			intent.putExtra("year", year);
//    			startActivity(intent);
//    			return true;
//			}
//    	});
//    	
//    	// Cost text
//    	TextView costText = new TextView(this);
//    	costText.setText(R.string.cost_title);
//    	costText.setBackgroundColor(backgroundColor);
//    	costText.setTextSize(textSize);
//    	TableRow.LayoutParams param1 = new TableRow.LayoutParams();
//    	param1.setMargins(0, 0, 0, 2);
//    	costText.setLayoutParams(param1);
//    	costText.setTypeface(null,Typeface.BOLD);
//    	costText.setGravity(Gravity.CENTER);
//    	newRow.addView(costText);
//
//    	// Plan text
//    	TextView planText = new TextView(this);
//    	planText.setText(R.string.plan_title);
//    	planText.setBackgroundColor(backgroundColor);
//    	planText.setTextSize(textSize);
//    	planText.setTextColor(planTextColor);
//    	planText.setGravity(Gravity.CENTER);
//    	TableRow.LayoutParams param2 = new TableRow.LayoutParams();
//    	param2.setMargins(1, 0, 0, 2);
//    	planText.setLayoutParams(param2);
//    	planText.setWidth(numericColumnsWidth);
//    	planText.setTypeface(null,Typeface.BOLD);
//    	newRow.addView(planText);
//    	
//    	// Fact text
//    	TextView factText = new TextView(this);
//    	factText.setText(R.string.fact_title);
//    	factText.setBackgroundColor(backgroundColor);
//    	factText.setTextSize(textSize);
//    	TableRow.LayoutParams param3 = new TableRow.LayoutParams();
//    	param3.setMargins(1, 0, 0, 2);
//    	factText.setLayoutParams(param3);
//    	factText.setGravity(Gravity.CENTER);
//    	factText.setWidth(numericColumnsWidth);
//    	factText.setTypeface(null,Typeface.BOLD);
//    	newRow.addView(factText);
//    	
//    	mainLayout.addView(newRow);
//    }
//    
//    private void addNewCostRow(final Cost cost) {	
//    	TableLayout mainLayout = (TableLayout)findViewById(R.id.mainLayout);
//    	
//    	TableRow newRow = new TableRow(this);
//    	newRow.setBackgroundColor(gridColor);
//    	newRow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
//    	newRow.setOnLongClickListener(new OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View v) {
//				Intent intent = new Intent(v.getContext(), AddNewCostActivity.class);
//				intent.putExtra("rowid", cost.getRowid());
//				intent.putExtra("month", month);
//    			intent.putExtra("year", year);
//    			startActivity(intent);
//    			return true;
//			}
//    	});
//    	
//    	// Cost text
//    	TextView costText = new TextView(this);
//    	costText.setText(cost.getName());
//    	costText.setBackgroundColor(backgroundColor);
//    	costText.setTextSize(textSize);
//    	TableRow.LayoutParams param1 = new TableRow.LayoutParams();
//    	param1.setMargins(0, 0, 0, 1);
//    	costText.setLayoutParams(param1);
//    	newRow.addView(costText);
//
//    	// Plan text
//    	TextView planText = new TextView(this);
//    	planText.setText(String.valueOf(formatter.format(cost.getPlan())) + " ");
//    	planText.setBackgroundColor(backgroundColor);
//    	planText.setTextSize(textSize);
//    	planText.setTextColor(planTextColor);
//    	planText.setGravity(Gravity.RIGHT);
//    	TableRow.LayoutParams param2 = new TableRow.LayoutParams();
//    	param2.setMargins(1, 0, 0, 1);
//    	planText.setLayoutParams(param2);
//    	planText.setWidth(numericColumnsWidth);
//    	newRow.addView(planText);
//    	
//    	// Fact text
//    	TextView factText = new TextView(this);
//    	factText.setText(String.valueOf(formatter.format(cost.getFact())) + " ");
//    	factText.setBackgroundColor(backgroundColor);
//    	factText.setTextSize(textSize);
//    	TableRow.LayoutParams param3 = new TableRow.LayoutParams();
//    	param3.setMargins(1, 0, 0, 1);
//    	factText.setLayoutParams(param3);
//    	factText.setGravity(Gravity.RIGHT);
//    	factText.setWidth(numericColumnsWidth);
//    	newRow.addView(factText);
//    	
//    	mainLayout.addView(newRow);
//    }
//    
//    private void addTotalRow() {
//    	TableLayout mainLayout = (TableLayout)findViewById(R.id.mainLayout);
//    	
//    	TableRow newRow = new TableRow(this);
//    	newRow.setBackgroundColor(gridColor);
//    	newRow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
//    	
//    	// Total title
//    	TextView costText = new TextView(this);
//    	costText.setText(R.string.total_title);
//    	costText.setBackgroundColor(backgroundColor);
//    	costText.setTextSize(textSize);
//    	TableRow.LayoutParams param1 = new TableRow.LayoutParams();
//    	param1.setMargins(0, 1, 0, 1);
//    	costText.setLayoutParams(param1);
//    	costText.setTypeface(null,Typeface.BOLD);
//    	newRow.addView(costText);
//
//    	// Plan text
//    	TextView planText = new TextView(this);
//    	planText.setText(String.valueOf(formatter.format(total.getPlan())) + " ");
//    	planText.setBackgroundColor(backgroundColor);
//    	planText.setTextSize(textSize);
//    	planText.setTextColor(planTextColor);
//    	planText.setGravity(Gravity.RIGHT);
//    	TableRow.LayoutParams param2 = new TableRow.LayoutParams();
//    	param2.setMargins(1, 1, 0, 1);
//    	planText.setLayoutParams(param2);
//    	planText.setWidth(numericColumnsWidth);
//    	planText.setTypeface(null,Typeface.BOLD);
//    	newRow.addView(planText);
//    	
//    	// Fact text
//    	TextView factText = new TextView(this);
//    	factText.setText(String.valueOf(formatter.format(total.getFact())) + " ");
//    	factText.setBackgroundColor(backgroundColor);
//    	factText.setTextSize(textSize);
//    	TableRow.LayoutParams param3 = new TableRow.LayoutParams();
//    	param3.setMargins(1, 1, 0, 1);
//    	factText.setLayoutParams(param3);
//    	factText.setGravity(Gravity.RIGHT);
//    	factText.setWidth(numericColumnsWidth);
//    	factText.setTypeface(null,Typeface.BOLD);
//    	newRow.addView(factText);
//    	
//    	mainLayout.addView(newRow);
//    }
//    
//    private void addIncomeRow() {
//    	TableLayout mainLayout = (TableLayout)findViewById(R.id.mainLayout);
//    	
//    	TableRow newRow = new TableRow(this);
//    	newRow.setBackgroundColor(gridColor);
//    	newRow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
//    	newRow.setOnLongClickListener(new OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View v) {
//				Intent intent = new Intent(v.getContext(), SetIncomeAmountActivity.class);
//				intent.putExtra("month", month);
//    			intent.putExtra("year", year);
//    			startActivity(intent);
//    			return true;
//			}
//    	});
//    	
//    	// Cost text
//    	TextView costText = new TextView(this);
//    	costText.setText(R.string.income_title);
//    	costText.setBackgroundColor(backgroundColor);
//    	costText.setTextSize(textSize);
//    	TableRow.LayoutParams param1 = new TableRow.LayoutParams();
//    	param1.span = 2;
//    	param1.setMargins(0, 0, 0, 1);
//    	costText.setLayoutParams(param1);
//    	costText.setTypeface(null,Typeface.BOLD);
//    	newRow.addView(costText);
//    	
//    	// Fact text
//    	TextView factText = new TextView(this);
//    	factText.setText(String.valueOf(formatter.format(income)) + " ");
//    	factText.setBackgroundColor(backgroundColor);
//    	factText.setTextSize(textSize);
//    	TableRow.LayoutParams param3 = new TableRow.LayoutParams();
//    	param3.setMargins(1, 0, 0, 1);
//    	factText.setLayoutParams(param3);
//    	factText.setGravity(Gravity.RIGHT);
//    	factText.setWidth(numericColumnsWidth);
//    	factText.setTypeface(null,Typeface.BOLD);
//    	newRow.addView(factText);
//    	
//    	mainLayout.addView(newRow);
//    }
//    
//    private void addSaveRow() {
//    	TableLayout mainLayout = (TableLayout)findViewById(R.id.mainLayout);
//    	
//    	TableRow newRow = new TableRow(this);
//    	newRow.setBackgroundColor(gridColor);
//    	newRow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
//    	newRow.setOnLongClickListener(new OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View v) {
//				Intent intent = new Intent(v.getContext(), SetSaveAmountActivity.class);
//				intent.putExtra("month", month);
//    			intent.putExtra("year", year);
//    			startActivity(intent);
//    			return true;
//			}
//    	});
//    	
//    	// Cost text
//    	TextView costText = new TextView(this);
//    	costText.setText(R.string.save_title);
//    	costText.setBackgroundColor(backgroundColor);
//    	costText.setTextSize(textSize);
//    	TableRow.LayoutParams param1 = new TableRow.LayoutParams();
//    	param1.span = 2;
//    	param1.setMargins(0, 0, 0, 1);
//    	costText.setLayoutParams(param1);
//    	costText.setTypeface(null,Typeface.BOLD);
//    	newRow.addView(costText);
//    	
//    	// Fact text
//    	TextView factText = new TextView(this);
//    	factText.setText(String.valueOf(formatter.format(save)) + " ");
//    	factText.setBackgroundColor(backgroundColor);
//    	factText.setTextSize(textSize);
//    	TableRow.LayoutParams param3 = new TableRow.LayoutParams();
//    	param3.setMargins(1, 0, 0, 1);
//    	factText.setLayoutParams(param3);
//    	factText.setGravity(Gravity.RIGHT);
//    	factText.setWidth(numericColumnsWidth);
//    	factText.setTypeface(null,Typeface.BOLD);
//    	newRow.addView(factText);
//    	
//    	mainLayout.addView(newRow);
//    }
//    
//    private void addBalanceRow() {
//    	TableLayout mainLayout = (TableLayout)findViewById(R.id.mainLayout);
//    	
//    	TableRow newRow = new TableRow(this);
//    	newRow.setBackgroundColor(gridColor);
//    	newRow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
//    	
//    	// Cost text
//    	TextView costText = new TextView(this);
//    	costText.setText(R.string.balance_title);
//    	costText.setBackgroundColor(backgroundColor);
//    	costText.setTextSize(textSize);
//    	TableRow.LayoutParams param1 = new TableRow.LayoutParams();
//    	param1.span = 2;
//    	param1.setMargins(0, 0, 0, 2);
//    	costText.setLayoutParams(param1);
//    	costText.setTypeface(null,Typeface.BOLD);
//    	newRow.addView(costText);
//    	
//    	// Fact text
//    	TextView factText = new TextView(this);
//    	factText.setText(String.valueOf(formatter.format(balance)) + " ");
//    	factText.setBackgroundColor(backgroundColor);
//    	factText.setTextSize(textSize);
//    	TableRow.LayoutParams param3 = new TableRow.LayoutParams();
//    	param3.setMargins(1, 0, 0, 2);
//    	factText.setLayoutParams(param3);
//    	factText.setGravity(Gravity.RIGHT);
//    	factText.setWidth(numericColumnsWidth);
//    	factText.setTypeface(null,Typeface.BOLD);
//    	newRow.addView(factText);
//    	
//    	mainLayout.addView(newRow);
//    }
}