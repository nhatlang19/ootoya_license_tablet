package com.vn.vietatech.combo.adapter;

import java.util.ArrayList;

import com.vn.vietatech.combo.MyApplication;
import com.vn.vietatech.combo.POSMenuActivity;
import com.vn.vietatech.model.PosMenu;
import com.vn.vietatech.utils.Utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Button;

public class MainMenuAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<PosMenu> listPosMenu = new ArrayList<PosMenu>();
    MyApplication globalVariable;

    ArrayList<Button> listButtonMenu = new ArrayList<Button>();

    public MainMenuAdapter(Context c) {
        this.mContext = c;

        globalVariable = (MyApplication) mContext.getApplicationContext();
        listPosMenu = globalVariable.getListPosMenu();
    }

    public int getCount() {
        return listPosMenu.size();
    }

    public PosMenu getItem(int position) {
        return listPosMenu.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Button btn;
        final PosMenu posMenu = listPosMenu.get(position);

            btn = new Button(mContext);
			/*btn.setLayoutParams(new GridView.LayoutParams(
					LayoutParams.MATCH_PARENT, 84)); //J3 ASUS*/
            btn.setLayoutParams(new GridView.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setStroke(2, Color.BLACK);
            drawable.setColor(Utils.parseColor(posMenu.getBtnColor()));
            btn.setBackgroundDrawable(drawable);
            btn.setTextSize(14);
            btn.setTypeface(Typeface.DEFAULT_BOLD);
            btn.setTextColor(Utils.parseColor(posMenu.getFontColor()));
            btn.setText(posMenu.getDescription());
            btn.setLines(2);
//            btn.setPadding(0, 0, 0, 0);
            btn.setMinWidth(0);
            btn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    POSMenuActivity activity = (POSMenuActivity) mContext;
                    activity.loadSubMenu(posMenu);

                    clearAllButton();

                    GradientDrawable drawable = (GradientDrawable) btn.getBackground();
                    drawable.setStroke(10, Color.BLACK);
                    btn.setBackgroundDrawable(drawable);
                }
            });

            listButtonMenu.add(btn);
            //convertView.setTag(btn);


        return btn;
    }

    private void clearAllButton() {
        for (Button button : listButtonMenu) {
            GradientDrawable drawable = (GradientDrawable) button.getBackground();
            drawable.setStroke(2, Color.BLACK);
            button.setBackgroundDrawable(drawable);
        }
    }
}