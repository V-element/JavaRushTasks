package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.IPQuery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;

public class LogParser implements IPQuery {
    private Path logDir;
    private List<String> entries = new ArrayList<>();
    private List<File> fileList = new ArrayList<>();


    public LogParser(Path logDir) {
        this.logDir = logDir;
        File[] files = logDir.toFile().listFiles();
        for (File file: files) {
            if (file.getName().endsWith(".log")) {
                fileList.add(file);
                parseFile(file);
            }
        }
    }

    public void parseFile(File file) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            if (bufferedReader.ready()) {
                entries.add(bufferedReader.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        return getUniqueIPs(after,before).size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        Set<String> ipSet = new HashSet<>();

        for (String s: entries) {
            String[] stringArray = s.split("\t");
            try {
                Date foundDate = new SimpleDateFormat("DD.MM.YYYY hh:mm:ss").parse(stringArray[2]);
                if (before == null && after == null
                        || after == null && before.before(foundDate)
                        || before == null && after.after(foundDate)
                    || before.before(foundDate) && after.after(foundDate)
                    || before.equals(foundDate) || after.equals(foundDate)) {
                    ipSet.add(stringArray[0]);
                }
            } catch (Exception e) {
                continue;
            }
        }

        return ipSet;
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        return null;
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        return null;
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        return null;
    }
}