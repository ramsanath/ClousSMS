package com.sanath.smswrapper;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.sanath.smswrapper.sms.SmsFetcher;
import com.sanath.smswrapper.models.Sms;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by sanath on 17/05/18.
 */

@RunWith(AndroidJUnit4.class)
public class SmsFetcherTest {

    @Test
    public void fetchTenSms() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getContext();

//        assertTrue(ContextCompat.checkSelfPermission(appContext,
//                "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED);

        List<Sms> tenSms = new SmsFetcher(appContext)
                .limit(10)
                .get();

        assertEquals(10, tenSms.size());
    }
}