# Mobile Software Design Assignment
# Demo Video
https://youtu.be/KVSKzYmN9vg

# FindIt - App Description
This is a college lost and found app for students of that college. Students who lost or found an item can post it on the app for all other students to see. You can contact whoever made a post by their phone number or the email (using student number). To manage the app's database, there's an admin who can delete and update items. The admin can login using their username and password to access the CRUD operations.

# Classes
Additional features are in **bold**.

**SplashScreen:**
- First activity when app is ran
- For visual aesthetics

**MainActivity:**
- Loads after the splash screen
- **You can swipe or click on the tabs to get to the lost or found fragment**
- Users can see lost and found items on each tab
- When an item is clicked on, the ItemClicked activity begins

**LostFragment + FoundFragment:**
- Contains a list of lost items and found items

**ItemClicked:**
- Details of the item are displayed
- These details are taken from the getters in the item object
- **You can share the details to another app with the share button at the bottom**
- User can also go back to the last activity using the exit button on the top right.

**PostActivity:**
- Allows the user to create a post
- **Users can choose to use their current location for the "last seen location" field in the form when they're making a post**
- There's radio buttons to choose if the item being posted is lost or found.
- There's input validation
- **When a user clicks the post button, it also displays the time and date of when the post was created. You can see it in the listview.**

**AdminActivity:**
- Admin login for to access the CRUD operations
- There's input validation

**AdminEditActivity:**
- Where the admin can see the items
- When an item is clicked on, the AdmindEditItemClick activity starts
- **User can refresh the list by swiping up**

**AdmindEditItemClick:**
- This is where the admin can update and delete items
- These details are taken from the getters in the item object
- There's input validation

**SectionsPagerAdapter:**
- Used for switching from fragment to fragment

**Item:**
- Class for making Item objects

**ItemAdapter:**
- Adapter used to add the objects into the custom listview (row.xml)

**DBHelper:**
- Creates the SQLite database and tables

**DBManager:**
- Has methods to do CRUD operations on the the items table
- Has methods to query the admins table

# Possible Other Features
If I had more time, I would have liked the user to be able to take pictures of the item in the app with the phone camera and post it. The image encoded to base64 and stored in the SQLite database as Blob.

I also would have made it so the user can store the phone number into their contacts by just clicking on the phone number in the ItemClicked activity.