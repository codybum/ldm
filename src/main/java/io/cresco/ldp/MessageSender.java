package io.cresco.ldp;

import io.cresco.library.plugin.PluginBuilder;
import io.cresco.library.utilities.CLogger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MessageSender implements Runnable  {

    private PluginBuilder plugin;
    private CLogger logger;

    private boolean isDone = false;

    public MessageSender(PluginBuilder plugin) {
        this.plugin = plugin;
        logger = plugin.getLogger(this.getClass().getName(), CLogger.Level.Info);

    }


    public void run() {

        while(plugin.isActive()) {
            try {


                if(!isDone) {

                    Archiver archiver = new Archiver(plugin, "dotfile", "md5", false, "tar");


                    Path inPath = Paths.get("/Users/cody/IdeaProjects/agent/test");

                    Path bagged = archiver.bagItUp(inPath);


                    if (bagged == null || !Files.exists(bagged)) {
                        logger.error("Failed to bag up directory [{}]", inPath);
                    }

                    logger.info("Boxing up [{}]", inPath.toAbsolutePath());
                    Path boxed = archiver.archive(bagged.toFile());
                    logger.info("Reverting bagging on directory [{}]", inPath);

                /*
                archiver.debagify(inPath);
                if (boxed == null || !Files.exists(boxed)) {
                    logger.error("Failed to box up directory [{}]", inPath);
                    //return false;
                }
                */

                    if (archiver.verifyBag(inPath)) {
                        logger.info("YEA VERIFIED!");
                    }
                    isDone = true;
                }




                Thread.sleep(60000);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }


}
