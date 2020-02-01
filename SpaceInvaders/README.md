# Section-12-Space Invaders-Again (Posted: 4/26/2019)

## Introduction

Today you will be using the same Space Invaders code
base from Section 9. You will be modifying the solution 
file to use lambdas instead of private classes. 

## Assignment

### Setup
Clone this repository, and add it to your local eclipse workspace.

You will be able to create a new eclipse project by cloning the repository
to a directory of your choosing, and then creating a new eclipse project 
for it. To do this, right click on your package explorer, and navigate through
``` new > Java Project```. From here, you should see a menu appear, where 
you will enter the name for your project. This can be anything you want. 
Additionally, before clicking ```Finish```, unclick the checkbox that 
says ```Use default lcoation``` next to it, and click the ```Browse``` 
button. This will allow you to select your git folder as a directory. 
Once this has been done, click the ```Finish``` button, and eclipse 
will make a Java project for you. 

Before starting, run the SpaceInvaders file as a Java Application to 
ensure that there are no errors, which will have the full functionality
as the previous section upon completion. 

### Editing the Code
For this section, you will need to modify the code, so that lambdas are 
used in place of mouse event handlers. Because the EventHandler classes
are primarily concerned with the handle method, you will need only 
pass an "event" parameter through your lambda, using the following style.
```canvas.setOnMouseMoved((event)->{...});```, where the internal of 
the curly braces is the code you would normally find in a handle method. 
Use this so that the two classes you wrote last section are no longer 
needed. 

Note that there is an additional privatized class, which will need to 
remain in order for the animation to work appropriately. The code which 
deals with the Timeline will need to remain the same, however it is appropriate
for this code to be moved. 
