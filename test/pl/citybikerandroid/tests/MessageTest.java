package pl.citybikerandroid.tests;

import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import pl.citybikerandroid.messages.InformativeMessage;
import pl.citybikerandroid.messages.LogisticalMessage;
import pl.citybikerandroid.messages.Message;
import pl.citybikerandroid.messages.ServiceMessage;
import pl.citybikerandroid.stations.BikeStation;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(RobolectricTestRunner.class)
public class MessageTest {
	Message m;
	
	InformativeMessage iM1, iM2;
	LogisticalMessage lM1, lM2;
	ServiceMessage sM;
	
	@Before
	public void setUp() throws Exception{
		//m = new Mes
		iM1 = new InformativeMessage("tekst wiadomości infomacyjnej");
		iM2 = new InformativeMessage("text wiadomości informacyjnej 2 ", "John");
		
		
		lM1 = new LogisticalMessage("widomość logistyczna od A do B tyle czasu",
				LogisticalMessage.TIME_BETWEEN, new BikeStation(), new BikeStation(), 1102 );
		
		lM2 = new LogisticalMessage("wiadomość log 2 od A do B jade", "Peter", 
				LogisticalMessage.GOING_TO, new BikeStation(), new BikeStation(),2322 );
		
		
		//iM2.setPhotoUrl()
		
		//test data
		
	}
	
	@Test
	public void testMessages() throws Exception{
		
		//text
		assertThat(iM1.getText(), equalTo("tekst wiadomości infomacyjnej"));
		assertThat(iM2.getText(), equalTo("text wiadomości informacyjnej 2 "));
		
		assertThat(lM1.getText(), equalTo("widomość logistyczna od A do B tyle czasu"));
		assertThat(lM2.getText(), equalTo("wiadomość log 2 od A do B jade"));
		
		//author
		assertThat(iM1.getAuthorName(), equalTo(Message.AUTHOR_ANONYMOUS));
		assertThat(iM2.getAuthorName(), equalTo("John"));
		
		assertThat(lM1.getAuthorName(), equalTo(Message.AUTHOR_ANONYMOUS));
		assertThat(lM2.getAuthorName(), equalTo("Peter"));
		
		//photo
		assertThat(iM1.hasPhoto(), equalTo(false));
		assertThat(lM1.hasPhoto(), equalTo(false));
		assertThat(lM2.hasPhoto(), equalTo(false));
		
	
	}

}
