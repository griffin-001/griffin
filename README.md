# Griffin

Keeping track of dependencies and vulnerabilities across multiple different SCM instances and CI/CD systems is a daunting task. Griffin solves this challenge by sitting above these services and providing a global view of the dependencies contained within a CI/CD system.

## Components

The system is split into two main components, stored in  `griffin-frontend` and `griffin-backend`.

### Frontend

TODO

### Backend

TODO

## Style Guide

See documentation [here](https://confluence.cis.unimelb.edu.au:8443/display/SWEN900132022TZ/Coding+Standards)

## Branches

Live version (auto deployed):

```Bash
main
```

Feature branches (these branch off of `main`):

```Bash
feature/<jira-id>/<jira-name>
```

Task branches (for miscellaneous tasks not directly connected to a user story):

```Bash
task/<short-description>
```

### Conventions

To ensure that you are always working with the latest version of the code, **EVERY** time you start work, in **ANY** branch, you do the following:

> Update that branch from `main`.
> Then update your local dependencies.

Likewise, whenever you are about to make a pull request:

> Update the branch from `main`.

If everyone follows this, merge conflicts will be almost entirely eliminated.

Finally, whenever you update dependencies:

> Push this information into the main branch as soon as possible.

This final point will ensure that everyone is working with the most recent set of dependencies, 
which will furher help reduce conflicts.

## Team

| Name             | Student Number |
|------------------|----------------|
| Glenn Phillips   | 820624         |
| James Vinnicombe | 916250         |
| Tom Woodruff     | 834481         |
| Caiwen Song      | 1146838        |
| Nicholas Wong    | 926736         |
| Yi Gao           | 1195878        |
| ...              |                |
| ...              |                |
| ...              |                |
| ...              |                |
