package com.floatingmuseum.mocloud.ui.comments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseCommentsActivity;
import com.floatingmuseum.mocloud.base.BaseCommentsItemAdapter;
import com.floatingmuseum.mocloud.data.bus.CommentLikeEvent;
import com.floatingmuseum.mocloud.data.bus.EventBusManager;
import com.floatingmuseum.mocloud.data.entity.Colors;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.utils.ColorUtil;
import com.floatingmuseum.mocloud.utils.SPUtil;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.floatingmuseum.mocloud.utils.TimeUtil;
import com.floatingmuseum.mocloud.utils.ToastUtil;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/9/1.
 */
public class CommentsActivity extends BaseCommentsActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_comments)
    RecyclerView rvComments;
    @BindView(R.id.srl_comments)
    SwipeRefreshLayout srlComments;
    @BindView(R.id.isSpoiler)
    CheckBox isSpoiler;
    @BindView(R.id.tv_spoiler)
    TextView tvSpoiler;
    @BindView(R.id.comment_box)
    EditText commentBox;
    @BindView(R.id.iv_reply)
    ImageView ivReply;
    @BindView(R.id.ll_comments)
    LinearLayout llComments;

    public static final String MOVIE_OBJECT = "movie_object";
    public static final String SORT_BY_NEWEST = "newest";
    public static final String SORT_BY_OLDEST = "oldest";
    public static final String SORT_BY_LIKES = "likes";
    public static final String SORT_BY_REPLIES = "replies";

    private CommentsPresenter presenter;
    private Movie movie;
    private Colors mainColors;
    private Colors itemColors;
    private List<Comment> commentsData;
    private BaseCommentsItemAdapter adapter;
    private LinearLayoutManager manager;
    private String currentSortCondition;
    private MenuItem miSortByNewest;
    private MenuItem miSortByOldest;
    private MenuItem miSortByLikes;
    private MenuItem miSortByReplies;
    //存储CommentID和点赞时间，防止多条点赞同时发送时，返回结果错乱
    private HashMap<Long, Integer> likeStateController = new HashMap<>();
    private int openSingleCommentActivityItemPosition;

    @Override
    protected int currentLayoutId() {
        return R.layout.activity_comments;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        movie = getIntent().getParcelableExtra(MOVIE_OBJECT);
        mainColors = getIntent().getParcelableExtra(MAIN_COLORS);
        itemColors = getIntent().getParcelableExtra(ITEM_COLORS);
        actionBar.setTitle(movie.getTitle());
        presenter = new CommentsPresenter(this);
        EventBusManager.register(this);
        currentSortCondition = SPUtil.getString(SPUtil.KEY_COMMENTS_SORT_CONDITION, SORT_BY_NEWEST);
        initView();
        if (mainColors != null && itemColors != null) {
            initColors();
        }
    }

    protected void initView() {
        commentsData = new ArrayList<>();
        adapter = new BaseCommentsItemAdapter(commentsData, itemColors);
        rvComments.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        srlComments.setOnRefreshListener(this);
        rvComments.setLayoutManager(manager);
        rvComments.setAdapter(adapter);

        srlComments.post(new Runnable() {
            @Override
            public void run() {
                triggerRefreshRequest();
            }
        });

        rvComments.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                loadMoreComments(manager, adapter, currentSortCondition, movie.getIds().getSlug(), presenter, srlComments);
            }
        });

        rvComments.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                openSingleCommentActivityItemPosition = position;
                Logger.d("条目被点击");
                Intent intent = new Intent(CommentsActivity.this, SingleCommentActivity.class);
                intent.putExtra(SingleCommentActivity.MAIN_COMMENT, commentsData.get(position));
                intent.putExtra(SingleCommentActivity.MOVIE_TITLE, movie.getTitle());
                if (mainColors != null && itemColors != null) {
                    intent.putExtra(MAIN_COLORS, mainColors);
                    intent.putExtra(ITEM_COLORS, itemColors);
                }
                startActivity(intent);
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                Comment comment = commentsData.get(position);
                switch (view.getId()) {
                    case R.id.iv_userhead:
                        Logger.d("头像被点击");
                        openUserActivity(CommentsActivity.this, comment.getUser());
                        break;
                    case R.id.ll_comment_likes:
                        if (likeStateController.containsKey(comment.getId())) {
                            ToastUtil.showToast("Like state is syncing.");
                            return;
                        }
                        likeStateController.put(comment.getId(), position);
                        syncCommentLike(comment.isLike(), comment);
                        break;
                }
            }
        });

        ivReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment();
            }
        });
    }

    private void initColors() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.setStatusBarColor(ColorUtil.darkerColor(mainColors.getRgb(), 0.4));
        }
        toolbar.setBackgroundColor(ColorUtil.darkerColor(mainColors.getRgb(), 0.2));
        srlComments.setBackgroundColor(mainColors.getRgb());
        llComments.setBackgroundColor(ColorUtil.darkerColor(mainColors.getRgb(), 0.1));
        tvSpoiler.setTextColor(mainColors.getTitleTextColor());
        commentBox.setTextColor(mainColors.getBodyTextColor());
        commentBox.setHintTextColor(mainColors.getTitleTextColor());
    }

    @Override
    protected void syncCommentLike(boolean isLike, Comment comment) {
        presenter.syncCommentLike(isLike, comment, presenter);
    }

    private void sendComment() {
        if (!SPUtil.isLogin()) {
            ToastUtil.showToast(R.string.not_login);
            return;
        }
        String replyContent = commentBox.getText().toString();
        Logger.d("回复内容:" + replyContent + "...isSpoiler" + isSpoiler.isChecked());
        if (!StringUtil.checkReplyContent(replyContent)) {
            ToastUtil.showToast(R.string.comment_tip1);
            return;
        }
        Comment comment = new Comment();
        comment.setSpoiler(isSpoiler.isChecked());
        comment.setComment(replyContent);
//        presenter.sendComment(comment, movie.getImdb_id());
    }

    public void onBaseDataSuccess(List<Comment> comments) {
        if (comments.size() < presenter.getLimit()) {
            alreadyGetAllData = true;
        }

        if (shouldClean) {
            commentsData.clear();
        }
        commentsData.addAll(comments);
        if (shouldClean) {
            manager.scrollToPosition(0);
        }
        adapter.notifyDataSetChanged();
        shouldClean = true;
    }

    public void onSendCommentSuccess(Comment comment) {
        Logger.d("sendComment...onSendCommentSuccess:" + comment.getComment());
        ToastUtil.showToast(R.string.comment_success);
        commentsData.add(0, comment);
        adapter.notifyItemInserted(0);
        resetCommentBox(commentBox, isSpoiler);
//        commentBox.setText("");
//        isSpoiler.setChecked(false);
//        KeyboardUtil.hideSoftInput(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.comments, menu);
        miSortByNewest = menu.findItem(R.id.sort_by_newest);
        miSortByOldest = menu.findItem(R.id.sort_by_oldest);
        miSortByLikes = menu.findItem(R.id.sort_by_likes);
        miSortByReplies = menu.findItem(R.id.sort_by_replies);
        setCheckedMenuItem();
        return true;
    }

    private void setCheckedMenuItem() {
        miSortByNewest.setChecked(currentSortCondition.equals(SORT_BY_NEWEST) ? true : false);
        miSortByOldest.setChecked(currentSortCondition.equals(SORT_BY_OLDEST) ? true : false);
        miSortByLikes.setChecked(currentSortCondition.equals(SORT_BY_LIKES) ? true : false);
        miSortByReplies.setChecked(currentSortCondition.equals(SORT_BY_REPLIES) ? true : false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.sort_by_newest:
                sortByDate(SORT_BY_NEWEST);
                setCheckedMenuItem();
                break;
            case R.id.sort_by_oldest:
                sortByDate(SORT_BY_OLDEST);
                setCheckedMenuItem();
                break;
            case R.id.sort_by_likes:
                sortByLikes(SORT_BY_LIKES);
                setCheckedMenuItem();
                break;
            case R.id.sort_by_replies:
                sortByReplies(SORT_BY_REPLIES);
                setCheckedMenuItem();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * false,表示不需要手动排序，重新按新sort规则请求评论
     */
    private boolean checkBeforeSort(String sortCondition) {
        if (commentsData.size() == 0 || sortCondition.equals(currentSortCondition)) {
            return false;
        }
        currentSortCondition = sortCondition;
        SPUtil.editString(SPUtil.KEY_COMMENTS_SORT_CONDITION, currentSortCondition);
        if (!alreadyGetAllData) {
            //如果未获取全部数据，就清除已获取数据，重新按照新条件获取数据
            //这是为了避免，比如，现有数据是按照newest获取的，然后按oldest排序，之后按oldest获取后续数据，可能和现有数据有重复，时间也可能冲突
            triggerRefreshRequest();
            Logger.d("未加载完全部，重新加载新sort");
            return false;
        }
        return true;
    }

    private void sortByDate(final String sortCondition) {
        if (!checkBeforeSort(sortCondition)) {
            return;
        }

        //如果已获取全部数据，直接排序
        Collections.sort(commentsData, new Comparator<Comment>() {
            @Override
            public int compare(Comment lhs, Comment rhs) {
                Date date1 = TimeUtil.formatStringToDate(TimeUtil.formatGmtTime(lhs.getCreated_at()), TimeUtil.TIME_FORMAT1);
                Date date2 = TimeUtil.formatStringToDate(TimeUtil.formatGmtTime(rhs.getCreated_at()), TimeUtil.TIME_FORMAT1);
                if (!date1.before(date2) && !date1.after(date2)) {
                    return 0;
                }
                if (date1.before(date2)) {
                    return sortCondition.equals(SORT_BY_NEWEST) ? 1 : -1;
                } else {
                    return sortCondition.equals(SORT_BY_NEWEST) ? -1 : 1;
                }
            }
        });
        Logger.d("已加载完全部，直接排序");
        adapter.notifyDataSetChanged();
        manager.scrollToPosition(0);
    }

    private void sortByReplies(final String sortCondition) {
        if (!checkBeforeSort(sortCondition)) {
            return;
        }

        Collections.sort(commentsData, new Comparator<Comment>() {
            @Override
            public int compare(Comment lhs, Comment rhs) {
                return rhs.getReplies() - lhs.getReplies();
            }
        });
        adapter.notifyDataSetChanged();
        manager.scrollToPosition(0);
    }

    private void sortByLikes(String sortCondition) {
        if (!checkBeforeSort(sortCondition)) {
            return;
        }

        Collections.sort(commentsData, new Comparator<Comment>() {
            @Override
            public int compare(Comment lhs, Comment rhs) {
                return rhs.getLikes() - lhs.getLikes();
            }
        });
        adapter.notifyDataSetChanged();
        manager.scrollToPosition(0);
    }

    private void triggerRefreshRequest() {
        srlComments.setRefreshing(true);
        onRefresh();
    }

    public void stopRefresh() {
        stopRefresh(srlComments);
    }

    @Override
    public void onRefresh() {
        presenter.start(currentSortCondition, movie.getIds().getSlug(), shouldClean);
    }

    public void onAddCommentToLikesSuccess(long commentId) {
        Comment comment = commentsData.get(likeStateController.get(commentId));
        if (comment.getId() == commentId) {
            likeStateController.remove(commentId);
            comment.setLike(true);
            int likes = comment.getLikes();
            comment.setLikes(++likes);
            adapter.notifyDataSetChanged();
            EventBus.getDefault().post(new CommentLikeEvent(commentId, true));
        }
    }

    public void onRemoveCommentFromLikesSuccess(long commentId) {
        Comment comment = commentsData.get(likeStateController.get(commentId));
        if (comment.getId() == commentId) {
            likeStateController.remove(commentId);
            comment.setLike(false);
            int likes = comment.getLikes();
            comment.setLikes(--likes);
            adapter.notifyDataSetChanged();
            EventBus.getDefault().post(new CommentLikeEvent(commentId, false));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCommentLikeEvent(CommentLikeEvent event) {
        Comment comment = commentsData.get(openSingleCommentActivityItemPosition);
        if (comment.getId() == event.commentId) {
            comment.setLike(event.isLike);
            comment.setLikes(event.likes);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onError(Exception e) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
