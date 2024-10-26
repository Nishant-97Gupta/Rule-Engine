# Rule Engine Application
This Rule Engine application allows users to evaluate rules and generate an Abstract Syntax Tree (AST) for visualization. The backend is built using Spring Boot and uses SQLite for data storage. The frontend is developed using React to display the results and visualize the generated AST.

# Table of Contents
Folder Structure,
Backend Overview,
Tech Stack,
Flow,
Database Configuration,
Database Schema,
Frontend Overview,
Tech Stack,
Folder Structure,
API Integration,
Tree Visualization,
Screenshots,
How to Run the Project



#  Folder Structure
# 1.1 Backend
    RuleEngine/
         1.1 src/
              1.1.1 main/
                  1.1.1.1 java/com/example/ruleengine
                        1.1.1.1.1 controller/
                           RuleController.java
                        1.1.1.1.2 model/
                            Rule.java
                            ASTNode.java
                        1.1.1.1.3 service/
                               RuleService.java
                              RuleEngineApplication.java
             1.1.2 resources/
                   application.properties
                      data.sql
                      schema.sql
                       database/mydatabase.db
                       
# 1.2 Frontend folder
  frontend/
      2.1 public/background_image.jpg
          index.html
     2.2 src/
        2.2.1 components/
                 RuleForm.js
        2.2.2 services/
                 ruleservices.js
      App.js
      index.js
      package.json
      README.md


# 2. Backend Overview
    2.1 Backend Tech Stack,
        Language: Java,
        Framework: Spring Boot,
        Database: SQLite,
         Build Tool: Maven,
         ORM: Hibernate

         
    2.2 Backend Flow
User Interaction: Users define rules and submit them through the frontend.
API Processing: The backend receives rules via the RuleController and processes them in the RuleService.
AST Generation: Rules are parsed into an Abstract Syntax Tree (AST) for logical evaluation.
Database Storage: Rules are stored in the SQLite database for future evaluation.
Evaluation: The backend evaluates rules and sends the result along with the AST back to the frontend for display.


# 2.3 Database Configuration
Database: SQLite
Connection: The SQLite database file is stored in the /database/mydatabase.db file.
JPA Configuration:
Hibernate is used for Object-Relational Mapping (ORM).
The application.properties file contains configuration to connect to SQLite:

properties:
spring.datasource.url=jdbc:sqlite:./database/mydatabase.db
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# 2.4 Database Schema
Table rules:
id (Primary Key): Auto-generated unique identifier.
rule_name: Name of the combined rule.
conditions: String representation of the conditions (e.g., "age > 18 AND income < 50000").
action: The action triggered after evaluation.


# 3. Frontend Overview
    3.1 Frontend Tech Stack
        Language: JavaScript
        Library: React
        HTTP Client: Fetch API for handling requests
        Visualization: react-d3-tree for tree visualization of the AST
   3.2 Frontend Folder Structure
        public/
        background_image.jpg: Background image for the app
        index.html: Entry HTML file for the React app
        
        src/
        components/RuleForm.js: Main component for the rule form and AST visualization
        services/ruleservices.js: Service for interacting with the backend APIs
         App.js: Main app component
        index.js: Entry point for the React app


# 3.3 API Integration
# 1. Evaluate Rules API:

     Endpoint: /api/rules/evaluate
         * Method: POST
         * Request Body:
         * json
        {
             "rules": ["age > 18", "income < 50000"],
            "operator": "AND",
              "userData": { "age": 20, "income": 45000 }
        }
       Response: Boolean indicating whether the conditions are satisfied.


# Generate AST API:
   Endpoint: /api/rules/generate-ast
   Method: GET
   Query Parameters: rules, operator, userData (as JSON)
   Response: AST structure in JSON format.

   
# 3.4 Tree Visualization
       Library Used: react-d3-tree
       
      Visualization Process:
           * The backend generates an AST and sends it to the frontend.
           * The AST is visualized in a tree structure using react-d3-tree.
           * The tree dynamically updates as new rules are evaluated.


# Screenshots

# 1. Rule Submission Form
    ![Rule Form](public/Screenshots/rule_form_screenshot.jpg)

# 2. AST Visualization
    ![AST Visual](public/Screenshots/ast_visualization_screenshot.jpg)



# How to Run the Project
 #  5.1 Backend
   Clone the repository:
          git clone https://github.com/yourusername/rule-engine.git

   Navigate to the backend directory:
         cd RuleEngine

   Build the project using Maven:
       mvn clean install

   Run the backend:
       mvn spring-boot:run

# 5.2 Frontend
Navigate to the frontend directory:
    cd frontend

Install dependencies:
   npm install

Start the frontend:
     npm start


# 5.3 Accessing the Application
Backend: Runs on http://localhost:8080
Frontend: Runs on http://localhost:3000


