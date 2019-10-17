# MediaPlayer with Face Detection

In this project the mediaplayer plays the video chosen. The mediaplayer is written in JavaFX. 
The project also has implementation of face tracking using the haarcascade(registered to Intel usage prohibited for commercial purposes) using opencv. 
The player plays and pauses based on if anyone is looking towards the webcamera. The player has a problem of jaggedness as the player checks for face every second as both of them are not syncronized well. Maybe fixing that will be the next thing to do in this project.

# Screenshots

<img src="https://cloud.githubusercontent.com/assets/16362957/20455213/84560e7c-ae7c-11e6-8ede-6f5bd5a56427.JPG" height="300" width="400">
<img src="https://cloud.githubusercontent.com/assets/16362957/20455214/8520d4e0-ae7c-11e6-8de9-1d6310c34f71.JPG" height="300" width="400">

# My Original Contribution & Learnings

Contribution =>
Implemented a media player by taking reference from various online articles and educational video.
Added a capability to play and pause video based on the viewer's face direction (video will play is viewer is watching towards webcam else will pause) using opencv and haarcascade.

Major Learnings => 
Learnt how to use JavaFX to make media player.
Learnt how to use haarcascade and opencv to detect faces from webcam pictures.


