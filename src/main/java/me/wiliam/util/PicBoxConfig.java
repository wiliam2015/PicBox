package me.wiliam.util;

import cn.hutool.setting.Setting;
import me.wiliam.domain.PicBoxTrayMenu;

import java.awt.*;

/**
 * 项目名称：PicBox<br>
 * *********************************<br>
 * <P>类名称：PicBoxConfig</P>
 * *********************************<br>
 * <P>类描述：PicBox系统配置</P>
 *
 * @version 1.0<br>
 * @author：wiliam<br>
 * @date：2020/06/27 <br>
 * 描述：<br>
 */
public class PicBoxConfig {

    private static PicBoxConfig picBoxConfig = new PicBoxConfig();
    /**系统配置*/
    private Setting setting;
    /**右键菜单实体*/
    private PicBoxTrayMenu trayMenu;

    /**右键菜单相关属性*/
    private static final String TEXT_SCREENSHOT="text.screenshot";
    private static final String TEXT_DISABLE_KEYBOARD_SHORTCUTS="text.disable.keyboard.shortcuts";
    private static final String TEXT_PREFERENCES="text.preferences";
    private static final String TEXT_HELP="text.help";
    private static final String TEXT_CHECK_UPDATE="text.check.update";
    private static final String TEXT_EXIT="text.exit";
    private PicBoxConfig(){
        setting = new Setting("config.setting");
        initTrayMenu(setting);
    }

    private void initTrayMenu(Setting setting) {
        trayMenu = new PicBoxTrayMenu();
        trayMenu.setTextScreenshot(new MenuItem(setting.getStr(TEXT_SCREENSHOT,"Screenshot")));
        trayMenu.setTextDisableKeyboardShortcuts(new MenuItem(setting.getStr(TEXT_DISABLE_KEYBOARD_SHORTCUTS,"Disable Keyboard shortcuts")));
        trayMenu.setTextPreferences(new MenuItem(setting.getStr(TEXT_PREFERENCES,"Preferences")));
        trayMenu.setTextHelp(new MenuItem(setting.getStr(TEXT_HELP,"Help")));
        trayMenu.setTextCheckUpdate(new MenuItem(setting.getStr(TEXT_CHECK_UPDATE,"Check Update")));
        trayMenu.setTextExit(new MenuItem(setting.getStr(TEXT_EXIT,"Exit")));
    }

    public static PicBoxConfig instance(){
        return picBoxConfig;
    }

    /**
     * 获取配置信息
     * @param key
     * @return
     */
    public String getProp(String key){
        return setting.getStr(key);
    }

    /**
     * 获取配置信息并设置默认值
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public String getProp(String key,String defaultValue){
        return setting.getStr(key,defaultValue);
    }

    /**
     * 获取右键菜单实体类
     * @return
     */
    public PicBoxTrayMenu getTrayMenu(){
        return trayMenu;
    }
}
