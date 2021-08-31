package javagames.render;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * ��ʾģʽʵ��
 * @author AcmenZhao
 *
 */
public class DisplayModeExample extends JFrame {
   
	/**
	 * ��ʾģʽ�ڲ���װ��
	 * @author AcmenZhao
	 *
	 */
   class DisplayModeWrapper {
      
	   //��ʾģʽ
      private DisplayMode dm;
      //��װ�๹�캯��
      public DisplayModeWrapper( DisplayMode dm ) {
         this.dm = dm;
      }
      //�Ƚ���ʾ���
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
   //����ѡ���
   private JComboBox displayModes;
   //ͼ���豸
   private GraphicsDevice graphicsDevice;
   //��ǰ��ʾģʽ
   private DisplayMode currentDisplayMode;
   /**
    * ��������ʼ��ͼ�λ���
    */
   public DisplayModeExample() {
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      graphicsDevice = ge.getDefaultScreenDevice();
      currentDisplayMode = graphicsDevice.getDisplayMode();
   }
   /**
    * ���ͼ�����
    * @return
    */
   private JPanel getMainPanel() {
      JPanel p = new JPanel();
      //��������ѡ���
      displayModes = new JComboBox( listDisplayModes() );
      //����ѡ�����ӵ����
      p.add( displayModes );
      //����ȫ����ť
      JButton enterButton = new JButton( "Enter Full Screen" );
      //����¼�����
      enterButton.addActionListener( new ActionListener() {
         public void actionPerformed( ActionEvent e ) {
            onEnterFullScreen();
         }
      });
      //��ť��ӵ����
      p.add( enterButton );
      //�����Ƴ�ȫ����ť
      JButton exitButton = new JButton( "Exit Full Screen" );
      //����¼�����
      exitButton.addActionListener( new ActionListener() {
         public void actionPerformed( ActionEvent e ) {
            onExitFullScreen();
         }
      });
      //��ť��ӵ����
      p.add( exitButton );
      return p;
   }
   /**
    * �����ʾģʽ�б�
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
    * ��������ʾ����
    */
   protected void createAndShowGUI() {
	  //���canvas
      Container canvas = getContentPane();
      //��panel��ӵ�canvas
      canvas.add( getMainPanel() );
      //�����ػ�
      canvas.setIgnoreRepaint( true );
      //����window operation
      setDefaultCloseOperation( EXIT_ON_CLOSE );
      //���ñ���
      setTitle( "Display Mode Test" );
      //����Ӧ��С
      pack();
      //���ÿɼ�
      setVisible( true );
   }
   /**
    * ִ��ȫ������
    */
   protected void onEnterFullScreen() {
	   //�鿴�豸�Ƿ�֧��ȫ��
      if( graphicsDevice.isFullScreenSupported() ) {
    	  //����û�ѡ���ģʽ
         DisplayMode newMode = getSelectedMode();
         //����Ҫȫ���Ĵ���
         graphicsDevice.setFullScreenWindow( this );
         //������ʾģʽ
         graphicsDevice.setDisplayMode( newMode );
      } 
   }
   /**
    * ִ���˳�ȫ������
    */
   protected void onExitFullScreen() {
	   //�ָ�֮ǰ����ʾģʽ
      graphicsDevice.setDisplayMode( currentDisplayMode );
      //���Ҫ����ȫ���Ĵ���
      graphicsDevice.setFullScreenWindow( null );
   }

   /**
    * ����û�ѡ�����ʾģʽ
    * @return
    */
   protected DisplayMode getSelectedMode() {
	   //������ѡ�����ѡ�����
      DisplayModeWrapper wrapper = 
         (DisplayModeWrapper)displayModes.getSelectedItem();
      //�Ӱ�װ���л�ȡ��ʾģʽ
      DisplayMode dm = wrapper.dm;
      //��ʾ��
      int width = dm.getWidth();
      //��ʾ��
      int height = dm.getHeight();
      //
      int bit = dm.getBitDepth(); // linux Bug-Fix Jan 2015
      //ˢ����ģʽ
      int refresh = DisplayMode.REFRESH_RATE_UNKNOWN;
      return new DisplayMode( width, height, bit, refresh );
   }
   
   /**
    * ������ 
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
