package com.liying.ipgw.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.adapter.ColorThemeListAdapter;
import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.model.ColorTheme;
import com.liying.ipgw.utils.Constants;
import com.liying.ipgw.utils.RecyclerItemClickListener;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2017/3/15 23:10
 * 版本：1.0
 * 描述：色彩主题Activity
 * 备注：
 * =======================================================
 */
public class ColorThemeActivity extends BaseActivity {
    private TextView tvBack;
    private RecyclerView rvColorTheme;
    private ColorThemeListAdapter adapter;
    private static ColorTheme[] colorThemes = new ColorTheme[8];
    private RecyclerItemClickListener.OnItemClickListener localListener;

    private static final String[] THEME_NAME = {
            "遠州鼠", "落栗", "蘇芳", "石竹",
            "枯草", "柳煤竹茶", "錆青磁", "鳩羽紫"
    };
    private static final int[] THEME_PREVIEW = {
            R.mipmap.theme_yzs, R.mipmap.theme_ll, R.mipmap.theme_sf, R.mipmap.theme_sz,
            R.mipmap.theme_kc, R.mipmap.theme_lmzc, R.mipmap.theme_qqc, R.mipmap.theme_jyz
    };

    private static final int[] STYLE = {
            R.style.YZS_Theme, R.style.LL_Theme, R.style.SF_Theme, R.style.SZ_Theme,
            R.style.KC_Theme, R.style.LMZC_Theme, R.style.QQC_Theme, R.style.JYZ_Theme
    };
    /**
     * 主题选择的顺序字符串
     */
    private static String themeSelectStr = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_theme);
        AccountApp.activityList.add(this);
        initView();
        setListener();
        initData();
        // 恢复保存的值
        if (savedInstanceState != null) {
            themeSelectStr = savedInstanceState.getString("themeStr");
        }

        if ("01234567".equals(themeSelectStr)) {
            // 将所有主题按顺序选择一遍，触发彩蛋
            // 但前提是，不可以滑动屏幕导致item复用
            AccountApp.getInstance().triggerEgg(ColorThemeActivity.this, 10);
        }
    }

    private void initView() {
        tvBack = (TextView) findViewById(R.id.tvBack);
        rvColorTheme = (RecyclerView) findViewById(R.id.rvColorTheme);
        //设置GridLayoutManager
        rvColorTheme.setItemAnimator(new DefaultItemAnimator());
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvColorTheme.setLayoutManager(layoutManager);

    }

    private void setListener() {
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        for (int i = 0; i < colorThemes.length; i++) {
            if (THEME_NAME[i].equals(AccountApp.colorThemeName)) {
                colorThemes[i] = new ColorTheme(i, THEME_NAME[i], THEME_PREVIEW[i], true, STYLE[i]);
            } else {
                colorThemes[i] = new ColorTheme(i, THEME_NAME[i], THEME_PREVIEW[i], false, STYLE[i]);
            }
        }
        adapter = new ColorThemeListAdapter(this, colorThemes);
        rvColorTheme.setAdapter(adapter);

        // 列表单击事件监听
        setRecyclerListener();
        rvColorTheme.addOnItemTouchListener(new RecyclerItemClickListener(this, rvColorTheme, localListener));
    }

    private void setRecyclerListener() {
        this.localListener = new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                themeSelectStr += String.valueOf(position);

                if (colorThemes[position].isSelected()) {
                    return;
                }
                setThemeSelected(position);
                // 保存设置
                AccountApp.getInstance().editor.putString(Constants.COLOR_THEME, colorThemes[position].getThemeName()).apply();
                AccountApp.colorThemeName = colorThemes[position].getThemeName();
                // 设置主题
                getApplication().setTheme(colorThemes[position].getStyleId());
                AccountApp.recreateActivity();  // 重新创建所有Activity
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        };
    }

    /**
     * 设置色彩主题列表的选中状态
     * @param position
     */
    private void setThemeSelected(int position) {
        for (int i = 0; i < colorThemes.length; i++) {
            colorThemes[i].setSelected(false);
        }
        colorThemes[position].setSelected(true);
        adapter.setData(colorThemes);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("themeStr", themeSelectStr);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AccountApp.activityList.remove(this);
    }
}
