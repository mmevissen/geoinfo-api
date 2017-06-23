package de.hsbochum.fbg.kswe.geoinfo.api;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by mmevissen
 */
public class UnsupportedCountryExceptionTest {

    @Test(expected = UnsupportedCountryException.class)
    public void testUnsupportedCountryException() throws IOException, UnsupportedCountryException {
        WeatherRetrieverImpl retriever = new WeatherRetrieverImpl();
       retriever.retrieve(new City("Paris", "France"));
    }
}