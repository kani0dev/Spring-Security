# The Spring Security

Spring Security is a framework that is part of the Spring Boot
ecosystem. It is one of the most popular and effective security
frameworks for Java applications.\
Spring Security is responsible for three major things: **authentication,
authorization, and basic security standards against common attacks**.

In simple terms, Spring Security **authenticates, authorizes, and
secures our applications**.\
Now the real question is: **HOW** does Spring Security do that? And here
is how.

------------------------------------------------------------------------

# Authentication

First things first, I have to make clear what authentication is and what
it isn't.

Authentication is the verification that an entity is present in our
application and then telling **WHAT** that entity (if it really exists)
**IS**.

In simpler human terms, authentication is proving that something exists
in our application and determining **what it actually is**.

------------------------------------------------------------------------

# Authorization

Authorization is the next step after authentication.

After we make sure that the entity **EXISTS** and we say **WHAT** this
entity **IS**, now we need to determine what it **CAN** do.

Example:

-   A user with the role `"user"` should not be able to perform
    operations on other users besides their own account.
-   Now an admin user... also **should not** be able to delete
    everything and have full control of the application, **BUT** since
    this is a simple guide, let's say that he can.

Spring Security is like the **bodyguard of a very messy club** full of
nested code and horrible practices that would probably make **Dennis
Ritchie** puke in his grave.\
And he is responsible for letting people in and out.

------------------------------------------------------------------------

# How Spring Security Does Both

Let's think about a real example.

Imagine that **Miss Dorothy** wants to log into our application to get
the recipe for a coconut cake.

In our application, Miss Dorothy will enter her username and password in
the login form, and the application will compare her credentials with
the data stored in our database.

If someone with those credentials exists, Miss Dorothy will be able to
make the sweet cake for her children --- **hooray!**

BUT if not... Miss Dorothy's soul will burn in hell for eternity and she
will never be able to read the coconut cake recipe.

Simple, right?

Now let's add a little more abstraction to this, since this is one of
the core concepts of **OOP**.

------------------------------------------------------------------------

# The Request Enters the Application

As we said before, Spring Security is the **bodyguard** of our
application.

The moment we add the dependency to our `pom.xml`, Spring Security
automatically blocks all endpoints, locking everyone out... including
ourselves.

That might seem wrong, but it's actually good.

It is a good practice to **block everything first**, and then explicitly
allow access where needed, instead of accidentally leaking information.

Let's assume a few things first:

Our database has a table called `users`, and the `User` entity has a
field named `role`.\
This field tells the user's role, either a default `"USER"` role or an
`"ADMIN"` role.

Assume this is true.

The important point here is that the **first thing a request reaches in
our application is Spring Security**.\
Not Spring Security itself directly, but a part of it known as the
**SecurityFilterChain**.

------------------------------------------------------------------------

# The SecurityFilterChain

The **SecurityFilterChain** is responsible for applying all Spring
Security features.

It is basically a **chain of filters** that process every request before
it reaches the application.

You can think of it as **a security checkpoint with multiple guards**.\
Each guard has a job, and the request must pass through all of them.

If something looks suspicious, the request is stopped right there and
sent back to the client.

------------------------------------------------------------------------

# Filters

A **filter** is a small processing step inside the SecurityFilterChain.

Each filter has a specific responsibility. For example:

-   checking authentication
-   validating credentials
-   verifying permissions
-   handling login
-   handling logout

Custom filters can also be added to the chain if the developer wants to
implement custom security behavior.

The important thing to understand is:

**every request passes through this chain before reaching the
controller.**

------------------------------------------------------------------------

# UsernamePasswordAuthenticationFilter

One of the most important filters is the
**UsernamePasswordAuthenticationFilter**.

This filter is responsible for handling **login requests that contain a
username and password**.

When a login request arrives, this filter extracts the credentials from
the request and passes them to the **AuthenticationManager**.

But this filter itself does not validate the credentials.\
Its job is simply to **collect the credentials and start the
authentication process**.

------------------------------------------------------------------------

# AuthenticationManager

The **AuthenticationManager** is responsible for coordinating the
authentication process.

Think of it as the **central authority that decides if the credentials
are valid or not**.

However, the AuthenticationManager does not directly check the database.

Instead, it delegates the job to another component.

------------------------------------------------------------------------

# UserDetailsService

The component responsible for retrieving user information is the
**UserDetailsService**.

Its job is simple:

Given a username, it loads the user data from the database.

This usually includes things like:

-   username
-   password
-   roles
-   permissions

Once the user data is loaded, Spring Security compares the provided
password with the stored password (usually using a password encoder).

If the credentials match, the user is considered **authenticated**.

------------------------------------------------------------------------

# The Security Context

After a user is successfully authenticated, Spring Security stores the
authentication information inside something called the
**SecurityContext**.

The SecurityContext is basically a place where Spring Security keeps
information about the **currently authenticated user**.

From this point forward, the application knows **who the user is** and
**what roles they have**.

------------------------------------------------------------------------

# Authorization Check

Now that the user is authenticated, the next step is **authorization**.

When the request tries to access a protected endpoint, Spring Security
checks if the user has the required role or permission.

For example:

-   `/users/me` → accessible to `"USER"` and `"ADMIN"`
-   `/admin` → accessible only to `"ADMIN"`

If the user does not have permission, Spring Security immediately stops
the request and returns an **HTTP 403 Forbidden** response.

------------------------------------------------------------------------

# Finally Reaching the Controller

If the request successfully passes through:

-   the **SecurityFilterChain**
-   the **authentication process**
-   the **authorization checks**

then --- and only then --- the request finally reaches the
**controller**.

At this point, the controller can safely assume that:

-   the user is authenticated
-   the user has permission to access that endpoint

The controller can now process the request normally and return a
response to the client.

------------------------------------------------------------------------

# When Something Goes Wrong

If something fails during this process, the request **never reaches the
controller**.

Examples:

**Invalid credentials**

→ Spring Security returns **401 Unauthorized**

**User does not have permission**

→ Spring Security returns **403 Forbidden**

In both cases, the request is stopped inside the SecurityFilterChain.

The bodyguard does his job and **does not let the wrong people enter the
club**.
