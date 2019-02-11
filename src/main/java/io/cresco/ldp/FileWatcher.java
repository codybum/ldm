package io.cresco.ldp;

import io.cresco.library.plugin.PluginBuilder;
import io.cresco.library.utilities.CLogger;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class FileWatcher  {

    private PluginBuilder plugin;
    private CLogger logger;

    private Timer timer;
    private boolean running = false;


    public FileWatcher(PluginBuilder plugin, String streamName, long interval, String incomingDirStr) {
        this.plugin = plugin;
        logger = plugin.getLogger(this.getClass().getName(), CLogger.Level.Info);

        timer = new Timer();
        timer.scheduleAtFixedRate(new FileMonitorTask(plugin, incomingDirStr), 500L, interval);
        running = true;
    }


    private class FileMonitorTask extends TimerTask {
        private PluginBuilder plugin;
        private File inputDirectory;

        FileMonitorTask(PluginBuilder plugin, String incomingDirStr) {
            this.plugin = plugin;
            inputDirectory = new File(incomingDirStr);
        }

        public void run() {

            try {

                Map<String,FileRecord> fileRecordMap = buildFileMap(inputDirectory);


            } catch(Exception ex) {
                logger.error("FileMonitorTask() " + ex.getMessage());
            }


        }
    }

    public void stop() {
        timer.cancel();
        running = false;
    }


    private Map<String,FileRecord> buildFileMap(File dirPath) {
        Map<String,FileRecord> fileMap = null;

        try {
            fileMap = new HashMap<>();


            File[] matches = dirPath.listFiles();
            for (File f : matches) {

                String id = UUID.randomUUID().toString();
                FileRecord fr = new FileRecord(id,f.getAbsolutePath(),f.getName(),getCheckSum(f.getAbsolutePath()),String.valueOf(f.length()));
                fileMap.put(id,fr);

            }


        } catch(Exception ex) {

            logger.error("Error: buildFileMap " + ex.getMessage());
            ex.printStackTrace();
        }

        return fileMap;
    }

    String getCheckSum(String path)  {
        String hash = null;

        //FileInputStream fis = null;
        try (FileInputStream fis = new FileInputStream(new File(path))) {
            //fis = new FileInputStream(new File(path));
            hash = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
        } catch (Exception ex) {
            System.out.println("MD5Tools : getCheckSum Error : " + ex.toString());
        }/* finally {
            fis.close();
		}*/

        return hash;

    }




}
