package com.example.networking;

public class Mountain {

    private String ID;
    private String name;
    private String type;
    private String company;
    private String location;
    private String category;
    private int size;
    private int cost;
    private Auxdata auxdata;

   /* public Mountain(String name, String location, int cost, Auxdata auxdata) {
        this.name = name;
        this.location = location;
        this.cost = cost;
        this.auxdata = auxdata;}*/

    public String getName() { return name;}

    public String getLocation() { return location;}

    public int getSize() { return size;}

    public Auxdata getAuxdata() { return auxdata;}

    @Override
    public String toString() { return name + " located at " + location + " at " + size + " meters high. ";}

}

