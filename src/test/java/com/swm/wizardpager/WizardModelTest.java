package com.swm.wizardpager;
 
 /*
  * Copyright SWM, LLC
  * Developer: tom
  * Date: 2/1/14
  * Time: 3:34 AM
  */

import android.util.Log;
import com.swm.wizardpager.wizard.model.AbstractWizardModel;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowLog;

import static com.swm.wizardpager.wizard.util.WizardUtil.createGson;
import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class WizardModelTest {

    AbstractWizardModel wizModel;

    Gson gson;

    @Before
    public void setUp() throws Exception {
        wizModel = new SandwichWizardModel(Robolectric.application);
        gson = createGson();
        ShadowLog.stream = System.out;
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertThat(wizModel).isNotNull();
    }

    @Test
    public void shouldSerialize() throws Exception {
        String wizJson = gson.toJson(wizModel);
        Log.i("WizJson", wizJson);
        AbstractWizardModel wizModelFromJson = gson.fromJson(wizJson, SandwichWizardModel.class);

        assertThat(wizModelFromJson).isEqualsToByComparingFields(wizModel);
        assertThat(wizModelFromJson).isNotSameAs(wizModel);
    }

    @Test @Ignore
    public void shouldParcel() throws Exception {
    }

}
