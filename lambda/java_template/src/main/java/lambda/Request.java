/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lambda;

/**
 *
 * @author wlloyd
 */
public class Request {

    
     String bucketname;
    public String getBucketname()
    {
        return bucketname;
    }
    
    public void setBucketname(String bucketName)
    {
        this.bucketname = bucketName;
    }
    
         String filename;
    public String getFilename()
    {
        return filename;
    }
    
    public void setFilename(String filename)
    {
        this.filename = filename;
    }
    
    private int row;
    public void setRow(int row)
    {
        this.row = row;
    }
    public int getRow()
    {
        return row;
    }
    
    
        private int col;
    public void setCol(int col)
    {
        this.col = col;
    }
    public int getCol()
    {
        return col;
    }
    

    public Request()
    {
        
    }
}
