package com.sogeti.utilities;


import org.testng.annotations.DataProvider;
public class DataProviderClass {

    @DataProvider(name = "tc2-data-provider")
    public Object [] [] dpMethod () {
        return new Object [] [] {{"us","90210","Beverly Hills"},{"us","12345","Schenectady"},{"ca","B2R","Waverley"}};
    }
}
