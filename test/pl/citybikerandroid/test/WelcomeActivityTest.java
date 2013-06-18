package pl.citybikerandroid.test;

import pl.citybikerandroid.R;
import pl.citybikerandroid.WelcomeActivity;

//import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class WelcomeActivityTest {

    @Test
    public void shouldHaveHappySmiles() throws Exception {
        String hello = new WelcomeActivity().getResources().getString(R.string.hello_world);
        assertThat(hello, equalTo("Hello world!"));
    }
}