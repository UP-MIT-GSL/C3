## About Check-UP ##

**Check-UP: Course Curriculum Checklist** is an Android application made for and by students of the [University of the Philippines Diliman](http://upd.edu.ph/). The application requires the user's registration year and course. It then uses the corresponding curriculum to track the subjects that a student has already taken and has yet to take. It will also display the student's academic standing and give suggestions on which subjects to take for the next semester. Statistics such as General Weighted Average (GWA), units left, years left, P.E.'s left, and more will help the student succeed in school.

Find out more information on our [Wiki](https://github.com/AITI-Philippines/C3/wiki). Thanks for checking out our project.


## Contributing to Check-UP ##

### Prerequisites ###

1. Set-up your git installation. See the tutorial on https://help.github.com/articles/set-up-git

    _Make sure to configure your **username** and **email**_

2. Generate an SSH key for your current machine. See the tutorial on https://help.github.com/articles/generating-ssh-keys

    _Make sure you add your **SSH key** to your GitHub Account Settings_

    
### Cloning ###

1. In the terminal, change to your desired parent directory for the project directory

    `cd <directory>`

2.  Clone the project

    `git clone git@github.com:AITI-Philippines/C3.git`


### Committing Newly Created Files ###

1. Change to the project directory

    `cd C3`

2. Add files to git's list of tracked files

    1. Add a new, _untracked_, file to git's list of tracked files
        
        `git add <file>`

    2. Add _multiple_ untracked files to git's listo of tracked files, by added the entire directory contents
        
        `git add .`
        
        _Hint: `.` indicates the current directory_

3. Commit your changes to the history of your repository, and include a useful message

    `git commit -m "<message>"`

4. Update the remote repository in GitHub

    `git push`


### Committing Modified Files ###

1. Commit add your updates to the history of your repository, and include a useful message

    `git commit -a -m "<message>"`

    _Hint: The `-a` flag tells you to automatically add all modified already-tracked files_

2. Update the remote repository in GitHub

    `git push`


## Running the Android Application

1. Install the Android SDK and the Eclipse Plugin. See the tutorial at http://developer.android.com/sdk/installing/index.html

2. In Eclipse, import the Check-UP Android project

    `File > Import > Android>Existing Android Code Into Workspace`

    In the following screen, specify the Android application Root Directory
    
    `<path>\C3\Check-UP`

    Select All projects, and continue

3. Run the application on a real device or an emulator. See the tutorial at http://developer.android.com/training/basics/firstapp/running-app.html


## Developers

* Bisais, Apryl Rose (apryl.rose04@gmail.com)
* Javier, Jewel Lex (lex.javier@gmail.com)
* Razon, Justine Arnon (arnon.razon@live.com)
* Santi, Nathan Lemuel (nahtan_itnas@yahoo.com)