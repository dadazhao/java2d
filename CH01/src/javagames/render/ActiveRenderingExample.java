package javagames.render;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javagames.util.*;

/**
 * ������Ⱦʵ��
 * @author AcmenZhao
 *
 */
public class ActiveRenderingExample extends JFrame implements Runnable {
   //֡�ʼ��㹤��
   private FrameRate frameRate;
   //������
   private BufferStrategy bs;
   //���п���
   private volatile boolean running;
   //��Ⱦ�߳�
   private Thread gameThread;
   
   /**
    * ���캯�� ��ʼ��֡�ʼ��㹤��
    */
   public ActiveRenderingExample() {
      frameRate = new FrameRate();
   }
   
   /**
    * ��������ʾ����
    */
   protected void createAndShowGUI() {
      //����Canvas��Ϸ���
      Canvas canvas = new Canvas();
      //��������С
      canvas.setSize( 320, 240 );
      //������屳����ɫ
      canvas.setBackground( Color.BLACK );
      //�ر�����Զ�ˢ��
      canvas.setIgnoreRepaint( true );
      //�������ӵ�����
      getContentPane().add( canvas );
      //���ô��ڱ���
      setTitle( "Active Rendering" );
      //�رմ����Զ�ˢ��
      setIgnoreRepaint( true );
      //�������Ӧ���ڴ�С
      pack();
      //���ڿɼ�
      setVisible( true );
      //���û������ĸ���
      canvas.createBufferStrategy( 2 );
      //��û���ȥ
      bs = canvas.getBufferStrategy();
      //��ʼ����Ϸ�����߳�
      gameThread = new Thread( this );
      //������Ϸ�߳�
      gameThread.start();
   }
   
   //�߳�ִ�еķ���
   public void run() {
	   //����
      running = true;
      //��ʼ��֡�ʼ��㹤��
      frameRate.initialize();
      //��Ϸѭ��
      while( running ) {
         gameLoop();
      }
   }
   
   /**
    * ��Ϸѭ������
    */
   public void gameLoop() {
      do {
         do {
            Graphics g = null;
            try {
            	//��ȡCanvas��������ͼ�ξ��
               g = bs.getDrawGraphics();
               //���������
               g.clearRect( 0, 0, getWidth(), getHeight() );
               //��Ⱦ
               render( g );
            } finally {
               if( g != null ) {
            	  //�ͷž��
                  g.dispose();
               }
            }
            
         } while( bs.contentsRestored() );
         //���Ƶ��Ӵ�
         bs.show();
       //�����������
      } while( bs.contentsLost() );
   }
   //��Ⱦ
   private void render( Graphics g ) {
	   //֡�ʼ���
      frameRate.calculate();
      //������ɫ
      g.setColor( Color.GREEN );
      //����֡���ı�
      g.drawString( frameRate.getFrameRate(), 30, 30 );
   }
   
   /**
    * �رմ���
    */
   protected void onWindowClosing() {
      try {
    	  //ֹͣ��Ϸѭ��
         running = false;
         //�ȴ�ѭ������
         gameThread.join();
      } catch( InterruptedException e ) {
         e.printStackTrace();
      }
      //�˳�
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
