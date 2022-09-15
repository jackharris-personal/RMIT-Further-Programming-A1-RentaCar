package com.jackgharris.cosc2288.mycar.controllers;

import com.jackgharris.cosc2288.mycar.core.Application;
import com.jackgharris.cosc2288.mycar.core.Controller;
import com.jackgharris.cosc2288.mycar.core.Request;
import com.jackgharris.cosc2288.mycar.core.Response;
import com.jackgharris.cosc2288.mycar.views.ReservationView;

public class ReservationController extends Controller {

    public ReservationController(Application app) {
        super(app);
        this.view = new ReservationView();
    }

    @Override
    public void handleRequest(Request request) {

    }

    @Override
    public void updateView(Response response) {

    }
}
