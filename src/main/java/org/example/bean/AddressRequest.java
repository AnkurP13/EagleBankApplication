package org.example.bean;

import javax.validation.constraints.NotBlank;

public class AddressRequest {

    @NotBlank(message = "Line1 is required")
    private String line1;

    private String line2;
    private String line3;

    @NotBlank(message = "Town is required")
    private String town;

    @NotBlank(message = "County is required")
    private String county;

    @NotBlank(message = "Postcode is required")
    private String postcode;

    // Getters and setters

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}

