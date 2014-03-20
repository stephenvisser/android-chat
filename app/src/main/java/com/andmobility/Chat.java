package com.andmobility;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.List;

public class Chat extends ListActivity {

  private Dialog progressDialog;

  private void fetch() {
    final List<ParseObject> messages = new ArrayList<ParseObject>();

    AsyncTask task = new AsyncTask<Object, Object, Object>() {

      @Override
      protected Void doInBackground(Object... params) {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Message");
        query.orderByDescending("_created_at");

        try {
          messages.addAll(query.find());
        } catch (ParseException e) {

        }
        return null;
      }

      @Override
      protected void onPreExecute() {
        Chat.this.progressDialog =
            ProgressDialog.show(Chat.this, "", "Loading Messages from Parse", true);
        super.onPreExecute();
      }

      @Override
      protected void onPostExecute(Object result) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Chat.this, R.layout.row);
        for (ParseObject message : messages) {
          adapter.add((String) message.get("text"));
        }
        setListAdapter(adapter);
        Chat.this.progressDialog.dismiss();
      }
    };

    task.execute();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_chat);

    fetch();

    final Button button = (Button) findViewById(R.id.button);
    button.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        final EditText text = (EditText) findViewById(R.id.editText);
        ParseObject obj = new ParseObject("Message");
        obj.put("text", text.getText().toString());
        try {
          obj.save();
          text.getText().clear();
          fetch();
        } catch (ParseException e) {
          Log.i("Chat", "uh oh");
        }
      }
    });
  }
}
