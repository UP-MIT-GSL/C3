from django.db import models

class College(models.Model):
    name = models.CharField(max_length=128, unique=True)
    
    def __unicode__(self):
        return self.name
        
    class Meta:
        ordering = ['name']

class Course(models.Model):
    DEGREES = (
        (u'B', u'B'),
        (u'BA', u'BA'),
        (u'BS', u'BS'),
        (u'C', u'Certificate'),
        (u'D', u'Diploma'),
        (u'M', u'M'),
        (u'MA', u'MA'),
        (u'MS', u'MS'),
        (u'PHD', u'PhD'),
    )
    
    college = models.ForeignKey(College)
    name = models.CharField(max_length=128)
    degree = models.CharField(max_length=3, choices=DEGREES, default='BS')
    
    def __unicode__(self):
        return u"%s %s" % (self.degree, self.name)
    
    class Meta:
        unique_together = ('degree', 'name')
        ordering = ['college', 'degree', 'name']

class Curriculum(models.Model):
    course = models.ForeignKey(Course)
    start_year = models.IntegerField()
    general_subjects = models.ManyToManyField('GeneralSubject', through='Elective')
    specific_subjects = models.ManyToManyField('SpecificSubject', through = 'Major')
    
    def __unicode__(self):
        return u"%d %s" % (self.start_year, self.course)
    
    class Meta:
        unique_together = ('course', 'start_year')
        ordering = ['course', 'start_year']
    
class Major(models.Model):    
    curriculum = models.ForeignKey(Curriculum)
    specific_subject = models.ForeignKey('SpecificSubject')
    semester = models.IntegerField()
    semester_year = models.IntegerField()

class Elective(models.Model):    
    curriculum = models.ForeignKey(Curriculum)
    general_subject = models.ForeignKey('GeneralSubject')
    semester = models.IntegerField()
    semester_year = models.IntegerField()
    
class SpecificSubject(models.Model):
    name = models.CharField(max_length=64, unique=True)
    general_subject = models.ForeignKey('GeneralSubject')
    
    def __unicode__(self):
        return self.name
        
    class Meta:
        ordering = ['name']
    
class GeneralSubject(models.Model):
    units = models.IntegerField()
    
    GENERAL_SUBJECT_TYPES = (
        ('M', 'Major'),
        ('FE', 'Free Elective'),
        ('GE', 'General Elective'),
        ('OE', 'Other Elective'),
        ('PE', 'Physical Education'),
        ('NSTP', 'National Service Training Program')
    )
    
    type = models.CharField(max_length=4, choices=GENERAL_SUBJECT_TYPES)
    other_elective = models.CharField(max_length=32, blank=True, null=True)
    
    is_ah = models.BooleanField('AH')
    is_mst = models.BooleanField('MST')
    is_ssp = models.BooleanField('SSP')
    is_english_communication = models.BooleanField('English/Communication')
    is_philippine_studies = models.BooleanField('Philippine Studies')
    is_philosophy = models.BooleanField('Philosophy')
    
    def __unicode__(self):
        description = self.get_type_display()
        if self.type == 'OE':
            description += self.other_elective
        elif self.type == 'GE':
            if self.is_ah: description += ' AH'
            if self.is_mst: description += ' MST'
            if self.is_ssp: description += ' SSP'
        if self.type == 'OE' or self.type == 'GE':
            if self.is_english_communication: description += ' English/Communication'
            if self.is_philippine_studies: description += ' Philippine Studies'
            if self.is_philosophy: description += ' Philosophy'
        description += u" (%d Units)" % (self.units)
        return description
    
    class Meta:
        unique_together = (
            'type',
            'is_ah', 'is_mst', 'is_ssp',
            'is_english_communication', 'is_philippine_studies', 'is_philosophy',
            'other_elective', 'units'
        )
        ordering = [
            'type',
            '-is_ah', '-is_mst', '-is_ssp',
            '-is_english_communication', '-is_philippine_studies', '-is_philosophy',
            'other_elective', 'units'
        ]
        
class Prerequisite(models.Model):
    prerequisite = models.ForeignKey(SpecificSubject, related_name='sub_prereq')
    specific_subject = models.ForeignKey(SpecificSubject, related_name='sub_needing_prereq')
        
    def __unicode__(self):
        return u"%s precedes %s" % (self.prerequisite, self.specific_subject.name)
    
    class Meta:
        unique_together = ('prerequisite', 'specific_subject')
        ordering = ['specific_subject', 'prerequisite']

class Corequisite(models.Model):
    corequisite = models.ForeignKey(SpecificSubject, related_name='sub_coreq')
    specific_subject = models.ForeignKey(SpecificSubject, related_name='sub_needing_coreq')
    
    def __unicode__(self):
        return u"%s requires %s" % (self.corequisite, self.specific_subject.name)
    
    class Meta:
        unique_together = ('corequisite', 'specific_subject')
        ordering = ['specific_subject', 'corequisite']
        
class User(models.Model):
    username = models.CharField(max_length=32, primary_key=True)
    email = models.EmailField(max_length=64, unique=True)
    password = models.CharField(max_length=32)
    name = models.CharField(max_length=64)
    student_no_year = models.IntegerField()
    course = models.ForeignKey(Course)
    
    USER_STATUS_TYPES = (
        ('REG', 'Regular'),
        ('NM', 'Non-Major'),
        ('ND', 'Non-Degree'),
        ('LOA', 'Leave of Absence'),
        ('RES', 'Residency'),
        ('G', 'Graduating'),
    )
    
    status = models.CharField(max_length=3, choices=USER_STATUS_TYPES)
    
    def __unicode__(self):
        return u"%s (%s)" % (self.username, self.name)
    
    class Meta:
        ordering = ['username']
    
class TakenSubject(models.Model):
    user = models.ForeignKey(User)
    specific_subject = models.ForeignKey(SpecificSubject, related_name='taken_subject')
    
    semester = models.IntegerField()
    semester_year = models.IntegerField()

    RATINGS = (
        (1, '1 Star'),
        (2, '2 Stars'),
        (3, '3 Stars'),
        (4, '4 Stars'),
        (5, '5 Stars'),
    )
    
    GRADES = (
        (1.00, '1.00'),
        (1.25, '1.25'),
        (1.50, '1.50'),
        (1.75, '1.75'),
        (2.00, '2.00'),
        (2.25, '2.25'),
        (2.50, '2.50'),
        (2.75, '2.75'),
        (3.00, '3.00'),
        (4.00, '4.00'),
        (5.00, '5.00'),
    )
    
    rating = models.IntegerField(choices=RATINGS)
    grade = models.DecimalField(max_digits=3, decimal_places=2, choices=GRADES)
    
    def __unicode__(self):
        return self.specific_subject.name
    class Meta:
        ordering = ['user', 'semester_year', 'semester', 'specific_subject']