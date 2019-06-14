package com.example.bawei.com.movies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.bean.SystemMessageBean;
import com.example.bawei.com.movies.util.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.bawei.com.movies.util.DateUtils.DATE_PATTERN;

public class SystemMessageAdapter extends RecyclerView.Adapter<SystemMessageAdapter.Holder> {

    private List<SystemMessageBean> mList;
    private Context mContext;

    public SystemMessageAdapter(Context context) {
        this.mContext = context;
        mList = new ArrayList<>();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sysytem_message, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int i) {

        holder.mViewTitle.setText(mList.get(i).title);
        holder.mViewContent.setText(mList.get(i).content);
        try {
            holder.mViewTime.setText(DateUtils.dateTransformer(mList.get(i).pushTime, DATE_PATTERN));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChangeStatus.setChange(view);
            }
        });

    }

    public interface setChangeStatus{
        void setChange(View view);
    }

    private setChangeStatus mChangeStatus;

    public void setChangeStatus(setChangeStatus status){
        this.mChangeStatus = status;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void clear() {
        mList.clear();
    }

    public void addList(List<SystemMessageBean> result) {
        if (result != null) {
            mList.addAll(result);
        }
    }


    public List<SystemMessageBean> getList() {
        return mList;
    }

    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView mViewTitle;
        @BindView(R.id.content)
        TextView mViewContent;
        @BindView(R.id.time)
        TextView mViewTime;

        public Holder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
