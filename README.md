jflemax
=======

java ee validation, security and annotation managed request handling

How to use this library:

//The following illustrates, a generic, application wide request handling

@Named
@OnRequests(onRequest={
  @OnRequest(toPages="*")
})
@ApplicationScoped
public class MyCDIController{
  public void onRequest(){
    //Do something meaningful here.
  }
}

//add JFlemaxListener as a phase listener in your application. faces-config file

<lifecycle>
  <phase-listener>com.anosym.jflemax.validation.listener.JFlemaxListener</phase-listener>
</lifecycle>

And you will be rolling.

You want to make sure that the user is logged in?
By default, we redirect on failure.

for any redirect, we return a boolean or an integer @see OnRequest documentation

@Named
@OnRequests(onRequest={
  @OnRequest(toPages="*", excludePages={"/register", "/login", "/index"}, 
        onRequestMethod="isUserLoggedIn", loginStatus=LoginStatus.WHEN_LOGGED_OUT,
        redirect=true, redirectPage="/login")
})
@ApplicationScoped
public class MyCDIController{
  public boolean isUserLoggedIn(){
    //your own implementation. the jflemax only redirects to the login page if this is true.
    return isCurrentUserSessionValid(); 
  }
}

There are scenarios when you want to handle different logic at different phases of the jsf lifecycle, 
see the @OnRequest, jsfPhases property.
When to give priority to some handling first, see the priority property
Want to execute the request only once, check the execute priority
Want to execute on ajax, or full request, check the requestStatus property
Want to redirect to different pages, based on your result, see the redirectOnResult, redirectPages
