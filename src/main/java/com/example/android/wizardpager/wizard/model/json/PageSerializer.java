package com.example.android.wizardpager.wizard.model.json;
 
 /*
  * Copyright SWM, LLC
  * Developer: tom
  * Date: 2/3/14
  * Time: 11:40 AM
  */

import com.example.android.wizardpager.wizard.model.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PageSerializer implements JsonSerializer<Page>, JsonDeserializer<Page> {

    private static enum PageType {
        text, singlechoice, multichoice, branch;

        public static PageType findByKey(String key) {
            for (PageType type : values()) {
                if (key.equals(type.toString())) {
                    return type;
                }
            }

            throw new UnsupportedOperationException("unknown PageType");
        }
    }

    @Override
    public JsonElement serialize(Page src, Type typeOfSrc, JsonSerializationContext context) {
        PageType pageType;

        if (src instanceof BranchPage) {
            pageType = PageType.branch;
        } else if (src instanceof MultipleFixedChoicePage) {
            pageType = PageType.multichoice;
        } else if (src instanceof SingleFixedChoicePage) {
            pageType = PageType.singlechoice;
        } else if (src instanceof CustomerInfoPage) {
            pageType = PageType.text;
        } else {
            throw new UnsupportedOperationException("unknown PageType");
        }
        src.setPageType(pageType.toString());
        return context.serialize(src);
    }

    @Override
    public Page deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Page page;

        JsonObject jsonObj = json.getAsJsonObject();
        String title = jsonObj.get("mTitle").getAsString();
        boolean required = jsonObj.get("mRequired").getAsBoolean();
        PageType pageType = PageType.findByKey(jsonObj.get("mPageType").getAsString());

        switch (pageType) {

            case branch:
                List<BranchPage.Branch> branches = deserializeBranches(context, jsonObj);
                page = new BranchPage(title, required, branches);
                break;
            case multichoice:
                ArrayList<String> choices = deserializeChoices(context, jsonObj);
                page = new MultipleFixedChoicePage(title, required, choices);
                break;
            case singlechoice:
                choices = deserializeChoices(context, jsonObj);
                page = new SingleFixedChoicePage(title, required, choices);
                break;
            case text:
                page = new CustomerInfoPage(title, required);
                break;

            default:
                throw new UnsupportedOperationException("unknown PageType");
        }

        return page;
    }

    private List<BranchPage.Branch> deserializeBranches(JsonDeserializationContext context, JsonObject jsonObj) {
        return context.deserialize(jsonObj.get("mBranches").getAsJsonArray(),
                        new TypeToken<List<BranchPage.Branch>>(){}.getType());
    }

    private ArrayList<String> deserializeChoices(JsonDeserializationContext context, JsonObject jsonObj) {
        return context.deserialize(jsonObj.get("mChoices").getAsJsonArray(),
                new TypeToken<ArrayList<String>>(){}.getType());
    }

}
