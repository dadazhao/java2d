package javagames.render;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javagames.util.*;

/**
 * ȫ����Ⱦʵ��
 * @author AcmenZhao
 *
 */
public class FullScreenRenderingExample extends JFrame implements Runnable {
   //֡�ʹ���
   private FrameRate frameRate;
   //������
   private BufferStrategy bs;
   //��Ϸѭ������
   private volatile boolean running;
   //��Ϸѭ���߳�
   private Thread gameThread;
   //ͼ���豸
   private GraphicsDevice graphicsDevice;
   //��ǰ��ʾģʽ
   private DisplayMode currentDisplayMode;

   /**
    * ��ʼ��֡���߶���
    */
   public FullScreenRenderingExample() {
      frameRate = new FrameRate();
   }
   /**
    * ��������ʾ����
    */
   protected void createAndShowGUI() {
      //����ˢ��
      setIgnoreRepaint( true );
      //��ʾװ��
      setUndecorated( true );
      //���ñ�����ɫ
      setBackground( Color.BLACK );
      //��õ�ǰͼ�λ���
      GraphicsEnvironment ge = 
         GraphicsEnvironment.getLocalGraphicsEnvironment();
      graphicsDevice = ge.getDefaultScreenDevice();
      //��ʼ����ǰ��ʾģʽ
      currentDisplayMode = graphicsDevice.getDisplayMode();
      //�Ƿ�֧��ȫ��
      if( !graphicsDevice.isFullScreenSupported() ) {
         System.err.println( "ERROR: Not Supported!!!" );
         System.exit( 0 );
      }
      //���õ�ǰ����ȫ��
      graphicsDevice.setFullScreenWindow( this );
      //���Ĭ�ϵ���ʾģʽ 
      graphicsDevice.setDisplayMode( getDisplayMode() );
      //����������
      createBufferStrategy( 2 );
      //��û�����
      bs = getBufferStrategy();
      //��Ӽ�������esc
      addKeyListener( new KeyAdapter() {
         public void keyPressed( KeyEvent e ) {
            if( e.getKeyCode() == KeyEvent.VK_ESCAPE ) {
               //�رճ���
            	shutDown();
            }
         }
      });
      //������������Ϸ�߳�
      gameThread = new Thread( this );
      gameThread.start();
   }
   /**
    * ���Ĭ�ϵ���ʾģʽ
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
    * ��Ϸ�߳�
    */
   public void run() {
	  //����״̬
      running = true;
      //��ʼ��֡����
      frameRate.initialize();
      while( running ) {
    	  //��Ϸѭ��
         gameLoop();
      }
   }
   /**
    * ��Ϸ��ѭ��
    */
   public void gameLoop() {
      do {
         do {
            Graphics g = null;
            try {
            	//��ȡͼ���豸���
               g = bs.getDrawGraphics();
               //��ջ�����
               g.clearRect( 0, 0, getWidth(), getHeight() );
               //��Ⱦ
               render( g );
            } finally {
               if( g != null ) {
                  g.dispose();
               }
            }
         } while( bs.contentsRestored() );
         //copy ����ʾ����
         bs.show();
      } while( bs.contentsLost() );
   }
   /**
    * ��Ⱦ
    * @param g
    */
   private void render( Graphics g ) {
	   //����֡��
      frameRate.calculate();
      //����ǰ��ɫ
      g.setColor( Color.GREEN );
      //�����ı�
      g.drawString( frameRate.getFrameRate(), 30, 30 );
      g.drawString( "Press ESC to exit...", 30, 60 );
   }
   //�رճ���
   protected void shutDown() {
      try {
    	  //��Ϸ�̹߳ر�
         running = false;
         //�ȴ��ر�
         gameThread.join();
         System.out.println( "Game loop stopped!!!" );
         //�ָ���ʾģʽ
         graphicsDevice.setDisplayMode( currentDisplayMode );
         //���ȫ����ʾ����
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
