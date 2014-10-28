package arrow.mems.bean;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named
public class ThemeBean implements Serializable {

  public static final Map<String, String> THEMES = new TreeMap<String, String>();
  static {
    ThemeBean.THEMES.put("Afterdark", "afterdark");
    ThemeBean.THEMES.put("Afternoon", "afternoon");
    ThemeBean.THEMES.put("Afterwork", "afterwork");
    ThemeBean.THEMES.put("Aristo", "aristo");
    ThemeBean.THEMES.put("Black-Tie", "black-tie");
    ThemeBean.THEMES.put("Blitzer", "blitzer");
    ThemeBean.THEMES.put("Bluesky", "bluesky");
    ThemeBean.THEMES.put("Casablanca", "casablanca");
    ThemeBean.THEMES.put("Cupertino", "cupertino");
    ThemeBean.THEMES.put("Dark-Hive", "dark-hive");
    ThemeBean.THEMES.put("Dot-Luv", "dot-luv");
    ThemeBean.THEMES.put("Eggplant", "eggplant");
    ThemeBean.THEMES.put("Excite-Bike", "excite-bike");
    ThemeBean.THEMES.put("Flick", "flick");
    ThemeBean.THEMES.put("Glass-X", "glass-x");
    ThemeBean.THEMES.put("Hot-Sneaks", "hot-sneaks");
    ThemeBean.THEMES.put("Humanity", "humanity");
    ThemeBean.THEMES.put("Le-Frog", "le-frog");
    ThemeBean.THEMES.put("Midnight", "midnight");
    ThemeBean.THEMES.put("Mint-Choc", "mint-choc");
    ThemeBean.THEMES.put("Overcast", "overcast");
    ThemeBean.THEMES.put("Pepper-Grinder", "pepper-grinder");
    ThemeBean.THEMES.put("Redmond", "redmond");
    ThemeBean.THEMES.put("Rocket", "rocket");
    ThemeBean.THEMES.put("Sam", "sam");
    ThemeBean.THEMES.put("Smoothness", "smoothness");
    ThemeBean.THEMES.put("South-Street", "south-street");
    ThemeBean.THEMES.put("Start", "start");
    ThemeBean.THEMES.put("Sunny", "sunny");
    ThemeBean.THEMES.put("Swanky-Purse", "swanky-purse");
    ThemeBean.THEMES.put("Trontastic", "trontastic");
    ThemeBean.THEMES.put("UI-Darkness", "ui-darkness");
    ThemeBean.THEMES.put("UI-Lightness", "ui-lightness");
    ThemeBean.THEMES.put("Vader", "vader");
    ThemeBean.THEMES.put("MEMS 800", "mems800");
  }

  public Map<String, String> getThemes() {
    return ThemeBean.THEMES;
  }
}
