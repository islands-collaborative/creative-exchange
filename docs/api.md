# API documentation

Right now this is a rough outline of the planned routes for our application's API.

# Auth Routes

- `POST /users`: Creates a new user when they sign up. Redirects user to user account page `/users/`
- `GET /login`: Displays a login page
- `GET /signup`: Displays a signup page
- `POST /login`: (Implemented by Spring Security) Logs in a user and gives the user agent the authentication token

# Main routes

- `GET /`: Displays the homepage/splash page
- `GET /about`: Displays an about page that tells the visitor about the website and its creators.
- `GET /privacy`: Displays a page with the privacy policy.

# Users

- `GET /discover`: Serves a view of the creators that are showcasing their profiles.
- `GET /users/{userId}`: Serves the public view of a user's profile. This will include a short bio, profile picture, and
  a feed of their posts.
- `GET /profile`: Requires authentication. Provides a view that allows users to edit their own profile, edit and add
  their posts.
- `PUT /profile`: Requires authentication. Allows a user to edit the details of their own profile, including changing
  their bio, information, linked accounts. Redirects to `/profile`.

# Posts

- `POST /posts`: Requires authentication. Creates a new post. Redirects to the user's private-facing profile page
  at `/profile`. Alternative redirect: `/posts/{postId}`. Alternatively redirect to their public-facing profile page
  at `/users/{userId}`.
- `PUT /posts/{postId}`: Requires authentication. Edits a post. Same redirect as above.
- `DELETE /posts/{postId}`: Requires authentication. Marks a post for deletion. Same redirect as above.

# Messaging

- `GET /messages`: Requires authentication. "Inbox view". Displays a list of message threads that the currently logged
  in user is a part of. Sorts the threads by how recent a message was posted and whether there are unread messages.
- `GET /users/{userId}/messages`: Requires authentication. Displays a thread of the messages between the currently
  logged in user and the subject user indicated by the `{userId}` parameter. Also provides a form for the user to send a
  message in that thread.
- `POST /users/{userId}/messages`: Requires authentication. Sends a message from the currently logged in user to the
  subject user. Redirects to the message thread at `/users/{userId}/messages`.
- `PUT /users/{userId}/messages/{messageId}`: (Stretch goal). Requires authentication. Edits a message. Redirects to the
  message thread at `/users/{userId}/messages`.
- `DELETE /users/{userId}/messages/{messageId}`: (Stretch goal). Requires authentication. Deletes a message. Redirects
  to the message thread at `/users/{userId}/messages`.

# Follows

- `PUT /users/{userId}/followers`: Requires auth. "Follows" the user. Takes a `redirect` parameter that redirects the
  user to the same view that there were at when they clicked the follow button. This could be either the discover page
  or the user profile page.
- `DELETE /users/{postId}/followers`: Requires auth. "Unfollows" the user. Takes a `redirect` parameter that redirects
  the user to the same view that there were at when they clicked the follow button. This could be either the discover
  page or the user profile page.

# Likes (stretch goal)

- `PUT /posts/{postId}/likes`: Requires auth. "Likes" the post. Takes a `redirect` parameter that redirects the user to
  the same view that there were at when they clicked the like button.
- `DELETE /posts/{postId}/likes`: Requires auth. "Unlikes" the post. Takes a `redirect` parameter that redirects the
  user to the same view that there were at when they clicked the like button.

# Comments (stretch goal)

- `POST /posts/{postId}/comments`: Requires auth. Makes a comment on a website. Takes a `redirect` parameter that
  redirects the user to the same view that there were at when they posted the comment. By default redirects the user to
  the post detail page at `/posts/{postId}`.
- `PUT /posts/{postId}/comments/{commentId}`: Requires auth. Edits a comment on a website. Takes a `redirect` parameter
  that redirects the user to the same view that there were at when they posted the comment. By default redirects the
  user to the post detail page at `/posts/{postId}`.
- `DELETE /posts/{postId}/comments/{commentId}`: Requires auth. Deletes a comment on a website. Takes a `redirect`
  parameter that redirects the user to the same view that there were at when they posted the comment. By default
  redirects the user to the post detail page at `/posts/{postId}`.

# Jobs (stretch goal)

- `POST /jobs/`: Requires auth.
- `PUT /jobs/{jobId}`: Requires auth.
- `DELETE /jobs/{jobId}`: Requires auth.

# Offers (stretch goal)

- `POST /jobs/{jobId}/offers`: Requires auth.
- `PUT /jobs/{jobId}/offers/{offerId}`: Requires auth.
- `DELETE /jobs/{jobId}/offers/{offerId}`: Requires auth.
