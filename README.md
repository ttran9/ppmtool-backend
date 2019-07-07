# Overview

- This is section 8 of [this course](https://www.udemy.com/full-stack-project-spring-boot-20-react-redux/)
    - I am making this repository as a back-up to this repository [here](https://github.com/ttran9/ExamplePPMTool).

- Since this is just for my own learning purposes I have decided to read the secret key (SECRET) in the SecurityConstants
file from an environment variable instead of just having it as a hard-coded public string.
    - I am still trying to figure out the most secure way to keep that private key string from being exposed to anyone
    else from the outside world and as I come across better solutions I will update the solution.

- Bug(s):
    1. I was testing this app I notice there is a weird bug where if the user types into the URL and should be redirected
    that the react-router being used is not working properly. Some examples below.
        - When the user is not logged in they will not be able to see the dashboard page but when they attempt to do so
        they are expected to be redirected to the login page but instead it appears that the Login Component is not rendering
        so instead the user is just returned JSON content (the user name and password are invalid) which is a custom way
        of just telling the user they need to be logged in but this should be displayed on the login form with bootstrap
        styling.
        - There is an issue of the user being logged in and typing into the URL to go to either the login and register
        pages where the expected behavior is that the user gets redirected to the dashboard page but instead
        there is the issue of the JSON content being returned indicating the user has entered invalid credentials but
        this should not be the case because the user is already logged in and this can be seen where the user has
        a valid jwt emailVerificationToken inside of their localStorage.
        - I will attempt to investigate the above where it appears I am not utilizing react-router properly and there are
    some undesirable side effects.
    2. **Solution (to i.):** 
        - As of 6/28/2019, I have uploaded a solution.
            - However, I need to continue to test this further to ensure that the regexes I have in place are working.
            - Earlier, I did not allow the users to type lower case letters although this would be acceptable so I have slightly
            modified the regex to allow for this.
        - I found [this](https://stackoverflow.com/questions/47689971/how-to-work-with-react-routers-and-spring-boot-controller)
        to be helpful as I decided to implement this but to also use regular expressions to match what the user was typing in
        and redirect to the index.html single page application and then let BrowserRouter take over.
            - I also had to modify the react app to redirect to the home page if the route cannot be found. I did this
            by simply moving the switch statement to wrap around all the routes and also adding a Redirect component
            at the very end (before the closing Switch statement tag).
        - I had another solution which wasn't very viable because I created a Controller to handle incoming requests 
        and to map those requests to the index page because if I had more routes to accept then I would've had to hard-code
        more routes into the controller and also the SecurityConfig class. So the above solution appears to be more flexible
    3. When attempting to login I noticed that we were throwing a 405 error and more specifically on the back-end we are using
    spring security's authentication method which throws a BadCredentials exception so this had to be properly handled
    by our CustomResponseEntityExceptionHandler so that the error message indicating that the user has entered an invalid
    username and password.
        - When debugging this I first looked at the console and noticed that when we were submitting we were hitting the
        /api/login with a POST request and shortly I realized that CustomResponseEntityExceptionHandler did not handle
        the BadCredentials exception and that is why I added in an extra method with the @ExceptionHandler annotation.

- New Features:
    1. Refer to bug #1 in the above section ("Bugs").
    2. I have added a feature where after the user is logged in then the user must first activate their account.
        - This is done by the server emailing the user with a provided emailVerificationToken that must be used in order to activate the user account.
            - Upon successful activation the emailVerificationToken is removed from the server.
            - I found [this](https://medium.com/@apdharshi/sending-email-confirmation-for-account-activation-with-spring-java-cc3f5bb1398e) 
            to be helpful when implementing spring's mailing service.
            - I also decided to add in a new custom exception which could be used by the server to return an error if there
            was an issue with the verification emailVerificationToken such as using a emailVerificationToken that no longer exists or has expired.
        - I also had to add a few new components on the front end in order to display web pages that the user must go through
        in order to activate his/her own account.
            - Also in the front end I had to add a new action in order to call the back-end api in order to process the
            account activation using a passed in emailVerificationToken from the URL as a parameter.
            - One thing to note is that upon successful activation I decided not to dispatch an event as I felt this was
            not necessary and simply moving the user to a successful registration page was sufficient. If it was needed
            to display a message from the server then I would display a new action type and handle this in the securityReducer
            but I did not find this necessary for the time being.  
        - When uploading this to Heroku I had a slight issue when attempting to register a new user.
            - After debugging for a little bit (analyzing the error logs) there was a really descriptive error where I
            had to follow a link in order to verify that it was really me logging in. Basically, since the Heroku app 
            is not local to my machine google was blocking the application from connecting via SMTP to send the verification
            email and once I followed this link and I tried to re-register the user I was able to create a new "enabled"
            or activated user.
    3. I have added in a feature where a user must be logged in to request for a password change.
        - I had to refactor quite a bit of code because I wanted to be able to use the same base domain object that was
        generating the tokens for the email verification (user account activation) and the password change. Both of these
        are based off of using a token that points to a user and has an expiration date of a day.
            - I will refactor my initial implementation to require that the password token change be expired within 15
            minutes of request.
        - While implementing this feature I also had to create a utility class inside the event package as I was
        going to re-use (violate DRY) when sending the emails for either the password change or the account activation.
        - In regards to implementing the front-end portion I found that I also had to refactor a few components because
        I was using some of the same "screens" when the user successfully changed their password or activated their account.
        I also decided that I should re-use the same "view/screen" for when the user initially requests to change their password
        that the user should be redirected to a page that tells him/her to check their email account for a link (with 
        a generated token) on how to change their password or activate their account.
            - When implementing the change password functionality I followed much of the same logic as I used for
            activating the user account and sending out the e-mail so this was a big factor as to why I had to do some
            refactoring of components so I could re-use them.
