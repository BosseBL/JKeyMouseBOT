import java.awt.AWTException;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.PrintStream;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;











public class JkeyBOT
  extends JFrame
  implements NativeKeyListener, NativeMouseListener, WindowListener
{
  public JTextField f1Field;
  public JTextField f2Field;
  public JTextField f3Field;
  public JSlider delay;
  public JLabel msLabel;
  public JLabel message;
  private JPanel f1Panel;
  private JPanel f2Panel;
  private JPanel f3Panel;
  private JPanel infoPanel;
  private JPanel messagePanel;
  private JLabel info1;
  private JLabel info2;
  private JLabel info3;
  private JPanel bottomPanel;
  public KeyBot robot;
  public MouseBot mrobot;
  Thread mT;
  long time;
  
  JkeyBOT()
  {
    super("JkeyBot");
    
    msLabel = new JLabel("delay:");
    delay = new JSlider(0, 1, 100, 100);
    delay.setPaintTicks(true);
    delay.setPaintLabels(true);
    
    f1Field = new JTextField(20);
    f1Field.setEditable(true);
    
    f2Field = new JTextField(20);
    f2Field.setEditable(true);
    
    f3Field = new JTextField(20);
    f3Field.setEditable(true);
    
    info1 = new JLabel("F4: start/stop mouse recording\n");
    info2 = new JLabel("F5: start/stop mouse playback    ");
    info3 = new JLabel("ECAPE: shut down program");
    
    message = new JLabel("...");
    
    f1Panel = new JPanel();
    f1Panel.add(new JLabel("F1"));
    f1Panel.add(f1Field);
    
    f2Panel = new JPanel();
    f2Panel.add(new JLabel("F2"));
    f2Panel.add(f2Field);
    
    f3Panel = new JPanel();
    f3Panel.add(new JLabel("F3"));
    f3Panel.add(f3Field);
    
    bottomPanel = new JPanel();
    bottomPanel.add(msLabel);
    bottomPanel.add(delay);
    
    infoPanel = new JPanel();
    infoPanel.setLayout(new FlowLayout());
    infoPanel.add(info1);
    infoPanel.add(info2);
    infoPanel.add(info3);
    

    messagePanel = new JPanel();
    messagePanel.setBackground(Color.WHITE);
    messagePanel.add(message);
    
    super.setLayout(new FlowLayout());
    super.add(f1Panel);
    super.add(f2Panel);
    super.add(f3Panel);
    super.add(new JLabel("Status: "));
    super.add(messagePanel);
    super.add(bottomPanel);
    
    super.add(info1);
    super.add(info2);
    super.add(info3);
    

    super.setDefaultCloseOperation(3);
    super.setSize(320, 350);
    super.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    super.setResizable(false);
    super.setVisible(true);
    try
    {
      robot = new KeyBot();
      mrobot = new MouseBot();
    } catch (AWTException e) {
      System.out.println("couldn't create KeyBot");
      e.printStackTrace();
    }
    
    addWindowListener(this);
    
    mT = null;
    time = 0L;
  }
  
  public static void main(String[] args) {
    try {
      
    } catch (NativeHookException e1) {
      System.out.println("Couldn't register hook");
      e1.printStackTrace();
      System.exit(1);
    }
    
    JkeyBOT bot = new JkeyBOT();
    GlobalScreen.getInstance().addNativeKeyListener(bot);
    GlobalScreen.getInstance().addNativeMouseListener(bot);
  }
  

  public void nativeKeyPressed(NativeKeyEvent e)
  {
    if (e.getKeyCode() == 27) {
      System.out.println("shuting down");
      GlobalScreen.unregisterNativeHook();
      System.out.println("hook is unregistered");
      System.exit(0);
    }
    
    if (e.getKeyCode() == 112) {
      robot.typeString(f1Field.getText(), delay.getValue());
    }
    if (e.getKeyCode() == 113) {
      robot.typeString(f2Field.getText(), delay.getValue());
    }
    if (e.getKeyCode() == 114) {
      robot.typeString(f3Field.getText(), delay.getValue());
    }
    if (e.getKeyCode() == 115)
    {
      if (mrobot.isRecording()) {
        mrobot.stopRecording();
        mrobot.addFirstTime(System.currentTimeMillis() - time);
        message.setText("...");
      }
      else {
        mrobot.startRecording();
        message.setText("recording");
      }
      time = 0L;
    }
    
    if (e.getKeyCode() == 116) {
      if (mrobot.isClicking()) {
        mrobot.stopClicking();
        message.setText("...");

      }
      else if (mrobot.hasRecording()) {
        mrobot.startClicking(delay.getValue());
        message.setText("playing");
        mT = new Thread(mrobot);
        mT.start();
      }
      else {
        message.setText("nothing is recorded");
      }
    }
  }
  

  public void nativeKeyReleased(NativeKeyEvent e) {}
  
  public void nativeKeyTyped(NativeKeyEvent e) {}
  
  public void windowClosing(WindowEvent e)
  {
    System.out.println("closing");
    GlobalScreen.unregisterNativeHook();
    System.out.println("hook is unregistered");
    System.exit(0); }
  
  public void windowActivated(WindowEvent arg0) {}
  
  public void windowClosed(WindowEvent arg0) {}
  
  public void windowDeactivated(WindowEvent arg0) {}
  
  public void windowDeiconified(WindowEvent arg0) {}
  
  public void windowIconified(WindowEvent arg0) {}
  
  public void windowOpened(WindowEvent arg0) {}
  public void nativeMouseClicked(NativeMouseEvent e) { if (mrobot.isRecording())
    {
      try
      {
        if (e.getButton() == 1) { long t;
          long t; if (time == 0L) {
            t = 0L;
          }
          else {
            t = System.currentTimeMillis() - time;
          }
          
          mrobot.saveClick(16, e.getX(), e.getY(), t);
          time = System.currentTimeMillis();
        }
        

        if (e.getButton() == 2) { long t;
          long t; if (time == 0L) {
            t = 0L;
          }
          else {
            t = System.currentTimeMillis() - time;
          }
          mrobot.saveClick(8, e.getX(), e.getY(), t);
          time = System.currentTimeMillis();
        }
      }
      catch (Exception ex) {
        System.out.println(ex.getMessage());
      }
    }
  }
  
  public void nativeMousePressed(NativeMouseEvent arg0) {}
  
  public void nativeMouseReleased(NativeMouseEvent arg0) {}
}
