package com.company.Util;

import com.company.model.Datasource;
import com.company.model.Listing;
import com.company.model.ListingStatus;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Validator {
    Datasource datasource = new Datasource();
    public List<String> locationIds;
    public List<String> statusIds;
    public List<String> marketplaceIds;

    public Validator() {
        this.locationIds = datasource.queryID(Datasource.COLUMN_LOCATION_ID, Datasource.TABLE_LOCATION);
        this.statusIds = datasource.queryID(Datasource.COLUMN_STAUS_ID, Datasource.TABLE_LISTING_STATUS);
        this.marketplaceIds = datasource.queryID(Datasource.COLUMN_MARKETPLACE_ID, Datasource.TABLE_MARKETPLACE);

    }

    public boolean validator(Listing listing) {
        ArrayList<String> errors = new ArrayList<String>();

        errors.add(isUUID(listing.getId(), "id"));
        errors.add(notNull(listing.getTitle(), "title"));
        errors.add(notNull(listing.getDescription(), "description"));
        errors.add(isReference(listing.getInventoryItemLocationId(), "location_id", locationIds));
        errors.add(greaterthanZero(listing.getListingPrice(), "listing_price"));
        errors.add(decimals(listing.getListingPrice(), "listing_price"));
        errors.add(length(listing.getCurrency(), "currency"));
        errors.add(greaterthanZero(listing.getQuantity(), "quantity"));
        errors.add(isReference(listing.getListingStatusToString(), "listing_status", statusIds));
        errors.add(isReference(listing.getMarketplaceToString(), "marketplace", marketplaceIds));
        errors.add(emailValidator(listing.getOwnerEmailAddress(), "owner_email_address"));

        // locationID


        boolean result = true;
        // save to csv
        for (int i = 0; i < errors.size(); i++) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("test.csv", true))) {
                if (errors.get(i) != "") {
                    result = false;
                    StringBuilder sb = new StringBuilder(listing.getId() + ";" + listing.getMarketplace() + ";");
                    sb.append(errors.get(i) + "\n");
                    writer.write(sb.toString());

                    writer.flush();
                    writer.close();
                }
            } catch (Exception e) {
                System.out.println("Can' save to csv " + e.getMessage());

            }
        }

        return result;
    }

    private String isUUID(String string, String columnName) {
        if (string == null) {
            return columnName;
        } else {
            Pattern p = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");
            if (p.matcher(string).matches() == true) {
                return "";
            } else {
                return columnName;
            }
        }
    }

    private String notNull(String string, String columnName) {
        if (string == null) {
            return columnName;
        } else {
            return "";
        }
    }

    private String isReference(String string, String columnName, List<String> idList) {
        if (idList.contains(string)) {
            return "";
        } else {
            return columnName;
        }
    }

    private String greaterthanZero(float i, String columnName) {
        if (i > 0) {
            return "";
        } else {
            return columnName;
        }
    }

    private String greaterthanZero(int i, String columnName) {
        if (i > 0) {
            return "";
        } else {
            return columnName;
        }
    }

    private String decimals(float price, String columnName) {
        String text = Float.toString(price);
        int integerPlaces = text.indexOf('.');
        int decimalPlaces = text.length() - integerPlaces - 1;
        if (decimalPlaces == 2) {
            return "";
        } else {
            return columnName;
        }
    }

    private String length(String string, String columnName) {
        if (string != null && string.length() == 3) {
            return "";
        } else {
            return columnName;
        }
    }

    private String emailValidator(String string, String columnName) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if (string != null && string.matches(regex)) {
            return "";
        } else {
            return columnName;
        }
    }
}

