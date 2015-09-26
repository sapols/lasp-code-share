import java.awt.*;
import javax.swing.*;

public class WebBrowserFrame extends JFrame
{
    private WebBrowserModel model;
    
    public WebBrowserFrame()
    {
        setTitle("Shawn's Browser");
        model = new WebBrowserModel();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setSize(screenSize.width/2, (int)(screenSize.height/1.5));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new WebBrowserPanel(model, this));
        setVisible(true);
    }
}
