package com.rodolfo.android1;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ChActivivity extends Activity {
	public static final String EXTRA_ANSWER_IS_TRUE =
			"com.rodolfo.android1.answer_is_true";
	public static final String EXTRA_ANSWER_SHOWN =
			"com.rodolfo.android1.answer_shown";
	public static final String STATE_OF_Q =
			"com.rodolfo.android1.state_of_q";
	private boolean mAnswerIsTrue;	
	private TextView mAnswerTextView;
	private TextView mbuildIDTextView;
	private Button mShowAnswer;
	private static final String KEY_CHA = "cha";
	private boolean cht_state=false;
	//private String Buildx;
	
	
	private void setAnswerShownResult(boolean isAnswerShown) {
		Intent data = new Intent();
		data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
		setResult(RESULT_OK, data);
		}
	
	@Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	super.onSaveInstanceState(savedInstanceState);
    	//Log.i(TAG, "onSaveInstanceState");
    	savedInstanceState.putBoolean(KEY_CHA, cht_state);
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_c);
		
		setAnswerShownResult(getIntent().getBooleanExtra(STATE_OF_Q, false));  // save cheat state of question if true always true
		
		mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
		mAnswerTextView = (TextView)findViewById(R.id.answerTextView);
		mbuildIDTextView = (TextView)findViewById(R.id.buildID);
		mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
		
		mbuildIDTextView.setText("API Level "+Build.VERSION.SDK_INT);
		
		if (savedInstanceState != null) {
        	if (savedInstanceState.getBoolean(KEY_CHA, false)) {
        		setAnswerShownResult(true); 	
        	}
        }
		
		mShowAnswer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
					if (mAnswerIsTrue) {
						mAnswerTextView.setText(R.string.true_button);
					} else {
						mAnswerTextView.setText(R.string.false_button);
					}
					setAnswerShownResult(true);
			}
			
		});
		
	}

}
