This is a [Next.js](https://nextjs.org/) project bootstrapped with [`create-next-app`](https://github.com/vercel/next.js/tree/canary/packages/create-next-app).

## Getting Started

You must have Yarn installed to run this application: [https://classic.yarnpkg.com/en/](https://classic.yarnpkg.com/en/)

Once ``yarn`` is installed **NOT** ``npm``:

Navigate to the ``griffin-frontend`` folder.  
```
cd griffin-frontend
```

Then, update your dependencies.  
```
yarn
```

Then, run the development server.  
```
yarn dev
```

Open [http://localhost:3000](http://localhost:3000) with your browser to see the result.

## Deployment

This application is automatically deployed to [Vercel](https://griffin-mu.vercel.app/scanner) continuously integrated on the main branch of this repository.

If you wish to deploy this manually, the steps to build and run the frontend include: 

Build the frontend artifacts

```
yarn build
```

Then, start your frontend
```
yarn start
```

Open [http://localhost:3000](http://localhost:3000) with your browser to see the result.

## Development

### IDE

Webstorm is **Strongly** recommended as an IDE. As students, we have access to the enterprise version for free.

To work on the frontend, open this directory as a project in Webstorm.

### File Structure

#### Views
Views describe what a specific page should be displaying. A file here should include all the code for displaying one single page within the project. If a component is specific to one page, put it here.

#### Pages 
All pages within the project should have their own file, displaying the view of that page.

#### Components (Global components)
All components that are not specific to one page should be put here.

#### APIs (Connecting to the backend)
Different API call groups should be put here in the same file. 

