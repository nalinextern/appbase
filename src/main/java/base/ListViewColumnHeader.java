package base;

import android.view.Gravity;

public class ListViewColumnHeader {
	public String id;
	public String caption;
	public String caption2;
	public String caption3;
	public String caption4;
	public String caption5;
	public int width = -1;
	public int gravity = Gravity.RIGHT;

	public ListViewColumnHeader(String caption) {
		this.caption = caption;
	}

	public ListViewColumnHeader(String caption, int gravity) {
		this.caption = caption;
		this.gravity = gravity;
	}

	public ListViewColumnHeader(String caption, String caption2) {
		this.caption = caption;
		this.caption2 = caption2;
	}
	
	

	public ListViewColumnHeader(String caption, String caption2, int gravity) {
		this.caption = caption;
		this.caption2 = caption2;
		this.gravity = gravity;
	}

}
