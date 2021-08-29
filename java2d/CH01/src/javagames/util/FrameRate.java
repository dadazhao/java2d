package javagames.util;
/**
 * ֡�ʼ��㹤��
 * @author AcmenZhao
 *
 */
public class FrameRate {
   
   /*
    * ��ʽ���ַ���
    */
   private String frameRate;
   /*
    * ����ʱ��
    */
   private long lastTime;
   /*
    * ƫ�Ƶ�ʱ��
    */
   private long delta;
   /*
    * ֡��
    */
   private int frameCount;

   /**
    * ��ʼ��
    */
   public void initialize() {
      lastTime = System.currentTimeMillis();
      frameRate = "FPS 0";
   }
   /**
    * ����֡��
    * �ۼ�ÿ����Ⱦ��ʱ�䣬ÿ��Ⱦһ�μ�����һ
    * ����Ⱦ��ʱ�����һ��ʱ���ü���
    */
   public void calculate() {
      long current = System.currentTimeMillis();
      delta += current - lastTime;
      lastTime = current;
      frameCount++;
      if( delta > 1000 ) {
         delta -= 1000;
         frameRate = String.format( "FPS %s", frameCount );
         frameCount = 0;
      }
   }
   
   public String getFrameRate() {
      return frameRate;
   }
}
