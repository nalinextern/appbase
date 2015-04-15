package base;


import base.base.ObjectBase;

public class SectionListViewItem extends ObjectBase {

	public Boolean isSection;
	public ObjectBase item;
	public int sectionIndex;
	public int itemIndex;
	
	public SectionListViewItem( Boolean isSection, int sectionIndex, int itemIndex ) {
		this.isSection = isSection;
		this.sectionIndex = sectionIndex;
		this.itemIndex = itemIndex;
	}
}
