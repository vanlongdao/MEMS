package arrow.framework.ui;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

import arrow.framework.util.i18n.Messages;
import arrow.mems.ui.ScreenContext;

public class MenuModelBuilder {

  public MenuModel createMenuModel(List<ScreenContext> contexts) {
    final MenuModel model = new DefaultMenuModel();
    String navigationPath = StringUtils.EMPTY;
    for (int index = contexts.size() - 1; index >= 0; index--) {
      final ScreenContext context = contexts.get(index);
      model.addElement(this.createMenuItem(context, index));
      navigationPath += ">>" + context.getScreenName();
    }

    System.out.println(navigationPath);
    return model;
  }

  private DefaultMenuItem createMenuItem(ScreenContext context, int index) {
    final DefaultMenuItem menuItem = new DefaultMenuItem();
    final String screenTitle = Messages.get(context.getScreenName());
    menuItem.setValue(screenTitle);
    // menuItem.setCommand("#{screenMonitor.backToScreen(" + context.getScreenCode() + ")}");
    menuItem.setId(String.valueOf(index));
    menuItem.setTitle(screenTitle);
    // menuItem.setUpdate("@all");
    // menuItem.setImmediate(true);
    // menuItem.setProcess("@this");
    return menuItem;
  }
}
