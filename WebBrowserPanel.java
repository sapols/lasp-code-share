import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;

public class WebBrowserPanel extends JPanel
{
    private WebBrowserModel model;
    private WebBrowserFrame frame;
    
    private JLabel barLabel;
    private JTextField urlBar;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    
    public WebBrowserPanel(WebBrowserModel m, WebBrowserFrame f)
    {
        model = m;
        frame = f;
        
        barLabel = new JLabel("URL: ");
        urlBar = new JTextField(45); 
        urlBar.addActionListener(new ActionListener() 
        {
           public void actionPerformed(ActionEvent e)
           {
               try
               {
                   model.connectFromURL(urlBar.getText());
               }
               catch (UnknownHostException ex)
               {
                   JOptionPane.showMessageDialog(frame, "Incorrect URL. Unknown host.");
                   urlBar.setText("");
               }
               catch (IOException ex)
               {
                   JOptionPane.showMessageDialog(frame, "Incorrect URL. IOException occurred.");
                   urlBar.setText("");
               }
               catch (Exception ex)
               {
                   JOptionPane.showMessageDialog(frame, "Incorrect URL.");
                   urlBar.setText("");
               }
          
               model.extractTitle();
               model.extractBody();
               frame.setTitle(model.getTitle());
               frame.repaint();
           }
        });
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setPreferredSize(new Dimension((int)(frame.getWidth()/1.4), (int)(frame.getHeight()/1.4)));
        scrollPane = new JScrollPane(textArea);
        scrollPane.setSize(new Dimension((int)(frame.getWidth()/1.4), (int)(frame.getHeight()/1.4)));
        add(barLabel, BorderLayout.PAGE_START);
        add(urlBar, BorderLayout.PAGE_START);
        add(scrollPane,BorderLayout.CENTER);
                    
    }
    
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        textArea.setSize(new Dimension((int)(frame.getWidth()/1.4), (int)(frame.getHeight()/1.4)));
        scrollPane.setSize(new Dimension((int)(frame.getWidth()/1.4), (int)(frame.getHeight()/1.4)));
        textArea.setText(model.getOutputString());
        
    }
}
