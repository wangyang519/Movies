package com.example.bawei.com.movies.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.activity.AttentionActivity;
import com.example.bawei.com.movies.activity.LoginActivity;
import com.example.bawei.com.movies.activity.MessageActivity;
import com.example.bawei.com.movies.activity.ShowActivity;
import com.example.bawei.com.movies.bean.UserInfo;
import com.example.bawei.com.movies.dao.DaoMaster;
import com.example.bawei.com.movies.dao.UserInfoDao;
import com.example.bawei.com.movies.util.ActivityCollectorUtil;
import com.example.bawei.com.movies.util.StatusBarUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class Wodefrag extends Fragment {

    private static final String TAG = "Wodefrag";

    @BindView(R.id.heard)
    ImageView mViewHeard;
    @BindView(R.id.logout)
    ImageView mViewOut;
    @BindView(R.id.messiage)
    ImageView mViewMessage;
    @BindView(R.id.attention)
    ImageView mViewAttention;

    private int maxSelectNum = 9;
    private PopupWindow pop;
    private List<LocalMedia> selectList = new ArrayList<>();
    private UserInfo mInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.wode_fra, container, false);
        ButterKnife.bind(this, view);

        StatusBarUtil.setTransparent(getActivity());

        ActivityCollectorUtil.addActivity(getActivity());

        mInfo = new UserInfo();

        mViewHeard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPop();
            }
        });

        mViewOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                UserInfoDao userInfoDao = DaoMaster.newDevSession(getActivity(), UserInfoDao.TABLENAME).getUserInfoDao();
//                userInfoDao.delete(mInfo);
//                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);

//                getActivity().finish();
//                startActivity(new Intent(getActivity(), LoginActivity.class));

                ActivityCollectorUtil.finishAllActivity();

            }
        });

        //  跳转我的信息
        mViewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), MessageActivity.class));
            }
        });

        //  跳转我的关注
        mViewAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AttentionActivity.class));
            }
        });

        return view;
    }

    private void showPop() {
        View bottomView = View.inflate(getActivity(), R.layout.layout_bottom_dialog, null);
        TextView mAlbum = bottomView.findViewById(R.id.tv_album);
        TextView mCamera = bottomView.findViewById(R.id.tv_camera);
        TextView mCancel = bottomView.findViewById(R.id.tv_cancel);

        pop = new PopupWindow(bottomView, -1, -2);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.5f;
        getActivity().getWindow().setAttributes(lp);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        pop.setAnimationStyle(R.style.main_menu_photo_anim);
        pop.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.tv_album:
                        //相册
                        PictureSelector.create(getActivity())
                                .openGallery(PictureMimeType.ofImage())
                                .maxSelectNum(maxSelectNum)
                                .minSelectNum(1)
                                .imageSpanCount(4)
                                .selectionMode(PictureConfig.MULTIPLE)
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                    case R.id.tv_camera:
                        //拍照
                        PictureSelector.create(getActivity())
                                .openCamera(PictureMimeType.ofImage())
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                    case R.id.tv_cancel:
                        //取消
                        //closePopupWindow();
                        break;
                }
                closePopupWindow();
            }
        };

        mAlbum.setOnClickListener(clickListener);
        mCamera.setOnClickListener(clickListener);
        mCancel.setOnClickListener(clickListener);
    }

    public void closePopupWindow() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
            pop = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<LocalMedia> images;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调

                    images = PictureSelector.obtainMultipleResult(data);
                    selectList.addAll(images);

//                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
//                    adapter.setList(selectList);
//                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtil.removeActivity(getActivity());
    }
}
