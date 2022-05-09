package hw4;
import java.util.*;

public class Simulator {
public static PriorityQueue<Event> timeline = new PriorityQueue<Event>(new EventComparator());
public static LinkedList<Request> primary = new LinkedList<Request>();
public static LinkedList<Request> secondary = new LinkedList<Request>();
public static int id = 0;
public static double pril = 0, curr_time = 0, mon_counter = 0, service1 = 0, service2 = 0,
completed_requests = 0, response_time = 0, secondl = 0, stay = 0, leave = 0, waiting_time = 0;
	//Run simulation
	static void simulate(double time, double rate) {
			int req_num = 0;
			while(curr_time < time) {
				Event event = timeline.remove();
				curr_time = event.getTime();
				if(event.getType() == "ARR") {
					checkArrival(rate);
				}else if(event.getType() == "NEXT") {
					checkNext1(rate);
				}else if(event.getType() == "DONE 0") {
//					checkFinish0();
				}else if(event.getType() == "MON") {
					checkMonitor(rate);				
				}else if(event.getType() == "NEXT 0") {
//					checkNext0(rate);
				}else if(event.getType() == "DONE") {
					checkFinish1();
				}
			}
		
			System.out.println("UTIL 0: " + rate * (1/(1/service1)));
			System.out.println("UTIL 1: " + rate * (1/(1/service2)));
			System.out.println("QLEN 0: " + pril / mon_counter);
			System.out.println("QLEN 1: " + secondl / mon_counter);
			System.out.println("TRESP: " + response_time / completed_requests);
			System.out.println("TWAIT: " + waiting_time / completed_requests);
		}
	
	//Check if Arrived (and if we can start)
	static void checkArrival(double rate) {
		Request req = new Request();
		req.setArrival1(curr_time);
		req.setID(id);
		System.out.println("R" + req.getID() + " ARR: " + req.getArrival1());
		id++;
		primary.add(req);
		if(primary.size() == 1) {
			req.setService(curr_time);
			waiting_time += req.getService() - req.getArrival1();
			System.out.println("R" + req.getID() + " START 0: " + req.getService());
			//if (leave == 0) {
				//timeline.add(new Event("DONE", curr_time + Exp.getExp((1/service))));
			//}else {
			timeline.add(new Event("NEXT", curr_time + Exp.getExp((1/service1))));
			//}
		}
		timeline.add(new Event("ARR", curr_time + Exp.getExp(rate)));
	}
	
	//Check if we have to push back to primary
//	static void checkNext0(double rate) {
//		Request r = secondary.remove();
//		r.setArrival1(curr_time);
//		System.out.println("R" + r.getID() + " NEXT 0: " + r.getArrival1());
//		if(primary.size() == 1) {
//			r.setService(curr_time);
//			
//			System.out.println("R" + r.getID() + " START 0: " + r.getService());
////			if (leave == 0) {
////				timeline.add(new Event("DONE 0", curr_time + Exp.getExp((1/service))));
////			}else {
//			timeline.add(new Event("NEXT 1", curr_time + Exp.getExp((1/service1)), r.getID()));
////			}
//		}
//		
//	}
	
	//Check if we can push to secondary
	static void checkNext1(double rate) {
		Request req = primary.remove();
		req.setArrival1(curr_time);
		secondary.add(req);
		System.out.println("R" + req.getID() + " NEXT 1: " + req.getArrival1());
		if(secondary.size() == 1) {
			req.setService(curr_time);
			waiting_time += req.getService() - req.getArrival1();
			System.out.println("R" + req.getID() + " START 1: " + req.getService());
//			if (stay == 0) {
//				timeline.add(new Event("NEXT 0", curr_time + Exp.getExp((1/service))));
//			}else {
			timeline.add(new Event("DONE", curr_time + Exp.getExp((1/service2))));
//			}
		}
		
		if(primary.size() > 0) {
			Request r = primary.peek();
			r.setService(curr_time);
			waiting_time = waiting_time += req.getService() - req.getArrival1();
			System.out.println("R" + req.getID() + " START 0: " + req.getService());
			timeline.add(new Event("NEXT", curr_time + Exp.getExp((1/service1))));

		}
	}
	
	//Check if we can finish an event
	static void checkFinish1() {
		Request r = secondary.remove();
		r.setFinish(curr_time);
		response_time += r.getFinish() - r.getArrival1();
		completed_requests++;
		System.out.println("R" + r.getID() + " DONE: " + r.getFinish());
		if(secondary.size() >= 1) {
			Request rt = secondary.peek();
			rt.setService(curr_time);
			waiting_time = waiting_time += rt.getService() - rt.getArrival1();
			System.out.println("R" + rt.getID() + " START 1: " + rt.getService());
			timeline.add(new Event("DONE", curr_time + Exp.getExp((1/service2))));
			
		}
	}
	
//	static void checkFinish0() {
//		Request r = primary.remove();
//		r.setFinish(curr_time);
//		response_time += r.getFinish() - r.getArrival1();
//		completed_requests++;
//		System.out.println("R" + r.getID() + " DONE: " + r.getFinish());
//		if(secondary.size() >= 1) {
//			Request rt = secondary.peek();
//			rt.setService(curr_time);
//			System.out.println("R" + rt.getID() + " START 0: " + rt.getService());
//			timeline.add(new Event("DONE", curr_time + Exp.getExp((1/service1)), rt.getID()));
//		}
//	}
	
	//Check if monitor and update statistics
	static void checkMonitor(double rate) {
		mon_counter++;
		pril += primary.size();
		secondl += secondary.size();
		timeline.add(new Event("MON", curr_time + Exp.getExp(rate)));
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Event e = new Event("ARR", Exp.getExp(Double.valueOf(args[1])));
		timeline.add(e);
		leave = Double.valueOf(args[4]);
		stay = Double.valueOf(args[5]);
		service1 = Double.valueOf(args[2]);
		service2 = Double.valueOf(args[3]);
		timeline.add(new Event("MON", Exp.getExp(Double.valueOf(args[1]))));
		simulate(Double.valueOf(args[0]),Double.valueOf(args[1]));
		

		
	}

}
