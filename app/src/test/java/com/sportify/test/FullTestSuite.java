package com.sportify.test;

import android.test.suitebuilder.TestSuiteBuilder;

import junit.framework.Test;

/**
 * Created by fluchey on 2017-04-14.
 */

public class FullTestSuite {
    public static Test suite(){
        return new TestSuiteBuilder(FullTestSuite.class).includeAllPackagesUnderHere().build();
    }

    public FullTestSuite(){
        super();
    }
}
