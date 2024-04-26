
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
![Screenshot](https://github.com/aranwarez/NTFTTH/assets/49429021/1c79384e-9d4e-4a9b-b647-fa8500b93a16)


### Complain Registration
![Screenshot](https://github.com/aranwarez/NTFTTH/assets/49429021/6f762fe3-c181-4af0-8989-973da9e376f1)

### FTTH Element Details
![Screenshot](https://github.com/aranwarez/NTFTTH/assets/49429021/1f353e4a-55c4-45ce-ba92-d0963cdad388)

### Trouble Ticket History
![Screenshot](https://github.com/aranwarez/NTFTTH/assets/49429021/fd6c2c57-757e-4924-9d5f-6efade2aad40)


