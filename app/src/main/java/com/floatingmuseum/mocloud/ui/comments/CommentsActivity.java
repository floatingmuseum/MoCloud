package com.floatingmuseum.mocloud.ui.comments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseActivity;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Ids;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.Reply;
import com.floatingmuseum.mocloud.data.entity.Sharing;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieDetail;
import com.floatingmuseum.mocloud.data.entity.User;
import com.floatingmuseum.mocloud.ui.user.UserActivity;
import com.floatingmuseum.mocloud.utils.KeyboardUtil;
import com.floatingmuseum.mocloud.utils.SPUtil;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.floatingmuseum.mocloud.utils.TimeUtil;
import com.floatingmuseum.mocloud.utils.ToastUtil;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/9/1.
 */
public class CommentsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_comments)
    RecyclerView rv_comments;
    @BindView(R.id.srl_comments)
    SwipeRefreshLayout srl_comments;
    @BindView(R.id.isSpoiler)
    CheckBox isSpoiler;
    @BindView(R.id.comment_box)
    EditText commentBox;
    @BindView(R.id.iv_reply)
    ImageView ivReply;

    public static final String MOVIE_OBJECT = "movie_object";
    public static final String SORT_BY_NEWEST = "newest";
    public static final String SORT_BY_OLDEST = "oldest";
    public static final String SORT_BY_LIKES = "likes";
    public static final String SORT_BY_REPLIES = "replies";

    private CommentsPresenter presenter;
    private TmdbMovieDetail movie;
    private List<Comment> commentsData;
    private CommentsAdapter adapter;
    private LinearLayoutManager manager;
    private String currentSortCondition;
    private MenuItem miSortByNewest;
    private MenuItem miSortByOldest;
    private MenuItem miSortByLikes;
    private MenuItem miSortByReplies;

    @Override
    protected int currentLayoutId() {
        return R.layout.activity_comments;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        movie = getIntent().getParcelableExtra(MOVIE_OBJECT);
        actionBar.setTitle(movie.getTitle());
        presenter = new CommentsPresenter(this);
        currentSortCondition = SPUtil.getString(SPUtil.KEY_COMMENTS_SORT_CONDITION, SORT_BY_NEWEST);
        initView();
    }

    protected void initView() {
        // TODO: 2017/1/9 build sort comments list by newest,oldest,likes,replies
        commentsData = new ArrayList<>();
        adapter = new CommentsAdapter(commentsData);
        rv_comments.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        srl_comments.setOnRefreshListener(this);
        rv_comments.setLayoutManager(manager);
        rv_comments.setAdapter(adapter);

        srl_comments.post(new Runnable() {
            @Override
            public void run() {
                triggerRefreshRequest();
            }
        });

        rv_comments.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                loadMoreComments(manager, adapter, currentSortCondition, movie.getImdb_id(), presenter, srl_comments);
            }
        });

        rv_comments.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Logger.d("条目被点击");
                Intent intent = new Intent(CommentsActivity.this, SingleCommentActivity.class);
                intent.putExtra(SingleCommentActivity.MAIN_COMMENT, commentsData.get(i));
                startActivity(intent);
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                switch (view.getId()) {
                    case R.id.iv_userhead:
                        Logger.d("头像被点击");
                        openUserActivity(CommentsActivity.this, commentsData.get(position).getUser());
                        break;
                    case R.id.tv_comment_likes:
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

    private void sendComment() {
        String replyContent = commentBox.getText().toString();
        Logger.d("回复内容:" + replyContent + "...isSpoiler" + isSpoiler.isChecked());
        if (!StringUtil.checkReplyContent(replyContent)) {
            ToastUtil.showToast(R.string.comment_tip1);
            return;
        }
        Comment comment = new Comment();
        comment.setSpoiler(isSpoiler.isChecked());
        comment.setComment(replyContent);
//        comment.setMovie(movie);
        presenter.sendComment(comment, movie.getImdb_id());
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
        ToastUtil.showToast(R.string.reply_success);
        commentsData.add(0, comment);
        adapter.notifyItemInserted(0);
        KeyboardUtil.hideSoftInput(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.comments, menu);
        miSortByNewest = menu.findItem(R.id.sort_by_newest);
        miSortByOldest = menu.findItem(R.id.sort_by_oldest);
        miSortByLikes = menu.findItem(R.id.sort_by_likes);
        miSortByReplies = menu.findItem(R.id.sort_by_replies);
        Logger.d("onCreateOptionsMenu");
        setCheckedMenuItem();
        return true;
    }

    private void setCheckedMenuItem(){
        miSortByNewest.setChecked(currentSortCondition.equals(SORT_BY_NEWEST)?true:false);
        miSortByOldest.setChecked(currentSortCondition.equals(SORT_BY_OLDEST)?true:false);
        miSortByLikes.setChecked(currentSortCondition.equals(SORT_BY_LIKES)?true:false);
        miSortByReplies.setChecked(currentSortCondition.equals(SORT_BY_REPLIES)?true:false);
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
    }

    private void sortByReplies(final String sortCondition) {
        if (!checkBeforeSort(sortCondition)) {
            return;
        }

        Collections.sort(commentsData, new Comparator<Comment>() {
            @Override
            public int compare(Comment lhs, Comment rhs) {
                return lhs.getReplies() - rhs.getReplies();
            }
        });
        adapter.notifyDataSetChanged();
    }

    private void sortByLikes(String sortCondition) {
        if (!checkBeforeSort(sortCondition)) {
            return;
        }

        Collections.sort(commentsData, new Comparator<Comment>() {
            @Override
            public int compare(Comment lhs, Comment rhs) {
                return lhs.getLikes() - rhs.getLikes();
            }
        });
        adapter.notifyDataSetChanged();
    }

    private void triggerRefreshRequest() {
        srl_comments.setRefreshing(true);
        onRefresh();
    }

    public void stopRefresh() {
        stopRefresh(srl_comments);
    }

    @Override
    public void onRefresh() {
        presenter.start(currentSortCondition, movie.getImdb_id(), shouldClean);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
