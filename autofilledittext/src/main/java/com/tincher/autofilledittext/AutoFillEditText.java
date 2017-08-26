package com.tincher.autofilledittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.RelativeLayout;

/**
 * 使用时需设定Tag，同 tag 的 AutoFillEditText 会自动记录上三次的输入
 * As a toy custom view
 * Created by Administrator on 2017/8/25.
 */

public class AutoFillEditText extends RelativeLayout {
    private static final float DEFAULT_TEXT_SIZE = 16;
    private static final int DEFAULT_EDITTEXT_PADDINGLEFT = 0;
    private static final int DEFAULT_EDITTEXT_PADDINGRIGHT = 0;
    private static final int DEFAULT_EDITTEXT_PADDINGTOP = 0;
    private static final int DEFAULT_EDITTEXT_PADDINGBOTTOM = 0;
    private static final int DEFAULT_CLEANER_SIZE = 20;
    private static final int DEFAULT_DROPDOWN_SIZE = 20;

    protected boolean autoFill,cleanerEnable;
    protected String mTag, tag1, tag2, tag3, hint, defaultText;
    protected int textColor;
    protected float textSize;
    protected int dropDownSize, cleanerSize;
    protected int paddingLeft, paddingRight, paddingTop, paddingBottom;
    protected Context context;
    protected EditText editText;
    protected Button cleanerButton, dropButton;

    private String[] list = new String[3];
    private ListPopupWindow listPopupWindow;


    public AutoFillEditText(Context context) {
        this(context, null);
    }

    public AutoFillEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoFillEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initTypedArray(context, attrs);
        initView(context);
    }

    public void initView(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.auto_fill, this, true);
        listPopupWindow = new ListPopupWindow(context);
        editText = (EditText) view.findViewById(R.id.et);
        cleanerButton = (Button) view.findViewById(R.id.bt);
        dropButton = (Button) view.findViewById(R.id.bt_drop);

        if (hint != null) editText.setHint(hint);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    if (cleanerEnable)cleanerButton.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(mTag)) {
                        SpHandler.setString(context, mTag, s.toString());
                    }
                } else {
                    cleanerButton.setVisibility(View.GONE);
                }
            }
        });
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    savePopWindowList(editText.getText().toString());
                }
            }
        });

        if (TextUtils.isEmpty(hint)) editText.setHint(hint);
        editText.setTextColor(textColor);
        editText.setTextSize(textSize);
        editText.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        if (autoFill)editText.setText(SpHandler.getString(context, mTag, ""));

        cleanerButton.setHeight(cleanerSize);
        cleanerButton.setWidth(cleanerSize);
        cleanerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

        dropButton.setHeight(dropDownSize);
        dropButton.setWidth(dropDownSize);
        dropButton.setBackgroundResource(R.drawable.ic_arrow_drop_down);
        dropButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listPopupWindow.isShowing()) {
                    listPopupWindow.dismiss();
                    dropButton.setBackgroundResource(R.drawable.ic_arrow_drop_up);
                } else {
                    showListPopWindow();
                    dropButton.setBackgroundResource(R.drawable.ic_arrow_drop_down);
                }
            }
        });

        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editText.setText(list[i]);
                listPopupWindow.dismiss();
            }
        });
        ;
        listPopupWindow.setAdapter(new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1, list));
        listPopupWindow.setAnchorView(editText);
        listPopupWindow.setModal(true);
    }

    private void initTypedArray(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoFillEditText);
        autoFill = mTypedArray.getBoolean(R.styleable.AutoFillEditText_autoFill, true);
        cleanerEnable = mTypedArray.getBoolean(R.styleable.AutoFillEditText_cleanerEnable, true);
        hint = mTypedArray.getString(R.styleable.AutoFillEditText_hint);
        mTag = mTypedArray.getString(R.styleable.AutoFillEditText_tag);
        tag1 = mTag + "0";
        tag2 = mTag + "1";
        tag3 = mTag + "2";
        defaultText = mTypedArray.getString(R.styleable.AutoFillEditText_defaultText);
        textColor = mTypedArray.getColor(R.styleable.AutoFillEditText_textColor, Color.BLACK);
        textSize = mTypedArray.getDimension(R.styleable.AutoFillEditText_textSize, DEFAULT_TEXT_SIZE);
        cleanerSize = (int) mTypedArray.getDimension(R.styleable.AutoFillEditText_cleanerSize, DEFAULT_CLEANER_SIZE);
        dropDownSize = (int) mTypedArray.getDimension(R.styleable.AutoFillEditText_dropDownSize, DEFAULT_DROPDOWN_SIZE);
        paddingLeft = mTypedArray.getLayoutDimension(R.styleable.AutoFillEditText_paddingLeft, DEFAULT_EDITTEXT_PADDINGLEFT);
        paddingRight = mTypedArray.getLayoutDimension(R.styleable.AutoFillEditText_paddingRight, DEFAULT_EDITTEXT_PADDINGRIGHT);
        paddingTop = mTypedArray.getLayoutDimension(R.styleable.AutoFillEditText_paddingTop, DEFAULT_EDITTEXT_PADDINGTOP);
        paddingBottom = mTypedArray.getLayoutDimension(R.styleable.AutoFillEditText_paddingBottom, DEFAULT_EDITTEXT_PADDINGBOTTOM);
        //获取资源后要及时回收
        mTypedArray.recycle();
    }

    private void showListPopWindow() {
        list[0] = SpHandler.getString(context, tag1, "");
        list[1] = SpHandler.getString(context, tag2, "");
        list[2] = SpHandler.getString(context, tag3, "");
        listPopupWindow.show();
    }

    private void savePopWindowList(String s) {
        if (TextUtils.isEmpty(s)) return;
        for (int i = 0; i < 3; i++) {
            if (TextUtils.isEmpty(list[i])) {
                list[i] = s;
                SpHandler.setString(context, mTag + i, list[i]);
                return;
            }
        }
        list[2] = list[1];
        list[1] = list[0];
        list[0] = s;
        for (int i = 0; i < 3; i++) {
            SpHandler.setString(context, mTag + i, list[i]);
        }
    }

    public Editable getText(){
        return this.editText.getText();
    }

    public void setText(String s){
        this.editText.setText(s);
    }

}
