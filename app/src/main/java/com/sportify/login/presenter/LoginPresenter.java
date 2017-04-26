package com.sportify.login.presenter;

import com.facebook.AccessToken;

/**
 * Created by peradrianbergman on 2017-04-18.
 */

public interface LoginPresenter {
    void loginUser();
    void requestFacebookLong(AccessToken token);
}
