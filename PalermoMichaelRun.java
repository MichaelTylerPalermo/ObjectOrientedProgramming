import java.lang.Object;
import java.util.Scanner;

class Time {
	private int hours;
	private int minutes;
	private int seconds;
	
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		if (hours < 0 || hours >= 60) {
			hours = 0;
		}else {
			this.hours = hours;
		}
	}
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		if (minutes < 0 || minutes >= 60) {
			minutes = 0;
		} else {
			this.minutes = minutes;
		}
	}
	public int getSeconds() {
		return seconds;
	}
	public void setSeconds(int seconds) {
		if (seconds < 0 || seconds >= 60) {
			seconds = 0;
		} else {
			this.seconds = seconds;
		}
	}
	public String toString() {
			return String.format("%02d:%02d:%02d",hours, minutes, seconds);		
	}
	public Time(int hours, int minutes, int seconds) {
		setHours(hours);
		setMinutes(minutes);
		setSeconds(seconds);
	}
	//Get Time in seconds to easily calculate elapsed time
	public int timeInSecondsSinceMidnight() {
		return (((getHours()*60)+getMinutes())*60)+getSeconds();
	}
}

class Location {
	private double latitude;
	private double longitude;
	
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLatitude(double latitude) {
		if (latitude > -90 && latitude < 90) {
			this.latitude = latitude;
		}else {
			latitude = 0;
		}
	}
	public void setLongitude(double longitude) {
		if (longitude > -180  && longitude < 180) {
			this.longitude = longitude;
		} else {
			longitude = 0;
		}
	}
	//This function will determine if the latitude you enter is north (N) or south (S) and output that string
	public String determineLatitudeDirection() { 
		if (latitude < 0 && latitude >= -90) {
			return "S";
		} else if (latitude >=0 && latitude <= 90) {
			return "N";
		} else {
			return null;
		}
	}
	//This function will determine if the longitude you enter is west (W) or east (E) and output that string
	public String determineLongitudeDirection() {
		if (longitude < 0 && longitude >= -180) {
			return "W";
		} else if (longitude >=0 && longitude <= 180) {
			return "E";
		} else {
			return null;
			}
	}
	//This 
	public double makeLatitudePositive(double latitude) {
		if(latitude <0 ) {
			return -1*latitude;
		}else 
			return latitude;
	}
	public double makeLongitudePositive(double longitude) {
		if(longitude < 0) {
			return -1*longitude;
		}else
			return longitude;
	}
	public String toString() {
		return String.format("Latitude: %.4f deg %s Longitude: %.4f deg %s",
				makeLatitudePositive(latitude),determineLatitudeDirection(),makeLongitudePositive(longitude),determineLongitudeDirection());
	}
	public Location(double latitude, double longitude) {
		setLatitude(latitude);
		setLongitude(longitude);
	}
}

class Run {
	private Time startTime;
	private Time endTime;
	private Location startLoc;
	private Location endLoc;
	
	public Time getStartTime() {
		return startTime;
	}
	public Time getEndTime() {
		return endTime;
	}
	public void setStartTime(int hours, int minutes, int seconds) {
		startTime = new Time(hours,minutes,seconds);
	}
	public void setEndTime(int hours, int minutes, int seconds) {
		endTime = new Time(hours, minutes, seconds);
	}
	public Location getStartLoc() {
		return startLoc;
	}
	public Location getEndLoc() {
		return endLoc;
	}
	public void setStartLoc(double lat, double lon) {
		startLoc = new Location(lat,lon);
	}
	public void setEndLoc(double lat, double lon) {
		endLoc = new Location(lat,lon);
	}
	public Run(int sH, int sM, int sS, int eH, int eM, int eS,
			double sLa, double sLo, double eLa, double eLo) {
		setStartTime(sH,sM,sS);
		setEndTime(eH,eM,eS);
		setStartLoc(sLa,sLo);
		setEndLoc(eLa,eLo);
	}
	public double calculateDistance(double sLa,double sLo,double eLa,double eLo) {
		  int R = 6371; // Radius of the earth in km
		  double dLat = deg2rad(eLa-sLa);  // deg2rad below
		  double dLon = deg2rad(eLo-sLo); 
		  double a = 
		    Math.sin(dLat/2) * Math.sin(dLat/2) +
		    Math.cos(deg2rad(sLa)) * Math.cos(deg2rad(eLa)) * 
		    Math.sin(dLon/2) * Math.sin(dLon/2); 
		  double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		  double d = R * c; // Distance in km
		  return d;
		}

		public double deg2rad(double deg) {
		  return deg * (Math.PI/180);
		}
	public double calculatePace() {
		return (endTime.timeInSecondsSinceMidnight() - startTime.timeInSecondsSinceMidnight())
				/ calculateDistance(startLoc.getLatitude(),startLoc.getLongitude(),endLoc.getLatitude(), endLoc.getLongitude())/60;
	}
	public String toString() {
		return String.format("Start: %s at %s"+"\nEnd: %s at %s", 
					startLoc.toString(),startTime.toString(),endLoc.toString(),endTime.toString())+
				String.format("\nDistance = %.2f km", calculateDistance(startLoc.getLatitude(),
						startLoc.getLongitude(),endLoc.getLatitude(), endLoc.getLongitude()))+
				String.format("\nPace = %.2f min/km", calculatePace());
				
	}
}
public class PalermoMichaelRun {
	public static void main(String[] args){
		int sHours,sMinutes,sSeconds;
		int eHours,eMinutes,eSeconds;
		double sLat,sLon;
		double eLat,eLon;
		Run run;
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter start time as hour, minutes, seconds: ");
		sHours = sc.nextInt();
		sMinutes = sc.nextInt();
		sSeconds = sc.nextInt();
		System.out.print("Enter end time as hour, minutes, and seconds: ");
		eHours = sc.nextInt();
		eMinutes = sc.nextInt();
		eSeconds = sc.nextInt();
		System.out.print("Enter start location as latitude and longitude: ");
		sLat = sc.nextDouble();
		sLon = sc.nextDouble();
		System.out.print("Enter end location as latitude and longitude: ");
		eLat = sc.nextDouble();
		eLon = sc.nextDouble();
		run = new Run(sHours, sMinutes, sSeconds, eHours, eMinutes, eSeconds, sLat, sLon, eLat, eLon);
		System.out.println("Here is your run:");
		System.out.println(run);
	}
}
