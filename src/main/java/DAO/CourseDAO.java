package DAO;

import Entity.Course;
import androidx.room.*;

import java.util.List;

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCourse(Course insertedCourse);

    @Update
    void updateCourse(Course updatedCourse);

    @Delete
    void deleteCourse(Course deletedCourse);

    @Query("SELECT * FROM courses ORDER BY courseID ASC")
    List<Course> getAllCourses();

    @Query("SELECT * FROM courses WHERE termID = :termID")
    List<Course> getAllCoursesByTermID(int termID);

    @Query("SELECT * FROM courses WHERE courseID = :courseID")
    Course getCourseByCourseID(int courseID);
}
