package io.cresco.ldp;


public class FileRecord {

    public String id;
    public String filePath;
    public String fileName;
    public String md5;
    public String size;
    public String status;


    public FileRecord(String id, String filePath, String fileName, String md5, String size) {
        this.id = id;
        this.filePath = filePath;
        this.fileName = fileName;
        this.md5 = md5;
        this.size = size;
        this.status = "0";
    }

}
