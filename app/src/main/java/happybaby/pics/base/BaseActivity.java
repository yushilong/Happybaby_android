package happybaby.pics.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import happybaby.pics.R;
import happybaby.pics.common.AppUtil;


/**
 * Created by yushilong on 2015/5/4.
 */
public abstract class BaseActivity extends ActionBarActivity implements IActivityHelper {

    private Toolbar toolbar;
    private TextView toolbarTitle;
    private ProgressDialog progressDialog;
    private View toolbarClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        progressDialog = AppUtil.getProgressDialog(this, "加载中...", true, true);
        if (IGetContentView() != null)
            setContentView(IGetContentView());
        else if (IGetContentViewResId() != 0) {
            setContentView(IGetContentViewResId());
        }
        View view = findViewById(R.id.toolbar);
        View title = findViewById(R.id.toolbarTitle);
        View close = findViewById(R.id.toolbarClose);
        if (view != null) {
            toolbar = (Toolbar) view;
            toolbar.setNavigationIcon(R.mipmap.ic_action_previous_item);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        if (title != null) {
            toolbarTitle = (TextView) title;
            toolbarTitle.setText(getTitle());
        }
        if (close != null) {
            toolbarClose = close;
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        IFindViews();
        IInitData();
        IRequest();
    }

    /**
     * @param item
     */
    public void onItemClick(MenuItem item) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    public Context IGetContext() {
        return this;
    }

    @Override
    public Activity IGetActivity() {
        return this;
    }

    @Override
    public abstract int IGetContentViewResId();

    @Override
    public View IGetContentView() {
        return null;
    }

    @Override
    public abstract void IFindViews();

    @Override
    public void IFindViews(View view) {

    }

    @Override
    public abstract void IInitData();

    @Override
    public void IRequest() {

    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public TextView getToolbarTitle() {
        return toolbarTitle;
    }

    public View getToolbarClose() {
        return toolbarClose;
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

}