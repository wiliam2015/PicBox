package me.wiliam;
/**
 * 项目名称：PicBox<br>
 * *********************************<br>
 * <P>类名称：MainApp</P>
 * *********************************<br>
 * <P>类描述：启动类</P>
 *菜单项(打开)中文乱码的问题是编译器的锅,如果使用IDEA,需要在Run-Edit Configuration在LoginApplication中的VM Options中添加-Dfile.encoding=GBK
 *如果使用Eclipse,需要右键Run as-选择Run Configuration,在第二栏Arguments选项中的VM Options中添加-Dfile.encoding=GBK
 * @author：wiliam<br>
 * @date：2020/06/27 <br>
 * 描述：<br>
 * @version 1.0<br>
 */

import javafx.application.Application;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.stage.Stage;
import me.wiliam.util.PicBoxConfig;
import me.wiliam.util.PicBoxSystemTray;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

//矩形截图快捷键  绑定事件
        KeyCombination keyCombination = KeyCombination.valueOf("ctrl+shift+alt+p");
//        scene.addMnemonic(new Mnemonic(rectangularScreenshot, keyCombination));


        primaryStage.setTitle("2D图形展示");
//        Group root = new Group();
//        //直线
//        Line line =new Line();
//        line.setStartX (150.0f);
//        line.setStartY(140.0f);
//        line.setEndX(450.0f);
//        line.setEndY(140.0f);
//
//        root.getChildren().add(line);
//
//        //竖线
//        Line shuxian =new Line();
//        shuxian.setStartX (180.0f);
//        shuxian.setStartY(140.0f);
//        shuxian.setEndX(180.0f);
//        shuxian.setEndY(280.0f);
//        root.getChildren().add(shuxian);
//
//        Scene scene = new Scene(root,600,300);
//        primaryStage.setScene(scene);

        primaryStage.show();
        //初始化托盘
        PicBoxSystemTray picBoxSystemTray = new PicBoxSystemTray(PicBoxConfig.instance().getTrayMenu());
        picBoxSystemTray.listen(primaryStage);
    }
}
