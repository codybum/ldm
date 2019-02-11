package io.cresco.ldp;

import io.cresco.library.plugin.PluginBuilder;
import io.cresco.library.utilities.CLogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class FileWatcherEngine {

    private PluginBuilder plugin;
    private CLogger logger;

    private AtomicBoolean lockWatchers = new AtomicBoolean();

    private Map<String,FileWatcher> watcherMap;

    public FileWatcherEngine(PluginBuilder plugin) {
        this.plugin = plugin;
        logger = plugin.getLogger(this.getClass().getName(), CLogger.Level.Info);
        watcherMap = Collections.synchronizedMap(new HashMap<>());
    }




}
