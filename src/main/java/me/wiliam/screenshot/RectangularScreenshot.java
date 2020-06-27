package me.wiliam.screenshot;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class RectangularScreenshot {

    //建一个矩形边框(后期用2d图形做最好)
    private HBox rectangularFrame;
    //开始坐标
    private double start_x;
    private double start_y;
    //结束坐标
    private double end_x;
    private double end_y;
    //动态获取宽高
    private double sceneX;
    private double sceneY;
    //提示框
    private Label label;

    //矩形截图界面
    public void rectangularScreenshot(AnchorPane anchorPane, Stage stage, Stage stagePanel) {

        //鼠标按下
        anchorPane.setOnMousePressed(event -> {
            //每次进入清空
            anchorPane.getChildren().clear();

            //建一个矩形边框
            rectangularFrame = new HBox();

            rectangularFrame.setStyle("-fx-background-color: #ffffff00;" +
                    "-fx-border-color: #ff312a;" +
                    "-fx-border-width: 2");

            //获取开始的鼠标坐标
            start_x = event.getSceneX();
            start_y = event.getSceneY();

            anchorPane.getChildren().add(rectangularFrame);


            //提示框
            label = new Label();
            label.setPrefHeight(30);
            label.setAlignment(Pos.CENTER);
            label.setPadding(new Insets(5));
            anchorPane.getChildren().add(label);


        });

        //给anchorPane设置拖拽检测
        anchorPane.setOnDragDetected(event -> anchorPane.startFullDrag());

        //检测anchorPane上的鼠标拖拽检测
        anchorPane.setOnMouseDragOver(event -> {

            //获取动态鼠标拖拽的坐标
            sceneX = event.getSceneX();
            sceneY = event.getSceneY();

            //判断优化
            /*
             * 整个屏幕分为四种情况截图
             *
             * sceneX 当前鼠标x轴
             * sceneY 当前鼠标y轴
             * start_x 开始鼠标x轴
             * start_y 开始鼠标y轴
             *
             * 分别依次为 ： 1.右下角 2.左上角 3.右上角 4.左下角
             *
             * 1.sceneX >= start_x && sceneY >= start_y
             * 2.sceneX <= start_x && sceneY <= start_y
             * 3.sceneX >= start_x && sceneY <= start_y
             * 4.sceneX <= start_x && sceneY >= start_y
             *
             * */
            if (sceneX >= start_x && sceneY >= start_y) {
                //右下角
                //框框 定位
                AnchorPane.setLeftAnchor(rectangularFrame, start_x);
                AnchorPane.setTopAnchor(rectangularFrame, start_y);
                //宽高提示 定位
                AnchorPane.setLeftAnchor(label, start_x);
                AnchorPane.setTopAnchor(label, start_y - label.getPrefHeight());

            } else if (sceneX <= start_x && sceneY <= start_y) {
                //左上角
                //框框 定位
                AnchorPane.setLeftAnchor(rectangularFrame, sceneX);
                AnchorPane.setTopAnchor(rectangularFrame, sceneY);
                //宽高提示 定位
                AnchorPane.setLeftAnchor(label, sceneX);
                AnchorPane.setTopAnchor(label, sceneY - label.getPrefHeight());

            } else if (sceneX >= start_x && sceneY <= start_y) {
                //右上角
                //框框 定位
                AnchorPane.setLeftAnchor(rectangularFrame, start_x);
                AnchorPane.setTopAnchor(rectangularFrame, sceneY);
                //宽高提示 定位
                AnchorPane.setLeftAnchor(label, start_x);
                AnchorPane.setTopAnchor(label, sceneY - label.getPrefHeight());

            } else if (sceneX <= start_x && sceneY >= start_y) {
                //右下角
                //框框 定位
                AnchorPane.setLeftAnchor(rectangularFrame, sceneX);
                AnchorPane.setTopAnchor(rectangularFrame, start_y);
                //宽高提示 定位
                AnchorPane.setLeftAnchor(label, sceneX);
                AnchorPane.setTopAnchor(label, start_y - label.getPrefHeight());

            }

            //计算矩形的宽高
            double rectangular_width = Math.abs(sceneX - start_x);
            double rectangular_height = Math.abs(sceneY - start_y);

            //动态设置宽高
            rectangularFrame.setPrefWidth(rectangular_width);
            rectangularFrame.setPrefHeight(rectangular_height);
            //提示文字
            label.setText("宽度：" + rectangular_width + "px   高度：" + rectangular_height + "px");

            if (rectangular_width > 0) {
                label.setStyle("-fx-background-color: #0a0a0a80;" +
                        "-fx-text-fill: #e8e8e8");
            }

        });


        //退出拖拽动作
        anchorPane.setOnMouseDragExited(event -> {
            //获得最后的坐标
            end_x = event.getSceneX();
            end_y = event.getSceneY();

            //完成按钮
            Button button = new Button("选择完成");
            button.setStyle("-fx-background-color: #1d94ff;" +
                    "-fx-text-fill: #ffffff");
            rectangularFrame.getChildren().add(button);
            rectangularFrame.setAlignment(Pos.BOTTOM_RIGHT);

            //计算截屏区域宽高
            double getimageW = Math.abs(start_x - end_x);
            double getimageH = Math.abs(start_y - end_y);

            //单击事件
            button.setOnAction(event1 -> {

                //先关面板窗口
                stagePanel.close();
                //在关工具窗口
                stage.close();

                //判断优化
                if (sceneX >= start_x && sceneY >= start_y) {
                    //右下角
                    //调用截图核心
                    new Screenshot().getScreenImg((int) start_x, (int) start_y, (int) getimageW, (int) getimageH);

                } else if (sceneX <= start_x && sceneY <= start_y) {
                    //左上角
                    //调用截图核心
                    new Screenshot().getScreenImg((int) sceneX, (int) sceneY, (int) getimageW, (int) getimageH);

                } else if (sceneX >= start_x && sceneY <= start_y) {
                    //右上角
                    //调用截图核心
                    new Screenshot().getScreenImg((int) start_x, (int) sceneY, (int) getimageW, (int) getimageH);

                } else if (sceneX <= start_x && sceneY >= start_y) {
                    //右下角
                    //调用截图核心
                    new Screenshot().getScreenImg((int) sceneX, (int) start_y, (int) getimageW, (int) getimageH);

                }


            });
        });
    }

}
