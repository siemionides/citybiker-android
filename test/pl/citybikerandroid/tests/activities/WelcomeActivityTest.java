package pl.citybikerandroid.tests.activities;

import pl.citybikerandroid.R;
import pl.citybikerandroid.WelcomeActivity;
import pl.citybikerandroid.stations.BikeStationAround;

import org.junit.Before;
//import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class WelcomeActivityTest {
	WelcomeActivity activity;
	BikeStationAround s1;
	
	@Before
	public void setUp() throws Exception{
		activity = Robolectric.buildActivity(WelcomeActivity.class).create().get();
		
		
	}
	
    @Test
    public void shouldHaveHappySmiles() throws Exception {
        String hello = new WelcomeActivity().getResources().getString(R.string.hello_world);
        assertThat(hello, equalTo("Hello world!"));
    }
    
    @Test
    public void shouldColorsBeCorrect() throws Exception{
    	
    }
    
    

    
    
}