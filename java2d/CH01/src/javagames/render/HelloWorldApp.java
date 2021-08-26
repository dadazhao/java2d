package javagames.render;

import java.awt.*;
import javax.swing.*;
import javagames.util.*;
/**
 * 第一个实例
 */
public class HelloWorldApp extends JFrame {
   
	//帧率计算工具
   private FrameRate frameRate;
   
   //构造函数 初始化帧率工具
   public HelloWorldApp() {
      frameRate = new FrameRate();
   }
   
   /**
    * 创建并显示窗口程序
    */
   protected void createAndShowGUI() {
	  //创建游戏面板
      GamePanel gamePanel = new GamePanel();
      //设置背景颜色
      gamePanel.setBackground( Color.WHITE );
      //设置面板大小
      gamePanel.setPreferredSize( new Dimension( 320, 240 ) );
      //将面板添加到窗口中
      getContentPane().add( gamePanel );
      //设置窗口的默认按钮功能
      setDefaultCloseOperation( EXIT_ON_CLOSE );
      //窗口标题
      setTitle( "Hello World!" );
      //组件自适应窗口大小
      pack();
      //帧率工具初始化
      frameRate.initialize();
      //窗口可见
      setVisible( true );
   }
   
   /**
    * 创建游戏面板类
    * @author AcmenZhao
    *
    */
   private class GamePanel extends JPanel {
	   //重写绘制方法
       public void paint( Graphics g ) {
         super.paint( g );
         //自定义绘制方法
         onPaint( g );
      }
   }
   /**
    * 自定义绘制方法
    * @param g
    */
   protected void onPaint( Graphics g ) {
	   //计算帧率
      frameRate.calculate();
      //设置前景色
      g.setColor( Color.BLACK );
      //绘制文字
      g.drawString( frameRate.getFrameRate(), 30, 30 );
      //清空重绘
      repaint();
   }

   public static void main( String[] args ) {
      final HelloWorldApp app = new HelloWorldApp();
      SwingUtilities.invokeLater( new Runnable() {
         public void run() {
            app.createAndShowGUI();
         }
      });
   }
}
