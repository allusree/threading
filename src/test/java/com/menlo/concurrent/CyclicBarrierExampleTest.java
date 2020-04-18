package com.menlo.concurrent;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

public class CyclicBarrierExampleTest {
    private CyclicBarrierExample cyclicBarrierExample;
    private String junitName;

    @Before
    public void setup() {
        cyclicBarrierExample = new CyclicBarrierExample();
    }

    @Test
    public void testExample() {
        cyclicBarrierExample.example();
        System.out.println("Junit completed");
    }
}
