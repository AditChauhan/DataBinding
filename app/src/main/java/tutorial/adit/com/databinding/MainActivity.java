package tutorial.adit.com.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import tutorial.adit.com.databinding.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
	ActivityMainBinding activityMainBinding;

	Handler mHand;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/***
		 *
		 * Capture the layout and reference it with activityMainBinding
		 */
		activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);



		mHand = new Handler(){
			/***
			 *
			 * @param msg
			 *     override the handlMesage to recieve the message being sent to the mainUI thread
			 */
			@Override
			public void handleMessage(Message msg) {


				/**
				 * using reference captures the view present in the layout
				 * and set the argument
				 */
				activityMainBinding.progressBar2.setProgress(msg.arg1);
			}
		};


		showProgress();
	}
	public void showProgress()
	{
		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i = 0; i < 100 ; i++) {
					/*
					 *    obtain a message reference and update the value
					 *    pass it to the handler of the mainUI Thread.
					 */
					Message message  = Message.obtain();
					message.arg1 = i ;
                    /*
                        send message using the message sendMessage to the mainUI thread
                     */
					mHand.sendMessage(message);
					try {
						Thread.sleep(100);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		th.start();
	}
}
