package com.example.note;
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
	 */
	public static final String[] TABLENAMES = {"colleges", "courses", "subjecttypes", "subjects", "prereqs", "status", "users"};
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
				db.execSQL("DROP TABLE IF EXISTS " + TABLENAMES[i] + ");");
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
	
	public Cursor getAllTakenSubjects(String username) {
		//TO DO
		return null;
	}
	
	public Cursor getCurriculumSubjects(String course) {
		//TO DO
		return null;
	}
	
	public boolean updateTakenSubjectRating(String username, String subject, int rating) {
		//TO DO
		return false;
	}
	
	public boolean updateTakenSubjectGrade(String username, String subject, float grade) {
		//TO DO
		return false;
	}
	
	public boolean updateTakenSubject(String username, String subject, int rating, float grade) {
		//TO DO
		return false;
	}
	
	public boolean setUserName(String realname) {
		//TO DO
		return false;
	}
	
	public boolean setUserStatus(String status) {
		//TO DO
		return false;
	}
	
	
	//sample for updating tables
	/**
	public boolean updateNote(long rowId, String title, String body) {
		ContentValues args = new ContentValues();
		args.put(KEY_TITLE, title);
		args.put(KEY_BODY, body);
		
		return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}
	*/
}
