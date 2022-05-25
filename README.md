# Griffin

Keeping track of dependencies and vulnerabilities across multiple different SCM instances and CI/CD systems is a daunting task. Griffin solves this challenge by sitting above these services and providing a global view of the dependencies contained within a CI/CD system.

The system is split into two components, stored in  `griffin-frontend` and `griffin-backend`.

## Frontend

The frontend consists of multiple components and pages in an organised file structure, including:

- Views (Page specific files)
- Pages 
- Components (Global components)
- APIs (Connecting to the backend)

For development instructions see the [readme](griffin-frontend/README.md) in griffin-frontend.

## Backend

The backend consists of the following components, all within the same Spring Boot application:

- Collector
- Transformer
- Web Server (serving API for frontend)
- Insights Database

For development instructions see the [readme](griffin-backend/README.md) in griffin-backend.

## Style Guide

See documentation [here](https://confluence.cis.unimelb.edu.au:8443/display/SWEN900132022TZ/Coding+Standards)

## Conventions

### Branches

Live version (auto deployed): `main`

Feature branches (these branch off `main`): `feature/<jira-id>/<jira-name>`

Task branches (tasks not registered on Jira): `task/<short-description>`

### Branching Guide

To ensure that you are always working with the latest version of the code, **EVERY** time you start work, in **ANY** branch, do the following:

> Update that branch from `main`.
> Then update your local dependencies.

Likewise, whenever you are about to make a pull request:

> Update the branch from `main`.

If everyone follows this, merge conflicts will be almost entirely eliminated.

Finally, whenever you update dependencies, to ensure that everyone is working with the most recent set of dependencies:

> Push this information into the main branch as soon as possible.

## Team

For team information, see the [home page](https://confluence.cis.unimelb.edu.au:8443/pages/viewpage.action?spaceKey=SWEN900132022TZ&title=Telstra+-+Team+T) in the documentation.
