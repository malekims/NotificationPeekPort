NotificationPeekPort
====================

Port Paranoid Android's Notification Peek feature to an apk and make it compatible with any Kit-Kat ROM. 

You can grab the lastest build apk in the **[releases tab](https://github.com/lzanita09/NotificationPeekPort/releases)**.

#### What it does
Notification Peek (Paranoid Android) is a nice feature for displaying notifications in your lock screen. It uses gyroscope and proximity sensors to detect the status of the device and display the notifications in a minimalistic UI in the right time.

#### What I did
* Removed several system APIs, replaced with public APIs.
* Attempted to make it work with only proximity sensor.
* UI tweaks, changed LinearLayout to GridLayout for displaying large numbers of unread notifications.
* Added notification peek timeout option.

#### How to uninstall
Because the Device Administrator disables apps that is activated from being uninstalled, you need to do the following if you want to uninstall the app:
* Go to your System Settings.
* Select **Security**.
* Select **Device administrators**.
* Uncheck NotificationPeek.
* Now you can uninstall the app.

#### What is missing
* Icon.
* Name.
* A lot of great features.

#### Known issue
* If the Peek view is shown, it won't update new incoming notifications. 

#### Credits
* **[Paranoid Android](http://paranoidandroid.co/)** and its open source **[AOSPA project](https://github.com/AOSPA)**.