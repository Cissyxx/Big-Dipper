package com.dipper.big.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.dipper.big.Record;

public class RecordTest
{
    private Record test;

    @Before
    public void set_up()
    {
        test = Mockito.mock(Record.class);
    }

    @Test
    public void test_contains()
    {
        final boolean value = true;
        Mockito.when(test.contains(1)).thenReturn(value);

        Assert.assertEquals(test.contains(1), value);
    }

    @Test
    public void test_getRecord()
    {
        final Set<Integer> record = new HashSet<Integer>();

        Mockito.when(test.getRecord()).thenReturn(record);

        Assert.assertEquals(test.getRecord(), record);
    }

}
