package base;

import java.util.ArrayList;



@SuppressWarnings("serial")
public class DataCollectionList<T> extends ArrayList<T>{
	
	public String tagValue;
	
	public void setTag(String tag){
		this.tagValue=tag;
	}

	public String getTag(){
		return tagValue;
	}
}
