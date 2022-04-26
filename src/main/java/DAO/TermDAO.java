package DAO;

import Entity.Term;
import androidx.room.*;

import java.util.List;

@Dao
public interface TermDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTerm(Term insertedTerm);

    @Update
    void updateTerm(Term updatedTerm);

    @Delete
    void deleteTerm(Term deletedTerm);

    @Query("SELECT * FROM terms ORDER BY termID ASC")
    List<Term> getAllTerms();

    @Query("SELECT * FROM terms WHERE termID = :termID")
    List<Term> getTermByTermID(int termID);
}
