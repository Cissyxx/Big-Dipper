package com.dipper.big.test;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dipper.big.Path;


public class PathIntegrationTest
{
    private Path test;
    private List<String> value;

    @Before
    public void set_up()
    {
        value = new LinkedList<String>();
        for (int i = 0; i < 10; i++)
        {
            value.add("Hello World No. " + i);
        }
        test = new Path(value);
    }

    @Test
    public void test_constructor()
    {
        Assert.assertEquals(test.getPath(), value);
        Assert.assertEquals(test.size(), 10);
    }

    @Test
    public void test_add()
    {
        test.add("Hello World No. 10");

        Assert.assertEquals(test.size(), 11);
    }

    @Test
    public void test_get()
    {
        Assert.assertEquals(test.get(0), "Hello World No. 0");
    }

    @Test
    public void test_subList()
    {
        final List<String> sub = value.subList(2, 5);

        Assert.assertEquals(test.subList(2, 5), sub);
    }

}
