package shared;

import base.DataCollectionList;
import base.base.FragmentBase;


public class WindowStackManager {
	
	DataCollectionList<FragmentBase> stackList=new DataCollectionList<FragmentBase>();
	public static int backViewId;
	public static WindowStackManager instant = null;
	
	public static WindowStackManager getInstant(){
		
		if(instant==null){
			
			instant=new WindowStackManager();
		}
		return instant;
	}
	
	public boolean putWindowToStack(FragmentBase window){
		
		if(stackList==null){
			
			stackList=new DataCollectionList<FragmentBase>();
		}
		
		backViewId=stackList.size();
		stackList.add(backViewId, window);
		return true;
	}
	
	public FragmentBase getWindowFromStack(){
		
		if(stackList==null){
			
			//return new FragmentBase();
		}
		FragmentBase window=(FragmentBase)stackList.get(stackList.size()-1);
		stackList.remove(stackList.size()-1);
		return window;
	}
	
	public void removeWindowFromStack(){
		
		if(stackList==null){
			
			return ;
		}
		stackList.remove(stackList.size()-1);
	}
	public void cleanWindowStackManager(){
		
		stackList.clear();
	}

}
