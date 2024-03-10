package com.david.util;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component
public class GsonUtil {

    public String toJSON(Object obj) {
        return new Gson().toJson(obj);

    }
}
