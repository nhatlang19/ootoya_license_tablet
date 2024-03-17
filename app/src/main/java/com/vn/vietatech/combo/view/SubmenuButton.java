package com.vn.vietatech.combo.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.vn.vietatech.api.ItemAPI;
import com.vn.vietatech.api.ItemComboAPI;
import com.vn.vietatech.combo.POSMenuActivity;
import com.vn.vietatech.model.Item;
import com.vn.vietatech.model.ItemCombo;
import com.vn.vietatech.model.PosMenu;
import com.vn.vietatech.model.PromoClass;
import com.vn.vietatech.model.Promotion;
import com.vn.vietatech.model.Setting;
import com.vn.vietatech.model.SubMenu;
import com.vn.vietatech.utils.SettingUtil;
import com.vn.vietatech.utils.Utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class SubmenuButton extends Button {

    private Context mContext;
    private Runnable runnable;
    private Handler handler;
    public int numberClick;
    private SubMenu subMenu;
    private ArrayList<Button> listButtonMenu;
    private PosMenu selectedPOSMenu;

    public SubmenuButton(Context context, final SubMenu subMenu
            , ArrayList<Button> listButtonMenu, PosMenu selectedPOSMenu) {
        super(context);
        this.mContext = context;
        this.subMenu = subMenu;
        this.listButtonMenu = listButtonMenu;
        this.selectedPOSMenu = selectedPOSMenu;

        /*this.setLayoutParams(new GridView.LayoutParams(
                LayoutParams.MATCH_PARENT, 100)); //J3*/

        this.setLayoutParams(new GridView.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)); //J2

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setStroke(2, Color.BLACK);
        drawable.setColor(Utils.parseColor(selectedPOSMenu.getBtnColor()));
        this.setBackgroundDrawable(drawable);
        this.setTextColor(Utils.parseColor(selectedPOSMenu.getFontColor()));
        this.setTextSize(12);
        this.setText(subMenu.getDescription());
        this.setLines(2);

        handler = new Handler();
        runnable = new Runnable() {
            public void run() {

                POSMenuActivity activity = (POSMenuActivity) mContext;
                try {
                    Setting setting = SettingUtil.read(mContext);
                    if (!setting.getType().equals("1")) {
                        numberClick = 1;
                    }

                    ArrayList<Item> items = new ItemAPI(mContext).getItemBySubMenuSelected(subMenu.getDefaultValue(), activity.getPriceLevel(), String.valueOf(numberClick));
                    for (int i = 0; i < items.size(); i++) {
                        Item item = items.get(i);
                        if (item.getOnPromotion().equals("Y")) {
                            // Xử lý thêm đon giá
                            double ratio = Double.parseDouble(item.getRatio());
                            if (ratio > 0) {
                                double price = (1 - ratio) * Double.parseDouble(item.getOrgPrice());
                                items.get(i).setPromoPrice(String.valueOf(price));
                            }

                            // Xử lý ngày
                            String onPromotionTemp = "N";
                            String promoDay = getPromoDay();
                            if (item.getPromoDay(promoDay).equals("Y")) {
                                onPromotionTemp = "Y";
                            }

                            if (onPromotionTemp.equals("Y")) {
                                if (item.getReCurr().equals("Y")) {
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
                                    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

                                    Date startDate = formatter.parse(item.getStartDate());
                                    Date endDate = formatter.parse(item.getEndDate());
                                    onPromotionTemp = getPromoTime(startDate, endDate);
                                }
                            }

                            if (onPromotionTemp.equals("N")) {
                                items.get(i).setPromoPrice(item.getOrgPrice());
                            }
                            items.get(i).setOnPromotion(onPromotionTemp);
                        }
                    }

                    int idxKM = -1;
                    ArrayList<Promotion> dtKM = new ArrayList<>();
                    if (items.size() > 0) {
                        // Nếu có 2 line thì chac chan la se co khuyen mai thi cho chon lua
                        for (int i = items.size() - 1; i >= 0; i--) {
                            Item item = items.get(i);
                            if (item.getOnPromotion().equals("Y")) {
                                Promotion promotion = new Promotion(item.getPromoCode(), item.getPromoDesc());
                                dtKM.add(promotion);
                            }
                        }
                    }

                    if (dtKM.size() > 0) {
                        int promoClass = Integer.parseInt(items.get(0).getPromoClass());
                        if (promoClass == PromoClass.MuaMtangN) {
                            dtKM.add(0, new Promotion("NoKM", "Không nhận khuyến mãi"));
                            activity.onOpenDialogPromotion(dtKM, items, numberClick);
                        }
                    } else {
                        Item item = items.get(0);
                        String comboPack = item.getComboPack();
                        item.setItemType(comboPack);
                        item.setNumberClick(numberClick);
                        if (comboPack.equals("N") || comboPack.equals("R")) {
                            item.setQty(String.valueOf(item.getNumberClick()));
                            activity.addItem(item);
                        } else if (comboPack.equals("C")) {
                            ArrayList<ItemCombo> itemComboList = new ItemComboAPI(mContext).getItemComboComboBySubMenuSelected(item.getItemCode());
                            item.setItemCombo(itemComboList);
                            activity.onOpenDialogCombo(item);
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                setColorButton();
                numberClick = 0;
            }
        };
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (numberClick != 0) {
                handler.removeCallbacks(runnable);
            }
            numberClick++;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            handler.postDelayed(runnable, 450);
        }
        return super.onTouchEvent(event);
    }

    private void setColorButton() {
        for (Button button : listButtonMenu) {
            GradientDrawable drawable = (GradientDrawable) button.getBackground();
            drawable.setStroke(2, Color.BLACK);
            button.setBackgroundDrawable(drawable);
        }

        GradientDrawable drawable = (GradientDrawable) this.getBackground();
        drawable.setStroke(10, Color.BLACK);
        this.setBackgroundDrawable(drawable);
    }

    private String getPromoDay()
    {
        String promoDay = "";
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.MONDAY -> promoDay = "MON";
            case Calendar.TUESDAY -> promoDay = "TUE";
            case Calendar.WEDNESDAY -> promoDay = "WED";
            case Calendar.THURSDAY -> promoDay = "THU";
            case Calendar.FRIDAY -> promoDay = "FRI";
            case Calendar.SATURDAY -> promoDay = "SAT";
            case Calendar.SUNDAY -> promoDay = "SUN";
        }
        return promoDay;
    }

    private String getPromoTime(Date startDate, Date endDate)
    {
        String onPromotion = "N";
        Date curentDate = new Date();

        int intStartDate = startDate.getHours() * 100 + startDate.getMinutes();
        int intEndDate = endDate.getHours() * 100 + endDate.getMinutes();

        int intCurrDate = curentDate.getHours() * 100 + curentDate.getMinutes();

        if (intStartDate <= intCurrDate && intCurrDate <= intEndDate)
            onPromotion = "Y";

        return onPromotion;
    }

}
