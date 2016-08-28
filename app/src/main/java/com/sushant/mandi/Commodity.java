package com.sushant.mandi;

import java.util.Date;

/**
 * Created by Sushant on 27-08-2016.
 */
public class Commodity {
    String id;
    Long timeStamp;
    String state;
    String district;
    String market;
    String commodity;
    String variety;
    String arrivalDate;
    Long minPrice;
    Long maxPrice;
    Long modalPrice;

    public Commodity(String id, Long timeStamp, String state,
                     String district, String market, String commodity,
                     String variety, String arrivalDate, Long minPrice,
                     Long maxPrice, Long modalPrice) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.state = state;
        this.district = district;
        this.market = market;
        this.commodity = commodity;
        this.variety = variety;
        this.arrivalDate = arrivalDate;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.modalPrice = modalPrice;
    }

    public String getId() {
        return id;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public String getState() {
        return state;
    }

    public String getDistrict() {
        return district;
    }

    public String getMarket() {
        return market;
    }

    public String getCommodity() {
        return commodity;
    }

    public String getVariety() {
        return variety;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public Long getMinPrice() {
        return minPrice;
    }

    public Long getMaxPrice() {
        return maxPrice;
    }

    public Long getModalPrice() {
        return modalPrice;
    }
}