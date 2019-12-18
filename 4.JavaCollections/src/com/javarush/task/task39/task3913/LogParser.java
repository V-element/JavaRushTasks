package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {
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
            while (bufferedReader.ready()) {
                entries.add(bufferedReader.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean dateBetween(Date date, Date after, Date before) {

        return (before == null && after == null
                || after == null && before.after(date)
                || before == null && after.before(date)
                || (after != null && before != null && (before.after(date) && after.before(date)
                || before.equals(date) || after.equals(date))));

    }

    public boolean dateBetweenNotEqual(Date date, Date after, Date before) {

        return (before == null && after == null
                || after == null && before.after(date)
                || before == null && after.before(date)
                || (after != null && before != null && (before.after(date) && after.before(date))));

    }

    public String getEventName(String eventName) {
        return eventName.replaceAll("\\d","").replaceAll("\\s","");
    }

    public Set<String> getUsersForEvent(Event event, Date after, Date before, Integer taskNumber) {
        Set<String> allLoggedUsers = new HashSet<>();

        for (String s: entries) {
            String[] stringArray = s.split("\t");
            try {
                Date foundDate = new SimpleDateFormat("d.M.y H:m:s").parse(stringArray[2]);
                if (Event.valueOf(getEventName(stringArray[3])).equals(event) && dateBetween(foundDate, after, before)) {
                    if (taskNumber == null || taskNumber != null && Integer.parseInt(stringArray[3].replaceAll("\\D","")) == taskNumber) {
                        allLoggedUsers.add(stringArray[1]);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return allLoggedUsers;
    }

    public Set<Date> getDatesForStatus(Status status, Date after, Date before) {
        Set<Date> DatesForStatus = new HashSet<>();

        for (String s: entries) {
            String[] stringArray = s.split("\t");
            try {
                Date foundDate = new SimpleDateFormat("d.M.y H:m:s").parse(stringArray[2]);
                if ((status == null || status.equals(Status.valueOf(stringArray[4]))) && dateBetween(foundDate, after, before)) {
                    DatesForStatus.add(foundDate);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return DatesForStatus;
    }

    public Set<Date> getDatesByUserAndEvent(String user, Event event, Date after, Date before) {
        Set<Date> Dates = new HashSet<>();

        for (String s: entries) {
            String[] stringArray = s.split("\t");
            try {
                Date foundDate = new SimpleDateFormat("d.M.y H:m:s").parse(stringArray[2]);
                if (event.equals(Event.valueOf(getEventName(stringArray[3]))) && user.equals(stringArray[1]) && dateBetween(foundDate, after, before)) {
                    Dates.add(foundDate);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return Dates;
    }

    public Date getDateOfTaskByUserAndEvent(String user, int task, Event event, Date after, Date before) {
        Set<Date> Dates = new HashSet<>();

        for (String s: entries) {
            String[] stringArray = s.split("\t");
            try {
                Date foundDate = new SimpleDateFormat("d.M.y H:m:s").parse(stringArray[2]);
                if (event.equals(Event.valueOf(getEventName(stringArray[3])))
                        && user.equals(stringArray[1])
                        && task == Integer.parseInt(stringArray[3].replaceAll("\\D",""))
                        && dateBetween(foundDate, after, before)) {
                    Dates.add(foundDate);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!Dates.isEmpty()) {
            TreeSet<Date> myTreeSet = new TreeSet(Dates);
            return myTreeSet.first();
        } else {
            return null;
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
                Date foundDate = new SimpleDateFormat("d.M.y H:m:s").parse(stringArray[2]);
                if (dateBetween(foundDate, after, before)) {
                    ipSet.add(stringArray[0]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ipSet;
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        Set<String> ipSet = new HashSet<>();

        for (String s: entries) {
            String[] stringArray = s.split("\t");
            try {
                Date foundDate = new SimpleDateFormat("d.M.y H:m:s").parse(stringArray[2]);
                if (user.equals(stringArray[1]) && dateBetween(foundDate, after, before)) {
                    ipSet.add(stringArray[0]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ipSet;
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        Set<String> ipSet = new HashSet<>();

        for (String s: entries) {
            String[] stringArray = s.split("\t");
            try {
                Date foundDate = new SimpleDateFormat("d.M.y H:m:s").parse(stringArray[2]);
                if (event.equals(Event.valueOf(getEventName(stringArray[3]))) && dateBetween(foundDate, after, before)) {
                    ipSet.add(stringArray[0]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ipSet;
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        Set<String> ipSet = new HashSet<>();

        for (String s: entries) {
            String[] stringArray = s.split("\t");
            try {
                Date foundDate = new SimpleDateFormat("d.M.y H:m:s").parse(stringArray[2]);
                if (status.equals(Status.valueOf(stringArray[4])) && dateBetween(foundDate, after, before)) {
                    ipSet.add(stringArray[0]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ipSet;
    }

    @Override
    public Set<String> getAllUsers() {
        Set<String> allUsers = new HashSet<>();

        for (String s: entries) {
            String[] stringArray = s.split("\t");
            allUsers.add(stringArray[1]);
        }

        return allUsers;
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {
        Set<String> allUsers = new HashSet<>();

        for (String s: entries) {
            String[] stringArray = s.split("\t");
            try {
                Date foundDate = new SimpleDateFormat("d.M.y H:m:s").parse(stringArray[2]);
                if (dateBetween(foundDate, after, before)) {
                    allUsers.add(stringArray[1]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return allUsers.size();
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        Set<String> NumberOfUserEvents = new HashSet<>();

        for (String s: entries) {
            String[] stringArray = s.split("\t");
            try {
                Date foundDate = new SimpleDateFormat("d.M.y H:m:s").parse(stringArray[2]);
                if (user.equals(stringArray[1]) && dateBetween(foundDate, after, before)) {
                    NumberOfUserEvents.add(getEventName(stringArray[3]));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return NumberOfUserEvents.size();
    }

    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        Set<String> allIPUsers = new HashSet<>();

        for (String s: entries) {
            String[] stringArray = s.split("\t");
            try {
                Date foundDate = new SimpleDateFormat("d.M.y H:m:s").parse(stringArray[2]);
            if (ip.equals(stringArray[0]) && dateBetween(foundDate, after, before)) {
                allIPUsers.add(stringArray[1]);
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return allIPUsers;
    }

    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        return getUsersForEvent(Event.LOGIN, after, before, null);
    }

    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        return getUsersForEvent(Event.DOWNLOAD_PLUGIN, after, before,null);
    }

    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        return getUsersForEvent(Event.WRITE_MESSAGE, after, before,null);
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        return getUsersForEvent(Event.SOLVE_TASK, after, before,null);

    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        return getUsersForEvent(Event.SOLVE_TASK, after, before, task);
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        return getUsersForEvent(Event.DONE_TASK, after, before,null);
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        return getUsersForEvent(Event.DONE_TASK, after, before, task);
    }

    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        Set<Date> Dates = new HashSet<>();
        for (String s: entries) {
            String[] stringArray = s.split("\t");
            try {
                Date foundDate = new SimpleDateFormat("d.M.y H:m:s").parse(stringArray[2]);
                if (user.equals(stringArray[1]) && event.equals(Event.valueOf(getEventName(stringArray[3]))) && dateBetween(foundDate, after, before)) {
                    Dates.add(foundDate);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Dates;
    }

    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        return getDatesForStatus(Status.FAILED, after, before);
    }

    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        return getDatesForStatus(Status.ERROR, after, before);
    }

    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
        Set<Date> Dates = getDatesForUserAndEvent(user, Event.LOGIN, after, before);
        if (!Dates.isEmpty()) {
            TreeSet<Date> myTreeSet = new TreeSet(Dates);
            return myTreeSet.first();
        } else {
            return null;
        }
    }

    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
        return getDateOfTaskByUserAndEvent(user, task, Event.SOLVE_TASK, after, before);
    }

    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        return getDateOfTaskByUserAndEvent(user, task, Event.DONE_TASK, after, before);
    }

    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        return getDatesByUserAndEvent(user, Event.WRITE_MESSAGE, after, before);
    }

    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        return getDatesByUserAndEvent(user, Event.DOWNLOAD_PLUGIN, after, before);
    }

    @Override
    public int getNumberOfAllEvents(Date after, Date before) {
        return getAllEvents(after, before).size();
    }

    @Override
    public Set<Event> getAllEvents(Date after, Date before) {
        Set<Event> eventSet = new HashSet<>();

        for (String s: entries) {
            String[] stringArray = s.split("\t");
            try {
                Date foundDate = new SimpleDateFormat("d.M.y H:m:s").parse(stringArray[2]);
                if (dateBetween(foundDate, after, before)) {
                    eventSet.add(Event.valueOf(getEventName(stringArray[3])));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return eventSet;
    }

    @Override
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        Set<Event> eventSet = new HashSet<>();

        for (String s: entries) {
            String[] stringArray = s.split("\t");
            try {
                Date foundDate = new SimpleDateFormat("d.M.y H:m:s").parse(stringArray[2]);
                if (ip.equals(stringArray[0]) && dateBetween(foundDate, after, before)) {
                    eventSet.add(Event.valueOf(getEventName(stringArray[3])));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return eventSet;
    }

    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        Set<Event> eventSet = new HashSet<>();

        for (String s: entries) {
            String[] stringArray = s.split("\t");
            try {
                Date foundDate = new SimpleDateFormat("d.M.y H:m:s").parse(stringArray[2]);
                if (user.equals(stringArray[1]) && dateBetween(foundDate, after, before)) {
                    eventSet.add(Event.valueOf(getEventName(stringArray[3])));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return eventSet;
    }

    @Override
    public Set<Event> getFailedEvents(Date after, Date before) {
        Set<Event> eventSet = new HashSet<>();

        for (String s: entries) {
            String[] stringArray = s.split("\t");
            try {
                Date foundDate = new SimpleDateFormat("d.M.y H:m:s").parse(stringArray[2]);
                if (Status.valueOf(stringArray[4]).equals(Status.FAILED) && dateBetween(foundDate, after, before)) {
                    eventSet.add(Event.valueOf(getEventName(stringArray[3])));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return eventSet;
    }

    @Override
    public Set<Event> getErrorEvents(Date after, Date before) {
        Set<Event> eventSet = new HashSet<>();

        for (String s: entries) {
            String[] stringArray = s.split("\t");
            try {
                Date foundDate = new SimpleDateFormat("d.M.y H:m:s").parse(stringArray[2]);
                if (Status.valueOf(stringArray[4]).equals(Status.ERROR) && dateBetween(foundDate, after, before)) {
                    eventSet.add(Event.valueOf(getEventName(stringArray[3])));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return eventSet;
    }

    @Override
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        int numberOfAttempt = 0;

        for (String s: entries) {
            String[] stringArray = s.split("\t");
            try {
                Date foundDate = new SimpleDateFormat("d.M.y H:m:s").parse(stringArray[2]);
                if (Event.valueOf(getEventName(stringArray[3])).equals(Event.SOLVE_TASK)
                        && task == Integer.parseInt(stringArray[3].replaceAll("\\D",""))
                        && dateBetween(foundDate, after, before)) {
                    numberOfAttempt++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return numberOfAttempt;
    }

    @Override
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        int numberOfAttempt = 0;

        for (String s: entries) {
            String[] stringArray = s.split("\t");
            try {
                Date foundDate = new SimpleDateFormat("d.M.y H:m:s").parse(stringArray[2]);
                if (Event.valueOf(getEventName(stringArray[3])).equals(Event.DONE_TASK)
                        && task == Integer.parseInt(stringArray[3].replaceAll("\\D",""))
                        && dateBetween(foundDate, after, before)) {
                    numberOfAttempt++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return numberOfAttempt;
    }

    @Override
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        Map<Integer, Integer> map = new HashMap<>();

        for (String s: entries) {
            String[] stringArray = s.split("\t");
            try {
                Date foundDate = new SimpleDateFormat("d.M.y H:m:s").parse(stringArray[2]);
                if (Event.valueOf(getEventName(stringArray[3])).equals(Event.SOLVE_TASK)
                        && dateBetween(foundDate, after, before)) {
                    int task = Integer.parseInt(stringArray[3].replaceAll("\\D",""));
                    if (map.containsKey(task)) {
                        map.put(task, map.get(task) + 1);
                    } else {
                        map.put(task, 1);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    @Override
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        Map<Integer, Integer> map = new HashMap<>();

        for (String s: entries) {
            String[] stringArray = s.split("\t");
            try {
                Date foundDate = new SimpleDateFormat("d.M.y H:m:s").parse(stringArray[2]);
                if (Event.valueOf(getEventName(stringArray[3])).equals(Event.DONE_TASK)
                        && dateBetween(foundDate, after, before)) {
                    int task = Integer.parseInt(stringArray[3].replaceAll("\\D",""));
                    if (map.containsKey(task)) {
                        map.put(task, map.get(task) + 1);
                    } else {
                        map.put(task, 1);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    public Set<Status> getStatuses(Date after, Date before) {
        Set<Status> statusSet = new HashSet<>();

        for (String s: entries) {
            String[] stringArray = s.split("\t");
            try {
                Date foundDate = new SimpleDateFormat("d.M.y H:m:s").parse(stringArray[2]);
                if (dateBetween(foundDate, after, before)) {
                    statusSet.add(Status.valueOf(stringArray[4]));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return statusSet;
    }

    private String getMatch(String logLine, String groupName) {
        String match = null;
        Matcher m = Pattern.compile(
                "(?<ip>[\\d]+.[\\d]+.[\\d]+.[\\d]+)\\s" +
                        "(?<user>[a-zA-Z ]+)\\s" +
                        "(?<date>[\\d]+.[\\d]+.[\\d]+ [\\d]+:[\\d]+:[\\d]+)\\s" +
                        "(?<event>[\\w]+)\\s?(" +
                        "(?<taskNumber>[\\d]+)|)\\s" +
                        "(?<status>[\\w]+)")
                .matcher(logLine);
        if (m.find()) {
            match = m.group(groupName);
        }
        return match;
    }

    private String getQLMatch(String query, String groupName) {
        String match = null;
        Matcher m = null;
        if (query.contains("and date between")) {
            m = Pattern.compile(
                    "get (?<field1>\\w+) for (?<field2>\\w+) = \"(?<value1>.*?)\" and date between \"(?<after>.*?)\" and \"(?<before>.*?)\"")
                    .matcher(query);
        } else {
            m = Pattern.compile(
                    "get (?<field1>\\w+) for (?<field2>\\w+) = \"(?<value1>.*?)\"")
                    .matcher(query);
        }
        if (m.find()) {
            match = m.group(groupName);
        }
        return match;
    }

    private List<String> getAllLogLines() {
        List<String> logLines = new ArrayList<>();

        try {
            List<Path> files = Files.list(logDir)
                    .filter(p -> p.toString()
                            .endsWith(".log"))
                    .collect(Collectors.toList());
            for (Path log : files) {
                logLines.addAll(Files.readAllLines(log));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logLines;
    }

    @Override
    public Set<Object> execute(String query) {
        Set<Object> returnData = new HashSet<>();

        String field1 = getQLMatch(query, "field1");
        String field2 = getQLMatch(query, "field2");
        String value1 = getQLMatch(query, "value1");
        String after = null;
        String before = null;
        try {
            after = getQLMatch(query, "after");
            before = getQLMatch(query, "before");
        } catch (IllegalArgumentException e) {
            //e.printStackTrace();
        }



        if (field1 != null && field2 != null && value1 != null) {
            for (String logLine : getAllLogLines()) {
                if (getMatch(logLine, field2).equals(value1)) {
                    if (after != null || before != null) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("d.M.y H:m:s");
                        try {
                            Date date = dateFormat.parse(getMatch(logLine, "date"));
                            if (dateBetweenNotEqual(date, dateFormat.parse(after), dateFormat.parse(before))) {
                                if (field1.equals("date")) {
                                    try {
                                        returnData.add(new SimpleDateFormat("d.M.y H:m:s").parse(getMatch(logLine, field1)));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else if (field1.equals("event")) {
                                    returnData.add(Event.valueOf(getMatch(logLine, field1)));
                                } else if (field1.equals("status")) {
                                    returnData.add(Status.valueOf(getMatch(logLine, field1)));
                                } else {
                                    returnData.add(getMatch(logLine, field1));
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (field1.equals("date")) {
                            try {
                                returnData.add(new SimpleDateFormat("d.M.y H:m:s").parse(getMatch(logLine, field1)));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (field1.equals("event")) {
                            returnData.add(Event.valueOf(getMatch(logLine, field1)));
                        } else if (field1.equals("status")) {
                            returnData.add(Status.valueOf(getMatch(logLine, field1)));
                        } else {
                            returnData.add(getMatch(logLine, field1));
                        }
                    }
                }
            }
        }

        switch (query) {
            case "get ip":
                returnData = new HashSet<>(getUniqueIPs(null, null));
                break;
            case "get user":
                returnData = new HashSet<>(getAllUsers());
                break;
            case "get date":
                returnData = new HashSet<>(getDatesForStatus(null, null,null));
                break;
            case "get event":
                returnData = new HashSet<>(getAllEvents(null, null));
                break;
            case "get status":
                returnData = new HashSet<>(getStatuses(null, null));
                break;
        }

        return returnData;
    }
}