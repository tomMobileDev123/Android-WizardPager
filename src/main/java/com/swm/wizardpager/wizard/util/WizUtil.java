package com.swm.wizardpager.wizard.util;
 
 /*
  * Copyright Hooky, Inc.
  * Developer: tom
  * Date: 2/3/14
  * Time: 11:57 AM
  */

import com.swm.wizardpager.wizard.model.Page;
import com.swm.wizardpager.wizard.model.json.PageInstanceCreator;
import com.swm.wizardpager.wizard.model.json.PageSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WizUtil {

    public static Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Page.class, new PageSerializer());
        gsonBuilder.registerTypeAdapter(Page.class, new PageInstanceCreator());
        gsonBuilder.serializeNulls();
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        return  gsonBuilder.create();
    }

}
