package upd.edu.ph.check_up;
import android.database.sqlite.*;
import android.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.Cursor;

public class DbAdapter {
	/**
	 * Contains the names of each table
	 * (0 = colleges)
	 * (1 = courses)
	 * (2 = subjecttypes)
	 * (3 = subjects)
	 * (4 = prereqs)
	 * (5 = status)
	 * (6 = users)
	 * (7 = takensubjects)
	 * (8 = curriculum)
	 * (9 = coreqs)
	 */
	public static final String[] TABLENAMES = {"colleges", "courses", "subjecttypes", "subjects", "prereqs", "status", "users", "takensubjects", "curriculum", "coreqs"};
	//colleges table column names
	public static final String KEY_COLLEGES_COLLEGEID = "college_id";
	public static final String KEY_COLLEGES_NAME = "name";
	//courses table column names
	public static final String KEY_COURSES_COURSEID = "course_id";
	public static final String KEY_COURSES_NAME = "name";
	public static final String KEY_COURSES_COLLEGEID = "college_id";
	//subjecttypes table column names
	public static final String KEY_SUBJECTTYPES_TYPEID = "type_id";
	public static final String KEY_SUBJECTTYPES_NAME = "name";
	//subjects table column names
	public static final String KEY_SUBJECTS_SUBJECTID = "subject_id";
	public static final String KEY_SUBJECTS_NAME = "name";
	public static final String KEY_SUBJECTS_UNITS = "units";
	public static final String KEY_SUBJECTS_TYPEID = "type_id";
	public static final String KEY_SUBJECTS_COLLEGEID = "college_id";
	//coreqs table column names
	public static final String KEY_COREQS_SUBJECTID = "subject_id";
	public static final String KEY_COREQS_COREQID = "coreq_id";
	//prereqs table column names
	public static final String KEY_PREREQS_SUBJECTID = "subject_id";
	public static final String KEY_PREREQS_PREREQID = "prereq_id";
	//status table column names
	public static final String KEY_STATUS_STATUSID = "status_id";
	public static final String KEY_STATUS_NAME = "name";
	//users table column names
	public static final String KEY_USERS_USERNAME = "username";
	public static final String KEY_USERS_PASSWORD = "password";
	public static final String KEY_USERS_STUDENTNO = "student_no";
	public static final String KEY_USERS_NAME = "name";
	public static final String KEY_USERS_COURSEID = "course_id";
	public static final String KEY_USERS_STATUSID = "status_id";
	//takensubjects table column names
	public static final String KEY_TAKENSUBJECTS_USERNAME = "username";
	public static final String KEY_TAKENSUBJECTS_SUBJECTID = "subject_id";
	public static final String KEY_TAKENSUBJECTS_RATING = "rating";
	public static final String KEY_TAKENSUBJECTS_GRADE = "grade";
	public static final String KEY_TAKENSUBJECTS_YEAR = "year";
	public static final String KEY_TAKENSUBJECTS_SEMESTER = "semester";
	//curriculum table column names
	public static final String KEY_CURRICULUM_COURSEID = "course_id";
	public static final String KEY_CURRICULUM_SUBJECTID = "subject_id";
	public static final String KEY_CURRICULUM_YEAR = "year";
	public static final String KEY_CURRICULUM_SEMESTER = "semester";
	
	private static final String TAG = "DbAdapter";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	
	/**
	 * Database schema
	 */
	private static final String DATABASE_CREATE =
			"CREATE TABLE 'colleges' (" +
			"	'college_id' INTEGER PRIMARY KEY AUTOINCREMENT," +
			"	'name' TEXT NOT NULL," +
			"	UNIQUE('name'));" +
			"CREATE TABLE 'courses' (" +
			"	'course_id' INTEGER PRIMARY KEY AUTOINCREMENT," +
			"	'name' TEXT NOT NULL," +
			"	'college_id' INTGER," +
			"	UNIQUE('name')," +
			"	FOREIGN KEY('college_id') REFERENCES colleges('college_id'));" +
			"CREATE TABLE 'subjecttypes' (" +
			"	'type_id' INTEGER PRIMARY KEY AUTOINCREMENT," +
			"	'name' TEXT NOT NULL," +
			"	UNIQUE('name'));" +
			"CREATE TABLE 'subjects' (" +
			"	'subject_id' INTEGER PRIMARY KEY AUTOINCREMENT," +
			"	'name' TEXT NOT NULL," +
			"	'units' INTEGER NOT NULL," +
			"	'type_id' INTEGER NOT NULL," +
			"	'college_id' INTEGER NOT NULL," +
			"	FOREIGN KEY('type_id') REFERENCES subjecttypes('type_id')," +
			"	FOREIGN KEY('college_id') REFERENCES colleges('college_id'));" +
			"CREATE TABLE 'coreqs' (" +
			"	'subject_id' INTEGER," +
			"	'coreq_id' INTEGER," +
			"	UNIQUE('subject_id', 'prereq_id')," +
			"	FOREIGN KEY('subject_id') REFERENCES subjects('subject_id')," +
			"	FOREIGN KEY('coreq_id') REFERENCES subjects('subject_id'));" +
			"CREATE TABLE 'prereqs' (" +
			"	'subject_id' INTEGER," +
			"	'prereq_id' INTEGER," +
			"	UNIQUE('subject_id', 'prereq_id')," +
			"	FOREIGN KEY('subject_id') REFERENCES subjects('subject_id')," +
			"	FOREIGN KEY('prereq_id') REFERENCES subjects('subject_id'));" +
			"CREATE TABLE 'status' (" +
			"	'status_id' INTEGER PRIMARY KEY AUTOINCREMENT," +
			"	'name' TEXT NOT NULL," +
			"	UNIQUE('name'));" +
			"CREATE TABLE 'users' (" +
			"	'username' TEXT PRIMARY KEY," +
			"	'password' TEXT NOT NULL," +
			"	'student_no' INTEGER NOT NULL," +
			"	'name' TEXT," +
			"	'course_id' INTEGER NOT NULL," +
			"	'status_id' INTEGER NOT NULL," +
			"	FOREIGN KEY('course_id') REFERENCES courses('course_id')," +
			"	FOREIGN KEY('status_id') REFERENCES status('status_id'));" +
			"CREATE TABLE 'takensubjects' (" +
			"	'username' TEXT NOT NULL," +
			"	'subject_id' INTEGER NOT NULL," +
			"	'rating' INTEGER," +
			"	'grade' REAL," +
			"	'year' INTEGER NOT NULL," +
			"	'semester' INTEGER NOT NULL," +
			"	FOREIGN KEY('username') REFERENCES users('username')," +
			"	FOREIGN KEY('subject_id') REFERENCES subjects('subject_id'));" +
			"CREATE TABLE 'curriculum' (" +
			"	'course_id' INTEGER NOT NULL," +
			"	'subject_id' INTEGER NOT NULL," +
			"	'year' INTEGER," +
			"	'semester' INTEGER NOT NULL," +
			"	FOREIGN KEY('course_id') REFERENCES courses('course_id')," +
			"	FOREIGN KEY('subject_id') REFERENCES subjects('subject_id'));";
	
	/**
	 * Main database for the application
	 */
	private static final String DATABASE_NAME = "C3";
	private static int DATABASE_VERSION = 2;
	
	private final Context mCtx;
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}
		
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all data.");
			for (int i = 0; i < TABLENAMES.length; i++) {
				db.execSQL("DROP TABLE IF EXISTS " + TABLENAMES[i] + ";");
			}
			onCreate(db);
		}
	}
	
	public DbAdapter(Context ctx) {
		this.mCtx = ctx;
	}
	
	public DbAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		mDbHelper.close();
	}
	
	/**
	 * Drops all tables then creates an empty database
	 */
	public void resetDatabase() {
		for (int i = 0; i < TABLENAMES.length; i++) {
			mDb.execSQL("DROP TABLE IF EXISTS " + TABLENAMES[i] + ";");
		}
		mDb.execSQL(DATABASE_CREATE);
	}
	
	/**
	 * CHECK name existence in the colleges table before using
	 * Gets the college_id column of the specified name
	 * @param name - College name
	 * @return - college id
	 */
	private int getCollegeID(String name) {
		Cursor c = mDb.rawQuery("SELECT college_id FROM colleges WHERE name='" + name + "'", null);
		return c.getInt(0);
	}
	
	/**
	 * CHECK name existence in the courses table before using
	 * Gets the course_id column of the specified name
	 * @param name - Course name
	 * @return - course id
	 */
	private int getCourseID(String name) {
		Cursor c = mDb.rawQuery("SELECT course_id FROM courses WHERE name='" + name + "'", null);
		return c.getInt(0);
	}
	
	/**
	 * CHECK name existence in the subjects table before using
	 * Gets the subject_id column of the specified name
	 * @param name - Subject name
	 * @return - subject id
	 */
	private int getSubjectID(String name) {
		Cursor c = mDb.rawQuery("SELECT subject_id FROM subjects WHERE name='" + name + "'", null);
		return c.getInt(0);
	}
	
	/**
	 * CHECK name existence in the subjecttypes table before using
	 * Gets the type_id column of the specified name
	 * @param name - Subject type name
	 * @return - type id
	 */
	private int getSubjectTypeID(String name) {
		Cursor c = mDb.rawQuery("SELECT type_id FROM subjecttypes WHERE name='" + name + "'", null);
		return c.getInt(0);
	}
	
	/**
	 * CHECK name existence in the status table before using
	 * Gets the status_id column of the specified name
	 * @param name - Status name
	 * @return - status id
	 */
	private int getStatusID(String name) {
		Cursor c = mDb.rawQuery("SELECT status_id FROM status WHERE name='" + name + "'", null);
		return c.getInt(0);
	}
	
	/**
	 * Gets the Subject type of the specified subject
	 * @param subject
	 * @return - the type of the subject
	 * @throws SubjectNameNotFoundException - if the subject is not in the subjects table
	 */
	public String getSubjectType(String subject) {
		if (!this.ifSubjectExists(subject)) {
			//TO DO
			//throw SubjectNameNotFoundException
		}
		Cursor c = mDb.rawQuery("SELECT t.name FROM subjects s, subjecttypes t WHERE t.type_id=s.type_id AND s.name='" + subject + "'", null);
		return c.getString(0);
	}
	
	/**
	 * Gets the year and the semester of the subject in the course curriculum
	 * @param subject - existing subject in the curriculum table
	 * @param course - existing course in the curriculum table
	 * @return Cursor - with the columns year, semester
	 * @throws SubjectNotInCurriculumException - if the subject is not in the curriculum table
	 */
	public Cursor getSubjectYearSemester(String subject, String course) {
		if (!this.ifSubjectInCurriculum(subject, course)) {
			//TO DO
			//throw SubjectNotInCurriculumException
		}
		Cursor c = mDb.rawQuery("SELECT year, semester FROM curriculum WHERE subject_id=" + getSubjectID(subject) + " AND course_id=" + getCourseID(course), null);
		return c;
	}
	
	/**
	 * Gets the prerequisites of the specified subject.
	 * @param subject - subject to be checked of prerequisites
	 * @return - names of the prerequisites
	 * @throws SubjectNameNotFoundException - if the subject is not in the subjects table
	 * @throws SubjectNoPrerequisitesException - if the subject does not have any prerequisites
	 */
	public Cursor getSubjectPrerequisites(String subject) {
		if (!this.ifSubjectExists(subject)) {
			//TO DO
			//throw SubjectNameNotFoundException
		}
		Cursor c = mDb.rawQuery("SELECT s.name FROM subjects s, prereqs p WHERE s.subject_id=p.prereq_id AND p.subject_id=" + getSubjectID(subject), null);
		if (c.getCount() == 0) {
			//TO DO
			//throw SubjectNoPrerequisitesException
		}
		return c;
	}
	
	/**
	 * Adds a new college
	 * @param name - College name (ie. College of Engineering, College of Science, etc.)
	 * @return - the row number of the added entry, otherwise -1 if unsuccessful
	 */
	public long addCollege(String name) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_COLLEGES_NAME, name);
		return mDb.insert(TABLENAMES[0], null, initialValues);
	}
	
	/**
	 * Adds a new course
	 * @param name - Course name (ie. BS Computer Science, BA Psychology, etc.)
	 * @param college - existing college name in the colleges table
	 * @return - the row number of the added entry, otherwise -1 if unsuccessful
	 */
	public long addCourse(String name, String college) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_COURSES_NAME, name);
		if (this.ifCollegeExists(college)) {
			initialValues.put(KEY_COURSES_COLLEGEID, getCollegeID(college));
		}
		return mDb.insert(TABLENAMES[1], null, initialValues);
	}
	
	/**
	 * Adds a new subject type
	 * @param name - Subject type name (ie. GE MST, PE, CWTS, etc.)
	 * @return - the row number of the added entry, otherwise -1 if unsuccessful
	 */
	public long addSubjectType(String name) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_SUBJECTTYPES_NAME, name);
		return mDb.insert(TABLENAMES[2], null, initialValues);
	}
	
	/**
	 * Adds a new subject
	 * @param name - Subject name (ie. CS 172, Math 17, etc.)
	 * @param units - Subject's number of units
	 * @param type - existing subject type in the subjecttypes table
	 * @param college - existing college name in the colleges table
	 * @return - the row number of the added entry, otherwise -1 if unsuccessful
	 * @throws SubjectTypeNotFoundException - if the subject type is not in the subjecttypes table
	 * @throws CollegeNameNotFoundException - if the college is not in the colleges table
	 */
	public long addSubject(String name, int units, String type, String college) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_SUBJECTS_NAME, name);
		initialValues.put(KEY_SUBJECTS_UNITS, units);
		if (this.ifSubjectTypeExists(type)) {
			initialValues.put(KEY_SUBJECTS_TYPEID, getSubjectTypeID(type));
		} else {
			//TO DO
			//throw SubjectTypeNotFoundException
		}
		if (this.ifCollegeExists(college)) {
			initialValues.put(KEY_SUBJECTS_COLLEGEID, getCollegeID(college));
		} else {
			//TO DO
			//throw CollegeNameNotFoundException
		}
		return mDb.insert(TABLENAMES[3], null, initialValues);
	}
	
	/**
	 * Adds a prequisite to a subject
	 * @param name - existing subject in the subjects table
	 * @param prerequisite - existing subject in the subjects table
	 * @return - the row number of the added entry, otherwise -1 if unsuccessful
	 * @throws SubjectPrerequisiteMismatchException - if the subject-prerequisite pair is not in the prereqs table
	 * @throws SubjectNameNotFoundException - if either the subject or prerequisite is not in the subjects table
	 */
	public long addPrerequisite(String name, String prerequisite) {
		ContentValues initialValues = new ContentValues();
		if (this.ifSubjectExists(name) || this.ifSubjectExists(prerequisite)) {
			if (this.ifPrerequisiteSubject(name, prerequisite)) {
				initialValues.put(KEY_PREREQS_SUBJECTID, getSubjectID(name));
				initialValues.put(KEY_PREREQS_PREREQID, getSubjectID(prerequisite));
			} else {
				//TO DO
				//throw SubjectPrerequisiteMismatchException
			}
		} else {
			//TO DO
			//throw SubjectNameNotFoundException
		}
		return mDb.insert(TABLENAMES[4], null, initialValues);
	}
	
	/**
	 * Adds a new status
	 * @param name - a user status (ie. Regular, Graduating, etc.)
	 * @return - the row number of the added entry, otherwise -1 if unsuccessful
	 */
	public long addStatus(String name) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_STATUS_NAME, name);
		return mDb.insert(TABLENAMES[5], null, initialValues);
	}
	
	/**
	 * Adds a new user
	 * @param username - student's username (preferably crs username)
	 * @param password - student's password
	 * @param studentno - student's student number (ie. 201200100)
	 * @param realname - student's real name
	 * @param course - existing course in the courses table
	 * @param status - existing status is the status table
	 * @return - the row number of the added entry, otherwise -1 if unsuccessful
	 * @throws CourseNameNotFoundException - if the course is not in the courses table
	 * @throws StatusNotFoundException - if the status is not in the status table
	 */
	public long addUser(String username, String password, int studentno, String realname, String course, String status) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_USERS_USERNAME, username);
		initialValues.put(KEY_USERS_PASSWORD, password);
		initialValues.put(KEY_USERS_STUDENTNO, studentno);
		initialValues.put(KEY_USERS_NAME, realname);
		if (this.ifCourseExists(course)) {
			initialValues.put(KEY_USERS_COURSEID, getCourseID(course));
		} else {
			//TO DO
			//throw CourseNameNotFoundException
		}
		if (this.ifStatusExists(status)) {
			initialValues.put(KEY_USERS_STATUSID, getStatusID(status));
		} else {
			//TO DO
			//throw StatusNotFoundException
		}
		return mDb.insert(TABLENAMES[6], null, initialValues);
	}
	
	/**
	 * Adds a new taken subject of a user
	 * @param username - existing username in the users table
	 * @param subject - existing subject in the subjects table
	 * @param rating - student's rating for the subject (CAN BE NULL) considered NULL if not one of the ff: 1, 2, 3, 4, 5
	 * @param grade - student's grade for the subject (CAN BE NULL) considered NULL if not a valid grade
	 * @param yeartaken - the year when the subject was taken
	 * @param semtaken - the semester when the subject was taken
	 * @return - the row number of the added entry, otherwise -1 if unsuccessful
	 * @throws UsernameNotFoundException - if the username is not in the users table
	 * @throws SubjectNameNotFoundException - if the subject is not in the subjects table
	 */
	public long addTakenSubject(String username, String subject, int rating, float grade, int yeartaken, int semtaken) {
		ContentValues initialValues = new ContentValues();
		if (this.ifUserExists(username)) {
			initialValues.put(KEY_TAKENSUBJECTS_USERNAME, username);
		} else {
			//TO DO
			//throw UsernameNotFoundException
		}
		if (this.ifSubjectExists(subject)) {
			initialValues.put(KEY_TAKENSUBJECTS_SUBJECTID, getSubjectID(subject));
		} else {
			//TO DO
			//throw SubjectNameNotFoundException
		}
		if (rating <= 5 && rating >= 1) {
			initialValues.put(KEY_TAKENSUBJECTS_RATING, rating);
		}
		boolean valid = false;
		float integer = grade - (grade % 1);
		if (integer == 1 || integer == 2 || integer == 3 || integer == 4 || integer == 5) {
			if (grade % 1 == 0.25 || grade % 1 == 0.5 || grade % 1 == 0.75) {
				if (integer == 1 || integer == 2 || integer == 3) {
					valid = true;
				}
			} else if (grade % 1 == 0){
				valid = true;
			}
		}
		if (valid) {
			initialValues.put(KEY_TAKENSUBJECTS_GRADE, grade);
		}
		initialValues.put(KEY_TAKENSUBJECTS_YEAR, yeartaken);
		initialValues.put(KEY_TAKENSUBJECTS_SEMESTER, semtaken);
		return mDb.insert(TABLENAMES[7], null, initialValues);
	}
	
	/**
	 * Adds a new subject to a course's curriculum
	 * @param course - existing course in the courses table
	 * @param subject - existing subject in the subjects table
	 * @param yeartobetaken - the designated year the subject is to be taken based on the curriculum
	 * @param semtobetaken - the designated semester the subject is to be taken based on the curriculum
	 * @return - the row number of the added entry, otherwise -1 if unsuccessful
	 * @throws CourseNameNotFoundException - if the course is not in the courses table
	 * @throws SubjectNameNotFoundException - if the subject is not in the subjects table
	 */
	public long addCurriculumSubject(String course, String subject, int yeartobetaken, int semtobetaken) {
		ContentValues initialValues = new ContentValues();
		if (this.ifCourseExists(course)) {
			initialValues.put(KEY_CURRICULUM_COURSEID, getCourseID(course)); 
		} else {
			//TO DO
			//throw CourseNameNotFoundException
		}
		if (this.ifSubjectExists(subject)) {
			initialValues.put(KEY_CURRICULUM_SUBJECTID, getSubjectID(subject));
		} else {
			//TO DO
			//throw SubjectNameNotFoundException
		}
		initialValues.put(KEY_CURRICULUM_YEAR, yeartobetaken);
		initialValues.put(KEY_CURRICULUM_SEMESTER, semtobetaken);
		return mDb.insert(TABLENAMES[8], null, initialValues);
	}
	
	/**
	 * Checks if the college exists in the database
	 * @param name - the name of the college
	 * @return - true if the college is in the colleges table
	 */
	public boolean ifCollegeExists(String name){
		Cursor c = mDb.rawQuery("SELECT * FROM colleges WHERE name='" + name + "'", null);
		if (c.getCount() == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if the course exists in the database
	 * @param name - the name of the course
	 * @return - true if the course is in the courses table
	 */
	public boolean ifCourseExists(String name) {
		Cursor c = mDb.rawQuery("SELECT * FROM courses WHERE name='" + name + "'", null);
		if (c.getCount() == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if the subject exists in the database
	 * @param name - the name of the subject
	 * @return - true if the subject is in the subjects table
	 */
	public boolean ifSubjectExists(String name) {
		Cursor c = mDb.rawQuery("SELECT * FROM subjects WHERE name='" + name + "'", null);
		if (c.getCount() == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if the subject type exists in the database
	 * @param name - the type of the subject
	 * @return - true if the subject type is in the subjecttypes table
	 */
	public boolean ifSubjectTypeExists(String name) {
		Cursor c = mDb.rawQuery("SELECT * FROM subjecttypes WHERE name='" + name + "'", null);
		if (c.getCount() == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if the status exists in the database
	 * @param name - the student's status
	 * @return - true if the status is in the status table
	 */
	public boolean ifStatusExists(String name) {
		Cursor c = mDb.rawQuery("SELECT * FROM status WHERE name='" + name + "'", null);
		if (c.getCount() == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if a subject is a prerequisite of another
	 * @param subject - the subject to be checked of
	 * @param prerequisite - the prerequisite of the subject to be checked of
	 * @return - true if the subject has the given prerequisite subject
	 */
	public boolean ifPrerequisiteSubject(String subject, String prerequisite) {
		Cursor c = mDb.rawQuery("SELECT a.prereq_id FROM prereqs a, subjects b WHERE a.subject_id=b.subject_id AND b.name='" + subject + "'", null);
		if (c.getCount() == 0) {
			return false;
		} else {
			c.moveToFirst();
			Cursor cc = mDb.rawQuery("SELECT * FROM prereqs a, subjects b WHERE a.prereq_id=b.subject_id AND b.name='" + prerequisite + "' AND a.prereq_id=" + c.getInt(0), null);
			if (cc.getCount() == 1) {
				return true;
			}
			while (c.moveToNext()) {
				cc = mDb.rawQuery("SELECT * FROM prereqs a, subjects b WHERE a.prereq_id=b.subject_id AND b.name='" + prerequisite + "' AND a.prereq_id=" + c.getInt(0), null);
				if (cc.getCount() == 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks if the username is in the database
	 * @param username - the student's username
	 * @return - true if the username is in the users table
	 */
	public boolean ifUserExists(String username) {
		Cursor c = mDb.rawQuery("SELECT * FROM users WHERE username='" + username + "'", null);
		if (c.getCount() == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if the user already has taken the given subject
	 * @param username - the student's username
	 * @param subject - the subject to be checked if the student has taken it
	 * @return - true if the username and subject exists in the takensubjects table
	 */
	public boolean ifTakenSubject(String username, String subject) {
		Cursor c = mDb.rawQuery("SELECT subject_id FROM takensubjects WHERE username='" + username + "'", null);
		if (c.getCount() == 0) {
			return false;
		} else {
			c.moveToFirst();
			Cursor cc = mDb.rawQuery("SELECT * FROM subjects s, takensubjects t WHERE t.username='" + username + "' AND s.subject_id=" + c.getInt(0) + " AND s.name='" + subject + "'", null);
			if (cc.getCount() == 1) {
				return true;
			}
			while (c.moveToNext()) {
				cc = mDb.rawQuery("SELECT * FROM subjects s, takensubjects t WHERE t.username='" + username + "' AND s.subject_id=" + c.getInt(0) + " AND s.name='" + subject + "'", null);
				if (cc.getCount() == 1) {
					return true;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Checks if the given subject is in a course curriculum
	 * @param subject - the student's subject
	 * @param course - the student's course
	 * @return - true if the subject is in the course
	 */
	public boolean ifSubjectInCurriculum(String subject, String course) {
		Cursor c = mDb.rawQuery("SELECT subject_id FROM curriculum c, courses d WHERE c.course_id=d.course_id AND d.name='" + course + "'", null);
		if (c.getCount() == 0) {
			return false;
		} else {
			c.moveToFirst();
			Cursor cc = mDb.rawQuery("SELECT * FROM curriculum c, subjects s WHERE s.subject_id=c.subject_id AND s.name='" + subject + "' AND s.subject_id=" + c.getInt(0), null);
			if (cc.getCount() == 1) {
				return true;
			}
			while (c.moveToNext()) {
				cc = mDb.rawQuery("SELECT * FROM curriculum c, subjects s WHERE s.subject_id=c.subject_id AND s.name='" + subject + "' AND s.subject_id=" + c.getInt(0), null);
				if (cc.getCount() == 1) {
					return true;
				}
			}
		}
		return true;
	}
	
	/**
	 * Gets all the names of the colleges in the database
	 * @return a Cursor with the column 'name'
	 */
	public Cursor getAllColleges() {
		return mDb.rawQuery("SELECT name FROM " + TABLENAMES[0], null);
	}
	
	/**
	 * Gets all the names of the courses in the database
	 * @return a Cursor with the column 'name'
	 */
	public Cursor getAllCourses() {
		return mDb.rawQuery("SELECT name FROM " + TABLENAMES[1], null);
	}
	
	/**
	 * Gets all the names of the subject types in the database
	 * @return a Cursor with the column 'name'
	 */
	public Cursor getAllSubjectTypes() {
		return mDb.rawQuery("SELECT name FROM " + TABLENAMES[2], null);
	}
	
	/**
	 * Gets all the names of the statuses in the database
	 * @return a Cursor with the column 'name'
	 */
	public Cursor getAllStatuses() {
		return mDb.rawQuery("SELECT name FROM " + TABLENAMES[3], null);
	}
	
	/**
	 * Gets all the names of the users in the database
	 * @return a Cursor with the column 'name'
	 */
	public Cursor getAllUsers() {
		return mDb.rawQuery("SELECT username FROM " + TABLENAMES[4], null);
	}
	
	/**
	 * Gets all of the information of each taken subject by the specified username
	 * @param username - the student's username
	 * @return a Cursor with the columns: subject, units, type, college, rating, grade, year, semester
	 * @throws UsernameNotFoundException if the username is not in the users table
	 */
	public Cursor getAllTakenSubjects(String username) {
		Cursor c = null;
		if (this.ifUserExists(username)) {
			c = mDb.rawQuery("SELECT s.name AS subject, s.units, r.name AS type, q.name AS college, t.rating, t.grade, t.year, t.semester  FROM users u, takensubjects t, subjects s, subjecttypes r, colleges q WHERE u.username=t.username AND s.subject_id=t.subject_id AND s.type_id=r.type_id AND s.college_id=q.college_id ORDER BY t.year", null);
		} else {
			//TO DO
			//throw UsernameNotFoundException
		}
		return c;
	}
	
	/**
	 * Gets all of the information of each UNTAKEN subject by the specified username
	 * @param username - the student's username
	 * @return a Cursor with the columns: subject, units, type, college, rating, grade, year, semester
	 * @throws UsernameNotFoundException if the username is not in the users table
	 */
	public Cursor getAllUntakenSubjects(String username) {
		Cursor c = null;
		if (this.ifUserExists(username)) {
			c = mDb.rawQuery("SELECT s.name AS subject, s.units, r.name AS type, q.name AS college, t.rating, t.grade, t.year, t.semester  FROM users u, takensubjects t, subjects s, subjecttypes r, colleges q WHERE u.username=t.username AND s.subject_id!=t.subject_id AND s.type_id=r.type_id AND s.college_id=q.college_id ORDER BY t.year", null);
		} else {
			//TO DO
			//throw UsernameNotFoundException
		}
		return c;
	}
	
	/**
	 * Gets all of the information of each taken subject by the specified username and subjecttype
	 * @param username - the student's username
	 * @param subjecttype - the type of the subject
	 * @return a Cursor with the columns: subject, units, type, college, rating, grade, year, semester
	 * @throws UsernameNotFoundException if the username is not in the users table
	 */
	public Cursor getAllTakenSubjects(String username, String subjecttypes) {
		Cursor c = null;
		if (this.ifUserExists(username)) {
			c = mDb.rawQuery("SELECT s.name AS subject, s.units, r.name AS type, q.name AS college, t.rating, t.grade, t.year, t.semester  FROM users u, takensubjects t, subjects s, subjecttypes r, colleges q WHERE u.username=t.username AND s.subject_id=t.subject_id AND s.type_id=r.type_id AND s.college_id=q.college_id AND r.name='" + subjecttypes + "' ORDER BY t.year", null);
		} else {
			//TO DO
			//throw UsernameNotFoundException
		}
		return c;
	}
	
	/**
	 * Gets all of the information of each UNTAKEN subject by the specified username and subjecttype
	 * @param username - the student's username
	 * @param subjecttype - the type of the subject
	 * @return a Cursor with the columns: subject, units, type, college, rating, grade, year, semester
	 * @throws UsernameNotFoundException if the username is not in the users table
	 */
	public Cursor getAllUntakenSubjects(String username, String subjecttypes) {
		Cursor c = null;
		if (this.ifUserExists(username)) {
			c = mDb.rawQuery("SELECT s.name AS subject, s.units, r.name AS type, q.name AS college, t.rating, t.grade, t.year, t.semester  FROM users u, takensubjects t, subjects s, subjecttypes r, colleges q WHERE u.username=t.username AND s.subject_id!=t.subject_id AND s.type_id=r.type_id AND s.college_id=q.college_id AND r.name='" + subjecttypes + "' ORDER BY t.year", null);
		} else {
			//TO DO
			//throw UsernameNotFoundException
		}
		return c;
	}
	
	/**
	 * Gets all information regarding the subjects in each curriculum
	 * @param course - the curriculum of the specified course
	 * @return a Cursor with the columns: course, subject, type, units, year, semester
	 * @throws CourseNameNotFoundException if the course name is not in the courses table
	 */
	public Cursor getCurriculumSubjects(String course) {
		Cursor c = null;
		if (this.ifCourseExists(course)) {
			c = mDb.rawQuery("SELECT c.name AS course, e.name AS subject, f.name AS type, e.units, d.year, d.semester FROM courses c, curriculum d, subjects e, subjecttypes f WHERE d.course_id=c.course_id AND d.subject_id=e.subject_id AND e.type_id=f.type_id", null);
		} else {
			//TO DO
			//throw CourseNameNotFoundException
		}
		return c;
	}
	
	/**
	 * Updates a users subject rating
	 * @param username - the student's username
	 * @param subject - the subject of the student
	 * @param rating - student's rating for the subject (CAN BE NULL) considered NULL if not one of the ff: 1, 2, 3, 4, 5
	 * @return - true if the update is successful
	 * @throws SubjectNameNotFoundException if the subject is not in the subjects table
	 * @throws UsernameNotFoundException if the username is not in the users table
	 */
	public boolean updateTakenSubjectRating(String username, String subject, int rating) {
		ContentValues args = new ContentValues();
		if (this.ifUserExists(username)) {
			if (this.ifSubjectExists(subject)) {
				if (rating <= 5 && rating >= 1) {
					args.put(KEY_TAKENSUBJECTS_RATING, rating);
				}
			} else {
				//TO DO
				//throw SubjectNameNotFoundException
			}
		} else {
			//TO DO
			//throw UsernameNotFoundException
		}
		return mDb.update(TABLENAMES[7], args, KEY_TAKENSUBJECTS_USERNAME, null) > 0;
	}
	
	
	/**
	 * Updates a user's subject grade
	 * @param username - the student's username
	 * @param subject - the subject of the student
	 * @param grade - student's grade for the subject (CAN BE NULL) considered NULL if not a valid grade
	 * @return - true if the update is successful
	 * @throws SubjectNameNotFoundException if the subject is not in the subjects table
	 * @throws UsernameNotFoundException if the username is not in the users table
	 */
	public boolean updateTakenSubjectGrade(String username, String subject, float grade) {
		ContentValues args = new ContentValues();
		if (this.ifUserExists(username)) {
			if (this.ifSubjectExists(subject)) {
				boolean valid = false;
				float integer = grade - (grade % 1);
				if (integer == 1 || integer == 2 || integer == 3 || integer == 4 || integer == 5) {
					if (grade % 1 == 0.25 || grade % 1 == 0.5 || grade % 1 == 0.75) {
						if (integer == 1 || integer == 2 || integer == 3) {
							valid = true;
						}
					} else if (grade % 1 == 0){
						valid = true;
					}
				}
				if (valid) {
					args.put(KEY_TAKENSUBJECTS_GRADE, grade);
				}
			} else {
				//TO DO
				//throw SubjectNameNotFound
			}
		} else {
			//TO DO
			//throw UsernameNotFoundException
		}
		return mDb.update(TABLENAMES[7], args, KEY_TAKENSUBJECTS_USERNAME, null) > 0;
	}
	
	/**
	 * Updates a user's rating and grade of a subject 
	 * @param username - the student's username
	 * @param subject - the subject of the student
	 * @param rating - student's rating for the subject (CAN BE NULL) considered NULL if not one of the ff: 1, 2, 3, 4, 5
	 * @param grade - student's grade for the subject (CAN BE NULL) considered NULL if not a valid grade
	 * @return - true is the update is successful
	 * @throws SubjectNameNotFoundException if the subject is not in the subjects table
	 * @throws UsernameNotFoundException if the username is not in the users table
	 */
	public boolean updateTakenSubject(String username, String subject, int rating, float grade) {
		ContentValues args = new ContentValues();
		if (this.ifUserExists(username)) {
			if (this.ifSubjectExists(subject)) {
				if (rating <= 5 && rating >= 1) {
					args.put(KEY_TAKENSUBJECTS_RATING, rating);
				}
				boolean valid = false;
				float integer = grade - (grade % 1);
				if (integer == 1 || integer == 2 || integer == 3 || integer == 4 || integer == 5) {
					if (grade % 1 == 0.25 || grade % 1 == 0.5 || grade % 1 == 0.75) {
						if (integer == 1 || integer == 2 || integer == 3) {
							valid = true;
						}
					} else if (grade % 1 == 0){
						valid = true;
					}
				}
				if (valid) {
					args.put(KEY_TAKENSUBJECTS_GRADE, grade);
				}	
			} else {
				//TO DO
				//throw SubjectNameNotFoundException
			}
		} else {
			//TO DO
			//throw UsernameNotFoundException
		}
		return mDb.update(TABLENAMES[7], args, KEY_TAKENSUBJECTS_USERNAME, null) > 0;
	}
	
	/**
	 * Update's the user's real name
	 * @param username - the student's username
	 * @param realname - the student's real name
	 * @return - true if the update is successful
	 * @throws UsernameNotFoundException if the username is not in the users table
	 */
	public boolean updateUserName(String username, String realname) {
		ContentValues args = new ContentValues();
		if (this.ifUserExists(username)) {
			args.put(KEY_USERS_NAME, realname);
		} else {
			//TO DO
			//throw UsernameNotFoundException
		}
		return mDb.update(TABLENAMES[6], args, KEY_USERS_USERNAME + "=" + username, null) > 0;
	}
	
	/**
	 * Updates the user's status
	 * @param username - the student's username
	 * @param status - the student's status
	 * @return - true if the update is successful
	 * @throws UsernameNotFoundException if the username is not in the users table
	 * @throws StatusNotFoundException if the status is not in the status table
	 */
	public boolean setUserStatus(String username, String status) {
		ContentValues args = new ContentValues();
		if (this.ifStatusExists(status)) {
			if (this.ifUserExists(username)) {				
				args.put(KEY_STATUS_STATUSID, this.getStatusID(status));
			} else {
				//TO DO
				//throw UsernameNotFoundException
			}
		} else {
			//TO DO
			//throw StatusNotFoundException
		}
		return mDb.update(TABLENAMES[6], args, KEY_USERS_USERNAME + "=" + username, null) > 0;
	}
	
	/**
	 * Computes the current GWA of the student based on the student's taken grades
	 * @param username - the student's username
	 * @return a float value of the student's GWA
	 * @throws UsernameNotFoundException if the username is not in the users table
	 * @throws NoGradesToComputeException if the takensubjects table is empty for the username
	 */
	public float getOverallGWA(String username) {
		if (!this.ifUserExists(username)) {
			//TO DO
			//throw UsernameNotFoundException
		}
		Cursor c = this.getAllTakenSubjects(username);
		if (c.getCount() == 0) {
			//TO DO
			//throw NoGradesToComputeException
		}
		int totalunits = 0;
		float sum = 0;
		c.moveToFirst();
		if (!c.isNull(c.getColumnIndex("grade"))) {
			totalunits += c.getInt(c.getColumnIndex("units"));
			sum += c.getInt(c.getColumnIndex("units")) * c.getFloat(c.getColumnIndex("grade"));
		}
		while (c.moveToNext()) {
			if (!c.isNull(c.getColumnIndex("grade"))) {
				totalunits += c.getInt(c.getColumnIndex("units"));
				sum += c.getInt(c.getColumnIndex("units")) * c.getFloat(c.getColumnIndex("grade"));
			}
		}
		return sum/totalunits;
	}
	
	/**
	 * Computes the GWA for a specific year and semester
	 * @param username - the student's username
	 * @param year - the year for the computation of the GWA
	 * @param sem - the semester for the computation of the GWA
	 * @return a float value of the student's GWA on the specified year and semester
	 * @throws UsernameNotFoundException if the username is not in the users table
	 * @throws NoGradesToComputeException if the takensubjects table is empty for the username
	 */
	public float getGWAatSemester(String username, int year, int sem) {
		if (!this.ifUserExists(username)) {
			//TO DO
			//throw UsernameNotFoundException
		}
		Cursor c = this.getAllTakenSubjects(username);
		if (c.getCount() == 0) {
			//TO DO
			//throw NoGradesToComputeException
		}
		int totalunits = 0;
		float sum = 0;
		c.moveToFirst();
		if ((c.getInt(c.getColumnIndex("year")) == year) && (c.getInt(c.getColumnIndex("sem")) == sem)) {
			totalunits += c.getInt(c.getColumnIndex("units"));
			sum += c.getInt(c.getColumnIndex("units")) * c.getFloat(c.getColumnIndex("grade"));
		}
		while (c.moveToNext()) {
			if ((!c.isNull(c.getColumnIndex("grade"))) && (c.getInt(c.getColumnIndex("year")) == year) && (c.getInt(c.getColumnIndex("sem")) == sem)) {
				totalunits += c.getInt(c.getColumnIndex("units"));
				sum += c.getInt(c.getColumnIndex("units")) * c.getFloat(c.getColumnIndex("grade"));
			}
		}
		return sum/totalunits;
	}
	
	/**
	 * Checks if the username can achieve the specified grade
	 * @param username - the student's username
	 * @param grade - the specified grade to be reached
	 * @return true if the grade can be reached
	 * @throws UsernameNotFoundException if the username is not in the users table
	 * @throws NoGradesToComputeException if the takensubjects table is empty for the username
	 */
	public boolean ifPossibleLaude(String username, float grade) {
		int totalunits = 0;
		float sum = 0;
		if (this.ifUserExists(username)) {
			Cursor c1 = this.getAllTakenSubjects(username);
			Cursor c2 = this.getAllUntakenSubjects(username);
			if (c1.getCount() == 0) {
				//TO DO
				//throw NoGradesToComputeException
			}
			c1.moveToFirst();
			if (!c1.isNull(c1.getColumnIndex("grade"))) {
				totalunits += c1.getInt(c1.getColumnIndex("units"));
				sum += c1.getInt(c1.getColumnIndex("units")) * c1.getFloat(c1.getColumnIndex("grade"));
			}
			while (c1.moveToNext()) {
				if (!c1.isNull(c1.getColumnIndex("grade"))) {
					totalunits += c1.getInt(c1.getColumnIndex("units"));
					sum += c1.getInt(c1.getColumnIndex("units")) * c1.getFloat(c1.getColumnIndex("grade"));
				}
			}
			c2.moveToFirst();
			if (!c2.isNull(c2.getColumnIndex("grade"))) {
				totalunits += c2.getInt(c2.getColumnIndex("units"));
				sum += c2.getInt(c2.getColumnIndex("units"));
			}
			while (c2.moveToNext()) {
				if (!c2.isNull(c2.getColumnIndex("grade"))) {
					totalunits += c2.getInt(c2.getColumnIndex("units"));
					sum += c2.getInt(c2.getColumnIndex("units"));
				}
			}
		} else {
			//TO DO
			//throw UsernameNotFoundException
		}
		return sum/totalunits <= grade;
	}
	
	/**
	*public float getGWAperSemester(String username) {
	*	
	*}
	*/
}
