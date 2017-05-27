package ee.voruvesi.voruvesi.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Work {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);

    private String workName = "Teadmata";
    private String workObject = "Teadmata";
    private String username = "Teadmata";
    private String date = "Teadmata";

    static {
        SDF.setTimeZone(Calendar.getInstance().getTimeZone());
    }

    public Work() {
    }

    public Work(String workName, String workObject, String username, String date) {
        this.workName = workName;
        this.workObject = workObject;
        this.username = username;
        this.date = date;
    }

    public Work(String workName, String workObject, String username, long timeStamp) {
        this.workName = workName;
        this.workObject = workObject;
        this.username = username;
        this.date = SDF.format(new Date(timeStamp));
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getWorkObject() {
        return workObject;
    }

    public void setWorkObject(String workObject) {
        this.workObject = workObject;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isBefore(String givenDate) {
        try {
            Date work = SDF.parse(date);
            Date given = SDF.parse(givenDate);
            return work.before(given);
        } catch (ParseException e) {
            if (!givenDate.equals("Teadmata")) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static String toDateString(long timeStamp) {
        return SDF.format(new Date(timeStamp));
    }

    @Override
    public String toString() {
        return "Work{" +
                "workName='" + workName + '\'' +
                ", workObject='" + workObject + '\'' +
                ", username='" + username + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
