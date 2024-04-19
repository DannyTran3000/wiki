# Wiki Portal

## Introduction

Welcome to WikiPortal, a web application that provides a user-friendly interface for browsing and searching through a curated collection of knowledge articles from various domains. WikiPortal aims to streamline the access to valuable information and facilitate learning and research.

## Key Features

- Search Functionality: Users can search for articles by keywords, titles, or categories to quickly find relevant information.
- Article Categories: Articles are organized into categories for easy navigation and exploration.
- User Accounts: Registered users can save favorite articles, contribute new content, and interact with the community.
- Admin Dashboard: Administrators have access to an admin dashboard for managing articles, users, and site settings.
- Responsive Design: The application is designed to be responsive and accessible on different devices, including desktops, tablets, and mobile phones.

## Project Structure

```
wiki/
├── src/
│   └── main/
│       ├── java/                       (Java source code)
│       |   └── com/
│       |       └── wiki/
│       |           ├── auth/
│       |           ├── config/
│       |           ├── controllers/
│       |           ├── helpers/
│       |           ├── interfaces/
│       |           └── models/
│       └── webapp/                     (Web application resources)
│           ├── public/
│           └── WEB-INF/
│               ├── views/              (JSP views)
│               └── web.xml             (Web deployment descriptor)
├── target/                             (Generated output, compiled classes)
├── pom.xml                             (Maven project configuration)
└── README.md                           (Project documentation and instructions)
```

## Getting Started

To run the WikiPortal application locally, follow these steps:

1. Clone the repository:

   - `git clone git@github.com:DannyTran3000/wiki.git`

2. Build the project using Maven:

   - `mvn clean package`

3. Deploy the WAR file to a servlet container (e.g., Tomcat):

   - Copy the generated WAR file from the `target/` directory to the webapps directory of your servlet container.

4. Start the servlet container and access the application:

   - Open a web browser and navigate to the URL where your servlet container is running (e.g., http://localhost:8080/wikiportal).

5. Explore the application features, search for articles, create a user account, and enjoy learning from the curated knowledge base!

For more detailed instructions and configuration options, refer to the project's README.md file and documentation.
