package com.example.databaset;


import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	
	TextView resultsTv;
	EditText nameEt, familyEt;
	SQLiteDatabase myDB;
	Cursor cursor;
	String table= "userNews";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 myDB = this.openOrCreateDatabase("DatabaseNews", MODE_PRIVATE, null); /// level 1 create database 
		 myDB.execSQL("CREATE TABLE IF NOT EXISTS " + table + " (f1 TEXT, f2 TEXT);"); /// level 2 create table in databse 

        
        nameEt=(EditText)findViewById(R.id.editTextName);
        familyEt=(EditText)findViewById(R.id.editTextFamily);
        resultsTv =(TextView)findViewById(R.id.textView1);
        
        Button bt=(Button)findViewById(R.id.buttonTet);
        Button btDelete =(Button)findViewById(R.id.buttonDelete);
        
        bt.setOnClickListener(new View.OnClickListener()
        {
			
			@Override
			public void onClick(View v) 
			{
				 String name =	nameEt.getText().toString();  ///// level 3 insert into database
			     String family =  familyEt.getText().toString();
			     String result = "('"+name+"','"+family+"');";
			     myDB.execSQL("INSERT INTO "
			    	     + "userNews" 
			    	     + " (f1,f2)"
			    	     + " VALUES " +result);
			     
			     
			}
		});
        
        
        bt.setOnLongClickListener(new View.OnLongClickListener() 
        {
			
			@Override
			public boolean onLongClick(View v) 
			{
           /////////////////////////////////////////////////////////////     
				   Cursor c = myDB.rawQuery("SELECT * FROM " +"userNews" , null); /// read from database

				   int Column1 = c.getColumnIndex("f1");
				   int Column2 = c.getColumnIndex("f2");
                    
				   
				   String show="";
				   // Check if our result was valid.
				   c.moveToFirst();
				   if (c != null)
				   {
				    // Loop through all Results
					   int n =1;
				    do{
				    	
				     String Name = c.getString(Column1);
				     String familyName = c.getString(Column2);
				       show+= n+") "+Name+" "+familyName+"\n ";
				       n++;
				       Log.i("do while", ""+n);
				    }while(c.moveToNext());
				   
				    resultsTv.setText(show);
				    
				    
				   }
			/////////////////////////////////////
                     return true;
			}
		});
	
        btDelete.setOnClickListener(new View.OnClickListener()
        {
			
			@Override
			public void onClick(View v)
			{
		         myDB.delete(table, "f2 = ?", new String[] { familyEt.getText().toString()});
			        
			}
		});
		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
