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

CREATE TABLE "subjects" (
	"subject_id" INTEGER PRIMARY KEY AUTOINCREMENT,
	"name" TEXT NOT NULL,
	"units" INTEGER NOT NULL,
	"type_id" INTEGER NOT NULL,
	"college_id" INTEGER NOT NULL,
	FOREIGN KEY("type_id") REFERENCES subjecttypes("type_id"),
	FOREIGN KEY("college_id") REFERENCES colleges("college_id")
);

CREATE TABLE "prereqs" (
	"subject_id" INTEGER,
	"prereq_id" INTEGER,
	UNIQUE("subject_id","prereq_id"),
	FOREIGN KEY("subject_id") REFERENCES subjects("subject_id"),
	FOREIGN KEY("prereq_id") REFERENCES subjects("subject_id")
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
	FOREIGN KEY("subject_id") REFERENCES subjects("subject_id")
);

CREATE TABLE "curriculum" (
	"course_id" INTEGER NOT NULL,
	"subject_id" INTEGER NOT NULL,
	"year" INTEGER,
	"semester" INTEGER,
	FOREIGN KEY("course_id") REFERENCES courses("course_id"),
	FOREIGN KEY("subject_id") REFERENCES subjects("subject_id")
);