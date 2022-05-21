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
import {readScanHistory} from "../../apis/scanHistoryApis";
import useCancelAxiosOnUnmount from "../../hooks/useCancelAxiosOnUnmount";
import BodyText from "../../components/Text/BodyText";

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

  // todo remove this - this is a test
  const [test, setTest] = useState(null);

  // this is for when the scanner page initially loads
  useEffect(() => {

    readScanHistory()
      .then((response) => {

        // todo remove this once loading is properly implemented
        setListOfScans(sampleScanResults);
        console.log(response);
        setTest(response.data);

        // todo this is the actual implementation
        // setListOfScans(response.data);

        setLoading(false);
      })
      .catch((error) => {
        console.error(error);
      })

    // an empty dependency array here ensures this loop only runs once
  }, []);

  // this contains an axios cleanup function
  useCancelAxiosOnUnmount();

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
    // todo this is just a test
    // <BodyText>
    //   {JSON.stringify(test)}
    // </BodyText>

    // todo comment this back in later
    <Scanner listOfScans={listOfScans} runScanner={runScanner}/>
  );
};

export default ScannerWrapper;