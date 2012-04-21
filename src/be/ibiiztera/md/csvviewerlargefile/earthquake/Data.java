/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ibiiztera.md.csvviewerlargefile.earthquake;

/**
 *
 * @author Manuel DAHMEN
 */
public class Data {
    public class GeoData
    {
        public float longitude;
        public float lattitude;
        public String location;
        public long timeMillis;
        public String locationName;
    }
    public class EarthQuake
    {
        public GeoData geoData;
        public String source;
        public float intensite;
    }
}
