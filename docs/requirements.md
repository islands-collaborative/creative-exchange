# Software Requirements

## Vision

Our vision is to create a platform for creators to display their works, share ideas and find commissions while giving consumers a streamlined experience to view and contact creators. This allows creators to actively search for commissions that meet their skillset and requirements. Consumers can discover by content creators have already produced or directly outline what they're looking for and letting creators contact them. This app is a dynamic platform that lets both creators and consumers manage their relationships the way they want to.

## Scope (In/Out)

### In

- This app will allow users to create secure accounts and sign in.

- This app will allow users to view an About Us page that tells them about the creators.

- This app will allow users to communicate with eachother

- This app will allow creators to display their works on their profile and through posts

- This app will allow users and create to follow creators and view their posts in a feed.

### Out

- This app will not manage transactions between creator and consumer.

- This app will not become an IOS or Android app.

- This app won't include admin roles

## MVP

- Users create and manage accounts as Creator or Consumer

- Creators display works on viewable profile and posts

- Users can follow/unfollow creators and see their posts in feeds

- About Us pages allows users to learn more about the creators

- Message communication

- Discover displays creators, profile picture, blurb and link to profile page

## Stretch

- Comments and Likes on posts

- Commission requests show up in creator feeds

- Discover by tag, username etc

- Websocket IM communication

- Multiple images on posts

- Users can hide posts

## Functional Requirements

- Visitor can visit home, signup, login, discover, about us, and creator profile pages

- Users can create profile, login, manage profile, view and follow other users, message other users, and view and delete posts

- Creators can make posts and pin works to their profile,

### Data Flow

A visitor navigates to the site. They are given the option to sign in, sign up or explore the site without signing in. They visit the sign up page and create a profile. Their information is stored the user database with the password encoded. They're redirected to their profile page. They navigate to the Discover page to find other users. The follow a creator whose content interests them which joins them as a follower and they're added to the creator's follow list in the respective many to many join table. They navigate to the feed page and the data base pulls all posts from creators they follow and displays them. The user clicks on the author of a post that interests them to message them. They type out a message and hit send. The message is stored in a joined many to many database. When they receive a message back they navigate to their inbox and the database returns all messages connected to those users and displays them by time stamp with the option to send a new message. Amazed at how great this webapp is the user clicks the about us page in the footer and learns more about the amazing individuals who made it.

## Non-Function Requirements

### Usability

We want our webapp to have a self-explanatory user-friendly experience. We should be able to send the link to our home page to an uninformed user and without any guidance have them:

- Know what the site is about just by viewing home page

- Navigate to the sign-up page and create an account

- Discover and follow a Creator

- Create a post

- View their feed

- Visit the About Us page

- Send and receive a message with another user

This will confirm that our site is fully functional and self-explanatory.

### Security

We want to make sure that our site is secure as possible to protect both our users and site integrity. We will ensure site integrity by running code injection tests in various fields to confirm our site can't be altered by outside sources. We will use Springs security dependency to encode hashed and salted passwords.