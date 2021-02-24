/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinancePlus;

/**
 *
 * @author lueva
 */
public class City {
    private int cityId;
    private String city;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public City(int cityId, String city) {
        this.cityId = cityId;
        this.city = city;
    }
     
      @Override
    public String toString(){
        return (Integer.toString(cityId) + " " + " [" + city +"]");
    }
}
