import java.io.*;
import java.net.*;

public class WebBrowserModel 
{
    private String outputStr = "";
    private String url;
    private Socket socket;
    private String title = "Shawn's Browser";
    
    public String getOutputString()
    {
        return outputStr;
    }
    
    public String getURL()
    {
        return url;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setOutputString(String out)
    {
        outputStr = out;
    }
    
    public void setSocket(Socket s)
    {
        socket = s;
    }
    
    public void setURL(String u)
    {
        url = u;
        enforcePrefix();
    }
       
    private void enforcePrefix()  
    {
        if (url.length() > 7)
        {
            if (url.substring(0, 7).equals("http://"))
            {
                //nothing
            }
            else
            {
                url = "http://" + url;
            }
        }
        else
        {
            url = "http://" + url;
        }
    }
    
    public void connectFromURL(String u) throws UnknownHostException, IOException, Exception
    {
        setURL(u);
        try
        {
           setSocket(getSocketFromUrl());
        }
        catch (UnknownHostException e)
        {
            throw e; 
        }
        catch (IOException e)
        {
            throw e;
        }
        
        try
        {
            assignOutputString();
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    
    public Socket getSocketFromUrl() throws UnknownHostException, IOException 
    {
        String trimmedUrl = getHostFromURL();
        try
        {
            Socket s = new Socket(trimmedUrl, 80);
            return s;
        }
        catch (UnknownHostException e)
        {
            throw e;
        }
        catch (IOException e)
        {
            throw e;
        }
    }
    
    public void extractTitle()
    {
        String t = "";
        for (int x = 0; x < outputStr.length(); x++)
        {
            if (outputStr.charAt(x) == '<')
            {
                if (outputStr.substring(x+1, x+7).equals("title>"))
                {
                    x = x+7;
                    while (outputStr.charAt(x) != '<')
                    {
                        t+= outputStr.charAt(x);
                        x++;
                    }
                    break;
                }
            }
        }
        
        title = t;
    }
    
    public void extractBody()
    {
        String b = "";

        for (int x = 0; x < outputStr.length(); x++)
        {
            if (outputStr.charAt(x) == '<')
            {
                if (outputStr.substring(x+1, x+5).equals("body"))
                {
                    x = x+5;
                    while (outputStr.charAt(x) != '>')
                    {
                        x++;
                    }
                    x++;
 
                    while (!outputStr.substring(x, x+7).equals("</body>"))
                    {
                        b += outputStr.charAt(x);
                        x++;
                    }
                    break;
                }
            }
        }
        
        outputStr = b;
    }
    
    public String generateHTTPRequestText() throws UnknownHostException, IOException
    {
        String text = "GET ";
        String filePath;
        String webAddress;

        filePath = getFilePathFromURL();
        webAddress = getHostFromURL();
        
        text += filePath + " HTTP/1.1\r\n";
        text += "Host: " + webAddress + "\r\n";
        text += "\r\n";    
        
        return text;
    }
    
    public String getFilePathFromURL() 
    {
        String path = ""; 
        boolean slashExisted = false;
        
        for (int x = 7; x < url.length(); x++)
        {
            if (url.charAt(x) == '/')
            {
                slashExisted = true;
                for (int i = x; i < url.length(); i++)
                {
                    path += url.charAt(i);
                }
                break;
            }
            
        }
        
        if (slashExisted == false)
        {
            path = "/";
        }
        
        return path;
    }
    
    public String getHostFromURL()
    {
        String host = "";
        
        for (int x = 7; x < url.length(); x++)
        {
            if (url.charAt(x) != '/')
            {
                host += url.charAt(x);
            }
            else
            {
                break;
            }
        }
        
        return host;
    }
    
    public void assignOutputString() throws Exception
    {
        String output = "";
        String line;
        
        try
        {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.print(generateHTTPRequestText());
            out.flush();
            while ((line = in.readLine()) != null)
            {
                output += line + "\n";
            }
     
            extractBody();
        }
        catch (Exception e)
        {
            throw e;
        }
        
        outputStr = output;
    }
}
