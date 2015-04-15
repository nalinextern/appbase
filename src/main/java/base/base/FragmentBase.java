package base.base;

import java.util.EventListener;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;

import base.IHostControl;

/**
 * Created by Sanka on 4/7/14.
 */
public abstract class FragmentBase extends Fragment implements Handler.Callback, IHostControl {

	public int viewId;
	public final void addViewController(ViewControllerBase controller,
			ViewGroup parent) {

		if (controller == null)
			return;

		View view = controller.getView();

		if (view != null) {

			if (parent != null)
				parent.addView(view);
				controller.refreshTitleView();
		}
	}

	public final void replaceView(int id, Fragment mBFrag) {

		try {
			if (mBFrag != null) {

				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
				fragmentTransaction.replace(id, mBFrag, mBFrag.getTag());
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
			}

		} catch (Exception e) {

		}
	}

    public void showDialogFragment( DialogFragment newFragment){

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        newFragment.show(ft, "dialog");
    }

	@Override
	public void enableRefreshView(boolean enable) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean handleMessage(Message message) {
		return false;
	}

	@Override
	public void setReloadFragment(EventListener eventListener, Object object) {
		// TODO Auto-generated method stub

	}

	public abstract void setOnFragmentParamChange();
}
