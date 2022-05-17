import React, {FunctionComponent, useEffect, useState} from 'react';
import Scanner from "./Scanner";
import {
  sampleScanResults,
  testVulnerabilities
} from "../../constants/sampleScanResults";
import PageContainer from "../../components/PageContainer";
import Heading from "../../components/Text/Heading";
import {Box, CircularProgress} from "@mui/material";
import ErrorPage from "../../components/ErrorPage";
import LoadingPage from "../../components/LoadingPage";

interface Props {
  // this function should not take any props for the time being
}

/////////////////////////////////////////////////
// todo Scanner todos

// NOW

// Connect axios to scanner

// NEXT SPRINT

// Clean up ui, especially the colours so that they are less blocky
// - maybe change this into coloured tags kind of like jira/trello

// Add additional column for the Vulnerability id / unique identifier / name
// - discuss this with the team

/////////////////////////////////////////////////


// todo this contains the logic for the scan component
const ScannerWrapper: FunctionComponent<Props> = (props) => {

  // This will store our data
  const [listOfScans, setListOfScans] = useState<ListOfScans | null>(null);

  // This will store if we are loading
  const [loading, setLoading] = useState<boolean>(true);


  // todo This is for when the scanner page initially loads
  useEffect(() => {

    // todo call the backend to initially load the data for the page
    // axios call can go here

    // after the call occurs, ensure you have a:
    // .then((res) => {
    // setListOfScans(res);
    // setLoading(false);
    // }

    // todo remove this once loading is properly implemented
    setListOfScans(sampleScanResults);
    setLoading(false);

    // this is what is called a cleanup function,
    // you probably won't need to put anything here, it is just best practice
    return () => {
      // todo handle any unresolved promises etc...
    };

    // an empty dependency array here ensures this loop only runs once
  }, []);

  // todo this is what call the update function, and runs the scanner
  function runScanner() {
    // first we set it to loading
    setLoading(true);

    // do the call

    // make sure we handle then
    // after the call occurs, ensure you have a:
    // .then((res) => {
    // setListOfScans(res);
    // setLoading(false);
    // }
  }

  if (loading) return <LoadingPage/>;
  if (!listOfScans) return <ErrorPage/>;

  return (
    // todo currently hardcoded
    <Scanner listOfScans={listOfScans} runScanner={runScanner}/>
  );
};

export default ScannerWrapper;