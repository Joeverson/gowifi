package br.edu.ifpb.dup.imwifi;

/**
 * Created by admin on 14/01/16.
 */
public class Hotpots {
    private String ssid;
    private String password;
    private Double lagitude;
    private Double longitude;

    public Hotpots(String ssid, String password, Double lagitude, Double longitude) {
        this.ssid = ssid;
        this.password = password;
        this.lagitude = lagitude;
        this.longitude = longitude;
    }

    public String getSsid() {
        return ssid;
    }

    public String getPassword() {
        return password;
    }

    public Double getLagitude() {
        return lagitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
