package in.co.rays.project_4.util;

import java.util.ResourceBundle;

/**
 * Read the property values from application properties file using Resource
 * Bundle
 *
 * @author Danish Khan
 * @version 1.0
 * @Copyright (c) SunilOS
 *
 */

public class PropertyReader {

    private static ResourceBundle rb = ResourceBundle
            .getBundle("in.co.rays.project_4.bundle.System");

    /**
     * Return value of key
     *
     * @param key
     * @return
     */

    public static String getValue(String key) {
    	System.out.println("PropertyReader.getVlue(key) line 28 key:" +key);
        String val = null;

        try {
            val = rb.getString(key);
            System.out.println("PropertyReader.getVlue(line 33) key:" +key+ "value: " +val);
        } catch (Exception e) {
            val = key;
        }

        return val;

    }

    /**
     * Gets String after placing param values
     *
     * @param key
     * @param param
     * @return String
     */
    public static String getValue(String key, String param) {
        System.out.println("PropertyReader.getVlue(key, param) line 50 key:" +key+ "param: " +param);
        String msg = getValue(key); //28 line wali getValue chalegi aur error.require ki property reader se value aa jayegi
        msg = msg.replace("{0}", param);
        return msg;
    }

    /**
     * Gets String after placing params values
     *
     * @param key
     * @param params
     * @return
     */
    public static String getValue(String key, String[] params) {
        System.out.println("PropertyReader.getVlue(key, params) line 64 key:" +key+ "params: " +params);

    	String msg = getValue(key);
        for (int i = 0; i < params.length; i++) {
            msg = msg.replace("{" + i + "}", params[i]);
        }
        return msg;
    }

    /**
     * Test method
     *
     * @param args
     */

    public static void main(String[] args) {
    	System.out.println("PropertyReader.MainMethod(String[]) line 80");
        String[] params = { "Roll No" };
        System.out.println(PropertyReader.getValue("error.require", params));
    }

}
