# Parser Service

## Overview

**Parser Service** is a microservice designed to extract text from resumes in PDF format. It uses a text extractor to
read the content of the PDF and then sends the extracted text to an NLP service for processing and structuring relevant
data such as name, work experience, education, skills, and more.

## Technologies Used

- **Java 23**: Main programming language.
- **Spring Boot**: Framework used to build the application.
- **Apache PDFBox**: Library for reading and extracting content from PDF files.

## Installation

### Prerequisites

- **Java 23 or higher**: Ensure you have Java 23 or a later version installed.
- **Maven**: A project management tool for Java projects. It is required to manage dependencies and build the project.
- **IDE**: Optional, but recommended to use an IDE like IntelliJ IDEA, Eclipse, or Visual Studio Code to work with the
  code.

### Steps to Run Locally

1. **Clone the repository:**

   First, clone the repository to your local machine:

   ```bash
   git clone https://github.com/imanolvr96/parser-service.git
   cd parser-service

2. **Build the project:**

   If you have Maven installed, run the following command to build the project and download the necessary dependencies:

    ```bash
   mvn clean install


3. **Run the application:**

   You can run the application using Spring Boot by executing:

    ```bash
    mvn spring-boot:run
   
    This will start the application locally, and it will be accessible at http://localhost:8080.

## Usage

- **API Endpoint**: The service exposes a POST endpoint `/extract-text-from-upload` that accepts a PDF file containing a
  resume and returns the extracted data in a structured format (e.g., JSON).

- **Example request**:

  Use `curl` to send a POST request with a PDF file:

   ```bash
   curl -X POST -F "file=@path/to/resume.pdf" http://localhost:8080/pdf/extract-text-from-upload

Replace `path/to/resume.pdf` with the actual path to the resume PDF file you want to parse.

- **Response**: The API will return the extracted data from the resume, which may include:

    - Name
    - Contact Information
    - Work Experience
    - Education
    - Skills
    - Certifications, etc.

## How to Use the Service

1. **Run the Service Locally**: Follow the installation steps to set up and run the service on your local machine.

2. **Sending a Resume**: Once the service is running, you can send a resume in PDF format through the
   `/extract-text-from-upload`
   endpoint. You can test the service with the provided `curl` command or integrate it into a web application.

3. **Integration**: The service can be easily integrated with other systems via HTTP requests. You can call the
   `/extract-text-from-upload` endpoint from your own frontend or backend services.

## License

This project is licensed under the MIT License.