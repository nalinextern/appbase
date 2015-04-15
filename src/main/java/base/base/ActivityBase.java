package base.base;

import shared.BaseFlyContext;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;


public class ActivityBase extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		BaseFlyContext.getInstant().setActivity(this);
		BaseFlyContext.getInstant().setApplicationContext(getApplicationContext());
	}


	public final void replaceView(int layout, FragmentBase fragmnet) {

		try {
			if (fragmnet != null) {

				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
				fragmentTransaction.replace(layout, fragmnet, fragmnet.getTag());
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
			}

		} catch (Exception e) {

		}
	}
}
