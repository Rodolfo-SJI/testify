package com.rodolfo.android1;

//import android.support.v7.app.ActionBarActivity;
//import android.support.v7.app.ActionBar;
//import android.support.v4.app.Fragment;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
import android.view.Menu;
//import android.view.MenuItem;
import android.view.View;
//import android.view.ViewGroup;
//import android.os.Build;
import android.widget.Button;
//import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Android1Activity extends Activity {
	
	//private static final String TAG = "Android1Activity";
	private static final String KEY_INDEX = "index";
	private static final String KEY_CHT = "cht";
	
	private Button mTrueButton;
	private Button mFalseButton;
	private Button mNextButton;
	private Button mPrevButton;
	private Button mCheatButton;
	private TextView mQuestionTextView;
	private int mCurrentIndex = 0;
	//private boolean mIsCheater;
	private boolean[] mQuestionIsCheatedOn = new boolean[] {
			false,false,false,false,false};
	
	private truefalse[] mQuestionBank = new truefalse[] {
			new truefalse(R.string.question_1, true),
			new truefalse(R.string.question_2, false),
			new truefalse(R.string.question_3, true),
			new truefalse(R.string.question_4, true),
			new truefalse(R.string.question_5, true),
			};
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {
			return;
		}
		mQuestionIsCheatedOn[mCurrentIndex] = data.getBooleanExtra(ChActivivity.EXTRA_ANSWER_SHOWN, false);
	}

	private void updateQuestion() {
		//Log.d(TAG, "Updating question text for question #" + mCurrentIndex,
		//		new Exception());
		
		int question = mQuestionBank[mCurrentIndex].getQuestion();
		mQuestionTextView.setText(question);
	}
	
	private void checkAnswer(boolean userPressedTrue) {
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
		int messageResId = 0;
		if (mQuestionIsCheatedOn[mCurrentIndex]) {
			messageResId = R.string.judgment_toast;
		} else {
		
			if (userPressedTrue == answerIsTrue) {
				messageResId = R.string.correct_t;
			} else {
				messageResId = R.string.incorrect_t;
			}
		}
		Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
	}
	
	@TargetApi(11)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_android1);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        	ActionBar actionBar = getActionBar();
        	actionBar.setSubtitle("Sleeping App");
        	}
        	
        
        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
    			updateQuestion();
    		}
    	});

        
        mTrueButton = (Button)findViewById(R.id.true_button);
        //mTrueButton = (Button)findViewById(R.id.question_text_view);
       
        mTrueButton.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		//Toast.makeText(Android1Activity.this, R.string.correct_t, Toast.LENGTH_LONG).show();
        		checkAnswer(true);
        		}
        	
        	});
        
        mFalseButton = (Button)findViewById(R.id.false_button);
        
        mFalseButton.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		//Toast.makeText(Android1Activity.this, R.string.incorrect_t , Toast.LENGTH_LONG).show();
        		checkAnswer(false);
        	    }
        	});
        
        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        		//mIsCheater= mQuestionIsCheatedOn[mCurrentIndex];
        		updateQuestion();
        	}
        });
        	

        mPrevButton = (Button)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        			
        		if (mCurrentIndex==0) { 
        			mCurrentIndex=4;
        		}else  { 
        			mCurrentIndex-=1;	
        		}
        		//mIsCheater= mQuestionIsCheatedOn[mCurrentIndex];
        		updateQuestion();
        	}
        });
        
        mCheatButton = (Button)findViewById(R.id.cheat_button);
    	mCheatButton.setOnClickListener(new View.OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			Intent i = new Intent(Android1Activity.this, ChActivivity.class);
    			boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
    			boolean currentChStateOfQ =mQuestionIsCheatedOn[mCurrentIndex];
    			i.putExtra(ChActivivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
    			i.putExtra(ChActivivity.STATE_OF_Q, currentChStateOfQ);
    			startActivityForResult(i,0);
    		}
    	});
        	
        if (savedInstanceState != null) {
        	mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        	mQuestionIsCheatedOn = savedInstanceState.getBooleanArray(KEY_CHT);
        }
        	updateQuestion();
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	super.onSaveInstanceState(savedInstanceState);
    	//Log.i(TAG, "onSaveInstanceState");
    	savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    	savedInstanceState.putBooleanArray(KEY_CHT,mQuestionIsCheatedOn);

    }
    
    public void onStart() {
    	super.onStart();
    		//Log.d(TAG, "onStart() called");
    }
    @Override
    public void onPause() {
    	super.onPause();
    	//Log.d(TAG, "onPause() called");
    }
    @Override
    public void onResume() {
    	super.onResume();
    		//Log.d(TAG, "onResume() called");
    }
    @Override
    public void onStop() {
    	super.onStop();
    	//Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	//Log.d(TAG, "onDestroy() called");
    }
    	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
          getMenuInflater().inflate(R.menu.android1, menu);
          return true;
    }

        
    }


