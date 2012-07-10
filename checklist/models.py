from django.db import models

'''
class Campus(models.Model):
    name = models.CharField(max_length=64)
    foundation_year = models.IntegerField()

class DegreeGrantingUnit(models.Model):
    name = models.CharField(max_length=128)
    foundation_year = models.IntegerField()
    campus = models.ForeignKey(Campus)
    
class Department(models.Model):
    name = models.CharField(max_length=128)
    unit = models.ForeignKey(DegreeGrantingUnit)

class Program(models.Model):
    name = models.CharField(max_length=128) # Computer Science, etc.
    degree = models.CharField(max_length=128) # BS, Diploma (?), etc.
    department = models.ForeignKey(Department)

class Semester(models.Model):
    year = models.IntegerKey() # 1, 2, 3, 4, 5
    semester = models.IntegerKey() # 1, 2, 3 (Summer)
    program = models.ForeignKey(Program)

class Subject(models.Model):
    name = models.CharField(max_length=128)
    units = models.IntegerKey() # 1, 3, 4, 5
'''