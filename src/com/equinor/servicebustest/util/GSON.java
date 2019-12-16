package com.equinor.servicebustest.util;

import com.google.gson.Gson;

public class GSON {
    private final Gson _gson;
    private static GSON instance;

    private GSON() {
        _gson = new Gson();
    }

    public static Gson get() {
        if(instance == null) {
            instance = new GSON();
        }
        return instance._gson;
    }
}
