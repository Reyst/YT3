package gsihome.reyst.y3t.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gsihome.reyst.y3t.R;
import gsihome.reyst.y3t.domain.IssueEntity;
import gsihome.reyst.y3t.utils.ServiceApiHolder;

public class IssueListAdapter extends RecyclerView.Adapter<IssueListAdapter.IssueViewHolder> {

    private Context mContext;
    private List<IssueEntity> mModel;

    private OnItemClickListener mOnItemClickListener;
    private DateFormat mFormatter;

    public IssueListAdapter(Context context, OnItemClickListener listener) {
        this.mContext = context;
        mOnItemClickListener = listener;
        mFormatter = ServiceApiHolder.getFormatter(context);
        mModel = new ArrayList<>();
    }

    public IssueListAdapter(Context context, List<IssueEntity> model, OnItemClickListener listener) {
        this(context, listener);
        mModel.addAll(model);
    }

    public boolean addAll(Collection<? extends IssueEntity> collection) {
        return mModel.addAll(collection);
    }

    public int size() {
        return mModel.size();
    }

    public void clear() {
        mModel.clear();
    }

    @Override
    public IssueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_card, parent, false);
        return new IssueViewHolder(v);
    }

    @Override
    public void onBindViewHolder(IssueViewHolder holder, int position) {

        IssueEntity issueEntity = mModel.get(position);

        holder.mTvCategoryTitle.setText(issueEntity.getCategory().getName());
        holder.mTvTaskDesc.setText(issueEntity.getBody());
        holder.mTvLikesAmount.setText(String.valueOf(issueEntity.getLikesCounter()));
        holder.mTvDateCreated.setText(mFormatter.format(issueEntity.getCreatedDate()));

        //holder.mIvCategoryIcon.setImageDrawable(ContextCompat.getDrawable(mContext, issueEntity.getIconId()));

        Picasso.with(mContext)
                .load(issueEntity.getTitle())
                .error(ContextCompat.getDrawable(mContext, R.drawable.building_and_upgrade))
                .into(holder.mIvCategoryIcon);


        String days = mContext.getResources().getString(R.string.days);
        String emptyString = mContext.getResources().getString(R.string.emptyString);

        int daysAmount = issueEntity.getDaysAmount();
        String strDaysAmount = (daysAmount > -1) ? String.valueOf(daysAmount).concat(" ").concat(days) : emptyString;
        holder.mTvDaysAmount.setText(strDaysAmount);

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
}