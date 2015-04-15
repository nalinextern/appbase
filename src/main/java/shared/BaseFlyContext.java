package shared;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;


/**
 * Created by Sanka on 4/13/14.
 */
public class BaseFlyContext {

    public static BaseFlyContext flyContext;
    public Context context;
    public Activity activity ;
    public FragmentManager fragmentManager ;

    public static BaseFlyContext getInstant(){

        if(flyContext == null){

            flyContext = new BaseFlyContext();
        }
        return flyContext;
    }
    
    public Context getApplicationContext(){
    	
    	return context;
    }
    
    public void setApplicationContext(Context c){
    	
    	context = c;
    }
    public Activity getActivity(){
    	
    	return activity;
    }
    
    public void setActivity(Activity a){
    	
    	activity = a;
    }

}
