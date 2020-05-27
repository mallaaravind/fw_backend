package com.aravind.projects.fwbackend;

import org.json.simple.JSONObject;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {
        DataStoreServiceImpl dsi = new DataStoreServiceImpl();
        String key = "Aravind111";
        String value = "Malla";
        Integer ttl = 10;
        dsi.createKeyValue(key, value, ttl);
        Thread.sleep(3000);
        JSONObject jsonObject = dsi.readKeyValue(key);
        Key keyObject = (Key) jsonObject.get(key);
        System.out.println(keyObject.getValue());
    }
}
