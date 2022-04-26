package Database;

import DAO.AssessmentDAO;
import DAO.CourseDAO;
import DAO.TermDAO;
import Entity.Assessment;
import Entity.Course;
import Entity.Term;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Everytime the database is changed the version has to be increased, ex. whenever there is a change to the Entity package classes
@Database(entities = {Term.class, Course.class, Assessment.class}, version=4, exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    // Makes an instance of the database
    private static volatile DatabaseBuilder INSTANCE;

    // Context is a part of android studio used by room and it tells you where you are in the flow of the program
    static DatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseBuilder.class) {
                // If you don't have a database one will be built
                if (INSTANCE == null) {
                    // Adding a line before .build() below can be used to allow main thread queries
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class, "universityDatabase.database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

