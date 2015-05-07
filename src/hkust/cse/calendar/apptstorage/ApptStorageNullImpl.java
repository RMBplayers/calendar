package hkust.cse.calendar.apptstorage;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.Timer;
import java.util.Vector;
import java.util.TimerTask;
import java.util.Calendar;
import java.io.*;

import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.Location;
import hkust.cse.calendar.unit.TimeSpan;
import hkust.cse.calendar.unit.User;
import hkust.cse.calendar.unit.UserTimer;

public class ApptStorageNullImpl extends ApptStorage implements Serializable{

	private static final long serialVersionUID = 5519103861072189849L; 
	
	private User defaultUser = null;
	final static private long [][] DAY_OF_MONTH = {{31,28,31,30,31,30,31,31,30,31,30,31},
		{31,29,31,30,31,30,31,31,30,31,30,31}};
// [0][] for non-leap year, [1][] for leap year
	
	public void setDefaultUser(User user) { 
		defaultUser = user;
	}
	
	@Override
	public void setTime(Timestamp t) {
		newTime.setTime(t);
	}
	
	@Override 
	public final Date getTime() {
		return newTime.getTime();
	}
	
	@Override
	public Date getSystemTime(Date d) {
		return newTime.getSystemTime(d);
	}

	@Override
	public Vector<Location> getLocationVector() {
		return apptLocation;
	}

    @Override
	public Vector<Location> getLocationList() {
		return locationList;
	}
	
	@Override
	public void setLocationVector(Vector<Location> locationVector) {
		apptLocation = locationVector;
	}


	@Override
	public void addLocationToVector(Location location) {
		apptLocation.add(location);
	}

	@Override
	public void removeLocationFromVector(Location location) {
		apptLocation.remove(location);
	}
	
	@Override
	//remebmer to set id
	public void SaveAppt(Appt appt) {
		String userID = defaultUser.ID();
		appt.setID(++mAssignedApptID);
		if (mAppts.containsKey(userID)) {
			Vector<Appt> Apptlist = mAppts.get(userID);
			Apptlist.add(appt);
			// warning, replace it with the following
			//mAppts.replace(userID, Apptlist);
			mAppts.put(userID, Apptlist);
		}
		else {
			Vector<Appt> Apptlist = new Vector<Appt>();
			Apptlist.add(appt);
			mAppts.put(userID, Apptlist);
		}
		// TODO Auto-generated method stub

	}

	@Override
	public Appt[] RetrieveAppts(TimeSpan d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override 
	public Appt[] RetrieveAppts(User entity, TimeSpan time) {
    
    //public Appt[] RetrieveAppts(User entity, TimeSpan time) {
        String userID = entity.ID();
        
        if (mAppts.containsKey(userID)) {
            Vector<Appt> Apptlist = mAppts.get(userID);
            Appt[] selectedAppts = new Appt[Apptlist.size()];
            int j = 0;
            for (int i = 0;i < Apptlist.size();i++) {            	
                // and < time + the length of a day in milliseconds
                long starttime = Apptlist.get(i).TimeSpan().StartTime().getTime();
                long endtime = Apptlist.get(i).TimeSpan().EndTime().getTime();
                if(Apptlist.get(i).getFrequency() == 0){
                    if ((starttime >= time.StartTime().getTime()
                         && starttime < time.EndTime().getTime())
                        ||(endtime > time.StartTime().getTime()
                           && endtime <= time.EndTime().getTime())
                        ||(starttime <= time.StartTime().getTime()
                           && endtime >= time.EndTime().getTime())) {
                            selectedAppts[j] = Apptlist.get(i);
                            j++;
                        }
                }
                
                else if(Apptlist.get(i).getFrequency() == 1){
                    long time_dura = 60*60*24*1000;
                    while (!(starttime >= time.EndTime().getTime())){
                        if ((starttime >= time.StartTime().getTime()
                             && starttime < time.EndTime().getTime())
                            ||(endtime > time.StartTime().getTime()
                               && endtime <= time.EndTime().getTime())
                            ||(starttime <= time.StartTime().getTime()
                               && endtime >= time.EndTime().getTime())) {
                                selectedAppts[j] = Apptlist.get(i);
                                j++;
                                break;
                            }
                        endtime += time_dura;
                        starttime += time_dura;
                    }
                }
                
                else if(Apptlist.get(i).getFrequency() == 2){
                    long time_dura = 7*60*60*24*1000;
                    while (!(starttime >= time.EndTime().getTime())){
                        if ((starttime >= time.StartTime().getTime()
                             && starttime < time.EndTime().getTime())
                            ||(endtime > time.StartTime().getTime()
                               && endtime <= time.EndTime().getTime())
                            ||(starttime <= time.StartTime().getTime()
                               && endtime >= time.EndTime().getTime())) {
                                selectedAppts[j] = Apptlist.get(i);
                                j++;
                                break;
                            }
                        endtime += time_dura;
                        starttime += time_dura;
                    }
                }
                
                else if(Apptlist.get(i).getFrequency() == 3){
                    //long time_dura = 60*60*24*1000;
                    int[][] m = new int[][]{{31,28,31,30,31,30,31,31,30,31,30,31},
                        {31,29,31,30,31,30,31,31,30,31,30,31}};
                    int if_leap_year = 0;
                    int month = Apptlist.get(i).TimeSpan().StartTime().getMonth();
                    int year = Apptlist.get(i).TimeSpan().StartTime().getYear();
                    long time_dura;
                    while(!(starttime >= time.EndTime().getTime())){
                        time_dura = 60*60*24*1000;
                        if(((year+1900)%4==0
                            &&(year+1900)%100!=0)
                           ||(year+1900)%400==0)
                            if_leap_year = 1;
                        else
                            if_leap_year = 0;
                        
                        if ((starttime >= time.StartTime().getTime() 
                             && starttime < time.EndTime().getTime())
                            ||(endtime > time.StartTime().getTime()
                               && endtime <= time.EndTime().getTime())
                            ||(starttime <= time.StartTime().getTime()
                               && endtime >= time.EndTime().getTime())) {
                                selectedAppts[j] = Apptlist.get(i);
                                j++;
                                break;
                            }
                        time_dura = time_dura*m[if_leap_year][month];
                        month++;
                        if(month > 11){
                            month = 0;
                            year++;
                        }
                        endtime += time_dura;
                        starttime += time_dura;
                    }
                }
            }
            
            if (j > 0) {
                Appt[] newlist = new Appt[j];
                for (int i = 0; i < j; i++) {
                    newlist[i] = selectedAppts[i];
                }
                return newlist;
            }
        }
        return null;
        // TODO Auto-generated method stub
    }
    
	@Override
	public Appt RetrieveAppts(int joinApptID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Appt[] RetrieveAppts(User entity, TimeSpan time, boolean b) {
	    
	    //public Appt[] RetrieveAppts(User entity, TimeSpan time) {
	        String userID = entity.ID();
	        
	        if (mAppts.containsKey(userID)) {
	            Vector<Appt> Apptlist = mAppts.get(userID);
	            Appt[] selectedAppts = new Appt[Apptlist.size()];
	            int j = 0;
	            for (int i = 0;i < Apptlist.size();i++) {
	            	if(!defaultUser.isAdmin() && userID != defaultUser.ID() && Apptlist.get(i).getPublicity() == false) {
	            		continue;
	            	}
	            	
	                // and < time + the length of a day in milliseconds
	                long starttime = Apptlist.get(i).TimeSpan().StartTime().getTime();
	                long endtime = Apptlist.get(i).TimeSpan().EndTime().getTime();
	                if(Apptlist.get(i).getFrequency() == 0){
	                    if ((starttime >= time.StartTime().getTime()
	                         && starttime < time.EndTime().getTime())
	                        ||(endtime > time.StartTime().getTime()
	                           && endtime <= time.EndTime().getTime())
	                        ||(starttime <= time.StartTime().getTime()
	                           && endtime >= time.EndTime().getTime())) {
	                            selectedAppts[j] = Apptlist.get(i);
	                            j++;
	                        }
	                }
	                
	                else if(Apptlist.get(i).getFrequency() == 1){
	                    long time_dura = 60*60*24*1000;
	                    while (!(starttime >= time.EndTime().getTime())){
	                        if ((starttime >= time.StartTime().getTime()
	                             && starttime < time.EndTime().getTime())
	                            ||(endtime > time.StartTime().getTime()
	                               && endtime <= time.EndTime().getTime())
	                            ||(starttime <= time.StartTime().getTime()
	                               && endtime >= time.EndTime().getTime())) {
	                                selectedAppts[j] = Apptlist.get(i);
	                                j++;
	                                break;
	                            }
	                        endtime += time_dura;
	                        starttime += time_dura;
	                    }
	                }
	                
	                else if(Apptlist.get(i).getFrequency() == 2){
	                    long time_dura = 7*60*60*24*1000;
	                    while (!(starttime >= time.EndTime().getTime())){
	                        if ((starttime >= time.StartTime().getTime()
	                             && starttime < time.EndTime().getTime())
	                            ||(endtime > time.StartTime().getTime()
	                               && endtime <= time.EndTime().getTime())
	                            ||(starttime <= time.StartTime().getTime()
	                               && endtime >= time.EndTime().getTime())) {
	                                selectedAppts[j] = Apptlist.get(i);
	                                j++;
	                                break;
	                            }
	                        endtime += time_dura;
	                        starttime += time_dura;
	                    }
	                }
	                
	                else if(Apptlist.get(i).getFrequency() == 3){
	                    //long time_dura = 60*60*24*1000;
	                    int[][] m = new int[][]{{31,28,31,30,31,30,31,31,30,31,30,31},
	                        {31,29,31,30,31,30,31,31,30,31,30,31}};
	                    int if_leap_year = 0;
	                    int month = Apptlist.get(i).TimeSpan().StartTime().getMonth();
	                    int year = Apptlist.get(i).TimeSpan().StartTime().getYear();
	                    long time_dura;
	                    while(!(starttime >= time.EndTime().getTime())){
	                        time_dura = 60*60*24*1000;
	                        if(((year+1900)%4==0
	                            &&(year+1900)%100!=0)
	                           ||(year+1900)%400==0)
	                            if_leap_year = 1;
	                        else
	                            if_leap_year = 0;
	                        
	                        if ((starttime >= time.StartTime().getTime() 
	                             && starttime < time.EndTime().getTime())
	                            ||(endtime > time.StartTime().getTime()
	                               && endtime <= time.EndTime().getTime())
	                            ||(starttime <= time.StartTime().getTime()
	                               && endtime >= time.EndTime().getTime())) {
	                                selectedAppts[j] = Apptlist.get(i);
	                                j++;
	                                break;
	                            }
	                        time_dura = time_dura*m[if_leap_year][month];
	                        month++;
	                        if(month > 11){
	                            month = 0;
	                            year++;
	                        }
	                        endtime += time_dura;
	                        starttime += time_dura;
	                    }
	                }
	            }
	            
	            if (j > 0) {
	                Appt[] newlist = new Appt[j];
	                for (int i = 0; i < j; i++) {
	                    newlist[i] = selectedAppts[i];
	                }
	                return newlist;
	            }
	        }
	        return null;
	        // TODO Auto-generated method stub
	    }
	
	
	public boolean checkOverlap(User user, Appt appt) {
		String userID = defaultUser.ID();
		Vector<Appt> Apptlist = mAppts.get(userID);
		if (Apptlist == null) {
			return false;
		}
		if (appt.getFrequency() == 0) {
			Appt[] Appts = RetrieveAppts(user, appt.TimeSpan());
			if (Appts != null) {
				return true;
			}
		} else if (appt.getFrequency() == 1) {
			for (int i = 0; i < Apptlist.size(); ++i) {
				Appt temp = Apptlist.get(i);
				if (temp.getFrequency() == 0) {
					if (temp.TimeSpan().EndTime().after(appt.TimeSpan().StartTime())&&isOverlapInHour(appt,temp)) {
						return true;
					}
				} else {
					if (isOverlapInHour(appt,temp)) {
						return true;
					}
				} 
			}
		} else if (appt.getFrequency() == 2) {
			for (int i = 0; i < Apptlist.size(); ++i) {
				Appt temp = Apptlist.get(i);
				if (temp.getFrequency() == 0) {
					if (temp.TimeSpan().EndTime().after(appt.TimeSpan().StartTime())&&
							isOverlapInHour(appt,temp)&&(temp.TimeSpan().EndTime().getDay()==appt.TimeSpan().StartTime().getDay())) {
						return true;
					}
				} else if (temp.getFrequency() == 1) {
					if (isOverlapInHour(appt,temp)) {
						return true;
					}
				} else if (temp.getFrequency() == 2) {
					if (isOverlapInHour(appt,temp)&&temp.TimeSpan().EndTime().getDay()==appt.TimeSpan().StartTime().getDay()) {
						return true;
					}
				} else {
					if (isOverlapInHour(appt,temp)) {
						return true;
					}
				}
			}
		} else if (appt.getFrequency() == 3) {
			for (int i = 0; i < Apptlist.size(); ++i) {
				Appt temp = Apptlist.get(i);
				if (temp.getFrequency() == 0) {
					if (temp.TimeSpan().EndTime().after(appt.TimeSpan().StartTime())&&
							isOverlapInHour(appt,temp)&&(temp.TimeSpan().EndTime().getDate()==appt.TimeSpan().StartTime().getDate())) {
						return true;
					}
				} else if (temp.getFrequency() == 1) {
					if (isOverlapInHour(temp,appt)) {
						return true;
					}
				} else if (temp.getFrequency() == 2) {
					if (isOverlapInHour(appt,temp)) {
						return true;
					}
				} else {
					if (isOverlapInHour(appt,temp)&&(temp.TimeSpan().EndTime().getDate()==appt.TimeSpan().StartTime().getDate())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	
	public boolean isOverlapInHour(Appt appt1, Appt appt2) {
		int startTime1 = appt1.TimeSpan().StartTime().getHours()*60+appt1.TimeSpan().StartTime().getMinutes();
		int startTime2 = appt2.TimeSpan().StartTime().getHours()*60+appt2.TimeSpan().StartTime().getMinutes();
		int endTime1 = appt1.TimeSpan().EndTime().getHours()*60+appt1.TimeSpan().EndTime().getMinutes();
		int endTime2 = appt2.TimeSpan().EndTime().getHours()*60+appt2.TimeSpan().EndTime().getMinutes();
		if ((startTime1 <= startTime2 && startTime2 < endTime1)||(startTime2 <= startTime1 && startTime1 < endTime2)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void UpdateAppt(Appt appt) {
		String userID = defaultUser.ID();
		if (mAppts.containsKey(userID)) {
			Vector<Appt> Apptlist = mAppts.get(userID);
			
			for (int i = 0;i < Apptlist.size();i++) {
				long starttime = Apptlist.get(i).TimeSpan().StartTime().getTime();
				long endtime = Apptlist.get(i).TimeSpan().EndTime().getTime();
				if ((starttime >= appt.TimeSpan().StartTime().getTime() 
						&& starttime < appt.TimeSpan().EndTime().getTime())
						||(endtime > appt.TimeSpan().StartTime().getTime()
						&& endtime <= appt.TimeSpan().EndTime().getTime())
						||(starttime <= appt.TimeSpan().StartTime().getTime()
						&& endtime >= appt.TimeSpan().EndTime().getTime())) {
					appt.setID(Apptlist.get(i).getID());
					Apptlist.set(i, appt);
					break;
				}
			}
		
		// TODO Auto-generated method stub

		}	
	}

	@Override
	public void RemoveAppt(Appt appt) {
		String userID = defaultUser.ID();
		if (mAppts.containsKey(userID)) {
			Vector<Appt> Apptlist = mAppts.get(userID);
			int size = Apptlist.size();
			for (int i = 0; i < size; i++) {
				if (appt.getID() == Apptlist.get(i).getID()) {
					Apptlist.remove(i);
//					mAppts.put(userID, Apptlist);
					return;
				}
			}
		}
		// TODO Auto-generated method stub
	}

	@Override
	public User getDefaultUser() {
		// TODO Auto-generated method stub
		return defaultUser;
	}

	@Override
	public void LoadApptFromXml() {
		// TODO Auto-generated method stub

	}
	// this function will schedule one appt nomatter it exist reminder or not
	public void scheduleAppt(Appt apt){
		if (apt.existReminder()){
			TimerTask r = new notifyTask(apt,taskScheduler,newTime);
			long userTime = newTime.getTime().getTime();
			System.out.println(userTime);
			long startTime = apt.TimeSpan().StartTime().getTime();
			System.out.println(startTime);
			long reminderTime = apt.getReminderTime();
			System.out.println(reminderTime);
			long notifyTime = startTime - userTime - reminderTime;
			System.out.println(notifyTime);
			if (notifyTime<0){		// may be because changed usertime
				long interval = 0;
				switch(apt.getFrequency()){
				case 0:
					System.out.print("happen before now,skip");
					return;
				case 1:
					interval = 24*60*60*1000;
					while(notifyTime<0)
						notifyTime+=interval;
					break;
				case 2:
					interval = 24*60*60*1000*7;
					while(notifyTime<0)
						notifyTime+=interval;
					break;
				case 3:
					// month is really a hard task
					// because interval changed
					int cy = apt.TimeSpan().StartTime().getYear();
					int cm = apt.TimeSpan().StartTime().getMonth();
					int leapyear = 0;
					// during the loop, consider a special case
					// a event happen at 31, but there is no 31 in the next month, skip a month
					boolean noDayForNextMonth = false;
					while(notifyTime<0 || noDayForNextMonth){
						// decide if it is leap year
						if (((cy+1900)%100==0 && (cy+1900)%400==0) 			
								|| ((cy+1900)%4==0 && (cy+1900)%100!=0)){
							leapyear = 1;
						}else{
							leapyear = 0;
						}
						interval = 24*60*60*1000*DAY_OF_MONTH[leapyear][cm];
						notifyTime+=interval;
						// calculate the next month
						cm++;
						if (cm == 11){
							cm = 0;
							cy++;
						}
						// decide if it is leap year
						if (((cy+1900)%100==0 && (cy+1900)%400==0) 			
								|| ((cy+1900)%4==0 && (cy+1900)%100!=0)){
							leapyear = 1;
						}else{
							leapyear = 0;
						}
						// next month doesn't has a 31 or 30
						if (apt.TimeSpan().StartTime().getDate()>DAY_OF_MONTH[leapyear][cm])	
							noDayForNextMonth = true;
						else
							noDayForNextMonth = false;																			
					}
					break;
				default:
					System.out.println("False Frequency");
					return;
				}
			}
		/*
		System.out.println();
		System.out.println(userTime);
		System.out.println(startTime);
		System.out.println(reminderTime);
		System.out.println(notifyTime);
		System.out.println();
		*/
		taskScheduler.schedule(r, notifyTime);
		}
	}
		

	/**
	 * this function will schedule all the appts with reminder
	 */
	public void scheduleApptsAll(){
		String userID = defaultUser.ID();
		// first discard all the existing schduled task
		taskScheduler.cancel();
		taskScheduler = new Timer();
		if (mAppts.containsKey(userID)){
			Vector<Appt> Apptlist = mAppts.get(userID);
			for (int i=0; i<Apptlist.size(); i++){
				Appt temp = Apptlist.get(i);
				this.scheduleAppt(temp);
			}
		}
	}
	
	/**
	 * \Note I don't want to use this method
	 */
	//public void whyShouldWePutScheduleHere()
	
	// verify user info
	public boolean verifyUser(String username, String password) {
		if (users.containsKey(username)) {
			System.out.println("jjj");
			if (users.get(username).Password().equals(password)) {
				System.out.println("ooo");
				return true;
			}
		}
		System.out.println("kkk");
		return false;
	}
	
	public User getUser(String username) {
		return users.get(username);
	}
	
	public void addUser(User user) {
		users.put(user.ID(), user);//System.out.println(users.toString());
	}
	
	@Override
	public void saveToDisk(String filepath){
		try{
			FileOutputStream fs = new FileOutputStream(filepath);
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(this);
			//os.writeUnshared(this);
			os.flush();
			os.close();
			fs.close();
			
			
			FileInputStream ls = new FileInputStream(filepath);
			ObjectInputStream ca = new ObjectInputStream(ls);
			Object temp = ca.readObject();
			ApptStorage A = (ApptStorage) temp;
			os.close();

			System.out.println("File saved!");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void loadFromDisk(String filepath){
		try{
			FileInputStream fs = new FileInputStream(filepath);
			ObjectInputStream os = new ObjectInputStream(fs);
			Object temp = os.readObject();
			ApptStorage A = (ApptStorage) temp;

			this.mAppts.putAll(A.mAppts);
			this.users.putAll(A.users);
			this.locationList.addAll(A.locationList);
			//System.out.println(this.users.toString());
			
			os.close();
		}catch(FileNotFoundException e){
			System.out.println("Wait! File Not Found!");
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Set<String> getAllUsers() {
		return users.keySet();
	}
	
	public User getUserView(){
		return this.userView;
	}
	
	public void setUserView(User user){
		userView = user;
	}
	
	public Vector<String> getAllUserIDs() {
		Vector<String> UserIDList = new Vector<String>(this.users.keySet());
		return UserIDList;
	}
	
	public void deleteUser(String userID){
		users.remove(userID);
		mAppts.remove(userID);
	}
}

//task to be run when notify
class notifyTask extends TimerTask{
	
	private Appt a;
	private transient Timer t;
	private UserTimer userTime;
	private int leapyear = 0;
	final static private long [][] DAY_OF_MONTH = {{31,28,31,30,31,30,31,31,30,31,30,31},
												{31,29,31,30,31,30,31,31,30,31,30,31}};
	// [0][] for non-leap year, [1][] for leap year
	
	notifyTask(Appt apt, Timer tim, UserTimer nt){
		a = apt;
		t = tim;
		userTime = nt;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		a.createNotifyFrame();
		long interval = 0;
		notifyTask r = new notifyTask(a,t,userTime);
		switch(a.getFrequency()){
		case 0:
			// only run once, skip
			break;
		case 1:
			// run daily
			interval = 24*60*60*1000;
			t.schedule(r, interval);
			break;
		case 2:
			// run weekly
			interval = 24*60*60*1000*7;
			t.schedule(r, interval);
			break;
		case 3:
			// run monthly, need to consider leap year or not
			// calculate the nearest apt time
			long nTime = userTime.getTime().getTime()+a.getReminderTime();
			Date nApTime = new Date(nTime);
			int cy = nApTime.getYear();
			int cm = nApTime.getMonth();
			int cd = nApTime.getDate();
			// decide if it is leap year
			if (((cy+1900)%100==0 && (cy+1900)%400==0) 			
					|| ((cy+1900)%4==0 && (cy+1900)%100!=0)){
				leapyear = 1;
			}else{
				leapyear = 0;
			}
			// add the day of current month
			interval = 24*60*60*1000*DAY_OF_MONTH[leapyear][cm];
			// calculate next month
			if(++cm == 12){
				cm = 0;
				cy++;
			}
			// trick: 1.we can at most skip one month for the special case here
			//		  2.if the year change, it can only be Jan. Leap or not doesn't matter 
			if (cd>DAY_OF_MONTH[leapyear][cm]){
				System.out.println("We should skip a month for special case");
				interval+=24*60*60*1000*DAY_OF_MONTH[leapyear][cm];
			}
			
			t.schedule(r, interval);
			break;
		default:
			System.out.println("False Frequency");
		
		}
	}
}


