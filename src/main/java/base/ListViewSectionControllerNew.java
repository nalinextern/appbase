package base;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import base.base.ObjectBase;
import extec.extec.appbase.R;


public class ListViewSectionControllerNew <T extends ObjectBase> extends BaseExpandableListAdapter  {

	public ExpandableListView expandableListView;
	private RelativeLayout mainView;
	private Boolean isViewPrepared = false;
	public IlistSectionDataProvider<T> listViewSectionDataProvider;
	private ArrayList<DataCollectionList<T>> items;
	private int sectionIemCount;
	private int previousItemCount;
	private LayoutInflater inflater;
	public ArrayList<ArrayList<View>> childViewCollection = null;
	public ArrayList<View> parentView = null;

	public ListViewSectionControllerNew(Context context,int dataRowTemplateLayoutID,IlistSectionDataProvider<T> listViewDataProvider) throws Exception {
		
		super();

			if ( context == null || listViewDataProvider == null )
				throw new Exception("context or listViewDataProvider cannot be null" );
//			
		childViewCollection = new ArrayList<ArrayList<View>>();
		parentView = new ArrayList<View>();
		this.listViewSectionDataProvider = listViewDataProvider;
		this.inflater = ( LayoutInflater ) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
	}

	
	/**
	 * Gets the main view including list view and header view
	 * 
	 * @return The main view
	 * @throws Exception
	 */
	public View getView() throws Exception {
		
			if ( !isViewPrepared )
				mainView = ( RelativeLayout ) createView();

		return mainView;
	}

	/**
	 * Gets the list view
	 * 
	 * @return
	 */
	public ExpandableListView getListView() {
		
			if ( !isViewPrepared )
				mainView = ( RelativeLayout ) createView();

			return expandableListView;
	}

	/**
	 * Set data items which the list view will be working with
	 * 
	 * @param items
	 */
	public void setListItems( ArrayList<DataCollectionList<T>> items ) {
		this.items = items;

			if ( isViewPrepared )
				refreshListView( true );
	}
	
	/**
	 * Set table header columns
	 * 
	 * @param columnHeaders
	 */
	/*public void setTableHeaderColumns( ArrayList<ListViewColumnHeader> columnHeaders ) {
		this.columnHeaders = columnHeaders;

		if ( isViewPrepared ) {
			createHeaderLayout();
		}
	}*/

	/**
	 * Main method which will prepare the list view and header view
	 */
	public View createView() {
		
		if ( !isViewPrepared ) {
			
			mainView = ( RelativeLayout ) this.inflater.inflate(R.layout.listview_section, null, false );
			expandableListView = ( ExpandableListView ) mainView.findViewById( R.id.expandableListView );
			//
			// TEMP : to check animation
			//
			// listView.setAlpha( 0.0f );
			this.expandableListView.setAdapter( this );
			//createHeaderLayout();
			isViewPrepared = true;
		}

		return mainView;
	}
	
	@Override
	public View getChildView(int groupPosition,  int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		
		if(childViewCollection.size() <= groupPosition){
			
			ArrayList<View> viewCollection = new ArrayList<View>();
			childViewCollection.add(groupPosition, viewCollection);
		}
			if (childViewCollection.get(groupPosition).size() <= childPosition) {
				
				convertView =  this.listViewSectionDataProvider.createItemView(groupPosition, parent,childPosition,convertView);
				childViewCollection.get(groupPosition).add(convertView);
			}
			else{
				
				//return childViewCollection.get(groupPosition).get(childPosition);
				T item = null;
		
				synchronized ( items ) {
					item = (items.get( groupPosition )).get(childPosition);
				}
				this.listViewSectionDataProvider.updateItemView(groupPosition,childPosition, convertView, item);
			}
		return convertView;
	}
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		
		if (parentView.size() <= groupPosition) {
			convertView =  this.listViewSectionDataProvider.createSectionView(groupPosition, parent);
			
				parentView.add(groupPosition,convertView);
			
			if(!expandableListView.isGroupExpanded(groupPosition))
			{
				expandableListView.expandGroup(groupPosition);
			}
			
		}else {
	
			return parentView.get(groupPosition);
		}
			
		//this.listViewSectionDataProvider.updateSectionView(groupPosition, convertView, (items.get( groupPosition )).getTag());
		
		return convertView;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		
		return  items.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	
	public final void setCount( int itemCount ) {
		
		this.previousItemCount = this.sectionIemCount;
		this.sectionIemCount = itemCount;
	}
	
	@Override
	public int getGroupCount() {
		return sectionIemCount;
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	public void expandAll(){
		
		for(int i=0;i<sectionIemCount;i++){
			
			expandableListView.expandGroup(i);
		}
	}

	public void collapesAll() {

		for (int i = 0; i < sectionIemCount; i++) {

			expandableListView.collapseGroup(i);
		}
	}


	
	public final void refreshListView( boolean force ) {
		
		int currentItemCount = -1;
		// TODO : Stop refreshing list view if scrolling		
		if ( items == null )
			return;

		synchronized ( items ) {
			currentItemCount = items.size();
		}

		if ( force || this.sectionIemCount == 0|| this.previousItemCount != currentItemCount ) {
			
			setCount( currentItemCount );
			notifyDataSetChanged();
			
			//setNotifyOnChange( false );
			//expandAll();
		} else {
			
			//notifyDataSetChanged();

//			int startIndex = expandableListView.getFirstVisiblePosition();
//			int count = expandableListView.getChildCount(); // TODO : Use last visible
//													// position instead
//
//			synchronized ( items ) {
//				//long currentTimeMillis = System.currentTimeMillis();
//
//				for ( int index = 0; index < count; index++ ) {
//					int itemIndex = index + startIndex;
//
//					if ( itemIndex >= items.size() )
//						break;
//
//					//T item = items.get( itemIndex );
//
//					/*if (items.lastUpdatedTime > this.lastUpdatedTime ) {
//						View view = listView.getChildAt( index );
//						this.listViewDataProvider.updateItemView(itemIndex, view, item );
//					}*/
//				}
//
//				//this.lastUpdatedTime = currentTimeMillis;
//			}
		}
		//
		// TEMP : to check animation
		//
		//if ( this.itemCount > 0 && listView.getAlpha() == 0 )
		//	listView.animate().setDuration( 1000 ).alpha( 1.0f ).start();
	}
	
	public void setTitleVisibleMode(){
		
		((LinearLayout)mainView.findViewById(R.id.listview_header)).setVisibility(View.GONE);
	}
	
}










