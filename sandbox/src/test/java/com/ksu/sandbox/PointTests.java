package com.ksu.sandbox;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Ksu on 28.02.2016.
 */
public class PointTests {

    @Test
    public void distanceBasicTest()
    {
        Point p = new Point(1, 1);
        Point p2 = new Point(1, 7);
        //assert p.distance(p2) == 6; - another way
        Assert.assertEquals(p.distance(p2), 6.0);

    }

    @Test
    public void distanceNegativeCoordinatesTest()
    {
        Point p = new Point(-1, 11);
        Point p2 = new Point(-1, 200);
        Assert.assertEquals(p.distance(p2), 189.0);

    }

    @Test(expectedExceptions=NullPointerException.class)
    public void distanceTestNullParam()
    {
        Point p = new Point(1, 8);
        Point p2 = null;
        p.distance(p2);
    }
}
