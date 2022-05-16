import React, {FunctionComponent} from 'react';
import SingleScan from "./SingleScan/SingleScan";

interface Props {
  items: Array<Vulnerability>,
}

const ScanList: FunctionComponent<Props> = (props) => {

  return (
    <React.Fragment>
      <SingleScan items={props.items}/>
      <SingleScan items={props.items}/>
      <SingleScan items={props.items}/>
    </React.Fragment>
  );
};

export default ScanList;