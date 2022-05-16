import React, {FunctionComponent, useEffect} from 'react';
import Scanner from "./Scanner";
import {testVulnerabilities} from "../../constants/scanResults";

interface Props {
  // this function should not take any props for the time being
}

// todo this contains the logic for the scan component
const ScannerWrapper: FunctionComponent<Props> = (props) => {


  // This is for when the scanner page initially loads
  useEffect(() => {

    // todo call the backend to initially load the data for the page
    // axios call can go here

    // after the call occurs, ensure you have a:
    // .then((res) => {}

    // this is what is called a cleanup function,
    // you probably won't need to put anything here, it is just best practice
    return () => {
      // todo handle any unresolved promises etc...
    };

    // an empty dependency array here ensures this loop only runs once
  }, []);





  
  return (
    <Scanner vulnerabilities={testVulnerabilities}/>
  );
};

export default ScannerWrapper;