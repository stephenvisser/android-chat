package com.andmobility;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;
import com.parse.PushService;

/**
 * Created by visser on 2014-03-15.
 */
public class App extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Parse.initialize(this, "87V5OF80WEIyxVCEh9kTPY9Gnq9BP0peg9f3fSQO",
        "KQvkkLhWwvZxk5H2MUoPLbftPpZP13VYS1bZ2Zkj");
    PushService.setDefaultPushCallback(this, Chat.class);

    ParseUser.enableAutomaticUser();

    ParseACL defaultACL = new ParseACL();
    defaultACL.setPublicReadAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);
  }
}
