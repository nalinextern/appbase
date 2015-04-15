package base.base;



import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import base.IHostControl;
import base.UiMessages;
import base.ViewParams;
import extec.extec.appbase.R;

public abstract  class ViewControllerBase  implements Runnable, Handler.Callback {	
	
/**
 * Private members
 */	
private volatile boolean continueRunningRefreshThread;
private Thread refreshThread;
private ViewParams params;
private Handler handler;
private View view;
private IHostControl hostControl;
//private TextView txtTitle;

public TextView txtDate;
public TextView txtcarNumber;

//private ImageView imgTitle;
private Boolean isTitleAreaInitialized = false;
//private Boolean isTitleShow = false;
//private Boolean islogEnable = false;
private LinearLayout viewTitle = null;
public boolean isAutomaticallyStartViewController=true;
public int startThreadDelayTime=500;

	
/**
 * Protected members
 */
protected LayoutInflater inflater;
public Activity activity;	
protected int refreshThreadInterval = 1000;


/**
 * Default constructor with mandatory parameters
 * @param activity Current activity
 * @param params Parameters for the view
 */
public ViewControllerBase( IHostControl hostControl, ViewParams params ) {
    try{
	this.hostControl = hostControl;
	this.activity = this.hostControl.getActivity();
	this.inflater = ( LayoutInflater ) activity.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
	this.handler = new Handler( this );		
	setParams( params );
} catch (Exception e) {
        e.printStackTrace();
    }
}

public ViewControllerBase( IHostControl hostControl, ViewParams params, boolean  islogEnable) {
	
	this.hostControl = hostControl;
	this.activity = this.hostControl.getActivity();
	this.inflater = ( LayoutInflater ) activity.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
	this.handler = new Handler( this );		
	setParams( params );
	//this.islogEnable = islogEnable;
	/*if (islogEnable)*/
		
}

/* View creation related methods */	

public View getView() {
	if ( this.view == null )
		this.view = createView();		
	
	return this.view;			
}

public void refreshTitleView() {
	
//	if(!isTitleShow){
//		
//		((LinearLayout)this.view.findViewById(R.id.listview_header)).setVisibility(View.GONE);
//		return;
//	}
//	if ( !isTitleAreaInitialized ){
//
//		if(viewTitle == null){
//
//			viewTitle = (LinearLayout)this.view.findViewById(R.id.listview_header);
//		}
//		View view = this.getTitleView();
//
//		if(view != null){
//
//			viewTitle.addView(view);
//		}
//
//	}
		//initializeTitleArea();
	
//	if ( txtTitle != null ) {
//		txtTitle.setText( this.getTitle() );
//	}
}
//
//private void initializeTitleArea() {
//	if ( this.view == null )
//		return;
//	
//	//View parent = ( View ) this.view.getParent();
//	RelativeLayout titleParentView = (RelativeLayout) this.inflater.inflate(
//			R.layout.view_title, null, false);
//	try
//	{
//		viewTitle = (LinearLayout)this.view.findViewById(R.id.listview_header);
//		viewTitle.addView(titleParentView);
//		//if ( parent != null ) {
//			//View titleParentView = parent.findViewById( R.id.view_title );	
//			
//			if ( titleParentView != null ) {
//				this.txtTitle = ( TextView ) titleParentView.findViewById( R.id.view_title_txtTitle );
//				this.txtDate = ( TextView ) titleParentView.findViewById( R.id.view_title_txtdate);
//				this.txtcarNumber = ( TextView ) titleParentView.findViewById( R.id.view_title_txtcar_number );
//				
//				//this.imgTitle = ( ImageView ) titleParentView.findViewById( R.id.view_title_imgTitle );
//			}
//		//}
//		
//		isTitleAreaInitialized = true;
//	}
//	catch ( Exception ex ) {			
//		
//	}		
//}
	
/**
 * Creates the initial view to be shown as soon as the view appears without populating
 * data, as this would give a better user experience when navigating between views. 
 * @return The initial view
 */
protected abstract View createView();

protected abstract View getTitleView();

protected abstract String getTitle();

public void setCarNumber(String carNumber){
	
	txtcarNumber.setText(carNumber);
}

public void setDate(String date){
	
	txtDate.setText(date);
}

/* View parameters related methods */	

public ViewParams getParams() {
	return params;		
}	

public void setParams( ViewParams params ) {
	this.params = params;
	
	if ( this.continueRunningRefreshThread )
		onParamsChanged();
}	

/*public ViewParams createDefaultParams() {
	return ViewParams.createEmpty();
}*/

/**
 * This will get called when parameters are changed in the view and needs to
 * be refreshed. Server requests should be un-subscribed for the previous parameter values 
 * and new requests should be sent according new ones. 
 */
public abstract void onParamsChanged();
	
/* Send server requests related methods */

/**
 * Send server request to SUBSCRIBE for data
 */
public abstract void addServerRequest();

/**
 * Send server request to UN-SUBSCRIBE for data
 */
public abstract void removeServerRequest();	
	
/* View refresh methods */
	
/**
 * This should handle all the refresh logics for the view
 */
public abstract void refreshView();	
	
public void startInternal() {
	//if (this.islogEnable)
		//SharedMethods.logMessage("startInternal");
	
		//if ( this.params == null )
			//setParams( this.createDefaultParams() );
		
		onParamsChanged();
		refreshView();
		startRefreshThread();
	
}

public void stop() {
	removeServerRequest();
	stopRefreshThread();
}

protected void startRefreshThread() {
	
	if ( continueRunningRefreshThread && refreshThread != null &&  refreshThread.isAlive() ) {
		
		return;		
	}

	continueRunningRefreshThread = true;
	refreshThread = new Thread( this );
	refreshThread.start();
}

protected void stopRefreshThread() {
	continueRunningRefreshThread = false;		
}		
	
@Override
public void run() {
	//if (this.islogEnable)
		//SharedMethods.logMessage("run");
	while( continueRunningRefreshThread ){
		try {				
			refreshViewOnUiThread();
			Thread.sleep( refreshThreadInterval );
		} catch (InterruptedException e) {
		}									
	}		
}

/* Helper methods to execute methods in UI thread */
	
public int getTimeInterval() {
	
	return refreshThreadInterval;
}

public void setTimeInterval(int interval) {
	refreshThreadInterval = interval;
}

public final void refreshViewOnUiThread() {
	//if (this.islogEnable)
		//SharedMethods.logMessage("refreshViewOnUiThread");
	sendMessageToUiThread( Message.obtain( this.handler, UiMessages.MSG_REFRESH_VIEW ) );
}

public void sendMessageToUiThread( android.os.Message msg ) {
	
	if ( handler == null )
		return;	
	
	if ( msg == null )
		handler.sendEmptyMessage( 0 );
	else		
		handler.sendMessage( msg );			
}	

@Override
public boolean handleMessage( android.os.Message msg ) {
	
	try{
		if ( msg == null )
			return false;
		
		switch ( msg.what ) {
			case UiMessages.MSG_REFRESH_VIEW:
				refreshView();
				break;
			case UiMessages.MSG_START_VIEW_CONTROLLER:
				startInternal();
				break;
		}			
	}
	catch (Exception e) {
		
		e.printStackTrace();
	}
	return true;
}


public abstract void closeView();

public void start() {
	//if (this.islogEnable)
		//SharedMethods.logMessage("start");
	if ( handler == null )
		handler = new Handler( this );
	
	handler.sendMessageDelayed( 
			Message.obtain( handler, 
					UiMessages.MSG_START_VIEW_CONTROLLER ),
					startThreadDelayTime );
}

public void setStartThreadDelayTime(int threadDelayTime){
	startThreadDelayTime=threadDelayTime;
}

public void setAutomaticallyStartViewController(boolean isAutomaticallyStartViewController) {
	this.isAutomaticallyStartViewController = isAutomaticallyStartViewController;
}
//
//public void setIsLogEnable(boolean islogEnable) {
//	this.islogEnable = islogEnable;
//}
}
