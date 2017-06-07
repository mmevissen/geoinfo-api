
package de.hsbochum.fbg.kswe.geoinfo.api;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author <a href="mailto:m.rieke@52north.org">Matthes Rieke</a>
 */
public class WeatherRetrieverImplTest {
    
    @Test
    public void testRetrieval() throws UnsupportedCountryException, IOException, InterruptedException, BrokenBarrierException, TimeoutException {
        WeatherRetrieverImpl retriever = new WeatherRetrieverImpl();
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        
        final AtomicReference<Float> temp = new AtomicReference<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Weather weather;
                try {
                    weather = retriever.retrieve(new City("Bochum", "Germany"));
                    temp.set(weather.getTemp());
                    
                    Thread.sleep(3000);
                    
                    cyclicBarrier.await();
                } catch (UnsupportedCountryException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } catch (BrokenBarrierException ex) {
                    ex.printStackTrace();
                }
                
            }
        }).start();
        
        cyclicBarrier.await(5, TimeUnit.SECONDS);
        
        //check and assert a range that is suitable for °C
        Assert.assertThat(temp.get() >= 60, CoreMatchers.is(true));
    }

}
