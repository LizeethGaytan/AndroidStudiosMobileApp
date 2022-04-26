package DAO;

import Entity.Assessment;
import androidx.room.*;

import java.util.List;

@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAssessment(Assessment insertedAssessment);

    @Update
    void updateAssessment(Assessment updatedAssessment);

    @Delete
    void deleteAssessment(Assessment deletedAssessment);

    @Query("SELECT * FROM assessments ORDER BY assessmentID ASC")
    List<Assessment> getAllAssessments();

    @Query("SELECT * FROM assessments WHERE courseID = :courseID")
    List<Assessment> getAllAssessmentsByCourseID(int courseID);

    @Query("DELETE FROM assessments WHERE courseID = :courseID")
    void deleteAllAssessmentsByCourse(int courseID);

    @Query("SELECT * FROM assessments WHERE assessmentID = :assessmentID")
    Assessment getAssessmentByAssessmentID(int assessmentID);
}
