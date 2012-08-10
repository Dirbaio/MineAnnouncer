package mineannouncer;

import java.net.*;
import java.util.logging.*;

public class AnnouncerThread extends Thread
{
    public boolean mustStop = false;

    String data;
    byte[] buf;
    MainFrame mf;
    
    public AnnouncerThread(String data, MainFrame mf)
    {
        this.data = data;
        this.buf = data.getBytes();
        this.mf = mf;
    }
    
    @Override
    public void run()
    {
        DatagramSocket socket = null;
        
        try
        {
            socket = new DatagramSocket();
            while(true)
            {
                if(mustStop) return;
                
                socket.send(new DatagramPacket(buf, buf.length, Inet4Address.getByName("224.0.2.60"), 4445));
                
                try
                {
                    Thread.sleep(2000);
                }
                catch (InterruptedException ex)
                {
                    Logger.getLogger(AnnouncerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        catch (Exception ex)
        {
            mf.errorOccured(ex.getMessage());
        }
        
        if(socket != null)
            socket.close();
    }
    
}
