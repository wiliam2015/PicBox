package me.wiliam.domain;

import java.awt.*;

/**
 * 项目名称：PicBox<br>
 * *********************************<br>
 * <P>类名称：PicBoxTrayMenu</P>
 * *********************************<br>
 * <P>类描述：系统托盘菜单</P>
 *
 * @version 1.0<br>
 * @author：wiliam<br>
 * @date：2020/06/27 <br>
 * 描述：<br>
 */
public class PicBoxTrayMenu {

    /**截图*/
    private MenuItem textScreenshot;

    /**禁用快捷键*/
    private MenuItem textDisableKeyboardShortcuts;

    /**首选项*/
    private MenuItem textPreferences;

    /**帮助*/
    private MenuItem textHelp;

    /**检查更新*/
    private MenuItem textCheckUpdate;

    /**退出*/
    private MenuItem textExit;

    public MenuItem getTextScreenshot() {
        return textScreenshot;
    }

    public void setTextScreenshot(MenuItem textScreenshot) {
        this.textScreenshot = textScreenshot;
    }

    public MenuItem getTextDisableKeyboardShortcuts() {
        return textDisableKeyboardShortcuts;
    }

    public void setTextDisableKeyboardShortcuts(MenuItem textDisableKeyboardShortcuts) {
        this.textDisableKeyboardShortcuts = textDisableKeyboardShortcuts;
    }

    public MenuItem getTextPreferences() {
        return textPreferences;
    }

    public void setTextPreferences(MenuItem textPreferences) {
        this.textPreferences = textPreferences;
    }

    public MenuItem getTextHelp() {
        return textHelp;
    }

    public void setTextHelp(MenuItem textHelp) {
        this.textHelp = textHelp;
    }

    public MenuItem getTextCheckUpdate() {
        return textCheckUpdate;
    }

    public void setTextCheckUpdate(MenuItem textCheckUpdate) {
        this.textCheckUpdate = textCheckUpdate;
    }

    public MenuItem getTextExit() {
        return textExit;
    }

    public void setTextExit(MenuItem textExit) {
        this.textExit = textExit;
    }
}
