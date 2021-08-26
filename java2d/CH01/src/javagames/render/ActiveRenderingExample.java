package javagames.render;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javagames.util.*;

/**
 * 主动渲染实例
 * @author AcmenZhao
 *
 */
public class ActiveRenderingExample extends JFrame implements Runnable {
   //帧率计算工具
   private FrameRate frameRate;
   //缓冲区
   private BufferStrategy bs;
   //运行控制
   private volatile boolean running;
   //渲染线程
   private Thread gameThread;
   
   /**
    * 构造函数 初始化帧率计算工具
    */
   public ActiveRenderingExample() {
      frameRate = new FrameRate();
   }
   
   /**
    * 创建并显示窗口
    */
   protected void createAndShowGUI() {
      //创建Canvas游戏面板
      Canvas canvas = new Canvas();
      //设置面板大小
      canvas.setSize( 320, 240 );
      //设置面板背景颜色
      canvas.setBackground( Color.BLACK );
      //关闭面板自动刷新
      canvas.setIgnoreRepaint( true );
      //将面板添加到窗口
      getContentPane().add( canvas );
      //设置窗口标题
      setTitle( "Active Rendering" );
      //关闭窗口自动刷新
      setIgnoreRepaint( true );
      //组件自适应窗口大小
      pack();
      //窗口可见
      setVisible( true );
      //设置缓冲区的个数
      canvas.createBufferStrategy( 2 );
      //获得缓存去
      bs = canvas.getBufferStrategy();
      //初始化游戏主动线程
      gameThread = new Thread( this );
      //启动游戏线程
      gameThread.start();
   }
   
   //线程执行的方法
   public void run() {
	   //运行
      running = true;
      //初始化帧率计算工具
      frameRate.initialize();
      //游戏循环
      while( running ) {
         gameLoop();
      }
   }
   
   /**
    * 游戏循环方法
    */
   public void gameLoop() {
      do {
         do {
            Graphics g = null;
            try {
            	//获取Canvas缓冲区的图形句柄
               g = bs.getDrawGraphics();
               //情况缓冲区
               g.clearRect( 0, 0, getWidth(), getHeight() );
               //渲染
               render( g );
            } finally {
               if( g != null ) {
            	  //释放句柄
                  g.dispose();
               }
            }
            
         } while( bs.contentsRestored() );
         //复制到视窗
         bs.show();
       //遍历所有组件
      } while( bs.contentsLost() );
   }
   //渲染
   private void render( Graphics g ) {
	   //帧率计算
      frameRate.calculate();
      //背景颜色
      g.setColor( Color.GREEN );
      //绘制帧率文本
      g.drawString( frameRate.getFrameRate(), 30, 30 );
   }
   
   /**
    * 关闭窗口
    */
   protected void onWindowClosing() {
      try {
    	  //停止游戏循环
         running = false;
         //等待循环结束
         gameThread.join();
      } catch( InterruptedException e ) {
         e.printStackTrace();
      }
      //退出
      System.exit( 0 );
   }
   
   public static void main( String[] args ) {
      final ActiveRenderingExample app = new ActiveRenderingExample();
      app.addWindowListener( new WindowAdapter() {
         public void windowClosing( WindowEvent e ) {
            app.onWindowClosing();
         }
      });
      SwingUtilities.invokeLater( new Runnable() {
         public void run() {
            app.createAndShowGUI();
         }
      });
   }

}
