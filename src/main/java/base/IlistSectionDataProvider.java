package base;




import android.view.View;
import android.view.ViewGroup;

import base.base.ObjectBase;


public interface IlistSectionDataProvider<T extends ObjectBase> {

	/**
	 * Creates a new row view for the list view
	 * 
	 * @param position
	 *            Row position
	 * @param parent
	 *            Parent control of the list view
	 * @return Created view
	 */
	public View createItemView( int position, ViewGroup parent ,int childPosition, View convertView);

	/**
	 * Updates the given row view according to the binded list item
	 * 
	 * @param view
	 *            Row view to be updated
	 * @param item
	 *            Binded data item
	 */
	public void updateItemView(  int position, int childPosition,View view, T item );
	
	/**
	 * Creates a new row view for the list view
	 * 
	 * @param position
	 *            Row position
	 * @param parent
	 *            Parent control of the list view
	 * @return Created view
	 */
	public View createSectionView( int position, ViewGroup parent );

	/**
	 * Updates the given row view according to the binded list item
	 * 
	 * @param view
	 *            Row view to be updated
	 * @param item
	 *            Binded data item
	 */
	public void updateSectionView(  int position,View view,String text ); 

}

