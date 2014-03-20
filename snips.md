Application
===========
    Parse.initialize(this, "87V5OF80WEIyxVCEh9kTPY9Gnq9BP0peg9f3fSQO",
        "KQvkkLhWwvZxk5H2MUoPLbftPpZP13VYS1bZ2Zkj");

    ParseUser.enableAutomaticUser();

    ParseACL defaultACL = new ParseACL();
    defaultACL.setPublicReadAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);

AndroidManifest
===============
  <uses-permission android:name="android.permission.INTERNET" />

Activity
========
Use IntelliJ refactoring for strings.xml

1. onCreate()

    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_chat);

    editText = findById(R.id.editText);
    button = findById(R.id.button);
    button.setOnClickListener(this);

    fetch();

2. findById

    <T extends View> T findById(int id) {
      return (T) findViewById(id);
    }

3. Global Variables - Use Intellij's generators

4. sendMessage

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.button:
        sendMessage();
        break;
    }
  }

  void sendMessage() {
    Editable text = editText.getText();

    if (TextUtils.isEmpty(text)) {
      Toast.makeText(this, R.string.required_text, Toast.LENGTH_LONG).show();
      editText.setError(getString(R.string.required_text));
      return;
    } else {
      editText.setError(null);
    }

    ParseObject obj = new ParseObject("Message");
    obj.put("text", text.toString());
    try {
      obj.save();
      text.clear();
      fetch();
    } catch (ParseException e) {
      Log.e(LOGTAG, "uh oh", e);
    }
  }

5. fetch()

    void fetch() {
      new FetchMessagesTask().execute();
    }

6. FetchMessagesTask onPreExecute

    Chat.this.progressDialog = ProgressDialog.show(Chat.this, getString(R.string.loading_title),
            getString(R.string.loading_text), true);
    super.onPreExecute();

7. FetchMessagesTask onPostExecute

    @Override protected void onPostExecute(List<String> messages) {
        super.onPostExecute(messages);
        ArrayAdapter<String> adapter =
            new ArrayAdapter<String>(Chat.this, android.R.layout.simple_list_item_1);
        adapter.addAll(messages);
        setListAdapter(adapter);
        Chat.this.progressDialog.dismiss();
    }

8. FetchMessagesTask doInBackground
    @Override protected List<String> doInBackground(Void... params) {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Message");
        query.orderByDescending("_created_at");

        try {
            List<ParseObject> rawMessages = query.find();
            List<String> messages = new ArrayList<String>(rawMessages.size());
            for (ParseObject message : rawMessages) {
                messages.add((String) message.get("text"));
            }
            return messages;
        } catch (ParseException e) {
            Log.e(LOGTAG, "uh oh", e);
            return Collections.emptyList();
        }
    }

9. Point out pitfalls of AsyncTask. Refactor with [SafeAsyncTask](https://github.com/roboguice/roboguice/blob/master/roboguice/src/main/java/roboguice/util/SafeAsyncTask.java).

Push
=====
1. Application

    PushService.setDefaultPushCallback(this, Chat.class);

2. AndroidManifest.xml under manifest

    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.VIBRATE"/>
    <uses-permission android:name="android.permission.WAKE_LOCK"/>
    <uses-permission android:name="android.permission.GET_ACCOUNTS"/>
    <uses-permission android:name="com.google.android.c2dm.permission.RECEIVE"/>
    <permission android:name="com.andmobility.permission.C2D_MESSAGE"
        android:protectionLevel="signature"/>
    <uses-permission android:name="com.andmobility.permission.C2D_MESSAGE"/>

2. AndroidManifest.xml under application

    <service android:name="com.parse.PushService"/>
    <receiver android:name="com.parse.GcmBroadcastReceiver"
        android:permission="com.google.android.c2dm.permission.SEND">
      <intent-filter>
        <action android:name="com.google.android.c2dm.intent.RECEIVE"/>
        <action android:name="com.google.android.c2dm.intent.REGISTRATION"/>
        <category android:name="com.andmobility"/>
      </intent-filter>
    </receiver>
    <!-- If you want to have a broadcast receiver -->
    <receiver android:name=".ChatPushReceiver" android:exported="false">
      <intent-filter>
        <action android:name="com.example.UPDATE_STATUS"/>
      </intent-filter>
    </receiver>

3. Activity launchMode = singleTop

4. Application

    PushService.setDefaultPushCallback(this, Chat.class);
    //PushService.subscribe(this, CHAT_CHANNEL, Chat.class);


5. ChatPushReceiver - If you want to have a broadcast receiver

    private static final String LOGTAG = "ChatPushReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
      try {
        String action = intent.getAction();
        String channel = intent.getExtras().getString("com.parse.Channel");
        JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));

        Log.d(LOGTAG, "got action " + action + " on channel " + channel + " with:");
        Iterator itr = json.keys();
        while (itr.hasNext()) {
          String key = (String) itr.next();
          Log.d(LOGTAG, "..." + key + " => " + json.getString(key));
        }
      } catch (JSONException e) {
        Log.d(LOGTAG, "JSONException", e);
      }
    }
