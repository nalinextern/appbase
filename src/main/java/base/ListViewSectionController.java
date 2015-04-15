package base;

import java.util.ArrayList;
import java.util.List;

import base.base.ObjectBase;
import extec.extec.appbase.R;
import shared.BaseFlyContext;
import android.content.Context;
import android.content.res.Resources;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * 
 * TODO : 1. use proper synchronization
 */
public class ListViewSectionController<T extends ObjectBase> extends
ArrayAdapter<T> {

	/**
	 * Private members
	 */
	private Context context;
	private ListView listView;
	private LinearLayout mainView;
	private ArrayList<ListViewColumnHeader> columnHeaders;
	private ViewGroup dataRowTemplate;
	private int dataRowTemplateLayoutID;
	private Boolean isViewPrepared = false;
	private IListViewSectionDataProvider<T> listViewDataProvider;
	private List<DataCollectionList<T>> sectionListItems; // parent items
	private ArrayList<SectionListViewItem> underlineItems; // Child items
	private int itemCount;
	private int previousItemCount=-1;
	//private long lastUpdatedTime;
	private LayoutInflater inflater;
	public int sectionIndex;
	LinearLayout header1 = null;
	//LinearLayout header2 = null;
	//	public LinkedHashMap<Integer, Boolean> collapseStatusMap;


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
	public ListViewSectionController( Context context,
			int dataRowTemplateLayoutID,
			IListViewSectionDataProvider<T> listViewDataProvider )
					throws Exception {

		super( context, -1 );

		if ( context == null || listViewDataProvider == null )
			throw new Exception(
					"context or listViewDataProvider cannot be null" );

		this.context = context;
		this.dataRowTemplateLayoutID = dataRowTemplateLayoutID;
		this.listViewDataProvider = listViewDataProvider;
		this.inflater = ( LayoutInflater ) context
				.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		this.underlineItems = new ArrayList<SectionListViewItem>();
		new SparseIntArray();
	}

	/**
	 * Gets the main view including list view and header view
	 * 
	 * @return The main view
	 * @throws Exception
	 */
	public View getView() throws Exception {
		if ( !isViewPrepared )
			mainView = ( LinearLayout ) createView();

		return mainView;
	}

	/**
	 * Gets the list view
	 * 
	 * @return
	 */
	public ListView getListView() {
		if ( !isViewPrepared )
			mainView = ( LinearLayout ) createView();

		return listView;
	}

	/**
	 * Set data items which the list view will be working with
	 * 
	 * @param items
	 */
	public void setListItems( List<DataCollectionList<T>> sectionListItems ) {
		this.sectionListItems = sectionListItems;
		
		//setCount( sectionListItems.size() );
		//notifyDataSetChanged();
		//setNotifyOnChange( false );

		//if ( isViewPrepared )
		//	refreshListView( true );
	}

	/**
	 * Set table header columns
	 * 
	 * @param columnHeaders
	 */
	public void setTableHeaderColumns( ArrayList<ListViewColumnHeader> columnHeaders ) {
		this.columnHeaders = columnHeaders;

		if ( isViewPrepared ) {
			// createHeaderLayout();
		}
	}

	/**
	 * Main method which will prepare the list view and header view
	 */
	public View createView() {
		if ( !isViewPrepared ) {
			mainView = ( LinearLayout ) this.inflater.inflate(
					R.layout.main_listview, null, false );
			listView = ( ListView ) mainView.findViewById( R.id.mainListView );
			header1 = ( LinearLayout) mainView.findViewById( R.id.listview_header);
			//header2 = ( LinearLayout) mainView.findViewById( R.id.listview_header2);
			
			header1.setVisibility(View.GONE);
			//header2.setVisibility(View.GONE);
			Resources r = BaseFlyContext.getInstant().getApplicationContext().getResources();
			int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, r.getDisplayMetrics());
			mainView.setPadding(0, px, 0, 0);
			listView.setAdapter( this );
			isViewPrepared = true;
		}

		return mainView;
	}

	/**
	 * Creates header layout according to header columns given
	 */	
	public View createSectionHeaderLayout() {		
		LinearLayout view = ( LinearLayout ) inflater.inflate( R.layout.listview_section_layout, null );
		LinearLayout header1Container = null;

		if ( columnHeaders != null && columnHeaders.size() > 0 ) {		
			if ( dataRowTemplate == null )
				dataRowTemplate = ( ViewGroup ) inflater.inflate( dataRowTemplateLayoutID, null );		

			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( 
					LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT  );		
			header1Container = new LinearLayout( this.context );
			header1Container.setLayoutParams( layoutParams );
			view.addView( header1Container );
		}


		/*ListViewController.createHeaderLayout( this.context, 
				columnHeaders, 
				dataRowTemplate, 
				header1Container, 
				null );*/


		return view;
	}

	/* Adapter related methods */

	/**
	 * Set data list item count
	 * 
	 * @param itemCount
	 */
	public final void setCount( int itemCountq ) {
		this.previousItemCount = this.itemCount;
		this.itemCount = itemCountq;
	}

	/**
	 * Returns data list item count.
	 */
	@Override
	public final int getCount() { // parent header count	

		return this.itemCount;
	}

	public void collpseList(int index) {

		sectionListItems.get(index).clear();

	}

	public void setCollpseStatus(int index, boolean isCollepsed) {

		//AppState.collapseStatusMap.put(index, isCollepsed);


	}

	/*
	 * private void updateUnderlineItems() {
		
		if(true)
			return;

		if ( this.sectionListItems == null || this.sectionListItems.size() == 0 ) {
			underlineItems.clear();		
			hasListSizeChangedInAnySection = true;
			return;			
		}

		int sectionSize = 0;
		int underlineItemIndex = -1;
		sectionSize = sectionListItems.size();
		hasListSizeChangedInAnySection = false;

		synchronized ( this.sectionListItems ) {

			for (  sectionIndex = 0; sectionIndex < sectionSize; sectionIndex++ ) {
				CollectionBase<T> list = sectionListItems.get( sectionIndex );

				if ( list == null )
					continue;

				underlineItemIndex++;

				int listSize = list.size();
			//	boolean isCollapseHeader =false;

				//if (isCollapseUpdated) { //Chiran

					if (AppState.collapseStatusMap.containsKey(sectionIndex) && AppState.collapseStatusMap.get(sectionIndex))
					{
					//	isCollapseHeader =true;						
						listSize=0;
						//sectionListItems.get(sectionIndex).clear();
						for(int i =underlineItems.size()-1;i>underlineItemIndex;i--){
							underlineItems.remove(i);

						}
						hasListSizeChangedInAnySection = true;
					}

		//		}

				int previousListSize = previousListSizeMap.get( sectionIndex, -1 );

				// if ( previousListSize < 0 )
				previousListSizeMap.put( sectionIndex, listSize );

				if ( !hasListSizeChangedInAnySection ) {
					if ( previousListSize == listSize )
						continue;
					else
						hasListSizeChangedInAnySection = true;
				}

				if ( this.underlineItems.size() < underlineItemIndex + 1 ) {
					SectionListViewItem underlineItem = new SectionListViewItem( true, sectionIndex, -1 );
					this.underlineItems.add( underlineItem );

					if(underlineItem.sectionIndex == 1)						
						continue;


				}
				else {

					SectionListViewItem underlineItem = this.underlineItems.get( underlineItemIndex );
					underlineItem.isSection = false;
					underlineItem.sectionIndex = sectionIndex;
					underlineItem.itemIndex = -1;	
					if(underlineItem.sectionIndex == 1)						
						continue;
				}

				//Add item to parent panel :Sub items



				for ( int itemIndex = 0; itemIndex < listSize; itemIndex++ ) {
					underlineItemIndex++;

					if ( this.underlineItems.size() < underlineItemIndex + 1 ) {
						SectionListViewItem underlineItem = new SectionListViewItem( false, sectionIndex, itemIndex );
						this.underlineItems.add( underlineItem );
					} else {
						SectionListViewItem underlineItem = this.underlineItems.get( underlineItemIndex );
						underlineItem.isSection = false;
						underlineItem.sectionIndex = sectionIndex;
						underlineItem.itemIndex = itemIndex;

					}
				}
			}
		}
	}
		 
	}
*/
	private void updateUnderlineItems(boolean bool) {

		if ( this.sectionListItems == null || this.sectionListItems.size() == 0 ) {
			underlineItems.clear();		
			return;			
		}

		int sectionSize = 0;
		int underlineItemIndex = -1;
		sectionSize = sectionListItems.size();
		underlineItems.clear();

		synchronized ( this.sectionListItems ) {

			for (  sectionIndex = 0; sectionIndex < sectionSize; sectionIndex++ ) {
				DataCollectionList<T> list = sectionListItems.get( sectionIndex );

				if ( list == null )
					continue;

				underlineItemIndex++;

				int listSize = list.size();
				boolean isCollapseHeader =false;


//				if (AppState.collapseStatusMap.containsKey(sectionIndex) && AppState.collapseStatusMap.get(sectionIndex))
//				{
//
//					listSize=0;
//					isCollapseHeader =true;
//
//				}
				if ( this.underlineItems.size() < underlineItemIndex + 1 ) {

					SectionListViewItem underlineItem = new SectionListViewItem( true, sectionIndex, -1 );
					this.underlineItems.add( underlineItem );

					if(isCollapseHeader)						
						continue;


				}
				else {

					SectionListViewItem underlineItem = this.underlineItems.get( underlineItemIndex );
					underlineItem.isSection = false;
					underlineItem.sectionIndex = sectionIndex;
					underlineItem.itemIndex = -1;

					if(isCollapseHeader)						
						continue;
				}

				//Add item to parent panel :Sub items

				if(isCollapseHeader)						
					continue;

				for ( int itemIndex = 0; itemIndex < listSize; itemIndex++ ) {
					underlineItemIndex++;

					if ( this.underlineItems.size() < underlineItemIndex + 1 ) {
						SectionListViewItem underlineItem = new SectionListViewItem( false, sectionIndex, itemIndex );
						this.underlineItems.add( underlineItem );
					} else {
						SectionListViewItem underlineItem = this.underlineItems.get( underlineItemIndex );
						underlineItem.isSection = false;
						underlineItem.sectionIndex = sectionIndex;
						underlineItem.itemIndex = itemIndex;

					}
				}
			}
		}
	}
	/**
	 * Returns a row view for the list view
	 */
	@Override
	public final View getView( int position, View convertView, ViewGroup parent ) {
		
		View view = convertView;		
		SectionListViewItem underlineItem = null;

		underlineItem = underlineItems.get( position );

		if ( underlineItem.isSection ) {
			if ( view != null ) {
				SectionListViewItem tagItem = ( SectionListViewItem ) view.getTag( R.layout.main_listview );

				if ( tagItem != null && !tagItem.isSection ) {

					view = this.listViewDataProvider.createSectionView( underlineItem.sectionIndex, parent );
					view.setTag( R.layout.main_listview, underlineItem );
					this.listViewDataProvider.updateSectionView( view, underlineItem.sectionIndex );
				}else{
					view.setTag( R.layout.main_listview, underlineItem );
					this.listViewDataProvider.updateSectionView( view, underlineItem.sectionIndex );
				}
			}
			else {

				view = this.listViewDataProvider.createSectionView( underlineItem.sectionIndex, parent ); 
				view.setTag( R.layout.main_listview, underlineItem );
				this.listViewDataProvider.updateSectionView( view, underlineItem.sectionIndex );
			}

		} else { 
		
			if ( view != null ) {
				SectionListViewItem tagItem = ( SectionListViewItem ) view.getTag( R.layout.main_listview );
				
				if ( tagItem != null && tagItem.isSection ) {

					view = this.listViewDataProvider.createItemView( underlineItem.itemIndex, parent ,tagItem.sectionIndex); 
				}
			} 
			else {
				view = this.listViewDataProvider.createItemView( underlineItem.itemIndex, parent,underlineItem.sectionIndex ); //

			}

			view.setTag( R.layout.main_listview, underlineItem );			
			T item = null;

			synchronized ( this.sectionListItems ) {
				if ( underlineItem.sectionIndex >= 0 && this.sectionListItems.size() > underlineItem.sectionIndex ) {
					DataCollectionList<T> list = this.sectionListItems.get( underlineItem.sectionIndex );

					if ( underlineItem.itemIndex >= 0 && list.size() > underlineItem.itemIndex )
						item = list.get( underlineItem.itemIndex );
				}
			}

			if ( item != null ) {
				this.listViewDataProvider.updateItemView(underlineItem.sectionIndex, view, item,underlineItem.sectionIndex );
			}


		}
		return view;
	}

	/* View refresh methods */

	/**
	 * Main refresh handler of the list view
	 * 
	 * @param force
	 *            Set this to true when a forceful refresh is needed
	 */
	public final void refreshListView( boolean force ) {
		int currentItemCount = -1;

		updateUnderlineItems(false);

		currentItemCount = underlineItems.size();
		// }
		setCount( currentItemCount );

		if (force || (this.itemCount == 0 && this.previousItemCount!=0)|| this.previousItemCount !=currentItemCount) {
			setCount( currentItemCount );
			notifyDataSetChanged();
			setNotifyOnChange( false );

		}else{

//			int startIndex = listView.getFirstVisiblePosition();
//			//int lastIndex = listView.getLastVisiblePosition();
//			int lastIndex = listView.getChildCount(); // Quick fix for the duplication Issue - Nalin
//
//			synchronized ( underlineItems ) {
//				long currentTimeMillis = System.currentTimeMillis();
//
//				for ( int index = startIndex; index < lastIndex+1; index++ ) {
//					
//					if ( index >= underlineItems.size() ){
//						break;
//					}
//					if(underlineItems.get(index).isSection){
//						continue;
//						
//					}
//					
//					T item = this.sectionListItems.get( underlineItems.get(index).sectionIndex ).get(underlineItems.get(index).itemIndex);
//
//					if ( item.lastUpdatedTime > this.lastUpdatedTime ) {
//						View view = listView.getChildAt( index );
//						this.listViewDataProvider.updateItemView(index, view, item,underlineItems.get(index).sectionIndex );
//					}
//				}
//
//				this.lastUpdatedTime = currentTimeMillis;
//			}


		}


	}

	
	public View getSingleView(int index){
		return listView.getChildAt( index );
	}
}
