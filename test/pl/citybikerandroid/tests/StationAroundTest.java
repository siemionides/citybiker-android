package pl.citybikerandroid.tests;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import pl.citybikerandroid.messages.InformativeMessage;
import pl.citybikerandroid.messages.LogisticalMessage;
import pl.citybikerandroid.messages.Message;
import pl.citybikerandroid.messages.ServiceMessage;
import pl.citybikerandroid.stations.StationAround;
import pl.citybikerandroid.stations.BikeStation;

/*import pl.citybikerandroid.tests.activities.InformativeMessage;
import pl.citybikerandroid.tests.activities.LogisticalMessage;
import pl.citybikerandroid.tests.activities.StationAround;*/


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.anyOf;

@RunWith(RobolectricTestRunner.class)
public class StationAroundTest {
	StationAround s1, s2;
	
    
    @Before
    public void setUpStationAroundObjectTest() throws Exception{
    	s1 = new StationAround();
//    		BikeStation bs1 = new BikeStation();    		
    	
    		s1.setName("Stacja pierwsza");
    		s1.setId(67874);
    		s1.setNrBikes(5);
    		
    		s1.addInformativeMessage(new InformativeMessage("Bardzo piekny dzien!"));
    		s1.addInformativeMessage(new InformativeMessage("Bardzo piekny dzien21 !", "james"));
    		s1.addLogisticalMessage(new LogisticalMessage("Jade tam i bede niebawem", 
    				LogisticalMessage.GOING_TO, new BikeStation("Stacja Druga", 54543),s1, 34534 ));
    		
    		s1.addLogisticalMessage(new LogisticalMessage("Czas przejazdu to ", 
    	    				LogisticalMessage.TIME_BETWEEN, new BikeStation("Stacja Druga", 54543), s1, 43433 ));
    	    				
    	    s1.addServiceMessage(new ServiceMessage("Rower neidziała dobrze!", "james"));
    	    
    	    s1.addServiceMessage(new ServiceMessage("Hamulce nie śmigają!"));
    	    
    		
    	s1.setDistanceTo(5.4);
    	
    	
    	s2 = new StationAround("Stacja nowa testowa", 65544, BikeStation.MORE_THAN_FOUR);
    	
    	s2.addInformativeMessage(new InformativeMessage("Halo day!"));
    	s2.setDistanceTo(5.3);
    		
    }
    
    @Test
    public void testStationAroundObject() throws Exception{
    	
    	
    	assertThat(s1.getName(), equalTo("Stacja pierwsza"));
    	assertThat(s1.getId(), equalTo(67874));
    	assertThat(s1.getNrBikes(), equalTo(5));
    	
    	assertThat(s2.getName(), equalTo("Stacja nowa testowa"));
    	assertThat(s2.getId(), equalTo(65544));
    	assertThat(s2.getNrBikes(), equalTo(BikeStation.MORE_THAN_FOUR));

    	
    	
    	// Test messages for s1
    	for(InformativeMessage m : s1.getInformativeMessages()){
    		assertTrue(m.getText().equals("Bardzo piekny dzien!") || m.getText().equals("Bardzo piekny dzien21 !") );
    	}
    	
    	for(LogisticalMessage m : s1.getLogisticalMessages()){
    		assertTrue(m.getText().equals("Jade tam i bede niebawem") || m.getText().equals("Czas przejazdu to ") );
    	}
    	
    	for(ServiceMessage m : s1.getServicelMessages()){
    		assertTrue(m.getText().equals("Rower neidziała dobrze!") || m.getText().equals("Hamulce nie śmigają!") );
    		assertTrue(m.getAuthorName().equals("james") || m.getAuthorName().equals(Message.AUTHOR_ANONYMOUS));
    	}
    	
    	// test messages for s2
    	for(InformativeMessage m : s2.getInformativeMessages()){
    		assertTrue(m.getText().equals("Halo day!") );
    	}
    	
    	
    	
    	assertThat(s1.getDistanceTo(), equalTo(5.4));
    	assertThat(s2.getDistanceTo(), equalTo(5.3));
    	
    }
	
}
