package javagames.render;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * 显示模式实例
 * @author AcmenZhao
 *
 */
public class DisplayModeExample extends JFrame {
   
	/**
	 * 显示模式内部包装类
	 * @author AcmenZhao
	 *
	 */
   class DisplayModeWrapper {
      
	   //显示模式
      private DisplayMode dm;
      //包装类构造函数
      public DisplayModeWrapper( DisplayMode dm ) {
         this.dm = dm;
      }
      //比较显示宽高
      public boolean equals( Object obj ) {
         DisplayModeWrapper other = (DisplayModeWrapper)obj;
         if( dm.getWidth() != other.dm.getWidth() )
            return false;
         if( dm.getHeight() != other.dm.getHeight() )
            return false;
         return true;
      }

      public String toString() {
         return "" + dm.getWidth() + " x " + dm.getHeight();
      }
   }
   //下拉选择框
   private JComboBox displayModes;
   //图形设备
   private GraphicsDevice graphicsDevice;
   //当前显示模式
   private DisplayMode currentDisplayMode;
   /**
    * 构造器初始化图形环境
    */
   public DisplayModeExample() {
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      graphicsDevice = ge.getDefaultScreenDevice();
      currentDisplayMode = graphicsDevice.getDisplayMode();
   }
   /**
    * 获得图形面板
    * @return
    */
   private JPanel getMainPanel() {
      JPanel p = new JPanel();
      //创建下拉选择框
      displayModes = new JComboBox( listDisplayModes() );
      //下拉选择框添加到面板
      p.add( displayModes );
      //创建全屏按钮
      JButton enterButton = new JButton( "Enter Full Screen" );
      //添加事件监听
      enterButton.addActionListener( new ActionListener() {
         public void actionPerformed( ActionEvent e ) {
            onEnterFullScreen();
         }
      });
      //按钮添加到面板
      p.add( enterButton );
      //创建推出全屏按钮
      JButton exitButton = new JButton( "Exit Full Screen" );
      //添加事件监听
      exitButton.addActionListener( new ActionListener() {
         public void actionPerformed( ActionEvent e ) {
            onExitFullScreen();
         }
      });
      //按钮添加到面板
      p.add( exitButton );
      return p;
   }
   /**
    * 获得显示模式列表
    * @return
    */
   private DisplayModeWrapper[] listDisplayModes() {
      ArrayList<DisplayModeWrapper> list = new ArrayList<DisplayModeWrapper>();
      for( DisplayMode mode : graphicsDevice.getDisplayModes() ) {
         //if( mode.getBitDepth() == 32 ) { // linux bug-fix Jan 2015
            DisplayModeWrapper wrap = new DisplayModeWrapper( mode );
            if( !list.contains( wrap ) ) {
               list.add( wrap );
            }
         //}
      }
      return list.toArray( new DisplayModeWrapper[0] );
   }
   /**
    * 创建并显示窗口
    */
   protected void createAndShowGUI() {
	  //获得canvas
      Container canvas = getContentPane();
      //将panel添加到canvas
      canvas.add( getMainPanel() );
      //忽略重回
      canvas.setIgnoreRepaint( true );
      //设置window operation
      setDefaultCloseOperation( EXIT_ON_CLOSE );
      //设置标题
      setTitle( "Display Mode Test" );
      //自适应大小
      pack();
      //设置可见
      setVisible( true );
   }
   /**
    * 执行全屏方法
    */
   protected void onEnterFullScreen() {
	   //查看设备是否支持全屏
      if( graphicsDevice.isFullScreenSupported() ) {
    	  //获得用户选择的模式
         DisplayMode newMode = getSelectedMode();
         //设置要全屏的窗口
         graphicsDevice.setFullScreenWindow( this );
         //设置显示模式
         graphicsDevice.setDisplayMode( newMode );
      } 
   }
   /**
    * 执行退出全屏方法
    */
   protected void onExitFullScreen() {
	   //恢复之前的显示模式
      graphicsDevice.setDisplayMode( currentDisplayMode );
      //清空要设置全屏的窗口
      graphicsDevice.setFullScreenWindow( null );
   }

   /**
    * 获得用户选择的显示模式
    * @return
    */
   protected DisplayMode getSelectedMode() {
	   //从下拉选择框获得选择的项
      DisplayModeWrapper wrapper = 
         (DisplayModeWrapper)displayModes.getSelectedItem();
      //从包装类中获取显示模式
      DisplayMode dm = wrapper.dm;
      //显示宽
      int width = dm.getWidth();
      //显示高
      int height = dm.getHeight();
      //
      int bit = dm.getBitDepth(); // linux Bug-Fix Jan 2015
      //刷新率模式
      int refresh = DisplayMode.REFRESH_RATE_UNKNOWN;
      return new DisplayMode( width, height, bit, refresh );
   }
   
   /**
    * 主函数 
    * @param args
    */
   public static void main( String[] args ) {
      final DisplayModeExample app = new DisplayModeExample();
      SwingUtilities.invokeLater( new Runnable() {
         public void run() {
            app.createAndShowGUI();
         }
      });
   }
}
