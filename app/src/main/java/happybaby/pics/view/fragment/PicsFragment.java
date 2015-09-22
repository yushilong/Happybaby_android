package happybaby.pics.view.fragment;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.ArrayList;

import happybaby.pics.R;
import happybaby.pics.base.BaseFragment;
import happybaby.pics.common.Constant;
import happybaby.pics.common.StringUtil;
import happybaby.pics.http.HttpCallback;
import happybaby.pics.http.HttpPageUtil;
import happybaby.pics.models.PicsModel;
import happybaby.pics.view.OnRcvScrollListener;
import happybaby.pics.view.adapter.PicsAdapter;

/**
 * Created by yushilong on 2015/9/18.
 */
public class PicsFragment extends BaseFragment {
    RecyclerView recyclerView;
    PicsAdapter adapter;
    HttpPageUtil httpPageUtil;
    int pn = 0;
    private String tag1, tag2;

    @Override
    public int IGetContentViewResId() {
        return R.layout.fragment_pics;
    }

    @Override
    public void IFindViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new OnRcvScrollListener() {
            @Override
            public void onBottom() {
                super.onBottom();
                pn = pn + 1;
                httpPageUtil.setUrl(getApi());
                httpPageUtil.async();
            }
        });
    }

    @Override
    public void IInitData() {
        adapter = new PicsAdapter(IGetActivity(), new ArrayList());
        recyclerView.setAdapter(adapter);
        httpPageUtil = new HttpPageUtil(null, null, null, PicsModel.class, new HttpCallback() {
            @Override
            public void success(Object object) {
                super.success(object);
                ArrayList models = (ArrayList) object;
                if (httpPageUtil.isRefreshState())
                    adapter.getModels().clear();
                adapter.getModels().addAll(models);
                adapter.notifyDataSetChanged();
            }
        });
        //
        tag1 = getArguments().getString("tag1");
        tag2 = getArguments().getString("tag2");
        httpPageUtil.setUrl(getApi());
        httpPageUtil.async();
    }

    private String getApi() {
        if (StringUtil.isEmpty(tag1))
            tag1 = "美女";
        if (StringUtil.isEmpty(tag2))
            tag2 = "黑丝";
        return String.format(Constant.BAIDU_IMAGE, pn, tag1, tag2);
    }
}
