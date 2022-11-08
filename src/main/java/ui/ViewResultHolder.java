package ui;

public class ViewResultHolder {
    public static ViewResult view;
    static{
        view = new ViewResult();
    }
    public static void reOpen(){
        if(null != view){
            view.close(0);
        }
        view = new ViewResult();
        view.show();
    }
}
