package com.jackgharris.cosc2288.mycar.tests;

import com.jackgharris.cosc2288.mycar.core.Request;
import org.junit.Test;

import static org.junit.Assert.*;

public class RequestObjectTests {


    @Test
    public void requestGetUserInput(){

        Request request = new Request();

        request.setUserInput("Hello World");

        assertTrue(request.getUserInput().matches("Hello World"));
    }

    @Test
    public void requestContainsUserInput(){

        Request request = new Request();

        request.setUserInput("Hello World");

        assertTrue(request.containsUserInput());
    }

    @Test
    public void requestResetUserInput(){
        Request request = new Request();

        request.setUserInput("Hello World");
        request.resetUserInput();

        assertNull(request.getUserInput());
    }

    @Test
    public void requestGet(){
        Request request = new Request();

        request.add("test","unit test");

        assertEquals("unit test", request.get("test"));
    }

    @Test
    public void requestContainsData(){
        Request request = new Request();

        request.add("test","unit test");

        assertTrue(request.containsData("test"));

    }

    @Test
    public void requestSetError(){
        Request request = new Request();

        request.setError("unit test error");

        assertEquals("unit test error", request.getError());
    }

    @Test
    public void requestIsInteger(){
        Request request = new Request();
        request.setUserInput("2");

        assertTrue(request.isInteger());
    }

    @Test
    public void requestGetUserInputAsInteger(){

        Request request = new Request();
        request.setUserInput("5");
        assertEquals(5,request.getUserInputAsInteger());
    }

    @Test
    public void requestIsDate(){
        Request request = new Request();

        request.setUserInput("18/09/2022");
        assertTrue(request.isDate());
    }

}
