
# Nepal Telecom : FTTH Complaint Management System

## Introduction
Welcome to the FTTH Complaint Management System! This web application is designed to streamline the process of managing complaints related to Fiber To The Home (FTTH) internet service provided by Nepal Telecom. It offers a comprehensive set of features to efficiently handle customer complaints and ensure timely resolution.

## Features
- **Trouble Ticket Import**: Import trouble tickets from NT mobile APP, IVR, and customer service.
- **Dashboard**: Monitor complaints based on types, count, assigned group, etc.
- **Trouble Ticket History**: Manage previous complaints to handle recurring issues.
- **Work Order Management**: Handle mass network failures with email and SMS alerts.
- **Customization**: Custom departments, regions, support centers, and mapping for trouble tickets.
- **Network Management**: Check balance, FUP limit, volume download/upload, and manage Wi-Fi settings.
- **Network Mapping**: Detailed information about the optical fiber network.
- **Team Management**: Assign and synchronize workload, manage priority queues.
- **Employee and Role Management**: Setup and assign roles within the system.
- **Custom Reports**: Generate various reports related to complaints, clearance, staff dispatch, etc.
- **API Integration**: NTTV API integration for additional functionalities.

## Database
The database schema is proprietary and under NDA with Nepal Telecom. While a schema disclosure has been requested, it hasn't been approved. Users may need to reverse engineer or design their own database schema.

## Getting Started
Follow these steps to set up and run the application:
### Prerequisites
- Java Development Kit (JDK) 8 or higher installed on your system
- Apache Maven installed on your system
- Access to Nepal Telecom's internal network or deployment environment
- Database access credentials (if required)

### Installation and Setup
1. Clone this repository to your local machine:
   ```sh
   git clone https://github.com/your/repository.git
   ```

2. Navigate to the project directory:
   ```sh
   cd ftth-complaint-management-system
   ```

3. Build the project using Maven:
   ```sh
   mvn clean install
   ```

### Configuration
1. Update the database configuration in `src/main/resources/application.properties` file with your database credentials and connection details.
2. Configure any other application properties or settings as needed in the same file.

### Deployment
1. Deploy the built WAR file to your Servlet container (e.g., Apache Tomcat):
   - Copy the generated WAR file from the `target` directory to the `webapps` directory of your Servlet container.
   - Start or restart the Servlet container.
2. Access the application using the provided URL and port number. Make sure you are connected to Nepal Telecom's internal network or deployment environment.

## Usage
1. Open your web browser and navigate to the URL where the application is deployed.
2. Log in with the provided credentials. Depending on your role, you will have access to different features and functionalities.

## Contributing
Contributions, suggestions, bug reports, and feature requests are welcome! Please follow the contribution guidelines outlined in CONTRIBUTING.md.

## License
This application is licensed under Nepal Telecom's terms and conditions. Refer to NT's licensing agreements for more information.

## Support
For further assistance or support, please contact [info@koheen.com].

## Disclaimer
This application is provided as-is, without any warranties. Use at your own risk. The developers and contributors are not liable for any damages or issues arising from the use of this application.

## Acknowledgments
We would like to thank all contributors, collaborators, and third-party tools/libraries that have contributed to the development of this application.

## Contact
For inquiries or further information, please contact [aran@koheen.com].

## Screenshot
Here are some screen of Web Application in Work
### Dashboard
![Screenshot](https://private-user-images.githubusercontent.com/49429021/324235239-1c79384e-9d4e-4a9b-b647-fa8500b93a16.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTM2OTAxMTUsIm5iZiI6MTcxMzY4OTgxNSwicGF0aCI6Ii80OTQyOTAyMS8zMjQyMzUyMzktMWM3OTM4NGUtOWQ0ZS00YTliLWI2NDctZmE4NTAwYjkzYTE2LnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA0MjElMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwNDIxVDA4NTY1NVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTRmYTU0YjVkYWZjZTY3ZWVhMWU3NmU2YTY5NmIxZjgyMDVjMTE0YjQ3OGNjMGI3YTlkNTJlODM3YWEyODMwZjAmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.31gFzRAO-Apgb39MYQdvCAT02veZFlUfc_lab7NbdxM)


### Complain Registration
![Screenshot](https://private-user-images.githubusercontent.com/49429021/324235238-6f762fe3-c181-4af0-8989-973da9e376f1.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTM2OTA0MjYsIm5iZiI6MTcxMzY5MDEyNiwicGF0aCI6Ii80OTQyOTAyMS8zMjQyMzUyMzgtNmY3NjJmZTMtYzE4MS00YWYwLTg5ODktOTczZGE5ZTM3NmYxLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA0MjElMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwNDIxVDA5MDIwNlomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTQ0YTNjNzRkNzQzMDRlYzlkOWY3NGY5ODc5ZTVkOTkyYTI2OWE0MGU3NTI5NjYwZGY3YTY5YzFmY2I3MGUyZmMmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.EPmdmapTQy6bFfKHRJk6AZ9qtinznJmJyLYBWiBd0JU)

### FTTH Element Details
![Screenshot](https://private-user-images.githubusercontent.com/49429021/324235240-1f353e4a-55c4-45ce-ba92-d0963cdad388.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTM2OTA0MjYsIm5iZiI6MTcxMzY5MDEyNiwicGF0aCI6Ii80OTQyOTAyMS8zMjQyMzUyNDAtMWYzNTNlNGEtNTVjNC00NWNlLWJhOTItZDA5NjNjZGFkMzg4LnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA0MjElMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwNDIxVDA5MDIwNlomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTdkYjY3ZDVjN2E5OGE0OWUzN2JkYTA3MzE1NjEyOWJmNWQwM2ZkZmY1MGI3ZTVlMjViNzk1YzYxZDRhNDcwOWImWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.3IWwdvrwEDPUEwdRY58yic0CZG9hyr6nW7Y6e9GOrzU)

### Trouble Ticket History
![Screenshot](https://private-user-images.githubusercontent.com/49429021/324235241-fd6c2c57-757e-4924-9d5f-6efade2aad40.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTM2OTA0MjYsIm5iZiI6MTcxMzY5MDEyNiwicGF0aCI6Ii80OTQyOTAyMS8zMjQyMzUyNDEtZmQ2YzJjNTctNzU3ZS00OTI0LTlkNWYtNmVmYWRlMmFhZDQwLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA0MjElMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwNDIxVDA5MDIwNlomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWVjZGMzNjkzMjE2MzZlNzBlYWUxODc0Yzc5ZmIyZDdmY2NhMzg0ZjYxOTNiYTE4ODA1NzFmZWRiYjQ4YTJkMTEmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.HYg9bqWXlc2ZdgcoxlgSqzOm3XWP6jG6C6HMDcJ4YSs)


