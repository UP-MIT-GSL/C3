CREATE TABLE "colleges" (
	"college_id" INTEGER PRIMARY KEY AUTOINCREMENT,
	"name" TEXT NOT NULL,
	UNIQUE("name")
);

CREATE TABLE "courses" (
	"course_id" INTEGER PRIMARY KEY AUTOINCREMENT,
	"name" TEXT NOT NULL,
	"college_id" INTEGER,
	UNIQUE("name"),
	FOREIGN KEY("college_id") REFERENCES colleges("college_id")
);

CREATE TABLE "subjecttypes" (
	"type_id" INTEGER PRIMARY KEY AUTOINCREMENT,
	"name" TEXT NOT NULL,
	UNIQUE("name")
);

CREATE TABLE "gsubjects" (
	"g_subject_id" INTEGER PRIMARY KEY AUTOINCREMENT,
	"name" TEXT NOT NULL,
	UNIQUE("name")
);

CREATE TABLE "ssubjects" (
	"s_subject_id" INTEGER PRIMARY KEY AUTOINCREMENT,
	"name" TEXT NOT NULL,
	"units" INTEGER NOT NULL,
	"g_subject_id" INTEGER NOT NULL,
	UNIQUE("name"),
	FOREIGN KEY("g_subject_id") REFERENCES gsubjects("g_subject_id")
);

CREATE TABLE "prereqs" (
	"subject_id" INTEGER,
	"prereq_id" INTEGER,
	UNIQUE("subject_id","prereq_id"),
	FOREIGN KEY("subject_id") REFERENCES ssubjects("s_subject_id"),
	FOREIGN KEY("prereq_id") REFERENCES ssubjects("s_subject_id")
);

CREATE TABLE "status" (
	"status_id" INTEGER PRIMARY KEY AUTOINCREMENT,
	"name" TEXT NOT NULL,
	UNIQUE("name")
);

CREATE TABLE "users" (
	"username" TEXT PRIMARY KEY,
	"password" TEXT NOT NULL,
	"student_no" INTEGER NOT NULL,
	"name" TEXT,
	"webmail" TEXT, 
	"course_id" INTEGER NOT NULL,
	"status_id" INTEGER NOT NULL,
	FOREIGN KEY("course_id") REFERENCES courses("course_id"),
	FOREIGN KEY("status_id") REFERENCES status("status_id")
);

CREATE TABLE "takensubjects" (
	"username" TEXT NOT NULL,
	"subject_id" INTEGER NOT NULL,
	"teacher" TEXT,
	"rating" INTEGER DEFAULT(1),
	"grade" REAL,
	"year" INTEGER NOT NULL,
	"semester" INTEGER NOT NULL,
	FOREIGN KEY("username") REFERENCES users("username"),
	FOREIGN KEY("subject_id") REFERENCES ssubjects("s_subject_id")
);

CREATE TABLE "curriculum" (
	"curriculum_id" INTEGER PRIMARY KEY AUTOINCREMENT,
	"course_id" INTEGER NOT NULL,
	"year" INTEGER NOT NULL,
	UNIQUE("curriculum_id", "year", "course_id"),
	FOREIGN KEY("course_id") REFERENCES courses("course_id")
);

CREATE TABLE "coreqs" (
	"subject_id" INTEGER,
	"coreq_id" INTEGER,
	UNIQUE("subject_id","coreq_id"),
	FOREIGN KEY("subject_id") REFERENCES ssubjects("s_subject_id"),
	FOREIGN KEY("coreq_id") REFERENCES ssubjects("s_subject_id")
);

CREATE TABLE "session" (
	"username" TEXT NOT NULL,
	FOREIGN KEY("username") REFERENCES users("username")
);

CREATE TABLE "tags" (
	"subject_id" INTEGER NOT NULL,
	"type_id" INTEGER NOT NULL,
	UNIQUE("subject_id", "type_id"),
	FOREIGN KEY("subject_id") REFERENCES gsubjects("g_subject_id"),
	FOREIGN KEY("type_id") REFERENCES subjecttypes("type_id")
);

CREATE TABLE "majors" (
	"subject_id" INTEGER NOT NULL,
	"curriculum_id" INTEGER NOT NULL,
	"takeyear" INTEGER NOT NULL,
	"takesem" INTEGER NOT NULL,
	UNIQUE("subject_id", "curriculum_id"),
	FOREIGN KEY("subject_id") REFERENCES ssubjects("s_subject_id"),
	FOREIGN KEY("curriculum_id") REFERENCES curriculum("curriculum_id")
);

CREATE TABLE "nonmajors" (
	"subject_id" INTEGER NOT NULL,
	"curriculum_id" INTEGER NOT NULL,
	"takeyear" INTEGER NOT NULL,
	"takesem" INTEGER NOT NULL,
	UNIQUE("subject_id", "curriculum_id"),
	FOREIGN KEY("subject_id") REFERENCES gsubjects("g_subject_id"),
	FOREIGN KEY("curriculum_id") REFERENCES curriculum("curriculum_id")
);