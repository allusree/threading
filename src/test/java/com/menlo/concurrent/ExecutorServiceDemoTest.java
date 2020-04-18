package com.menlo.concurrent;

import org.junit.Before;
import org.junit.Test;

public class ExecutorServiceDemoTest {

    private ExecutorServiceDemo executorServiceDemo;

    @Before
    public void setup(){
        executorServiceDemo = new ExecutorServiceDemo();
    }
    @Test
    public void testThreadExecute() throws Exception {
        executorServiceDemo.threadExecute();
        System.out.println("JUNIT completed");
    }

    @Test
    public void testThreadSubmit() throws Exception {
        executorServiceDemo.threadSubmit();
        System.out.println("JUNIT completed");
    }
    @Test
    public void testThreadSubmitCallable() throws Exception {
        executorServiceDemo.threadSubmitCallable();
        System.out.println("JUNIT completed");
    }
    @Test
    public void testThreadCallableInvoke() throws Exception {
        executorServiceDemo.threadCallableInvoke();
        System.out.println("JUNIT completed");
    }
    @Test
    public void testThreadSubmitCallableCanceled() throws Exception {
        executorServiceDemo.threadSubmitCallableCancel();
        System.out.println("JUNIT completed");
    }
}
