package com.vsahin.moneycim.View;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.vsahin.moneycim.R;
import com.vsahin.moneycim.View.AddAndEditSpending.AddAndEditSpendingActivity;
import com.vsahin.moneycim.View.Base.BaseActivity;
import com.vsahin.moneycim.View.SpendingList.SpendingListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.container)
    CoordinatorLayout container;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        showRootFragment(SpendingListFragment.newInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.share:
                shareApp();
                return true;
            case R.id.about:
                showAboutDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareApp(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
        String sAux = "Hey look at this application\n" + getString(R.string.app_link) +"\n";
        i.putExtra(Intent.EXTRA_TEXT, sAux);
        startActivity(Intent.createChooser(i, "Share"));
    }

    private void showAboutDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_main_dialog_about);

        ImageView playStoreImage = dialog.findViewById(R.id.play_store_icon);
        playStoreImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "market://search?q=pub:" + getString(R.string.app_play_store_id);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });

        dialog.show();
    }

    @OnClick(R.id.fab)
    public void openAddAndEditSpendingActivity(){
        startActivity(AddAndEditSpendingActivity.newIntent(this));
    }
}
