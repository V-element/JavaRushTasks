package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.DateQuery;
import com.javarush.task.task39.task3913.query.EventQuery;
import com.javarush.task.task39.task3913.query.IPQuery;
import com.javarush.task.task39.task3913.query.UserQuery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery {
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
                if (status.equals(Status.valueOf(stringArray[4])) && dateBetween(foundDate, after, before)) {
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
        return 0;
    }

    @Override
    public Set<Event> getAllEvents(Date after, Date before) {
        return null;
    }

    @Override
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        return null;
    }

    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        return null;
    }

    @Override
    public Set<Event> getFailedEvents(Date after, Date before) {
        return null;
    }

    @Override
    public Set<Event> getErrorEvents(Date after, Date before) {
        return null;
    }

    @Override
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        return 0;
    }

    @Override
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        return 0;
    }

    @Override
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        return null;
    }

    @Override
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        return null;
    }
}