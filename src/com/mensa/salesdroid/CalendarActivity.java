/*
 * Copyright (C) 2011 Chris Gao <chris@exina.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mensa.salesdroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CalendarView;

public class CalendarActivity extends Activity {
//	public static final String MIME_TYPE = "vnd.android.cursor.dir/vnd.exina.android.calendar.date";
//	CalendarView mView = null;
//	TextView mHit;
//	Handler mHandler = new Handler();
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//        mView = (CalendarView)findViewById(R.id.calendar);
//        mView.setOnCellTouchListener(this);
        
//        if(getIntent().getAction().equals(Intent.ACTION_PICK))
//        	findViewById(R.id.hint).setVisibility(View.INVISIBLE);
        CalendarView calendar = new CalendarView(this);
        setContentView(calendar);
    }

	

    
}