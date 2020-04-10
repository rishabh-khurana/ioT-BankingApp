# BankAssist

## Objective

Our ultimate goal is to allow customers to save time in the branch as customers want things to be
done quickly and efficiently. In turn, this frees up time for bank employees to focus their time on
human centric aspects of the branch, such as tasks that require creative thinking and innovation.
Furthermore, employees can also focus their time on providing a more fulfilling experience for
customers

## Background

IoT is an emerging technology in this day and age. Data can be retrieved from sensors, which in turn
can be used to produce useful insights for companies. By 2020, there will be over 26 billion
connected devices. Most banks use apps to create a more convenient for their customers and we are
looking to add it on by creating an app which will make queueing easier, making it a more pleasant
experience and hopefully getting branch tasks done quicker.

## Who is it for?

Developers - People who love to develop apps in order to satisfy the customer’s needs.  

Customers - People who love to book things via an app on the mobile

Banks - Will help their queueing system for their branches and trying to find ways to improve the customer experience

## Assumptions/hardware used

I. Only an Android App will be developed for this project. No iOS App hence only Android
phones are used.

II. [Bluecats](https://www.bluecats.com/) bluetooth proximity [beacons](http://wwwpress.bluecats.com/buy-beacons/) will be used to detect customers and eventually
collect data.

III. Developed in Kotlin, Javascript and NodeJS. MySQL will be used for databases along with Python for
analytics and machine learning.

IV. Bluecats web app for monitoring and if maintenance is required.


## Application Walkthrough

### Step 1 - Customer Logs In to the Application 
<img src="images/Screenshot_1.png" width="250" height="420">

### Step 2 - Customer Selects the Service

Once the user selects the service and click on the button below, the app prompts the user to switch on the bluetooth and
starts watching for a beacon in close range. Once a beacon is detected the application sends a get request to the ticketing API and waits for a response. If a response is received , the application opens the next view to display the Kiosk and ticket details.

<img src="images/Screenshot_2.png" width="250" height="420">

### Step 3 - Customer Receives the ticket Number
<img src="images/Screenshot_3.png" width="250" height="420">

### Step 4 - Customer gives Feedback
<img src="images/Screenshot_4.png" width="250" height="420">
