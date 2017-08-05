package com.example.lenovo.controller.activity.settingActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.myapplication.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.exceptions.HyphenateException;

import java.util.Collections;
import java.util.List;

public class BlacklistActivity extends Activity {
    private BlacklistAdapter adapter;
    private ImageView la_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xffed3f3f);
        }
        setContentView(R.layout.activity_blacklist);

        la_back = findViewById(R.id.la_back);
        la_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back(la_back);

            }
        });
        ListView listView = (ListView) findViewById(R.id.list);

        // get blacklist from local databases
        List<String> blacklist = EMClient.getInstance().contactManager().getBlackListUsernames();

        // show the blacklist
        if (blacklist != null) {
            Collections.sort(blacklist);
            adapter = new BlacklistAdapter(this, blacklist);
            listView.setAdapter(adapter);
        }

        registerForContextMenu(listView);
    }

    private void back(ImageView la_back) {
        finish();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.em_remove_from_blacklist, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.remove) {
            final String tobeRemoveUser = adapter.getItem(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
            // remove user out from blacklist
            removeOutBlacklist(tobeRemoveUser);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * remove user out from blacklist
     *
     * @param tobeRemoveUser
     */
    void removeOutBlacklist(final String tobeRemoveUser) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage(getString(R.string.be_removing));
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        new Thread(new Runnable() {
            public void run() {
                try {
                    EMClient.getInstance().contactManager().removeUserFromBlackList(tobeRemoveUser);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            adapter.remove(tobeRemoveUser);
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), R.string.Removed_from_the_failure, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * adapter
     *
     */
    private class BlacklistAdapter extends ArrayAdapter<String> {

        public BlacklistAdapter(Context context, List<String> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.ease_row_contact, null);
            }
            String username = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.name);
            ImageView avatar = (ImageView) convertView.findViewById(R.id.avatar);

            EaseUserUtils.setUserAvatar(getContext(), username, avatar);
            EaseUserUtils.setUserNick(username, name);

            return convertView;
        }

    }
}
