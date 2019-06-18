package com.company.model;


import org.json.simple.JsonArray;
import org.json.simple.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Datasource {

    private static final String listingUrl="https://my.api.mockaroo.com/listing?key=63304c70";
    private static final String locationUrl="https://my.api.mockaroo.com/location?key=63304c70";
    private static final String listingStatusUrl="https://my.api.mockaroo.com/listingStatus?key=63304c70";
    private static final String marketplaceUrl="https://my.api.mockaroo.com/marketplace?key=63304c70";

    private static final String DB_NAME = "listing.db";
    private static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\Máté\\Desktop\\JavaPrograms\\ReportingApp\\" + DB_NAME;


    public static final String TABLE_LISTING_STATUS ="listing_status";
    public static final String COLUMN_STAUS_ID ="id";
    private static final String COLUMN_STATUS_NAME ="status_name";

    private static final String TABLE_LISTING ="listing";
    private static final String COLUMN_LISTING_ID="id";
    private static final String COLUMN_LISTING_TITLE="title";
    private static final String COLUMN_LISTING_DESCRIPTION="description";
    private static final String COLUMN_LISTING_LOCATIONID="location_id";
    private static final String COLUMN_LISTING_PRICE="listing_price";
    private static final String COLUMN_LISTING_CURRENCY="currency";
    private static final String COLUMN_LISTING_QUANTITY="quantity";
    private static final String COLUMN_LISTING_STATUS="listing_status";
    private static final String COLUMN_LISTING_MARKETPLACE="marketplace";
    private static final String COLUMN_LISTING_UPLOADTIME="upload_time";
    private static final String COLUMN_LISTING_EMAIL="owner_email_address";

    public static final String TABLE_LOCATION ="location";
    public static final String COLUMN_LOCATION_ID="id";
    private static final String COLUMN_LOCATION_MANAGER="manager_name";
    private static final String COLUMN_LOCATION_PHONE="phone";
    private static final String COLUMN_LOCATION_1ADDRESS="address_primary";
    private static final String COLUMN_LOCATION_2ADDRESS="address_secondary";
    private static final String COLUMN_LOCATION_COUNTRY="country";
    private static final String COLUMN_LOCATION_TOWN="town";
    private static final String COLUMN_LOCATION_POSTALCODE="postal_code";

    public static final String TABLE_MARKETPLACE ="marketplace";
    public static final String COLUMN_MARKETPLACE_ID="id";
    private static final String COLUMN_MARKETPLACE_NAME="marketplace_name";

    private static final String QUERY_MARKETPLACE = "SELECT DISTINCT " + TABLE_MARKETPLACE + "." + COLUMN_MARKETPLACE_NAME + " FROM " + TABLE_MARKETPLACE +
            " INNER JOIN " + TABLE_LISTING + " ON " + TABLE_MARKETPLACE + "." + COLUMN_MARKETPLACE_ID +
            " = " + TABLE_LISTING + "." + COLUMN_LISTING_MARKETPLACE +
            " WHERE " + TABLE_LISTING + "." + COLUMN_LISTING_MARKETPLACE + " = ?";
    private static final String SQL_END_FOR_MONTHLY_DATA=" AND strftime('%m', upload_time) = ? AND strftime('%Y', upload_time) = ?";



    private PreparedStatement queryMarketplace;

    private Connection conn;

    public boolean open(){


        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            queryMarketplace=conn.prepareStatement(QUERY_MARKETPLACE);
            return true;
        }catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if(queryMarketplace != null){
            queryMarketplace.close();
        }
            if (conn != null) {
            conn.close();
        }

        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }


    public static String getListingUrl() {
        return listingUrl;
    }

    public static String getLocationUrl() {
        return locationUrl;
    }

    public static String getListingStatusUrl() {
        return listingStatusUrl;
    }

    public static String getMarketplaceUrl() {
        return marketplaceUrl;
    }

    public void createTables() {
        try {conn = DriverManager.getConnection(CONNECTION_STRING);
            Statement statement = conn.createStatement();
            statement.execute("DROP TABLE IF EXISTS " + TABLE_LISTING_STATUS);
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_LISTING_STATUS +
                    " (" + COLUMN_STAUS_ID + " integer, "
                    + COLUMN_STATUS_NAME + " text)");

            statement.execute("DROP TABLE IF EXISTS " + TABLE_LISTING);
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_LISTING +
                    " (" + COLUMN_LISTING_ID + " text, "
                    + COLUMN_LISTING_TITLE +  " text, "
                    + COLUMN_LISTING_DESCRIPTION +  " text, "
                            + COLUMN_LISTING_PRICE + " float, "
                            + COLUMN_LISTING_LOCATIONID + " text, "
                            + COLUMN_LISTING_CURRENCY + " text, "
                            + COLUMN_LISTING_QUANTITY + " integer, "
                            + COLUMN_LISTING_STATUS + " integer, "
                            + COLUMN_LISTING_MARKETPLACE + " integer, "
                            + COLUMN_LISTING_UPLOADTIME + " text, "
                            + COLUMN_LISTING_EMAIL + " text)");

            statement.execute("DROP TABLE IF EXISTS " + TABLE_LOCATION);
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_LOCATION +
                    " (" + COLUMN_LOCATION_ID + " text, "
                    + COLUMN_LOCATION_MANAGER +  " text, "
                    + COLUMN_LOCATION_PHONE +  " text, "
                    + COLUMN_LOCATION_1ADDRESS + " text, "
                    + COLUMN_LOCATION_2ADDRESS + " text, "
                    + COLUMN_LOCATION_COUNTRY + " text, "
                    + COLUMN_LOCATION_TOWN+ " text, "
                    + COLUMN_LOCATION_POSTALCODE + " text)");

            statement.execute("DROP TABLE IF EXISTS " + TABLE_MARKETPLACE);
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_MARKETPLACE +
                    " (" + COLUMN_MARKETPLACE_ID + " integer, "
                    + COLUMN_MARKETPLACE_NAME + " text)");

            statement.close();

        } catch (
                SQLException e) {
            System.out.println("Something went wrong:" + e.getMessage());
        }
    }

    public  void insertStatus(ListingStatus status){
        try{

        Statement statement = conn.createStatement();
        statement.execute("INSERT INTO "+ TABLE_LISTING_STATUS +
                " ("+ COLUMN_STAUS_ID +", "
                + COLUMN_STATUS_NAME +") "
                +"VALUES("+status.getId()+", \""+status.getStatusName()+"\")");
        statement.close();
        }catch (SQLException e){
        System.out.println(e.getMessage());
    }
}

    public  void insertListing(Listing listing){
        try {
            Statement statement = conn.createStatement();
            statement.execute("INSERT INTO " + TABLE_LISTING +
                    " (" + COLUMN_LISTING_ID + ", "
                    + COLUMN_LISTING_TITLE + ", " + COLUMN_LISTING_DESCRIPTION + ", " +
                    COLUMN_LISTING_LOCATIONID + ", " + COLUMN_LISTING_PRICE + ", " + COLUMN_LISTING_CURRENCY + ", "
                    + COLUMN_LISTING_QUANTITY + ", " + COLUMN_LISTING_STATUS + ", " + COLUMN_LISTING_MARKETPLACE + ", "
                    + COLUMN_LISTING_UPLOADTIME + ", " + COLUMN_LISTING_EMAIL + ") "
                    + "VALUES(\""+ listing.getId() + "\", \"" + listing.getTitle() +"\", \"" + listing.getDescription() + "\", \""
                    + listing.getInventoryItemLocationId() + "\", " + listing.getListingPrice() + ", \"" + listing.getCurrency() + "\", "
                    + listing.getQuantity() + ", " + listing.getListingStatus() + ", " + listing.getMarketplace() + ", \""
                    + listing.getUploadTime() + "\", \"" + listing.getOwnerEmailAddress() + "\")");
            statement.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public  void insertLocation(Location location){
        try{

        Statement statement = conn.createStatement();
        statement.execute("INSERT INTO "+ TABLE_LOCATION +
                " ("+ COLUMN_LOCATION_ID +", " + COLUMN_LOCATION_MANAGER +", "+COLUMN_LOCATION_PHONE +", "
                +COLUMN_LOCATION_1ADDRESS +", "+COLUMN_LOCATION_2ADDRESS +", "+COLUMN_LOCATION_COUNTRY +", "
                        +COLUMN_LOCATION_TOWN +", "+COLUMN_LOCATION_POSTALCODE + ") "
                + "VALUES(\""+ location.getId() + "\", \"" + location.getManagerName() +"\", \"" + location.getPhone() + "\", \""
                + location.getAddressPrimary() + "\", \"" + location.getAddressSecondary() + "\", \"" + location.getCountry() + "\", \""
                + location.getTown() + "\", \"" + location.getPostalCode()+ "\")");
        statement.close();
        }catch (SQLException e){
        System.out.println(e.getMessage());
    }
}

    public  void insertMarketplace(Marketpalce marketpalce) {
        try {
            Statement statement = conn.createStatement();
            statement.execute("INSERT INTO " + TABLE_MARKETPLACE +
                    " (" + COLUMN_MARKETPLACE_ID + ", "
                    + COLUMN_MARKETPLACE_NAME + ") "
                    + "VALUES(" + marketpalce.getId() + ", \"" + marketpalce.getMarketplaceName() + "\")");
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public  List<String> queryID(String column, String table){

        StringBuilder sb = new StringBuilder("SELECT ");
        sb.append(column);
        sb.append(" FROM ");
        sb.append(table);
        try {conn = DriverManager.getConnection(CONNECTION_STRING);
            Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sb.toString());
            List<String> ids = new ArrayList<>();
            while (results.next()) {
                ids.add(results.getString(1).toString());
            }
            results.close();
            statement.close();
            return ids;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    private String getCount(String table) {
        String sql = "SELECT COUNT(*) AS count FROM " + table;
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sql)) {
            int count = results.getInt("count");
            return Integer.toString(count);
        } catch (SQLException e) {
            System.out.println("Query failed :" + e.getMessage());
            return null;
        }
    }

    private String getCountForMarketpalces(String marketName) {
        String sql = "SELECT COUNT("+TABLE_MARKETPLACE+"."+COLUMN_MARKETPLACE_NAME+") FROM "
                +TABLE_MARKETPLACE+" INNER JOIN "+TABLE_LISTING+" ON "+TABLE_MARKETPLACE+"."+COLUMN_MARKETPLACE_ID
                +" = "+TABLE_LISTING+"."+COLUMN_LISTING_MARKETPLACE+" WHERE "+TABLE_MARKETPLACE+"."+COLUMN_MARKETPLACE_NAME
                +" = \""+marketName+"\"";
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sql)) {
            int count = results.getInt(1);
            return Integer.toString(count);
        } catch (SQLException e) {
            System.out.println("Query failed :" + e.getMessage());
            return null;
        }
    }

    private String getCountForMarketpalces(String marketName, String month, String year){
        String sql = "SELECT COUNT("+TABLE_MARKETPLACE+"."+COLUMN_MARKETPLACE_NAME+") FROM "
                +TABLE_MARKETPLACE+" INNER JOIN "+TABLE_LISTING+" ON "+TABLE_MARKETPLACE+"."+COLUMN_MARKETPLACE_ID
                +" = "+TABLE_LISTING+"."+COLUMN_LISTING_MARKETPLACE+" WHERE "+TABLE_MARKETPLACE+"."+COLUMN_MARKETPLACE_NAME
                +" = \""+marketName+"\""+SQL_END_FOR_MONTHLY_DATA;
        try (PreparedStatement getMonthyCount=conn.prepareStatement(sql)){
            getMonthyCount.setString(1,month);
            getMonthyCount.setString(2,year);
            ResultSet results = getMonthyCount.executeQuery();
            int count = results.getInt(1);
            return Integer.toString(count);
        } catch (SQLException e) {
            System.out.println("Query failed :" + e.getMessage());
            return null;
        }
    }


    private String getPriceInformation(String marketName, String infoType) {

        String sql = "SELECT " +infoType+"("+TABLE_LISTING+"."+COLUMN_LISTING_PRICE+") FROM "
                +TABLE_LISTING+" INNER JOIN "+TABLE_MARKETPLACE+" ON "+TABLE_MARKETPLACE+"."+COLUMN_MARKETPLACE_ID
                +" = "+TABLE_LISTING+"."+COLUMN_LISTING_MARKETPLACE+" WHERE "+TABLE_MARKETPLACE+"."+COLUMN_MARKETPLACE_NAME
                +" = \""+marketName+"\"";
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sql)) {
            float count = results.getFloat(1);
            return Float.toString(count);

        } catch (SQLException e) {
            System.out.println("Query failed :" + e.getMessage());
            return null;
        }
    }
    private String getPriceInformation(String marketName, String infoType, String month, String year) {

        String sql = "SELECT " +infoType+"("+TABLE_LISTING+"."+COLUMN_LISTING_PRICE+") FROM "
                +TABLE_LISTING+" INNER JOIN "+TABLE_MARKETPLACE+" ON "+TABLE_MARKETPLACE+"."+COLUMN_MARKETPLACE_ID
                +" = "+TABLE_LISTING+"."+COLUMN_LISTING_MARKETPLACE+" WHERE "+TABLE_MARKETPLACE+"."+COLUMN_MARKETPLACE_NAME
                +" = \""+marketName+"\""+SQL_END_FOR_MONTHLY_DATA;
        try (PreparedStatement getMonthyPrice=conn.prepareStatement(sql)){
            getMonthyPrice.setString(1,month);
            getMonthyPrice.setString(2,year);
             ResultSet results = getMonthyPrice.executeQuery();
            float count = results.getFloat(1);
            return Float.toString(count);
        } catch (SQLException e) {
            System.out.println("Query failed :" + e.getMessage());
            return null;
        }
    }

    private String getBestLister() {
        String sql = "SELECT "+TABLE_LISTING+"."+COLUMN_LISTING_EMAIL+", " +"COUNT("+TABLE_LISTING+"."+COLUMN_LISTING_EMAIL+
                ") AS \"value_occurrence\" FROM " +TABLE_LISTING+" GROUP BY "+TABLE_LISTING+"."+COLUMN_LISTING_EMAIL+
                " ORDER BY \"value_occurrence\" DESC LIMIT 1";
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sql)) {
            String email = results.getString(1);
            return email;
        } catch (SQLException e) {
            System.out.println("Query failed :" + e.getMessage());
            return null;
        }
    }

    private String getBestLister( String month, String year) {
        String sql = "SELECT "+TABLE_LISTING+"."+COLUMN_LISTING_EMAIL+", " +"COUNT("+TABLE_LISTING+"."+COLUMN_LISTING_EMAIL+
                ") AS \"value_occurrence\" FROM " +TABLE_LISTING+" WHERE strftime('%m', upload_time) = ? AND strftime('%Y', upload_time) = ?"+" GROUP BY "+TABLE_LISTING+"."+COLUMN_LISTING_EMAIL+
                " ORDER BY \"value_occurrence\" DESC LIMIT 1";
        try (PreparedStatement getMonthyBestLister=conn.prepareStatement(sql)){
            getMonthyBestLister.setString(1,month);
            getMonthyBestLister.setString(2,year);
             ResultSet results = getMonthyBestLister.executeQuery();
            String email = results.getString(1);
            return email;
        } catch (SQLException e) {
            System.out.println("Query failed :" + e.getMessage());
            return null;
        }
    }
    private JsonObject totalReport(){
        JsonObject totalReportObj= new JsonObject();
        totalReportObj.put("Total listing count", getCount(TABLE_LISTING));
        totalReportObj.put("Total eBay listing count",getCountForMarketpalces("EBAY"));
        totalReportObj.put("Total eBay listing price",getPriceInformation("EBAY", "SUM"));
        totalReportObj.put("Average eBay listing price",getPriceInformation("EBAY", "AVG"));
        totalReportObj.put("Total Amazon listing count",getCountForMarketpalces("AMAZON"));
        totalReportObj.put("Total Amazon listing price",getPriceInformation("AMAZON", "SUM"));
        totalReportObj.put("Average Amazon listing price",getPriceInformation("AMAZON", "AVG"));
        totalReportObj.put("Best lister email address",getBestLister());
        return totalReportObj;
    }

    private JsonObject monthlyReport(String month, String year){
        JsonObject monthlyReportObj= new JsonObject();
        monthlyReportObj.put("Report of month",year+"-"+month);
        monthlyReportObj.put("Total eBay listing count",getCountForMarketpalces("EBAY", month, year));
        monthlyReportObj.put("Total eBay listing price",getPriceInformation("EBAY", "SUM", month, year));
        monthlyReportObj.put("Average eBay listing price",getPriceInformation("EBAY", "AVG", month, year));
        monthlyReportObj.put("Total Amazon listing count",getCountForMarketpalces("AMAZON", month, year));
        monthlyReportObj.put("Total Amazon listing price",getPriceInformation("AMAZON", "SUM", month, year));
        monthlyReportObj.put("Average Amazon listing price",getPriceInformation("AMAZON", "AVG", month, year));
        monthlyReportObj.put("Best lister email address",getBestLister(month, year));

        return monthlyReportObj;
    }

    public void report(){
        JsonArray report = new JsonArray();
        report.add(totalReport());
        for(int i=7; i<=8; i++){
            String year="201"+Integer.toString(i);
            for(int x=1 ; x<=12; x++){
                if(x<=9) {
                    String month = "0" + Integer.toString(x);
                    report.add( monthlyReport(month ,year));
                }else{
                    String month = Integer.toString(x);
                    report.add( monthlyReport(month ,year));
                }
            }

        }
        try (FileWriter file = new FileWriter("report.txt")) {
            file.write(report.toString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + report);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

