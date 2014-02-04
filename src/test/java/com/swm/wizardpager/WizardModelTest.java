package com.swm.wizardpager;
 
 /*
  * Copyright SWM, LLC
  * Developer: tom
  * Date: 2/1/14
  * Time: 3:34 AM
  */

import android.util.Log;
import com.google.gson.Gson;
import com.swm.wizardpager.wizard.model.AbstractWizardModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowLog;

import static com.swm.wizardpager.wizard.util.WizUtil.createGson;
import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class WizardModelTest {

    AbstractWizardModel wizModel;

    Gson GSON = createGson();

    @Before
    public void setUp() throws Exception {
        wizModel = new SandwichWizardModel(Robolectric.application);
        ShadowLog.stream = System.out;
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertThat(wizModel).isNotNull();
    }

    @Test
    public void shouldSerialize() throws Exception {
        String wizJson = GSON.toJson(wizModel);
        Log.i("WizJson", wizJson);
        AbstractWizardModel wizModelFromJson = GSON.fromJson(wizJson, SandwichWizardModel.class);

        assertThat(wizModelFromJson).isEqualsToByComparingFields(wizModel);
        assertThat(wizModelFromJson).isNotSameAs(wizModel);
    }

    /*@Test @Ignore
    public void shouldParcel() throws Exception {
        Parcel p = Parcel.obtain();
        wizModel.writeToParcel(p, 0);
        p.setDataPosition(0);

        AbstractWizardModel wizFromP = AbstractWizardModel.CREATOR.createFromParcel(p);

        assertThat(wizFromP).isEqualTo(wizModel);
        assertThat(wizFromP).isNotSameAs(wizModel);
    }*/

}
