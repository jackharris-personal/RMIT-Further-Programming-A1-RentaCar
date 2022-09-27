//**** PACKAGE ****\\
//Set the specific package for this class file
package com.jackgharris.cosc2288.mycar.enums;

//**** CREATE ENUM ****\\
//Create our public emun, in this case we call it sort and have two options
//"Ascending" & "Descending" this makes it easy for the CarCollection to be
//locked to one of the two input options.

//16/09/2022 | Added Greater than and less than to the sort enum, these are used
//inside our new getWhereSeats helper facade method to determine if we want seats
//greater or less than the integer provided.
public enum Sort {

    //Sort by descending
    DESCENDING,
    //Sort by Ascending
    ASCENDING,
    //Sort by greater than
    GREATER_THAN,
    //sort by less than
    LESS_THAN
}
