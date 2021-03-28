# HandyHandFront -- Usage

## Setup

### API Location

After installing your app, you can setup the address on which you should listen to the API.
By default, this will be setup to http://localhost:8080.  
Otherwise you should modify the 'api.address' file and enter your custom address.  
If this file doesn't exist, it will be automatically created on your first run.

## Usage

When using our app you should make a difference between two type of usage:
- Registered
- Unregistered

You can use your local script when unregistered, but you need to be logged to access your online script or other devs scripts.  

## Display

### Home Page

The home page display you the current view of the leapmotion camera or a default picture if no LeapMotion is reachable.  
You also have a switch indicating you if your LeapMotion is detected or not.

### TopBar

On the top bar, you can see different buttons, The left one opens up the side menu.  
The right button allow you to register or login with an existing account.  
If you're already logged in, the disconnect and EnvSync button appear.  
The env sync button allow you to send online your local script mapped with a gesture and fetch your online script linked with a gesture too.  

### Side Menu

From the side menu you can access all the other pages known as:
  - Home
  - My Scripts
  - Scripts
  - My Gestures
  - Gestures
  - Executables

### My Scripts

> You do not have to be logged in

Allow you to interact with your local scripts.
Configure them and listen to them on your LeapMotion.
Add new Script or remove some.
Listen to a script by switching it on

### Scripts

> You have to be logged in

You can here interact with online scripts
You can listen to them if you link them with a gesture.
You can also remove them.
Listen to a script by switching it on

### My Gestures

> You do not have to be logged in

Register new gesture to link them with scripts in the future
They are managed in a local context

### Gestures

> You have to be logged in

Manage gestures sent online

### Executables

> You do not have to be logged in

Link the meme-type to a particular executable on your machine to allow script execution





