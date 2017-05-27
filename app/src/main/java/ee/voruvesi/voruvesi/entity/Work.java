package ee.voruvesi.voruvesi.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

//ühe hooldustöö-objekti kohta hoitav info
public class Work {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH); //kuupäeva formaat

    //"Teadmata" vaikeväärtused
    private String workName = "Teadmata";
    private String workObject = "Teadmata";
    private String username = "Teadmata";
    private String date = "Teadmata";

    static {
        SDF.setTimeZone(Calendar.getInstance().getTimeZone()); //määrame telo kohaliku ajatsooni kuupäeva formaadile
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
        this.date = toDateString(timeStamp);
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

    public boolean isBefore(String givenDate) { //kas selle hooldustöö kuupäev on varem kui antud kuupäev
        try {
            Date work = SDF.parse(date);
            Date given = SDF.parse(givenDate);
            return work.before(given);
        } catch (ParseException e) {
            if (!date.equals("Teadmata") && !givenDate.equals("Teadmata")) { //kui kumbki kuupäev on "Teadmata" siis tuleb viga, kuid see meid ei huvita
                e.printStackTrace();
            }
        }
        return true;
    }

    public static String toDateString(long timeStamp) { //millisekundid -> kuupäev
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
