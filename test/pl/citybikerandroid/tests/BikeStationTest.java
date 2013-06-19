package pl.citybikerandroid.tests;

import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import pl.citybikerandroid.messages.InformativeMessage;
import pl.citybikerandroid.messages.LogisticalMessage;
import pl.citybikerandroid.messages.ServiceMessage;
import pl.citybikerandroid.stations.BikeStation;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(RobolectricTestRunner.class)
public class BikeStationTest {
	BikeStation s1, s2;
	
	Date date1,date2, date3, date4;
	

	@Before
	public void setUp() throws Exception{

		//test date // year - month, day, hour , minute
		date1 = new Date(2012, 8, 03, 15, 43);
		date2 = new Date(2012, 8, 03, 15, 50);
		date3 = new Date(2012, 8, 03, 15, 55);
		date4 = new Date(2012, 8, 03, 15, 59);
		
		s1 = new BikeStation("stacja1", 4343);
			s1.addInformativeMessage(new InformativeMessage("bardzo tutaj fajnie!", date1));
			s1.addInformativeMessage(new InformativeMessage("bardzo tutaj fajnie!", date2));
			
			s1.addLogisticalMessage(new LogisticalMessage("jade na moko", LogisticalMessage.GOING_TO, s1, new BikeStation(), 232, date3));
			s1.addLogisticalMessage(new LogisticalMessage("jade na moko", LogisticalMessage.GOING_TO, s1, new BikeStation(), 232, date1));
			
		s2 = new BikeStation("statcja2", 3545);
			s2.addServiceMessage(new ServiceMessage("nie dziala dzwonek", date4));
			s2.addServiceMessage(new ServiceMessage("nie dziala hamulec", date3));
			s2.addServiceMessage(new ServiceMessage("nie dziala hamulec", date2));
	}
	
	
	@Test
	public void testMessages() throws Exception{
		
		assertThat(s1.getLastInformativeMessage().getDate(), equalTo(new Date(2012, 8, 03, 15, 50)));
		
		assertThat(s1.getLastLogisticalMessage().getDate(), equalTo(new Date(2012, 8, 03, 15, 55)));
		
		assertThat(s2.getLastServiceMessage().getDate(), equalTo(new Date(2012, 8, 03, 15, 59)));

		
		
		
	
	}

}
