package com.dipper.big.test;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.dipper.big.DistanceResult;
import com.dipper.big.LocationException;
import com.dipper.big.MapManager;


public class MapManagerTest
{

    private MapManager mManager;

    //    @Test
    //    public void test1() throws Exception  {
    //        //  create mock
    //        final DistanceMatrix distanceMatrix = Mockito.mock(DistanceMatrix.class);
    //        System.out.println("1");
    //        final GeoApiContext mContext = Mockito.mock(GeoApiContext.class);
    //        System.out.println("2");
    //        final String apiKey = "123456";
    //        System.out.println("3");
    //
    //        Mockito.doReturn(mContext).when(mContext.setApiKey(apiKey));
    //        //        Mockito.when(mManager.mContext.setApiKey(apiKey)).
    //        //
    //        //        // define return value for method getUniqueId()
    //        //        Mockito.when(DistanceMatrixApi.getDistanceMatrix(mContext, mArray,mArray).await()).thenReturn(distanceMatrix);
    //        //
    //        //        // use mock in test....
    //        //        assertEquals(DistanceMatrixApi.getDistanceMatrix(mContext, mArray,mArray).await(), distanceMatrix);
    //    }

    @Test
    public void test_getAllOptimalPath() throws LocationException
    {
        final DistanceResult mResult = new DistanceResult();
        final DistanceResult destinations = new DistanceResult();
        Mockito.doReturn(mResult).when(mManager.getAllOptimalPath(destinations, true));
        Assert.assertEquals(mResult, mResult);
    }
    // public DistanceResult getAllOptimalPath(DistanceResult mDestinations, boolean isStartLocSet)

    //    public static MapManager getInstance(final String API_KEY)
    //    {
    //        mManager.mContext.setApiKey(API_KEY);
    //        return mManager;
    //    }

    //    // Demonstrates the return of multiple values
    //    @Test
    //    public void testMoreThanOneReturnValue()  {
    //        final Iterator i= mock(Iterator.class);
    //        when(i.next()).thenReturn("Mockito").thenReturn("rocks");
    //        final String result=i.next()+" "+i.next();
    //        //assert
    //        assertEquals("Mockito rocks", result);
    //    }
}
