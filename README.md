# Benefit App - Integrative Project 2024B

This repository contains the code and resources for the Benefit App Project, conducted by our team at Afeka College.

## Team Members and Roles
- **Gal Angel** - Team Leader
- **Yahav Ler** - SCRUM Master
- **Yam Horin** - QA Engineer / Product Owner
- **Diana Gurvits** - System Architect / Technical Writer
- **Shaked Ben Guy** - DevOps
- **Yarden Krispel** - DBA
- **Alon Bitran** - UIX Engineer

## Project Overview
The aim of the app is to provide users with a platform for managing benefits from different consumer clubs or stores. The project consists of a server-side component called "SuperApp" and a client application known as "MiniApp". There are two clients in our program: a Java client and an Android client. All MiniApps are designed for browsing and exploring points of interest. The MiniApp communicates with the SuperApp server to retrieve data from the database.

## Development Methodology
The project was developed using the agile method, divided into four sprints. At the end of each sprint, we wrote a report that contained the completed tasks, difficulties, and unfinished tasks.

## Installation

### Docker Installation

</p>
<div align="center">
 <img alt="docker" height="200px" src="https://logos-world.net/wp-content/uploads/2021/02/Docker-Logo.png">
</div>

the project use a docker image of postgre sql , you need to install docker
1. Visit the official [Docker website](https://www.docker.com/).
2. Choose the appropriate Docker version for your operating system. For macOS or Linux, hover over the download button, and two options for installation will show up.
3. Open the download link and run the installation on your computer.
4. Once the installation is complete, Docker should be ready to use.
5. Before running the server of the program, make sure Docker is running on your computer.

### Accessing the Database

You may access the database through the H2 console at [http://localhost:8085/h2-console](http://localhost:8085/h2-console).

### Installing Workspace (recommanded)
</p>
<div align="center">
 <img alt="spring boot" height="200px" src="https://cdn.hashnode.com/res/hashnode/image/upload/v1636832404785/mTXlsmro-.png?w=1600&h=840&fit=crop&crop=entropy&auto=compress,format&format=webp">
</div>

1. Download Spring Tool Suite from the following [link](https://spring.io/tools/).
2. Select the appropriate version for your operating system (Windows, macOS, or Linux) and download the installer.
3. Install Spring on your computer according to your operating system:
   - **For Windows:**
     - Run the downloaded .exe installer.
     - Follow the prompts to complete the installation.
     - Choose the installation directory or go with the default one.
   - **For macOS:**
     - Open the downloaded .dmg file.
     - Drag the STS application to the Applications folder.
   - **For Linux:**
     - Extract the downloaded .tar.gz file to the desired directory.
     - Open a terminal and navigate to the STS directory.
     - Run the STS executable to start the IDE.
4. Launch Spring Tool Suite: after installation, launch STS from the Start menu (Windows), Applications folder (macOS), or terminal (Linux). On the first run, select a workspace directory where your projects will be stored.

## Usage

To use this project, clone the repository and navigate to the relevant directories for code, data, and documentation.
1. active docker
2. clone the project
```console
git clone <repository_url>
```
3. run the project by clicking the green play button on eclips spring boot workspace
4. if you run this on VSC you need to download the <a href="https://code.visualstudio.com/docs/java/java-spring-boot">VSC extention for spring boot </a>

