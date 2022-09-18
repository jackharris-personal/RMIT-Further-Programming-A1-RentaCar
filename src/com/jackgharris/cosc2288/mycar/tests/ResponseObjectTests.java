package com.jackgharris.cosc2288.mycar.tests;

import com.jackgharris.cosc2288.mycar.core.Ascii;
import com.jackgharris.cosc2288.mycar.core.Request;
import com.jackgharris.cosc2288.mycar.core.Response;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResponseObjectTests {


    @Test
    public void responseGetData(){
        Response response = new Response();
        response.add("test", 123);

        assertEquals(123,response.get("test"));
    }

    @Test
    public void responseContainsKey(){
        Response response = new Response();
        response.add("my response","Hello World!");

        assertTrue(response.containsKey("my response"));
    }

    @Test
    public void responseGetNotification(){
        Response response = new Response();
        response.setNotification("Hello World!");

        assertEquals(Ascii.green+"Hello World!"+Ascii.black,response.getNotification());
    }

    @Test
    public void responseContainsNotification(){
        Response response = new Response();
        response.setNotification("ABC");

        assertTrue(response.containsNotification());
    }

    @Test
    public void responseGetError(){
        Response response = new Response();
        response.setError("ERROR !!!");

        assertEquals(Ascii.red+"ERROR !!!"+Ascii.black, response.getError());
    }

    @Test
    public void responseContainsError(){
        Response response = new Response();
        response.setError("New Error!");

        assertTrue(response.containsError());
    }

    @Test
    public void responseGetViewRedirect(){
        Response response = new Response();
        response.setViewRedirect("/test");

        assertEquals("/test", response.getViewRedirect());
    }

    @Test
    public void responseShouldRedirect(){
        Response response = new Response();
        response.setViewRedirect("/test2");

        assertTrue(response.shouldRedirect());
    }

    @Test
    public void responseCreateFromRequest(){
        Request request = new Request();
        request.add("test",123);

        Response response = new Response(request);

        assertTrue(response.containsKey("test"));

    }

}
