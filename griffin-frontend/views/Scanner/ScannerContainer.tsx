import React, {FunctionComponent} from 'react';
import Scanner from "./Scanner";
import {testVulnerabilities} from "../../constants/scanResults";

interface Props {

}

const ScannerContainer: FunctionComponent<Props> = (props) => {

  // todo this contains the logic for the scan component


  return (
    <Scanner vulnerabilities={testVulnerabilities}/>
  );
};

export default ScannerContainer;