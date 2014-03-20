package com.andmobility;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chat extends ListActivity implements View.OnClickListener {

  private static final String LOGTAG = "Chat";

  EditText editText;
  Button button;
  Dialog progressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_chat);

    editText = findById(R.id.editText);
    button = findById(R.id.button);
    button.setOnClickListener(this);

    fetch();
  }

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

  void fetch() {
    new FetchMessagesTask().execute();
  }

  <T extends View> T findById(int id) {
    return (T) findViewById(id);
  }

  class FetchMessagesTask extends AsyncTask<Void, Void, List<String>> {

    @Override
    protected void onPreExecute() {
      Chat.this.progressDialog = ProgressDialog.show(Chat.this, getString(R.string.loading_title),
          getString(R.string.loading_text), true);
      super.onPreExecute();
    }

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

    @Override protected void onPostExecute(List<String> messages) {
      super.onPostExecute(messages);
      ArrayAdapter<String> adapter =
          new ArrayAdapter<String>(Chat.this, android.R.layout.simple_list_item_1);
      adapter.addAll(messages);
      setListAdapter(adapter);
      Chat.this.progressDialog.dismiss();
    }
  }
}
