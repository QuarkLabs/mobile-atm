package com.ivantha.mobileatm.common;

import com.ivantha.mobileatm.model.User;

public class Session {
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Session.currentUser = currentUser;
    }
}
