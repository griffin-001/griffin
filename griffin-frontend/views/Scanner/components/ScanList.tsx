import React, {FunctionComponent} from 'react';
import SingleScan from "./SingleScan/SingleScan";

interface Props {
  listOfScans: ListOfScans,
}

const ScanList: FunctionComponent<Props> = (props) => {

  return (
    <React.Fragment>
      {props.listOfScans.map((scan, index) => (
        <SingleScan scan={scan} key={index}/>
      ))}
    </React.Fragment>
  );
};

export default ScanList;