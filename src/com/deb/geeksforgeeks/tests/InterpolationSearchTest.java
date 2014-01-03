package com.deb.geeksforgeeks.tests;

import com.deb.geeksforgeeks.searching.InterpolationSearch;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 1/3/14
 * Time: 9:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class InterpolationSearchTest {
    @Test
    public void testInterpolateSearch() throws Exception {
        int[] array = {1,2,3,5,8,15,18,100,150};
        Assert.assertEquals(1, InterpolationSearch.interpolateSearch(array, 2));
        Assert.assertEquals(-1,InterpolationSearch.interpolateSearch(array, 4));
        Assert.assertEquals(-1,InterpolationSearch.interpolateSearch(array, 9));
    }
}
