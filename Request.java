package hw4;
public class Request {
	    public double arrival1;
	    public double service;
	    public double finish;
	    public int id;

	    public Request() {
	        this.arrival1 = 0;
	        this.service = 0;
	        this.finish = 0;
	    }
		
		public double getArrival1() {
			return arrival1;
		}
		
		public double getService() {
			return service;
		}
		
		
		public double getFinish() {
			return finish;
		}
		
		public int getID() {
			return id;
		}
		
		public void setArrival1(double time) {
			arrival1 = time;
		}
		
		public void setService(double time) {
			service = time;
		}
		
		public void setFinish(double time) {
			finish = time;
		}
		
		public void setID(int id) {
			this.id = id;
		}

	}

