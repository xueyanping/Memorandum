package com.xue.yado.memorandum.activity;



import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;

import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.xue.yado.memorandum.Constant;
import com.xue.yado.memorandum.R;

import com.xue.yado.memorandum.entity.Memoire;
import com.xue.yado.memorandum.myView.CustomPopupWindow;
import com.xue.yado.memorandum.utils.AppCache;
import com.xue.yado.memorandum.utils.ImageUtils;
import com.xue.yado.memorandum.utils.PermissionReq;
import com.xue.yado.memorandum.utils.PopupWindowBackground;
import com.xue.yado.memorandum.utils.ScreenUtils;
import com.xue.yado.memorandum.utils.TimerUtil;
import com.xue.yado.memorandum.utils.ToastUtils;
import com.xue.yado.memorandum.utils.dbUtils;


import java.io.File;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import butterknife.BindView;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class AddMemorandumActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.new_memorandum_toobbar)
    android.support.v7.widget.Toolbar new_memorandum_toobbar;

    @BindView(R.id.add_biaoqian)
    ImageView add_biaoqian;

    @BindView(R.id.create_time)
    TextView create_time;

    @BindView(R.id.new_content)
    EditText new_content;

    @BindView(R.id.floatingActionsMenu)
    FloatingActionsMenu floatingActionsMenu;

    @BindView(R.id.tixing)
    FloatingActionButton tixing;

    @BindView(R.id.daiban)
    FloatingActionButton daiban;

    @BindView(R.id.paizhao)
    FloatingActionButton paizhao;

    @BindView(R.id.tupian)
    FloatingActionButton tupian;

    private String path;
    private Uri imageUri;
    private File file;

    int position;//内容位置

    private boolean isChanged = false;//判断内容是否被更改
    //private boolean isShowSave =false;
    private Memoire memoire;

    private CustomPopupWindow popupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initEvent();
    }

    @Override
    public void setContentView() {
        super.setContentView();
        setContentView(R.layout.new_memorandum_fragment_layout);
    }

    @Override
    public void initViews() {
        super.initViews();
        PermissionReq.with(this).permissions("android.permission.CAMERA").result(new PermissionReq.Result() {
            @Override
            public void onGranted() {
                setListener();
            }

            @Override
            public void onDenied() {
                Toast.makeText(AddMemorandumActivity.this, "权限未赋予", Toast.LENGTH_SHORT).show();
            }
        }).request();

        Intent intent = getIntent();
        //从悬浮按钮跳转过来的action为null
        if(null != intent.getAction()){
            position = intent.getIntExtra("position",0);
            if(intent.getAction() .equals(Constant.SEARCH_ACTION)){
                memoire = AppCache.getSearchList().get(position);
                create_time.setText(memoire.getCreateDate());
            }else if(intent.getAction() .equals(Constant.NO_SEARCH_ACTION)){
                memoire = AppCache.getMemireList().get(position);
                create_time.setText(memoire.getLastModifyDate());
            }
            initContent();
            //将光标移至文本末尾
            new_content.setSelection(new_content.getText().length());
        }

        initToolBar(new_memorandum_toobbar,getString(R.string.main_title),true,false, Constant.RIGHT_TYPE_1);
    }

    /**
     * 初始化内容，将图片路径转换成图片
     */
    public void initContent(){
        String input = memoire.getContent().toString();
        //String regex = "<img src=\\".*?\\"\\/>";
        Pattern p = Pattern.compile("\\<img src=\".*?\"\\/>");
        Matcher m = p.matcher(input);
        //List<String> result = new ArrayList<String>();

        SpannableString spannable = new SpannableString(input);
        while(m.find()){
            //这里s保存的是整个式子，即<img src="xxx"/>，start和end保存的是下标
            String s = m.group();
            int start = m.start();
            int end = m.end();
            //path是去掉<img src=""/>的中间的图片路径
            String path = s.replaceAll("\\<img src=\"|\"\\/>","").trim();

            //利用spannableString和ImageSpan来替换掉这些图片
            int width = ScreenUtils.getScreenWidth();
            //int height = ScreenUtils.getScreenHeight();
            try {
                Bitmap bitmap = ImageUtils.getSmallBitmap(path, width, 480);
                ImageSpan imageSpan = new ImageSpan(this, bitmap);
                spannable.setSpan(imageSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        new_content.setText(spannable);
    }

    @Override
    public void initEvent() {
        super.initEvent();
        create_time.setText(TimerUtil.getYear_Month_Day(new Date()));
    }


    /**
     * 打开相册
     */
    private void selectPicFromAlum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,2);
    }

    public void takePhote(){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        this.path = Environment.getExternalStorageDirectory().getAbsolutePath();
        this.path = this.path+"/abc/"+System.currentTimeMillis()+".jpg";
        file = new File(this.path);
        file.getParentFile().mkdirs();
        Log.i( "takePhote: ","path=="+this.path);
        imageUri = FileProvider.getUriForFile(this,"com.xue.yado.memorandum.provider",file);
        //对系统返回的uri添加临时访问权限
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //指定图片的输出地址(把拍好的照片保存在outputFile 的Uri中，不保留在相册中)
        i.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(i,1);
    }



public void setImageIntoEditText(String path) {
    //换行，防止与文字等在同一行
    if (!new_content.getText().equals("")) {
        new_content.append("\n");
    }
    Html.ImageGetter imageGetterFromLocal = new Html.ImageGetter() {
        @Override
        public Drawable getDrawable(String source) {
            Drawable drawable = null;
            drawable = Drawable.createFromPath(source);
            drawable.setBounds(0, 0, ScreenUtils.getScreenWidth(),
                    ScreenUtils.getScreenHeight() * 2 / 3);
            return drawable;
        }
    };

    String tagPath = "<img src=\"" + path + "\"/>";
    Bitmap bitmap = BitmapFactory.decodeFile(path);
    if (null != bitmap) {
        SpannableString ss = insertImageIntoEdit(path, tagPath);
        // Spanned spanned = Html.fromHtml("<img src=" + path + "/>", imageGetterFromLocal, new MyTagHandler(this, new_content));
        //  String s = Html.toHtml(spanned);
        //  Log.i("setImageIntoEditText", "spanned==="+s);
        insertContent(ss);
        new_content.append("\n");
    }

}


    private void insertContent(SpannableString ss) {
        Editable editable = new_content.getText();
        int start = new_content.getSelectionStart();
        editable.insert(start,ss);
        //Log.i("setImageIntoEditText", "span==== "+editable);
        new_content.setText(editable);
        new_content.setSelection(start+ss.length());
        new_content.setFocusableInTouchMode(true);
        new_content.setFocusable(true);
    }

    private SpannableString insertImageIntoEdit(String path,String tagPath) {
        SpannableString ss = new SpannableString(tagPath) ;
        Bitmap bitmap = ImageUtils.getSmallBitmap(path,ScreenUtils.getScreenWidth(),480);
        ImageSpan imageSpan = new ImageSpan(this,bitmap);
        ss.setSpan(imageSpan,0, tagPath.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if(requestCode == 1){
        if(resultCode == RESULT_OK){
            setImageIntoEditText(this.path);
            //将拍好的照片保存至相册
            Uri contentUri = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            //Log.i("onActivityResult", "onActivityResult: "+contentUri);
            intent.setData(contentUri);
            this.sendBroadcast(intent);
        }
    }
    if(requestCode == 2){
        if(resultCode == RESULT_OK){
            Uri uri = data.getData();
//            Log.i("onActivityResult", "onActivityResult:==== "+uri);
//            Log.i("onActivityResult", "imagePath:==== "+getImagePath(uri));
            setImageIntoEditText(getImagePath(uri));
        }
    }
}

    /**
     * 通过uri获取图片绝对路径
     * @param uri
     * @return
     */
    private String getImagePath(Uri uri) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = this.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    @Override
    public void setListener() {
        super.setListener();
        tixing.setOnClickListener(this);
        daiban.setOnClickListener(this);
        tupian.setOnClickListener(this);
        paizhao.setOnClickListener(this);
        new_content.setOnClickListener(this);
        add_biaoqian.setOnClickListener(this);
        new_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!isChanged){
                    isChanged = true;
                }

                if(isChanged){
                    initToolBar(new_memorandum_toobbar,getString(R.string.addNewMemorandum_toolbar),true,true, Constant.RIGHT_TYPE_1);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tixing:
                break;
            case R.id.daiban:
                Toast.makeText(this, "daiban", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tupian:
                selectPicFromAlum();
                break;
            case R.id.paizhao:
                takePhote();
                break;
            case R.id.add_biaoqian:
                showDialog();
        }

    }

    /**
     * 弹出对话框
     */

    private void showDialog() {
        popupWindow = new CustomPopupWindow(this,memoire);
        popupWindow.showAsDropDown(popupWindow.getContentView(),0,0,Gravity.BOTTOM|Gravity.CENTER);
        if(popupWindow.isShowing()){
            PopupWindowBackground.setBackgroundAlpha(this,0.6f);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    saveOrUpdate();
                }
            }).start();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.toolBar_save:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        saveOrUpdate();
                    }
                }).start();
                finish();
                break;
            case android.R.id.home:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        saveOrUpdate();
                    }
                }).start();
                finish();
                break;
        }
        return true;

    }

    private void saveOrUpdate() {
        //对象为null 内容不为空就去新建对象保存
        if(null == memoire && !new_content.getText().toString().trim().equals("")){
            memoire = new Memoire(null,new_content.getText().toString(),TimerUtil.getDateWithoutHourAndMinute(create_time.getText().toString().trim()),new Date(),"");
        }else if(null == memoire && new_content.getText().toString().trim().equals("")){
            //对象为null 内容不为空不执行保存
            return ;

        }else if(null != memoire && new_content.getText().toString().trim().equals("")){
            //对象不为null 内容为空则删除该对象
            memoire.delete();
            memoire = null;
        }else{
            memoire.setContent(new_content.getText().toString());
            memoire.setLastModifyDate(new Date());
            memoire.setType(memoire.getType());
        }
        //首先判断是否修改过
        if(isChanged && memoire !=null){
            if(dbUtils.saveOrUpdateData(memoire)){
                isChanged = false;
               // ToastUtils.toast(this,"保存成功");
            }else{
                ToastUtils.toast(this,"保存失败");
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(popupWindow != null){
            popupWindow.dismiss();
            popupWindow = null;
         }
        }
}
