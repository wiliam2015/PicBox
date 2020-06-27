package me.wiliam.screenshot;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Screenshot {
    /*
    * 第一二个参数为要截取图片的左上角坐标
    * 第三四个参数为要截取图片的宽度
    * */
    //截图核心
    public void getScreenImg(int start_x,int start_y, int imageWidth, int imageHeight){

        try {
            //截取类
            Robot robot = new Robot();
            //一个矩形（强转类型）
            Rectangle rectangle = new Rectangle( start_x, start_y, imageWidth, imageHeight);
            //返回一个BufferedImage类型的图片
            BufferedImage screenCapture = robot.createScreenCapture(rectangle);
            //将转换BufferedImage类型的图片为可写的image
            WritableImage writableImage = SwingFXUtils.toFXImage(screenCapture, null);

            //调用图片编辑窗口
//            new PictureEditing().shotEditing(writableImage);

            //获得一个系统剪切板
            Clipboard systemClipboard = Clipboard.getSystemClipboard();
            //获得一个内容
            ClipboardContent content = new ClipboardContent();
            //添加到内容中
            content.putImage(writableImage);
            //将内容放入剪贴板中
            systemClipboard.setContent(content);

        } catch (AWTException e) {
            e.printStackTrace();
        }

    }


}
