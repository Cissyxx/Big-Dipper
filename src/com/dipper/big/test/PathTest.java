package com.dipper.big.test;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.dipper.big.Path;

public class PathTest
{
    private Path test;

    @Before
    public void set_up()
    {
        test = Mockito.mock(Path.class);
    }

    @Test
    public void test_size()
    {
        Mockito.when(test.size()).thenReturn(1);

        Assert.assertEquals(test.size(), 1);
    }

    @Test
    public void test_get()
    {
        final String value = "Hello World!";
        Mockito.when(test.get(1)).thenReturn(value);

        Assert.assertEquals(test.get(1), value);
    }

    @Test
    public void test_subList()
    {
        final List<String> sub = new LinkedList<String>();
        Mockito.when(test.subList(1, 5)).thenReturn(sub);

        Assert.assertEquals(test.subList(1, 5), sub);
    }

    @Test
    public void test_getPath()
    {
        final List<String> path = new LinkedList<String>();
        Mockito.when(test.getPath()).thenReturn(path);

        Assert.assertEquals(test.getPath(), path);
    }

}
