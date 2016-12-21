package com.floatingmuseum.mocloud.ui.comments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Reply;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.floatingmuseum.mocloud.utils.ToastUtil;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/12/20.
 */

public class CommentReplyDialog extends AlertDialog {

    @BindView(R.id.reply_comment)
    EditText replyComment;
    @BindView(R.id.tv_spoiler)
    TextView tvSpoiler;
    @BindView(R.id.send_reply)
    ImageView sendReply;
    @BindView(R.id.is_spoiler)
    CheckBox isSpoiler;

    private SingleCommentActivity activity;
    private SingleCommentPresenter presenter;
    private Comment comment;

    protected CommentReplyDialog(Context context,SingleCommentPresenter presenter,Comment comment) {
        super(context);
        activity = (SingleCommentActivity) context;
        this.presenter = presenter;
        this.comment = comment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_replay);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
//        replyComment
        sendReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String replyContent = replyComment.getText().toString();
                Logger.d("回复内容:"+replyContent+"...isSpoiler"+isSpoiler.isChecked());
                if (!StringUtil.checkReplyContent(replyContent)) {
                    ToastUtil.showToast(R.string.comment_tip1);
                    return;
                }
                Reply reply = new Reply();
                reply.setSpoiler(isSpoiler.isChecked());
                reply.setComment(replyContent);
                presenter.sendReply(comment.getId(),reply);
                // TODO: 2016/12/20 提交回复
            }
        });

        //弹出软键盘
        getWindow().clearFlags( WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
}
