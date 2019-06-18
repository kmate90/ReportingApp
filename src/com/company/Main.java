package com.company;

import java.util.ArrayList;

import com.company.Util.FTPUploadFile;
import com.company.Util.Parser;
import com.company.Util.Validator;
import com.company.model.*;

public class Main {

    public static void main(String[] args) {
        Datasource datasource = new Datasource();
        FTPUploadFile upload=new FTPUploadFile();
        if(!datasource.open()){
            System.out.println("Can't open data source");
            return; }

        Parser parser= new Parser();
        Validator validator = new Validator();
        ArrayList<ListingStatus> statuses = parser.getListingStatus(Datasource.getListingStatusUrl());
        ArrayList<Listing> listings = parser.getListing(Datasource.getListingUrl());
        ArrayList<Location> locations = parser.getLocation(Datasource.getLocationUrl());
        ArrayList<Marketpalce> marketpalces = parser.getMarketplace(Datasource.getMarketplaceUrl());

        datasource.createTables();

        try {
            for(int i =0; i < statuses.size(); i++) {
                datasource.insertStatus(statuses.get(i));
            }
            for(int i =0; i < listings.size(); i++) {
                if (validator.validator(listings.get(i)) == true) {
                    datasource.insertListing(listings.get(i));
                }
            }
            for(int i =0; i < locations.size(); i++) {
                datasource.insertLocation(locations.get(i));
            }
            for(int i =0; i < marketpalces.size(); i++) {
                datasource.insertMarketplace(marketpalces.get(i));
            }

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        datasource.report();
        upload.upload();
        datasource.close();
    }
}
