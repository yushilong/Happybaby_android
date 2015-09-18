package happybaby.pics.base;

import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by yushilong on 2015/5/4.
 */
public abstract class ReplenishAdapter<T> extends BaseAdapter {
    public List<T> list;
    public Activity activity;
    private AbsListView absListView;
    private View anchor;

    public ReplenishAdapter(Activity activity, List<T> mList) {
        super();
        this.list = mList;
        this.activity = activity;
    }

    public ReplenishAdapter(Activity activity, List<T> mList, final AbsListView absListView, final View anchor) {
        this.list = mList;
        this.absListView = absListView;
        this.activity = activity;
        this.anchor = anchor;
        if (anchor != null)
            anchor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (absListView != null) {
                        absListView.setSelection(0);
                        anchor.setVisibility(View.GONE);
                    }
                }
            });
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(activity).inflate(itemLayoutRes(), null);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (anchor != null) {
            if (position > getShowAnchorCount() - 1)
                anchor.setVisibility(View.VISIBLE);
            else
                anchor.setVisibility(View.GONE);
        }
        return getView(position, convertView, parent, holder);
    }

    /**
     * 使用该getView方法替换原来的getView方法，需要子类实现
     *
     * @param position
     * @param convertView
     * @param parent
     * @param holder
     * @return
     */
    public abstract View getView(int position, View convertView, ViewGroup parent, ViewHolder viewHolder);

    /**
     * 各个控件的缓存
     *
     * @author lscm
     */
    public class ViewHolder {
        public SparseArray<View> views = new SparseArray<View>();

        /**
         * 指定resId和类型即可获取到相应的view
         *
         * @param convertView
         * @param resId
         * @return
         */
        public <T extends View> T obtainView(View convertView, int resId) {
            View v = views.get(resId);
            if (null == v) {
                v = convertView.findViewById(resId);
                views.put(resId, v);
            }
            return (T) v;
        }
    }

    /**
     * 改方法需要子类实现，需要返回item布局的resource id
     *
     * @return
     */
    public abstract int itemLayoutRes();

    public int getShowAnchorCount() {
        return 10;//default is 10
    }
}