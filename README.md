# ArtTherapy
SANTA CLARA UNIVERSITYCOEN 268 Mobile Application Development Assignment (ANDROID). 
A custom view that allows users to draw arbitrary pixels.
When the user shakes the phone:The drawing is  reset.
An eraser plays sound. 
The sound is played in a different thread from the UI thread,using IntentService.
When the phone is unlocked, a broadcast receiver is  triggered. The broadcast receiver a notification, asking the user to make a drawing. If the user taps the notification, it launch's the Draw Activity.
