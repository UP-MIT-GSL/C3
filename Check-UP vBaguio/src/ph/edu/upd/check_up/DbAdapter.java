package ph.edu.upd.check_up;
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
	 * (3 = gsubjects)
	 * (4 = ssubjects)
	 * (5 = prereqs)
	 * (6 = status)
	 * (7 = users)
	 * (8 = takensubjects)
	 * (9 = curriculum)
	 * (10 = coreqs)
	 * (11 = session)
	 * (12 = tags)
	 * (13 = majors)
	 * (14 = nonmajors)
	 */
	public static final String[] TABLENAMES = {"colleges", "courses", "subjecttypes", "gsubjects", "ssubjects", "prereqs", "status", "users", "takensubjects", "curriculum", "coreqs", "session", "tags", "majors", "nonmajors"};
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
	//gsubjects table column names
	public static final String KEY_GSUBJECTS_SUBJECTID = "g_subject_id";
	public static final String KEY_GSUBJECTS_NAME = "name";
	//ssubjects table column names
	public static final String KEY_SSUBJECTS_SUBJECTID = "subject_id";
	public static final String KEY_SSUBJECTS_NAME = "name";
	public static final String KEY_SSUBJECTS_UNITS = "units";
	public static final String KEY_SSUBJECTS_GENERALID = "g_subject_id";
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
	public static final String KEY_USERS_WEBMAIL = "webmail";
	public static final String KEY_USERS_COURSEID = "course_id";
	public static final String KEY_USERS_STATUSID = "status_id";
	//takensubjects table column names
	public static final String KEY_TAKENSUBJECTS_USERNAME = "username";
	public static final String KEY_TAKENSUBJECTS_SUBJECTID = "subject_id";
	public static final String KEY_TAKENSUBJECTS_TEACHER = "teacher";
	public static final String KEY_TAKENSUBJECTS_RATING = "rating";
	public static final String KEY_TAKENSUBJECTS_GRADE = "grade";
	public static final String KEY_TAKENSUBJECTS_YEAR = "year";
	public static final String KEY_TAKENSUBJECTS_SEMESTER = "semester";
	//curriculum table column names
	public static final String KEY_CURRICULUM_COURSEID = "course_id";
	public static final String KEY_CURRICULUM_CURRICULUMID = "curriculum_id";
	public static final String KEY_CURRICULUM_YEAR = "year";
	//coreqs table column names
	public static final String KEY_COREQS_SUBJECTID = "subject_id";
	public static final String KEY_COREQS_PREREQID = "coreq_id";
	//session table column names
	public static final String KEY_SESSION_USERNAME = "username";
	//tags table column names
	public static final String KEY_TAGS_SUBJECTID = "subject_id";
	public static final String KEY_TAGS_TYPEID = "type_id";
	//majors table column names
	public static final String KEY_MAJORS_SUBJECTID = "subject_id";
	public static final String KEY_MAJORS_CURRICULUMID = "curriculum_id";
	public static final String KEY_MAJORS_TAKEYEAR = "takeyear";
	public static final String KEY_MAJORS_TAKESEM = "takesem";
	//nonmajors table column names
	public static final String KEY_NONMAJORS_SUBJECTID = "subject_id";
	public static final String KEY_NONMAJORS_CURRICULUMID = "curriculum_id";
	public static final String KEY_NONMAJORS_TAKEYEAR = "takeyear";
	public static final String KEY_NONMAJORS_TAKESEM = "takesem";
	
	
	private static final String TAG = "DbAdapter";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	
	/**
	 * Database schema
	 */
	private static final String DATABASE_CREATE0 =
			"CREATE TABLE 'colleges' (" +
			"	'college_id' INTEGER PRIMARY KEY AUTOINCREMENT," +
			"	'name' TEXT NOT NULL," +
			"	UNIQUE('name'));";
	private static final String DATABASE_CREATE1 =
			"CREATE TABLE 'courses' (" +
			"	'course_id' INTEGER PRIMARY KEY AUTOINCREMENT," +
			"	'name' TEXT NOT NULL," +
			"	'college_id' INTEGER," +
			"	UNIQUE('name')," +
			"	FOREIGN KEY('college_id') REFERENCES colleges('college_id'));";
	private static final String DATABASE_CREATE2 =
			"CREATE TABLE 'subjecttypes' (" +
			"	'type_id' INTEGER PRIMARY KEY AUTOINCREMENT," +
			"	'name' TEXT NOT NULL," +
			"	UNIQUE('name'));";
	private static final String DATABASE_CREATE3 =
			"CREATE TABLE 'gsubjects' (" +
			"	'g_subject_id' INTEGER PRIMARY KEY AUTOINCREMENT," +
			"	'name' TEXT NOT NULL," +
			"	UNIQUE('name'));";
	private static final String DATABASE_CREATE4 =
			"CREATE TABLE 'ssubjects' (" +
			"	's_subject_id' INTEGER PRIMARY KEY AUTOINCREMENT," +
			"	'name' TEXT NOT NULL," +
			"	'units' INTEGER NOT NULL," +
			"	'g_subject_id' INTEGER NOT NULL," +
			"	FOREIGN KEY('g_subject_id') REFERENCES gsubjects('g_subject_id'));";
	private static final String DATABASE_CREATE5 =
			"CREATE TABLE 'prereqs' (" +
			"	'subject_id' INTEGER," +
			"	'prereq_id' INTEGER," +
			"	UNIQUE('subject_id', 'prereq_id')," +
			"	FOREIGN KEY('subject_id') REFERENCES ssubjects('s_subject_id')," +
			"	FOREIGN KEY('prereq_id') REFERENCES ssubjects('s_subject_id'));";
	private static final String DATABASE_CREATE6 =
			"CREATE TABLE 'status' (" +
			"	'status_id' INTEGER PRIMARY KEY AUTOINCREMENT," +
			"	'name' TEXT NOT NULL," +
			"	UNIQUE('name'));";
	private static final String DATABASE_CREATE7 =
			"CREATE TABLE 'users' (" +
			"	'username' TEXT PRIMARY KEY," +
			"	'password' TEXT NOT NULL," +
			"	'student_no' INTEGER NOT NULL," +
			"	'name' TEXT," +
			"	'webmail' TEXT," +
			"	'course_id' INTEGER NOT NULL," +
			"	'status_id' INTEGER NOT NULL," +
			"	FOREIGN KEY('course_id') REFERENCES courses('course_id')," +
			"	FOREIGN KEY('status_id') REFERENCES status('status_id'));";
	private static final String DATABASE_CREATE8 =
			"CREATE TABLE 'takensubjects' (" +
			"	'username' TEXT NOT NULL," +
			"	'subject_id' INTEGER NOT NULL," +
			"	'teacher' TEXT," +
			"	'rating' INTEGER DEFAULT(1)," +
			"	'grade' REAL," +
			"	'year' INTEGER NOT NULL," +
			"	'semester' INTEGER NOT NULL," +
			"	FOREIGN KEY('username') REFERENCES users('username')," +
			"	FOREIGN KEY('subject_id') REFERENCES ssubjects('s_subject_id'));";
	private static final String DATABASE_CREATE9 =
			"CREATE TABLE 'curriculum' (" +
			"	'curriculum_id' INTEGER PRIMARY KEY NOT NULL," +
			"	'course_id' INTEGER NOT NULL," +
			"	'year' INTEGER," +
			"	UNIQUE('curriculum_id', 'year', 'course_id')," +
			"	FOREIGN KEY('course_id') REFERENCES courses('course_id'));";
	private static final String DATABASE_CREATE10 =
			"CREATE TABLE 'coreqs' (" +
			"	'subject_id' INTEGER," +
			"	'coreq_id' INTEGER," +
			"	UNIQUE('subject_id', 'coreq_id')," +
			"	FOREIGN KEY('subject_id') REFERENCES ssubjects('s_subject_id')," +
			"	FOREIGN KEY('coreq_id') REFERENCES ssubjects('s_subject_id'));";
	private static final String DATABASE_CREATE11 =
			"CREATE TABLE 'session' (" +
			"	'username' TEXT NOT NULL," +
			"	FOREIGN KEY('username') REFERENCES users('username'));";
	private static final String DATABASE_CREATE12 =
			"CREATE TABLE 'tags' (" +
			"	'subject_id' INTEGER NOT NULL," +
			"	'type_id' INTEGER NOT NULL," +
			"	UNIQUE('subject_id', 'type_id')," +
			"	FOREIGN KEY('subject_id') REFERENCES gsubjects('g_subject_id')," +
			"	FOREIGN KEY('type_id') REFERENCES subjecttypes('type_id'));";
	private static final String DATABASE_CREATE13 = 
			"CREATE TABLE 'majors' (" +
			"	'subject_id' INTEGER NOT NULL," +
			"	'curriculum_id' INTEGER NOT NULL," +
			"	'takeyear' INTEGER NOT NULL," +
			"	'takesem' INTEGER NOT NULL," +
			"	UNIQUE('subject_id', 'curriculum_id')," +
			"	FOREIGN KEY('subject_id'), REFERENCES ssubjects('s_subject_id')," +
			"	FOREIGN KEY('curriculum_id'), REFERENCES curriculum('curriculum_id'));";
	private static final String DATABASE_CREATE14 =
			"CREATE TABLE 'nonmajors' (" +
			"	'subject_id' INTEGER NOT NULL," +
			"	'curriculum_id' INTEGER NOT NULL," +
			"	'takeyear' INTEGER NOT NULL," +
			"	'takesem' INTEGER NOT NULL," +
			"	UNIQUE('subject_id', 'curriculum_id')," +
			"	FOREIGN KEY('subject_id'), REFERENCES gsubjects('g_subject_id')," +
			"	FOREIGN KEY('curriculum_id'), REFERENCES curriculum('curriculum_id'));";
	
	
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
			for (int i = 0; i < TABLENAMES.length; i++) {
				db.execSQL("DROP TABLE IF EXISTS " + TABLENAMES[i] + ";");
			}
			db.execSQL(DATABASE_CREATE0);
			db.execSQL(DATABASE_CREATE1);
			db.execSQL(DATABASE_CREATE2);
			db.execSQL(DATABASE_CREATE3);
			db.execSQL(DATABASE_CREATE4);
			db.execSQL(DATABASE_CREATE5);
			db.execSQL(DATABASE_CREATE6);
			db.execSQL(DATABASE_CREATE7);
			db.execSQL(DATABASE_CREATE8);
			db.execSQL(DATABASE_CREATE9);
			db.execSQL(DATABASE_CREATE10);
			db.execSQL(DATABASE_CREATE11);
			db.execSQL(DATABASE_CREATE12);
			db.execSQL(DATABASE_CREATE13);
			db.execSQL(DATABASE_CREATE14);
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
		mDb.execSQL(DATABASE_CREATE0);
		mDb.execSQL(DATABASE_CREATE1);
		mDb.execSQL(DATABASE_CREATE2);
		mDb.execSQL(DATABASE_CREATE3);
		mDb.execSQL(DATABASE_CREATE4);
		mDb.execSQL(DATABASE_CREATE5);
		mDb.execSQL(DATABASE_CREATE6);
		mDb.execSQL(DATABASE_CREATE7);
		mDb.execSQL(DATABASE_CREATE8);
		mDb.execSQL(DATABASE_CREATE9);
		mDb.execSQL(DATABASE_CREATE10);
		mDb.execSQL(DATABASE_CREATE11);
		mDb.execSQL(DATABASE_CREATE12);
		mDb.execSQL(DATABASE_CREATE13);
		mDb.execSQL(DATABASE_CREATE14);
	}
	
	
	//if methods
	/**
	 * Checks if the college exists in the database
	 * @param name - the name of the college
	 * @return - true if the college is in the colleges table
	 */
	public boolean ifCollegeExists(String name){
		Cursor c = mDb.rawQuery("SELECT * FROM colleges WHERE name='" + name + "'", null);
		if (c.getCount() <= 0) {
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
	 * Checks if the subject type exists in the database
	 * @param name - the type of the subject used for tagging in the tags table
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
	 * Checks if the general subject exists in the database
	 * @param name - the name of the subject
	 * @return - true if the subject is in the gsubjects table
	 */
	public boolean ifGeneralSubjectExists(String name) {
		Cursor c = mDb.rawQuery("SELECT * FROM gsubjects WHERE name='" + name + "'", null);
		if (c.getCount() == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if the specific subject exists in the database
	 * @param name - the name of the subject
	 * @return - true if the subject is in the ssubjects table
	 */
	public boolean ifSpecificSubjectExists(String name) {
		Cursor c = mDb.rawQuery("SELECT * FROM ssubjects WHERE name='" + name + "'", null);
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
	 * @throws - SpecificSubjectNotFoundException if one of the parameters are not in the ssubjects table
	 */
	public boolean ifPrerequisiteSubject(String subject, String prerequisite) {
		if (!this.ifSpecificSubjectExists(subject) || !this.ifSpecificSubjectExists(prerequisite)) {
			//throw SpecificSubjectNotFoundException
		}
		Cursor c = mDb.rawQuery("SELECT * FROM prereqs WHERE subject_id='" + this.getSpecificSubjectID(subject) + "' AND prereq_id='" + this.getSpecificSubjectID(prerequisite) + "'", null);
		if (c.getCount() == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if the user has already passed the prerequisites for the specified subject
	 * @param username - the user to be checked for completed prerequisites
	 * @param subject - the subject to be checked for completed prerequisites
	 * @return - true if the user has taken all the prerequisites for the specified subject
	 */
	public boolean ifTakenPrerequisites(String username, String subject) {
		int ctr = 0;
		String[] prereqs = this.getSubjectPrerequisites(subject);
		String[] taken = this.toStringArray(this.getAllTakenSubjects(username));
		for (int i = 0; i < prereqs.length; i++) {
			for (int j = 0; j < taken.length; j++) {
				if (prereqs[i].equalsIgnoreCase(taken[j])) {
					ctr++;
				}
			}
		}
		return ctr == prereqs.length;
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
		Cursor c = mDb.rawQuery("SELECT * FROM takensubjects WHERE username='" + username + "' AND subject_id=" + this.getSpecificSubjectID(subject), null);
		if (c.getCount() == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if there exists a curriculum with the specified course and year of the curriculum implementation
	 * @param course - the course to be checked for a curriculum
	 * @param year - the year that the curriculum takes effect
	 * @return - true if the course has a curriculum for the specified year
	 */
	public boolean ifCurriculumExists(String course, int year) {
		Cursor c = mDb.rawQuery("SELECT * FROM curriculum c, courses d WHERE c.course_id=d.course_id AND d.name='" + course + "' AND c.year=" + year, null);
		if (c.getCount() == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if the given subject is in a course curriculum
	 * @param subject - the student's subject
	 * @param course - the student's course
	 * @param year - the year that the curriculum takes effect
	 * @return - true if the subject is in the course's curriculum
	 * @throws SubjectNotFoundException - if the subject is not in the ssubjects table or gsubjects table
	 */
	public boolean ifSubjectInCurriculum(String subject, String course, int year) {
		if (!this.ifSpecificSubjectExists(subject) && !this.ifGeneralSubjectExists(subject) ) {
			//throw SubjectNotFoundException
		}
		String[] subjects = this.getAllCurriculumSubjects(course, year);
		for (int i = 0; i < subjects.length; i++) {
			if (subjects[i].equalsIgnoreCase(subject)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if a subject is a corequisite of another
	 * @param subject - the subject to be checked of
	 * @param prerequisite - the corequisite of the subject to be checked of
	 * @return - true if the subject has the given corequisite subject
	 */
	public boolean ifCorequisiteSubject(String subject, String corequisite) {
		Cursor c = mDb.rawQuery("SELECT a.coreq_id FROM coreqs a, ssubjects b WHERE a.subject_id=b.subject_id AND b.name='" + subject + "'", null);
		if (c.getCount() == 0) {
			return false;
		} else {
			c.moveToFirst();
			Cursor cc = mDb.rawQuery("SELECT * FROM coreqs a, ssubjects b WHERE a.coreq_id=b.subject_id AND b.name='" + corequisite + "' AND a.coreq_id=" + c.getInt(0), null);
			if (cc.getCount() == 1) {
				return true;
			}
			while (c.moveToNext()) {
				cc = mDb.rawQuery("SELECT * FROM coreqs a, ssubjects b WHERE a.coreq_id=b.subject_id AND b.name='" + corequisite + "' AND a.coreq_id=" + c.getInt(0), null);
				if (cc.getCount() == 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks if the user has already passed the corequisites for the specified subject
	 * @param username - the user to be checked for completed corequisites
	 * @param subject - the subject to be checked for completed corequisites
	 * @return - true if the user has taken all the corequisites for the specified subject
	 */
	public boolean ifTakenCorequisites(String username, String subject) {
		int ctr = 0;
		String[] coreqs = this.getSubjectCorequisites(subject);
		String[] taken = this.toStringArray(this.getAllTakenSubjects(username));
		for (int i = 0; i < coreqs.length; i++) {
			for (int j = 0; j < taken.length; j++) {
				if (coreqs[i].equalsIgnoreCase(taken[j])) {
					ctr++;
				}
			}
		}
		return ctr == coreqs.length;
	}
	
	/**
	 * Checks if the username and password is correct
	 * @param username - current user of the application
	 * @param password - the password entered by the user
	 * @return - true if the user has entered the correct password
	 */
	public boolean ifValidUser(String username, String password) {
		if (this.ifUserExists(username)) {
			Cursor c = mDb.rawQuery("SELECT username, password FROM users WHERE username='" + username + "'", null);
			if (c.getString(0).contentEquals(username) && c.getString(1).contentEquals(password)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if the subject has the tag (subjecttype) specified
	 * @param subject - the subject to be checked
	 * @param tag - the subject type to be checked
	 * @return - true if the subject has the specified subject type
	 */
	public boolean ifSubjectHasTag(String subject, String tag) {
		String[] tags = this.getAllSubjectTags(subject);
		for (int i = 0; i < tags.length; i++) {
			if (tags[i].equalsIgnoreCase(tag)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if the username can achieve the specified grade
	 * @param username - the student's username
	 * @param grade - the specified grade to be reached
	 * @return true if the grade can be reached
	 * @throws UsernameNotFoundException if the username is not in the users table
	 */
	public boolean ifPossibleLaude(String username, float grade) {
		int totalunits = 0;
		float sum = 0;
		if (this.ifUserExists(username)) {
			Cursor c1 = this.getAllTakenSubjects(username);
			Cursor c2 = this.getAllUntakenSubjects(username);

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
			//throw UsernameNotFoundException
		}
		return sum/totalunits <= grade;
	}
	
	
	//has methods
	/**
	 * Checks if the specified subjects has at least 1 prerequisite
	 * @param subject - subject to be checked of prerequisites
	 * @return
	 */
	public boolean hasPrerequisites(String subject) {
		if (this.getSubjectPrerequisites(subject).length > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the specified subjects has at least 1 corequisite
	 * @param subject - subject to be checked of prerequisites
	 * @return
	 */
	public boolean hasCorequisites(String subject) {
		if (this.getSubjectCorequisites(subject).length > 0) {
			return true;
		}
		return false;
	}
	
	
	//get methods
	/**
	 * Gets the college_id column of the specified name
	 * @param name - College name
	 * @return - college id
	 * @throws CollegeNotFoundException - if the college name is not in the colleges table
	 */
	public int getCollegeID(String name) {
		if (!this.ifCollegeExists(name)) {
			//throw CollegeNotFoundException
		}
		Cursor c = mDb.rawQuery("SELECT college_id FROM colleges WHERE name='" + name + "'", null);
		c.moveToFirst();
		return c.getInt(0);
	}
	
	/**
	 * Gets the course_id column of the specified name
	 * @param name - Course name
	 * @return - course id
	 * @throws CourseNotFoundException - if the course name is not in the courses table
	 */
	public int getCourseID(String name) {
		if (!this.ifCourseExists(name)) {
			//throw CourseNotFoundException
		}
		Cursor c = mDb.rawQuery("SELECT course_id FROM courses WHERE name='" + name + "'", null);
		c.moveToFirst();
		return c.getInt(0);
	}
	
	/**
	 * Gets the type_id column of the specified name
	 * @param name - Subject type name
	 * @return - type id
	 * @throws SubjectTypeNotFoundException - if the subject type is not in the subjecttypes table
	 */
	public int getSubjectTypeID(String name) {
		if (!this.ifSubjectTypeExists(name)) {
			//throw SubjectTypeNotFoundException
		}
		Cursor c = mDb.rawQuery("SELECT type_id FROM subjecttypes WHERE name='" + name + "'", null);
		c.moveToFirst();
		return c.getInt(0);
	}
	
	/**
	 * Gets the g_subject_id column of the specified name
	 * @param name - Subject name
	 * @return - subject id
	 * @throws GeneralSubjectNotFoundException - if the general subject name is not in the gsubjects table
	 */
	public int getGeneralSubjectID(String name) {
		if (!this.ifGeneralSubjectExists(name)) {
			//throw GeneralSubjectNotFoundException
		}
		Cursor c = mDb.rawQuery("SELECT g_subject_id FROM gsubjects WHERE name='" + name + "'", null);
		c.moveToFirst();
		return c.getInt(0);
	}
	
	/**
	 * Gets the subject_id column of the specified name
	 * @param name - Subject name
	 * @return - subject id
	 * @throws SpecificSubjectNotFoundException - if the specific subject name is not in the ssubjects table
	 */
	public int getSpecificSubjectID(String name) {
		if (!this.ifSpecificSubjectExists(name)) {
			//throw SpecificSubjectNotFoundException
		}
		Cursor c = mDb.rawQuery("SELECT subject_id FROM ssubjects WHERE name='" + name + "'", null);
		c.moveToFirst();
		return c.getInt(0);
	}	
	
	/**
	 * Gets the status_id column of the specified name
	 * @param name - Status name
	 * @return - status id
	 * @throws StatusNotFoundException - if the status name is not in the status table
	 */
	public int getStatusID(String name) {
		if (!this.ifStatusExists(name)) {
			//throw StatusNotFoundException
		}
		Cursor c = mDb.rawQuery("SELECT status_id FROM status WHERE name='" + name + "'", null);
		c.moveToFirst();
		return c.getInt(0);
	}
	
	/**
	 * Gets the curriculum_id column of the specified course and year
	 * @param course - Course name
	 * @param year - year when the curriculum is effective
	 * @return - curriculum id
	 * @throws CourseNotFoundException - if the course name is not in the courses table
	 * @throws CurriculumNotFoundException - if the course to year pair is not in the curriculum table
	 */
	public int getCurriculumID(String course, int year) {
		if (!this.ifCourseExists(course)) {
			//throw CourseNotFoundException
		}
		if (!this.ifCurriculumExists(course, year)) {
			//throw CurriculumNotFoundException
		}
		Cursor c = mDb.rawQuery("SELECT curriculum_id FROM curriculum WHERE course_id=" + this.getCourseID(course) + " AND year=" + year, null);
		c.moveToFirst();
		return c.getInt(0);
	}
	
	/**
	 * Gets the Subject types (tags) of the specified subject (only general subjects have tags)
	 * @param subject - Subject name
	 * @return - the type of the subject
	 * @throws GeneralSubjectNameNotFoundException - if the subject is not in the gsubjects table
	 */
	public String[] getSubjectTags(String subject) {
		if (!this.ifGeneralSubjectExists(subject)) {
			//throw GeneralSubjectNameNotFoundException
		}
		Cursor c = mDb.rawQuery("SELECT s.name FROM subjecttypes s, tags t WHERE s.type_id=t.type_id AND t.subject_id=" + this.getGeneralSubjectID(subject), null);
		c.moveToFirst();
		return this.toStringArray(c);
	}
	
	/**
	 * Gets the year and the semester of the subject in the course curriculum
	 * @param subject - existing subject in the curriculum table
	 * @param course - existing course in the curriculum table
	 * @param curriculum - the year of the curriculum to be used
	 * @return Cursor - with the columns year, semester
	 * @throws SubjectNotInCurriculumException - if the subject is not in the curriculum table
	 */
	public Cursor getSubjectYearSemester(String subject, String course, int curriculum) {
		if (!this.ifSubjectInCurriculum(subject, course, curriculum)) {
			//throw SubjectNotInCurriculumException
		}
		Cursor c = null;
		if (this.ifGeneralSubjectExists(subject)) {
			c = mDb.rawQuery("SELECT takeyear AS year, takesem AS semester FROM nonmajors WHERE subject_id=" + this.getGeneralSubjectID(subject) + " AND curriculum_id=" + this.getCurriculumID(course, curriculum), null);
		} else if (this.ifSpecificSubjectExists(subject)) {
			c = mDb.rawQuery("SELECT takeyear AS year, takesem AS semester FROM majors WHERE subject_id=" + this.getSpecificSubjectID(subject) + " AND curriculum_id=" + this.getCurriculumID(course, curriculum), null);
		}
		c.moveToFirst();
		return c;
	}
	
	/**
	 * Gets the prerequisites of the specified subject.
	 * @param subject - subject to be checked of prerequisites
	 * @return - names of the prerequisites. returns an empty array of none
	 * @throws SpecificSubjectNameNotFoundException - if the subject is not in the ssubjects table
	 */
	public String[] getSubjectPrerequisites(String subject) {
		if (!this.ifSpecificSubjectExists(subject)) {
			//throw SubjectNameNotFoundException
		}
		Cursor c = mDb.rawQuery("SELECT s.name FROM ssubjects s, prereqs p WHERE s.subject_id=p.prereq_id AND p.subject_id=" + getSpecificSubjectID(subject), null);
		c.moveToFirst();
		return this.toStringArray(c);
	}
	
	/**
	 * Gets the prerequisites of the specified subject.
	 * @param subject - subject to be checked of corequisites
	 * @return - names of the corequisites. returns an empty array of none
	 * @throws SpecificSubjectNameNotFoundException - if the subject is not in the ssubjects table
	 */
	public String[] getSubjectCorequisites(String subject) {
		if (!this.ifSpecificSubjectExists(subject)) {
			//throw SubjectNameNotFoundException
		}
		Cursor c = mDb.rawQuery("SELECT s.name FROM ssubjects s, coreqs p WHERE s.subject_id=p.coreq_id AND p.subject_id=" + getSpecificSubjectID(subject), null);
		c.moveToFirst();
		return this.toStringArray(c);
	}
	
	/**
	 * Gets all the names of the colleges in the database
	 * @return a String array containing the college names
	 */
	public String[] getAllColleges() {
		return this.toStringArray(mDb.rawQuery("SELECT name FROM " + TABLENAMES[0], null));
	}
	
	/**
	 * Gets all the names of the courses in the database
	 * @return a String array containing the course names
	 */
	public String[] getAllCourses() {
		return this.toStringArray(mDb.rawQuery("SELECT name FROM " + TABLENAMES[1], null));
	}
	
	/**
	 * Gets all the names of the subject types in the database
	 * @return a String array containing the subject type names
	 */
	public String[] getAllSubjectTypes() {
		return this.toStringArray(mDb.rawQuery("SELECT name FROM " + TABLENAMES[2], null));
	}
	
	/**
	 * Gets all the names of the general subjects in the database
	 * @return a String array containing the general subject names
	 */
	public String[] getAllGeneralSubjects() {
		return this.toStringArray(mDb.rawQuery("SELECT name FROM " + TABLENAMES[3], null));
	}
	
	/**
	 * Gets all the names of the specific subjects in the database
	 * @return a String array containing the general subject names
	 */
	public String[] getAllSpecificSubjects() {
		return this.toStringArray(mDb.rawQuery("SELECT name FROM " + TABLENAMES[4], null));
	}
	
	/**
	 * Gets all the names of the statuses in the database
	 * @return a String array containing the status names
	 */
	public String[] getAllStatuses() {
		return this.toStringArray(mDb.rawQuery("SELECT name FROM " + TABLENAMES[6], null));
	}
	
	/**
	 * Gets all the names of the users in the database
	 * @return a String array containing the usernames
	 */
	public String[] getAllUsers() {
		return this.toStringArray(mDb.rawQuery("SELECT username FROM " + TABLENAMES[7], null));
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
			c = mDb.rawQuery("SELECT s.name AS subject, s.units, q.name AS college, t.rating, t.grade, t.year, t.semester FROM users u, takensubjects t, ssubjects s, colleges q WHERE u.username=t.username AND s.subject_id=t.subject_id AND s.college_id=q.college_id ORDER BY t.year", null);
		} else {
			//throw UsernameNotFoundException
		}
		c.moveToFirst();
		return c;
	}
	
	/**
	 * Gets all of the information of each taken subject by the specified username, year, and semester
	 * @param username - the student's username
	 * @param year - the year of the subject taken
	 * @param sem - the semester of the subject taken
	 * @return a Cursor with the columns: subject, units, type, college, rating, grade, year, semester
	 * @throws UsernameNotFoundException if the username is not in the users table
	 */
	public Cursor getAllTakenSubjects(String username, int year, int sem) {
		Cursor c = null;
		if (this.ifUserExists(username)) {
			c = mDb.rawQuery("SELECT s.name AS subject, s.units, q.name AS college, t.rating, t.grade, t.year, t.semester  FROM users u, takensubjects t, subjects s, colleges q WHERE u.username=t.username AND s.subject_id=t.subject_id AND s.college_id=q.college_id AND t.year=" + year + " AND t.sem=" + sem + " ORDER BY t.year", null);
		} else {
			//throw UsernameNotFoundException
		}
		c.moveToFirst();
		return c;
	}
	
	/**
	 * NOT YET IMPLEMENTED
	 * Gets all of the information of each UNTAKEN subject by the specified username
	 * @param username - the student's username
	 * @return a Cursor with the columns: subject, units, type, college, rating, grade, year, semester
	 * @throws UsernameNotFoundException if the username is not in the users table
	 */
	public Cursor getAllUntakenSubjects(String username) {
		Cursor c = null;
		if (this.ifUserExists(username)) {
			//TO DO
		} else {
			//throw UsernameNotFoundException
		}
		//c.moveToFirst();
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
			c = mDb.rawQuery("SELECT s.name AS subject, s.units, q.name AS college, t.rating, t.grade, t.year, t.semester FROM users u, takensubjects t, ssubjects s, colleges q WHERE u.username=t.username AND s.subject_id=t.subject_id AND s.college_id=q.college_id ORDER BY t.year", null);
		} else {
			//throw UsernameNotFoundException
		}
		c.moveToFirst();
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
			//TO DO
		} else {
			//throw UsernameNotFoundException
		}
		//c.moveToFirst();
		return c;
	}
	
	/**
	 * Gets all the tags of the specified subject
	 * @param subject - the specific subject to be checked of tags
	 * @return a String array containing the tags of the subject. null if subject has no tags
	 * @throws GeneralSubjectNotFoundException if the general subject is not in the gsubjects table
	 */
	public String[] getAllSubjectTags(String subject) {
		if (!this.ifGeneralSubjectExists(subject)) {
			//throw GeneralSubjectNotFoundException
		}
		Cursor c = mDb.rawQuery("SELECT t.name FROM subjecttypes s, tags t, gsubjects g WHERE g.g_subject_id=t.subject_id AND t.type_id=s.type_id AND g.name='" + subject + "'", null);
		c.moveToFirst();
		return this.toStringArray(c);
	}
	
	/**
	 * Gets all the major subjects for the specified course and year of the curriculum
	 * @param course - the course of the curriculum
	 * @param year - the year the curriculum is made
	 * @return - a String array containing the major(specific) subject names. null if there are no major subjects
	 * @throws CourseNameNotFoundException if the course if not in the courses table
	 * @throws CurriculumNotFoundException if the curriculum is not in the curriculum table
	 */
	public String[] getAllCurriculumMajorSubjects(String course, int year) {
		if (!this.ifCourseExists(course)) {
			//throw CourseNameNotFoundException
		}
		if (!this.ifCurriculumExists(course, year)) {
			//throw CurriculumNotFoundException
		}
		Cursor c = mDb.rawQuery("SELECT s.name, m.takeyear, m.takesem FROM majors m, ssubjects s WHERE m.curriculum_id=" + this.getCurriculumID(course, year) + "m.subject_id=s.subject_id", null);
		c.moveToFirst();
		return this.toStringArray(c);
	}
	
	/**
	 * Gets all the minor subjects for the specified course and year of the curriculum
	 * @param course - the course of the curriculum
	 * @param year - the year the curriculum is made
	 * @return - a String array containing the minor(general) subject names. null if there are no major subjects
	 * @throws CourseNameNotFoundException if the course if not in the courses table
	 * @throws CurriculumNotFoundException if the curriculum is not in the curriculum table
	 */
	public String[] getAllCurriculumMinorSubjects(String course, int year) {
		if (!this.ifCourseExists(course)) {
			//throw CourseNameNotFoundException
		}
		if (!this.ifCurriculumExists(course, year)) {
			//throw CurriculumNotFoundException
		}
		Cursor c = mDb.rawQuery("SELECT s.name, m.takeyear, m.takesem FROM nonmajors m, ssubjects s WHERE m.curriculum_id=" + this.getCurriculumID(course, year) + "m.subject_id=s.subject_id", null);
		c.moveToFirst();
		return this.toStringArray(c);
	}
	
	
	/**
	 * Gets all information regarding the subjects in each curriculum
	 * @param course - the curriculum of the specified course
	 * @param year - the year when the curriculum takes effect
	 * @return a String array containing the major and minor subjects
	 * @throws CurriculumNotFoundException if the curriculum is not in the
	 * @throws CourseNameNotFoundException if the course name is not in the courses table
	 */
	public String[] getAllCurriculumSubjects(String course, int year) {
		String[] major = this.getAllCurriculumMajorSubjects(course, year);
		String[] minor = this.getAllCurriculumMinorSubjects(course, year);
		String[] ret = new String[major.length + minor.length];
		int ctr = 0;
		for (int i = 0; i < major.length; i++) {
			ret[ctr] = major[i];
			ctr++;
		}
		for (int i = 0; i < major.length; i++) {
			ret[ctr] = minor[i];
			ctr++;
		}
		return ret;
	}
	
	/**
	 * Gets number of units of the specified curriculum, year, & semester
	 * @param course - the course of the curriculum
	 * @param curriculumyear - the year when the curriculum is effective
	 * @param year - the year from the curriculum to be computed for the number of units
	 * @param semester the semester from the curriculum to be computed for the number of units
	 * @return int - the nuumber of units for the specified semester
	 * @throws CurriculumNotFoundException if the username is not in the users table
	 */
	public int getUnitsInSemester(String course, int curriculumyear, int year, int semester) {
		int i = 0;
		if (this.ifCurriculumExists(course, curriculumyear)) {
			Cursor c = mDb.rawQuery("SELECT COUNT(*) FROM majors m, ssubjects s WHERE m.subject_id=s.subject_id AND m.curriculum_id=" + this.getCurriculumID(course, curriculumyear) + " AND m.takeyear=" + year + " AND m.takesem=" + semester, null);
			Cursor d = mDb.rawQuery("SELECT COUNT(*) FROM nonmajors m, gsubjects s WHERE m.subject_id=s.g_subject_id AND m.curriculum_id=" + this.getCurriculumID(course, curriculumyear) + " AND m.takeyear=" + year + " AND m.takesem=" + semester, null);
			i += c.getCount();
			i =+ d.getCount();
		} else {
			//throw CurriculumNotFoundException
		}
		return i;
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
			//throw UsernameNotFoundException
		}
		Cursor c = this.getAllTakenSubjects(username);
		if (c.getCount() == 0) {
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
			//throw UsernameNotFoundException
		}
		Cursor c = this.getAllTakenSubjects(username);
		if (c.getCount() == 0) {
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
	
	
	//add methods
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
		if (this.ifCollegeExists(college)) {
			initialValues.put(KEY_COURSES_NAME, name);
			initialValues.put(KEY_COURSES_COLLEGEID, getCollegeID(college));
		}
		return mDb.insert(TABLENAMES[1], null, initialValues);
	}
	
	/**
	 * Adds a new subject type
	 * @param name - Subject type name
	 * @return - the row number of the added entry, otherwise -1 if unsuccessful
	 */
	public long addSubjectType(String name) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_SUBJECTTYPES_NAME, name);
		return mDb.insert(TABLENAMES[2], null, initialValues);
	}
	
	/**
	 * Adds a new general subject
	 * @param name - general subject name
	 * @return - the row number of the added entry, otherwise -1 if unsuccessful
	 */
	public long addGeneralSubject(String name) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_GSUBJECTS_NAME, name);
		return mDb.insert(TABLENAMES[3], null, initialValues);
	}
	
	/**
	 * Adds a new specific subject
	 * @param specificname - the specific Subject name (ie. CS 172, Math 17, etc.)
	 * @param units - Subject's number of units
	 * @param generalname - existing generalname in the gsubjects table (ie. Major, GE, PE, CWTS, etc.)
	 * @return - the row number of the added entry, otherwise -1 if unsuccessful
	 * @throws GeneralSubjectNameNotFoundException if the generalname is not in the gsubjects table
	 */
	public long addSpecificSubject(String specificname, int units, String generalname) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_SSUBJECTS_NAME, specificname);
		initialValues.put(KEY_SSUBJECTS_UNITS, units);
		if (!this.ifGeneralSubjectExists(generalname)) {
			//throw GeneralSubjectNameNotFoundException
		}
		initialValues.put(KEY_SSUBJECTS_GENERALID, this.getGeneralSubjectID(generalname));
		return mDb.insert(TABLENAMES[4], null, initialValues);
	}
	
	/**
	 * Adds a prequisite to a subject
	 * @param name - existing subject in the subjects table
	 * @param prerequisite - existing subject in the subjects table
	 * @return - the row number of the added entry, otherwise -1 if unsuccessful
	 * @throws SubjectNameNotFoundException if either the subject or prerequisite is not in the subjects table
	 */
	public long addPrerequisite(String name, String prerequisite) {
		ContentValues initialValues = new ContentValues();
		if (this.ifSpecificSubjectExists(name) || this.ifSpecificSubjectExists(prerequisite)) {
			initialValues.put(KEY_PREREQS_SUBJECTID, getSpecificSubjectID(name));
			initialValues.put(KEY_PREREQS_PREREQID, getSpecificSubjectID(prerequisite));
		} else {
			//throw SubjectNameNotFoundException
		}
		return mDb.insert(TABLENAMES[5], null, initialValues);
	}
	
	/**
	 * Adds a new status
	 * @param name - a user status (ie. Regular, Graduating, etc.)
	 * @return - the row number of the added entry, otherwise -1 if unsuccessful
	 */
	public long addStatus(String name) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_STATUS_NAME, name);
		return mDb.insert(TABLENAMES[6], null, initialValues);
	}
	
	/**
	 * Adds a new user
	 * @param username - student's username (preferably crs username)
	 * @param password - student's password
	 * @param studentno - student's student number (ie. 201200100)
	 * @param realname - student's real name
	 * @param course - existing course in the courses table
	 * @param status - existing status is the status table
	 * @param webmail - the student's CRS webmail
	 * @return - the row number of the added entry, otherwise -1 if unsuccessful
	 * @throws CourseNameNotFoundException if the course is not in the courses table
	 * @throws StatusNotFoundException if the status is not in the status table
	 */
	public long addUser(String username, String password, int studentno, String realname, String course, String status, String webmail) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_USERS_USERNAME, username);
		initialValues.put(KEY_USERS_PASSWORD, password);
		initialValues.put(KEY_USERS_STUDENTNO, studentno);
		initialValues.put(KEY_USERS_NAME, realname);
		initialValues.put(KEY_USERS_WEBMAIL, webmail);
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
		return mDb.insert(TABLENAMES[7], null, initialValues);
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
	 * @throws UsernameNotFoundException if the username is not in the users table
	 * @throws SubjectNameNotFoundException if the subject is not in the subjects table
	 */
	public long addTakenSubject(String username, String subject, int rating, float grade, int yeartaken, int semtaken) {
		ContentValues initialValues = new ContentValues();
		if (this.ifUserExists(username)) {
			initialValues.put(KEY_TAKENSUBJECTS_USERNAME, username);
		} else {
			//TO DO
			//throw UsernameNotFoundException
		}
		if (this.ifSpecificSubjectExists(subject)) {
			initialValues.put(KEY_TAKENSUBJECTS_SUBJECTID, getSpecificSubjectID(subject));
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
		return mDb.insert(TABLENAMES[8], null, initialValues);
	}
	
	/**
	 * Adds a new curriculum to a course
	 * @param course - the course to be added for a new curriculum
	 * @param year - the year when the curriculum is effective
	 * @return - the row number of the added entry, otherwise -1 if unsuccessful
	 * @throws CourseNameNotFoundException if the the course is not in the courses table
	 */
	public long addCurriculum(String course, int year) {
		ContentValues initialValues = new ContentValues();
		if (!this.ifCourseExists(course)) {
			//throw CourseNameNotFoundException
		}
		initialValues.put(KEY_CURRICULUM_COURSEID, this.getCourseID(course));
		initialValues.put(KEY_CURRICULUM_YEAR, year);
		return mDb.insert(TABLENAMES[9], null, initialValues);
	}
	
	/**
	 * Adds a corequisite to a subject
	 * @param name - existing subject in the ssubjects table
	 * @param corequisite - existing subject in the ssubjects table
	 * @return - the row number of the added entry, otherwise -1 if unsuccessful
	 * @throws SubjectNameNotFoundException if either the subject or corequisite is not in the subjects table
	 */
	public long addCorequisite(String name, String corequisite) {
		ContentValues initialValues = new ContentValues();
		if (this.ifSpecificSubjectExists(name) || this.ifSpecificSubjectExists(corequisite)) {
			initialValues.put(KEY_PREREQS_SUBJECTID, getSpecificSubjectID(name));
			initialValues.put(KEY_PREREQS_PREREQID, getSpecificSubjectID(corequisite));
		} else {
			//throw SubjectNameNotFoundException
		}
		return mDb.insert(TABLENAMES[10], null, initialValues);
	}
	
	public long addSession(String username) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_SESSION_USERNAME, username);
		return mDb.insert(TABLENAMES[11], null, initialValues);
	}
	
	/**
	 * Adds a tag to a general subject
	 * @param subject - the subject to be assigned to a tag
	 * @param tag - the subject tag(type) to assign
	 * @return - the row number of the added entry, otherwise -1 if unsuccessful
	 * @throws GeneralSubjectNameNotFoundException if the subject is not in the gsubjects table
	 * @throws SubjectTypeNotFoundException if the tag is not in the subjecttypes table
	 */
	public long addTag(String subject, String tag) {
		if (!this.ifGeneralSubjectExists(subject)) {
			//throw GeneralSubjectNameNotFoundException
		}
		if (!this.ifSubjectTypeExists(tag)) {
			//throw SubjectTypeNotFoundException
		}
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TAGS_SUBJECTID, this.getGeneralSubjectID(subject));
		initialValues.put(KEY_TAGS_TYPEID, this.getSubjectTypeID(tag));
		return mDb.insert(TABLENAMES[12], null, initialValues);
	}
	
	/**
	 * Adds a new major subject to a course's curriculum
	 * @param course - existing course in the courses table
	 * @param year - the year of the curriculum to be followed
	 * @param subject - existing subject in the subjects table CAN BE NULL
	 * @param yeartobetaken - the designated year the subject is to be taken based on the curriculum
	 * @param semtobetaken - the designated semester the subject is to be taken based on the curriculum
	 * @return - the row number of the added entry, otherwise -1 if unsuccessful
	 * @throws CourseNameNotFoundException if the course is not in the courses table
	 * @throws CurriculumNotFoundException if the curriculum is not in the curriculum table
	 * @throws SpecificSubjectNameNotFoundException if the subject is not in the ssubjects table
	 */
	public long addCurriculumMajorSubject(String course, int year, String subject, int yeartobetaken, int semtobetaken) {
		ContentValues initialValues = new ContentValues();
		if (!this.ifCourseExists(course)) {
			//throw CourseNameNotFoundException
		}
		if (!this.ifCurriculumExists(course, year)) {
			//throw CurriculumNotFoundException
		}
		if (!this.ifSpecificSubjectExists(subject)) {
			//throw SpecificSubjectNameNotFoundException
		}
		initialValues.put(KEY_MAJORS_CURRICULUMID, this.getCurriculumID(course, year));
		initialValues.put(KEY_MAJORS_SUBJECTID, this.getSpecificSubjectID(subject));
		initialValues.put(KEY_MAJORS_TAKESEM, semtobetaken);
		initialValues.put(KEY_MAJORS_TAKEYEAR, yeartobetaken);
		return mDb.insert(TABLENAMES[13], null, initialValues);
	}
	
	/**
	 * Adds a new minor(nonmajor) subject to a course's curriculum
	 * @param course - existing course in the courses table
	 * @param year - the year of the curriculum to be followed
	 * @param subject - existing subject in the subjects table CAN BE NULL
	 * @param yeartobetaken - the designated year the subject is to be taken based on the curriculum
	 * @param semtobetaken - the designated semester the subject is to be taken based on the curriculum
	 * @return - the row number of the added entry, otherwise -1 if unsuccessful
	 * @throws CourseNameNotFoundException if the course is not in the courses table
	 * @throws CurriculumNotFoundException if the curriculum is not in the curriculum table
	 * @throws GeneralSubjectNameNotFoundException if the subject is not in the gsubjects table
	 */
	public long addCurriculumMinorSubject(String course, int year, String subject, int yeartobetaken, int semtobetaken) {
		ContentValues initialValues = new ContentValues();
		if (!this.ifCourseExists(course)) {
			//throw CourseNameNotFoundException
		}
		if (!this.ifCurriculumExists(course, year)) {
			//throw CurriculumNotFoundException
		}
		if (!this.ifGeneralSubjectExists(subject)) {
			//throw GeneralSubjectNameNotFoundException
		}
		initialValues.put(KEY_NONMAJORS_CURRICULUMID, this.getCurriculumID(course, year));
		initialValues.put(KEY_NONMAJORS_SUBJECTID, this.getGeneralSubjectID(subject));
		initialValues.put(KEY_NONMAJORS_TAKESEM, semtobetaken);
		initialValues.put(KEY_NONMAJORS_TAKEYEAR, yeartobetaken);
		return mDb.insert(TABLENAMES[14], null, initialValues);
	}
	
	//update & set methods
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
			if (this.ifSpecificSubjectExists(subject)) {
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
		return mDb.update(TABLENAMES[8], args, KEY_TAKENSUBJECTS_USERNAME, null) > 0;
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
			if (this.ifSpecificSubjectExists(subject)) {
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
		return mDb.update(TABLENAMES[8], args, KEY_TAKENSUBJECTS_USERNAME, null) > 0;
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
			if (this.ifSpecificSubjectExists(subject)) {
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
		return mDb.update(TABLENAMES[8], args, KEY_TAKENSUBJECTS_USERNAME, null) > 0;
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
		return mDb.update(TABLENAMES[7], args, KEY_USERS_USERNAME + "=" + username, null) > 0;
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
		return mDb.update(TABLENAMES[7], args, KEY_USERS_USERNAME + "=" + username, null) > 0;
	}

	
	//misc. methods
	/**
	 * Gets all the data in the first column and returns all data in a String array
	 * @param c - the cursor to be converted
	 * @return - String array of the first column of the cursor
	 */
	public String[] toStringArray(Cursor c) {
		if (c.getCount() == 0) {
			return null;
		}
		String[] array = new String[c.getCount()];
		c.moveToFirst();
		for (int i = 0; i < array.length; i++) {
			array[i] = c.getString(0);
			c.moveToNext();
		}
		return array;
	}
}
