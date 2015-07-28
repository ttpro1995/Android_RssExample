package com.hahattpro.meowdebughelper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;

public class CopyInputStream
{
    private InputStream _is;
    private ByteArrayOutputStream _copy = new ByteArrayOutputStream();

    /**
     *
     */
    public CopyInputStream(InputStream is)
    {
        _is = is;

        try
        {
            copy();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private int copy() throws IOException
    {
        int read = 0;
        int chunk = 0;
        byte[] data = new byte[256];

        while(-1 != (chunk = _is.read(data)))
        {
            read += data.length;
            _copy.write(data, 0, chunk);
        }

        return read;
    }

    public InputStream getCopy()
    {
        return (InputStream)new ByteArrayInputStream(_copy.toByteArray());
    }

    public String getString(){
        try {
            String resultString = null;
            InputStream inputStream = getCopy();
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line = null;
            while ((line=reader.readLine())!=null){
                buffer.append(line);
            }
            resultString = buffer.toString();
            return resultString;

        }catch (Exception e){e.printStackTrace();}
        return null;
    }
}