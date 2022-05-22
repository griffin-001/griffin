import React, {FunctionComponent, useEffect, useState} from 'react';
import Scanner from "./Scanner";
import ErrorPage from "../../components/ErrorPage";
import LoadingPage from "../../components/LoadingPage";
import {
  readScanHistory,
  readUpdatedScanHistory
} from "../../apis/scanHistoryApis";
import useCancelAxiosOnUnmount from "../../hooks/useCancelAxiosOnUnmount";

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


// this contains the logic for the scan component
const ScannerWrapper: FunctionComponent<Props> = (props) => {

  // This will store our data
  const [listOfScans, setListOfScans] = useState<ListOfScans | null>(null);

  // This will store if we are loading
  const [loading, setLoading] = useState<boolean>(true);

  // this is for when the scanner page initially loads
  useEffect(() => {

    readScanHistory()
      .then((response) => {
        console.log(response)


        // this is some demo data
        // setListOfScans(sampleScanResults);

        // actual response
        setListOfScans(response.data);
        setLoading(false);
      })
      .catch((error) => {
        console.error(error);
      })

    // an empty dependency array here ensures this loop only runs once
  }, []);

  // this contains an axios cleanup function
  useCancelAxiosOnUnmount();

  // this is what call the update function, and runs the scanner
  function runScanner() {
    // first we set it to loading
    setLoading(true);

    // do the call
    readUpdatedScanHistory()
      .then((response) => {
        setListOfScans(response.data);
        // done loading
        setLoading(false);
      });
  }

  if (loading) return <LoadingPage/>;
  if (!listOfScans) return <ErrorPage/>;

  return (
    <Scanner listOfScans={listOfScans} runScanner={runScanner}/>
  );
};

export default ScannerWrapper;