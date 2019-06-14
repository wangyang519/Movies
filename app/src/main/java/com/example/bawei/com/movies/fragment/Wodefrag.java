package com.example.bawei.com.movies.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.activity.AttentionActivity;
import com.example.bawei.com.movies.activity.FeedBackActivity;
import com.example.bawei.com.movies.activity.LoginActivity;
import com.example.bawei.com.movies.activity.MessageActivity;
import com.example.bawei.com.movies.activity.RccordActivity;
import com.example.bawei.com.movies.activity.SystemMessageActivity;
import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.bean.UserInfo;
import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.dao.DaoMaster;
import com.example.bawei.com.movies.dao.UserInfoDao;
import com.example.bawei.com.movies.presenter.Sign_inPresenter;
import com.example.bawei.com.movies.util.ActivityCollectorUtil;
import com.example.bawei.com.movies.util.StatusBarUtil;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
    @BindView(R.id.rccord)
    ImageView mViewRccord;
    @BindView(R.id.feedback)
    ImageView mViewFeedBack;
    @BindView(R.id.sign_in)
    Button mButtonSign_in;
    @BindView(R.id.systemMessage)
    ImageView mViewSystemMessage;

    private PopupWindow pop;
    private UserInfoDao mDao;

    private Sign_inPresenter mPresenter;

    //拍照 ResponseCode
    private static final int TAKE_PHOTO = 11;// 拍照
    private static final int CROP_PHOTO = 12;// 裁剪图片
    private static final int LOCAL_CROP = 13;// 本地图库

    private Uri imageUri;
    private String filePath;
    private UserInfo mUserInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.wode_fra, container, false);
        ButterKnife.bind(this, view);

        StatusBarUtil.setTransparent(getActivity());

        ActivityCollectorUtil.addActivity(getActivity());

        mUserInfo = DaoMaster.newDevSession(getContext(), UserInfoDao.TABLENAME).getUserInfoDao().loadAll().get(0);

        mViewHeard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop();
            }
        });

        //  跳转到系统消息页面
        mViewSystemMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SystemMessageActivity.class));
            }
        });

        //  签到
        mButtonSign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPresenter = new Sign_inPresenter(new DataCall<Result>() {
                    @Override
                    public void success(Result result) {
                        Toast.makeText(getContext(), result.message, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void error(Result result) {
                        Toast.makeText(getContext(), result.message, Toast.LENGTH_LONG).show();
                    }
                });

                mPresenter.requestData(String.valueOf(mUserInfo.getId()), mUserInfo.getSessionId());
            }
        });

        //  退出登录
        mViewOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDao = DaoMaster.newDevSession(getActivity(),UserInfoDao.TABLENAME).getUserInfoDao();
                UserInfo unique = mDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).unique();
                unique.setStatus(0);
                mDao.insertOrReplaceInTx(unique);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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

        //  跳转购票记录
        mViewRccord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RccordActivity.class));
            }
        });

        //  跳转意见反馈
        mViewFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FeedBackActivity.class));
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
                        Intent intent1 = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent1, LOCAL_CROP);
                        break;
                    case R.id.tv_camera:
                        File takePhoneImage = new File(Environment.getExternalStorageDirectory(),
                                "take_photo_image.jpg");
                        try {
                            // 如果文件存在删除
                            if (takePhoneImage.exists()) {
                                takePhoneImage.delete();
                            }
                            takePhoneImage.createNewFile();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        imageUri = Uri.fromFile(takePhoneImage);
                        filePath = imageUri.getPath();
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, TAKE_PHOTO);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:  // 拍照
                if (resultCode == RESULT_OK) {
                    // 创建intent用于裁剪图片
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    // 设置数据为文件uri，类型为图片格式
                    intent.setDataAndType(imageUri, "image/*");
                    // 允许裁剪
                    intent.putExtra("scale", true);
                    // 指定输出到文件uri中
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    // 启动intent，开始裁剪
                    startActivityForResult(intent, CROP_PHOTO);
                }
                //让系统重新扫描SDCard某个文件，相册里也会马上显示
             /*   Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(new File("/sdcard/image.jpg"));
                intent.setData(uri);
                Context.sendBroadcast(intent);*/
                break;
            case LOCAL_CROP:  // 系统图库
                if (resultCode == RESULT_OK) {
                    // 创建intent用于裁剪图片
                    Intent intent1 = new Intent("com.android.camera.action.CROP");
                    // 获取图库所选图片的uri
                    Uri uri = data.getData();
                    intent1.setDataAndType(uri, "image/*");
                    //  设置裁剪图片的宽高
                    intent1.putExtra("outputX", 300);
                    intent1.putExtra("outputY", 300);
                    // 裁剪后返回数据
                    intent1.putExtra("return-data", true);
                    // 启动intent，开始裁剪
                    startActivityForResult(intent1, CROP_PHOTO);
                }
            case CROP_PHOTO:   // 裁剪后
                if (resultCode == RESULT_OK) {
                    try {
                        // 展示拍照后裁剪的图片
                        if (imageUri != null) {
                            // 创建BitmapFactory.Options对象
                            BitmapFactory.Options option = new BitmapFactory.Options();
                            // 属性设置，用于压缩bitmap对象
                            option.inSampleSize = 2;
                            option.inPreferredConfig = Bitmap.Config.RGB_565;
                            // 根据文件流解析生成Bitmap对象
                            Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri), null, option);
                            // 展示图片
                            mViewHeard.setImageBitmap(bitmap);
                            imageUri = null;

                            //让系统重新扫描SDCard某个文件，相册里也会马上显示
                           /* Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            Uri uri = Uri.fromFile(new File("/sdcard/image.jpg"));
                            intent.setData(uri);
                            Context.sendBroadcast(intent);*/

                            return;
                        }

                        // 展示图库中选择裁剪后的图片
                        if (data != null) {
                            // 根据返回的data，获取Bitmap对象
                            Bitmap bitmap = data.getExtras().getParcelable("data");
                            // 展示图片
                            mViewHeard.setImageBitmap(bitmap);
                            data = null;
                            return;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    public void upload(View view) {
        upload();
    }

    private static final String nextLine = "\r\n";
    private static final String twoHyphens = "--";
    //分割线  随便写一个
    private static final String boundary = "wk_file_2519775";

    /**
     * 上传
     */
    //diyige 第二个点击事件
    public void upload() {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                File file = new File(filePath);
                HttpURLConnection connection = null;
                try {
                    URL url1 = new URL("http://169.254.5.80:8080/upload");
                    connection = (HttpURLConnection) url1.openConnection();

                    connection.setDoOutput(true);
                    // 设置字符集
                    connection.setRequestProperty("Charsert", "UTF-8");
                    //设置接收编码
                    connection.setRequestProperty("Accept-Charset", "utf-8");
                    //开启长连接可以持续传输
                    connection.setRequestProperty("Connection", "keep-alive");
                    //设置请求参数格式以及boundary分割线
                    connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    //设置接收返回值的格式
                    connection.setRequestProperty("Accept", "application/json");

                    OutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    //分隔符头部
                    String header = twoHyphens + boundary + nextLine;
                    //分隔符参数设置
                    header += "Content-Disposition: form-data;name=\"file\";" + "filename=\"" + file.getName() + "\"" + nextLine + nextLine;
                    //写入输出流
                    outputStream.write(header.getBytes());


                    //读取文件并写入
                    FileInputStream inputStream = new FileInputStream(file);
                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = inputStream.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, length);
                    }
                    //文件写入完成后加回车
                    outputStream.write(nextLine.getBytes());

                    //写入结束分隔符
                    String footer = nextLine + twoHyphens + boundary + twoHyphens + nextLine;
                    outputStream.write(footer.getBytes());
                    outputStream.flush();

                    if (connection.getResponseCode() == 200) {
//                        return CharStreams.toString(new InputStreamReader(connection.getInputStream()));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    public void closePopupWindow() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
            pop = null;
        }
    }

}
