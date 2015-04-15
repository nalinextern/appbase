package base.widgetHandlers;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import base.base.ObjectBase;

public abstract class AdapterControllerBase <T extends ObjectBase> extends ArrayAdapter<T>{
    
   private T[] data = null;
   public Resources res;
   LayoutInflater inflater;
   public View mainView = null;
   
//   private Boolean isViewPrepared = false;
//   private Spinner mainSpinner = null;
    
   /*************  CustomAdapter Constructor *****************/
   public AdapterControllerBase( Activity activity, int textViewResourceId,T[] objects,Resources resLocal) 
   {
       super(activity, textViewResourceId,objects);
       data = objects;
       inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
   }

   protected abstract View createItemView(LayoutInflater inflater,int position, View convertView,ViewGroup parent);
   protected abstract void updateItemView(int position, View convertView,T item);
   
//   public View getView() {
//	   
//	   if(mainView == null)
//		   createView();
//	   
//	   return mainView;
//   }
//   
//   public View createView(){
//	   
//	   if (!isViewPrepared) {
//
//			mainView = (RelativeLayout) this.inflater.inflate(
//					R.layout.main_spinner, null, false);
//			mainSpinner = (Spinner) mainView.findViewById(R.id.mainSpiner);
//			//
//			// TEMP : to check animation
//			//
//			// listView.setAlpha( 0.0f );
//			this.mainSpinner.setAdapter(this);
//			// createHeaderLayout();
//			isViewPrepared = true;
//		}
//
//		return mainView;
//   }
   
   public void setDataCollection(T[] d){
	   
	   data = d;
   }
   @Override
	public int getCount() {
	
	   return data.length;
	}
   
   @Override
   public View getDropDownView(int position, View convertView,ViewGroup parent) {
     
	   return getCustomView(position, convertView, parent);
   }

   @Override
   public View getView(int position, View convertView, ViewGroup parent) {
      
	   return getCustomView(position, convertView, parent);
   }

   public View getCustomView(int position, View convertView, ViewGroup parent) {

       //View row = inflater.inflate(R.layout.single_row, parent, false);
	  if(convertView == null){
		  
		  convertView = createItemView(inflater, position, convertView,parent);
	  }
	  T item = data[position];
      updateItemView(position, convertView,item);
      return convertView;
     }
}