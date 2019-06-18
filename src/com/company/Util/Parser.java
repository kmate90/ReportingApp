package com.company.Util;

import com.company.model.Listing;
import com.company.model.ListingStatus;
import com.company.model.Location;
import com.company.model.Marketpalce;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class Parser {


    public ArrayList<ListingStatus> getListingStatus(String data_url) {

        String inline = "";
        try {
            URL url = new URL(data_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responsecode = conn.getResponseCode();
            if (responsecode != 200)
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            else {
                Scanner sc = new Scanner(url.openStream());
                while (sc.hasNext()) {
                    inline += sc.nextLine();
                }
                System.out.println("\nJSON Input in String format");
                System.out.println(inline);
                sc.close();
            }

            JSONParser parse = new JSONParser();
            JSONArray listingStatus = (JSONArray) parse.parse(inline);
            ArrayList<ListingStatus> statuses = new ArrayList<>();

            for (int i = 0; i < listingStatus.size(); i++) {
                JSONObject jsonobj_1 = (JSONObject) listingStatus.get(i);
                ListingStatus temp = new ListingStatus();
                int id = Integer.parseInt(jsonobj_1.get("id").toString());
                temp.setId(id);
                temp.setStatusName(jsonobj_1.get("status_name").toString());
                statuses.add(temp);
            }
            conn.disconnect();
            return statuses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Listing> getListing(String data_url) {

        String inline = "";
        try {
            URL url = new URL(data_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responsecode = conn.getResponseCode();
            if (responsecode != 200)
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            else {
                Scanner sc = new Scanner(url.openStream());
                while (sc.hasNext()) {
                    inline += sc.nextLine();
                }
                System.out.println("\nJSON Input in String format");
                System.out.println(inline);
                sc.close();
            }

            JSONParser parse = new JSONParser();
            JSONArray listing = (JSONArray) parse.parse(inline);
            ArrayList<Listing> listings = new ArrayList<>();

            for (int i = 0; i < listing.size(); i++) {
                JSONObject jsonobj_1 = (JSONObject) listing.get(i);
                Listing temp = new Listing();
                temp.setId(jsonobj_1.get("id").toString());
                temp.setTitle(jsonobj_1.get("title").toString());
                temp.setDescription(jsonobj_1.get("description").toString());
                temp.setInventoryItemLocationId(jsonobj_1.get("location_id").toString());
                float price = Float.parseFloat(jsonobj_1.get("listing_price").toString());
                temp.setListingPrice(price);
                temp.setCurrency(jsonobj_1.get("currency").toString());
                int quantity = Integer.parseInt(jsonobj_1.get("quantity").toString());
                temp.setQuantity(quantity);
                int listingStatus = Integer.parseInt(jsonobj_1.get("listing_status").toString());
                temp.setListingStatus(listingStatus);
                int marketplace = Integer.parseInt(jsonobj_1.get("marketplace").toString());
                temp.setMarketplace(marketplace);
                if (jsonobj_1.get("upload_time") != null) {
                    String date = fixDate(jsonobj_1.get("upload_time").toString());

                    temp.setUploadTime(date);
                } else {
                    temp.setUploadTime("missing");
                }
                temp.setOwnerEmailAddress(jsonobj_1.get("owner_email_address").toString());
                listings.add(temp);
            }
            conn.disconnect();
            return listings;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public ArrayList<Location> getLocation(String data_url) {

        String inline = "";
        try {
            URL url = new URL(data_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responsecode = conn.getResponseCode();
            if (responsecode != 200)
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            else {
                Scanner sc = new Scanner(url.openStream());
                while (sc.hasNext()) {
                    inline += sc.nextLine();
                }
                System.out.println("\nJSON Input in String format");
                System.out.println(inline);
                sc.close();
            }

            JSONParser parse = new JSONParser();
            JSONArray location = (JSONArray) parse.parse(inline);
            ArrayList<Location> locations = new ArrayList<>();

            for (int i = 0; i < location.size(); i++) {
                JSONObject jsonobj_1 = (JSONObject) location.get(i);
                Location temp = new Location();
                temp.setId(jsonobj_1.get("id").toString());
                temp.setManagerName(jsonobj_1.get("manager_name").toString());
                temp.setPhone(jsonobj_1.get("phone").toString());
                temp.setAddressPrimary(jsonobj_1.get("address_primary").toString());
                if (jsonobj_1.get("address_secondary") != null) {
                    temp.setAddressSecondary(jsonobj_1.get("address_secondary").toString());
                } else {
                    temp.setAddressSecondary("No secondary address");
                }
                temp.setCountry(jsonobj_1.get("country").toString());
                temp.setTown(jsonobj_1.get("town").toString());
                if (jsonobj_1.get("postal_code") != null) {
                    temp.setPostalCode(jsonobj_1.get("postal_code").toString());
                } else {
                    temp.setPostalCode("No postal code");
                }
                locations.add(temp);
            }
            conn.disconnect();
            return locations;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String fixDate(String content) {
        String str = content;
        String[] arrOfStr = str.split("/", 3);
        String month = arrOfStr[0];
        String day = arrOfStr[1];
        String year = arrOfStr[2];
        if (month.length() < 2) {
            month = "0" + month;
        }
        if (day.length() < 2) {
            day = "0" + day;
        }

        StringBuilder sb = new StringBuilder(year + "-" + month + "-" + day);

        return sb.toString();
    }


    public ArrayList<Marketpalce> getMarketplace(String data_url) {

        String inline = "";
        try {
            URL url = new URL(data_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responsecode = conn.getResponseCode();
            if (responsecode != 200)
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            else {
                Scanner sc = new Scanner(url.openStream());
                while (sc.hasNext()) {
                    inline += sc.nextLine();
                }
                System.out.println("\nJSON Input in String format");
                System.out.println(inline);
                sc.close();
            }

            JSONParser parse = new JSONParser();
            JSONArray marketplace = (JSONArray) parse.parse(inline);
            ArrayList<Marketpalce> marketpalces = new ArrayList<>();

            for (int i = 0; i < marketplace.size(); i++) {
                JSONObject jsonobj_1 = (JSONObject) marketplace.get(i);
                Marketpalce temp = new Marketpalce();
                int id = Integer.parseInt(jsonobj_1.get("id").toString());
                temp.setId(id);
                temp.setMarketplaceName(jsonobj_1.get("marketplace_name").toString());
                marketpalces.add(temp);
            }
            conn.disconnect();
            return marketpalces;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

