Labs 11 and 12: Data Visualization and Application Hosting

📋 Overview

This section outlines the activities and accomplishments for Labs 11 and 12, focusing on visualizing our application's data using Python and deploying our backend service to Microsoft Azure for cloud hosting.

⚙️ Prerequisites

To run and test the components from these labs, ensure the following are installed and configured:

Programming Languages: Python 3.x, Java 17+
Frameworks & Libraries:
Python: pandas, matplotlib, mysql-connector-python
Java: Spring Boot, Maven
Database: MySQL Server (running and accessible)
Cloud Infrastructure: Microsoft Azure account (Azure Cloud Shell)
🔬 Lab 11: Data Visualization

Objective: Extract data from the application database and generate meaningful visual reports.

What we did:

Database Integration: Established a secure connection between a Python script (lab11_visualization.py) and our Spring Boot MySQL database.
Data Extraction: Executed SQL queries to fetch key business metrics, such as customer subscription status, address type distribution, and preferred languages.
Data Processing: Utilized the pandas library to clean and structure the tabular data for plotting.
Visualization: Leveraged matplotlib to generate dynamic visual representations of our data, outputting various charts:
Active vs. Expired Customers (Bar Chart)
Address Types Distribution (Pie Chart)
Preferred Languages (Bar Chart)
Reporting: Automatically saved the generated plots as PNG images to the repository for easy access and review.
☁️ Lab 12: Cloud Hosting

Objective: Deploy the Spring Boot backend application to the cloud to make it publicly accessible.

What we did:

Cloud Environment Setup: Configured the Azure Cloud Shell for our deployment environment.
Configuration Adjustments: Resolved database connection strings and environment variables to ensure the backend could communicate with the database while hosted on Azure.
Deployment Process: Packaged the Spring Boot application using Maven and deployed the .jar file to Azure App Service.
Troubleshooting: Overcame Azure regional policy restrictions and successfully spun up the instance.
Verification: Verified the live API endpoints, confirming that the cloud-hosted backend is successfully processing requests.
🔗 Application Link

Hosting URL: https://anmol-demo-final2026.azurewebsites.net/
<img width="2662" height="1618" alt="image" src="https://github.com/user-attachments/assets/55ec5acf-f44d-4c08-b89c-aea6f24af43a" />

