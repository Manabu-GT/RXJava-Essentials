package com.packtpub.apps.rxjava_essentials.apps;

import java.util.List;


public class ApplicationsList {

    private static ApplicationsList ourInstance = new ApplicationsList();

    private List<AppInfo> mList;

    private ApplicationsList() {
    }

    public List<AppInfo> getList() {
        return mList;
    }

    public void setList(List<AppInfo> list) {
        mList = list;
    }

    public static ApplicationsList getInstance() {
        return ourInstance;
    }
}
