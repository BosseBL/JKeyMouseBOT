import java.awt.AWTException;
import java.awt.Robot;
import java.io.PrintStream;
import java.util.ArrayList;



























public class MouseBot
  extends Robot
  implements Runnable
{
  private boolean isRecording;
  private ArrayList<MouseBot.MouseClick> clicklist;
  private boolean isClicking;
  
  public MouseBot()
    throws AWTException
  {
    clicklist = new ArrayList();
  }
  

  public void startClicking(int ms)
  {
    isClicking = true;
    isRecording = false;
    super.setAutoDelay(ms);
  }
  
  public void stopClicking() {
    isClicking = false;
    isRecording = false;
  }
  
  public boolean isClicking() {
    return isClicking;
  }
  
  public boolean isRecording() {
    return isRecording;
  }
  
  public void startRecording() {
    clicklist.clear();
    isRecording = true;
    isClicking = false;
    System.out.println("start recording");
  }
  
  public void stopRecording() {
    isRecording = false;
    isClicking = false;
    System.out.println("stop recording");
  }
  
  public void saveClick(int button, int x, int y, long time) throws Exception {
    if (isRecording) {
      clicklist.add(new MouseBot.MouseClick(this, button, x, y, time));
      System.out.println("added a mouse click");
    } else {
      throw new Exception("tried to save click while KeyBot is not recording");
    }
  }
  
  public boolean hasRecording() { if (clicklist.size() > 0) return true;
    return false;
  }
  

  public void run()
  {
    System.out.println("start clicking");
    int i = 0;
    long delay = super.getAutoDelay();
    super.setAutoDelay(5);
    while (isClicking)
    {
      super.mouseMove(((MouseBot.MouseClick)clicklist.get(i)).getX(), ((MouseBot.MouseClick)clicklist.get(i)).getY());
      super.mousePress(((MouseBot.MouseClick)clicklist.get(i)).getButton());
      super.mouseRelease(((MouseBot.MouseClick)clicklist.get(i)).getButton());
      
      System.out.println("made a click");
      try {
        Thread.sleep(((float)delay / 100.0F * (float)((MouseBot.MouseClick)clicklist.get(i)).getTime()));
      }
      catch (InterruptedException e) {
        System.out.println("failed sleep");
        e.printStackTrace();
      }
      if (i < clicklist.size() - 1) {
        i++;
      }
      else {
        i = 0;
      }
    }
    System.out.println("stop clicking");
    isClicking = false;
  }
  
  public void addFirstTime(long t) {
    MouseBot.MouseClick mc = (MouseBot.MouseClick)clicklist.get(0);
    mc.setTime(t);
    clicklist.set(0, mc);
  }
}
