package javagames.util;
/**
 * 帧率计算工具
 * @author AcmenZhao
 *
 */
public class FrameRate {
   
   /*
    * 格式话字符串
    */
   private String frameRate;
   /*
    * 最后的时间
    */
   private long lastTime;
   /*
    * 偏移的时间
    */
   private long delta;
   /*
    * 帧数
    */
   private int frameCount;

   /**
    * 初始化
    */
   public void initialize() {
      lastTime = System.currentTimeMillis();
      frameRate = "FPS 0";
   }
   /**
    * 计算帧率
    * 累计每次渲染的时间，每渲染一次计数加一
    * 当渲染的时间大于一秒时重置计数
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
