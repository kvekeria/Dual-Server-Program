package hw4;
import java.util.*;
public class EventComparator implements Comparator<Event>{
	public int compare(Event ev1, Event ev2) {
		if (ev1.getTime() < ev2.getTime()) {
			return -1;
		}else if (ev1.getTime() > ev2.getTime()) {
			return 1;
		}else {
			return 0;
		}
	}

}
