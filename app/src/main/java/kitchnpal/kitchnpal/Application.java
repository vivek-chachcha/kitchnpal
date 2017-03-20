package kitchnpal.kitchnpal;

import kitchnpal.util.FontUtil;

public final class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FontUtil.setDefaultFont(this, "DEFAULT", "fonts/Roboto-Regular.ttf");
        FontUtil.setDefaultFont(this, "MONOSPACE", "fonts/Roboto-Light.ttf");
        FontUtil.setDefaultFont(this, "SANS_SERIF", "fonts/Roboto-Thin.ttf");
        FontUtil.setDefaultFont(this, "SERIF", "fonts/Roboto-Bold.ttf");
    }
}
