package com.whixard.simpletags.tools;

import com.whixard.simpletags.SimpleTags;

public class ReloadManager {
    public static void reloadConfig() {
        SimpleTags.getPlugin().reloadConfig();
    }

    public static void reloadMessages() {
        SimpleTags.getMessage();
    }
}
