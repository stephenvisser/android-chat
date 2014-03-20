package com.andmobility;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;
import com.parse.PushService;

public class App extends Application {
  private static final String APP_ID = "87V5OF80WEIyxVCEh9kTPY9Gnq9BP0peg9f3fSQO";
  private static final String CLIENT_KEY = "KQvkkLhWwvZxk5H2MUoPLbftPpZP13VYS1bZ2Zkj";

  public static final String CHAT_CHANNEL = "Chats";

  @Override
  public void onCreate() {
    super.onCreate();

    Parse.initialize(this, APP_ID, CLIENT_KEY);

    PushService.subscribe(this, CHAT_CHANNEL, Chat.class);

    ParseUser.enableAutomaticUser();

    ParseACL defaultACL = new ParseACL();
    defaultACL.setPublicReadAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);
  }
}
