package comp3111.coursescraper;

public class Section {
	private static final int DEFAULT_MAX_SLOT = 3;
	
	private String title;
	private Slot [] slots;
	private int numSlots;

	@Override
	public Section clone() {
		Section s = new Section();
		s.title = this.title;
		s.slots = this.slots;
		s.numSlots = this.numSlots;
		return s;
	}
	
	public Section() {
		slots = new Slot[DEFAULT_MAX_SLOT];
		for (int i = 0; i < DEFAULT_MAX_SLOT; i++) 
			slots[i] = null;
		numSlots = 0;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void addSlot(Slot s) {
		if (numSlots >= DEFAULT_MAX_SLOT)
			return;
		slots[numSlots++] = s.clone();
	}
	
	public Slot getSlot(int i) {
		if (i >= 0 && i < numSlots)
			return slots[i];
		return null;
	}
	
	public int getNumSlots() {
		return numSlots;
	}
	
	public void setNumSlots(int numSlots) {
		this.numSlots = numSlots;
	}
	
	public String getSlotToString(int i) {
		if (i >= 0 && i < numSlots)
			return slots[i].toString();
		return null;
	}
}


