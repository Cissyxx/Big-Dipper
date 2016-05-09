package com.dipper.big.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dipper.big.Record;

public class RecordIntegrationTest
{
    private Record test;

    @Before
    public void set_up()
    {
        test = new Record();
    }

    @Test
    public void test_contructor()
    {
        final Set<Integer> record = new HashSet<Integer>();
        record.add(1);
        test = new Record(record);

        Assert.assertTrue(test.contains(1));
    }

    @Test
    public void test_add()
    {
        test.add(1);
        Assert.assertEquals(test.size(), 1);
    }

    @Test
    public void test_remove()
    {
        test.add(1);
        test.remove(1);
        Assert.assertEquals(test.size(), 0);
    }

    @Test
    public void test_contains()
    {
        test.add(1);

        Assert.assertEquals(test.contains(1), true);
        Assert.assertEquals(test.contains(0), false);
    }
}
