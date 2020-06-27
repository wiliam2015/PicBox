package me.wiliam.util;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.wiliam.domain.PicBoxTrayMenu;
import me.wiliam.screenshot.RectangularScreenshot;
import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

/**
 * 项目名称：PicBox<br>
 * *********************************<br>
 * <P>类名称：PicBoxSystemTray</P>
 * *********************************<br>
 * <P>类描述：PicBox系统托盘类</P>
 *
 * @version 1.0<br>
 * @author：wiliam<br>
 * @date：2020/06/27 <br>
 * 描述：<br>
 */
public class PicBoxSystemTray {
    /**日志*/
    private Logger logger = Logger.getLogger(PicBoxSystemTray.class);
    /**托盘菜单实体类*/
    private PicBoxTrayMenu trayMenu;
    /**logo*/
    private static final String LOGO_PATH="/static/images/logo.png";

    private static TrayIcon trayIcon;
    private static ActionListener showListener;
    private static ActionListener exitListener;
    private static MouseListener mouseListener;
    Stage stagePanel;
    public PicBoxSystemTray(PicBoxTrayMenu trayMenu){
        try {
            //检查系统是否支持托盘
            if (!SystemTray.isSupported()) {
                //系统托盘不支持
                logger.info(Thread.currentThread().getStackTrace()[ 1 ].getClassName() + ":系统托盘不支持");
                return;
            }
            this.trayMenu = trayMenu;
            //设置图标尺寸自动适应
            trayIcon.setImageAutoSize(true);
            //系统托盘
            SystemTray tray = SystemTray.getSystemTray();
            //弹出式菜单组件
            PopupMenu popup = new PopupMenu();
            initPopUpMenu(popup);
            trayIcon.setPopupMenu(popup);
            //鼠标移到系统托盘,会显示提示文本
            trayIcon.setToolTip("PicBox\n截图快捷键 F2");
            tray.add(trayIcon);
        } catch (Exception e) {
            //系统托盘添加失败
            logger.error(Thread.currentThread().getStackTrace()[ 1 ].getClassName() + ":系统托盘初始化失败", e);
        }
    }

    /**
     * 初始化右键菜单
     * @param popup
     */
    private void initPopUpMenu(PopupMenu popup) {

        popup.add(trayMenu.getTextScreenshot());
        popup.add(trayMenu.getTextDisableKeyboardShortcuts());
        popup.add(trayMenu.getTextPreferences());
        popup.add(trayMenu.getTextHelp());
        popup.add(trayMenu.getTextCheckUpdate());
        popup.add(trayMenu.getTextExit());

    }

    static{
        //执行stage.close()方法,窗口不直接退出
        Platform.setImplicitExit(false);
        //此处不能选择ico格式的图片,要使用16*16的png格式的图片
        URL url = MySystemTray.class.getResource(LOGO_PATH);
        Image image = Toolkit.getDefaultToolkit().getImage(url);
        //系统托盘图标
        trayIcon = new TrayIcon(image);
        //初始化监听事件(空)
        showListener = e -> Platform.runLater(() -> {});
        exitListener = e -> {};
        mouseListener = new MouseAdapter() {};
    }

    /**
     * 更改系统托盘所监听的Stage
     */
    public void listen(Stage stage){
        //防止报空指针异常
        if(showListener == null || exitListener == null || mouseListener == null || trayMenu == null  || trayIcon == null){
            return;
        }
        //移除原来的事件
        trayMenu.getTextPreferences().removeActionListener(showListener);
        trayMenu.getTextExit().removeActionListener(exitListener);
        trayIcon.removeMouseListener(mouseListener);
        //行为事件: 点击"打开"按钮,显示窗口
        showListener = e -> Platform.runLater(() -> showStage(stage));
        //行为事件: 点击"退出"按钮, 就退出系统
        exitListener = e -> {
            System.exit(0);
        };
        //鼠标行为事件: 单机显示stage
        mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //鼠标左键
                if (e.getButton() == MouseEvent.BUTTON1) {
                    showStage(stage);
                }
            }
        };
        //给菜单项添加事件
        trayMenu.getTextScreenshot().addActionListener((e -> {
            screenshotPanel(stage,0);
        }));
        trayMenu.getTextPreferences().addActionListener(showListener);
        trayMenu.getTextExit().addActionListener(exitListener);
        //给系统托盘添加鼠标响应事件
        trayIcon.addMouseListener(mouseListener);
    }

    //创建，一个截图面板
    private void screenshotPanel(Stage stage, int callJudgment) {

        //一个截图面板
        stagePanel = new Stage();

        AnchorPane anchorPane = new AnchorPane();
        //设置一个半透明
        anchorPane.setStyle("-fx-background-color: rgba(17,17,17,0.19)");

        Scene scene = new Scene(anchorPane);
        scene.setFill(Paint.valueOf("#ffffff00"));
        stagePanel.setScene(scene);
        //去除全屏幕时的文字提示
        stagePanel.setFullScreenExitHint("");
        //去除窗口样式
        stagePanel.initStyle(StageStyle.TRANSPARENT);
        //设置全屏（窗口上必须要有场景图和根节点）
        stagePanel.setFullScreen(true);
        stagePanel.show();

        //监听事件  按esc按键关闭窗口
        scene.setOnKeyPressed(event -> {
            //设置快捷键
            if (event.getCode() == KeyCode.ESCAPE) {
                //关闭截屏窗口
                stagePanel.close();
                //打开截图工具
                stage.show();
            }
        });

        //按鼠标右键关闭窗口
        scene.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {

            String name = event.getButton().name();

            if (name.equals(MouseButton.SECONDARY.name())) {
                //关闭截屏窗口
                stagePanel.close();
                //打开截图工具
                stage.show();
            }

        });

        //判断调用那个截图方法
        switch (callJudgment) {
            case 0:
                //调用矩形截图方法
                new RectangularScreenshot().rectangularScreenshot(anchorPane, stage, stagePanel);
                break;
            case 1:
                //调用任意截图方法
                break;
        }


    }
    /**
     * 关闭窗口
     */
    public void hide(Stage stage){
        Platform.runLater(() -> {
            //如果支持系统托盘,就隐藏到托盘,不支持就直接退出
            if (SystemTray.isSupported()) {
                //stage.hide()与stage.close()等价
                stage.hide();
            } else {
                System.exit(0);
            }
        });
    }

    /**
     * 点击系统托盘,显示界面(并且显示在最前面,将最小化的状态设为false)
     */
    private void showStage(Stage stage){
        //点击系统托盘,
        Platform.runLater(() -> {
            if(stage.isIconified()){ stage.setIconified(false);}
            if(!stage.isShowing()){ stage.show(); }
            stage.toFront();
        });
    }
}
