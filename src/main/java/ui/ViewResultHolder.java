package ui;

import java.awt.*;

public class ViewResultHolder {
    private static ViewResult view;
    static{
        view = new ViewResult();
        view.setLocation(600,300);
    }
    public static void reOpen(){
        Point point = view.getLocation();
        System.out.println(point.toString());
        int index = view.getSelectedIndex();
        if(null != view){
            view.close(0);
        }
        view = new ViewResult();
        view.setLocation(point);
        view.setSelectedIndex(index);
        view.show();
    }
}
