package com.example.android.wizardpager; 
 
 /*
  * Copyright SWM, LLC
  * Developer: tom
  * Date: 2/1/14
  * Time: 3:34 AM
  */

import android.util.Log;
import com.example.android.wizardpager.wizard.model.json.PageInstanceCreator;
import com.example.android.wizardpager.wizard.model.AbstractWizardModel;
import com.example.android.wizardpager.wizard.model.Page;
import com.example.android.wizardpager.wizard.model.json.PageSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowLog;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class WizardModelTest {

    AbstractWizardModel wizModel;

    Gson gson;

    @Before
    public void setUp() throws Exception {
        wizModel = new SandwichWizardModel(Robolectric.application);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Page.class, new PageSerializer());
        gsonBuilder.registerTypeAdapter(Page.class, new PageInstanceCreator());
        gsonBuilder.serializeNulls();
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gson = gsonBuilder.create();

        ShadowLog.stream = System.out;
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertThat(wizModel).isNotNull();
    }

    @Test
    public void shouldSerializeAndDeserialize() throws Exception {
        String wizJson = gson.toJson(wizModel);
        Log.i("HoloYolo", wizJson);
        AbstractWizardModel wizModelFromJson = gson.fromJson(wizJson, SandwichWizardModel.class);

        assertThat(wizModelFromJson).isEqualsToByComparingFields(wizModel);
        assertThat(wizModelFromJson).isNotSameAs(wizModel);
    }

}
