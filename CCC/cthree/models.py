from django.db import models
from django.contrib import admin
from django.contrib.auth.models import User
# Create your models here.
class UserLink(models.Model):
    from_user = models.ForeignKey(User, related_name="from_user")
    to_user = models.ForeignKey(User, related_name="to_user")
    date_added = models.DateField()

    def __unicode__(self):
        return u'%s is following %s' % (self.from_user.username, self.to_user.username)

    def save(self, *args, **kwargs):
        if (self.from_user.username != self.to_user.username):
            super(UserLink, self).save(*args, **kwargs)
        else:
            raise Error

    class Meta:
        unique_together = ("from_user", "to_user")

admin.site.register(UserLink)