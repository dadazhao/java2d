package javagames.render;

import java.awt.*;
import javax.swing.*;
import javagames.util.*;
/**
 * ��һ��ʵ��
 */
public class HelloWorldApp extends JFrame {
   
	//֡�ʼ��㹤��
   private FrameRate frameRate;
   
   //���캯�� ��ʼ��֡�ʹ���
   public HelloWorldApp() {
      frameRate = new FrameRate();
   }
   
   /**
    * ��������ʾ���ڳ���
    */
   protected void createAndShowGUI() {
	  //������Ϸ���
      GamePanel gamePanel = new GamePanel();
      //���ñ�����ɫ
      gamePanel.setBackground( Color.WHITE );
      //��������С
      gamePanel.setPreferredSize( new Dimension( 320, 240 ) );
      //�������ӵ�������
      getContentPane().add( gamePanel );
      //���ô��ڵ�Ĭ�ϰ�ť����
      setDefaultCloseOperation( EXIT_ON_CLOSE );
      //���ڱ���
      setTitle( "Hello World!" );
      //�������Ӧ���ڴ�С
      pack();
      //֡�ʹ��߳�ʼ��
      frameRate.initialize();
      //���ڿɼ�
      setVisible( true );
   }
   
   /**
    * ������Ϸ�����
    * @author AcmenZhao
    *
    */
   private class GamePanel extends JPanel {
	   //��д���Ʒ���
       public void paint( Graphics g ) {
         super.paint( g );
         //�Զ�����Ʒ���
         onPaint( g );
      }
   }
   /**
    * �Զ�����Ʒ���
    * @param g
    */
   protected void onPaint( Graphics g ) {
	   //����֡��
      frameRate.calculate();
      //����ǰ��ɫ
      g.setColor( Color.BLACK );
      //��������
      g.drawString( frameRate.getFrameRate(), 30, 30 );
      //����ػ�
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
