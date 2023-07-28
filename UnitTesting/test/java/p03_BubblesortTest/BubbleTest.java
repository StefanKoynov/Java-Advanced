package p03_BubblesortTest;

import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.Test;
import p04_BubbleSortTest.Bubble;

public class BubbleTest {


    @Test
    public void testBubbleSort() {

        int [] numbers = {34,0,2,45,67,87,1,3,99,12};

        Bubble.sort(numbers);

        int [] expected = {0,1,2,3,12,34,45,67,87,99};

        Assert.assertArrayEquals(numbers, expected);
    }


}
