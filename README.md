# Events Management (Social) API 


## Overview

* This is complete API backend that I wrote some time back as a freelancer for a client who wanted API driven event management application.  
* All the APIs are working as of Sep 2019 when I ported the code to latest the Spring Boot.
* This is a Spring Boot based REST API backend.
* Feel free to use in any manner.

## Dev Set up
* Last tested on Java verion: 12.0.2 on Ubuntu 18.04 LTS
* Have mongodb running in local
* Check out the code, import in eclipse as maven project and you should be all set.
* Start the application by launching main() at EventsapiservApplication.java
* Use the postman collection "eventsapiserv_postman.postman_collection.json" to test the APIs

## Indexes needed
* db.createCollection("appLog", {"capped":true, "max":5000, "size":300000})
* db.yumuUser.createIndex({"socialInfo.name":"text", "socialInfo.email":"text"})
* db.activity.createIndex({name:"text", description: "text", hashtags: "text"})
* db.post.createIndex({content:"text", hashtags:"text"})
* db.yumuUser.createIndex({"socialInfo.userId":1}, {unique:true})

## Use cases

User

* users login with facebook/google+
* users can create an activity
* users can join or follow activities
* owner user can disable an activity
* owner user can disable an offensive post
* users can upvote/downvote/pin/report and activity or post. User clout (Ranking) grows due to these actions. Clout depends on votes, pins, memberships, connections etc.
* member users can create a post inside an acitivity
* Users can decide what to share with others

Activities/Posts/Activity Stream 

* Activity Stream defaults to location based
* Activity Stream is made up of stream of elements. Elements can be of type activity, post, user actions, and, anything else. For MVP Activity Stream is made up of only activities.
* Guests see only public elements
* Activity Stream can be of multiple kind: My activities, group acitvities, this weekend, and discussions
* Pinned elements will show up in My activities
* Logged in user can create an activity
* Activity can be public or private (TBD)
* Template based activity creation. MVP will have few templates in the form of metadata
* Users with higher clout can create templates (not for MVP)
* Activities have properties like: name, date, location, privacy, pictures, hashtag, address, # of members, and repetition.
* Element can be shared on Facebook/Google+
* Element CRUD operations should store location
* Activity owner can invite other users
* Activity can have more than one owner. How?
* Terms and conditions should be shown during activity creation
* Activities will have conversations (made up of posts)
* Activity will have a rating score based on an algorithm
* Member users can invite other friends for an activity
* Owner users can approve membership requests
* Owner users can make an activity public/private


Search

* list activities by location
* list activities created by someone
* list activities for which someone is a member
* list activities for which someone is a follower
* list users

Notifications

* Friend request from users
* Membership request to users
* Membership request from users

Profile

* My profile will have privacy settings
* My profile will show notifications where users can act on it
* Other profile will show only public elements of the other user
* Other profile will show list of events where user is a member
* Other profile will show list of events where user is a follower
