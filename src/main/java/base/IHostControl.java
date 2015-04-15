package base;


import java.util.EventListener;

import android.app.Activity;
import android.view.ViewGroup;

import base.base.ViewControllerBase;

public interface IHostControl {

	void enableRefreshView( boolean enable );
	void addViewController( ViewControllerBase viewController, ViewGroup parent );
	Activity getActivity();
	abstract void setReloadFragment(EventListener eventListener,Object object);

}
