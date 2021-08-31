package javagames.render;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javagames.util.*;

/**
 * 全屏渲染实例
 * @author AcmenZhao
 *
 */
public class FullScreenRenderingExample extends JFrame implements Runnable {
   //帧率工具
   private FrameRate frameRate;
   //缓冲区
   private BufferStrategy bs;
   //游戏循环控制
   private volatile boolean running;
   //游戏循环线程
   private Thread gameThread;
   //图形设备
   private GraphicsDevice graphicsDevice;
   //当前显示模式
   private DisplayMode currentDisplayMode;

   /**
    * 初始化帧工具对象
    */
   public FullScreenRenderingExample() {
      frameRate = new FrameRate();
   }
   /**
    * 创建并显示窗口
    */
   protected void createAndShowGUI() {
      //忽略刷新
      setIgnoreRepaint( true );
      //显示装饰
      setUndecorated( true );
      //设置背景颜色
      setBackground( Color.BLACK );
      //获得当前图形环境
      GraphicsEnvironment ge = 
         GraphicsEnvironment.getLocalGraphicsEnvironment();
      graphicsDevice = ge.getDefaultScreenDevice();
      //初始化当前显示模式
      currentDisplayMode = graphicsDevice.getDisplayMode();
      //是否支持全屏
      if( !graphicsDevice.isFullScreenSupported() ) {
         System.err.println( "ERROR: Not Supported!!!" );
         System.exit( 0 );
      }
      //设置当前窗口全屏
      graphicsDevice.setFullScreenWindow( this );
      //获得默认的显示模式 
      graphicsDevice.setDisplayMode( getDisplayMode() );
      //创建缓冲区
      createBufferStrategy( 2 );
      //获得缓冲区
      bs = getBufferStrategy();
      //添加监听键盘esc
      addKeyListener( new KeyAdapter() {
         public void keyPressed( KeyEvent e ) {
            if( e.getKeyCode() == KeyEvent.VK_ESCAPE ) {
               //关闭程序
            	shutDown();
            }
         }
      });
      //创建并启动游戏线程
      gameThread = new Thread( this );
      gameThread.start();
   }
   /**
    * 获得默认的显示模式
    * @return
    */
   private DisplayMode getDisplayMode() {
	   // Make sure to use a display mode for your system.
	   // The DisplayMode.REFRESH_RATE_UNKNOWN and
	   // DisplayMode.BIT_DEPTH_MULTI flags may be required.
      return new DisplayMode( 
         800, 600, 32, DisplayMode.REFRESH_RATE_UNKNOWN );
   }
   /**
    * 游戏线程
    */
   public void run() {
	  //运行状态
      running = true;
      //初始化帧工具
      frameRate.initialize();
      while( running ) {
    	  //游戏循环
         gameLoop();
      }
   }
   /**
    * 游戏主循环
    */
   public void gameLoop() {
      do {
         do {
            Graphics g = null;
            try {
            	//获取图形设备句柄
               g = bs.getDrawGraphics();
               //清空缓冲区
               g.clearRect( 0, 0, getWidth(), getHeight() );
               //渲染
               render( g );
            } finally {
               if( g != null ) {
                  g.dispose();
               }
            }
         } while( bs.contentsRestored() );
         //copy 到显示窗口
         bs.show();
      } while( bs.contentsLost() );
   }
   /**
    * 渲染
    * @param g
    */
   private void render( Graphics g ) {
	   //计算帧率
      frameRate.calculate();
      //设置前景色
      g.setColor( Color.GREEN );
      //绘制文本
      g.drawString( frameRate.getFrameRate(), 30, 30 );
      g.drawString( "Press ESC to exit...", 30, 60 );
   }
   //关闭程序
   protected void shutDown() {
      try {
    	  //游戏线程关闭
         running = false;
         //等待关闭
         gameThread.join();
         System.out.println( "Game loop stopped!!!" );
         //恢复显示模式
         graphicsDevice.setDisplayMode( currentDisplayMode );
         //清空全屏显示窗口
         graphicsDevice.setFullScreenWindow( null );
         System.out.println("Display Restored...");
      } catch( InterruptedException e ) {
         e.printStackTrace();
      }
      System.exit( 0 );
   }
   
   public static void main( String[] args ) {
      final FullScreenRenderingExample app = new FullScreenRenderingExample();
      SwingUtilities.invokeLater( new Runnable() {
         public void run() {
            app.createAndShowGUI();
         }
      });
   }

}
