package gsihome.reyst.y3t.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.TimeUtils;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import gsihome.reyst.y3t.R;
import gsihome.reyst.y3t.domain.IssueEntity;
import gsihome.reyst.y3t.utils.ServiceApiHolder;

public class IssueListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_ITEM = 1;
    private static final int VIEW_PROG = 0;

    private Context mContext;
    private List<IssueEntity> mModel;
    private OnItemClickListener mOnItemClickListener;
    private DateFormat mFormatter;
    private String mDays;
    private String mEmptyString;
    public IssueListAdapter(Context context, OnItemClickListener listener) {

        mContext = context;

        mDays = context.getString(R.string.days);
        mEmptyString = context.getString(R.string.emptyString);

        mOnItemClickListener = listener;
        mFormatter = ServiceApiHolder.getFormatter(context);
        mModel = new ArrayList<>();
    }

    public List<IssueEntity> getModel() {
        return mModel;
    }

    public void setModel(List<IssueEntity> model) {
        mModel = model;
    }

    public void addAll(Collection<? extends IssueEntity> collection) {
        mModel.addAll(collection);
    }

    public int size() {
        return mModel.size();
    }

    public void clear() {
        mModel.clear();
    }

    public void add(IssueEntity object) {
        mModel.add(object);
    }

    public void remove(int location) {
        mModel.remove(location);
    }

    public boolean contains(IssueEntity entity) {
        return mModel.contains(entity);
    }

    @Override
    public int getItemViewType(int position) {
        return mModel.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;
        RecyclerView.ViewHolder result = null;

        switch (viewType) {
            case VIEW_ITEM:
                v = LayoutInflater.from(mContext).inflate(R.layout.list_item_card, parent, false);
                result = new IssueViewHolder(v);
                break;
            case VIEW_PROG:
                v = LayoutInflater.from(mContext).inflate(R.layout.progress_item, parent, false);
                result = new ProgressViewHolder(v);
                break;
        }

        return result;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof IssueViewHolder) {

            IssueViewHolder holder = (IssueViewHolder) viewHolder;

            IssueEntity issueEntity = mModel.get(position);

            holder.mTvCategoryTitle.setText(issueEntity.getCategory().getName());
            holder.mTvTaskDesc.setText(issueEntity.getBody());
            holder.mTvLikesAmount.setText(String.valueOf(issueEntity.getLikesCounter()));
            holder.mTvDateCreated.setText(mFormatter.format(issueEntity.getCreatedDate()));

            Picasso.with(mContext)
                    .load(R.drawable.ic_doc)
                    .into(holder.mIvCategoryIcon);


            Date cd = issueEntity.getCreatedDate();

            int daysAmount = -1;
            if (cd != null) {
                daysAmount = (int) TimeUnit.DAYS
                        .convert(System.currentTimeMillis() - cd.getTime(), TimeUnit.MILLISECONDS);
            }

            holder.mTvDaysAmount.setText((daysAmount > -1) ? String.valueOf(daysAmount)
                    .concat(" ").concat(mDays) : mEmptyString);

        } else {
            ((ProgressViewHolder) viewHolder).mProgressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return mModel.size();
    }

    public interface OnItemClickListener {
        void onItemClick(IssueEntity entity);
    }

    public class IssueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.category_title)
        TextView mTvCategoryTitle;
        @BindView(R.id.task_desc)
        TextView mTvTaskDesc;
        @BindView(R.id.amount_days)
        TextView mTvDaysAmount;
        @BindView(R.id.date_created)
        TextView mTvDateCreated;
        @BindView(R.id.likes_amount)
        TextView mTvLikesAmount;
        @BindView(R.id.category_icon)
        ImageView mIvCategoryIcon;

        public IssueViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(mModel.get(position));
            }
        }
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.progress_bar)
        ProgressBar mProgressBar;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}