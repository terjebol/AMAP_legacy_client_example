package com.equinor.servicebustest.messages;

import com.equinor.servicebustest.util.GSON;
import com.google.gson.reflect.TypeToken;

import java.util.Date;
import java.util.HashMap;

public class MessagePackage<T> {
    public String Id;
    public Date Timestamp;
    private String Content;

    @Override
    public String toString() {
        return "[ \"" + Timestamp + "\", " +
                "Id=\"" + Id + "\", " +
                "Details=\"" + Content + " ]";
    }

    public MessagePackage(T obj) {
        Content = GSON.get().toJson(obj,new TypeToken<HashMap<String, String>>() {}.getType());
    }

    public HashMap<String, Object> getInnerData() {
        return GSON.get().<HashMap<String, Object>>fromJson(Content, HashMap.class);
    }
}
