package base;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import base.base.ObjectBase;
import extec.extec.appbase.R;

public class ListViewController<T extends ObjectBase> extends ArrayAdapter<T> {

	/**
	 * Private members
	 */
	private ListView listView;
	private LinearLayout mainView;
	// private ArrayList<ListViewColumnHeader> columnHeaders;
	// private ViewGroup dataRowTemplate;
	// private int dataRowTemplateLayoutID;
	private Boolean isViewPrepared = false;
	private IListViewDataProvider<T> listViewDataProvider;
	private List<T> items;
	private int itemCount;
	private int previousItemCount;
	//private LinearLayout titleView;
	// private long lastUpdatedTime;
	private LayoutInflater inflater;

	/**
	 * Default constructor
	 * 
	 * @param context
	 *            Current context
	 * @param dataRowTemplateLayoutID
	 *            Layout id for a data row which will be used when creating the
	 *            header
	 * @param listViewDataProvider
	 *            Data provider for the list view
	 * @throws Exception
	 */
	public ListViewController(Context context, int dataRowTemplateLayoutID,IListViewDataProvider<T> listViewDataProvider) throws Exception {

		super(context, -1);

		if (context == null || listViewDataProvider == null)
			throw new Exception("context or listViewDataProvider cannot be null");

		// this.dataRowTemplateLayoutID = dataRowTemplateLayoutID;
		this.listViewDataProvider = listViewDataProvider;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * Gets the main view including list view and header view
	 * 
	 * @return The main view
	 * @throws Exception
	 */
	public View getView() throws Exception {

		if (!isViewPrepared)
			mainView = (LinearLayout) createView();

		return mainView;
	}

	/**
	 * Gets the list view
	 * 
	 * @return
	 */
	public ListView getListView() {

		if (!isViewPrepared)
			mainView = (LinearLayout) createView();

		return listView;
	}
	/**
	 * Set data items which the list view will be working with
	 * 
	 * @param items
	 */
	public void setListItems(List<T> items) {

		this.items = items;

		if (isViewPrepared)
			refreshListView(true);
	}

	/**
	 * Set table header columns
	 * 
	 * @param columnHeaders
	 */
	/*
	 * public void setTableHeaderColumns( ArrayList<ListViewColumnHeader>
	 * columnHeaders ) { this.columnHeaders = columnHeaders;
	 * 
	 * if ( isViewPrepared ) { createHeaderLayout(); } }
	 */

	/**
	 * Main method which will prepare the list view and header view
	 */
	public View createView() {

		if (!isViewPrepared) {

			mainView = (LinearLayout) this.inflater.inflate(R.layout.main_listview, null, false);
			listView = (ListView) mainView.findViewById(R.id.mainListView);
			//titleView = (LinearLayout) mainView.findViewById(R.id.listview_header);
			//
			// TEMP : to check animation
			//
			// listView.setAlpha( 0.0f );
			this.listView.setAdapter(this);
			
//			Resources r = BaseFlyContext.getInstant().getApplicationContext().getResources();
//			int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, r.getDisplayMetrics());
//			mainView.setPadding(0, px, 0, 0);
			
			 createHeaderLayout();
			isViewPrepared = true;
		}

		return mainView;
	}

    public void hideListViewHeader(){

        LinearLayout headerView = (LinearLayout) mainView.findViewById(R.id.listview_header);
        headerView.setVisibility(View.GONE);
    }

	/**
	 * Creates header layout according to header columns given
	 */
	protected void createHeaderLayout() {
		/*
		 * if ( dataRowTemplate == null ) dataRowTemplate = ( ViewGroup )
		 * inflater.inflate( dataRowTemplateLayoutID, null );
		 * 
		 * LinearLayout header1Container = ( LinearLayout )
		 * mainView.findViewById( R.id.listview_header ); LinearLayout
		 * header2Container = ( LinearLayout ) mainView.findViewById(
		 * R.id.listview_header2 );
		 * 
		 * ListViewController.createHeaderLayout( this.context, columnHeaders,
		 * dataRowTemplate, header1Container, header2Container );
		 */
	}

	/*
	 * public static void createHeaderLayout( Context context,
	 * ArrayList<ListViewColumnHeader> columnHeaders, ViewGroup dataRowTemplate,
	 * LinearLayout header1Container, LinearLayout header2Container ) {
	 * 
	 * if ( columnHeaders == null || columnHeaders.size() == 0 ||
	 * dataRowTemplate == null || header1Container == null ) return; // // If
	 * the row template is a table layout get the internal row // instead of the
	 * parent table // if ( dataRowTemplate instanceof TableLayout &&
	 * dataRowTemplate.getChildCount() > 0 ) dataRowTemplate = ( ViewGroup )
	 * dataRowTemplate.getChildAt( 0 );
	 * 
	 * boolean isDataRowTemplateAvailable = ( dataRowTemplate != null );
	 * LinearLayout header = header1Container; int columnCount =
	 * columnHeaders.size(); Boolean hasSecondaryColumns = false; // // At the
	 * moment maximum 2 rows are supported // for ( int rowIndex = 0; rowIndex <
	 * 2; rowIndex++ ) { header.removeAllViews();
	 * 
	 * for ( int colIndex = 0; colIndex < columnCount; colIndex++ ) {
	 * ListViewColumnHeader colHeader = columnHeaders.get( colIndex ); float
	 * weight = -1.0f; int width = -1;
	 * 
	 * if ( isDataRowTemplateAvailable ) { View dataRowView = null; int
	 * colIndexInRowTemplate = colIndex;
	 * 
	 * if ( LinearLayoutRTL.isRtlMode ) colIndexInRowTemplate = columnCount -
	 * colIndex - 1;
	 * 
	 * if ( dataRowTemplate.getChildCount() > colIndexInRowTemplate )
	 * dataRowView = dataRowTemplate .getChildAt( colIndexInRowTemplate );
	 * 
	 * if ( dataRowView != null ) { ViewGroup.LayoutParams params = dataRowView
	 * .getLayoutParams();
	 * 
	 * if ( params instanceof LinearLayout.LayoutParams ) {
	 * LinearLayout.LayoutParams linearLayoutParams = (
	 * LinearLayout.LayoutParams ) params; weight = linearLayoutParams.weight;
	 * width = linearLayoutParams.width; } } }
	 * 
	 * TextView txtColHeader = new TextView( context );
	 * 
	 * if ( rowIndex == 0 ) txtColHeader.setText( colHeader.caption ); else
	 * txtColHeader.setText( colHeader.caption2 );
	 * 
	 * LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
	 * LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, weight );
	 * 
	 * if ( width >= 0 ) params.width = width;
	 * 
	 * if ( LinearLayoutRTL.isRtlMode && colHeader.gravity != Gravity.RIGHT )
	 * colHeader.gravity = Gravity.RIGHT;
	 * 
	 * txtColHeader.setGravity( colHeader.gravity );
	 * txtColHeader.setLayoutParams( params ); txtColHeader.setTextAppearance(
	 * context, R.style.listview_header_cell ); header.addView( txtColHeader );
	 * // // Set flag to add 2nd row if secondary captions are available. // if
	 * ( rowIndex == 0 && !hasSecondaryColumns && colHeader.caption2 != null )
	 * hasSecondaryColumns = true; } // // Stop after adding the first row if
	 * secondary captions are not // provided. // if ( !hasSecondaryColumns ||
	 * header2Container == null ) break; else header = header2Container; } }
	 */

	/* Adapter related methods */

	/**
	 * Set data list item count
	 * 
	 * @param itemCount
	 */
	public final void setCount(int itemCount) {

		this.previousItemCount = this.itemCount;
		this.itemCount = itemCount;
	}

	/**
	 * Returns data list item count.
	 */
	@Override
	public final int getCount() {
		return this.itemCount;
	}

	/**
	 * Returns a row view for the list view
	 */
	@Override
	public final View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;
		// Log.d("getView", ""+position);
		if (view == null)
			view = this.listViewDataProvider.createItemView(position, parent);

		T item = null;

		synchronized (items) {
			item = items.get(position);
		}

		this.listViewDataProvider.updateItemView(position, view, item);
		return view;
	}

	/* View refresh methods */

	public final void refreshListView() {
		refreshListView(false);
	}

	/**
	 * Main refresh handler of the list view
	 * 
	 * @param force
	 *            Set this to true when a forceful refresh is needed
	 */
	public final void refreshListView(boolean force) {
		int currentItemCount = -1;

		// TODO : Stop refreshing list view if scrolling

		if (items == null)
			return;

		synchronized (items) {
			currentItemCount = items.size();
		}

		if (force || this.itemCount == 0|| this.previousItemCount != currentItemCount) {

			setCount(currentItemCount);
			notifyDataSetChanged();
			setNotifyOnChange(false);

		} else {

			int startIndex = listView.getFirstVisiblePosition();
			int count = listView.getChildCount(); // TODO : Use last visible
													// position instead

			synchronized (items) {
				// long currentTimeMillis = System.currentTimeMillis();

				for (int index = 0; index < count; index++) {
					int itemIndex = index + startIndex;

					if (itemIndex >= items.size())
						break;

					// T item = items.get( itemIndex );

					/*
					 * if (items.lastUpdatedTime > this.lastUpdatedTime ) { View
					 * view = listView.getChildAt( index );
					 * this.listViewDataProvider.updateItemView(itemIndex, view,
					 * item ); }
					 */
				}

				// this.lastUpdatedTime = currentTimeMillis;
			}
		}
		//
		// TEMP : to check animation
		//
		// if ( this.itemCount > 0 && listView.getAlpha() == 0 )
		// listView.animate().setDuration( 1000 ).alpha( 1.0f ).start();
	}
	
	public void setTitleVisibleMode(){
		
	//	((LinearLayout)mainView.findViewById(R.id.listview_header)).setVisibility(View.GONE);
	}
}
