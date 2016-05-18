package com.jennyeckstein.udacitycoursepicker.data;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.test.AndroidTestCase;

/**
 * Created by Jenny on 5/18/2016.
 */
public class TestProvider extends AndroidTestCase {

    /*
    Test if ContentProvider is registered with Android Manifest
     */
    public void testProviderRegistry(){
        PackageManager packageManager = mContext.getPackageManager();
        ComponentName componentName =
                new ComponentName(mContext.getPackageName(), CourseProvider.class.getName());

        try{
            ProviderInfo providerInfo = packageManager.getProviderInfo(componentName, 0);
            assertEquals("Error: MovieProvider registered with authority: " + providerInfo.authority +
                    " instead of authority: " + CourseContract.CONTENT_AUTHORITY, providerInfo.authority, CourseContract.CONTENT_AUTHORITY);
        }catch(PackageManager.NameNotFoundException e){
            assertTrue("Error: CourseProvider not registered at " + mContext.getPackageName(), false);
        }
    }
}
