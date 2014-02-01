package com.example.android.wizardpager; 
 
 /*
  * Copyright Hooky, Inc.
  * Developer: tom
  * Date: 2/1/14
  * Time: 3:34 AM
  */

import com.example.android.wizardpager.wizard.model.AbstractWizardModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class WizardModelTest {

    AbstractWizardModel wizModel;

    Gson gson;

    @Before
    public void setUp() throws Exception {
        wizModel = new SandwichWizardModel(Robolectric.application);
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertThat(wizModel).isNotNull();
    }

    @Test
    public void shouldSerializeAndDeserialize() throws Exception {
        String wizJson = gson.toJson(wizModel);
        AbstractWizardModel wizModelFromJson = gson.fromJson(wizJson, SandwichWizardModel.class);

        assertThat(wizModelFromJson).isEqualsToByComparingFields(wizModel);
        assertThat(wizModelFromJson).isNotSameAs(wizModel);
    }

}
