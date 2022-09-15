package com.jackgharris.cosc2288.mycar.views;

import com.jackgharris.cosc2288.mycar.core.Request;
import com.jackgharris.cosc2288.mycar.core.Response;
import com.jackgharris.cosc2288.mycar.core.View;

public class ReservationView extends View {

    public Request selectPickupDate(Response response){

        return this.getUserInput(new Request());
    }

    public Request selectReturnDate(Response response){

        return this.getUserInput(new Request());
    }

    public Request showVehicleDetails(Response response){

        return this.getUserInput(new Request());
    }

    public Request collectUserDetails(Response response){

        return this.getUserInput(new Request());
    }

    public Request confirmAndPay(Response response){

        return this.getUserInput(new Request());
    }

}
