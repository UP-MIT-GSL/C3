from django.http import HttpResponse
from django.contrib.auth.models import User
from friends.models import *
from django.views.decorators.csrf import csrf_exempt, csrf_protect
import re

def profile(request):
    userprofile = getUsername(request)
    output = userprofile + '\'s profile does not exist'
    if 'username' in request.session:
        if ifUserexists(userprofile):
            output = 'You are not following ' + userprofile + ' you can\'t view his/her profile' 
            user = getUser(request.session['username'])
            fromusers = user.from_user.get_query_set()
            pattern = ' is following (?P<follow>\w+)'
            followinglist = []
            for fromuser in fromusers:
                if (None == re.search(pattern, str(fromuser))):
                    print ''
                r1 = re.search(pattern, str(fromuser))
                followinglist.append(str(r1.group('follow')))
            for i in range(len(followinglist)):
                if followinglist[i] == userprofile:
                    output = 'You are now viewing ' + userprofile + '\'s profile!'
                    usersfollowing = getUser(userprofile).from_user.get_query_set()
                    for userfollowing in usersfollowing:
                        output = output + '<br/>' + str(userfollowing)
                    break
    else:
        output = '<html> <form name="Input" action="http://localhost:8000/home/" method="post"> You must be logged-in to view profiles <br/> Username: <input type="text" name="user" /> <br/> Password: <input type="password" name="password" /> <br/> <input type="submit" value="Log In" /> </form> </body> </html>'
        
    return HttpResponse(output)

def logout(request):
    if 'username' in request.session:
        output = '<html> <form name="Input" action="http://localhost:8000/" method="post"> Username: <input type="text" name="user" /> <br/> Password: <input type="password" name="password" /> <br/> <input type="submit" value="Log In" /> </form> </body> </html>'
        del request.session['username']
    else:
        return login(request)
    return HttpResponse(output)
    
@csrf_exempt
def home(request):
    if 'username' in request.session:
        fromusers = getUser(request.session['username']).from_user.get_query_set()
        output = 'user ' + request.session['username'] + ' logged in <br/>'
        for user in fromusers:
            output = output + str(user) + '<br/>'
        output = '<html> <form name="Input" action="http://localhost:8000/logout/">'+ output + ' <br/> <input type="submit" value="Log Out" /> </form> </body> </html>'
    else:
        return login(request)
    return HttpResponse(output)

def ifUserexists(username):
    users = User.objects.get_query_set()
    exists = False
    for user in users:
        if str(user) == username:
            exists = True
            break
    return exists

def getUser(username):
    users = User.objects.get_query_set()
    for user in users:
        if str(user) == username:
            return user
    return None

@csrf_exempt
def login(request):
    output = '<html> <form name="Input" action="http://localhost:8000/home/" method="post"> FriendBook <br/> Username: <input type="text" name="user" /> <br/> Password: <input type="password" name="password" /> <br/> <input type="submit" value="Log In" /> </form> </body> </html>'
    if 'username' in request.session:
        return home(request)
    else:
        if request.POST != { }:
            username = request.POST['user']
            password = request.POST['password']
            if ifUserexists(username):
                user = getUser(username)
                if user.check_password(password):
                    request.session['username'] = str(user)
                    return login(request)
                else:
                    output = 'Error: wrong username/password'
            else:
                output = 'Error: wrong username/password'
                
    return HttpResponse(output)
    
def users(request):
    users = User.objects.get_query_set()
    output = ''
    for user in users:
        output = output + str(user) + '<br/>'
    return HttpResponse(output)
    
def followers(request):
    username = getUsername(request)
    if ifUserexists(username):
        tousers = getUser(username).to_user.get_query_set()
        output = ''
        for touser in tousers:
            output = output + str(touser) + '<br/>'
        return HttpResponse(output)
    else:
        return HttpResponse('Error: user ' + username + ' does not exist')
    
    return HttpResponse(str(request))
    
def getUsername(request):
    pattern = 'PATH_INFO\': u\'/(?P<username>\w+)/'
    if (None == re.search(pattern, str(request))):
        return ''
    r1 = re.search(pattern, str(request))
    return str(r1.group('username'))

def following(request):
    username = getUsername(request)
    if ifUserexists(username):
        fromusers = getUser(username).from_user.get_query_set()
        output = ''
        for fromuser in fromusers:
            output = output + str(fromuser) + '<br/>'
        return HttpResponse(output)
    else:
        return HttpResponse('Error: user ' + username + ' does not exist')
    
    return HttpResponse(str(request))