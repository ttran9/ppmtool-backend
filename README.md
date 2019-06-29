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
        a valid jwt token inside of their localStorage.
        - I will attempt to investigate the above where it appears I am not utilizing react-router properly and there are
    some undesirable side effects.
    2. **Solution (to i.):** 
        - I found [this](https://stackoverflow.com/questions/47689971/how-to-work-with-react-routers-and-spring-boot-controller)
        to be helpful as I decided to implement this but to also use regular expressions to match what the user was typing in
        and redirect to the index.html single page application and then let BrowserRouter take over.
            - I also had to modify the react app to redirect to the home page if the route cannot be found. I did this
            by simply moving the switch statement to wrap around all the routes and also adding a Redirect component
            at the very end (before the closing Switch statement tag).
        - I had another solution which wasn't very viable because I created a Controller to handle incoming requests 
        and to map those requests to the index page because if I had more routes to accept then I would've had to hard-code
        more routes into the controller and also the SecurityConfig class. So the above solution appears to be more flexible
