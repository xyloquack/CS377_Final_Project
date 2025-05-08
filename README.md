# Stock Photo Search Application
By Jackson Belzer and Ryan Wood

# Functionality
This app works by requesting images from an online image API called Unsplash. When typing in and sending a search query, that query is forwarded to the API in order to retrieve the related images. These images will then be displayed in a grid format, and each image can be clicked on to be enlarged for better viewing. When in this detailed view, the user can decide to save or unsave a previously saved image to be shown locally. This is where the button for saved images come into play on the main screen. Clicking this button will allow the user to scroll through their previously saved images.

# Running the App
Ensure that you have an Android device or emulator that is running with the AndroidSDK in version 24 or above, which corresponds to Android 7.0.
To successfully make API calls, you will need to populate your local.properties file with an API key by adding a line like this: "UNSPLASH_ACCESS_KEY=(your api key)"
