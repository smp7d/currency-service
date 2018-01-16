## What is this?
This is a Java/Springboot web service app which allows callers to retrieve exchange rates for currencies

## How to run?
This is a Maven app. Either build an uber jar or (more simply) import into an IDE of your choice and run the type CurrencyServiceApplication

## What functionality does this provide?
Run it and check out the docs at http://localhost:8080/swagger-ui.html

## Got any examples?
Sure, try:

http://localhost:8080/rates?base=USD

http://localhost:8080/rates?base=NOK&target=USD

http://localhost:8080/rates?base=USD&timestamp=2016-05-01T12:00:00Z

http://localhost:8080/rates?base=NOK&timestamp=2016-05-01T12:00:00Z&target=USD

Or play around with the documentation above to find out more.
Playing with the timestamp when it converts to different days in CET (the base for the data) is...um...lots of fun

## Is it robust?
Negative. This could use a lot of polishing. The API can easily be broken by bad input and there was little care given to sane error handling, though the package this is built on should provide some reasonable defaults.

## Where would I go from here?
Here are my thoughts: 
https://docs.google.com/document/d/17r7Ccuoy7zO9gTBXBbNjqNclGzH8hr2Wh0P7lHNHRYk/edit?usp=sharing


