/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.swm.wizardpager.wizard.model;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Represents a single page in the wizard.
 */
public abstract class Page implements PageTreeNode {
    /**
     * The key into {@link #getData()} used for wizards with simple (single) values.
     */
    public static final String SIMPLE_DATA_KEY = "_";

    protected ModelCallbacks mCallbacks;

    /**
     * Current wizard values/selections.
     */
    protected Bundle mData = new Bundle();
    @Expose protected String mTitle;
    @Expose protected boolean mRequired = false;
    protected String mParentKey;

    /**
     * For JSON serialization / deserialization.
     */
    @Expose @SerializedName("type") protected String mPageType;

    protected Page(String title, boolean required) {
        this.mTitle = title;
        this.mRequired = required;
    }

    protected Page(ModelCallbacks callbacks, String title) {
        mCallbacks = callbacks;
        mTitle = title;
    }

    public ModelCallbacks getCallbacks() {
        return mCallbacks;
    }

    public void setCallbacks(ModelCallbacks mCallbacks) {
        this.mCallbacks = mCallbacks;
    }

    public Bundle getData() {
        return mData;
    }

    public String getTitle() {
        return mTitle;
    }

    public boolean isRequired() {
        return mRequired;
    }

    void setParentKey(String parentKey) {
        mParentKey = parentKey;
    }

    @Override
    public Page findByKey(String key) {
        return getKey().equals(key) ? this : null;
    }

    @Override
    public void flattenCurrentPageSequence(ArrayList<Page> dest) {
        dest.add(this);
    }

    public abstract Fragment createFragment();

    public String getKey() {
        return (mParentKey != null) ? mParentKey + ":" + mTitle : mTitle;
    }

    public abstract void getReviewItems(ArrayList<ReviewItem> dest);

    public boolean isCompleted() {
        return true;
    }

    public void resetData(Bundle data) {
        mData = data;
        notifyDataChanged();
    }

    public void notifyDataChanged() {
        mCallbacks.onPageDataChanged(this);
    }

    public Page setRequired(boolean required) {
        mRequired = required;
        return this;
    }

    public String getPageType() {
        return mPageType;
    }

    public void setPageType(String mPageType) {
        this.mPageType = mPageType;
    }

}