from checklist.models import *
from django.contrib import admin

# college admin
class CourseInline(admin.TabularInline):
    model = Course
    fields = ['degree', 'name']
class CollegeAdmin(admin.ModelAdmin):
    inlines = [CourseInline]
admin.site.register(College, CollegeAdmin)

# course admin
class CurriculumInline(admin.TabularInline):
    model = Curriculum
class CourseAdmin(admin.ModelAdmin):
    fields = ('college', 'degree', 'name')
    list_display = ('__unicode__', 'college', 'degree', 'name')
    list_display_links = ('__unicode__',)
    list_editable = ('college', 'degree', 'name')
    inlines = [CurriculumInline]
admin.site.register(Course, CourseAdmin)

# curriculum admin
class ElectiveInline(admin.TabularInline):
    model = Curriculum.general_subjects.through
    fields = ('semester', 'semester_year', 'general_subject')
class MajorInline(admin.TabularInline):
    model = Curriculum.specific_subjects.through
    fields = ('semester', 'semester_year', 'specific_subject')
class CurriculumAdmin(admin.ModelAdmin):
    list_display = ('__unicode__', 'course', 'start_year')
    list_display_links = ('__unicode__',)
    list_editable = ('course', 'start_year',)
    inlines = [ElectiveInline, MajorInline]
admin.site.register(Curriculum, CurriculumAdmin)

# general subject admin
class SpecificSubjectInline(admin.TabularInline):
    model = SpecificSubject
class GeneralSubjectAdmin(admin.ModelAdmin):
    list_display = (
        '__unicode__',
        'type',
        'is_ah', 'is_mst', 'is_ssp',
        'is_english_communication', 'is_philippine_studies', 'is_philosophy',
        'other_elective',
        'units',
    )
    list_display_links = ('__unicode__',)
    list_editable = (
        'type',
        'is_ah', 'is_mst', 'is_ssp',
        'is_english_communication', 'is_philippine_studies', 'is_philosophy',
        'other_elective',
        'units'
    )
    inlines = [SpecificSubjectInline, ElectiveInline]
admin.site.register(GeneralSubject, GeneralSubjectAdmin)

# specific subject admin
class PrerequisiteInline(admin.TabularInline):
    model = Prerequisite
    fk_name = 'prerequisite'
class CorequisiteInline(admin.TabularInline):
    model = Corequisite
    fk_name = 'corequisite'
class SpecificSubjectAdmin(admin.ModelAdmin):
    list_display = ('__unicode__', 'name', 'general_subject')
    list_display_links = ('__unicode__',)
    list_editable = ('name', 'general_subject')
    inlines = [PrerequisiteInline, CorequisiteInline, MajorInline]
admin.site.register(SpecificSubject, SpecificSubjectAdmin)

# user admin
class TakenSubjectInline(admin.TabularInline):
    model = TakenSubject
class UserAdmin(admin.ModelAdmin):
    list_display = ('__unicode__', 'username', 'email', 'course', 'student_no_year', 'status')
    list_display_links = ('__unicode__',)
    list_editable = ('email', 'course', 'student_no_year', 'status')
    inlines = [TakenSubjectInline]
admin.site.register(User, UserAdmin)